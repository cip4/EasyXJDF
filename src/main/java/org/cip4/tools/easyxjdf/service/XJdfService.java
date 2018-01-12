/**
 * All rights reserved by
 * <p>
 * flyeralarm GmbH
 * Alfred-Nobel-Straße 18
 * 97080 Würzburg
 * <p>
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
import org.cip4.lib.xjdf.schema.*;
import org.cip4.lib.xjdf.type.IntegerList;
import org.cip4.lib.xjdf.type.Shape;
import org.cip4.lib.xjdf.util.DimensionUtil;
import org.cip4.lib.xjdf.util.IDGeneratorUtil;
import org.cip4.lib.xprinttalk.PrintTalkNodeFactory;
import org.cip4.lib.xprinttalk.builder.PrintTalkBuilder;
import org.cip4.lib.xprinttalk.schema.PrintTalk;
import org.cip4.lib.xprinttalk.xml.PrintTalkPackager;
import org.cip4.lib.xprinttalk.xml.PrintTalkParser;
import org.cip4.tools.easyxjdf.model.XJdfModel;

import javax.xml.bind.JAXBElement;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.file.Paths;

/**
 * Service class which provides all XJDF functionality.
 *
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
     *
     * @param xJdfModel The XJDF details.
     * @param url       The target URL.
     */
    public void send(XJdfModel xJdfModel, String url) throws Exception {

        // create PrintTalk Document
        PrintTalk ptk = createPrintTalk(xJdfModel);

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
        packager.packagePrintTalk(ptk, docName);

        // get response code
        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new ConnectException();
        }
    }

    /**
     * Generate XJDF Document and save in target location.
     *
     * @param xJdfModel      The XJDF details.
     * @param targetLocation The target location.
     * @throws Exception Is thrown in case an exception is occurred.
     */
    public void saveAs(XJdfModel xJdfModel, String targetLocation) throws Exception {

        // create PrintTalk Document
        PrintTalk ptk = createPrintTalk(xJdfModel);

        if (!FilenameUtils.isExtension(targetLocation, "zip")) {
            targetLocation += ".zip";
        }

        // save to target location
        File file = new File(targetLocation);
        OutputStream os = new FileOutputStream(file);

        if ("zip".equalsIgnoreCase(FilenameUtils.getExtension(targetLocation))) {

            // save as ZIP
            String docName = xJdfModel.getJobId() + ".ptk";

            PrintTalkPackager packager = new PrintTalkPackager(os);
            packager.packagePrintTalk(ptk, docName);

        } else {
            // parse print talk
            PrintTalkParser parser = new PrintTalkParser();
            byte[] bytes = parser.parsePrintTalk(ptk);

            // save as XJDF
            IOUtils.copy(new ByteArrayInputStream(bytes), os);
        }

        os.close();
    }

    /**
     * Creates an XJDF Document from the XJdfModel.
     *
     * @param xJdfModel The XJDF Model object.
     * @return XJDF Document.
     */
    private PrintTalk createPrintTalk(XJdfModel xJdfModel) throws URISyntaxException {

        // prepare model
        if (StringUtils.isEmpty(xJdfModel.getJobId())) {
            xJdfModel.setJobId(IDGeneratorUtil.generateID("ID"));
        }

        // create print talk
        ProductBuilder productBuilder = new ProductBuilder(xJdfModel.getAmount());

        Sides sides = Sides.TWO_SIDED_HEAD_TO_HEAD;

        if (!StringUtils.isEmpty(xJdfModel.getMediaQuality())) { // Media Quality
            MediaIntent mediaIntent = nf.createMediaIntent(xJdfModel.getMediaQuality());
            mediaIntent.setMediaType(MediaType.PAPER);
            productBuilder.addIntent(mediaIntent);
        }


        if (!StringUtils.isEmpty(xJdfModel.getNumColors())) {

            // compute side
            String[] numColors = xJdfModel.getNumColors().split(" ");

            if ("0".equals(numColors[0])) {
                // one sided back
                sides = Sides.ONE_SIDED_BACK;

            } else if ("0".equals(numColors[1])) {
                // one sided front
                sides = Sides.ONE_SIDED;
            }

            // set color intent
            ColorIntent colorIntent = nf.createColorIntent();
            productBuilder.addIntent(colorIntent);

            if (!"0".equals(numColors[0])) {
                SurfaceColor surfaceColorFront = nf.createSurfaceColor();
                surfaceColorFront.setSurface(Side.FRONT);

                if ("1".equals(numColors[0])) {
                    surfaceColorFront.getColorsUsed().add("Black");

                } else {
                    surfaceColorFront.getColorsUsed().add("Cyan");
                    surfaceColorFront.getColorsUsed().add("Magenta");
                    surfaceColorFront.getColorsUsed().add("Yellow");
                    surfaceColorFront.getColorsUsed().add("Black");
                }

                colorIntent.getSurfaceColor().add(surfaceColorFront);
            }

            if (!"0".equals(numColors[1])) {
                SurfaceColor surfaceColorBack = nf.createSurfaceColor();
                surfaceColorBack.setSurface(Side.BACK);

                if ("1".equals(numColors[1])) {
                    surfaceColorBack.getColorsUsed().add("Black");

                } else {
                    surfaceColorBack.getColorsUsed().add("Cyan");
                    surfaceColorBack.getColorsUsed().add("Magenta");
                    surfaceColorBack.getColorsUsed().add("Yellow");
                    surfaceColorBack.getColorsUsed().add("Black");
                }

                colorIntent.getSurfaceColor().add(surfaceColorBack);
            }
        }

        if (xJdfModel.getFinishedDimensions() != null) {

            double x = DimensionUtil.mm2Dtp(xJdfModel.getFinishedDimensions().getX());
            double y = DimensionUtil.mm2Dtp(xJdfModel.getFinishedDimensions().getY());
            double z = DimensionUtil.mm2Dtp(xJdfModel.getFinishedDimensions().getZ());

            LayoutIntent layoutIntent = nf.createLayoutIntent();
            layoutIntent.setPages(xJdfModel.getPages());
            layoutIntent.setSides(sides);
            layoutIntent.setFinishedDimensions(new Shape(x,y,z));
            productBuilder.addIntent(layoutIntent);
        }

        Product product = productBuilder.build();

        XJdfBuilder xJdfBuilder = new XJdfBuilder(xJdfModel.getJobId(), "Web2Print", xJdfModel.getJobName());
        xJdfBuilder.addProduct(product);

        URI uri = Paths.get(xJdfModel.getRunList()).toUri();
        String filename = FilenameUtils.getName(xJdfModel.getRunList());
        xJdfBuilder.addResource(nf.createRunList(new org.cip4.lib.xjdf.type.URI(uri, "asset/" + filename)));

        if (!StringUtils.isEmpty(xJdfModel.getCatalogId())) // Catalog ID
            xJdfBuilder.addGeneralID(nf.createGeneralID("CatalogID", xJdfModel.getCatalogId()));

        if (!StringUtils.isEmpty(xJdfModel.getCustomerId())) // CustomerID
            xJdfBuilder.addResource(nf.createCustomerInfo(xJdfModel.getCustomerId()));

        XJDF xjdf = xJdfBuilder.build();
        xjdf.getTypes().add("Product");

        PrintTalkBuilder ptkBuilder = new PrintTalkBuilder();
        ptkBuilder.addRequest(ptkNf.createPurchaseOrder(xJdfModel.getJobId(), null, xjdf));
        return ptkBuilder.build();
    }
}
