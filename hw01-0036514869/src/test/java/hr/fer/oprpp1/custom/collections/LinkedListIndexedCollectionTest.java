package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void testConstructor() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		if (!(col.isEmpty())) 
			Assertions.fail();
	}
	
	@Test
	public void testConstructorWithCollection() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		if (!(col2.size() == 2 && col2.get(0).equals(1) 
				&& col2.get(col2.size()-1).equals(2) &&
				col2.get(1) == col2.get(col2.size()-1))) 
			Assertions.fail();
	}	
		
	@Test 
	public void testAddNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		try {
			col.add(null);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test 
	public void testAddFirst() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		if (!(col.get(0).equals("Element")))
			Assertions.fail();
	}
	
	@Test 
	public void testAddTwo() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		if (!(col.get(0).equals(1)) && 
				col.get(1).equals(2) &&
				col.get(col.size()-1).equals(2) &&
				col.get(col.size()-2).equals(1))
			Assertions.fail();
	}
	
	@Test
	public void testGetOutOfBounds() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		try {
			col.get(-1);
		} catch (IndexOutOfBoundsException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testGetElement() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		if (!((col.get(0)).equals("Element")))
			Assertions.fail();
	}
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		col.clear();
		if (!(col.isEmpty()))
			Assertions.fail();
	}
	
	@Test
	public void testInsertIndexOutOfBounds() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		try {
			col.insert("Element",-1);
		} catch (IndexOutOfBoundsException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testInsertNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		try {
			col.insert(null,0);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testInsert() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.insert(3, 1);
		if (!(col.get(0).equals(1) &&
				col.get(1).equals(3) &&
				col.get(col.size()-1).equals(2) &&
				col.get(col.size()-2).equals(3) && 
				col.size() == 3))
			Assertions.fail();
	}
	
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		if (col.indexOf("Element") != 0)
			Assertions.fail();
	}
	
	@Test
	public void testIndexOfNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		if (col.indexOf(null) != -1)
			Assertions.fail();
	}
	
	
	@Test
	public void testIndexOfNotFound() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		if (col.indexOf("Element") != -1)
			Assertions.fail();
	}
	
	@Test
	public void testRemoveFirst() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(0);
		if (!(col.indexOf(2) == 0 && col.get(0).equals(2)))
			Assertions.fail();
	}
	
	@Test
	public void testRemoveLast() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(2);
		if (!(col.indexOf(3) == -1 && col.get(col.size()-1).equals(2)))
			Assertions.fail();
	}
	
	@Test
	public void testRemove() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(1);
		if (!(col.indexOf(3) == 1 && col.get(1).equals(3) 
				&& col.size() == 2))
			Assertions.fail();
	}
	
	@Test
	public void testRemoveIndexOutOfBounds() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		try {
			col.remove(1);
		} catch (IndexOutOfBoundsException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testIsEmpty() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		if (!(col.isEmpty()))
			Assertions.fail();
	}
	
	@Test
	public void testNotEmpty() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		if (col.isEmpty())
			Assertions.fail();
	}
	
	@Test
	public void testSize() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		if (col.size() != 1)
			Assertions.fail();
	}
	
	@Test
	public void testToArray() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		Object[] array = col.toArray();
		if (!(array.length == 1 && array[0].equals("Element")))
			Assertions.fail();
	}
	
	@Test
	public void testToArrayNotNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		Object[] array = col.toArray();
		if (!(array.length == 0 && array != null))
			Assertions.fail();
	}
	
	@Test
	public void testContains() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Element");
		if (!(col.contains("Element")))
			Assertions.fail();
	}
	
	@Test
	public void testForEach() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		class Proc extends Processor {
			public void process(Object value) {
				col2.add(value);
			}
		};
		col.forEach(new Proc());
		if (!(col2.get(0).equals(1) && col2.get(1).equals(2)))
			Assertions.fail();
	}
	
}
