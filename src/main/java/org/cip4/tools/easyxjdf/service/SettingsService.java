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

	private static final String RES_DEFAULT_SETTINGS = "/org/cip4/tools/easyxjdf/defaultSettings.xml";

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
	 * Create a new settings file.
	 * @throws ConfigurationException
	 * @throws IOException In case DefaultSettings file cannot be copyied.
	 */
	private String createSettings(SettingsModel settings) throws ConfigurationException, IOException {

		String pathDir = FilenameUtils.concat(FileUtils.getUserDirectoryPath(), "EasyXJDF");
		new File(pathDir).mkdirs();

		String pathFile = FilenameUtils.concat(pathDir, "settings.xml");
		File file = new File(pathFile);

		if (!file.exists()) {

			// XMLConfiguration
			// file.createNewFile();

			// InputStream is = SettingsService.class.getResourceAsStream(RES_DEFAULT_SETTINGS);
			// OutputStream os = new FileOutputStream(pathFile);
			// IOUtils.copy(is, os);
			// is.close();
			// os.close();
		}

		XMLConfiguration xmlConfig = new XMLConfiguration();

		xmlConfig.setRootElementName("EasyXJDF");
		xmlConfig.setProperty(KEY_SYSTEM_TYPE, settingsModel.getSystemType());

		xmlConfig.save(pathFile);

		return pathFile;
	}

	/**
	 * Load and parse settings file.
	 * @param is File as InputStream.
	 * @return SettingsModel containing details from file.
	 * @throws ConfigurationException
	 */
	private SettingsModel loadSettings(XMLConfiguration xmlConfig) throws ConfigurationException {

		// create model
		SettingsModel settingsModel = new SettingsModel();

		// load and fill
		settingsModel.setSystemType(xmlConfig.getString(KEY_SYSTEM_TYPE, "Other"));
		settingsModel.setUrl(xmlConfig.getString(KEY_URL, ""));
		settingsModel.setDefault(xmlConfig.getBoolean(KEY_IS_DEFAULT, false));

		String[] lstMediaQualities = xmlConfig.getStringArray(KEY_MEDIA_QUALITIES);
		settingsModel.setMediaQualities(Arrays.asList(lstMediaQualities));

		String[] lstCustomerIDs = xmlConfig.getStringArray(KEY_CUSTOMER_ID);
		settingsModel.setCustomerIDs(Arrays.asList(lstCustomerIDs));

		String[] lstCatalogIDs = xmlConfig.getStringArray(KEY_CATALOG_ID);
		settingsModel.setCatalogIDs(Arrays.asList(lstCatalogIDs));

		List<Object> lstAmountObj = xmlConfig.getList(KEY_AMOUNTS, new ArrayList<Object>());
		settingsModel.setAmount(new ArrayList<Integer>(lstAmountObj.size()));

		for (Object obj : lstAmountObj)
			settingsModel.getAmount().add(Integer.parseInt(obj.toString()));

		// return model
		return settingsModel;
	}

	/**
	 * Update SystemType attribute.
	 */
	private void updateSystemType() {

	}
}
