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

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

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

	private final static String RES_SETTING = "/org/cip4/tools/easyxjdf/settingsTest.xml";

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
	 * Test method for {@link org.cip4.tools.easyxjdf.service.SettingsService#loadSettings()}.
	 */
	@Test
	public void testLoadSettings() throws Exception {

		// arrange
		String fileName = SettingsServiceTest.class.getResource(RES_SETTING).getFile();
		File mockSettings = new File(fileName);

		Field field = SettingsService.class.getDeclaredField("settingsFile");
		field.setAccessible(true);
		field.set(service, mockSettings);

		// act
		Method method = SettingsService.class.getDeclaredMethod("loadSettings");
		method.setAccessible(true);
		SettingsModel settingsModel = (SettingsModel) method.invoke(service);

		// assert
		Assert.assertEquals("SystemType is wrong.", "Heidelberg Prinect", settingsModel.getSystemType());
		Assert.assertEquals("Url is wrong.", "http://localhost:8011", settingsModel.getUrl());
		Assert.assertEquals("IsDefault is wrong.", true, settingsModel.isDefaultUrl());

		Assert.assertEquals("Number MediaQuantities is wrong.", 3, settingsModel.getMediaQualities().size());
		Assert.assertEquals("MediaQuantity is wrong.", "IPG_250", settingsModel.getMediaQualities().get(2));

		Assert.assertEquals("Number CustomerIDs is wrong.", 4, settingsModel.getCustomerIDs().size());
		Assert.assertEquals("CustomerID is wrong.", "FA-123", settingsModel.getCustomerIDs().get(0));

		Assert.assertEquals("Number CatalogIDs is wrong.", 2, settingsModel.getCatalogIDs().size());
		Assert.assertEquals("CatalogID is wrong.", "CAT_FOLDER", settingsModel.getCatalogIDs().get(1));

		Assert.assertEquals("Number Amounts is wrong.", 7, settingsModel.getAmounts().size());
		Assert.assertEquals("Amount is wrong.", new Integer(5000), settingsModel.getAmounts().get(4));
	}

	@Test
	public void testLoadSettingsNoFile() throws Exception {

		// arrange
		File file = File.createTempFile("EasyXJDF-Test-" + UUID.randomUUID().toString(), "tmp");

		Field field = SettingsService.class.getDeclaredField("settingsFile");
		field.setAccessible(true);
		field.set(service, file);

		// act
		Method method = SettingsService.class.getDeclaredMethod("loadSettings");
		method.setAccessible(true);
		SettingsModel settingsModel = (SettingsModel) method.invoke(service);

		// assert
		Assert.assertEquals("SystemType is wrong.", "Other", settingsModel.getSystemType());
		Assert.assertEquals("Url is wrong.", "", settingsModel.getUrl());
		Assert.assertEquals("IsDefault is wrong.", false, settingsModel.isDefaultUrl());
	}

}
