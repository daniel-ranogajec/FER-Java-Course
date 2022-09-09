package hr.fer.oprpp1.hw04.db;

/**
 * Class that offers constants of type IComparisonOperators
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ComparisonOperators {
	
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;
	public static final IComparisonOperator LIKE = (s1, s2) -> {
		if (!(s2.contains("*")))
			return s1.compareTo(s2) == 0;
		
		String[] substr = s2.toLowerCase().split("\\*");
		if (substr.length == 1) {
			if (s2.startsWith("*")) 
				return s1.toLowerCase().endsWith(substr[0]);
			return s1.toLowerCase().startsWith(substr[0]);
		}
		
		if (substr.length != 2 || s1.length() < s2.length() - 1)
			return false;
		return (s1.toLowerCase().startsWith(substr[0]) && s1.toLowerCase().endsWith(substr[1]));
	};
	
	



}
