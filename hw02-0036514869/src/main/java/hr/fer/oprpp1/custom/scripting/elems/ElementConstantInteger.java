package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element that contains integer value
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ElementConstantInteger extends Element{
	
	private int value;
	
	/**
	 * Creates new ElementConstantInteger with value int
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/**
	 * Method that returns Element value
	 * @return value as int
	 */
	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		if (value != other.value)
			return false;
		return true;
	}


	
	
}
