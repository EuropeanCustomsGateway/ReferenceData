package org.ecg.refdata.datasource.xml;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.beanutils.BeanUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.datasource.xml.ReferenceData.NameAndDescription;
import org.ecg.refdata.datasource.xml.ReferenceData.SimpleItem;
import org.ecg.refdata.datasource.xml.ReferenceData.SimpleItem.CodeDescription;
import org.ecg.refdata.datasource.utils.Utils;
import org.ecg.refdata.datasource.utils.XMLRefReader;
import org.ecg.refdata.exceptions.IncorrectParameterException;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.QueryResult;
import org.ecg.refdata.query.impl.QueryResultImpl;
import org.ecg.refdata.query.model.impl.SimpleItemImpl;
import org.ecg.refdata.utils.Validator;
import java.io.InputStream;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * Current class consist of methods which provides access to XML files which
 * represents reference data.
 *
 */
public class XMLFileDataSource implements ReferenceDataSource {

    private static final long serialVersionUID = -567067173470958457L;
    private final static String XML_SUFFIX = "xml";
    private final static String DEFAULT_LANGUAGE_CODE = "EN";

    /**
     * @serial Path to files source
     */
    private String filesSource;

    /**
     * @serial Parameter which allows get XML files from jar file or specific
     * localization
     */
    private boolean useEmbeddedData;
    /**
     * @serial Default language code
     */
    private String defaultLanguageCode = DEFAULT_LANGUAGE_CODE;

    /**
     * @serial Dictionary id to reference data values
     */
    private Map<String, ReferenceData> mapReferenceData = null;

    /**
     * @return the mapReferenceData
     */
    public Map<String, ReferenceData> getMapReferenceData() {
        return mapReferenceData;
    }

    private static Logger logger = Logger.getLogger(XMLFileDataSource.class
            .getName());

    /**
     * Method sets input directory which suppose to contain all input XML files
     *
     * @param filesSource the filesSource to set
     */
    public void setFilesSource(String filesSource) {
        this.filesSource = filesSource;
    }

    /**
     * @param useEmbeddedData the useEmbeddedData to set
     */
    public void setUseEmbeddedData(boolean useEmbeddedData) {
        this.useEmbeddedData = useEmbeddedData;
    }

    /**
     * Initialize reference data map from files in set filesSource
     */
    public void initializeReferenceDataMap()
            throws ReferenceDataSourceInternalException {

        if (mapReferenceData != null) {
            logger.info("XMLDataSource initialized");
            return;
        }

        List<File> xmlFilesList = new LinkedList<File>();
        try {
            // if embedded data is set we look for the data fiels in jar file
            // in directory specified by filesSource
            if (useEmbeddedData) {
                String jarPrefix = ".jar";
                String uniqueDir = "/tmpX45/";
                URI filesSourceURI = this.getClass().getResource(
                        this.filesSource).toURI();

                //FIXME getPath?? - to avoid any problems with character conversion
                String pathToJarFile = filesSourceURI.toString()
                        .split(jarPrefix)[0].substring(9)
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
            logger.log(Level.SEVERE,
                    "A problem occurred while trying access resources: "
                    + filesSource, e);
        }

        mapReferenceData = new HashMap<String, ReferenceData>();
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

                mapReferenceData.put(referenceData.getId(), referenceData);
            }

        }
    }

    /**
     * JAXB Unmarshaller from input
     *
     * @param string file name
     */
    public static ReferenceData getReferenceDataFromFile(File file) {
        String shortFileName = "..."
                + file.getAbsolutePath().substring(
                        file.getAbsolutePath().length() / 2);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    "org.ecg.refdata.datasource.xml", XMLFileDataSource.class
                    .getClassLoader());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setEventHandler(new ValidationEventHandler() {
                @Override
                public boolean handleEvent(ValidationEvent event) {
                    System.out.println("event");
                    return true;
                }
            });

//			logger.info("Start unmarshalling file: ..." + shortFileName);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setValidating(false);
            SAXParser saxParser = spf.newSAXParser();

            XMLReader xmlReader = new XMLRefReader(saxParser.getXMLReader());

            FileInputStream fileInputStrem = new FileInputStream(file);
            SAXSource source = new SAXSource(xmlReader, new InputSource(
                    fileInputStrem));

            ReferenceData referenceData = (ReferenceData) unmarshaller
                    .unmarshal(source);
//			logger
//					.info("Unmarshalling file completed: " + referenceData.getItemType()
//							+ ", msgId="
//							+ referenceData.getId());
            return referenceData;
        } catch (JAXBException e) {
            logger.log(Level.INFO, "Problem with JAXB parser.", e);
        } catch (Exception e) {
            logger.log(Level.INFO, "Cannot unmarshall file.", e);
            e.printStackTrace();
        }
        logger.warning("Could not read data from: " + shortFileName
                + " - all data in this file will be ignored");
        return null;
    }

    /**
     * JAXB Unmarshaller from input
     *
     * @param string file name
     */
    public static ReferenceData getReferenceDataFromStream(InputStream stream) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    "org.ecg.refdata.datasource.xml", XMLFileDataSource.class
                    .getClassLoader());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setValidating(true);
            SAXParser saxParser = spf.newSAXParser();

            XMLReader xmlReader = new XMLRefReader(saxParser.getXMLReader());

            SAXSource source = new SAXSource(xmlReader, new InputSource(
                    stream));

            ReferenceData referenceData = (ReferenceData) unmarshaller
                    .unmarshal(source);
//			logger
//					.info("Unmarshalling stream completed: " + referenceData.getItemType()
//							+ ", msgId="
//							+ referenceData.getId());
            return referenceData;
        } catch (JAXBException e) {
            logger.log(Level.INFO, "Problem with JAXB parser.", e);
        } catch (Exception e) {
            logger.log(Level.INFO, "Cannot unmarshall stream.", e);
            e.printStackTrace();
        }

        return null;
    }

    // /**
    // * Method returns <code>DictionaryInfo</code> instance which contains
    // useful
    // * information about current dictionary (name, description, modification
    // * date etc.).
    // *
    // * @see
    // org.ecg.refdata.ConcreteRefDataSource#getDictionaryInfo(java.lang.String)
    // */
    // public DictionaryInfo getDictionaryInfo(String dictionaryId,
    // String languageCode) throws NoSuchDictionaryException {
    //
    // // we will not use xml data source anyway
    // return new DictionaryInfoImpl("dictName", "dictDesc", new Date(), 1);
    //
    // }
    /**
     * Method returns complete set of information regarding specific dictionary.
     * The <code>QueryResult</code> instance consist of language code, list of
     * dictionary items, and the nuber of start element on the list.
     *
     * @throws ReferenceDataSourceInternalException
     *
     * @see org.ecg.refdata.ConcreteRefDataSource#getItemsList(java.lang.String,
     * java.lang.String, java.lang.String, java.util.Date, java.lang.String,
     * int, int)
     */
    public QueryResult getItemsList(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode,
            int itemsStart, int itemsCount, String metadata, boolean strictValue) throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {

        boolean startsWith = true;
        QueryResultImpl queryResult = this.getItems(dictionaryId, column,
                value, refDataDate, languageCode, itemsStart, itemsCount,
                startsWith, strictValue);
        return queryResult;
    }

    /**
     * Method returns an unique item for required input parameters
     *
     * @throws NoSuchDictionaryException
     * @throws ReferenceDataSourceInternalException
     *
     * @see
     * org.ecg.refdata.ConcreteRefDataSource#getUniqueItem(java.lang.String,
     * java.lang.String, java.lang.String, java.util.Date, java.lang.String)
     */
    public QueryResult getUniqueItem(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode, String metadata, boolean strictValue)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {

        boolean startsWith = false;
        QueryResultImpl queryResult = this.getItems(dictionaryId, column,
                value, refDataDate, languageCode, 0, 1, startsWith, strictValue);
        return queryResult;
    }

    /**
     * Method returns instance of <code>QueryResultImpl</code> class which
     * contains dictionary items for specific input requirements.
     *
     * @param dictionaryId a dictionary id
     * @param column column name
     * @param value value of particular column in database
     * @param refDataDate date of reference data
     * @param languageCode language code
     * @param itemsStart points out the place where the current list begin
     * @param itemsCount stands how many items is on the input list
     * @param startsWith flag decides if "value" will be treated as starWith
     * condition or equal.
     * @return an instance of <code>QueryResultImpl</code>
     * @throws ReferenceDataSourceInternalException
     * @throws NoSuchDictionaryException
     */
    private QueryResultImpl getItems(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode,
            int itemsStart, int itemsCount, boolean startsWith, boolean strictValue)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {

        if (dictionaryId == null) {
            logger.warning("DictionaryID is null");
            throw new IncorrectParameterException(
                    "Incorrect parameter: dictionaryId cannot be null");
        }

        if (Validator.isColumnNameNullOrEmpty(column)) {
            logger.warning("Column isn't correctly defined, value: " + column
                    + " is incorrect");
            throw new IncorrectParameterException(
                    "Incorrect parameter: search column name cannot be null");
        }

        if (languageCode == null) {
            logger.warning("Language code isn't defined");
            throw new IncorrectParameterException(
                    "Incorrect parameter: language code name cannot be null");
        }

        if (mapReferenceData == null) {
            initializeReferenceDataMap();
        }

        if (mapReferenceData == null) {
            logger
                    .warning("There is no ReferenceData for elements from resource: "
                            + this.filesSource);
            throw new ReferenceDataSourceInternalException(
                    "There is no ReferenceData for elements from resource: "
                    + this.filesSource);
        }

        ReferenceData referenceData = mapReferenceData.get(dictionaryId);

        if (referenceData == null) {
            logger.warning("Dictionary number: " + dictionaryId
                    + " doesn't exit, aviable"
                    + "\naviable dictionaries count: "
                    + mapReferenceData.keySet().size()
                    + "\naviable dictionaries: " + mapReferenceData.keySet());

            throw new NoSuchDictionaryException(dictionaryId);
        }

        List<DictionaryItem> items = new ArrayList<DictionaryItem>();

        List<SimpleItem> listSimpleItem = referenceData.getSimpleItem();
        List<NameAndDescription> listNameAndDescription = referenceData.getNameAndDescription();
        NameAndDescription nameAndDescription = this
                .getNameAndDescriptionFromList(listNameAndDescription,
                        languageCode);

        if (nameAndDescription == null) {
            logger.severe("Current ReferenceData is not completed");
            nameAndDescription = new NameAndDescription();
            nameAndDescription.setDescription("description");
            nameAndDescription.setName("name");
            nameAndDescription.setLanguageCode(defaultLanguageCode);
            languageCode = defaultLanguageCode;
        }
        languageCode = nameAndDescription.getLanguageCode();

        String columnValue = null;
        for (SimpleItem simpleItem : listSimpleItem) {

            try {
                columnValue = BeanUtils.getProperty(simpleItem, column);
            } catch (Exception e) {
                logger.severe("Column name with name: " + column
                        + " doesn't exist, using default code");
                columnValue = "code";
            }

            if (Utils.compareDatesXML(simpleItem.getValidFrom(), refDataDate,
                    simpleItem.getValidTo())) {
                boolean hasFound = false;
                if (value == null) {
                    hasFound = true; // TODO: make sure it should mean that we
                    // don't care if it is null
                } else if (startsWith) {
                    hasFound = columnValue.startsWith(value);
                } else {
                    hasFound = columnValue.equals(value);
                }
                if (hasFound) {
                    List<CodeDescription> listCodeDesctiption = simpleItem
                            .getCodeDescription();
                    for (CodeDescription codeDescription : listCodeDesctiption) {
                        if (codeDescription.getLanguageCode().equals(
                                languageCode)) {
                            items.add(new SimpleItemImpl(columnValue,
                                    codeDescription.getDescription()));
                        }
                    }
                }
            }
        }

        int numberOfElemnets = items.size();

        if (itemsStart >= numberOfElemnets) {
            itemsStart = numberOfElemnets - 1;
        }
        if (itemsStart < 0) {
            itemsStart = 0;
        }
        if (itemsStart + itemsCount > numberOfElemnets) {
            itemsCount = numberOfElemnets - itemsStart;
        }

        // instead of sublist (which is not serializable) i recommend to use
        // following code
        List<DictionaryItem> tempList = new ArrayList<DictionaryItem>();

        for (int i = 0; i < items.size(); i++) {

            if (i < itemsStart || i >= itemsStart + itemsCount) {
                continue;
            } else {
                tempList.add(items.get(i));
            }
        }

        // List<DictionaryItem> itemsPart = Collections.unmodifiableList(items
        // .subList(itemsStart, itemsStart + itemsCount));
        List<DictionaryItem> itemsPart = Collections.unmodifiableList(tempList);

        return new QueryResultImpl(dictionaryId, nameAndDescription.getName(),
                nameAndDescription.getDescription(), refDataDate,
                numberOfElemnets, languageCode, column, itemsPart, itemsStart);
    }

    /**
     * Find NameAndDescription object for language code
     *
     * @param listNameAndDescription
     * @param languageCode
     * @return
     */
    private NameAndDescription getNameAndDescriptionFromList(
            List<NameAndDescription> listNameAndDescription,
            String localLanguageCode) {

        NameAndDescription nameAndDescription = null;
        NameAndDescription defaultNameAndDescription = null;

        ListIterator<NameAndDescription> listIterator = listNameAndDescription
                .listIterator();

        while (listIterator.hasNext() && nameAndDescription == null) {
            NameAndDescription tempNameAndDescription = listIterator.next();
            if (tempNameAndDescription.getLanguageCode().equals(
                    localLanguageCode)) {
                nameAndDescription = tempNameAndDescription;
            }
            if (tempNameAndDescription.getLanguageCode().equals(
                    defaultLanguageCode)) {
                defaultNameAndDescription = tempNameAndDescription;
            }
        }

        if (nameAndDescription != null) {
            return nameAndDescription;
        } else {
            logger.warning("Missing NameAndDescription for language: "
                    + localLanguageCode);
        }
        if (defaultNameAndDescription != null) {
            logger.warning("Default NameAndDesctiprion is set for language: "
                    + defaultLanguageCode);
            return defaultNameAndDescription;
        } else {
            logger.warning("Missing NameAndDescription for defalutlanguage: "
                    + defaultLanguageCode);
        }
        return null;
    }

    // FIXME: make it properly
    public int testDataProvider(String id, String columnName) {

        return 0;
    }

    public Boolean isValid(String dictionaryId, String column, String value, Date refDataDate, String metadata, boolean strictValue)
            throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {
        QueryResult item = getUniqueItem(dictionaryId, column, value, refDataDate, defaultLanguageCode, metadata, strictValue);
        if (item == null) {
            return false;
        }

        if (item.getItems().isEmpty()) {
            return false;
        }

        return true;

    }

}
