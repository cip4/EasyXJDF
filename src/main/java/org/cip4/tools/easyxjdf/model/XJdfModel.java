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

import org.cip4.lib.xjdf.type.Shape;

/**
 * Simple XJdf model object.
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class XJdfModel {

	private String jobId;

	private String jobName;

	private String runList;

	private String catalogId;

	private String customerId;

	private String mediaQuality;

	private int amount;

	private String numColors;

	private Shape finishedDimensions;

	/**
	 * Default constructor.
	 */
	public XJdfModel() {
	}

	/**
	 * Getter for jobId attribute.
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Setter for jobId attribute.
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * Getter for jobName attribute.
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * Setter for jobName attribute.
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Getter for contentData attribute.
	 * @return the contentData
	 */
	public String getRunList() {
		return runList;
	}

	/**
	 * Setter for contentData attribute.
	 * @param contentData the contentData to set
	 */
	public void setRunList(String runList) {
		this.runList = runList;
	}

	/**
	 * Getter for catalogId attribute.
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * Setter for catalogId attribute.
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * Getter for customerId attribute.
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * Setter for customerId attribute.
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * Getter for mediaQuality attribute.
	 * @return the mediaQuality
	 */
	public String getMediaQuality() {
		return mediaQuality;
	}

	/**
	 * Setter for mediaQuality attribute.
	 * @param mediaQuality the mediaQuality to set
	 */
	public void setMediaQuality(String mediaQuality) {
		this.mediaQuality = mediaQuality;
	}

	/**
	 * Getter for amount attribute.
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Setter for amount attribute.
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the numColors
	 */
	public String getNumColors() {
		return numColors;
	}

	/**
	 * @param numColors the numColors to set
	 */
	public void setNumColors(String numColors) {
		this.numColors = numColors;
	}

	/**
	 * @return the finishedDimensions
	 */
	public Shape getFinishedDimensions() {
		return finishedDimensions;
	}

	/**
	 * @param finishedDimensions the finishedDimensions to set
	 */
	public void setFinishedDimensions(Shape finishedDimensions) {
		this.finishedDimensions = finishedDimensions;
	}

}
