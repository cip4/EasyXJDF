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
package org.cip4.tools.easyxjdf;

import org.apache.commons.lang.exception.ExceptionUtils;
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
	 * Processes an Exception.
	 * @param e Exception to process.
	 */
	public static void processException(Shell shell, Exception e) {

		// analyze excpetion
		String stackTace = ExceptionUtils.getFullStackTrace(e);
		String message = ExceptionUtils.getMessage(e);

		// new model object
		ErrorModel errorModel = new ErrorModel();
		errorModel.setStackTrace(stackTace);
		errorModel.setMessage(message);
		errorModel.setLocalizedMessage(e.getLocalizedMessage());

		// show error dialog
		ErrorController errorController = new ErrorController(shell, errorModel);
		errorController.showView();
	}

	/**
	 * Show the XJdfView Form.
	 */
	public void showView() {

		// show view
		errorView.open();
	}

}
