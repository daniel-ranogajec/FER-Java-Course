package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that extends AbstractLocalizationProvider
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	private String language;
	private ResourceBundle bundle;
	private static LocalizationProvider lp = new LocalizationProvider();
	
	/**
	 * Constructor which sets default language as English
	 */
	private LocalizationProvider() {
		this.language = "en";
		this.bundle =  ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.translations", Locale.forLanguageTag(language));
	}
	
	/**
	 * Public method that returns instance of LocalizationProvider
	 * @return LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return lp;
	}
	
	/**
	 * Public method used for setting language
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
		this.bundle =  ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.translations", Locale.forLanguageTag(language));
		fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
