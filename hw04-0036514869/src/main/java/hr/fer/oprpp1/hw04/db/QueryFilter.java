package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class that implements IFilter
 * 
 * @author Daniel_Ranogajec
 *
 */
public class QueryFilter implements IFilter {
	
	private List<ConditionalExpression> list;
	
	/**
	 * Constructor class that gets a List of ConditionalExpressions
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		super();
		this.list = list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression conditionalExpression : list) {
			if  (!(conditionalExpression.getComparisonOperator()
					.satisfied(conditionalExpression.getFieldGetter().get(record),
							conditionalExpression.getStringLiteral()))) 
				return false;
			
		}
		return true;
		
	}

}
