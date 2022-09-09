package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that implements ILocalizationProvider
 * 
 * @author Daniel_Ranogajec
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	List<ILocalizationListener> list;

	/**
	 * Constructor
	 */
	public AbstractLocalizationProvider() {
		this.list = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		list.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		list.remove(l);
	}
	
	/**
	 * Method used for signaling all listeners
	 */
	public void fire() {
		for (ILocalizationListener l : list) {
			l.localizationChanged();
		}
	}
}
