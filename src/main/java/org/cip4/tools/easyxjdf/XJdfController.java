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

import org.apache.commons.lang.StringUtils;
import org.cip4.lib.xprinttalk.PrintTalkFactory;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener;
import org.cip4.tools.easyxjdf.event.XJdfSendEvent;
import org.cip4.tools.easyxjdf.event.XJdfSendEventListener;
import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.cip4.tools.easyxjdf.service.SettingsService;
import org.cip4.tools.easyxjdf.service.XJdfService;

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
				xJdfView.showInfo("XJDF successfully was saved.");

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
		 * @see org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener#notify(org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent)
		 */
		@Override
		public void notify(XJdfSendEvent sendEvent) {

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
					xJdfView.showInfo(String.format("XJDF successfully has been sent to \"%s\"", url));
				}

				// auto extend
				if (settings.isAutoExtend()) {

					// auto extend
					settingsService.autoExtend(sendEvent.getxJdfModel());

					// update settings in view
					xJdfView.updateSettings(settingsService.loadSettings());
				}

			} catch (Exception e) {

				// process exception
				ErrorController.processException(xJdfView.shell, e);
			}

		}

	}
}
