package hr.fer.oprpp1.custom.collections;

/**
 * Interface which is used for iterating Collections elements
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface ElementsGetter<E> {

	/**
	 * Method that tells us if Collection has next element
	 * @return true if Collection has more elements, false otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * Method that returns reference on next element of Collection
	 * @return reference on next element of given Collection
	 * @throws NoSuchElementException if there are no more elements in collection
	 */
	E getNextElement();
	
	/**
	 * Method that calls process on all remaining elements of Collection
	 * @param p processor
	 */
	default void processRemaining(Processor<? super E> p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}			
	}
}
