package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Database emulator. When started the program reads the data from database.txt
 * 
 * @author Daniel_Ranogajec
 *
 */
public class StudentDB {

	public static void main(String[] args) {
		
		StudentDatabase db = new StudentDatabase("src/main/resources/database.txt");
		
		try(Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.print("> ");
				String s = sc.nextLine().toLowerCase().trim();
				if (s.equalsIgnoreCase("exit")) {
					System.out.println("Goodbye!");
					break;
				}
				
				if (s.startsWith("query")) {
					s = s.replaceFirst("query", "");
					
					QueryParser parser = new QueryParser(s);
					
					List<StudentRecord> records = new ArrayList<>();
					try {
						StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
						if (record != null)
							records.add(record);
						System.out.println("Using index for record retrieval.");
					} catch (IllegalStateException ex) {
						records = db.filter(new QueryFilter(parser.getQuery()));
					}
					
					List<String> output = RecordFormatter.format(records);
					output.forEach(System.out::println);
					System.out.println();
								
				}
			}
		}
		
	}
	
}
