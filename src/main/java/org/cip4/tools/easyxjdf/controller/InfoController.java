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
package org.cip4.tools.easyxjdf.controller;

import javax.swing.JFrame;
import javax.xml.bind.JAXBException;

import org.cip4.tools.easyxjdf.model.InfoModel;
import org.cip4.tools.easyxjdf.service.InfoService;
import org.cip4.tools.easyxjdf.view.InfoView;

/**
 * The Info Controller Class (MVC Pattern).
 * @author s.meissner
 * @date 30.01.2013
 */
public class InfoController {

	private final InfoView infoView;

	/**
	 * Default constructor.
	 * @throws JAXBException
	 */
	public InfoController(JFrame parent) {

		// load infos
		InfoModel infoModel = new InfoService().getInfos();

		// initialize instance variables
		this.infoView = new InfoView(parent, infoModel);

	}

	/**
	 * Show the SendView Form.
	 */
	public void showView() {

		// show view
		infoView.open();
	}

}
