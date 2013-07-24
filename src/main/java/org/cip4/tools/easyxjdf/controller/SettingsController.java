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
package org.cip4.tools.easyxjdf.controller;

import javax.swing.JFrame;

import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.cip4.tools.easyxjdf.service.SettingsService;
import org.cip4.tools.easyxjdf.view.SettingsDialog;

/**
 * The Send Controller Class (MVC Pattern).
 * @author s.meissner
 * @date 30.01.2013
 */
public class SettingsController {

	private final JFrame parent;

	/**
	 * Custom constructor. Accepting a parameter for initializing.
	 * @param parent The Parent Shell object.
	 */
	public SettingsController(JFrame parent) {

		this.parent = parent;
	}

	/**
	 * Show the SettingView Form.
	 * @return The latest setting model.
	 */
	public SettingsModel showView() {

		// load settings
		SettingsService settingsService = new SettingsService();
		SettingsModel settingsModel = settingsService.loadSettings();

		// show view
		SettingsDialog settingsDialog = new SettingsDialog(parent, settingsModel);
		settingsDialog.showDialog();
		SettingsModel result = settingsDialog.getResult();

		// if necessary, save
		if (result != null) {
			try {
				settingsService.saveSettings(result);
			} catch (Exception e) {
				ErrorController.processException(parent, e);
			}
		}

		// return result
		return result;
	}
}
