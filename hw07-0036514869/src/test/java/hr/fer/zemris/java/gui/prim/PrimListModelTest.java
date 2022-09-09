package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimListModelTest {

	@Test
	public void testPrimaryNumbers() {
		
		PrimListModel model = new PrimListModel();
		
		if (model.getSize() != 1 && model.getElementAt(0) != 1)
			Assertions.fail();
		model.next();
		if (model.getElementAt(1) != 2)
			Assertions.fail();
		model.next();
		if (model.getElementAt(2) != 3)
			Assertions.fail();
		model.next();
		if (model.getElementAt(3) != 5)
			Assertions.fail();
		model.next();
		if (model.getElementAt(4) != 7)
			Assertions.fail();
	}
}
