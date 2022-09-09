package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

/**
 * Class used as adapter for LocalizableAction 
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LocalizableAdapter extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param key
	 * @param lp
	 */
	public LocalizableAdapter(String key, ILocalizationProvider lp) {
		super(key, lp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

}
