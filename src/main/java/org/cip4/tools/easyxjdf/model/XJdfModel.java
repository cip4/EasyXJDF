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
 * Simple XJdf model object.
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class XJdfModel {

	private String jobId;

	private String contentData;

	private int amount;

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
	 * Getter for contentData attribute.
	 * @return the contentData
	 */
	public String getContentData() {
		return contentData;
	}

	/**
	 * Setter for contentData attribute.
	 * @param contentData the contentData to set
	 */
	public void setContentData(String contentData) {
		this.contentData = contentData;
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

}
