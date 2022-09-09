package hr.fer.zemris.java.gui.layouts;


/**
 * Class that has two read-only int properties row and column
 * 
 * @author Daniel_Ranogajec
 *
 */
public class RCPosition {

	private int row;
	private int column;
	
	/**
	 * Constructor method
	 * @param row int
	 * @param column int
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter function
	 * @return row of this position
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter function
	 * @return column of this position
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Function used for parsing String to RCPosition
	 * @param text String
	 * @return new RCPosition()
	 * @throws IllegalArgumentException if unable to parse the text
	 */
	public static RCPosition parse(String text) {
		String[] spt = text.split(",");
		if (spt.length != 2) {
			throw new IllegalArgumentException("Must give two integers with an comma inbetween.");
		}
		try {
			int row = Integer.parseInt(spt[0]);
			int column = Integer.parseInt(spt[1]);
			return new RCPosition(row, column);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Must give two integers with an comma inbetween.");
		}
	}
}
