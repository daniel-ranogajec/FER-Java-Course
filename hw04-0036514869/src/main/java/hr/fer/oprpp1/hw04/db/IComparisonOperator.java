package hr.fer.oprpp1.hw04.db;

/**
 * Strategy
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface IComparisonOperator {

	/**
	 * Method that takes two Strings and returns <code>true</code> if satisfied
	 */
	public boolean satisfied(String value1, String value2);
	
}
