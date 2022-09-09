package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Listner for single document model
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Method called when documents modified status is updated
	 * @param model SingleDocumentModel
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Method called when documents path is updated
	 * @param model SingleDocumentModel
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
	
}