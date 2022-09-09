package hr.fer.oprpp1.zemris.lsystems.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class GenerateTest {
	
	@Test
	public void testLevel0() {
		LSystemBuilderImpl lSystem = new LSystemBuilderImpl();
		String s = lSystem.setAxiom("F").registerProduction('F', "F+F--F+F").build().generate(0);
		if (!(s.equals("F")))
			Assertions.fail();
	}
	
	@Test
	public void testLevel1() {
		LSystemBuilderImpl lSystem = new LSystemBuilderImpl();
		String s = lSystem.setAxiom("F").registerProduction('F', "F+F--F+F").build().generate(1);
		if (!(s.equals("F+F--F+F")))
			Assertions.fail();
	}
	
	@Test
	public void testLevel2() {
		LSystemBuilderImpl lSystem = new LSystemBuilderImpl();
		String s = lSystem.setAxiom("F").registerProduction('F', "F+F--F+F").build().generate(2);
		if (!(s.equals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F")))
			Assertions.fail();	}
	
}
