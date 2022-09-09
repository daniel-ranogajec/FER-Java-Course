package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface that represents a model capable of holding zero, one or more documents
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Method used for creating new document
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Method used for getting current document in model
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Method used for loading new document into model
	 * @param path of document
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Method used for saving given SingleDocumentModel to given path
	 * @param model SingleDOcumentModel
	 * @param newPath Path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Method used for closing given SingleDocumentModel
	 * @param model SingleDocumentModel
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Method for adding MultipleDocumentListener
	 * @param l MultipleDocumentListener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Method for removing MultipleDocumentListener
	 * @param l MultipleDocumentListener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Method that returns number of documents in model
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * Method that returns document on given index
	 * @param index of ducument
	 * @return SingleDocumentModel document
	 */
	SingleDocumentModel getDocument(int index);

}