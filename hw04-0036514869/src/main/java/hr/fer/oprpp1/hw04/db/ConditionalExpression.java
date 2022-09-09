package hr.fer.oprpp1.hw04.db;

/**
 * Class that gets through constructor three arguments: a reference to IFieldValueGetter strategy, a reference to string literal and a reference to IComparisonOperator strategy 
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ConditionalExpression {
	
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor class that gets three arguments
	 * @param fieldGetter IFieldValueGetter
	 * @param stringLiteral String
	 * @param comparisonOperator IComparisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter function that returns IFieldValueGetter fieldGetter
	 * @return IFieldValueGetter fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter function that returns String literal
	 * @return String literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter function that returns IComparisonOperator comparisonOperator
	 * @return IComparisonOperator comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
