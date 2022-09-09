package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Stack-like class that is used for storing TurtleStates
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Context {
	
	ObjectStack<TurtleState> stack = new ObjectStack<>();

	/**
	 * Used for getting current TurtleState, doesn't remove it from the stack
	 * @return current TurtleState
	 * @throws EmptyStackException if stack is empty
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Used for pushing TurtleState to stack
	 * @throws NullPointerException if TurtleState is <code>null</code>
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Used for getting current TurtleState, removes it from stack
	 * @return current TurtleState
	 * @throws EmptyStackException if stack is empty
	 */
	public void popState() {
		stack.pop();
	}
}
