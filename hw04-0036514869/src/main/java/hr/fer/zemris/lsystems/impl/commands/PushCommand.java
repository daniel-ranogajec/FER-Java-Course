package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command used for putting a copy of current TurtleState to the stack
 * 
 * @author Daniel_Ranogajec
 *
 */
public class PushCommand implements Command{

	/**
	 * Method that puts a copy of current TurtleState to the stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		if (ctx == null) 
			throw new NullPointerException("Context is null!");
		
		TurtleState state = ctx.getCurrentState();
		ctx.pushState(state.copy());
	}

}
