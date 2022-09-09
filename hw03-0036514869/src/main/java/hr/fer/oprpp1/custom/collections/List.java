package hr.fer.oprpp1.custom.collections;

/**
 * Interface that extends Collection
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface List<E> extends Collection<E> {
	
	/**
	 * Returns the object that is stored in backing array at position index. Valid indexes are 0 to size-1.
	 * @param index of the element you want to get
	 * @return element with given index
	 * @throws IndexOutOfBoundException if index is not between 0 and size-1 (inclusive)
	 */
	E get(int index);
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array. The legal positions are 0 to size 
	 * (both are included). 
	 * @param value that you want inserted in collection
	 * @param position where you want the value to be inserted
	 * @throws IndexOutOfBoundsException if position isn't between 0 and size (included)
	 * @throws NullPointerException if you pass <code>null</code> as value
	 */
	void insert(E value, int position);
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
	 * not found.
	 * @param value that you want to know the index of
	 * @return index of that value, -1 if not found
	 */
	int indexOf(Object value);
	
	/**
	 * Removes element at specified index from collection. Element that was previously at location index+1
	 * after this operation is on location index, etc. Legal indexes are 0 to size-1.
	 * @param index of the element you want removed
	 * @throws IndexOutOfBoundsException if index is not between 0 and size-1 (inclusive)
	 */
	void remove(int index);
	
}
