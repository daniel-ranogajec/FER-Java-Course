package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node representing an entire document
 * 
 * @author Daniel_Ranogajec
 *
 */
public class DocumentNode extends Node{
	
	/**
	 * Creates new DocumentNode
	 */
	public DocumentNode() {}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < this.numberOfChildren(); i++) {
			s += this.getChild(i).toString();
		}
		return s;
	}
	
	

}
