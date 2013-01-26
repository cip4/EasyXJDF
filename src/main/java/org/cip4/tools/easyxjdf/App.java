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


/**
 * Application start class.
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class App {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {

		// Start application.
		XJdfController xJdfController = new XJdfController();
		xJdfController.showView();
	}

}
