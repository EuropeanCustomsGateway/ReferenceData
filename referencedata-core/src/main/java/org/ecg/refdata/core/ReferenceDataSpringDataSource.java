package org.ecg.refdata.core;

import org.ecg.refdata.IDictionaryConfig;
import org.ecg.refdata.IReferenceDataConfig;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.QueryResult;
import org.ecg.refdata.utils.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class represents proxy object which gets data from all data sources (which
 * configurations are available in spring-beans.xml configuration file).
 * Constructor takes just one argument <CODE>IReferenceDataConfig</CODE> which
 * represents mapped object from spring configuration of data sources and
 * dictionary settings.
 */
public class ReferenceDataSpringDataSource implements ReferenceDataSource {

    private static final long serialVersionUID = -4345274187416252343L;

    private static final String defaultLanguageCode = "en";

    /**
     * Base mapping for all dictionary id to referenceDataSourceDS that handle
     * dictionary information and data
     *
     * @serial Map of IDictionaryConfig
     */
    private Map<String, IDictionaryConfig> dictionaryRedirect = new HashMap<String, IDictionaryConfig>();

    private static final Log logger = LogFactory.getLog(ReferenceDataSpringDataSource.class);

    /**
     * Constructor takes <CODE>IReferenceDataConfig</CODE> object which contain
     * all information from Spring configuration file.
     *
     * @param refData an instance of <code>IReferenceDataConfig</code> class
     */
    public ReferenceDataSpringDataSource(IReferenceDataConfig refData) {

        // simply initialize internal map with data taken from
        // IReferenceDataConfig
        this.dictionaryRedirect = refData.getDictionaryConfigMap();
        logger.info("Following dictionaries are configured: "
                + dictionaryRedirect.keySet().toString());

    }

    /**
     * Returns information about items from a dictionary. All items are returned
     * for which property starts with a specified value.
     *
     * @param dictionaryId dictionary id
     * @param column property name
     * @param value property value
     * @param refDataDate reference data date
     * @param languageCode language code
     * @param itemsStart first item index
     * @param itemsCount how many items to retrieve
     * @return query result
     * @throws NoSuchDictionaryException Cannot find dictionary with specified
     * id.
     * @throws ReferenceDataSourceInternalException
     */
    public QueryResult getItemsList(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode,
            int itemsStart, int itemsCount, String metadata, boolean strictValue) throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {

//		logger.info("getItemsList called for dictionary id: " + dictionaryId);
//		if (logger.isLoggable(Level.FINER)) {
//			logger.finer("Parameters of getItemsList : " + dictionaryId + ", "
//					+ column + ", " + value + ", " + refDataDate + ", "
//					+ languageCode + ", " + column + ", " + itemsStart + ", ");
//
//		}
        IDictionaryConfig config = getDictionaryConfig(dictionaryId);
        if (Validator.isColumnNameNullOrEmpty(column)) {
            column = config.getDefSearchColumn();
        }
        if (languageCode == null || languageCode.trim().length() == 0) {
            languageCode = defaultLanguageCode;
        }
        if (itemsCount <= 0) {
            itemsCount = config.getDefItemsOnPage();
        }

//        logger.info(" getItemsList(" +dictionaryId+ ", " + column+ ", " + value+ ", " + refDataDate+ ", " + languageCode+ ", " + itemsStart+ ", " + itemsCount+"); ");
        long start = System.nanoTime();
        QueryResult result = getReferenceDataSource(dictionaryId).getItemsList(
                dictionaryId, column, value, refDataDate, languageCode,
                itemsStart, itemsCount, metadata, strictValue);
//        logger.info(" getUniqueItem(\"" +dictionaryId+ "\", \"" + column+ "\", \"" + value+ "\", \"" + refDataDate+ "\", \"" + languageCode+ "\""+ ", \"" + itemsStart+ "\", \"" + itemsCount+"\"); // executed in " + (((double)(System.nanoTime() - start)) / 1000000)+" ms");
        propagateDefaultsDictionarySettingToResult(result, dictionaryId);
        return result;

    }

    /**
     * Returns information about an item from a dictionary. Single item is
     * returned for which property matches exactly specified value.
     *
     * @param dictionaryId dictionary id
     * @param column property name
     * @param value property value
     * @param refDataDate reference data date
     * @param languageCode language code
     * @return query result The returned <code>QueryResult</code> object
     * contains list of items in dictionary, language code, and number which
     * indicates first item on the list.
     * @throws NoSuchDictionaryException Cannot find dictionary with specified
     * id.
     * @throws ReferenceDataSourceInternalException
     */
    public QueryResult getUniqueItem(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode, String metadata, boolean strictValue)
            throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {

//		logger.info("getUniqueItem called for dictionary id: " + dictionaryId);
//		if (logger.isLoggable(Level.FINER)) {
//			logger.finer("Parameters of getUniqueItem: " + dictionaryId + ", "
//					+ column + ", " + value + ", " + refDataDate + ", "
//					+ languageCode + ", " + column + ", ");
//
//		}
        IDictionaryConfig config = getDictionaryConfig(dictionaryId);
        if (Validator.isColumnNameNullOrEmpty(column)) {
            column = config.getDefSearchColumn();
        }
        if (languageCode == null || languageCode.trim().length() == 0) {
            languageCode = defaultLanguageCode;
        }

//        long start = System.nanoTime();
        QueryResult result = dictionaryRedirect.get(dictionaryId)
                .getRefDataSource().getUniqueItem(dictionaryId, column, value,
                        refDataDate, languageCode, metadata, strictValue);

//        logger.info(" getUniqueItem(\"" +dictionaryId+ "\", \"" + column+ "\", \"" + value+ "\", \"" + refDataDate+ "\", \"" + languageCode+ "\"); // executed in " + (((double)(System.nanoTime() - start)) / 1000000)+" ms");
        propagateDefaultsDictionarySettingToResult(result, dictionaryId);
        return result;
    }

    /**
     * Fill query results with additional information take from dictionary
     * configuration
     *
     * @param result query result to be filled in
     * @throws NoSuchDictionaryException
     */
    private void propagateDefaultsDictionarySettingToResult(QueryResult result,
            String reqId) throws NoSuchDictionaryException {

        IDictionaryConfig config = null;

        if (result == null) {

            // config = getDictionaryConfig(reqId);
            // we shouldn't even send a config in case of any problems
        } else {

            config = getDictionaryConfig(result.getDictionaryId());

            result.setValidColumns(config.getPreferredColumns());
            result.setMinTextSearchLength(config.getDefMinTextSearchLength());
            result.setItemsOnPage(config.getDefItemsOnPage());

        }

    }

    /**
     * Returns ReferenceDataSourceDS for the given dictionaryId, throws
     * NoSuchDictionaryException
     *
     * @param dictionaryId id of the dictionary for which ReferenceDataSourceDS
     * is requested
     * @return ReferenceDataSourceDS for the given dictionaryId,
     * @throws NoSuchDictionaryException throw if no ReferenceDataSourceDS is
     * configured for requested dictionaryId
     */
    private ReferenceDataSource getReferenceDataSource(String dictionaryId)
            throws NoSuchDictionaryException {
        IDictionaryConfig config = getDictionaryConfig(dictionaryId);
        if (config != null) {
            ReferenceDataSource referenceDataSource = config.getRefDataSource();
            if (referenceDataSource != null) {
                return referenceDataSource;
            } else {

            }
        }
        logger.error("No dictionary configured for  id: " + dictionaryId);
        throw new NoSuchDictionaryException(dictionaryId);
    }

    /**
     * Returns IDictionaryConfig for the given dictionaryId, throws
     * NoSuchDictionaryException
     *
     * @param dictionaryId id of the dictionary for which IDictionaryConfig is
     * requested
     * @return IDictionaryConfig for the given dictionaryId,
     * @throws NoSuchDictionaryException throw if no IDictionaryConfig is
     * configured for requested dictionaryId
     */
    private IDictionaryConfig getDictionaryConfig(String dictionaryId)
            throws NoSuchDictionaryException {
        IDictionaryConfig config = this.dictionaryRedirect
                .get(dictionaryId);
        if (config != null) {
            return config;
        }

        for (String pattern : dictionaryRedirect.keySet()) {
            if (dictionaryId.matches(pattern)) {
                return dictionaryRedirect.get(pattern);
            }
        }

        logger.error("No dictionary configured for  id: " + dictionaryId);
        throw new NoSuchDictionaryException(dictionaryId);
    }

    /**
     * Returns a map containing dictionary configs for all dictionaries in this
     * referenceDataSource
     *
     * @return map containing dictionary configs
     */
    Map<String, IDictionaryConfig> getDictionarydonfigMap() {
        return dictionaryRedirect;
    }

    public Boolean isValid(String dictionaryId, String column, String value, Date refDataDate, String metadata, boolean strictValue) throws NoSuchDictionaryException, ReferenceDataSourceInternalException {
//        logger.info("isValid called for dictionary id: " + dictionaryId);

        IDictionaryConfig config = getDictionaryConfig(dictionaryId);
        if (Validator.isColumnNameNullOrEmpty(column)) {
            column = config.getDefSearchColumn();
        }

        long start = System.nanoTime();
        Boolean res = dictionaryRedirect.get(dictionaryId).getRefDataSource().isValid(dictionaryId, column, value, refDataDate, metadata, strictValue);
//        logger.info("*** " + res + " isValid(\"" +dictionaryId+ "\", \"" + column+ "\", \"" + value+ "\", \"" + refDataDate+ "); // executed in " + (((double)(System.nanoTime() - start)) / 1000000)+" ms");
        return res;
    }
}
