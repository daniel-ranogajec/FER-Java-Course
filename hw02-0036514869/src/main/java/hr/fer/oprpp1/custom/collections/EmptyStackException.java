package hr.fer.oprpp1.custom.collections;

/**
 * Exception called when pop or peek methods are called on empty stack
 * 
 * @author Daniel_Ranogajec
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@code EmptyStackException} with no detail message.
     */
	public EmptyStackException() {
		super();
	}
	
	 /**
     * Constructs a {@code EmptyStackException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
	public EmptyStackException(String s) {
		super(s);
	}
}
