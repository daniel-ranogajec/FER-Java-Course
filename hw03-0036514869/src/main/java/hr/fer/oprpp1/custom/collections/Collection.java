package hr.fer.oprpp1.custom.collections;

/**
 * Collection is an interface that represents some general collection of objects
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface Collection<E>{
	
	/**
	 * Returns true if collection contains no objects and false otherwise. 
	 * @return true if collection is empty, false otherwise
	 */
	default boolean isEmpty() {
		if (this.size() ==0 ) 
			return true;
		return false;
	}
	
	/**
	 * Returns the number of currently stored objects in this collections.
	 * @return number of objects stored in this collection
	 */
	int size();
	
	/**
	 * Adds the given object into this collection.
	 * @param value that you want to add to this collection
	 */
	void add(E value);
	
	/**
	 * Returns true only if the collection contains given value, as determined by equals method. 
	 * @param value that you want to check if it is in the collection
	 * @return true if collection contains the object, false otherwise
	 */
	boolean contains(Object value);
	
	/**
	 * Returns true only if the collection contains given value as determined by equals method and removes 
	 * one occurrence of it.
	 * @param value that you want to remove
	 * @return true if the value was removed, false otherwise
	 */
	boolean remove(Object value);
		
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and
	 * returns the array. This method never returns null.
	 * @throws UnsupportedOperationException 
	 */
	Object[] toArray();
	
	/**
	 * Method calls processor.process(.) for each element of this collection. 
	 * @param processor you want to use process on
	 */
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}	
	
	/**
	 * Method adds into the current collection all elements from the given collection. 
	 * Other collection remains unchanged.
	 * @param other is the Collection you want to add to this Collection
	 */
	default void addAll(Collection<? extends E>other) {
		other.forEach(this::add);
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	void clear();
	
	/**
	 * Method that creates new ElementsGetter which is used for iterating over Collection.
	 * @return new ElementsGetter
	 */
	ElementsGetter<E> createElementsGetter();
	
	/**
	 * Adds elements from other collection to this collection if they pass a test given by tester
	 * @param col that you want to add elements from
	 * @param tester that you want to use to test with
	 */
	default void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester) {
		ElementsGetter<? extends E> getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			E o = getter.getNextElement();
			if (tester.test(o))
				this.add(o);
		}
	}
}

