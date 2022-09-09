package hr.fer.oprpp1.custom.collections;

/**
 * Class used for storing pairs of Objects as key and value 
 * @author Daniel_Ranogajec
 *
 * @param <K> parameterized data type for key
 * @param <V> parameterized data type for value
*/
public class Dictionary<K,V> {
	
	private ArrayIndexedCollection<KeyValue<K,V>> col;
	
	/**
	 * Constructor used for creating a new Dictionary with given parameterized data types
	 */
	public Dictionary() {
		this.col = new ArrayIndexedCollection<KeyValue<K,V>>();
	}
	
	/**
	 * Method that returns <code>true</code> if dictionary is empty
	 * @return <code>true</code> if dictionary is empty
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * Method that returns size of dictionary
	 * @return size of dictionary
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Method that removes all elements from dictionary
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * Method that adds new element in dictionary.
	 * In case that you try adding a new element with same key as some other element, 
	 * it overwrites the old element.
	 * @param key with which the value will be associated
	 * @param value that is associated by the key
	 * @return the value of old key (returns <code>null</code> if there is no old element)
	 * @throws NullPointerException if key is <code>null</code>   
	 */
	public V put(K key, V value) {
		KeyValue<K,V> kv = getKeyValue(key);

		if (kv == null) {
			col.add(new KeyValue<K, V>(key, value));
			return null;
		} 
		col.remove(kv);
		col.add(new KeyValue<K, V>(key, value));
		return kv.value;
		
	}
	
	/**
	 * Method that returns value of a given key
	 * @param key that you want to know the value of
	 * @return value of given key, or <code>null</code> if key was not found
	 */
	public V get(Object key) {
		KeyValue<K,V> kv = getKeyValue(key);
		if (kv != null)
			return kv.value;
		return null;
	}
	
	/**
	 * Method used for removing element from dictionary
	 * @param key that you want to remove 
	 * @return value of removed key, or <code>null</code> if key was not found
	 */
	public V remove(K key) {
		KeyValue<K,V> kv = getKeyValue(key);
		if (kv == null)
			return null;
		col.remove(kv);
		return kv.value;
	}
	
	/**
	 * Method that returns KeyValue for a given key
	 * @param key that you want to get KeyValue of
	 * @return KeyValue of given key, or <code>null</code> if key was not found
	 */
	private KeyValue<K,V> getKeyValue(Object key) {
		for (int i = 0; i < col.size(); i++) {
			if (col.get(i).key.equals(key))
				return col.get(i);
		}
		return null;
	}
	
	
	/**
	 * Nested class used for creating a Dictionary
	 * 
	 * @author Daniel_Ranogajec
	 *
	 * @param <K> parameterized data type for key
	 * @param <V> parameterized data type for value
	 */
	private static class KeyValue<K,V> {
		final K key;
		final V value;
		
		KeyValue(K key, V value) {
			if (key == null) 
				throw new NullPointerException("Key mustn't be null!");
			
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns a representation of KeyValue in a form like "(" + key + ", " + value + ")".
		 * @return a String representation of KeyValue
		 */
		@Override
		public String toString() {
			return "(" + key + ", " + value + ")";
		}
		
	}

}
