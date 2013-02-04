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

import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.cip4.lib.xprinttalk.PrintTalkFactory;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener;
import org.cip4.tools.easyxjdf.event.XJdfSendEvent;
import org.cip4.tools.easyxjdf.event.XJdfSendEventListener;
import org.cip4.tools.easyxjdf.exception.ConnectionException;
import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.cip4.tools.easyxjdf.service.SettingsService;
import org.cip4.tools.easyxjdf.service.XJdfService;
import org.eclipse.swt.SWT;

/**
 * The XJDF Controller Class (MVC Pattern).
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class XJdfController {

	private final XJdfView xJdfView;

	private final SettingsService settingsService;

	/**
	 * Default constructor.
	 * @throws JAXBException
	 */
	public XJdfController() {

		// initialize instance variables
		this.settingsService = new SettingsService();
		this.xJdfView = new XJdfView(settingsService.loadSettings());

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
				xJdfView.showMessage("XJDF successfully was saved.", SWT.ICON_INFORMATION | SWT.OK);

				// auto extend
				SettingsModel settings = settingsService.loadSettings();

				if (settings.isAutoExtend()) {

					// auto extend
					settingsService.autoExtend(saveAsEvent.getxJdfModel());

					// update settings in view
					xJdfView.updateSettings(settingsService.loadSettings());
				}

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
		 * @throws ConnectionException
		 * @see org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent)
		 */
		@Override
		public void notify(XJdfSendEvent sendEvent) throws ConnectionException {

			try {

				// manage settings
				SettingsModel settings = settingsService.loadSettings();

				if (!settings.isDefaultUrl() || StringUtils.isEmpty(settings.getUrl())) {

					// open send dialog
					SettingsController settingsController = new SettingsController(xJdfView.shell);
					settings = settingsController.showView();
				}

				// send
				if (settings != null) {

					// get url
					String url = settings.getUrl();

					// send xjdf
					XJdfService xJdfService = new XJdfService();
					xJdfService.send(sendEvent.getxJdfModel(), url);

					// show info
					xJdfView.showMessage(String.format("XJDF successfully has been sent to \"%s\"", url), SWT.ICON_INFORMATION | SWT.OK);
				}

				// auto extend
				if (settings.isAutoExtend()) {

					// auto extend
					settingsService.autoExtend(sendEvent.getxJdfModel());

					// update settings in view
					xJdfView.updateSettings(settingsService.loadSettings());
				}

			} catch (ConnectException e) {
				processConnectionException();
				throw new ConnectionException("Cannot send XJDF.", e);

			} catch (UnknownHostException e) {
				processConnectionException();
				throw new ConnectionException("Cannont send XJDF", e);

			} catch (Exception e) {

				// process exception
				ErrorController.processException(xJdfView.shell, e);
			}

		}

		/**
		 * Process Connection exceptions.
		 * @param url URL which has occured the exception.
		 */
		private void processConnectionException() {

			// load url
			SettingsModel settings = settingsService.loadSettings();
			String url = settings.getUrl();

			// show message
			xJdfView.showMessage(String.format("Error sending XJDF to \"%s\". \r\nPlease check the Connection Settings.", url), SWT.ICON_ERROR | SWT.OK);

			// disable url as default
			try {
				settingsService.disableDefaultUrl();
			} catch (Exception ex) {
				ErrorController.processException(xJdfView.shell, ex);
			}

			xJdfView.updateSettings(settingsService.loadSettings());
		}

	}
}
