package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Program that extends JFrame
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Calculator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Stack<Double> stack = new Stack<>();
	private boolean inv = false;
	private Container cp;
	private CalcModelImpl calcModel;
	private List<JButton> invButtons;

	/**
	 * Constructor method called by main class
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
		setTitle("Java Calculator v1.0");
	}

	/**
	 * Private method used for initializing the GUI
	 */
	private void initGUI() {
		cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		calcModel = new CalcModelImpl();
		stack = new Stack<>();

		JLabel display = new JLabel();
		display.setBackground(Color.YELLOW);
		display.setOpaque(true);
		display.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		display.setFont(display.getFont().deriveFont(30f));
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		cp.add(display, new RCPosition(1,1));

		calcModel.addCalcValueListener(e -> display.setText(calcModel.toString()));	
		addAllButtons();		
	}

	/**
	 * Private method used for adding all buttons to the layout
	 */
	private void addAllButtons() {
		RCPosition[] digitPositions = new RCPosition[] { new RCPosition(5,3), new RCPosition(4,3), new RCPosition(4,4),
				new RCPosition(4,5), new RCPosition(3,3), new RCPosition(3,4), new RCPosition(3,5),
				new RCPosition(2,3), new RCPosition(2,4), new RCPosition(2,5) };
		for (int i = 0; i <= 9; i++) {
			addButton(new JButton(Integer.toString(i)),
					e -> {
						JButton source = (JButton)e.getSource();
						calcModel.insertDigit(Integer.parseInt(source.getText()));
					},
					digitPositions[i]);
		}

		addButton(new JButton("+/-"), e -> calcModel.swapSign(), new RCPosition(5,4));
		addButton(new JButton("."), e -> calcModel.insertDecimalPoint(), new RCPosition(5,5));

		String[] oper = new String[]{"+", "-", "*", "/"};
		for (int i = 0; i < 4; i++) {
			addButton(new JButton(oper[i]),
					e -> {
						if (calcModel.hasFrozenValue())
							throw new CalculatorInputException();
						else {
						JButton source = (JButton)e.getSource();
						double value = calcModel.getValue();
						if (calcModel.getPendingBinaryOperation() != null) 
							value = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), value);
						calcModel.setActiveOperand(value);
						calcModel.setPendingBinaryOperation(switch(source.getText()) {
						case "+" ->  (x, y) -> x + y;
						case "-" ->  (x, y) -> x - y;
						case "*" ->  (x, y) -> x * y;
						case "/" ->  (x, y) -> x / y;
						default -> throw new IllegalArgumentException("Unexpected value: " + source.getText());
						});
						calcModel.setValue(value);
						calcModel.clear();
						calcModel.freezeValue(Double.toString(value)); }
						},
					new RCPosition(5-i,6));

		}
		addButton(new JButton("="), 
				e -> {
					double value = calcModel.getValue();
					if (calcModel.getPendingBinaryOperation() != null) {
						value = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), value);
						calcModel.clear();
						calcModel.setPendingBinaryOperation(null);
					}
					calcModel.computeValue(value);
					},
				new RCPosition(1,6));
		addButton(new JButton("clr"), e -> calcModel.clear(), new RCPosition(1,7));
		addButton(new JButton("reset"), e -> calcModel.clearAll(), new RCPosition(2,7));
		addButton(new JButton("push"), e -> stack.push(calcModel.getValue()), new RCPosition(3,7));
		addButton(new JButton("pop"),
				e -> {
					double value = stack.pop();
					calcModel.setValue(value);}, 
				new RCPosition(4,7));
		addButton(new JButton("1/x"), e -> calcModel.setValue(1.0 / calcModel.getValue()), new RCPosition(2,1));

		invButtons = new ArrayList<>(); 
		String[] nonInvNames = new String[] {"sin", "cos", "tan", "ctg", "log", "ln", "x^n"};
		String[] invNames = new String[] {"arcsin", "arccos", "arctan", "arcctg", "10^x", "e^x", "x^(1/n)"};;

		JCheckBox inv = new JCheckBox("inv");
		inv.addActionListener(e -> {
			swapInvButtons(invButtons, this.inv==true ? nonInvNames : invNames);
			this.inv = (this.inv == true ? false : true);
		});
		cp.add(inv, new RCPosition(5,7));	

		addInvButton(new JButton("sin"), 
				e -> {
					if (calcModel.hasFrozenValue())
						throw new CalculatorInputException();
					else {
					if (!this.inv) 
						calcModel.setValue(Math.sin(Math.toRadians(calcModel.getValue())));
					else 
						calcModel.setValue(Math.toDegrees(Math.asin(calcModel.getValue())));
					}
				}, 
				new RCPosition(2,2));
		addInvButton(new JButton("cos"),
				e -> {
					if (calcModel.hasFrozenValue())
						throw new CalculatorInputException();
					else {
					if (!this.inv) 
						calcModel.setValue(Math.cos(Math.toRadians(calcModel.getValue())));
					else 
						calcModel.setValue(Math.toDegrees(Math.acos(calcModel.getValue())));
					}
				},
				new RCPosition(3,2));
		addInvButton(new JButton("tan"),
				e -> {
					if (calcModel.hasFrozenValue())
						throw new CalculatorInputException();
					else {
					if (!this.inv) 
						calcModel.setValue(Math.tan(Math.toRadians(calcModel.getValue())));
					else 
						calcModel.setValue(Math.toDegrees(Math.atan(calcModel.getValue())));
					}
				},
				new RCPosition(4,2));
		addInvButton(new JButton("ctg"),
				e -> {
					if (calcModel.hasFrozenValue())
						throw new CalculatorInputException();
					else {
					if (!this.inv) 
						calcModel.setValue(1.0/(Math.tan(Math.toRadians(calcModel.getValue()))));
					else 
						calcModel.setValue(90-(Math.toDegrees(Math.atan(calcModel.getValue()))));
					}
				},
				new RCPosition(5,2));
		addInvButton(new JButton("log"),
				e -> {
					if (calcModel.hasFrozenValue())
						throw new CalculatorInputException();
					else {
					if (!this.inv) 
						calcModel.setValue(Math.log10(calcModel.getValue()));
					else 
						calcModel.setValue(Math.pow(10, calcModel.getValue()));
					}
				},
				new RCPosition(3,1));
		addInvButton(new JButton("ln"),	
				e -> {
					if (calcModel.hasFrozenValue())
						throw new CalculatorInputException();
					else {
					if (!this.inv) 
						calcModel.setValue(Math.log(calcModel.getValue()));
					else 
						calcModel.setValue(Math.pow(Math.E, calcModel.getValue()));
					}
				},
				new RCPosition(4,1));

		addInvButton(new JButton("x^n"),	
				e -> {
					if (calcModel.hasFrozenValue())
						throw new CalculatorInputException();
					else {
					double value = calcModel.getValue();
					if (calcModel.getPendingBinaryOperation() != null) 
						value = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), value);
					calcModel.setActiveOperand(value);

					if (!this.inv) 
						calcModel.setPendingBinaryOperation((x,n) -> Math.pow(x, n));
					else 
						calcModel.setPendingBinaryOperation((x,n) -> Math.pow(x, 1.0/n));
					calcModel.clear();
					calcModel.freezeValue(Double.toString(value));
					}
				},
				new RCPosition(5,1));
	}

	/**
	 * Private method used for adding specific button with its listener to the layout
	 * @param button
	 * @param e
	 * @param position
	 */
	private void addButton(JButton button, ActionListener e, RCPosition position) {
		button.setBackground(Color.CYAN);
		button.addActionListener(e);
		cp.add(button, position);
	}

	/**
	 * Private method used for adding specific inverse button with its listener to the layout
	 * @param button
	 * @param e
	 * @param position
	 */
	private void addInvButton(JButton button, ActionListener e, RCPosition position) {
		addButton(button, e, position);
		invButtons.add(button);
	}

	/**
	 * Private method used for swapping names on inverse buttons
	 * @param invButtons
	 * @param names
	 */
	private void swapInvButtons(List<JButton> invButtons, String[] names) {
		int i = 0;
		for (JButton button : invButtons) 
			button.setText(names[i++]);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
}
