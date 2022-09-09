package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectStackTest {

	@Test
	public void testConstructor() {
		ObjectStack o = new ObjectStack();
		if (!(o.isEmpty() && o.size() == 0))
			Assertions.fail();
	}
	
	@Test
	public void testConstructorOther() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Element");
		ObjectStack o = new ObjectStack(col);
		if (!(o.size() == 1 && o.peek().equals("Element")))
			Assertions.fail();
	}
	
	@Test
	public void testIsEmpty() {
		ObjectStack o = new ObjectStack();
		ObjectStack o2 = new ObjectStack();
		o2.push("Element");
		if (!(o.isEmpty()) || o2.isEmpty())
			Assertions.fail();
	}
	
	@Test
	public void testSize() {
		ObjectStack o = new ObjectStack();
		o.push("Element");
		if (o.size() != 1)
			Assertions.fail();
	}
	
	@Test
	public void testPush() {
		ObjectStack o = new ObjectStack();
		o.push("Element");
		if (!(o.size() == 1 && o.peek().equals("Element")))
			Assertions.fail();
	}
	
	@Test
	public void testPushNull() {
		ObjectStack o = new ObjectStack();
		try {
			o.push(null);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testPop() {
		ObjectStack o = new ObjectStack();
		o.push("Element");
		Object element = o.pop();
		if (!(element.equals("Element") && o.size() == 0))
			Assertions.fail();
	}	
		
	@Test
	public void testPopEmpty() {
		ObjectStack o = new ObjectStack();
		try {
			o.pop();
		} catch (EmptyStackException ex) {
			return;
		}
		Assertions.fail();
	}	
	
	@Test
	public void testPeek() {
		ObjectStack o = new ObjectStack();
		o.push("Element");
		Object element = o.peek();
		if (!(element.equals("Element") && o.size() == 1))
			Assertions.fail();
	}
	
	@Test
	public void testPeekEmpty() {
		ObjectStack o = new ObjectStack();
		try {
			o.peek();
		} catch (EmptyStackException ex) {
			return;
		}
		Assertions.fail();
	}	
	
	@Test
	public void testClear() {
		ObjectStack o = new ObjectStack();
		o.push("Element");
		o.clear();
		if (o.size() != 0)
			Assertions.fail();
	}
	
}
