package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command used for drawing lines on Painter
 * 
 * @author Daniel_Ranogajec
 *
 */
public class DrawCommand implements Command{
	
	private double step;
	
	/**
	 * Constructor method that gets a step
	 * @param step double
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Method that draws line on Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		if (ctx == null) 
			throw new NullPointerException("Context is null!");
		if (painter == null)
			throw new NullPointerException("Painter is null!");
		
		TurtleState state = ctx.getCurrentState();
		Vector2D v0 = state.getPosition().copy();
		state.getPosition().add(state.getDirection().scaled((state.getDistance() * step)));
		painter.drawLine(v0.getX(), v0.getY(), state.getPosition().getX(), state.getPosition().getY(), state.getColor(), 1f);
	}
	
}
