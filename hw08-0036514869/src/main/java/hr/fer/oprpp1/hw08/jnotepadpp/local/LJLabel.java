package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Class that extends JLabel
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LJLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4507822972586266305L;

	private String key;
	private ILocalizationListener listener;
	private ILocalizationProvider prov;
	
	/**
	 * Constructor
	 * @param key
	 * @param lp
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;		
		updateLabel();
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				updateLabel();
			}
		};
		prov.addLocalizationListener(listener);
		
	}
	
	/**
	 * Method called when language is changed
	 */
	private void updateLabel() {
		this.setText(prov.getString(key));
	}
	
}
