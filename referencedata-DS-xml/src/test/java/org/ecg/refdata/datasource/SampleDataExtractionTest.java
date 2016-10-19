package org.ecg.refdata.datasource;

import org.ecg.refdata.datasource.utils.Utils;
import org.ecg.refdata.datasource.utils.XMLRefReader;
import org.ecg.refdata.datasource.xml.ReferenceData;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Current class tests internal data extraction mechanism for sample data of XML
 * data source. Data provider can use both: embedded (from specific jar file)
 * and external (directory with xml files) data.
 * <p/>
 * Sample configuration of XML data provider:
 * <p/>
 * <CODE>
 * <bean id="datasourceXML"
 * class="org.ecg.refdata.datasource.xml.XMLFileDataSource" singleton="true">
 * <property name="filesSource" value="/org/ecg/refdata/defaultsXML"></property>
 * <property name="useEmbeddedData" value="true"></property>
 * </bean>
 * </CODE>
 *
 */
public class SampleDataExtractionTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @serial Path to files source.
     */
    private String filesSource = "/org/ecg/refdata/defaultsXML";

    private final static String XML_SUFFIX = "xml";

    @Test
    public void testDontUseEmbededData() {

        List<File> xmlFilesList = new LinkedList<File>();

        try {

            // if embedded data is set we look for the data fiels in jar file
            // in directory specified by filesSource
            if (true) {

                String jarPrefix = ".jar";
                String uniqueDir = "/tmpX45/";

                URI filesSourceURI = null;

                try {

                    filesSourceURI = this.getClass().getResource(
                            this.filesSource).toURI();

                } catch (NullPointerException npe) {
                    logger.info("Cannot open resource from location: "
                            + filesSource + " details: " + npe.getMessage());
                    return;
                }

                String pathToJarFile = filesSourceURI.toString().split(
                        jarPrefix)[0].substring(9)
                        + jarPrefix;

                String serverDict = pathToJarFile.substring(0, pathToJarFile
                        .length()
                        - jarPrefix.length());

                String tmpDir = serverDict + uniqueDir;

                try {

                    Utils.unzipFiles(pathToJarFile, filesSource, tmpDir);
                    this.filesSource = tmpDir + this.filesSource.substring(1);

                } catch (Exception e) {

                    // note that if unzipping is not successful it may means we
                    // are running directly from eclipse/maven without zipped
                    // data
                    filesSource = this.getClass().getResource(this.filesSource)
                            .getPath();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        File dir = new File(filesSource);

        logger.info("Reading xml files from dir: " + dir.getAbsolutePath());

        File[] files = dir.listFiles();

        if (files != null) {

            for (File file : files) {

                if (file.isFile()) {

                    String filename = file.getAbsolutePath();

                    if (filename.endsWith(XML_SUFFIX)) {
                        xmlFilesList.add(file);
                    }

                }

            }

        }

        // Add dictionaries from xmlFilesList to map of ReferenceData
        for (File file : xmlFilesList) {

            ReferenceData referenceData = getReferenceDataFromFile(file);

            if (referenceData != null) {
                assertTrue(referenceData.getId() != null);
            }

        }

        assertTrue(xmlFilesList.size() != 0);

        logger.info("Test complette");

    }

    /**
     * Method gets proper <CODE>ReferenceData</CODE> from related XML data
     * source files.
     *
     * @param file
     * @return
     */
    private ReferenceData getReferenceDataFromFile(File file) {
        String shortFileName = "..."
                + file.getAbsolutePath().substring(
                        file.getAbsolutePath().length() / 2);
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(
                    "org.ecg.refdata.datasource.xml", getClass()
                    .getClassLoader());

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            logger.info("Start unmarshalling file: ..." + shortFileName);

            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setValidating(true);
            SAXParser saxParser = spf.newSAXParser();

            XMLReader xmlReader = new XMLRefReader(saxParser.getXMLReader());

            FileInputStream fileInputStrem = new FileInputStream(file);
            SAXSource source = new SAXSource(xmlReader, new InputSource(
                    fileInputStrem));

            ReferenceData referenceData = (ReferenceData) unmarshaller
                    .unmarshal(source);

            logger.info("Unmarshalling file completed: " + referenceData
                    + ", msgId="
                    + referenceData.getId());

            return referenceData;

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Could not read data from: " + shortFileName
                + " - all data in this file will be ignored");

        return null;

    }

}
