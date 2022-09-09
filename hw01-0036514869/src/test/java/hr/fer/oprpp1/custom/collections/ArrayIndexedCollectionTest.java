package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void testConstructor() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		if (!(col.isEmpty() && col.size() == 0))
			Assertions.fail();
	}
	
	@Test
	public void testConstructorInput() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(1);
		
		if (!(col.isEmpty() && col.size() == 0))
			Assertions.fail();
	}
	
	@Test
	public void testConstructorException() {
		try {
			new ArrayIndexedCollection(0);
		} catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test 
	public void testConstructorOtherNull() {
		try {
			new ArrayIndexedCollection(null);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test 
	public void testConstructorOtherSizeBigger() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.add(2);
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col, 1);
		if (!(col2.size()==2 && col2.get(0).equals(1)))
			Assertions.fail();
	}
	
	@Test 
	public void testConstructorOtherIsNull() {
		try {
			new ArrayIndexedCollection(null);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testGetOutOfBounds() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		try {
			col.get(-1);
		} catch (IndexOutOfBoundsException ex) {
			return;
		}
		Assertions.fail();
		
	}
	
	@Test 
	public void testAddGetElement() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Element");
		if (!(col.get(0).equals("Element"))) {
			Assertions.fail();
		}
	}
	
	@Test 
	public void testAddNull() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		try {
			col.add(null);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testReallocate() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(1);
		col.add(1);
		col.add(2);
		if (!(col.get(0).equals(1)) && col.get(1).equals(2)) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testAddOnMaxedOutElements() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(1);
		col.add(1);
		col.add(2);
		if (col.size() != 2) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Element");
		col.clear();
		if (!(col.get(0) == null)) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testInsertIndexOutOfBoundsException() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		try {
			col.insert("Element",17);
		} catch (IndexOutOfBoundsException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testInsertNullPointerException() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		try {
			col.insert(null,0);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testInsertShifting() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(1);
		col.add(2);
		col.insert("Element",0);
		if ((!(col.get(0).equals("Element")) && !(col.get(1).equals(1) && 
				!(col.get(2).equals(2))))) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.insert("Element", 0);
		if (col.indexOf("Element") != 0) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testInsertAndIndexOf() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.add(2);
		col.insert("Element", 1);
		if (!(col.indexOf("Element") == 1 && col.indexOf(2) == 2)) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testIndexOfNotFount() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		if (col.indexOf("Element") != -1) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testIndexOfNull() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		if (col.indexOf(null) != -1) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.add(2);
		col.remove(0);
		if (!(col.get(0).equals(2)) && col.get(1) != null) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testRemoveException() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(1);
		try {
			col.remove(2);
		} catch (IndexOutOfBoundsException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testSize() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Element");
		if (col.size() != 1) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testIsEmpty() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		if (!(col.isEmpty())) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testContains() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Element");
		if (!(col.contains("Element"))) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testToArray() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Element");
		Object[] array = col.toArray();
		if (!(array.length == 1 && array[0].equals("Element")))
			Assertions.fail();
	}
	
	@Test
	public void testToArrayNotNull() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		Object[] array = col.toArray();
		if (!(array.length == 0 && array != null))
			Assertions.fail();
	}
	
	@Test
	public void testForEach() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
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
