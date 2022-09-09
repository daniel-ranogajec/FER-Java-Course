package hr.fer.oprpp1.math;

/**
 * Class used for modeling 2D vectors with two real number components (x and y)
 * @author Daniel_Ranogajec
 *
 */
public class Vector2D {

	private double x;
	private double y;
	
	/**
	 * Constructor for making a new Vector
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter function for returning x value
	 * @return x (double)
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter function for returning y value
	 * @return y (double)
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Operation that adds other vector to this vector (changes value of this)
	 * @param offset vector that you want to add to this vector
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Operation that adds other vector to this vector and returns a new vector with added value
	 * @param offset vector you want to add to this vector
	 * @return new Vector2D with added value of this and other vector
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Operation that rotates a vector for a given angle (changes value of this)
	 * @param angle used for rotating this vector
	 */
	public void rotate(double angle) {
		double oldX = this.x;
		this.x = Math.cos(angle) * oldX - Math.sin(angle) * this.y;
		this.y = Math.sin(angle) * oldX + Math.cos(angle) * this.y;
	}
	
	/**
	 * Operation that rotates a vector for a given angle and returns a new vector with rotated value
	 * @param angle used for rotating this vector
	 * @return new Vector2d with rotated value of this
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle) * this.x - Math.sin(angle) * this.y, 
				Math.sin(angle) * this.x + Math.cos(angle) * this.y);
	}
	
	/**
	 * Operation used for scaling a vector for a given scale (changes value of this)
	 * @param scaler used for scaling this vector
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Operation used for scaling a vector for a given scale which returns a new vector with scaled value
	 * @param scaler used for scaling this vector
	 * @return new Vector2D with scaled value of this
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	/**
	 * Method that returns the copy of this vector
	 * @return new Vector2D which has same values as this vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	
}
