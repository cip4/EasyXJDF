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
 * XJDF EventListener class for save as events.
 * @author stefan.meissner
 * @date 25.01.2013
 */
public interface XJdfSaveAsEventListener extends EventListener {

	public void notify(XJdfSaveAsEvent xJdfSaveAsEvent);

}
