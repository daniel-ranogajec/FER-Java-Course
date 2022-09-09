package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UtilTest {

	@Test
	public void testhextobyte() {
		byte[] b = Util.hextobyte("01aE22");
		if (b[0] != 1 && b[1] != 2 && b[2] != 34 && b.length != 3) 
			Assertions.fail();
	}
	
	@Test
	public void testhextobyteZeroLength() {
		byte[] b = Util.hextobyte("");
		if (b.length != 0) 
			Assertions.fail();
	}
	
	@Test
	public void testhextobyteOddSized() {
		try {
			Util.hextobyte("1aE22");
		} catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testhextobyteIllegalCharacter() {
		try {
			Util.hextobyte("12ZY");
		} catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testbytetohex() {
		if (!Util.bytetohex(new byte[] {1, -82, 34}).equals("01ae22"))
			Assertions.fail();
	}
	
	@Test
	public void testbytetohexEmpty() {
		if (!Util.bytetohex(new byte[] {}).equals(""))
			Assertions.fail();
	}
}
