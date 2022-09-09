package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Implementation of CalcModel
 * 
 * @author Daniel_Ranogajec
 *
 */
public class CalcModelImpl implements CalcModel{
	
	private boolean editable = true;
	private boolean positive = true;
	private String digitsString = "";
	private double digits;
	private String frozenValue = null;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return digits;
	}

	@Override
	public void setValue(double value) {
		digits = value;
		digitsString = Double.toString(Math.abs(value));
		if (value < 0) {
			swapSign();
			digits = -digits;
		}
		editable = false;
		frozenValue = null;
		changedValue();
	}
	
	/**
	 * Method based on setValue that is called after using = button
	 * @param value
	 */
	public void computeValue(double value) {
		setValue(value);
		editable = true;
		digitsString = "";
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		digitsString = "";
		digits = 0;
		editable = true;
		positive = true;
		frozenValue = null;
		changedValue();
	}

	@Override
	public void clearAll() {	
		 activeOperand = null;
		 pendingOperation = null;
		 clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Number is not editable");
		
		if (digitsString.isEmpty() && digits != 0) {
			if (positive) 
				digitsString = Double.toString(digits);
			 else 
				digitsString = Double.toString(-digits);
		}	
		
		digits = -digits;
			
		if (positive) 
			positive = false;
		 else 
			positive = true;
		
		frozenValue = null;
		changedValue();

	}	

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Number is not editable");
		if (digitsString.contains("."))
			throw new CalculatorInputException("Two points in same number.");
		if (digitsString.isEmpty())
			throw new CalculatorInputException("Adding point when there is no number.");
		
		digitsString += ".";
		frozenValue = null;
		changedValue();

	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) 
			throw new CalculatorInputException("Model is not editable.");
		if (digitsString.length() >= 308) 
			throw new CalculatorInputException("Number is too big.");

		if (digitsString.equals("0")) {
			if (digit == 0)
				return;
			digitsString = "";
		}
		
		digitsString += digit;
		try {
			if (positive)
				digits = Double.parseDouble(digitsString);
			else 
				digits = -Double.parseDouble(digitsString);

		} catch (NumberFormatException ex) {
			digitsString = digitsString.substring(0, digitsString.length()-1);
			throw new CalculatorInputException("Not a digit.");
		}
		
		frozenValue = null;
		changedValue();
	}

	@Override
	public boolean isActiveOperandSet() {
		return !(activeOperand == null);
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet())
			throw new IllegalStateException("Operand is not set-up.");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;		
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	@Override
	public String toString() {
		if (hasFrozenValue()) { 
			if (positive)
				return frozenValue;
			else
				return "-" + frozenValue;
		}
		
		if (digitsString.isEmpty())
			digitsString = "0";
		
		if (positive)
			return digitsString;
		else
			return "-" + digitsString;

		
	}
	
	/**
	 * Mettod used for setting frozen value
	 * @param value
	 */
	public void freezeValue(String value) {
		this.frozenValue = value;
		changedValue();
	}
	
	/**
	 * Method used for checking if there is frozen value
	 * @return
	 */
	public boolean hasFrozenValue() {
		return this.frozenValue != null ? true : false;
	}
	
	/**
	 * Private method used for telling listeners that value changed
	 */
	private void changedValue() {
		for (CalcValueListener l : listeners) 
			l.valueChanged(this);
	}

}
