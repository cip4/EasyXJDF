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
package org.cip4.tools.easyxjdf;

import org.cip4.tools.easyxjdf.controller.XJdfController;

/**
 * Application start class.
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class App {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// set system properties
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "EasyXJDF");

		// Start application.
		XJdfController xJdfController = new XJdfController();
		xJdfController.showView();
	}

}
