package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command used for changing TurtleState color
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ColorCommand implements Command{

	private Color color;

	/**
	 * Constructor method that gets a Color
	 * @param color
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}

	/**
	 * Method that changes color of current TurtleState
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		if (ctx == null) 
			throw new NullPointerException("Context is null!");

		ctx.getCurrentState().setColor(color);			
	}

}
