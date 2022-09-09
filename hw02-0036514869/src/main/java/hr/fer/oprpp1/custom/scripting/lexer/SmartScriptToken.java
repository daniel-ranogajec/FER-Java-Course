package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Class that SmartScriptLexer uses for tokenizing text
 * 
 * @author Daniel_Ranogajec
 *
 */
public class SmartScriptToken {

	private SmartScriptTokenType type;
	private Object value;

	/**
	 * Creates new SmartScriptToken with type and value
	 * @param type can be one of SmartScriptTokenType enums
	 * @param value can be anything
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Method that returns token type
	 * @return SmartScriptTokenType
	 */
	public SmartScriptTokenType getType() {
		return type;
	}

	/**
	 * Method that returns token value
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
	
}
