package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void testIsEmpty() {
		Dictionary<String, Integer> d= new Dictionary<>();
		if (!(d.isEmpty()))
			Assertions.fail();
	}

	@Test
	public void testSize() {
		Dictionary<String, Integer> d= new Dictionary<>();
		d.put("key", 1);
		if (!(d.size() == 1))
			Assertions.fail();
	}

	@Test
	public void testClear() {
		Dictionary<String, Integer> d= new Dictionary<>();
		d.put("key", 1);
		d.clear();
		if (!(d.size() == 0))
			Assertions.fail();
	}
	
	@Test
	public void testPut() {
		Dictionary<String, Integer> d= new Dictionary<>();
		d.put("key", 1);
		if (d.put("key", 2) != 1 || d.size() != 1 || !(d.get("key").equals(2)))
			Assertions.fail();
	}
	
	@Test
	public void testGet() {
		Dictionary<String, Integer> d= new Dictionary<>();
		d.put("key", 1);
		if (d.get("key") != 1) 
			Assertions.fail();
	}
	
	@Test
	public void testRemove() {
		Dictionary<String, Integer> d= new Dictionary<>();
		d.put("key", 1);
		if (d.remove("key") != 1 || d.size() != 0 || d.remove("random") != null) 
			Assertions.fail();
	}
	
	@Test
	public void testNullKey() {
		Dictionary<String, Integer> d= new Dictionary<>();
		try {
			d.put(null, 1);
		} catch (NullPointerException ex) {
			return;
		}
		Assertions.fail();
	}



}
