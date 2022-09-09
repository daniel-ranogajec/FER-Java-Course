package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.Processor;

public class CollectionDemo {

	public static void main(String[] args) {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>(2);
		col.add(Integer.valueOf(20).toString());
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection<String> col2 = new LinkedListIndexedCollection<>(col);
		// This is local class representing a Processor which writes objects to System.out
		class P implements Processor<String> {
			public void process(String o) {
				System.out.println(o);
			}
		};
		System.out.println("col elements:");
		col.forEach(new P());
		System.out.println("col elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(Integer.valueOf(20)); // removes 20 from collection (at position 0)
		System.out.println();
		ArrayIndexedCollection<String> col3 = new ArrayIndexedCollection<>(2);
		col3.addAllSatisfying(col2, v -> v.length() > 3);
		col3.forEach(System.out::println);

	}

}
