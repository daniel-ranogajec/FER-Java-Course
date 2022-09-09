package hr.fer.oprpp1.custom.collections.demo;

import java.util.ConcurrentModificationException;

import hr.fer.oprpp1.custom.collections.*;

public class ElementGetterDemo5 {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (ConcurrentModificationException ex) {
			System.err.println(ex);
		}
		
	}
}
