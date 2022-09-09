package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Exception that is called when there is a problem with SmartScriptLexer
 * 
 * @author Daniel_Ranogajec
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a {@code SmartScriptLexerException} with no detail message.
     */
    public SmartScriptLexerException() {
        super();
    }
	
	 /**
     * Constructs a {@code SmartScriptLexerException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }
}
