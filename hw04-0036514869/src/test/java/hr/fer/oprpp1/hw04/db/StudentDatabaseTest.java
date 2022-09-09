package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	@Test
	public void testForJMBAG() {
		List<String> list = new ArrayList<>();
		list.add("0000000001	A	A	1");
		StudentDatabase sdb = new StudentDatabase(list);
		StudentRecord student = sdb.forJMBAG("0000000001");
		if (!(student.getFirstName().equals("A") && student.getLastName().equals("A") && student.getFinalGrade() == 1))
			Assertions.fail();
	}
	
	@Test
	public void testFilterTrue() {
		List<String> list = new ArrayList<>();
		list.add("0000000001	A	A	1");
		StudentDatabase sdb = new StudentDatabase(list);
		List<StudentRecord> listFiltered = sdb.filter(s -> s != null);
		if (listFiltered.isEmpty()) 
			Assertions.fail();
	}
	
	@Test
	public void testFilterFalse() {
		List<String> list = new ArrayList<>();
		list.add("0000000001	A	A	1");
		StudentDatabase sdb = new StudentDatabase(list);
		List<StudentRecord> listFiltered = sdb.filter(s -> s == null);
		if (!(listFiltered.isEmpty()))
			Assertions.fail();
	}
	
	@Test
	public void testRealFile() {
		StudentDatabase sdb = new StudentDatabase("src/main/resources/database.txt");
		List<StudentRecord> list = sdb.filter(s -> s != null);
		if (list.size() != 63)
			Assertions.fail();
	}
}
