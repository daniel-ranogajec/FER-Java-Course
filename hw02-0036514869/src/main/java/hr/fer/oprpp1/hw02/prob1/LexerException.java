package hr.fer.oprpp1.hw02.prob1;

/**
 * Exception called when there is a problem with Lexer
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a {@code LexerException} with no detail message.
     */
    public LexerException() {
        super();
    }
	
	 /**
     * Constructs a {@code LexerException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public LexerException(String message) {
        super(message);
    }

}
