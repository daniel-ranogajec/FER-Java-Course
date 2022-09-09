package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken());
	}

	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetToken() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test");
		try {
			lexer.getToken();
		} catch (SmartScriptLexerException ex){
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testNextToken() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test");
		lexer.nextToken();
		if (lexer.getToken().getType() != SmartScriptTokenType.TEXT)
			Assertions.fail();
	}
	
	@Test
	public void testNextTokens() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test {$ = var 12.2 \"str\" @f + i");
		if (lexer.nextToken().getType() != SmartScriptTokenType.TEXT)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.TAG)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.VARIABLE)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.DOUBLE)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.STRING)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.FUNCTION)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.OPERATOR)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.VARIABLE)
			Assertions.fail();
		else if (lexer.nextToken().getType() != SmartScriptTokenType.EOF)
			Assertions.fail();
	}
	
	@Test
	public void testStringIllegalEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test {$ = \"string \\{ test\"");
		lexer.nextToken();
		lexer.nextToken();
		try {
			lexer.nextToken();
		} catch (SmartScriptLexerException ex) {
			return;
		}
		Assertions.fail();
	}

	@Test
	public void testIllegalFunction() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test {$ = @0_Function");
		lexer.nextToken();
		lexer.nextToken();
		try {
			lexer.nextToken();
		} catch (SmartScriptLexerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testUnknownBodyToken() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test {$ = =");
		lexer.nextToken();
		lexer.nextToken();
		try {
			lexer.nextToken();
		} catch (SmartScriptLexerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testUnknownTagToken() {
		SmartScriptLexer lexer = new SmartScriptLexer("Test {$ TEST test");
		lexer.nextToken();
		try {
			lexer.nextToken();
		} catch (SmartScriptLexerException ex) {
			return;
		}
		Assertions.fail();
	}

	



}
