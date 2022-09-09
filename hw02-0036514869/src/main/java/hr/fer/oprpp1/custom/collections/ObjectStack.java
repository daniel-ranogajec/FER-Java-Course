package hr.fer.oprpp1.custom.collections;


/**
 * ObjectStack is an stack-like collection
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ObjectStack {

	ArrayIndexedCollection col;
	
	/**
	 * Creates new ObjectStack
	 */
	public ObjectStack() {
		col = new ArrayIndexedCollection();
	}
	
	/**
	 * Creates new ObjectStack and fills it in with another collection
	 * @param c that will be added to this ObjectStack
	 */
	public ObjectStack(Collection c) {
		col = new ArrayIndexedCollection(c);
	}
	
	/**
	 * Returns true if collection contains no objects and false otherwise. 
	 * @return true if collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		return col.isEmpty() ? true : false;
	}
	
	/**
	 * Returns the number of currently stored objects in this collections.
	 * @return number of objects stored in this collection
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Pushes given value on the stack
	 * @param value that you want to add on top of stack
	 * @throws NullPointerException if <code>null</code> is given as value
	 */
	public void push(Object value) {
		if (value == null)
			throw new NullPointerException("Null value cant be placed on stack.");
		
		col.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * @return last value pushed on stack
	 * @throws EmptyStackException if pop is called on empty stack
	 */
	public Object pop() {
		if (col.size() == 0)
			throw new EmptyStackException("Popping from an empty stack.");
		
		Object popped = col.get(col.size()-1);
		col.remove(col.size()-1);	
		return popped;
	}
	
	/**
	 * Returns last element placed on stack.
	 * @return last value pushed on stack
	 * @throws EmptyStackException if peek is called on empty stack
	 */
	public Object peek() {
		if (col.size() == 0)
			throw new EmptyStackException("Peeking on an empty stack.");
		
		Object peeked = col.get(col.size()-1);
		return peeked;	
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	void clear() {
		col.clear();
	}
	
	
}
