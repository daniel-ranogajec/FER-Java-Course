package hr.fer.oprpp1.custom.collections;

/**
 * Interface that tells if object is acceptable or not
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface Tester<E> {

	/**
	 * Method that tests given Object
	 * @param obj Object that you want to test
	 * @return true if object is acceptable, false otherwise
	 */
	boolean test(E obj);
	
}
