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

import org.cip4.tools.easyxjdf.event.SendEvent;
import org.cip4.tools.easyxjdf.event.SendEventListener;
import org.cip4.tools.easyxjdf.model.SendModel;
import org.eclipse.swt.widgets.Shell;

/**
 * The Send Controller Class (MVC Pattern).
 * @author s.meissner
 * @date 30.01.2013
 */
public class SendController {

	private final SendView sendView;

	private SendModel sendModel;

	/**
	 * Default constructor.
	 * @throws JAXBException
	 */
	public SendController(Shell parent) {

		// initialize instance variables
		this.sendView = new SendView(parent);

		// registrate listener
		sendView.addSendListener(new SendListener());
	}

	/**
	 * Getter for sendModel attribute.
	 * @return the sendModel
	 */
	public SendModel getSendModel() {
		return sendModel;
	}

	/**
	 * Setter for sendModel attribute.
	 * @param sendModel the sendModel to set
	 */
	public void setSendModel(SendModel sendModel) {
		this.sendModel = sendModel;
	}

	/**
	 * Show the SendView Form.
	 */
	public void showView() {

		// show view
		sendView.open();

	}

	/**
	 * Listener class for Send event.
	 * @author stefan.meissner
	 * @date 25.01.2013
	 */
	private class SendListener implements SendEventListener {

		/**
		 * @throws Exception
		 * @see org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent)
		 */
		@Override
		public void notify(SendEvent sendEvent) {

			// update model
			sendModel = sendEvent.getSendModel();
		}

	}
}
