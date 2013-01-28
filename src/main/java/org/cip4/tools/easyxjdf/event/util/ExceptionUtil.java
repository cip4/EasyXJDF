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
package org.cip4.tools.easyxjdf.event.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.cip4.tools.easyxjdf.ErrorController;
import org.cip4.tools.easyxjdf.model.ErrorModel;
import org.eclipse.swt.widgets.Shell;

/**
 * Exception Util class for processing Exceptions.
 * @author s.meissner
 * @date 28.01.2013
 */
public class ExceptionUtil {

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

}
