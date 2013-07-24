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
package org.cip4.tools.easyxjdf.service;

import java.io.IOException;
import java.util.Properties;

import org.cip4.lib.xjdf.xml.XJdfConstants;
import org.cip4.lib.xprinttalk.xml.PrintTalkConstants;
import org.cip4.tools.easyxjdf.model.InfoModel;

/**
 * Service class which gathers and provides implementation details.
 * @author stefan.meissner
 * @date 30.01.2013
 */
public class InfoService {

	private static final String RES_BUILD_PROPS = "/org/cip4/tools/easyxjdf/build.properties";

	private static final Properties props = loadProperties();

	/**
	 * Default constructor;
	 */
	public InfoService() {

	}

	/**
	 * Returns an InfoModel object which already contains all application details.
	 * @return New InfoModel Object.
	 */
	public InfoModel getInfos() {

		// new model
		InfoModel infoModel = new InfoModel();

		// set attributes
		infoModel.setEasyXJdfVersion(props.getProperty("version", "UNKNOWN"));
		infoModel.setEasyXJdfBuildDate(props.getProperty("build.date", "UNKNOWN"));

		infoModel.setxJdfVersion(XJdfConstants.XJDF_CURRENT_VERSION);
		infoModel.setxJdfLibVersion(XJdfConstants.XJDF_LIB_VERSION);
		infoModel.setxJdfLibBuildDate(XJdfConstants.XJDF_LIB_BUILD_DATE);

		infoModel.setPtkVersion(PrintTalkConstants.PTK_CURRENT_VERSION);
		infoModel.setPtkLibVersion(PrintTalkConstants.PTK_LIB_VERSION);
		infoModel.setPtkLibBuildDate(PrintTalkConstants.PTK_LIB_BUILD_DATE);

		// return object
		return infoModel;
	}

	/**
	 * Load Properties.
	 * @return New Properties object.
	 */
	private static Properties loadProperties() {

		// load Properties
		Properties props = new Properties();

		try {
			props.load(XJdfConstants.class.getResourceAsStream(RES_BUILD_PROPS));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props;
	}

}
