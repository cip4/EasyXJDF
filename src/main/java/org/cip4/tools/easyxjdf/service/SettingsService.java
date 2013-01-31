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
package org.cip4.tools.easyxjdf.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.cip4.tools.easyxjdf.model.SettingsModel;

/**
 * Service class which handles all User Settings.
 * @author s.meissner
 * @date 31.01.2013
 */
public class SettingsService {

	private static final String KEY_SYSTEM_TYPE = "Settings.Connector.SystemType";

	private static final String KEY_URL = "Settings.Connector.Url";

	private static final String KEY_IS_DEFAULT = "Settings.Connector.IsDefault";

	private static final String KEY_MEDIA_QUALITIES = "Settings.Suggestions.MediaQualities.MediaQuality";

	private static final String KEY_CUSTOMER_ID = "Settings.Suggestions.CustomerIDs.CustomerID";

	private static final String KEY_CATALOG_ID = "Settings.Suggestions.CatalogIDs.CatalogID";

	private static final String KEY_AMOUNTS = "Settings.Suggestions.Amounts.Amount";

	private SettingsModel settingsModel;

	/**
	 * Default constructor.
	 */
	public SettingsService() {

	}

	/**
	 * Getter for settingsModel attribute.
	 * @return the settingsModel
	 */
	public SettingsModel getSettingsModel() {
		return settingsModel;
	}

	/**
	 * Load and parse settings file.
	 * @param is File as InputStream.
	 * @return SettingsModel containing details from file.
	 * @throws ConfigurationException
	 */
	private SettingsModel loadSettings(String fileName) throws ConfigurationException {

		// read file
		XMLConfiguration config = new XMLConfiguration(fileName);

		// create model
		SettingsModel settingsModel = new SettingsModel();

		// load and fill
		settingsModel.setSystemType(config.getString(KEY_SYSTEM_TYPE, "Other"));
		settingsModel.setUrl(config.getString(KEY_URL, ""));
		settingsModel.setDefault(config.getBoolean(KEY_IS_DEFAULT, false));

		String[] lstMediaQualities = config.getStringArray(KEY_MEDIA_QUALITIES);
		settingsModel.setMediaQualities(Arrays.asList(lstMediaQualities));

		String[] lstCustomerIDs = config.getStringArray(KEY_CUSTOMER_ID);
		settingsModel.setCustomerIDs(Arrays.asList(lstCustomerIDs));

		String[] lstCatalogIDs = config.getStringArray(KEY_CATALOG_ID);
		settingsModel.setCatalogIDs(Arrays.asList(lstCatalogIDs));

		List<Object> lstAmountObj = config.getList(KEY_AMOUNTS, new ArrayList<Object>());
		settingsModel.setAmount(new ArrayList<Integer>(lstAmountObj.size()));

		for (Object obj : lstAmountObj)
			settingsModel.getAmount().add(Integer.parseInt(obj.toString()));

		// return model
		return settingsModel;
	}
}
