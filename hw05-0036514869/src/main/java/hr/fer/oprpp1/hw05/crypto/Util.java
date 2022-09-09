package hr.fer.oprpp1.hw05.crypto;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class that has two methods
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Util {
	
	/**
	 * Hex-digits
	 */
	private static final List<Character> HEXDIGITS = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'a', 'b', 'c', 'd', 'e', 'f');
	
	/**
	 * Method used for converting hex to byte
	 * @param keyText hex String
	 * @return array of bytes
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() %2 != 0)
			throw new IllegalArgumentException("Odd-sized hex-value!");
		
		byte[] bytearray = new byte[keyText.length()/2];
		char[] hex = keyText.toCharArray();
		
		
		int bytecnt = 0;
		int hexcnt = 0;

		while (hexcnt < keyText.length()) {
			int x = HEXDIGITS.indexOf(Character.toLowerCase(hex[hexcnt++]));
			int y = HEXDIGITS.indexOf(Character.toLowerCase(hex[hexcnt++]));
			if (x == -1 || y == -1) 
				throw new IllegalArgumentException("Invalid characters!");
			
			bytearray[bytecnt++] = (byte)(x*16 + y);
		}
		
		return bytearray;
	}
	
	/**
	 * Method used for converting bytes to hex-value
	 * @param bytearray array of bytes
	 * @return hex String
	 */
	public static String bytetohex(byte[] bytearray) {
		String s = "";
		for (byte b : bytearray) {
			s += HEXDIGITS.get((b >> 4) & 0xF);
			s += HEXDIGITS.get(b & 0xF);
		}
		return s;
	}
	
}
