package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that represents a map with scattered addressing
 * 
 * @author Daniel_Ranogajec
 *
 * @param <K> parameterized data type for key
 * @param <V> parameterized data type for value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{

	private TableEntry<K, V> table[];
	private int size;
	private int capacity;
	private int modificationCount;
	
	/**
	 * Constructor for creating a new SimpleHashtable with given capacity
	 * @param capacity for creating a hash table
	 * @throws IllegalArgumentException if capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) 
			throw new IllegalArgumentException("Size must be greater than 1!");
			
		for (int i = 0; ; i++) {
			if (Math.pow(2, i) >= capacity) {
				this.capacity = (int)Math.pow(2, i);
				break;
			}
		}
		this.table = (TableEntry<K, V>[]) new TableEntry[capacity];
		this.size = 0;
		this.modificationCount = 0;
	}
	
	/**
	 * Constructor for creating a new SimpleHashtable with capacity of 16
	 */
	public SimpleHashtable() {
		this(16);
	}
	/**
	 * Method that adds new element in a table.
	 * In case that you try adding a new element with same key as some other element, 
	 * it overwrites the old element.
	 * @param key with which the value will be associated
	 * @param value that is associated by the key
	 * @return the value of old key (returns <code>null</code> if there is no old element)
	 * @throws NullPointerException if key is <code>null</code>   
	 */
	public V put(K key, V value) {
		int slot = getSlot(key);
		if (containsKey(key)) {
			TableEntry<K, V> entry = table[slot];
			while(!(entry.key.equals(key))) {
				entry = entry.next;
			}
			V oldValue = entry.value;
			entry.value = value;
			return oldValue;
			
		} else {
			modificationCount++;
			size++;
			if (this.size >= this.capacity * 0.75) {
				reallocate();
			}
			slot = getSlot(key);
			if (table[slot] == null) {
				table[slot] = new TableEntry<K, V>(key, value, null);
			} else {
				TableEntry<K, V> entry = table[slot];
				while (entry.next != null) {
					entry = entry.next;
				}
				entry.next = new TableEntry<K, V>(key, value, null);
			}
			return null;
		}
	}
	
	/**
	 * Method used for reallocating the memory
	 */
	@SuppressWarnings("unchecked")
	private void reallocate() {
		this.capacity *= 2;
		TableEntry<K, V> oldTable[] = this.table;
		this.table = (TableEntry<K, V>[]) new TableEntry[capacity];

		for (int i = 0; i < oldTable.length; i++) {
			TableEntry<K, V> entry = oldTable[i];
			while (entry != null) {
				int slot = getSlot(entry.key);
				 if (table[slot] == null) {
						table[slot] = new TableEntry<K, V>(entry.key, entry.value, null);
				} else {
					TableEntry<K, V> entryTable = table[slot];
					while (entryTable.next != null) {
						entryTable = entryTable.next;
					}
					entryTable.next = new TableEntry<K, V>(entry.key, entry.value, null);
				}
				 
				entry = entry.next;
			}
		}

	}
	
	/**
	 * Method used for getting a slot in table for a given key
	 * @param key 
	 * @return the slot of a key
	 */
	private int getSlot(Object key) {
		return Math.abs(key.hashCode()) % this.capacity;
	}

	/**
	 * Method that returns value of a given key
	 * @param key that you want to know the value of
	 * @return value of given key, or <code>null</code> if key was not found
	 */
	public V get(Object key) {
		TableEntry<K, V> entry = getEntry(key);
		if (entry != null)
			return entry.value;
		
		return null;
	}
	
	private TableEntry<K, V> getEntry(Object key) {
		if (key == null)
			return null;
		
		int slot = getSlot(key);
		if (table[slot] != null) {
			TableEntry<K, V> entry = table[slot];
			while (entry != null && !(entry.key.equals(key))) {
				entry = entry.next;
			}
			if (entry != null)
				return entry;
		}
		return null;
	}

	/**
	 * Method that returns size of the table
	 * @return size of the table
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Method used for checking if the table contains given key
	 * @param key that you want to check
	 * @return <code>true</code> if the table contains given key
	 */
	public boolean containsKey(Object key) {
		TableEntry<K, V> entry = getEntry(key);
		if (entry != null)
			return true;
		
		return false;
	}
	
	/**
	 * Method used for checking if the table contains given value
	 * @param value that you want to check
	 * @return <code>true</code> if the table contains given value
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < capacity; i++) {
			TableEntry<K, V> entry = table[i];
			while (entry != null) {
				if (entry.value.equals(value))
					return true;
				entry = entry.next;
			}
		}
		return false;
	}
	
	/**
	 * Method used for removing element from the table
	 * @param key that you want to remove 
	 * @return value of removed key, or <code>null</code> if key was not found
	 */
	public V remove(Object key) {
		modificationCount++;
		if (containsKey(key)) {
			int slot = getSlot(key);
			TableEntry<K, V> entry = table[slot];
			V value;
			if (!(entry.key.equals(key))) {
				while (!(entry.next.key.equals(key))) {
					entry = entry.next;
				}
				value = entry.next.value;
				entry.next = entry.next.next;
			} else {
				value = table[slot].value;
				table[slot] = table[slot].next;
			}
			size--;
			return value;
		}
		return null;
	}
	
	/**
	 * Method that returns <code>true</code> if the table is empty
	 * @return <code>true</code> if the table is empty
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Returns a representation of SimpleHashtable in a form like "[" + TableEntry + ", " + TableEntry + ... + "]".
	 * @return a String representation of SimpleHashtable
	 */
	@Override
	public String toString() {
		String s = "[";
		int cnt = 0;
		for (int i = 0; i < this.capacity; i++) {
			TableEntry<K, V> entry = table[i];
			while (entry != null) {
				s += entry;
				if (++cnt != size)
					s += ", ";
				entry = entry.next;
			}	
		}
		s += "]";
		return s;
	}
	
	/**
	 * Method that returns an array of TableEntry references from the table
	 * @return array of TableEntry references
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K, V>[] array = (TableEntry<K, V>[])new TableEntry[size];
		int cnt = 0;
		for (int i = 0; i < capacity; i++) {
			TableEntry<K, V> entry = table[i];
			while (entry != null) {
				array[cnt++] = entry;
				entry = entry.next;
			}	
		}
		return array;
		
	}
	
	/**
	 * Method that removes all elements the table
	 */
	public void clear() {
		modificationCount++;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	/**
	 * Nested class used for creating the SimpleHashtable
	 * 
	 * @author Daniel_Ranogajec
	 *
	 * @param <K> parameterized data type for key
	 * @param <V> parameterized data type for value
	 */
	public static class TableEntry<K, V> {
		
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Constructor for TableEntry which gets key, value and a reference to the next TableEntry as parameters
		 * @param key of this TableEntry
		 * @param value of this TableEntry
		 * @param next reference to the next TableEntry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) 
				throw new NullPointerException("The key mustn't be null!");
			
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter function for getting the key 
		 * @return key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Getter function for getting the value 
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter function for setting a value 
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns a representation of TableEntry in a form like key + "=" + value.
		 * @return a String representation of TableEntry
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}	
		
	}

	/**
	 * Method used for creating an iterator class used for iterating over SimpleHashtable
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Nested class that implements Iterator that is used for iterating over SimpleHashtable
	 * 
	 * @author Daniel_Ranogajec
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		private int slot;
		private TableEntry<K, V> currentEntry = null;
		private TableEntry<K, V> nextEntry = null;
		private int modificationCountIter;
		
		/**
		 * Constructor for the iterator
		 */
		public IteratorImpl() {
			this.slot = 0;
			this.modificationCountIter = SimpleHashtable.this.modificationCount;
			getNextEntry();
		}

		/**
		 * Method that checks if the table has more elements stored
		 * @return <code>true</code> if it has more elements stored
		 * @throws ConcurrentModificationException if the table was change since making the iterator
		 */
		@Override
		public boolean hasNext() {
			if (this.modificationCountIter != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("The table was changed since iteration started!");
			}
			
			return nextEntry != null;
		}
		/**
		 * Method that returns the next element of the table 
		 * @return next TableEntry from the table
		 * @throws ConcurrentModificationException if the table was change since making the iterator
		 * @throws NoSuchElementException if there is no more elements in the table
		 */
		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Calling next when there are no more elements in the table.");
			}
			
			currentEntry = nextEntry;
			getNextEntry();
			return currentEntry;
		}

		/**
		 * Method used for getting the next TableEntry, changes only the nextEntry variable of this
		 */
		private void getNextEntry() {
			if (currentEntry == null) {
				while (SimpleHashtable.this.table[slot] == null) {
					slot++;
				}
				nextEntry = SimpleHashtable.this.table[slot];
				return;
			}
			
			if (currentEntry.next != null) {
				nextEntry = currentEntry.next;
				return;
			} 
			while(slot < SimpleHashtable.this.table.length - 1) {
				if (SimpleHashtable.this.table[++slot] != null) {
					nextEntry = SimpleHashtable.this.table[slot];
					return;
				}
			}
			nextEntry = null;
		}

		/**
		 * Method used for removing current element of the table, changes current TableEntry to <code>null</code>.
		 * @throws ConcurrentModificationException if the table was change since making the iterator
		 * @throws IllegalStateException when trying to remove the same element more than once
		 */
		public void remove() {
			if (this.modificationCountIter != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("The table was changed since iteration started!");
			}
			if (currentEntry == null) {
				throw new IllegalStateException("Trying to remove the same element more than once!");
			}
			
			SimpleHashtable.this.remove(currentEntry.key);
			this.modificationCountIter++;
			this.currentEntry = null;
			
		}
		
	}
}
