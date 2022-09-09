package hr.fer.oprpp1.custom.collections;

/**
 * ArrayIndexedCollection is a class that extends Collection
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Nested class 
	 */
	static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
		
		/**
		 * Creates new ListNode with given value.
		 * @param value that you want to store in a Collection
		 */
		ListNode(Object value) {
			this.value = value;
			this.previous = null;
			this.next = null;
		}
		
	}

	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Creates new LinkedListIndexedCollection with no elements
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Creates new LinkedListIndexedCollection and fills it with other collection
	 * @param col that you want to add to this Collection
	 * @throws NullPointerException if given collection is <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection col) {
		if (col == null) 
			throw new NullPointerException("Given collection mustn't be null!");
		this.addAll(col);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) 
			throw new NullPointerException("You can't add null as element!");
		
		ListNode node = new ListNode(value);
		if (first == null) {
			first = last = node;
		} else {
			node.previous = last;
			last.next = node;
			last = node;
		}
		size++;
	}
	
	/**
	 * Returns the object that is stored in backing array at position index. Valid indexes are 0 to size-1.
	 * @param index of the element you want to get
	 * @return value of element with given index
	 * @throws IndexOutOfBoundException if index is not between 0 and size-1 (inclusive)
	 */
	public Object get(int index) {
		if (!(index >= 0 && index < size))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1 (inclusive).");
		
		ListNode temp;
		if (index < size/2) {
			temp = first;
			for (int i = 0; i < index; i++) {
				temp = temp.next;
			}
		} else {
			temp = last;
			for (int i = size-1; i > index; i--) {
				temp = temp.previous;
			}
		}
		
		return temp.value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in linked-list. 
	 * Elements starting from this position are shifted one position. The legal positions are 0 to size 
	 * (both are included). 
	 * @param value that you want inserted in collection
	 * @param position where you want the value to be inserted
	 * @throws IndexOutOfBoundsException if position isn't between 0 and size (included)
	 * @throws NullPointerException if you pass <code>null</code> as value
	 */
	public void insert(Object value, int position) {
		if (!(position >= 0 && position <= size))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size (inclusive).");
		if (value == null) 
			throw new NullPointerException("The value mustn't be null!");
	
		ListNode node = new ListNode(value);
		if (position == 0) {
			first.previous = node;
			node.next = first;
			first = node;
		} else if (position == size) {
			last.next = node;
			node.previous = last;
			last = node;
		} else {
			ListNode temp;
			if (position < size/2) {
				temp = first;
				for (int i = 0; i < position; i++) {
					temp = temp.next;
				}
			} else {
				temp = last;
				for (int i = size-1; i > position; i--) {
					temp = temp.previous;
				}
			}
			temp.previous.next = node;
			node.previous = temp.previous;
			node.next = temp;
			temp.previous = node;
		}
		
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
	 * not found.
	 * @param value that you want to know the index of
	 * @return index of that value, -1 if not found
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;
	
		ListNode node = new ListNode(value);
		ListNode temp = first;
		for (int i = 0; i < size; i++) {
			if (node.value.equals(temp.value))
				return i;
			temp = temp.next;
		}
		
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. Element that was previously at location index+1
	 * after this operation is on location index, etc. Legal indexes are 0 to size-1.
	 * @param index of the element you want removed
	 * @throws IndexOutOfBoundsException if index is not between 0 and size-1 (inclusive)
	 */
	public void remove(int index) {
		if (!(index >= 0 && index < size))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1 (inclusive).");
		
		if (size == 1) {
			first = last = null;
		} else if  (index == 0) {
			first = first.next;
			first.previous = null;
		} else if (index == size-1) {
			last = last.previous;
			last.next = null;
		} else {
			ListNode temp;
			if (index < size/2) {
				temp = first;
				for (int i = 0; i < index; i++) {
					temp = temp.next;
				}
			} else {
				temp = last;
				for (int i = size-1; i > index; i--) {
					temp = temp.previous;
				}
			}
			temp.previous.next = temp.next;
			temp.next.previous = temp.previous;
		}
		size--;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		if (this.size() == 0) 
			return true;
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size()];
		ListNode temp = first;
		for (int i = 0; i < this.size(); i++) {
			array[i] = temp.value;
			temp = temp.next;
		}
		return array;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) == -1 ? false : true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode temp = first;
		for (int i = 0; i < size; i++) {
			processor.process(temp.value);
			temp = temp.next;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		ListNode temp = first;
		for (int i = 0; i < this.size; i++) {
			if (temp.value.equals(value)) {
				this.remove(i);
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	
}