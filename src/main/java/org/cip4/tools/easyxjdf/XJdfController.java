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

import org.cip4.tools.easyxjdf.event.SaveAsEvent;
import org.cip4.tools.easyxjdf.event.SaveAsEventListener;
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
	 */
	public XJdfController() {

		// initialize instance variables
		this.xJdfView = new XJdfView();

		// registrate listener
		xJdfView.addSaveAsListener(new SaveAsListener());
		xJdfView.addSendListener(new SendListener());
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
	private class SaveAsListener implements SaveAsEventListener {

		/**
		 * @throws Exception
		 * @see org.cip4.tools.easyxjdf.event.SaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.SaveAsEvent)
		 */
		public void notify(SaveAsEvent xJdfEvent) {

			XJdfService service = new XJdfService();
			try {
				service.saveAs(xJdfEvent.getxJdfModel(), xJdfEvent.getTargetLocation());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	/**
	 * Listener class for Send event.
	 * @author stefan.meissner
	 * @date 25.01.2013
	 */
	public class SendListener implements SaveAsEventListener {

		/**
		 * @see org.cip4.tools.easyxjdf.event.SaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.SaveAsEvent)
		 */
		public void notify(SaveAsEvent xJdfEvent) {
			// TODO Auto-generated method stub

		}

	}
}
