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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.cip4.lib.xjdf.XJdfNodeFactory;
import org.cip4.lib.xjdf.builder.ProductBuilder;
import org.cip4.lib.xjdf.builder.XJdfBuilder;
import org.cip4.lib.xjdf.schema.Product;
import org.cip4.lib.xjdf.schema.XJDF;
import org.cip4.lib.xprinttalk.PrintTalkNodeFactory;
import org.cip4.lib.xprinttalk.builder.PrintTalkBuilder;
import org.cip4.lib.xprinttalk.schema.PrintTalk;
import org.cip4.lib.xprinttalk.xml.PrintTalkPackager;
import org.cip4.lib.xprinttalk.xml.PrintTalkParser;
import org.cip4.tools.easyxjdf.model.XJdfModel;

/**
 * Service class which provides all XJDF functionality.
 * @author stefan.meissner
 * @date 25.01.2013
 */
public class XJdfService {

	private final XJdfNodeFactory nf;

	private final PrintTalkNodeFactory ptkNf;

	/**
	 * Default constructor.
	 */
	public XJdfService() {

		// init instance variables
		nf = new XJdfNodeFactory();
		ptkNf = new PrintTalkNodeFactory();
	}

	/**
	 * Generate XJDF Document and save in target location.
	 * @param xJdfModel The XJDF details.
	 * @param targetLocation The target location.
	 * @throws Exception
	 */
	public void saveAs(XJdfModel xJdfModel, String targetLocation) throws Exception {

		// create PrintTalk Document
		PrintTalk ptk = createPrintTalk(xJdfModel);

		// parse print talk
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintTalkParser parser = new PrintTalkParser();
		parser.parsePrintTalk(ptk, bos);
		bos.close();

		byte[] bytes = bos.toByteArray();

		// save to target location
		File file = new File(targetLocation);
		OutputStream os = new FileOutputStream(file);

		if ("zip".equalsIgnoreCase(FilenameUtils.getExtension(targetLocation))) {

			// save as ZIP
			String docName = xJdfModel.getJobId() + ".xjdf";
			PrintTalkPackager packager = new PrintTalkPackager(bytes);
			packager.packageXJdf(os, docName);

		} else {

			// save as XJDF
			IOUtils.copy(new ByteArrayInputStream(bytes), os);
		}

		os.close();
	}

	/**
	 * Creates an XJDF Document from the XJdfModel.
	 * @param xJdfModel The XJDF Model object.
	 * @return XJDF Document.
	 */
	private PrintTalk createPrintTalk(XJdfModel xJdfModel) {

		// create print talk
		ProductBuilder productBuilder = new ProductBuilder(xJdfModel.getAmount());
		Product product = productBuilder.build();

		XJdfBuilder xJdfBuilder = new XJdfBuilder(xJdfModel.getJobId());
		xJdfBuilder.addProduct(product);
		xJdfBuilder.addParameter(nf.createRunList(xJdfModel.getContentData()));
		XJDF xjdf = xJdfBuilder.build();

		PrintTalkBuilder ptkBuilder = new PrintTalkBuilder();
		ptkBuilder.addRequest(ptkNf.createPurchaseOrder(xJdfModel.getJobId(), null, xjdf));
		PrintTalk ptk = ptkBuilder.build();

		// return PrintTalk
		return ptk;
	}

}
