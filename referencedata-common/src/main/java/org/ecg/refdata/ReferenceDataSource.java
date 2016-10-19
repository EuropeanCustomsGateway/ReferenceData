package org.ecg.refdata;

import java.io.Serializable;
import java.util.Date;

import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.QueryResult;

/**
 * Basically interface allows get data from reference data. It contains two
 * methods for getting results from data source. Method
 * <code>getUniqueItem</code> gets only one result when we send query, and
 * method <code>getItemsList</code> gets list from data source for specified
 * dictionary
 *
 */
public interface ReferenceDataSource extends Serializable {

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
     * @throws ReferenceDataSourceInternalException is thrown in case of
     * internal (usually communication) problems
     */
    QueryResult getUniqueItem(String dictionaryId, String column, String value,
            Date refDataDate, String languageCode, String metadata, boolean strictValue)
            throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException;

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
     * @throws ReferenceDataSourceInternalException is thrown in case of
     * internal (usually communication) problems
     */
    QueryResult getItemsList(String dictionaryId, String column, String value,
            Date refDataDate, String languageCode, int itemsStart,
            int itemsCount, String metadata, boolean strictValue) throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException;

    /**
     * checks if value is valid for specified parameters
     *
     * @param dictionaryId id of dictionary too look for value to exists
     * @param searchColumnName column under which value will be searched
     * @param searchValue value to be searched
     * @param validationDate date when value should be valid
     * @return
     * @throws org.ecg.refdata.exceptions.NoSuchDictionaryException
     * @throws org.ecg.refdata.exceptions.ReferenceDataSourceInternalException
     */
    Boolean isValid(String dictionaryId, String column,
            String value, Date refDataDate, String metadata, boolean strictValue) throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException;
}
