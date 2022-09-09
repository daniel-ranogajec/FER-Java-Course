package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element which represents an operator represented by a symbol
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ElementOperator extends Element{
	
	private String symbol;
	
	/**
	 * Creates new ElementOperator with given symbol
	 * @param symbol (String)
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Method that returns symbol as String
	 * @return symbol
	 */
	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Method that returns Element symbol
	 * @return symbol as String
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}


	
	
}
