package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAdapter;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * Text Editor program
 * 
 * @author Daniel_Ranogajec
 *
 */
public class JNotepadPP extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel model;
	private final FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	private String copy = "";
	private JTextArea statusbarLen = new JTextArea();
	private JTextArea statusbarCaret = new JTextArea();
	private Clock statusbarClock;

	/**
	 * Constructor
	 */
	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(800, 800);

		initGUI();
	}

	/**
	 * Method called by constructor
	 */
	private void initGUI() {

		model = new DefaultMultipleDocumentModel();

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(model, BorderLayout.CENTER);

		model.addMultipleDocumentListener(new MultipleDocumentAdapter() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				String s = "(unnamed)";
				if (model.getCurrentDocument().getFilePath() != null)
					s = model.getCurrentDocument().getFilePath().toString();
				s += " - JNotepad++";
				JNotepadPP.this.setTitle(s);

				SingleDocumentListener l = new SingleDocumentListener() {

					@Override
					public void documentModifyStatusUpdated(SingleDocumentModel model) {
						JNotepadPP.this.setstatusbarLen();
					}

					@Override
					public void documentFilePathUpdated(SingleDocumentModel model) {

					}

				};
				if (previousModel != null)
					previousModel.removeSingleDocumentListener(l);
				currentModel.addSingleDocumentListener(l);


				ChangeListener cl = new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						Caret c = (Caret)e.getSource();
						JNotepadPP.this.setstatusbarCaret(Math.abs(c.getDot()-c.getMark()));
					}
				};

				if (previousModel != null)
					previousModel.getTextComponent().getCaret().removeChangeListener(cl);
				currentModel.getTextComponent().getCaret().addChangeListener(cl);

				setstatusbarLen();
				setstatusbarCaret(0);
				
				copyAction.setEnabled(false);		
				cutAction.setEnabled(false);
				toUppercaseAction.setEnabled(false);
				toLowercaseAction.setEnabled(false);
				invertCaseAction.setEnabled(false);
				sortAscendingAction.setEnabled(false);
				sortDescendingAction.setEnabled(false);
				uniqueAction.setEnabled(false);
				
				model.getCurrentDocument().getTextComponent().getCaret().addChangeListener( new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						Caret c = (Caret)e.getSource();
						int l = Math.abs(c.getDot()-c.getMark());
						copyAction.setEnabled(l!=0);		
						cutAction.setEnabled(l!=0);
						toUppercaseAction.setEnabled(l!=0);
						toLowercaseAction.setEnabled(l!=0);
						invertCaseAction.setEnabled(l!=0);
						sortAscendingAction.setEnabled(l!=0);
						sortDescendingAction.setEnabled(l!=0);
						uniqueAction.setEnabled(l!=0);
						
					}
				});
			}

		});
		model.createNewDocument();

		createActions();
		createMenus();
		createToolbars();
		createStatusbar();

		WindowListener wl = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				flp.disconnect();
				flp.connect();

				int changed = 0;
				for (SingleDocumentModel m : model) {
					if (m.isModified()) 
						changed++;
				}
				if (changed == 0) {
					dispose();
				} else if (changed == 1) {
					model.forEach(m -> {
						if (m.isModified()) {
							String[] options = new String[] {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
							String s = "";
							if (m.getFilePath() != null)
								s = m.getFilePath().toString();
							int rezultat = JOptionPane.showOptionDialog(JNotepadPP.this, 
									flp.getString("file") + s + flp.getString("warningNotSaved"), flp.getString("warning"),
									JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,  null, options, options[0]);
							switch(rezultat) {
							case JOptionPane.CLOSED_OPTION:
								return;
							case 0:
								save(m, true);
							case 1:
								dispose();
							case 2:
								return;
							}
						}
					});
				} else {
					for (SingleDocumentModel m : model) {
						if (m.isModified()) {
							String[] options = new String[] {flp.getString("yes"), flp.getString("no"), flp.getString("saveAll"), flp.getString("cancel")};
							String s = "";
							if (m.getFilePath() != null)
								s = m.getFilePath().toString();
							int rezultat = JOptionPane.showOptionDialog(JNotepadPP.this, 
									flp.getString("file") + s + flp.getString("warningNotSaved"), flp.getString("warning"),
									JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,  null, options, options[0]);
							switch(rezultat) {
							case JOptionPane.CLOSED_OPTION:
								return;
							case 0:
								save(m);
								continue;
							case 1:
								dispose();
								return;
							case 2:
								saveAll();
								for (SingleDocumentModel m2 : model) {
									if (m2.isModified())
										return;
								}
								dispose();
							case 3:
								return;
							}
						}
					}
					dispose();
				}		


			}

			@Override
			public void windowClosed(WindowEvent e) {
				statusbarClock.kill();
			}

		};
		this.addWindowListener(wl);

	}

	/**
	 * Private method used for creating statusbar
	 */
	private void createStatusbar() {
		JPanel panel = new JPanel(new GridLayout(1,3));
		statusbarLen.setEditable(false);
		statusbarLen.setBackground(Color.CYAN);
		panel.add(statusbarLen);

		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setstatusbarLen();				
			}
		});

		statusbarCaret.setEditable(false);
		statusbarCaret.setBackground(Color.CYAN);
		panel.add(statusbarCaret);

		statusbarClock = new Clock();
		statusbarCaret.setEditable(false);
		statusbarClock.setBackground(Color.CYAN);
		panel.add(statusbarClock);

		this.add(panel, BorderLayout.SOUTH);		
	}

	/**
	 * Private method used for checking length which is shown in statusbar
	 */
	private void setstatusbarLen() {
		statusbarLen.setText(flp.getString("length") + ": " + model.getCurrentDocument().getTextComponent().getText().length());
	}

	/**
	 * Private method used for checking which ln and col is the caret currently on
	 * @param size
	 */
	protected void setstatusbarCaret(int size) {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		int pos = area.getCaretPosition();
		Document doc = area.getDocument();
		int ln = doc.getDefaultRootElement().getElementIndex(pos);
		int col = pos - doc.getDefaultRootElement().getElement(ln).getStartOffset();
		statusbarCaret.setText(String.format("Ln: %d	 Col: %d	Sel: %d", ln + 1, col + 1, size));
	}

	/**
	 * Method called for saving given document model
	 * @param m SingleDocumentModel
	 */
	private void save(SingleDocumentModel m) {
		save(m, false);
	}
	
	/**
	 * Method called for saving document models upon exiting
	 * @param m
	 * @param exit true if called upon exiting file
	 */
	private void save(SingleDocumentModel m, boolean exit) {
		Path path = m.getFilePath();
		if(path == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(flp.getString("save"));
			if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("nothingSaved"), 
						flp.getString("warning"), 
						JOptionPane.WARNING_MESSAGE);	
				if (exit) {
					String[] options = new String[] {flp.getString("yes"), flp.getString("no")};
					String s = "";
					if (m.getFilePath() != null)
						s = m.getFilePath().toString();
					int rezultat = JOptionPane.showOptionDialog(JNotepadPP.this, 
							flp.getString("file") + s + flp.getString("warningNotSaved"), flp.getString("warning"),
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,  null, options, options[0]);
					switch(rezultat) {
					case JOptionPane.CLOSED_OPTION:
						return;
					case 0:
						save(m, true);
					}
				}
				return;
			}
			path = jfc.getSelectedFile().toPath();
		}

		model.saveDocument(m, path);
		JNotepadPP.this.setTitle(model.getCurrentDocument().getFilePath().toString() + " - JNotepad++");
	}

	/**
	 * Method called for saving all models
	 */
	private void saveAll() {
		for (SingleDocumentModel m : model) {
			if (m.isModified()) {
				save(m);
			}		
		}
	}

	/**
	 * Action for creating new document
	 */
	private Action createNewDocumentAction = new LocalizableAction("newDocument", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}		

	};

	/**
	 * Action for opening new document
	 */
	private Action openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("open"));
			if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			model.loadDocument(fc.getSelectedFile().toPath());
		}
	};

	/**
	 * Action for saving document
	 */
	private Action saveDocumentAction =  new LocalizableAction("save", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			save(model.getCurrentDocument());
		}

	};

	/**
	 * Action for saving document on chosen location
	 */
	private Action saveAsDocumentAction = new LocalizableAction("saveAs", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Path p = model.getCurrentDocument().getFilePath();
			if(p != null) {
				String[] options = new String[] {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
				int rezultat = JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("fileExists"), flp.getString("warning"),
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,  null, options, options[0]);
				switch(rezultat) {
				case JOptionPane.CLOSED_OPTION:
					return;
				case 0:
					break;
				case 1:
				case 2:
					return;
				}
			}

			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(flp.getString("save"));
			if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("nothingSaved"), 
						flp.getString("warning"), 
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			p = jfc.getSelectedFile().toPath();
			model.saveDocument(model.getCurrentDocument(), p);
			JNotepadPP.this.setTitle(model.getCurrentDocument().getFilePath().toString() + " - JNotepad++");

		}
	};

	/**
	 * Action for closing current tab
	 */
	private Action closeTabAction = new LocalizableAction("closeTab", flp){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getCurrentDocument().isModified()) {
				String[] options = new String[] {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
				int rezultat = JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("saveTab"), flp.getString("warning"),
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,  null, options, options[0]);
				switch(rezultat) {
				case JOptionPane.CLOSED_OPTION:
					return;
				case 0:
					save(model.getCurrentDocument());
				case 1:
					break;
				case 2:
					return;
				}
			}

			model.closeDocument(model.getCurrentDocument());

		}

	};

	/**
	 * Action for exiting aplication
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	/**
	 * Action for copying selected text
	 */
	private Action copyAction = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document d = model.getCurrentDocument().getTextComponent().getDocument();
			Caret c = model.getCurrentDocument().getTextComponent().getCaret();
			int len = Math.abs(c.getDot()-c.getMark());
			if(len==0) return;
			int offset = Math.min(c.getDot(), c.getMark());

			try {
				JNotepadPP.this.copy = d.getText(offset, len);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}

			pasteAction.setEnabled(true);
		}
	};

	/**
	 * Action for cutting selected text
	 */
	private Action cutAction = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document d = model.getCurrentDocument().getTextComponent().getDocument();
			Caret c = model.getCurrentDocument().getTextComponent().getCaret();
			int len = Math.abs(c.getDot()-c.getMark());
			if(len==0) return;
			int offset = Math.min(c.getDot(), c.getMark());

			try {
				JNotepadPP.this.copy = d.getText(offset, len);
				d.remove(offset, len);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
			
			pasteAction.setEnabled(true);
		}
	};

	/**
	 * Action for pasting copied text
	 */
	private Action pasteAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document d = model.getCurrentDocument().getTextComponent().getDocument();
			Caret c = model.getCurrentDocument().getTextComponent().getCaret();
			int offset = Math.min(c.getDot(), c.getMark());

			try {
				d.insertString(offset, JNotepadPP.this.copy, null);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Action for getting statistics of current document
	 */
	private Action statisticsAction = new LocalizableAction("statistics", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document d = model.getCurrentDocument().getTextComponent().getDocument();
			int len = d.getLength();
			char[] text;
			try {
				text = d.getText(0, len).toCharArray();
			} catch (BadLocationException ex) {
				return;
			}
			int chars = 0;
			int lines = 1;
			for (int i = 0; i < text.length; i++) {
				if (text[i] == '\n') {
					lines++;
				} else if (text[i] != ' ' && text[i] != '\t')
					chars++;
			}

			String stats = flp.getString("stats");
			String[] s = stats.split("XYZ");
			if (s.length == 4)
				JOptionPane.showMessageDialog(null, s[0] + len + s[1] + chars + s[2] + lines + s[3]);
		}
	};

	/**
	 * Action for changing selected text to uppercase
	 */
	private Action toUppercaseAction = new LocalizableAction("toUppercase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(true);
		}
		
	};

	/**
	 * Action for changing selected text to lowercase
	 */
	private Action toLowercaseAction = new LocalizableAction("toLowercase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(false);
		}
	};

	/**
	 * Action for changing selected text to inverted case
	 */
	private Action invertCaseAction = new LocalizableAction("invertCase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document d = model.getCurrentDocument().getTextComponent().getDocument();
			Caret c = model.getCurrentDocument().getTextComponent().getCaret();
			int len = Math.abs(c.getDot()-c.getMark());
			if(len==0) return;
			int offset = Math.min(c.getDot(), c.getMark());

			try {
				char[] s = d.getText(offset, len).toCharArray();
				for (int i = 0; i < s.length; i++) {
					if (Character.isUpperCase(s[i])) 
						s[i] = Character.toLowerCase(s[i]);
					else 
						s[i] = Character.toUpperCase(s[i]);

				}
				d.remove(offset,len);
				d.insertString(offset, new String(s), null);

			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Action for changing sorting selected rows in ascending order
	 */
	private Action sortAscendingAction = new LocalizableAction("sortAscending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(true);
		}

	};

	/**
	 * Action for changing sorting selected rows in descending order
	 */
	private Action sortDescendingAction = new LocalizableAction("sortDescending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(false);
		}

	};

	/**
	 * Action for changing deleting rows that are not unique
	 */
	private Action uniqueAction = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] s = getLines();
			Set<String> set = new HashSet<>(Arrays.asList(s));
			removeAndReplace(String.join("\n", set));
		}

	};

	/**
	 * Private method that returns array of selected text
	 */
	private String[] getLines() {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		int x = area.getCaret().getDot();
		int y = area.getCaret().getMark();
		return area.getText().substring(x, y).split("\n");
	}

	/**
	 * Private method used for sorting
	 * @param ascending true if ascending order, false if descending
	 */
	private void sort(boolean ascending) {
		String[] s = getLines();
		if (s.length == 1)
			return;

		Locale locale = new Locale(flp.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);

		if (ascending) {
			for (int i = 0; i < s.length - 1; i++) {
				for (int j = 0; j < s.length - i - 1; j++) {
					if (collator.compare(s[j], s[j+1]) > 0) {
						String temp = s[j];
						s[j] = s[j+1];
						s[j+1] = temp;
					}
				}
			}
		} else {
			for (int i = 0; i < s.length - 1; i++) {
				for (int j = 0; j < s.length - i - 1; j++) {
					if (collator.compare(s[j], s[j+1]) < 0) {
						String temp = s[j];
						s[j] = s[j+1];
						s[j+1] = temp;
					}
				}
			}
		}

		removeAndReplace(String.join("\n", s));
	}

	/**
	 * Method used for removing selected text and inserting given String
	 * @param s String you want to insert
	 */
	private void removeAndReplace(String s) {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		int x = area.getCaret().getDot();
		int y = area.getCaret().getMark();
		try {
			area.getDocument().remove(Math.min(x, y), Math.abs(x-y));
			area.getDocument().insertString(Math.min(x, y), s, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}		
	}

	/**
	 * Method sed for changing case
	 * @param upper true for uppercase, false for lowercase
	 */
	private void changeCase(boolean upper) {
		Document d = model.getCurrentDocument().getTextComponent().getDocument();
		Caret c = model.getCurrentDocument().getTextComponent().getCaret();
		int len = Math.abs(c.getDot()-c.getMark());
		if(len==0) return;
		int offset = Math.min(c.getDot(), c.getMark());

		try {
			String s = d.getText(offset, len);
			d.remove(offset,len);
			if (upper)
				d.insertString(offset, s.toUpperCase(), null);
			else
				d.insertString(offset, s.toLowerCase(), null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Private method used for creating actions
	 */
	private void createActions() {
		createNewDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O); 

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S); 

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C")); 
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C); 

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X")); 
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X); 

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V); 
	}

	/**
	 * Private method used for creating menus
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(new LocalizableAdapter("file", flp));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createNewDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(statisticsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new JMenu(new LocalizableAdapter("edit", flp));
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		pasteAction.setEnabled(false);

		JMenu languageMenu = new JMenu(new LocalizableAdapter("languages", flp));
		JMenuItem en = new JMenuItem("English");
		en.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("en"));
		JMenuItem hr = new JMenuItem("Croatian");
		hr.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("hr"));
		JMenuItem de = new JMenuItem("German");
		de.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("de"));
		languageMenu.add(en);
		languageMenu.add(hr);
		languageMenu.add(de);
		menuBar.add(languageMenu);

		JMenu tools = new JMenu(new LocalizableAdapter("tools", flp));
		JMenu changeCase = new JMenu(new LocalizableAdapter("changeCase", flp));
		changeCase.add(new JMenuItem(toUppercaseAction));
		changeCase.add(new JMenuItem(toLowercaseAction));
		changeCase.add(new JMenuItem(invertCaseAction));
		JMenu sort = new JMenu(new LocalizableAdapter("sort", flp));
		sort.add(new JMenuItem(sortAscendingAction));
		sort.add(new JMenuItem(sortDescendingAction));
		tools.add(changeCase);
		tools.add(sort);
		tools.add(new JMenuItem(uniqueAction));
		menuBar.add(tools);


		this.setJMenuBar(menuBar);
	}

	/**
	 * Private method used for creating toolbar
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		toolBar.add(new JButton(createNewDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(closeTabAction));
		toolBar.add(new JButton(statisticsAction));
		toolBar.addSeparator();

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Static class giving real time
	 */
	private static class Clock extends JTextArea{

		private static final long serialVersionUID = 1L;

		private volatile String time;
		private Thread t;
		private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		private boolean killThread = false;

		/**
		 * Constructor
		 */
		public Clock() {
			updateTime();

			t = new Thread(()->{
				while(true) {
					try {
						Thread.sleep(1000);
						if (killThread)
							break;
					} catch(Exception ex) {}
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}

		/**
		 * Method called on exiting from application
		 */
		private void kill() {
			killThread = true;
		}

		/**
		 * Method called every second for updating label
		 */
		private void updateTime() {
			time = formatter.format(LocalDateTime.now());
			this.setText(time);
		}

	}

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->  new JNotepadPP().setVisible(true));
	}
}
