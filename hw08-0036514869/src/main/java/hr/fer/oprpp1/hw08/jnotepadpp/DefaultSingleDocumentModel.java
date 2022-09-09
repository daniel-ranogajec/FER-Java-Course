package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class that implements SingleDocumentModel
 * 
 * @author Daniel_Ranogajec
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path filePath;
	private JTextArea area;
	private boolean modified = false;
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor
	 * @param filePath of document
	 * @param text of document
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		super();
		this.filePath = filePath;		
		area = new JTextArea();
		area.setText(text);
		area.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
				fire();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
				fire();				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modified = true;
				fire();				
			}
			
		});
	}
	
	/**
	 * Method used for signaling listeners that document modify status is updated
	 */
	private void fire() {
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}

	@Override
	public JTextArea getTextComponent() {
		return area;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null)
			throw new IllegalArgumentException("Path can not be null!");
		
		this.filePath = path;
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;		
		fire();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
