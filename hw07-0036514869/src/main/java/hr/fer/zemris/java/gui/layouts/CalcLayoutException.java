package hr.fer.zemris.java.gui.layouts;

/**
 * Exception that is called when there is a problem with CalcLayout
 * 
 * @author Daniel_Ranogajec
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a {@code CalcLayoutException} with no detail message.
     */
    public CalcLayoutException() {
        super();
    }
	
	 /**
     * Constructs a {@code CalcLayoutException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
