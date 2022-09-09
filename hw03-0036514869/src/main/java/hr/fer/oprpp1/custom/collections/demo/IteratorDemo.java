package hr.fer.oprpp1.custom.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class IteratorDemo {

	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		System.out.println(examMarks);
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}		
		
		System.out.println();
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(),
						pair2.getKey(), pair2.getValue());
			}
		}
		
		System.out.println();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
				try {
					iter.remove();
				} catch (IllegalStateException ex) {
					System.out.println(ex);
				}
			}
		}
		System.out.println();
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		System.out.println();
		
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
		while(iter2.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter2.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
				try {
					iter2.hasNext();
				} catch (ConcurrentModificationException ex) {
					System.out.println(ex);
					break;
				}
			}
		}
		System.out.println();
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter3 = examMarks.iterator();
		while(iter3.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter3.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter3.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());
	}

}
