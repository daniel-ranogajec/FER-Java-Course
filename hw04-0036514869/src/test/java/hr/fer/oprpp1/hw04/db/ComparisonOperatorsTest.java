package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		if (!(oper.satisfied("Ana", "Jasna")))
			Assertions.fail();
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		if (oper.satisfied("Ana", "Jasna"))
			Assertions.fail();
	}
	
	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		if (!(oper.satisfied("Ana", "Ana")))
			Assertions.fail();
	}
	
	@Test
	public void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		if (oper.satisfied("Zagreb", "Aba*"))
			Assertions.fail();
		if (oper.satisfied("AAA", "AA*AA"))
			Assertions.fail();
		if (!(oper.satisfied("AAAA", "AA*AA")))
			Assertions.fail();
	}
	
	
}
