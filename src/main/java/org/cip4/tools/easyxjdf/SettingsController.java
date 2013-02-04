/**
 * All rights reserved by
 * 
 * flyeralarm GmbH
 * Alfred-Nobel-Stra�e 18
 * 97080 W�rzburg
 *
 * Email: info@flyeralarm.com
 * Website: http://www.flyeralarm.com
 */
package org.cip4.tools.easyxjdf;

import org.cip4.tools.easyxjdf.event.SettingsSaveEvent;
import org.cip4.tools.easyxjdf.event.SettingsSaveEventListener;
import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.cip4.tools.easyxjdf.service.SettingsService;
import org.eclipse.swt.widgets.Shell;

/**
 * The Send Controller Class (MVC Pattern).
 * @author s.meissner
 * @date 30.01.2013
 */
public class SettingsController {

	private final SettingsView settingsView;

	private SettingsModel settingsModel;

	private SettingsService settingsService;

	/**
	 * Custom constructor. Accepting a parameter for initializing.
	 * @param parent The Parent Shell object.
	 */
	public SettingsController(Shell parent) {

		// load settings
		this.settingsService = new SettingsService();
		this.settingsModel = settingsService.loadSettings();

		// initialize instance variables
		this.settingsView = new SettingsView(parent, this.settingsModel);

		// register listener
		settingsView.addSettingsSaveEventListener(new SettingsSaveEventListenerImpl());
	}

	/**
	 * Show the SettingView Form.
	 * @return The latest setting model.
	 */
	public SettingsModel showView() {

		// show view
		SettingsModel result = settingsView.open();

		// return result
		return result;

	}

	/**
	 * Listener class for Send event.
	 * @author stefan.meissner
	 * @date 25.01.2013
	 */
	private class SettingsSaveEventListenerImpl implements SettingsSaveEventListener {

		/**
		 * @throws Exception
		 * @see org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent)
		 */
		@Override
		public void notify(SettingsSaveEvent settingsSaveEvent) {

			// update model
			settingsModel = settingsSaveEvent.getSettingsModel();

			// save settings
			try {
				settingsService.saveSettings(settingsModel);
			} catch (Exception e) {
				ErrorController.processException(settingsView.shell, e);
			}
		}

	}
}
