package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically
 * 
 * @author Daniel_Ranogajec
 *
 */
public class EchoNode extends Node{
	
	private Element[] elements;

	/**
	 * Creates new EchoNode with array of elements as parameter
	 * @param elements that you want to store in EchoNode
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Method that returns an array of elements stored in EchoNode
	 * @return array of Element objects
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public String toString() {
		String s = "{$ = ";
		for (Element element : elements) {
			s += element.toString() + " ";
		}
		s += "$}";
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		if (!Arrays.equals(elements, other.elements))
			return false;
		return true;
	}
	
	
	
}
