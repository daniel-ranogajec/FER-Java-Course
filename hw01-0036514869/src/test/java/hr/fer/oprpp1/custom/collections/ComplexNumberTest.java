package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test
	public void testConstructor() {
		ComplexNumber num = new ComplexNumber(0,0);
		if (!(num.toString().equals("0")))
			Assertions.fail();
	}
	
	@Test
	public void testConstructor2() {
		ComplexNumber num = new ComplexNumber(1,1);
		if (!(num.toString().equals("1+i")))
			Assertions.fail();
	}
	
	@Test
	public void testConstructor3() {
		ComplexNumber num = new ComplexNumber(1,-1);
		if (!(num.toString().equals("1-i")))
			Assertions.fail();
	}
	
	@Test
	public void testFromReal() {
		ComplexNumber num = ComplexNumber.fromReal(1);
		if (!(num.toString().equals("1")))
			Assertions.fail();
	}
	
	@Test
	public void testFromImaginary() {
		ComplexNumber num = ComplexNumber.fromImaginary(1);
		if (!(num.toString().equals("i")))
			Assertions.fail();
	}
	
	@Test
	public void testFromImaginary2() {
		ComplexNumber num = ComplexNumber.fromImaginary(-2);
		if (!(num.toString().equals("-2i")))
			Assertions.fail();
	}
	
	@Test
	public void testFromMagintudeAndAngle() {
		ComplexNumber num = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), 45);
		if (!(Math.round(num.getReal())==1 && Math.round(num.getImaginary())==1))
			Assertions.fail();
	}
	
	@Test
	public void testParse() {
		String s = "+3.51";
		ComplexNumber num = ComplexNumber.parse(s);
		if (!(num.getReal() == 3.51 && num.getImaginary() == 0))
			Assertions.fail();
	}
	
	@Test
	public void testParse2() {
		String s = "-3.17";
		ComplexNumber num = ComplexNumber.parse(s);
		if (!(num.getReal() == -3.17 && num.getImaginary() == 0))
			Assertions.fail();
	}
	@Test
	public void testParse3() {
		String s = "-2.71i";
		ComplexNumber num = ComplexNumber.parse(s);
		if (!(num.getReal() == 0 && num.getImaginary() == -2.71))
			Assertions.fail();
	}
	
	@Test
	public void testParse4() {
		String s = "i";
		ComplexNumber num = ComplexNumber.parse(s);
		if (!(num.getReal() == 0 && num.getImaginary() == 1))
			Assertions.fail();
	}
	
	@Test
	public void testParse5() {
		String s = "-i";
		ComplexNumber num = ComplexNumber.parse(s);
		if (!(num.getReal() == 0 && num.getImaginary() == -1))
			Assertions.fail();
	}
	
	@Test
	public void testParse6() {
		String s = "1+i";
		ComplexNumber num = ComplexNumber.parse(s);
		if (!(num.getReal() == 1 && num.getImaginary() == 1))
			Assertions.fail();
	}
	
	@Test
	public void testParse7() {
		String s = "-2.71-3.15i";
		ComplexNumber num = ComplexNumber.parse(s);
		if (!(num.getReal() == -2.71 && num.getImaginary() == -3.15))
			Assertions.fail();
	}
	@Test
	public void testParseException() {
		String s = "-2.71--3.15i";
		try {
			ComplexNumber.parse(s);
		}
		catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}

	@Test
	public void testParseException2() {
		String s = "-2.71-i3.15";
		try {
			ComplexNumber.parse(s);
		}
		catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testGetReal() {
		ComplexNumber num = new ComplexNumber(2,1);
		if (num.getReal() != 2)
			Assertions.fail();
	}
	
	@Test
	public void testGetImaginary() {
		ComplexNumber num = new ComplexNumber(2,1);
		if (num.getImaginary() != 1)
			Assertions.fail();
	}

	@Test
	public void testGetMagnitude() {
		ComplexNumber num = new ComplexNumber(1,1);
		if (num.getMagnitude() != Math.sqrt(2))
			Assertions.fail();
	}
	
	@Test
	public void testGetAngle() {
		ComplexNumber num = new ComplexNumber(0,1);
		if (num.getAngle() != Math.PI/2)
			Assertions.fail();
	}
	
	@Test
	public void testAdd() {
		ComplexNumber num = new ComplexNumber(1,1);
		ComplexNumber num2 = num.add(new ComplexNumber(1,1));
		if (num2.getReal() != 2 && num2.getImaginary() != 2)
			Assertions.fail();
	}
	
	@Test
	public void testSub() {
		ComplexNumber num = new ComplexNumber(3,2);
		ComplexNumber num2 = num.mul(new ComplexNumber(1,2));
		if (num2.getReal() != -1 && num2.getImaginary() != 8)
			Assertions.fail();
	}
	
	@Test
	public void testDiv() {
		ComplexNumber num = new ComplexNumber(3,2);
		ComplexNumber num2 = num.div(new ComplexNumber(1,2));
		if (num2.getReal() != 1.4 && num2.getImaginary() != -0.8)
			Assertions.fail();
	}
	
	@Test
	public void testPower() {
		ComplexNumber num = new ComplexNumber(3,2);
		ComplexNumber num2 = num.power(3);
		if (num2.getReal() != -9 && num2.getImaginary() != 46)
			Assertions.fail();
	}
	
	@Test
	public void testPowerException() {
		ComplexNumber num = new ComplexNumber(3,2);
		try {
			num.power(-1);
		} catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}

	@Test
	public void testRoot() {
		ComplexNumber num = new ComplexNumber(0,2);
		ComplexNumber num2[] = num.root(2);
		if (!(Math.round(num2[0].getReal()) == 1 && Math.round(num2[0].getImaginary()) == 1
				&& Math.round(num2[1].getReal()) == -1 && Math.round(num2[1].getImaginary()) == -1))
			Assertions.fail();
	}
	
	@Test
	public void testRootException() {
		ComplexNumber num = new ComplexNumber(3,2);
		try {
			num.root(0);
		} catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testToString() {
		ComplexNumber num = new ComplexNumber(1.5,1);
		if (!(num.toString().equals("1.5+i")))
			Assertions.fail();
	}
	
	@Test
	public void testToString2() {
		ComplexNumber num = ComplexNumber.parse("1.01-i");
		if (!(num.toString().equals("1.01-i")))
			Assertions.fail();
	}
	
	@Test
	public void testToString3() {
		ComplexNumber num = ComplexNumber.parse("1.001-i");
		if (!(num.toString().equals("1-i")))
			Assertions.fail();
	}
	
	@Test
	public void testToString4() {
		ComplexNumber num = ComplexNumber.parse("1.0099-i");
		if (!(num.toString().equals("1.01-i")))
			Assertions.fail();
	}
	@Test
	public void testToString5() {
		ComplexNumber num = ComplexNumber.parse("0.00001-0.00001i");
		if (!(num.toString().equals("0")))
			Assertions.fail();
	}
	
	@Test
	public void testToString6() {
		ComplexNumber num = ComplexNumber.parse("0.00001+i");
		if (!(num.toString().equals("i")))
			Assertions.fail();
	}
	
}
