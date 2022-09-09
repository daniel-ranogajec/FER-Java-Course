package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Functional interface, for a given symbol defines the action that turtle must take
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface Command {

	/**
	 * Execute this command
	 * @param ctx 
	 * @param painter
	 */
	void execute(Context ctx, Painter painter);
	
}
