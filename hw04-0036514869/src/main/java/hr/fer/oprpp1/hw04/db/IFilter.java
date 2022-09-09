package hr.fer.oprpp1.hw04.db;

/**
 * Functional interface
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface IFilter {
	
	/**
	 * Method that accepts a reference to an object
	 * @param record StudentRecord
	 */
	public boolean accepts(StudentRecord record);
	
}
