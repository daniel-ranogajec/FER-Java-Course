package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.MissingResourceException;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Class that extends AbstractAction
 * 
 * @author Daniel_Ranogajec
 *
 */
public abstract class LocalizableAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1496028231737424015L;
	
	private ILocalizationListener listener;
	private ILocalizationProvider prov;
	private String key;
	
	/**
	 * Constructor
	 * @param key
	 * @param lp
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		putValue(Action.NAME, prov.getString(this.key));
		try {
			putValue(Action.SHORT_DESCRIPTION, prov.getString(key + "Desc"));
		} catch (MissingResourceException ex) {}
		
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, prov.getString(key));
				try {
					putValue(Action.SHORT_DESCRIPTION, prov.getString(key + "Desc"));
				} catch (MissingResourceException ex) {}
			}
		};
		prov.addLocalizationListener(listener);
		
	}
	

	
}
