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
package org.cip4.tools.easyxjdf.exception;

/**
 * Exception which is thrown in case an Connection Exception occurs.
 * @author stefan.meissner
 * @date 05.02.2013
 */
public class ConnectionException extends Exception {

	/**
	 * Custom constructor. Initializing exception by several params.
	 * @param message Exception message.
	 * @param t Base exception.
	 */
	public ConnectionException(String message, Throwable t) {
		super(message, t);
	}

}
