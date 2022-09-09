package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

/**
 * Class used for defining Turtles position, color, direction and effective length
 * 
 * @author Daniel_Ranogajec
 *
 */
public class TurtleState {

	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double distance;
		
	/**
	 * Constructor method for creating a new TurtleState
	 * @param position Vector2D
	 * @param direction Vector2D
	 * @param color Color
	 * @param distance Distance
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double distance) {
		super();
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.distance = distance;
	}
	
	/**
	 * Method that returns the copy of this TurtleState
	 * @return new TurtleState which has same values as this TurtleState
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, distance);
	}

	/**
	 * Used for getting Vector2D position of this TurtleState
	 * @return Vector2D position of this TurtleState
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Used for getting Vector2D direction of this TurtleState
	 * @return Vector2D direction of this TurtleState
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Used for getting Color of this TurtleState
	 * @return Color of this TurtleState
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Used for setting Color of this TurtleState
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Used for getting double effective distance of this TurtleState
	 * @return double effective distance of this TurtleState
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Used for setting distance of this TurtleState
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	
}
