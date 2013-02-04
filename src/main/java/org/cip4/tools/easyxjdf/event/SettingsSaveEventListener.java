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
package org.cip4.tools.easyxjdf.event;

import java.util.EventListener;

/**
 * EventListener class for save setting events.
 * @author s.meissner
 * @date 04.02.2013
 */
public interface SettingsSaveEventListener extends EventListener {

	public void notify(SettingsSaveEvent settingSaveEvent);

}
