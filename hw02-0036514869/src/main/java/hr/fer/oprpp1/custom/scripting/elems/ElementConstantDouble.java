package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element that contains double value
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ElementConstantDouble extends Element{
	
	private double value;
	
	/**
	 * Creates new ElementConstantDouble with value double
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}

	/**
	 * Method that returns Element value
	 * @return value as double
	 */
	public double getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}	
	
}
