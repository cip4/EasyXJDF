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
package org.cip4.tools.easyxjdf.model;

import java.util.List;

/**
 * Simple Settings Model Object.
 * @author s.meissner
 * @date 31.01.2013
 */
public class SettingsModel {

	private String systemType;

	private String url;

	private boolean isDefault;

	private List<String> MediaQualities;

	private List<String> CustomerIDs;

	private List<String> CatalogIDs;

	private List<Integer> Amount;

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
	 * Getter for url attribute.
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Setter for url attribute.
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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

	/**
	 * Getter for mediaQualities attribute.
	 * @return the mediaQualities
	 */
	public List<String> getMediaQualities() {
		return MediaQualities;
	}

	/**
	 * Setter for mediaQualities attribute.
	 * @param mediaQualities the mediaQualities to set
	 */
	public void setMediaQualities(List<String> mediaQualities) {
		MediaQualities = mediaQualities;
	}

	/**
	 * Getter for customerIDs attribute.
	 * @return the customerIDs
	 */
	public List<String> getCustomerIDs() {
		return CustomerIDs;
	}

	/**
	 * Setter for customerIDs attribute.
	 * @param customerIDs the customerIDs to set
	 */
	public void setCustomerIDs(List<String> customerIDs) {
		CustomerIDs = customerIDs;
	}

	/**
	 * Getter for catalogIDs attribute.
	 * @return the catalogIDs
	 */
	public List<String> getCatalogIDs() {
		return CatalogIDs;
	}

	/**
	 * Setter for catalogIDs attribute.
	 * @param catalogIDs the catalogIDs to set
	 */
	public void setCatalogIDs(List<String> catalogIDs) {
		CatalogIDs = catalogIDs;
	}

	/**
	 * Getter for amount attribute.
	 * @return the amount
	 */
	public List<Integer> getAmount() {
		return Amount;
	}

	/**
	 * Setter for amount attribute.
	 * @param amount the amount to set
	 */
	public void setAmount(List<Integer> amount) {
		Amount = amount;
	}

}
