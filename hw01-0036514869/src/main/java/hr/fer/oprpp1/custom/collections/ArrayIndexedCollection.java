package hr.fer.oprpp1.custom.collections;

/**
 * ArrayIndexedCollection is a class that extends Collection
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	private Object[] elements;

	/**
	 * Creates new ArrayIndexedCollection with initialCapacity
	 * @param initialCapacity sets the initial capacity of elements
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		if (initialCapacity < 1) 
			throw new IllegalArgumentException("InitialCapacity mustn't be lower than one!");
		
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Creates new ArrayIndexedCollection with initialCapacity of 16
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Creates new ArrayIndexedCollection with given initialCapacity (or more if other collection 
	 * is bigger than initialCapacity) and fills it with other collection
	 * @param initialCapacity sets the initial capacity of elements
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		super();
		if (other == null) 
			throw new NullPointerException("The given collection mustn't be null!");
		
		if (initialCapacity < other.size()) 
			initialCapacity = other.size();	
		
		this.size = 0;
		this.elements = new Object[initialCapacity];
		this.addAll(other);
	}
	
	/**
	 * Creates new ArrayIndexedCollection with initialCapacity of 16 (or more if other collection is bigger than 16)
	 *  and fills it with other collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if (value==null) 
			throw new NullPointerException("The value mustn't be null!");
		
		if (size==elements.length) 
			reallocate();
		
		elements[size++] = value;
	}
	
	/**
	 * If the elements array is full, it is reallocated by doubling its initialCapacity.
	 */
	public void reallocate() {
		Object[] newElements = new Object[2*size];
		int i;
		for (i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
	}

	/**
	 * Returns the object that is stored in backing array at position index. Valid indexes are 0 to size-1.
	 * @param index of the element you want to get
	 * @return element with given index
	 * @throws IndexOutOfBoundException if index is not between 0 and size-1 (inclusive)
	 */
	public Object get(int index) {
		if (size != 0 && !(index >= 0 && index < size)) 
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1.");
		
		return elements[index];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) 
			elements[i] = null;
		size = 0;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array. The legal positions are 0 to size 
	 * (both are included). 
	 * @param value that you want inserted in collection
	 * @param position where you want the value to be inserted
	 * @throws IndexOutOfBoundsException if position isn't between 0 and size (included)
	 * @throws NullPointerException if you pass <code>null</code> as value
	 */
	public void insert(Object value, int position) {
		if (!(position >= 0 && position <= size)) 
			throw new IndexOutOfBoundsException("Position must be between 0 and size (included).");
		if (value==null) 
			throw new NullPointerException("The value mustn't be null!");
		
		if (position == elements.length)
			reallocate();
		
		if (elements[position] == null) {
			elements[position] = value;
		} else {
			while (position < elements.length) {
				Object temp = elements[position];
				elements[position] = value;
				if (temp == null) {
					break;
				}
				value = temp;
				position++;
			}
			if (position == elements.length) {
				reallocate();
				elements[position] = value;
			}
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
					
		for (int i = 0; i < size; i++) {
			if (elements[i]!=null && elements[i].equals(value))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. Element that was previously at location index+1
	 * after this operation is on location index, etc. Legal indexes are 0 to size-1.
	 * @param index of the element you want removed
	 * @throws IndexOutOfBoundsException if index is not between 0 and size-1 (inclusive)
	 */
	void remove(int index) {
		if (!(index >= 0 && index < size))
			throw new IndexOutOfBoundsException("Index must be between 0 and size-1.");
		
		for (int i = index; i < size-1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size-1] = null;
		
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
	public boolean contains(Object value) {
		return indexOf(value) == -1 ? false : true;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException if elements is null
	 */
	@Override
	public Object[] toArray() {
		if (elements == null)
			throw new UnsupportedOperationException("This method never returns null.");
		
		Object[] array = new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			array[i] = elements[i];
		}
		
		return array;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			if (elements[i] != null) {
				processor.process(elements[i]);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				this.remove(i);
				return true;
			}		
		}
		return false;
	}
}
