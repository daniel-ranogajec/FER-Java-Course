package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node representing a piece of text data
 * 
 * @author Daniel_Ranogajec
 *
 */
public class TextNode extends Node{
	
	private String text;
	
	/**
	 * Creates new TextNode with text
	 * @param text (String)
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Method that returns text of TextNode
	 * @return text (String)
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	

}
