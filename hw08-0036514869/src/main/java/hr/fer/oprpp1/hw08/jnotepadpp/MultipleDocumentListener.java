package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Listener for MultipleDocumentModel
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Method called when current document in changed
	 * @param previousModel SingleDocumentModel can be <code>null</code>
	 * @param currentModel SingleDocumentModel can be <code>null</code>
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Method called when document is added to model
	 * @param model SingleDocumentModel added document
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Method called when document is removed from model
	 * @param model SingleDocumentModel removed document
	 */
	void documentRemoved(SingleDocumentModel model);
}