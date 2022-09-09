package hr.fer.zemris.java.gui.layouts;
import javax.swing.JLabel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcLayoutExceptionTest {

	private CalcLayout layout;
	
	private static CalcLayout newCalcLayout() {
		return new CalcLayout();
	}

	@BeforeEach
	public void setup() {
		layout = newCalcLayout();
	}

	@Test
	public void testRow1() {
		try {
			layout.addLayoutComponent(new JLabel(), new RCPosition(0,1));
		} catch (CalcLayoutException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testRow2() {
		try {
			layout.addLayoutComponent(new JLabel(), new RCPosition(6,1));
		} catch (CalcLayoutException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testColumn1() {
		try {
			layout.addLayoutComponent(new JLabel(), new RCPosition(1,0));
		} catch (CalcLayoutException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testColumn2() {
		try {
			layout.addLayoutComponent(new JLabel(), new RCPosition(1,8));
		} catch (CalcLayoutException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testWhenRowIs1() {
		try {
			layout.addLayoutComponent(new JLabel(), new RCPosition(1,2));
		} catch (CalcLayoutException ex) {
			return;
		}
		Assertions.fail();
	}
	
	@Test
	public void testMultiple() {
		layout.addLayoutComponent(new JLabel(), new RCPosition(1,1));
		try {
			layout.addLayoutComponent(new JLabel(), new RCPosition(1,1));
		} catch (CalcLayoutException ex) {
			return;
		}
		Assertions.fail();
	}
}