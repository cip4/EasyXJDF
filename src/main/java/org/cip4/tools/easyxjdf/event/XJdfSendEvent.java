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

import org.cip4.tools.easyxjdf.model.XJdfModel;

/**
 * XJDF Send Event class.
 * @author s.meissner
 * @date 30.01.2013
 */
public class XJdfSendEvent extends EventObject {

	private static final long serialVersionUID = 6772126490771241159L;

	private final XJdfModel xJdfModel;

	/**
	 * Custom constructor. Accepting source and model object for initializing.
	 * @param xJdfModel XJdfModel object.
	 */
	public XJdfSendEvent(XJdfModel xJdfModel) {

		super(xJdfModel);

		this.xJdfModel = xJdfModel;
	}

	/**
	 * Getter for xJdfModel attribute.
	 * @return the xJdfModel
	 */
	public XJdfModel getxJdfModel() {
		return xJdfModel;
	}

}
