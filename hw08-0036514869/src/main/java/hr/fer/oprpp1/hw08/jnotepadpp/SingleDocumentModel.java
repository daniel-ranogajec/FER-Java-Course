package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface that represents a model of single document
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Method for getting models text component
	 * @return JTextArea
	 */
	JTextArea getTextComponent();
	
	/**
	 * Method for getting models file path
	 * @return Path
	 */
	Path getFilePath();
	
	/**
	 * Method for setting models file path
	 * @param path 
	 */
	void setFilePath(Path path);
	
	/**
	 * Method that checks if model is modified
	 * @return true if model is modified, false otherwise
	 */
	boolean isModified();
	
	/**
	 * Method for updating boolean modified of model
	 * @param modified
	 */
	void setModified(boolean modified);
	
	/**
	 * Method for adding SingleDocumentListener
	 * @param l SingleDocumentListener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Method for removing SingleDocumentListener
	 * @param l SingleDocumentListener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}