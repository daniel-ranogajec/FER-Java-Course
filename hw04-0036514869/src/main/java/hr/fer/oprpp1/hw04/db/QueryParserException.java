package hr.fer.oprpp1.hw04.db;

/**
 * Exception that is called when there is a problem with QueryParser
 * 
 * @author Daniel_Ranogajec
 *
 */
public class QueryParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a {@code QueryParserException} with no detail message.
     */
    public QueryParserException() {
        super();
    }
	
	 /**
     * Constructs a {@code QueryParserException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public QueryParserException(String message) {
        super(message);
    }
}
