/**
 * All rights reserved by
 * 
 * flyeralarm GmbH
 * Alfred-Nobel-Stra�e 18
 * 97080 Würzburg
 *
 * Email: info@flyeralarm.com
 * Website: http://www.flyeralarm.com
 */
package org.cip4.tools.easyxjdf.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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

	private final File settingsFile;

	/**
	 * Default constructor.
	 */
	public SettingsService() {

		// init settings file
		String pathDir = FilenameUtils.concat(FileUtils.getUserDirectoryPath(), "EasyXJDF");
		new File(pathDir).mkdirs();

		String pathFile = FilenameUtils.concat(pathDir, "settings.xml");
		this.settingsFile = new File(pathFile);
	}

	/**
	 * Save all setting in user profile.
	 * @throws ConfigurationException
	 * @throws IOException In case DefaultSettings file cannot be copyied.
	 */
	public void saveSettings(SettingsModel settings) throws ConfigurationException, IOException {

		// new configuration
		XMLConfiguration xmlConfig = new XMLConfiguration();

		// write settins to config file
		xmlConfig.setRootElementName("EasyXJDF");
		xmlConfig.setProperty(KEY_SYSTEM_TYPE, settings.getSystemType());
		xmlConfig.setProperty(KEY_URL, settings.getUrl());
		xmlConfig.setProperty(KEY_IS_DEFAULT, Boolean.toString(settings.isDefault()));

		for (String mediaQuality : settings.getMediaQualities()) {
			xmlConfig.addProperty(KEY_MEDIA_QUALITIES, mediaQuality);
		}

		for (String catalogId : settings.getCatalogIDs()) {
			xmlConfig.addProperty(KEY_CATALOG_ID, catalogId);
		}

		for (String customerId : settings.getCustomerIDs()) {
			xmlConfig.addProperty(KEY_CUSTOMER_ID, customerId);
		}

		for (Integer amount : settings.getAmounts()) {
			xmlConfig.addProperty(KEY_AMOUNTS, amount);
		}

		// save config files
		xmlConfig.save(settingsFile);

	}

	/**
	 * Load and parse settings file.
	 * @param is File as InputStream.
	 * @return SettingsModel containing details from file.
	 * @throws ConfigurationException
	 */
	public SettingsModel loadSettings() {

		// create model
		SettingsModel settings;

		if (!settingsFile.isFile()) {

			// load defaults
			settings = defaultSettings();

		} else {

			XMLConfiguration xmlConfig;

			try {
				xmlConfig = new XMLConfiguration(settingsFile);

			} catch (ConfigurationException e) {

				// return default settings
				return defaultSettings();
			}

			// create model
			settings = new SettingsModel();

			// load and fill
			settings.setSystemType(xmlConfig.getString(KEY_SYSTEM_TYPE, "Other"));
			settings.setUrl(xmlConfig.getString(KEY_URL, ""));
			settings.setDefault(xmlConfig.getBoolean(KEY_IS_DEFAULT, false));

			String[] lstMediaQualities = xmlConfig.getStringArray(KEY_MEDIA_QUALITIES);
			settings.setMediaQualities(Arrays.asList(lstMediaQualities));

			String[] lstCustomerIDs = xmlConfig.getStringArray(KEY_CUSTOMER_ID);
			settings.setCustomerIDs(Arrays.asList(lstCustomerIDs));

			String[] lstCatalogIDs = xmlConfig.getStringArray(KEY_CATALOG_ID);
			settings.setCatalogIDs(Arrays.asList(lstCatalogIDs));

			List<Object> lstAmountObj = xmlConfig.getList(KEY_AMOUNTS, new ArrayList<Object>());
			settings.setAmounts(new ArrayList<Integer>(lstAmountObj.size()));

			for (Object obj : lstAmountObj)
				settings.getAmounts().add(Integer.parseInt(obj.toString()));
		}

		// return model
		return settings;
	}

	/**
	 * Returns the default settings.
	 * @return SeetingsModel with default settings.
	 */
	private SettingsModel defaultSettings() {

		// create model
		SettingsModel settingsModel = new SettingsModel();

		// fill
		settingsModel.setSystemType("Other");
		settingsModel.setUrl("");
		settingsModel.setDefault(false);

		settingsModel.setMediaQualities(new ArrayList<String>());
		settingsModel.setCustomerIDs(new ArrayList<String>());
		settingsModel.setCatalogIDs(new ArrayList<String>());
		settingsModel.setAmounts(new ArrayList<Integer>());

		// return model
		return settingsModel;
	}
}
