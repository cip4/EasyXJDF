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
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.cip4.tools.easyxjdf.model.XJdfModel;

/**
 * Service class which handles all User Settings.
 * @author s.meissner
 * @date 31.01.2013
 */
public class SettingsService {

	private static final String KEY_SYSTEM_TYPE = "Settings.Connector.SystemType";

	private static final String KEY_URL = "Settings.Connector.Url";

	private static final String KEY_IS_DEFAULT = "Settings.Connector.IsDefaultUrl";

	private static final String KEY_AUTO_EXTEND = "Settings.Suggestions.AutoExtend";

	private static final String KEY_MEDIA_QUALITIES = "Settings.Suggestions.MediaQualities.MediaQuality";

	private static final String KEY_CUSTOMER_ID = "Settings.Suggestions.CustomerIDs.CustomerID";

	private static final String KEY_CATALOG_ID = "Settings.Suggestions.CatalogIDs.CatalogID";

	private static final String KEY_AMOUNTS = "Settings.Suggestions.Amounts.Amount";

	private static final String KEY_FINISHED_DIMENSIONS = "Settings.Suggestions.FinishedDimensions.FinishedDimensions";

	private final File settingsFile;

	/**
	 * Default constructor.
	 */
	public SettingsService() {

		// init settings file
		String pathDir = FilenameUtils.concat(FileUtils.getUserDirectoryPath(), "CIP4Tools");
		pathDir = FilenameUtils.concat(pathDir, "EasyXJDF");
		new File(pathDir).mkdirs();

		String pathFile = FilenameUtils.concat(pathDir, "settings.xml");
		this.settingsFile = new File(pathFile);
	}

	/**
	 * Disables the default URL.
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public void disableDefaultUrl() throws ConfigurationException, IOException {

		// load settings
		SettingsModel settings = loadSettings();

		// disable
		settings.setDefaultUrl(false);

		// save settings
		saveSettings(settings);
	}

	/**
	 * Extend suggestion values by a new XJdfModel.
	 * @param xJdfModel XJDFModel to extend to.
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public void autoExtend(XJdfModel xJdfModel) throws ConfigurationException, IOException {

		// load settings
		SettingsModel settings = loadSettings();

		if (!settings.isAutoExtend()) {
			return;
		}

		// update amount
		int amount = xJdfModel.getAmount();

		if (amount != 0 && !settings.getAmounts().contains(amount)) {
			settings.getAmounts().add(amount);
		}

		// update MediaQuality
		String mediaQuality = xJdfModel.getMediaQuality();

		if (!StringUtils.isEmpty(mediaQuality) && !settings.getMediaQualities().contains(mediaQuality)) {
			settings.getMediaQualities().add(mediaQuality);
		}

		// update CatalogID
		String catalogID = xJdfModel.getCatalogId();

		if (!StringUtils.isEmpty(catalogID) && !settings.getCatalogIDs().contains(catalogID)) {
			settings.getCatalogIDs().add(catalogID);
		}

		// update CustomerID
		String customerID = xJdfModel.getCustomerId();

		if (!StringUtils.isEmpty(customerID) && !settings.getCustomerIDs().contains(customerID)) {
			settings.getCustomerIDs().add(customerID);
		}

		// update FinishedDimensions
		String finishedDimensions = xJdfModel.getFinishedDimensions().toString();

		if (finishedDimensions != null) {
			settings.getFinishedDimensions().add(finishedDimensions);
		}

		// save settings
		saveSettings(settings);
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
		xmlConfig.setProperty(KEY_IS_DEFAULT, Boolean.toString(settings.isDefaultUrl()));
		xmlConfig.setProperty(KEY_AUTO_EXTEND, Boolean.toString(settings.isAutoExtend()));

		Collections.sort(settings.getMediaQualities());
		for (String mediaQuality : settings.getMediaQualities()) {
			xmlConfig.addProperty(KEY_MEDIA_QUALITIES, mediaQuality);
		}

		Collections.sort(settings.getCatalogIDs());
		for (String catalogId : settings.getCatalogIDs()) {
			xmlConfig.addProperty(KEY_CATALOG_ID, catalogId);
		}

		Collections.sort(settings.getCustomerIDs());
		for (String customerId : settings.getCustomerIDs()) {
			xmlConfig.addProperty(KEY_CUSTOMER_ID, customerId);
		}

		Collections.sort(settings.getAmounts());
		for (Integer amount : settings.getAmounts()) {
			xmlConfig.addProperty(KEY_AMOUNTS, amount);
		}

		Collections.sort(settings.getFinishedDimensions());
		for (String finishedDimensions : settings.getFinishedDimensions()) {
			xmlConfig.addProperty(KEY_FINISHED_DIMENSIONS, finishedDimensions);
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
			settings.setDefaultUrl(xmlConfig.getBoolean(KEY_IS_DEFAULT, false));
			settings.setAutoExtend(xmlConfig.getBoolean(KEY_AUTO_EXTEND, true));

			String[] lstMediaQualities = xmlConfig.getStringArray(KEY_MEDIA_QUALITIES);
			settings.setMediaQualities(new ArrayList<String>(Arrays.asList(lstMediaQualities)));

			String[] lstCustomerIDs = xmlConfig.getStringArray(KEY_CUSTOMER_ID);
			settings.setCustomerIDs(new ArrayList<String>(Arrays.asList(lstCustomerIDs)));

			String[] lstCatalogIDs = xmlConfig.getStringArray(KEY_CATALOG_ID);
			settings.setCatalogIDs(new ArrayList<String>(Arrays.asList(lstCatalogIDs)));

			List<Object> lstAmountObj = xmlConfig.getList(KEY_AMOUNTS, new ArrayList<Object>());
			settings.setAmounts(new ArrayList<Integer>(lstAmountObj.size()));

			String[] lstFinishedDimensions = xmlConfig.getStringArray(KEY_FINISHED_DIMENSIONS);
			settings.setFinishedDimensions(new ArrayList<String>(Arrays.asList(lstFinishedDimensions)));

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
		settingsModel.setDefaultUrl(false);
		settingsModel.setAutoExtend(true);

		settingsModel.setMediaQualities(new ArrayList<String>());
		settingsModel.setCustomerIDs(new ArrayList<String>());
		settingsModel.setCatalogIDs(new ArrayList<String>());
		settingsModel.setAmounts(new ArrayList<Integer>());
		settingsModel.setFinishedDimensions(new ArrayList<String>());

		// return model
		return settingsModel;
	}
}
