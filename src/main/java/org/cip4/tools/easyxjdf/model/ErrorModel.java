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
 * Simple Error model object.
 * @author s.meissner
 * @date 28.01.2013
 */
public class ErrorModel {

	private String message;

	private String stackTrace;

	private String localizedMessage;

	/**
	 * Getter for message attribute.
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message attribute.
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for stackTrace attribute.
	 * @return the stackTrace
	 */
	public String getStackTrace() {
		return stackTrace;
	}

	/**
	 * Setter for stackTrace attribute.
	 * @param stackTrace the stackTrace to set
	 */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	/**
	 * Getter for localizedMessage attribute.
	 * @return the localizedMessage
	 */
	public String getLocalizedMessage() {
		return localizedMessage;
	}

	/**
	 * Setter for localizedMessage attribute.
	 * @param localizedMessage the localizedMessage to set
	 */
	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}

}
