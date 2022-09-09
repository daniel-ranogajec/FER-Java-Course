package hr.fer.oprpp1.custom.collections;

/**
 * Processor is a model of an object capable of performing some operation on the passed object
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface Processor<E> {

	/**
	 * Method performed by Processor.
	 * @param value that will be processed
	 */
	void process(E value);
	
}
