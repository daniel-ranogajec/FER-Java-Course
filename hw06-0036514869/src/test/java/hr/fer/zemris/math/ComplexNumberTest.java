package hr.fer.zemris.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void testGetReal() {
		Complex num = new Complex(2,1);
		if (num.getReal() != 2)
			Assertions.fail();
	}
	
	@Test
	public void testGetImaginary() {
		Complex num = new Complex(2,1);
		if (num.getImaginary() != 1)
			Assertions.fail();
	}

	@Test
	public void testModule() {
		Complex num = new Complex(1,1);
		if (num.module() != Math.sqrt(2))
			Assertions.fail();
	}
	
	@Test
	public void testAdd() {
		Complex num = new Complex(1,1);
		Complex num2 = num.add(new Complex(1,1));
		if (num2.getReal() != 2 && num2.getImaginary() != 2)
			Assertions.fail();
	}
	
	@Test
	public void testSub() {
		Complex num = new Complex(1,1);
		Complex num2 = num.sub(new Complex(1,1));
		if (num2.getReal() != 0 && num2.getImaginary() != 0)
			Assertions.fail();
	}
	
	@Test
	public void testNegate() {
		Complex num = new Complex(1,1);
		Complex num2 = num.negate();
		if (num2.getReal() != -1 && num2.getImaginary() != -1)
			Assertions.fail();
	}
	
	@Test
	public void testMul() {
		Complex num = new Complex(3,2);
		Complex num2 = num.multiply(new Complex(1,2));
		if (num2.getReal() != -1 && num2.getImaginary() != 8)
			Assertions.fail();
	}
	
	@Test
	public void testDiv() {
		Complex num = new Complex(3,2);
		Complex num2 = num.divide(new Complex(1,2));
		if (num2.getReal() != 1.4 && num2.getImaginary() != -0.8)
			Assertions.fail();
	}
	
	@Test
	public void testPower() {
		Complex num = new Complex(3,2);
		Complex num2 = num.power(3);
		if (num2.getReal() != -9 && num2.getImaginary() != 46)
			Assertions.fail();
	}
	
	@Test
	public void testPowerException() {
		Complex num = new Complex(3,2);
		try {
			num.power(-1);
		} catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}

	@Test
	public void testRoot() {
		Complex num = new Complex(0,2);
		Complex num2[] = num.root(2);
		if (!(Math.round(num2[0].getReal()) == 1 && Math.round(num2[0].getImaginary()) == 1
				&& Math.round(num2[1].getReal()) == -1 && Math.round(num2[1].getImaginary()) == -1))
			Assertions.fail();
	}
	
	@Test
	public void testRootException() {
		Complex num = new Complex(3,2);
		try {
			num.root(0);
		} catch (IllegalArgumentException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void complexParse() {
		Complex num = Complex.parse("1+i");
		if (!(num.getReal()==1 && num.getImaginary()==1))
				Assertions.fail();
	}
	
	@Test
	public void complexParse2() {
		Complex num = Complex.parse("i");
		if (!(num.getReal()==0 && num.getImaginary()==1))
				Assertions.fail();
	}

	@Test
	public void complexParse3() {
		Complex num = Complex.parse("-i");
		if (!(num.getReal()==0 && num.getImaginary()==-1))
				Assertions.fail();
	}
	
	@Test
	public void complexParse4() {
		Complex num = Complex.parse("1");
		if (!(num.getReal()==1 && num.getImaginary()==0))
				Assertions.fail();
	}
	
	@Test
	public void ComplexParse5() {
		Complex[] num = {Complex.parse("0"), Complex.parse("i0"), Complex.parse("0+i0"), Complex.parse("0-i0")};
		for (Complex complex : num) {
			if (!(complex.getReal()==0 && complex.getImaginary()==0))
				Assertions.fail();
		}
	}
}
