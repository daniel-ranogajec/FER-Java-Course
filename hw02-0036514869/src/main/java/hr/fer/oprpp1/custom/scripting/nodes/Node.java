package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all other Node classes
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Node {
	
	private ArrayIndexedCollection col = null;
	
	/**
	 * Creates new Node
	 */
	public Node() {}
	
	/**
	 * Adds given child to an internally managed collection of children.
	 * @param child that you want to add to collection
	 * @throws NullPointerException if given <code>null</code> as child
	 */
	public void addChildNode(Node child) {
		if (child == null)
			throw new NullPointerException();
			
		if (col == null)
			col = new ArrayIndexedCollection();
		
		col.add(child);
		
	}	
	/**
	 * Returns a number of direct children for given Node.
	 * @return number of children
	 */
	public int numberOfChildren() {
		if (col == null)
			return 0;
		
		return col.size();
	}
	
	/**
	 * Returns child on given index
	 * @param index of child
	 * @return child on given index
	 * @throws IndexOutOfBoundsException 
	 */
	public Node getChild(int index) {
		if (col == null)
			throw new IndexOutOfBoundsException();
		
		return (Node) col.get(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
			return false;
		return true;
	}

	

	
}
