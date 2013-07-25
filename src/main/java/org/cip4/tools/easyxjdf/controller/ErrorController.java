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

import java.awt.Dialog;

import javax.swing.JFrame;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.cip4.tools.easyxjdf.model.ErrorModel;
import org.cip4.tools.easyxjdf.view.ErrorDialog;

/**
 * The Error Controller Class (MVC Pattern).
 * @author s.meissner
 * @date 28.01.2013
 */
public class ErrorController {

	/**
	 * Default constructor.
	 */
	private ErrorController() {

	}

	/**
	 * Processes an Exception.
	 * @param e Exception to process.
	 */
	public static void processException(JFrame parent, Exception e) {

		// create error model
		ErrorModel errorModel = createModel(e);

		// show error dialog
		ErrorDialog dialog = new ErrorDialog(parent, errorModel);
		dialog.showDialog();
	}

	/**
	 * Processes an Exception.
	 * @param e Exception to process.
	 */
	public static void processException(Dialog parent, Exception e) {

		// create error model
		ErrorModel errorModel = createModel(e);

		// show error dialog
		ErrorDialog dialog = new ErrorDialog(parent, errorModel);
		dialog.showDialog();
	}

	/**
	 * Create error model from exception.
	 * @param e Exception as source.
	 * @return Error model generated from exception.
	 */
	private static ErrorModel createModel(Exception e) {
		// analyze excpetion
		String stackTace = ExceptionUtils.getFullStackTrace(e);
		String message = ExceptionUtils.getMessage(e);

		// new model object
		ErrorModel errorModel = new ErrorModel();
		errorModel.setStackTrace(stackTace);
		errorModel.setMessage(message);
		errorModel.setLocalizedMessage(e.getLocalizedMessage());

		// return error
		return errorModel;
	}

}
