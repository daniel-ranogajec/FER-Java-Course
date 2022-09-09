package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.math.Vector2D;

public class Vector2DTest {

	@Test
	public void testConstructor() {
		Vector2D vector = new Vector2D(0,0);
		if (!(vector.getX() == 0 && vector.getY() == 0))
			Assertions.fail();
	}
	
	@Test
	public void testAdd() {
		Vector2D vector = new Vector2D(1,0);
		vector.add(new Vector2D(0,1));
		if (!(vector.getX() == 1 && vector.getY() == 1))
			Assertions.fail();
	}
	
	@Test
	public void testAdded() {
		Vector2D vector = new Vector2D(1,0);
		Vector2D vector2 = vector.added(new Vector2D(0,1));
		if (!(vector2.getX() == 1 && vector2.getY() == 1)
				&& vector.getX() == 1 && vector.getY() == 0)
			Assertions.fail();
	}
	
	@Test
	public void testRotate() {
		Vector2D vector = new Vector2D(1,1);
		vector.rotate(Math.PI);
		if (!(Math.round(vector.getX()) == -1 && Math.round(vector.getY()) == -1))
			Assertions.fail();
	}
	
	@Test
	public void testRotated() {
		Vector2D vector = new Vector2D(1,1);
		Vector2D vector2 = vector.rotated(Math.PI);
		if (!(Math.round(vector2.getX()) == -1 && Math.round(vector2.getY()) == -1
				&& vector.getX() == 1 && vector.getY() == 1))
			Assertions.fail();
	}
	
	@Test
	public void testScale() {
		Vector2D vector = new Vector2D(1,1);
		vector.scale(2);
		if (!(vector.getX() == 2 && vector.getY() == 2))
			Assertions.fail();
	}

	@Test
	public void testScaled() {
		Vector2D vector = new Vector2D(1,1);
		Vector2D vector2 = vector.scaled(2);
		if (!(vector2.getX() == 2 && vector2.getY() == 2
				&& vector.getX() == 1 && vector.getY() == 1))
			Assertions.fail();
	}
	
	@Test
	public void copy() {
		Vector2D vector = new Vector2D(1,1);
		Vector2D vector2 = vector.copy();
		if (!(vector2.getX() == 1 && vector2.getY() == 1))
			Assertions.fail();
	}
}
