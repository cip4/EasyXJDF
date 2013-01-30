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
package org.cip4.tools.easyxjdf.model;

/**
 * Simple Send model object.
 * @author s.meissner
 * @date 30.01.2013
 */
public class SendModel {

	private String systemType;

	private String targetUrl;

	private boolean isDefault;

	/**
	 * Getter for systemType attribute.
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * Setter for systemType attribute.
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	/**
	 * Getter for targetUrl attribute.
	 * @return the targetUrl
	 */
	public String getTargetUrl() {
		return targetUrl;
	}

	/**
	 * Setter for targetUrl attribute.
	 * @param targetUrl the targetUrl to set
	 */
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	/**
	 * Getter for isDefault attribute.
	 * @return the isDefault
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * Setter for isDefault attribute.
	 * @param isDefault the isDefault to set
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

}
