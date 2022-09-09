package hr.fer.oprpp1.hw04.db.demo;

import java.util.List;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.RecordFormatter;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class RecordFormatterDemo {

	public static void main(String[] args) {
		
		StudentDatabase db = new StudentDatabase("src/main/resources/database.txt");
		QueryParser parser = new QueryParser("jmbag >= \"0000000030\" and firstname like \"A*\"");
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		List<String> output = RecordFormatter.format(records);
		output.forEach(System.out::println);
		
		System.out.println();
		
		QueryParser parser2 = new QueryParser("jmbag = \"0000000003\" ");
		List<StudentRecord> records2 = db.filter(new QueryFilter(parser2.getQuery()));
		List<String> output2 = RecordFormatter.format(records2);
		output2.forEach(System.out::println);
		
		System.out.println();
		
		QueryParser parser3 = new QueryParser("lastName LIKE \"Be*\"");
		List<StudentRecord> records3 = db.filter(new QueryFilter(parser3.getQuery()));
		List<String> output3 = RecordFormatter.format(records3);
		output3.forEach(System.out::println);
		
	}
}
