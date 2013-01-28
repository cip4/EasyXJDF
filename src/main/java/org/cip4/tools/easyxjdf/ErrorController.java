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

import org.cip4.tools.easyxjdf.model.ErrorModel;
import org.eclipse.swt.widgets.Shell;

/**
 * The Error Controller Class (MVC Pattern).
 * @author s.meissner
 * @date 28.01.2013
 */
public class ErrorController {

	private final ErrorView errorView;

	/**
	 * Default constructor.
	 */
	public ErrorController(Shell parent, ErrorModel errorModel) {

		// initialize instance variables
		this.errorView = new ErrorView(parent, errorModel);

	}

	/**
	 * Show the XJdfView Form.
	 */
	public void showView() {

		// show view
		errorView.open();
	}

}
