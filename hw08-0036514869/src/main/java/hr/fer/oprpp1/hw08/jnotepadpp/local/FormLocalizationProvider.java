package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class that extends LocalizationProviderBridge
 * 
 * @author Daniel_Ranogajec
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Constructor
	 * @param lp
	 * @param frame
	 */
	public FormLocalizationProvider(ILocalizationProvider lp, JFrame frame) {
		super(lp);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				FormLocalizationProvider.this.connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				FormLocalizationProvider.this.disconnect();
			}

		});
	}

}
