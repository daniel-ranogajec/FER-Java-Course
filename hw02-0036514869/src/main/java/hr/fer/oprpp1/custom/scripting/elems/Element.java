package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Base class for other Element classes
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Element {
	
	/**
	 * Creates new Element with no parameters
	 */
	public Element() {}

	/**
	 * Method that returns value as String
	 * @return value
	 */
	public String asText() {
		return "";
	}
	
	@Override
	public String toString() {
		return this.asText();
	}
	
}
