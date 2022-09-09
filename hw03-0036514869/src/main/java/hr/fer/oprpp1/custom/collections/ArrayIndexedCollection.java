package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * ArrayIndexedCollection is a class that extends Collection
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ArrayIndexedCollection<E> implements List<E> {

	private int size;
	private E[] elements;
	private long modificationCount;

	/**
	 * Creates new ArrayIndexedCollection with initialCapacity
	 * @param initialCapacity sets the initial capacity of elements
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		if (initialCapacity < 1) 
			throw new IllegalArgumentException("InitialCapacity mustn't be lower than one!");
		
		this.size = 0;
		this.elements = (E[])new Object[initialCapacity];
		this.modificationCount = 0L;
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends E> other, int initialCapacity) {
		super();
		if (other == null) 
			throw new NullPointerException("The given collection mustn't be null!");
		
		if (initialCapacity < other.size()) 
			initialCapacity = other.size();	
		
		this.size = 0;
		this.elements = (E[])new Object[initialCapacity];
		this.addAll(other);
		this.modificationCount = 0L;
	}
	
	/**
	 * Creates new ArrayIndexedCollection with initialCapacity of 16 (or more if other collection is bigger than 16)
	 *  and fills it with other collection
	 */
	public ArrayIndexedCollection(Collection<? extends E> other) {
		this(other, 16);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(E value) {
		if (value==null) 
			throw new NullPointerException("The value mustn't be null!");
		
		if (size==elements.length) 
			reallocate();
		
		elements[size++] = value;
		modificationCount++;
	}
	
	/**
	 * If the elements array is full, it is reallocated by doubling its initialCapacity.
	 */
	@SuppressWarnings("unchecked")
	public void reallocate() {
		Object[] newElements = new Object[2*size];
		int i;
		for (i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = (E[])newElements;
		modificationCount++;
	}

	/**
	 * {@inheritDoc}
	 */
	public E get(int index) {
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
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void insert(E value, int position) {
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
				E temp = elements[position];
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
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
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
	 * {@inheritDoc}
	 */
	public void remove(int index) {
		if (!(index >= 0 && index < size))
			throw new IndexOutOfBoundsException("Index must be between 0 and size-1.");
		
		for (int i = index; i < size-1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size-1] = null;
		
		size--;
		modificationCount++;
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
	public boolean remove(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				this.remove(i);
				return true;
			}		
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ArrayElementsGetter(this);
	}
	
	/**
	 * Nested class used for iterating over ArrayIndexedCollection
	 * 
	 * @author Daniel_Ranogajec
	 *
	 */
	private class ArrayElementsGetter implements ElementsGetter<E>{
		
		private ArrayIndexedCollection<E> col;
		private int currentPosition;
		private long savedModificationCount;

		/**
		 * Creates new ArrayElementsGetter object used to iterate over given Collection
		 * @param col that you want to iterate over
		 * @throws NullPointerException if given collection is null
		 */
		ArrayElementsGetter(ArrayIndexedCollection<E> col) {
			if (col == null)
				throw new NullPointerException("Collection mustn't be null");
			
			this.col = col;
			this.currentPosition = 0;
			this.savedModificationCount = col.modificationCount;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNextElement() {
			checkForModifications();
			
			if (currentPosition < col.size && col.elements[currentPosition] != null)
				return true;
			
			return false;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public E getNextElement() {
			checkForModifications();

			if (!(this.hasNextElement())) 
				throw new NoSuchElementException("There are no more elements in this Collection.");
			
			return col.elements[currentPosition++];
		}
		
		/**
		 * Method that makes sure Collection didn't change since making this ElementsGetter.
		 * @throws ConcurrentModificationException if Collection was modified
		 */
		private void checkForModifications() {
			if (this.savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("The Collection has been modified");
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + (int) (modificationCount ^ (modificationCount >>> 32));
		result = prime * result + size;
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ArrayIndexedCollection))
			return false;
		ArrayIndexedCollection<E> other = (ArrayIndexedCollection<E>) obj;
		if (!Arrays.deepEquals(elements, other.elements))
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	
	

}
