package hr.fer.oprpp1.hw04.db.demo;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class QueryFilterDemo {

	public static void main(String[] args) {
		
		StudentDatabase db = new StudentDatabase("src/main/resources/database.txt");
		QueryParser parser = new QueryParser("jmbag = \"0000000001\"  ");
		if(parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			System.out.println(r);
		} else {
			for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				System.out.println(r);
			}
		}
		System.out.println();
		
		QueryParser parser2 = new QueryParser("firstName like \"Bo*\"  ");
		if(parser2.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser2.getQueriedJMBAG());
			System.out.println(r);
		} else {
			for(StudentRecord r : db.filter(new QueryFilter(parser2.getQuery()))) {
				System.out.println(r);
			}
		}
		
		System.out.println();

	}
	
}
