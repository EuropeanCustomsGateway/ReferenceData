package org.ecg.refdata.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.ecs.html.Div;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.QueryResult;
import org.ecg.refdata.utils.MessageBoundle;

/**
 * Servlet provides layer for connection with business logic of whole
 * application.
 *
 * List of configurable parameters for current servlet:
 *
 * If no "referenceDataFactoryConfigString" is defined in description system
 * will load default configuration of data source and will try to connect with
 * external EJB component to get a data.
 *
 * Reference data factory configuration for EJB factory:
 *
 * - "referenceDataFactoryClass" contains proper name of EJB facade factory
 * class eg. org.ecg.refdata.ejb.EJBFactory,
 *
 * - "referenceDataFactoryConfigString" contains configuration of JNDI for
 * communication layer between web and EJB layer (format myHost:port/jndiName)
 * eg. "veryImportantServer:1099/significant-ear/sampleBean/remote" without
 * "jnp://" prefix.
 *
 * Reference data factory configuration for Spring factory:
 *
 * - "referenceDataFactoryClass" contains proper name of Spring facade factory
 * class eg. org.ecg.refdata.ejb.SpringFactory,
 *
 * - "referenceDataFactoryConfigString" contains path to xml bean file config
 * eg. /org/ecg/refdata/spring-beans.xml,
 *
 * Example configuration from web.xml:
 *
 * <context-param> <param-name>referenceDataFactoryClass</param-name>
 * <param-value>org.ecg.refdata.ejb.ReferenceDataEJBFacadeFactory</param-value>
 * </context-param> <context-param>
 * <param-name>referenceDataFactoryConfigString</param-name>
 * <param-value>localhost:1099/ReferenceDataSBBean/remote</param-value>
 * </context-param>
 *
 *
 */
public class RefDataServlet extends HttpServlet {

    private static final long serialVersionUID = 6356245234544L;

    // LoupeCreator needs it
    private static String DEFAULT_REFERENCE_DATA_SERVLET_NAME = "RefData";

    private static Logger logger = Logger.getLogger(RefDataServlet.class.getName());

    private static MessageBoundle messageBoundle = new MessageBoundle("referencedata");
    /**
     * @serial Reference to {@link ReferenceDataSource}
     */
    private ReferenceDataSource referenceDataSource;
    /**
     * @serial Reference to {@link OutputGenerator}
     */
    private OutputGenerator outputGenerator = new OutputGenerator();

    @Override
    public void init() throws ServletException {
        RefDataHolder.configureDataFactory(getServletContext(), this);
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            process(request, response);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot generate output.", e);
            response.reset();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            process(request, response);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot generate output.", e);
            response.reset();
        }
    }

    /**
     * System supports eighter POST or GET requests from AJAX. Servlet redirects
     * control to current method
     */
    private void process(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {

        String resources = request.getParameter("resources");
        Locale locale = (Locale) request.getSession().getAttribute(
                org.apache.struts.Globals.LOCALE_KEY);

        // if resource parameter was set it means we want to get localized
        // resources not loupe with data
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        if (resources != null) {
            logger.finer("Generating js locale boundle for: " + locale);
            StringBuilder sb = processLocaleMessages(locale);
            response.getWriter().append(sb);
        } else {
            processLoupe(request, response);
        }
    }

    /**
     * This function gets input information about localization and return string
     * in javascript format for reference data support
     *
     * @param locale - input localization
     * @return string with javascript generator
     */
    private StringBuilder processLocaleMessages(Locale locale) {
        StringBuilder sbTemp = new StringBuilder();
        String[] keys = {"loading", "error_loading", "error_field",
            "search_len", "error_dictionary", "error_connection", "error_result"};

        if (locale == null) {
            locale = new Locale("en", "EN");
        }

        logger.finer("Processing locale messages for locale: " + locale);

        sbTemp.append("var __refDataCCT__infoText = {");
        for (String key : keys) {
            sbTemp.append(key + ":\"" + messageBoundle.getString(locale, key)
                    + "\",");
        }
        sbTemp.append("};");

        return sbTemp;
    }

    /**
     * System supports eighter POST or GET requests from AJAX. Servlet redirects
     * control to current method
     */
    private void processLoupe(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {

        if (request.getSession() == null || request.getSession().isNew()) {
            return;
        }

        String dictionaryId = request.getParameter("dictionaryId");
        String metadata = request.getParameter("metadata");
        String searchColumn = request.getParameter("column");
        String value = request.getParameter("value");
        //ECG-323 non-latin chars don't work with GET method
        //getCharacterEncoding is UTF-8 but chars are encoded in
        //default ISO-8859-1
        if (value != null) {
            value = new String(value.getBytes("8859_1"), "UTF-8");
        }
        String sDate = request.getParameter("date");
        String languageCode = request.getParameter("languageCode");
        String columns = "null".equalsIgnoreCase(request.getParameter("columns")) ? null : request.getParameter("columns");
        String sItemsStart = request.getParameter("itemsStart");
        String sItemsOnPage = request.getParameter("itemsOnPage");
        String sDisplayOnly = request.getParameter("displayOnly");
        boolean strictValue = Boolean.valueOf(request.getParameter("strictValue"));

        // It allows to take country code form pattern "xx_XX" e.g. "en_EN"
        String[] languageCodeArray = languageCode.split("_");
        if (languageCodeArray.length == 2) {
            languageCode = languageCodeArray[1].toUpperCase();
        } else {
            languageCode = languageCodeArray[0].toUpperCase();
        }

        StringBuilder sb = new StringBuilder(
                "Reference servlet query: dictionaryId=");
        sb.append(dictionaryId);
        sb.append(", column=");
        sb.append(searchColumn);
        sb.append(", metadata");
        sb.append(metadata);
        sb.append(", value=");
        sb.append(value);
        sb.append(", date=");
        sb.append(sDate);
        sb.append(", languageCode=");
        sb.append(languageCode);
        sb.append(", columns=");
        sb.append(columns);
        sb.append(", searchColumns=");
        sb.append(searchColumn);
        sb.append(", itemsStart=");
        sb.append(sItemsStart);
        sb.append(", itemsOnPage=");
        sb.append(sItemsOnPage);
        sb.append(", displayOnly=");
        sb.append(sDisplayOnly);
        logger.finer(sb.toString());

        // parse date
        Date date;
        try {
            date = sDate != null ? new Date(Long.parseLong(sDate)) : null;
        } catch (Exception e) {
            logger.info("Cannot parse reference data date. " + e.getMessage());
            generateDivInfo(response.getOutputStream(), request, "error_taglib");
            return;
        }

        // parse start index
        int itemsStart;
        try {
            itemsStart = sItemsStart != null ? Integer.parseInt(sItemsStart)
                    : 0;
        } catch (Exception e) {
            logger.info("Cannot parse reference data start index. "
                    + e.getMessage());
            generateDivInfo(response.getOutputStream(), request, "error_taglib");
            return;
        }

        // parse items count
        int itemsOnPage;
        try {
            itemsOnPage = sItemsOnPage != null ? Integer.parseInt(sItemsOnPage)
                    : 0;
        } catch (Exception e) {
            logger.info("Cannot parse reference data page size. "
                    + e.getMessage());
            generateDivInfo(response.getOutputStream(), request, "error_taglib");
            return;
        }

        // parse if display mode
        boolean displayOnly;
        try {
            displayOnly = sDisplayOnly != null ? Boolean
                    .parseBoolean(sDisplayOnly) : false;
        } catch (Exception e) {
            logger.info("Cannot parse reference data display mode. "
                    + e.getMessage());
            generateDivInfo(response.getOutputStream(), request, "error_taglib");
            return;
        }

        if (itemsStart < 0) {
            itemsStart = 0;
        }

        QueryResult queryResult;
        try {

            referenceDataSource = RefDataHolder.getReferenceDataSource();

            queryResult = referenceDataSource.getItemsList(dictionaryId,
                    searchColumn, value, date, languageCode, itemsStart,
                    itemsOnPage, metadata, strictValue);

            if (queryResult == null) {
                logger.warning("Query Result for dictionary id: "
                        + dictionaryId + " is null");
                throw new ReferenceDataSourceInternalException(
                        "Null result returned");
            }

            logger.info("Query result, totalCount="
                    + queryResult.getTotalCount() + ", items.size="
                    + queryResult.getItems().size() + ", itemsStart="
                    + queryResult.getItemsStart());

            /**
             * Set columns names to be displayed in a table
             */
            String[] columnsList;
            // if columns are not set in taglib - use defult settings from
            // query results
            if (GenericValidator.isBlankOrNull(columns)) {
                // override columns list
                logger
                        .finest("No columns defined in tag, using defaults from queryResult: "
                                + Arrays.toString(queryResult.getValidColumns()));
                columnsList = queryResult.getValidColumns();
                if (columnsList != null) {
                    if (Arrays.asList(columnsList).contains("invalidForUE") || Arrays.asList(columnsList).contains("invalidForCountries")) {
                        columnsList = new String[]{"type", "grn", "number", "accessCode"};
                    }
                }
            } else {
                // by default columns in taglib are concatenated with ','
                columnsList = columns.split(",");
            }
            if (itemsOnPage <= 0) {
                itemsOnPage = queryResult.getItemsOnPage();
            }
            /**
             * render output
             */
            outputGenerator.generate(new RefDataPageContext(this,
                    getServletContext(), request, response), response.getOutputStream(),
                    queryResult, searchColumn, columnsList, itemsOnPage,
                    displayOnly);

        } catch (ReferenceDataSourceInternalException e) {
            logger
                    .log(Level.SEVERE, "Internal exception: " + e.getMessage(),
                            e);
            generateDivInfo(response.getOutputStream(), request, "internal_error",
                    languageCodeArray);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        } catch (NoSuchDictionaryException e) {
            logger.severe("Cannot find dictionary for id: " + dictionaryId);
            if (dictionaryId != null && dictionaryId.startsWith("grn")) {
                //no dictionary with grn defined
                generateDivInfo(response.getOutputStream(), request, "error_dictionary_grn",
                        dictionaryId);
            } else if (dictionaryId != null && dictionaryId.startsWith("materials")) {
                //no dictionary with materials defined
                generateDivInfo(response.getOutputStream(), request, "error_dictionary_materials",
                        dictionaryId);
            } else if (dictionaryId != null && dictionaryId.startsWith("trader")) {
                //no dictionary with trader defined
                generateDivInfo(response.getOutputStream(), request, "error_dictionary_trader",
                        dictionaryId);
            } else {
                generateDivInfo(response.getOutputStream(), request, "error_dictionary",
                        dictionaryId);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

    }

    /**
     * Returns a name of servlet from related web.xml descriptor
     *
     * @return default reference to data servlet name
     */
    public static String getDefaultServletName() {
        return RefDataServlet.DEFAULT_REFERENCE_DATA_SERVLET_NAME;
    }

    public void generateDivInfo(OutputStream outputSteam,
            HttpServletRequest request, String text, String... parameter)
            throws UnsupportedEncodingException, IOException {
        Locale locale = (Locale) request.getSession().getAttribute(
                org.apache.struts.Globals.LOCALE_KEY);
        if (locale == null) {
            locale = new Locale("pl");
        }
        String textRef = messageBoundle.getString(locale, text);
        if (parameter != null && parameter.length > 0) {
            textRef = textRef.replaceAll("[0]", parameter[0]);
        }
        Div mainDivRelInfo = new Div();
        mainDivRelInfo.setCodeSet("UTF-8");
        mainDivRelInfo.setStyle("position: relative; width: auto;");
        Div mainInfo = new Div();
        mainInfo.setCodeSet("UTF-8");
        mainInfo
                .setStyle("position: absolute;  width: 180px; background-color:#EEE; padding: 0; border: 1px solid #111; display:block; z-index: 101");
        mainInfo
                .setTagText("<table style='font-size:8pt;' border='0' width='100%'><tr><td onclick='RefDataSupport.closeNewDiv();'><img src='fw/refdata/image/bcls.gif'></td><td width='100%'>"
                        + textRef + "</td></tr></table>");
        mainDivRelInfo.addElement(mainInfo);
        outputSteam.write(mainDivRelInfo.toString("UTF-8")
                .getBytes("UTF-8"));
    }
}
