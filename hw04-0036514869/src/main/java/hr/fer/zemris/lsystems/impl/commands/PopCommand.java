package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command used for removing current TurtleState from the stack
 * 
 * @author Daniel_Ranogajec
 *
 */
public class PopCommand implements Command{

	/**
	 * Method that removes current TurtleState from the stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		if (ctx == null) 
			throw new NullPointerException("Context is null!");
		
		ctx.popState();
	}

}
