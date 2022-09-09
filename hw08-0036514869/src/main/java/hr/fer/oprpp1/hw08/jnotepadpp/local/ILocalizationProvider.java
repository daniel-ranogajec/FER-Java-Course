package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface that works with localization listeners
 * 
 * @author Daniel_Ranogajec
 *
 */
public interface ILocalizationProvider {

	/**
	 * Method for adding localization listener 
	 * @param l ILocalizationListener
	 */
	public void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Method for removing localization listener 
	 * @param l ILocalizationListener
	 */
	public void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Method used for translating given key
	 * @param key
	 * @return translated key
	 */
	public String getString(String key);
	
	/**
	 * Method used for getting current language
	 * @return current language
	 */
	public String getCurrentLanguage();
}
