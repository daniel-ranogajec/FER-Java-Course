package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command similar to DrawCommand, only doesn't draw a line
 * 
 * @author Daniel_Ranogajec
 *
 */
public class SkipCommand implements Command{
	
	private double step;
	
	/**
	 * Constructor method that gets a step
	 * @param step double
	 */
	public SkipCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Method that skips a distance
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		if (ctx == null) 
			throw new NullPointerException();
		
		TurtleState state = ctx.getCurrentState();
		Vector2D v0 = state.getPosition();
		Vector2D v1 = state.getDirection().scaled(state.getDistance() * step);
		v0.add(v1);

	}
	
}
