package hr.fer.oprpp1.hw04.db;

/**
 * Strategy
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface IFieldValueGetter {

	/**
	 * Method that takes StudentRecord and returns a String
	 */
	public String get(StudentRecord record);
	
}
