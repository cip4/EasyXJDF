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
package org.cip4.tools.easyxjdf.service;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Test Case for SettingsService.
 * @author stefan.meissner
 * @date 31.01.2013
 */
public class SettingsServiceTest {

	private final static String RES_SETTING = "/org/cip4/tools/easyxjdf/settings.xml";

	private SettingsService service;

	/**
	 * Set up unit test.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		service = new SettingsService();
	}

	/**
	 * Tear down unit test.
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		service = null;
	}

	/**
	 * Test method for {@link org.cip4.tools.easyxjdf.service.SettingsService#getSettingsModel()}.
	 */
	@Test
	public void testGetSettingsModel() throws Exception {

		// arrange
		String fileName = SettingsServiceTest.class.getResource(RES_SETTING).getFile();

		// act
		Method method = SettingsService.class.getDeclaredMethod("loadSettings", String.class);
		method.setAccessible(true);
		SettingsModel settingsModel = (SettingsModel) method.invoke(service, fileName);

		// assert
		Assert.assertEquals("SystemType is wrong.", "Heidelberg Prinect", settingsModel.getSystemType());
		Assert.assertEquals("Url is wrong.", "http://localhost:8011", settingsModel.getUrl());
		Assert.assertEquals("IsDefault is wrong.", true, settingsModel.isDefault());

		Assert.assertEquals("Number MediaQuantities is wrong.", 3, settingsModel.getMediaQualities().size());
		Assert.assertEquals("MediaQuantity is wrong.", "IPG_250", settingsModel.getMediaQualities().get(2));

		Assert.assertEquals("Number CustomerIDs is wrong.", 4, settingsModel.getCustomerIDs().size());
		Assert.assertEquals("CustomerID is wrong.", "FA-123", settingsModel.getCustomerIDs().get(0));

		Assert.assertEquals("Number CatalogIDs is wrong.", 2, settingsModel.getCatalogIDs().size());
		Assert.assertEquals("CatalogID is wrong.", "CAT_FOLDER", settingsModel.getCatalogIDs().get(1));

		Assert.assertEquals("Number Amounts is wrong.", 7, settingsModel.getAmount().size());
		Assert.assertEquals("Amount is wrong.", new Integer(5000), settingsModel.getAmount().get(4));

	}

}
