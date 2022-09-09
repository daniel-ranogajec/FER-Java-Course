package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Exception that is called when there is a problem with SmartScriptParser
 * 
 * @author Daniel_Ranogajec
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a {@code SmartScriptParserException} with no detail message.
     */
    public SmartScriptParserException() {
        super();
    }
	
	 /**
     * Constructs a {@code SmartScriptParserException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public SmartScriptParserException(String message) {
        super(message);
    }
}
