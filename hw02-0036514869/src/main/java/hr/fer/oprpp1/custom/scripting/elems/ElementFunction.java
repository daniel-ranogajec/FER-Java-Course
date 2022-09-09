package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element which represents a function that has a name
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ElementFunction extends Element{
	
	private String name;
	
	/**
	 * Creates new ElementConstantInteger with name 
	 * @param name (String)
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Method that returns name as String
	 * @return name
	 */
	@Override
	public String asText() {
		return "@" + name;
	}

	/**
	 * Method that returns Element name
	 * @return name as String
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}


