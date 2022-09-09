package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command used for updating the current TurtleState by scaling its effective distance by given factor
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ScaleCommand implements Command{

	private double factor;

	/**
	 * Constructor method that gets a double factor used for scaling current TutrleStates effective distance
	 * @param factor double
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}

	/**
	 * Method that updates current TurtleStates effective distance
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		if (ctx == null) 
			throw new NullPointerException("Context is null!");

		ctx.getCurrentState().setDistance(ctx.getCurrentState().getDistance() * factor);			
	}

}
