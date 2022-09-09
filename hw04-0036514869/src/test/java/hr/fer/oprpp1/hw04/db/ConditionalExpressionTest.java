package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	@Test
	public void test() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Per*",
				ComparisonOperators.LIKE);
		StudentRecord student = new StudentRecord("0000000001", "Peric", "Pero", 5);
		if (!(expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(student), 
				expr.getStringLiteral()))) 
			Assertions.fail();
	}
}
