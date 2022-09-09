package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class that extends JTabbedPane and implements MultipleDocumentModel
 * 
 * @author Daniel_Ranogajec
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6899650695557055574L;
	
	private SingleDocumentModel currentModel;
	private List<SingleDocumentModel> list = new ArrayList<>();
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	private final ImageIcon RED = getIcon("icons/red_icon.png");
	private final ImageIcon GREEN = getIcon("icons/green_icon.png");

	/**
	 * Constructor that takes no arguments
	 */
	public DefaultMultipleDocumentModel() {
		super();
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel m = currentModel;
				currentModel = list.get(DefaultMultipleDocumentModel.this.getSelectedIndex());
				DefaultMultipleDocumentModel.this.fireCurrentDocumentChanged(m, currentModel);
			}
			
		});
	}

	/**
	 * Method called when current document changes
	 * @param previousModel SingleDocumentModel
	 * @param currentModel SingleDocumentModel
	 * @throws IllegalArgumentException if both previousModel and currentModel is null
	 */
	protected void fireCurrentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if (previousModel == null && currentModel == null)
			throw new IllegalArgumentException("Previous model or current model can be null, but not both!");
		
		listeners.forEach(l -> l.currentDocumentChanged(previousModel, currentModel));		
	}
	
	/**
	 * Method called when document is added to model
	 * @param model SingleDocumentModel
	 */
	protected void fireAddedDocument(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentAdded(model));		
	}
	
	/**
	 * Method called when document is removed from model
	 * @param model SingleDocumentModel
	 */
	protected void fireRemovedDocument(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentRemoved(model));		
	}
	

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return list.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return createNewDocument(null, "");
	}
	
	/**
	 * Method used for creating new document
	 * @param path
	 * @param text
	 * @return SingleDocumentModel
	 */
	private SingleDocumentModel createNewDocument(Path path, String text) {
		return addNewDocument(new DefaultSingleDocumentModel(path, text), path);
	}
	
	/**
	 * Method used for adding new document to model
	 * @param singleModel
	 * @param path
	 * @return SingleDocumentModel
	 */
	private SingleDocumentModel addNewDocument(DefaultSingleDocumentModel singleModel, Path path) {
		SingleDocumentModel m = currentModel;
		this.currentModel = singleModel;
		this.list.add(currentModel);
		fireAddedDocument(currentModel);
		fireCurrentDocumentChanged(m, currentModel);
		
		JPanel panel = new JPanel();
		JTextArea text = currentModel.getTextComponent();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(text));
		
		if (path != null) {
			this.addTab(path.getFileName().toString(), GREEN, panel, path.toString());
		} else {
			this.addTab("(unnamed)", GREEN, panel, "(unnamed)");
		}
		this.setSelectedIndex(list.indexOf(currentModel));
		
		currentModel.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				DefaultMultipleDocumentModel.this.setIconAt(list.indexOf(currentModel), RED);
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {}
		});
		
		return currentModel;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentModel;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null)
			throw new NullPointerException();
		
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(
					DefaultMultipleDocumentModel.this, 
					"File "+path.toAbsolutePath()+" doesn't exist!", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return currentModel;
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFilePath() != null && list.get(i).getFilePath().equals(path)) {
				SingleDocumentModel m = currentModel;
				currentModel = list.get(i);
				this.setSelectedIndex(i);
				fireCurrentDocumentChanged(m, currentModel);
				return currentModel;
			}
		}
		
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(
					DefaultMultipleDocumentModel.this, 
					"Error while reading file "+path.toAbsolutePath()+".", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return currentModel;
		}
		String text = new String(bytes, StandardCharsets.UTF_8);
		
		if (list.size() == 1 && currentModel.getTextComponent().getText().isEmpty() && !currentModel.isModified() && currentModel.getFilePath() == null) {
			createNewDocument(path, text);
			closeDocument(list.get(0));
		} else {
			createNewDocument(path, text);
		}
	
		return currentModel;
	}
	
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, data);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					DefaultMultipleDocumentModel.this, 
					"Error while saving file "+newPath.toFile().getAbsolutePath(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(
				DefaultMultipleDocumentModel.this, 
				"File was saved.", 
				"Info", 
				JOptionPane.INFORMATION_MESSAGE);
		
		model.setFilePath(newPath);
		model.setModified(false);
		this.setIconAt(list.indexOf(currentModel), GREEN);
		this.setTitleAt(list.indexOf(currentModel), currentModel.getFilePath().getFileName().toString());
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (list.size() < 1) {
			return;
		}
		
		if (list.size() == 1) {
			createNewDocument();
		} 
		
		int i = list.indexOf(model);
		if (i == -1)
			return;
		
		if (i > 0)
			i--;
		
		this.removeTabAt(list.indexOf(model));
		list.remove(model);
		currentModel = list.get(i);
		this.setSelectedIndex(i);
		fireCurrentDocumentChanged(model, currentModel);
		fireRemovedDocument(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return list.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return list.get(index);
	}
	
	private ImageIcon getIcon(String name) {
		InputStream is = this.getClass().getResourceAsStream(name);	
		if (is == null) 
			throw new NullPointerException();
		
		byte[] bytes;
		try {
			bytes = is.readAllBytes();
			is.close();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
