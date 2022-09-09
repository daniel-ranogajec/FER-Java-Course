package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;

/**
 * SmartScriptParser is a class used for parsing tokens given by SmartScriptLexer
 * 
 * @author Daniel_Ranogajec
 *
 */
public class SmartScriptParser {
	
	SmartScriptLexer lexer;
	DocumentNode document;
	ObjectStack parents;

	/**
	 * Creates new SmartScriptParser using SmartScript lexer on given text
	 * @param text that you want to parse
	 */
	public SmartScriptParser(String text) {
		this.lexer = new SmartScriptLexer(text);
		document = new DocumentNode();
		parents = new ObjectStack();
		parents.push(document);
		parse();
	}
	
	/**
	 * Method used for parsing the tokens
	 * @throws SmartScriptParserException if token type is unknown or if the for loop doesn't start with ElementVariable, 
	 * if it doesn't have two or three more Elements of type variable, number or string, or if it has too many arguments
	 */
	private void parse() {
		while (lexer.nextToken().getType() != SmartScriptTokenType.EOF) {
			SmartScriptToken token = lexer.getToken();
			Node child;
			if (token.getType() == SmartScriptTokenType.TEXT) {
				child = new TextNode(token.getValue().toString());
				((Node) parents.peek()).addChildNode(child);
				
			} else if (token.getType() == SmartScriptTokenType.TAG && token.getValue().equals("FOR")) {
				if (lexer.nextToken().getType() != SmartScriptTokenType.VARIABLE)
					throw new SmartScriptParserException("ForLoop must start with ElementVariable!");				
				ElementVariable variable = new ElementVariable(lexer.getToken().getValue().toString());				
				Element startExpression = forLoopGetElement(lexer.nextToken().getType());
				Element endExpression = forLoopGetElement(lexer.nextToken().getType());
				Element stepExpression = null;

				if (lexer.nextToken().getType() != SmartScriptTokenType.ENDTAG) {
					stepExpression = forLoopGetElement(lexer.getToken().getType());
					if (lexer.nextToken().getType() != SmartScriptTokenType.ENDTAG) {
						throw new SmartScriptParserException("Too many arguments in for loop!");
					}
				}
				child = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
				((Node) parents.peek()).addChildNode(child);
				parents.push(child);
				
			} else if (token.getType() == SmartScriptTokenType.TAG && token.getValue().equals("END")) {
				parents.pop();
				if (parents.isEmpty()) 
					throw new SmartScriptParserException("Document contains more {$END$}-s than opened non-empty tags");
				
			} else if (token.getType() == SmartScriptTokenType.TAG && token.getValue().equals("=")) {
				ArrayIndexedCollection elements = new ArrayIndexedCollection();
				token = lexer.nextToken();
				while ( token.getType() != SmartScriptTokenType.ENDTAG) {
					if (token.getType() == SmartScriptTokenType.EOF)
						throw new SmartScriptParserException("Tag was not closed");
					else if (token.getType() == SmartScriptTokenType.VARIABLE) 
						elements.add(new ElementVariable(token.getValue().toString()));
					else if (token.getType() == SmartScriptTokenType.INTEGER) 
						elements.add(new ElementConstantInteger(Integer.parseInt(token.getValue().toString())));
					else if (token.getType() == SmartScriptTokenType.DOUBLE) 
						elements.add(new ElementConstantDouble(Double.parseDouble(token.getValue().toString())));
					else if (token.getType() == SmartScriptTokenType.STRING) 
						elements.add(new ElementString(token.getValue().toString()));
					else if (token.getType() == SmartScriptTokenType.OPERATOR) 
						elements.add(new ElementOperator(token.getValue().toString()));
					else if (token.getType() == SmartScriptTokenType.FUNCTION) 
						elements.add(new ElementFunction(token.getValue().toString()));
					else
						throw new SmartScriptParserException("Unknown token type.");
					token = lexer.nextToken();
				}
				
				Element[] elems = new Element[elements.size()];
				for (int i = 0; i < elements.size(); i++) 
					elems[i] = (Element)elements.get(i);
				
				child = new EchoNode(elems);
				((Node) parents.peek()).addChildNode(child);
				
			} else {
				throw new SmartScriptParserException("Unknown token type.");
			}
		} 
	}
	
	/**
	 * Method used for parsing parameters for the for loop
	 * @param type
	 * @return element
	 */
	private Element forLoopGetElement(SmartScriptTokenType type) {
		Element elem;
		if (type == SmartScriptTokenType.VARIABLE) {
			elem = new ElementVariable((String) lexer.getToken().getValue());
		} else if (type == SmartScriptTokenType.INTEGER) {
			elem = new ElementConstantInteger(Integer.parseInt(lexer.getToken().getValue().toString()));
		} else if (type == SmartScriptTokenType.DOUBLE) {
			elem = new ElementConstantDouble(Double.parseDouble(lexer.getToken().getValue().toString()));
		} else if (type == SmartScriptTokenType.STRING) {
			elem = new ElementString((String) lexer.getToken().getValue());
		} else {
			throw new SmartScriptParserException("ForLoop must have one ElementVariable and after that two or three Elements of type variable, number or string.");
		}
		return elem;
	}

	/**
	 * Method that returns the main document node
	 * @return DocumentNode
	 */
	public DocumentNode getDocumentNode() {
		return document;
	}	
	
}
