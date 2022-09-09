package hr.fer.oprpp1.hw04.db.demo;

import hr.fer.oprpp1.hw04.db.QueryParser;

public class QueryParserDemo {

	public static void main(String[] args) {

		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\"	");
		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
		System.out.println("size: " + qp1.getQuery().size()); // 1
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
		try {
			System.out.println(qp2.getQueriedJMBAG()); // would throw!
		} catch (IllegalStateException ex) {
			System.out.println(ex);
		}
		System.out.println("size: " + qp2.getQuery().size()); // 2

		
		
	}
}
