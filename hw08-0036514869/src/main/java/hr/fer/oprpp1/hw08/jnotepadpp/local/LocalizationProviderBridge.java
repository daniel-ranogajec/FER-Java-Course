package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Class that extends AbstractLocalizationProvider
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	private boolean connected = false;
	private ILocalizationProvider parent;
	private ILocalizationListener listener;
	
	/**
	 * Constructor
	 * @param lp ILocalizationProvider
	 */
	public LocalizationProviderBridge(ILocalizationProvider lp) {
		parent = lp;
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	/**
	 * Method called when frame is opened
	 */
	public void connect() {
		if (connected)
			return;

		parent.addLocalizationListener(listener);
		
		connected = true;
	}
	
	/**
	 * Method called when frame is closed
	 */
	public void disconnect() {	
		parent.removeLocalizationListener(listener);
		listener = null;
		connected = false;
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}

}
