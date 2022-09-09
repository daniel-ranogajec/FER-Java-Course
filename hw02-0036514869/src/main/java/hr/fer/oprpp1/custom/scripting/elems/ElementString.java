package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element which represents a String
 * 
 * @return
 */
public class ElementString extends Element{
	
	private String value;
	
	/**
	 * Creates mew ElementString with value
	 * @param value (String)
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return "\"" + value + "\"";
	}

	/**
	 * Method that returns Element value
	 * @return value as String
	 */
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}