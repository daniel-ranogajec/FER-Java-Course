package hr.fer.oprpp1.hw02.prob1;

/**
 * Class that Lexer uses for tokenizing text
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;

	/**
	 * Creates new Token with type and value
	 * @param type can be one of TokenType enums
	 * @param value can be anything
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Method that returns Token type
	 * @return TokenType
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Method that returns Token value
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
