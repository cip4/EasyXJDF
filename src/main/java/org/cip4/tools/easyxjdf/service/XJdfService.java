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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.cip4.lib.xjdf.XJdfNodeFactory;
import org.cip4.lib.xjdf.builder.ProductBuilder;
import org.cip4.lib.xjdf.builder.XJdfBuilder;
import org.cip4.lib.xjdf.schema.EnumSides;
import org.cip4.lib.xjdf.schema.Product;
import org.cip4.lib.xjdf.schema.XJDF;
import org.cip4.lib.xjdf.type.IntegerList;
import org.cip4.lib.xjdf.type.Shape;
import org.cip4.lib.xjdf.util.DimensionUtil;
import org.cip4.lib.xjdf.util.IDGeneratorUtil;
import org.cip4.lib.xprinttalk.PrintTalkNodeFactory;
import org.cip4.lib.xprinttalk.builder.PrintTalkBuilder;
import org.cip4.lib.xprinttalk.schema.PrintTalk;
import org.cip4.lib.xprinttalk.xml.PrintTalkNavigator;
import org.cip4.lib.xprinttalk.xml.PrintTalkPackager;
import org.cip4.lib.xprinttalk.xml.PrintTalkParser;
import org.cip4.tools.easyxjdf.model.XJdfModel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	 * Generate XJDF Document and send to target URL.
	 * @param xJdfModel The XJDF details.
	 * @param url The target URL.
	 */
	public void send(XJdfModel xJdfModel, String url) throws Exception {

		// create PrintTalk Document
		PrintTalk ptk = createPrintTalk(xJdfModel);

		// parse print talk
		PrintTalkParser parser = new PrintTalkParser();
		byte[] bytes = parser.parsePrintTalk(ptk, true); // No validation: BUG in JAXB Framework

		// transfer to target url
		URL u = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) u.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("CONTENT-TYPE", "application/zip");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		// write ZIP package to output stream
		String docName = xJdfModel.getJobId() + ".xjdf";

		PrintTalkPackager packager = new PrintTalkPackager(connection.getOutputStream());
		packager.packagePrintTalk(new PrintTalkNavigator(bytes), docName, null);

		// get response code
		int responseCode = connection.getResponseCode();

		if (responseCode != 200) {
			throw new ConnectException();
		}
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
		PrintTalkParser parser = new PrintTalkParser();
		byte[] bytes = parser.parsePrintTalk(ptk);

        // target location has to be a zip file
        String ext = FilenameUtils.getExtension(targetLocation);

        if(!FilenameUtils.isExtension(targetLocation, "zip")) {
            targetLocation += ".zip";
        }

		// save to target location
		File file = new File(targetLocation);
		OutputStream os = new FileOutputStream(file);

		if ("zip".equalsIgnoreCase(FilenameUtils.getExtension(targetLocation))) {

			// save as ZIP
			String docName = xJdfModel.getJobId() + ".xjdf";

			PrintTalkPackager packager = new PrintTalkPackager(os);
			packager.packagePrintTalk(new PrintTalkNavigator(bytes), docName, null);

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

		// prepare model
		if (StringUtils.isEmpty(xJdfModel.getJobId())) {
			xJdfModel.setJobId(IDGeneratorUtil.generateID("ID"));
		}

		// create print talk
		ProductBuilder productBuilder = new ProductBuilder(xJdfModel.getAmount());

		EnumSides sides = EnumSides.TWO_SIDED_HEAD_TO_HEAD;

		if (!StringUtils.isEmpty(xJdfModel.getMediaQuality())) // Media Quality
			productBuilder.addIntent(nf.createMediaIntent(xJdfModel.getMediaQuality()));

		if (!StringUtils.isEmpty(xJdfModel.getNumColors())) {

			// compute side
			String[] numColors = xJdfModel.getNumColors().split(" ");

			if ("0".equals(numColors[0])) {
				// one sided back
				sides = EnumSides.ONE_SIDED_BACK;

			} else if ("0".equals(numColors[1])) {
				// one sided front
				sides = EnumSides.ONE_SIDED;
			}

			// set num colors
			productBuilder.addIntent(nf.createColorIntent(new IntegerList(xJdfModel.getNumColors())));
		}

		if (xJdfModel.getFinishedDimensions() != null) {

			double x = DimensionUtil.mm2Dtp(xJdfModel.getFinishedDimensions().getX());
			double y = DimensionUtil.mm2Dtp(xJdfModel.getFinishedDimensions().getY());
			double z = DimensionUtil.mm2Dtp(xJdfModel.getFinishedDimensions().getZ());

			productBuilder.addIntent(nf.createLayoutIntent(xJdfModel.getPages(), sides, new Shape(x, y, z)));
		}

		Product product = productBuilder.build();

		XJdfBuilder xJdfBuilder = new XJdfBuilder(xJdfModel.getJobId(), "Web2Print", xJdfModel.getJobName());
		xJdfBuilder.addProduct(product);
		xJdfBuilder.addParameter(nf.createRunList(Paths.get(xJdfModel.getRunList()).toUri().toString()));

		if (!StringUtils.isEmpty(xJdfModel.getCatalogId())) // Catalog ID
			xJdfBuilder.addGeneralID(nf.createGeneralID("CatalogID", xJdfModel.getCatalogId()));

		if (!StringUtils.isEmpty(xJdfModel.getCustomerId())) // CustomerID
			xJdfBuilder.addParameter(nf.createCustomerInfo(xJdfModel.getCustomerId()));

		XJDF xjdf = xJdfBuilder.build();

		PrintTalkBuilder ptkBuilder = new PrintTalkBuilder();
		ptkBuilder.addRequest(ptkNf.createPurchaseOrder(xJdfModel.getJobId(), null, xjdf));
		PrintTalk ptk = ptkBuilder.build();

		// return PrintTalk
		return ptk;
	}

}
