package hr.fer.oprpp1.custom.scripting.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.oprpp1.custom.scripting.nodes.*;

public class SmartScriptParserTest {
	
	@Test
	public void testTagNoEnd() {
		String text = "test {$ = \"Some \\ test X\" ";
		try {
			new SmartScriptParser(text);
		} catch (SmartScriptParserException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testText() {
		String text = "{ bla } blu \\{$=1$}. Nothing interesting {=here}.";
		SmartScriptParser parser = new SmartScriptParser(text);
		Node document = parser.getDocumentNode();
		if (document.numberOfChildren() != 1) 
			Assertions.fail();
	}
	
	@Test
	public void testForLoop() {
		String text = "test {$ FOR i 1 10 1 $}";
		SmartScriptParser parser = new SmartScriptParser(text);
		Node document = parser.getDocumentNode();
		ForLoopNode node = (ForLoopNode)document.getChild(1);
		if (!(((ElementVariable)node.getVariable()).asText().equals("i") && ((Element)node.getStartExpression()).asText().equals("1")
				 && ((Element)node.getEndExpression()).asText().equals("10")  && ((Element)node.getStepExpression()).asText().equals("1")))
			Assertions.fail();
	}
	
	@Test
	public void testForLoopTooManyArguments() {
		String text = "test {$ FOR i 1 10 1 2$}";
		try {
			new SmartScriptParser(text);
		} catch (SmartScriptParserException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testForLoopNotEnoughArguments() {
		String text = "test {$ FOR i 1 $}";
		try {
			new SmartScriptParser(text);
		} catch (SmartScriptParserException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testEndTag() {
		String text = "test {$ END $}";
		try {
			new SmartScriptParser(text);
		} catch (SmartScriptParserException ex) {
			return;
		}
		Assertions.fail();
	}
	

	@Test
	public void testExample1() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node document = parser.getDocumentNode();
		
		if (document.numberOfChildren() != 1) 
			Assertions.fail();
		 try {
			 @SuppressWarnings("unused")
			Node node = (TextNode)document.getChild(0);
		 } catch (ClassCastException ex) {
			 Assertions.fail();
		 }
	}

	@Test
	public void testExample2() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node document = parser.getDocumentNode();
		
		if (document.numberOfChildren() != 1) 
			Assertions.fail();
		 try {
			 @SuppressWarnings("unused")
			Node node = (TextNode)document.getChild(0);
		 } catch (ClassCastException ex) {
			 Assertions.fail();
		 }
	}
	
	@Test
	public void testExample3() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node document = parser.getDocumentNode();
		
		if (document.numberOfChildren() != 1) 
			Assertions.fail();
		 try {
			 @SuppressWarnings("unused")
			Node node = (TextNode)document.getChild(0);
		 } catch (ClassCastException ex) {
			 Assertions.fail();
		 }
	}
	
	@Test
	public void testExample4() {
		String text = readExample(4);
		 try {
			new SmartScriptParser(text);
		 } catch (SmartScriptLexerException ex) {
			 return;
		 }
		 Assertions.fail();
	}
	
	@Test
	public void testExample5() {
		String text = readExample(5);
		 try {
			new SmartScriptParser(text);
		 } catch (SmartScriptLexerException ex) {
			 return;
		 }
		 Assertions.fail();
	}
	
	@Test
	public void testExample6() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node document = parser.getDocumentNode();
		
		if (document.numberOfChildren() != 2) 
			Assertions.fail();
		 try {
			 @SuppressWarnings("unused")
			Node node = (EchoNode)document.getChild(1);
		 } catch (ClassCastException ex) {
			 Assertions.fail();
		 }
	}
	
	@Test
	public void testExample7() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node document = parser.getDocumentNode();
		
		if (document.numberOfChildren() != 2) 
			Assertions.fail();
		 try {
			 @SuppressWarnings("unused")
			Node node = (EchoNode)document.getChild(1);
		 } catch (ClassCastException ex) {
			 Assertions.fail();
		 }
	}
	
	@Test
	public void testExample8() {
		String text = readExample(8);
		 try {
			SmartScriptParser parser = new SmartScriptParser(text);
			System.out.println(((ElementString)((Element[])((EchoNode)
					(parser.getDocumentNode().getChild(1))).getElements())[0]).asText());
		 } catch (Exception ex) {
			 return;
		 }
		 Assertions.fail();
	}
	
	@Test
	public void testExample9() {
		String text = readExample(9);
		 try {
			new SmartScriptParser(text);
		 } catch (SmartScriptLexerException ex) {
			 return;
		 }
		 Assertions.fail();
	}
	
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch(IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}

}
