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

import javax.xml.bind.JAXBException;

import org.cip4.lib.xprinttalk.PrintTalkFactory;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener;
import org.cip4.tools.easyxjdf.event.XJdfSendEvent;
import org.cip4.tools.easyxjdf.event.XJdfSendEventListener;
import org.cip4.tools.easyxjdf.model.SendModel;
import org.cip4.tools.easyxjdf.service.XJdfService;

/**
 * The XJDF Controller Class (MVC Pattern).
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class XJdfController {

	private final XJdfView xJdfView;

	/**
	 * Default constructor.
	 * @throws JAXBException
	 */
	public XJdfController() {

		// initialize instance variables
		this.xJdfView = new XJdfView();

		// registrate listener
		xJdfView.addXJdfSaveAsListener(new XJdfSaveAsListener());
		xJdfView.addXJdfSendListener(new XJdfSendListener());

		// init XJDF Librariy
		PrintTalkFactory.init(true);
	}

	/**
	 * Show the XJdfView Form.
	 */
	public void showView() {

		// show view
		xJdfView.open();
	}

	/**
	 * Listener class for SaveAs event.
	 * @author stefan.meissner
	 * @date 25.01.2013
	 */
	private class XJdfSaveAsListener implements XJdfSaveAsEventListener {

		/**
		 * @throws Exception
		 * @see org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent)
		 */
		@Override
		public void notify(XJdfSaveAsEvent saveAsEvent) {

			try {
				// save XJDF
				XJdfService service = new XJdfService();
				service.saveAs(saveAsEvent.getxJdfModel(), saveAsEvent.getTargetLocation());

				// show info
				xJdfView.showInfo("XJDF successfully was saved.");

			} catch (Exception e) {

				// process exception
				ErrorController.processException(xJdfView.shell, e);
			}
		}

	}

	/**
	 * Listener class for Send event.
	 * @author stefan.meissner
	 * @date 25.01.2013
	 */
	public class XJdfSendListener implements XJdfSendEventListener {

		/**
		 * @see org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent)
		 */
		@Override
		public void notify(XJdfSendEvent sendEvent) {

			try {

				// open send dialog
				SendController sendController = new SendController(xJdfView.shell);
				sendController.showView();

				// get send model object
				SendModel sendModel = sendController.getSendModel();

				// send xjdf
				XJdfService service = new XJdfService();
				service.send(sendEvent.getxJdfModel(), sendModel.getTargetUrl());

				// show info
				xJdfView.showInfo(String.format("XJDF successfully has been sent to \"%s\"", sendModel.getTargetUrl()));

			} catch (Exception e) {

				// process exception
				ErrorController.processException(xJdfView.shell, e);
			}

		}

	}
}
