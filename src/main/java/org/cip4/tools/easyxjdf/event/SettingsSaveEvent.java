/**
 * All rights reserved by
 * 
 * flyeralarm GmbH
 * Alfred-Nobel-Straße 18
 * 97080 Würzburg
 *
 * Email: info@flyeralarm.com
 * Website: http://www.flyeralarm.com
 */
package org.cip4.tools.easyxjdf.event;

import java.util.EventObject;

import org.cip4.tools.easyxjdf.model.SettingsModel;

/**
 * Settings Save Event class.
 * @author s.meissner
 * @date 30.01.2013
 */
public class SettingsSaveEvent extends EventObject {

	private static final long serialVersionUID = 6772126490771241159L;

	private final SettingsModel settingsModel;

	/**
	 * Custom constructor. Accepting source and model object for initializing.
	 * @param settingsModel The SettingsModel object.
	 */
	public SettingsSaveEvent(SettingsModel settingsModel) {

		super(settingsModel);

		this.settingsModel = settingsModel;
	}

	/**
	 * Getter for settingsModel attribute.
	 * @return the settingsModel
	 */
	public SettingsModel getSettingsModel() {
		return settingsModel;
	}

}
