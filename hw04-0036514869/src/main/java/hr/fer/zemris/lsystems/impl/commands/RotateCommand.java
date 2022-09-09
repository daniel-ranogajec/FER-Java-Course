package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command used for rotating the current TurtleState for given angle
 * 
 * @author Daniel_Ranogajec
 *
 */
public class RotateCommand implements Command{
	
	private double angle;
	
	/**
	 * Constructor method that gets and angle used for rotating the current TurtleState
	 * @param angle
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle;
	}

	/**
	 * Method used for rotating current TurtleState
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		if (ctx == null) 
			throw new NullPointerException("Context is null!");
		
		TurtleState state = ctx.getCurrentState();
		state.getDirection().rotate(Math.toRadians(angle));
	}
	
}
