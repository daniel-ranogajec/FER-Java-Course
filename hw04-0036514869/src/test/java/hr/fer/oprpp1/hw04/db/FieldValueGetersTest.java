package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FieldValueGetersTest {

	@Test
	public void test() {
		StudentRecord student = new StudentRecord("0000000001", "Peric", "Pero", 5);
		if (!(FieldValueGetters.FIRST_NAME.get(student).equals("Pero") &&
				FieldValueGetters.LAST_NAME.get(student).equals("Peric") && 
				FieldValueGetters.JMBAG.get(student).equals("0000000001")))
			Assertions.fail();
	}
	
}
