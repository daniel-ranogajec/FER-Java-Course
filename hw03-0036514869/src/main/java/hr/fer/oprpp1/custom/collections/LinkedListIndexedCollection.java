package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * ArrayIndexedCollection is a class that extends Collection
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LinkedListIndexedCollection<E> implements List<E> {

	/**
	 * Nested class 
	 */
	static class ListNode<E> {
		ListNode<E> previous;
		ListNode<E> next;
		E value;
		
		/**
		 * Creates new ListNode with given value.
		 * @param value that you want to store in a Collection
		 */
		ListNode(E value) {
			this.value = value;
			this.previous = null;
			this.next = null;
		}
		
	}

	private int size;
	private ListNode<E> first;
	private ListNode<E> last;
	private long modificationCount;
	
	/**
	 * Creates new LinkedListIndexedCollection with no elements
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
		modificationCount = 0L;
	}
	
	/**
	 * Creates new LinkedListIndexedCollection and fills it with other collection
	 * @param col that you want to add to this Collection
	 * @throws NullPointerException if given collection is <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection<? extends E> col) {
		if (col == null) 
			throw new NullPointerException("Given collection mustn't be null!");
		this.addAll(col);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(E value) {
		if (value == null) 
			throw new NullPointerException("You can't add null as element!");
		
		ListNode<E> node = new ListNode<E>(value);
		if (first == null) {
			first = last = node;
		} else {
			node.previous = last;
			last.next = node;
			last = node;
		}
		
		size++;
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public E get(int index) {
		if (!(index >= 0 && index < size))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1 (inclusive).");
		
		ListNode<E> temp;
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
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void insert(E value, int position) {
		if (!(position >= 0 && position <= size))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size (inclusive).");
		if (value == null) 
			throw new NullPointerException("The value mustn't be null!");
	
		ListNode<E> node = new ListNode<E>(value);
		if (position == 0) {
			first.previous = node;
			node.next = first;
			first = node;
		} else if (position == size) {
			last.next = node;
			node.previous = last;
			last = node;
		} else {
			ListNode<E> temp;
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
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc}
	 */
		public int indexOf(Object value) {
		if (value == null)
			return -1;
	
		
		ListNode<E> temp = first;
		for (int i = 0; i < size; i++) {
			if (Objects.equals(value, temp.value))
				return i;
			temp = temp.next;
		}
		
		return -1;
	}
	
	/**
	 * {@inheritDoc}
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
			ListNode<E> temp;
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
	public Object[] toArray() {
		Object[] array = new Object[this.size()];
		ListNode<E> temp = first;
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
	public boolean remove(Object value) {
		ListNode<E> temp = first;
		for (int i = 0; i < this.size; i++) {
			if (temp.value.equals(value)) {
				this.remove(i);
				return true;
			}
			temp = temp.next;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new LinkedListElementsGetter(this);
	}
	
	/**
	 * Nested class used for iterating over LinkedListCollection
	 * 
	 * @author Daniel_Ranogajec
	 *
	 */
	private class LinkedListElementsGetter implements ElementsGetter<E>{
		
		private LinkedListIndexedCollection<E> col;
		private int currentPosition;
		private ListNode<E> currentNode;
		private long savedModificationCount;
		
		/**
		 * Creates new LinkedListElementsGetter object used to iterate over given Collection
		 * @param col that you want to iterate over
		 * @throws NullPointerException if given collection is null
		 */
		LinkedListElementsGetter(LinkedListIndexedCollection<E> col) {
			if (col == null)
				throw new NullPointerException("Collection mustn't be null");
			
			this.col = col;
			this.currentPosition = 0;
			this.currentNode = col.first;
			this.savedModificationCount = col.modificationCount;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNextElement() {
			checkForModifications();
			
			if (currentPosition < col.size && currentNode != null)
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
			
			E currentNodeValue = currentNode.value;
			currentNode = currentNode.next;
			return currentNodeValue;
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
	
}