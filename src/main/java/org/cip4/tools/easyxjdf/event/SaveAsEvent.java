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
 * SaveAs Event class.
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class SaveAsEvent extends EventObject {

	private static final long serialVersionUID = 6772126490771241159L;

	private final XJdfModel xJdfModel;

	private final String targetLocation;

	/**
	 * Custom constructor. Accepting source and model object for initializing.
	 * @param xJdfModel XJdfModel object.
	 * @param targetLocation Target location of XJDF
	 */
	public SaveAsEvent(XJdfModel xJdfModel, String targetLocation) {

		super(xJdfModel);

		this.xJdfModel = xJdfModel;
		this.targetLocation = targetLocation;
	}

	/**
	 * Getter for xJdfModel attribute.
	 * @return the xJdfModel
	 */
	public XJdfModel getxJdfModel() {
		return xJdfModel;
	}

	/**
	 * Getter for targetLocation attribute.
	 * @return the targetLocation
	 */
	public String getTargetLocation() {
		return targetLocation;
	}

}
