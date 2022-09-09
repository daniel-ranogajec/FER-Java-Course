package hr.fer.oprpp1.custom.collections.demo;

import java.util.NoSuchElementException;

import hr.fer.oprpp1.custom.collections.*;

public class ElementGetterDemo {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (NoSuchElementException ex) {
			System.err.println(ex);
		}
		
	}
}
