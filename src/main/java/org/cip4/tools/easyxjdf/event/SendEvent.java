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
package org.cip4.tools.easyxjdf.event;

import java.util.EventObject;

import org.cip4.tools.easyxjdf.model.SendModel;

/**
 * Send Event class.
 * @author s.meissner
 * @date 30.01.2013
 */
public class SendEvent extends EventObject {

	private static final long serialVersionUID = 6772126490771241159L;

	private final SendModel sendModel;

	/**
	 * Custom constructor. Accepting source and model object for initializing.
	 * @param sendModel SendModel object.
	 */
	public SendEvent(SendModel sendModel) {

		super(sendModel);

		this.sendModel = sendModel;
	}

	/**
	 * Getter for sendModel attribute.
	 * @return the sendModel
	 */
	public SendModel getSendModel() {
		return sendModel;
	}

}
