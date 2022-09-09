package hr.fer.zemris.java.gui.charts;

/**
 * Class that has two read-only int properties x and y
 * 
 * @author Daniel_Ranogajec
 *
 */
public class XYValue {

	private int x;
	private int y;
	
	/**
	 * Constructor method
	 * @param x int
	 * @param y int
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
