package hr.fer.oprpp1.custom.collections;

/**
 * Collection is a class that represents some general collection of objects
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Collection {
		
	/**
	 * Creates new Collection.
	 */
	Collection() {}
	
	/**
	 * Returns true if collection contains no objects and false otherwise. 
	 * @return true if collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (this.size()==0) return true;
		return false;
	}
	
	/**
	 * Returns the number of currently stored objects in this collections.
	 * @return number of objects stored in this collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection.
	 * @param value that you want to add to this collection
	 */
	public void add(Object value) {}
	
	/**
	 * Returns true only if the collection contains given value, as determined by equals method. 
	 * @param value that you want to check if it is in the collection
	 * @return true if collection contains the object, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Returns true only if the collection contains given value as determined by equals method and removes 
	 * one occurrence of it.
	 * @param value that you want to remove
	 * @return true if the value was removed, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and
	 * returns the array. This method never returns null.
	 * @throws UnsupportedOperationException 
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process(.) for each element of this collection. 
	 * @param processor you want to use process on
	 */
	public void forEach(Processor processor) {}
	
	/**
	 * Method adds into the current collection all elements from the given collection. 
	 * Other collection remains unchanged.
	 * @param other is the Collection you want to add to this Collection
	 */
	public void addAll(Collection other) {
		class LocalProcessor extends Processor{
			
			public LocalProcessor() {}
			
			@Override
			public void process(Object value) {
				if (value != null)
					Collection.this.add(value);
			}
			
		}
		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {}
	
}

