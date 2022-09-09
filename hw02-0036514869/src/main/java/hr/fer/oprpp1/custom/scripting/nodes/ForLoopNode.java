package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * A node representing for-loop construct
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ForLoopNode extends Node{
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * Creates new ForLoopNode with parameters that represent variable, start, end and step
	 * @param variable ElementVariable
	 * @param startExpression Element
	 * @param endExpression Element
	 * @param stepExpression Element (can be <code>null</code>)
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Method that returns ElementVariable
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Method that returns Element
	 * @return startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Method that returns Element
	 * @return endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Method that returns Element, can return <code>null</code>
	 * @return stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		String s = "{$ FOR variable=" + variable + ", startExpression=" + startExpression + ", endExpression="
				+ endExpression + ", stepExpression=" + stepExpression + "$}\n\t";
		
		for (int i = 0; i < this.numberOfChildren(); i++) {
			s += this.getChild(i).toString() + "\n";
		}
		s += "{$ END $}";
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((endExpression == null) ? 0 : endExpression.hashCode());
		result = prime * result + ((startExpression == null) ? 0 : startExpression.hashCode());
		result = prime * result + ((stepExpression == null) ? 0 : stepExpression.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		if (endExpression == null) {
			if (other.endExpression != null)
				return false;
		} else if (!endExpression.equals(other.endExpression))
			return false;
		if (startExpression == null) {
			if (other.startExpression != null)
				return false;
		} else if (!startExpression.equals(other.startExpression))
			return false;
		if (stepExpression == null) {
			if (other.stepExpression != null)
				return false;
		} else if (!stepExpression.equals(other.stepExpression))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
	
	
	

	
}
