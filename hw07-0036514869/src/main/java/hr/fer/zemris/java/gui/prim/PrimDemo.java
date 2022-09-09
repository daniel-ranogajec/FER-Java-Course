package hr.fer.zemris.java.gui.prim;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo program that extends JFrame.
 * On start opens a window which shows two lists with a button that generates next primary number.
 * 
 * @author Daniel_Ranogajec
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor method called by main class
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500,500);
		initGUI();
	}

	/**
	 * Private method used for initializing the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		JPanel panel = new JPanel(new GridLayout(1, 0));
		panel.add(new JScrollPane(list1));
		panel.add(new JScrollPane(list2));
		
		JButton next = new JButton("sljedeći");
		next.addActionListener(e -> model.next());
	
		cp.add(panel, BorderLayout.CENTER);
		cp.add(next, BorderLayout.PAGE_END);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
