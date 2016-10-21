package org.ecg.refdata.datasource.ejb;

import org.ecg.refdata.IReferenceDataConfig;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;
import org.ecg.refdata.exceptions.IncorrectParameterException;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.DictionaryInfo;
import org.ecg.refdata.query.QueryResult;
import java.io.Serializable;
import java.util.Map;

/**
 * Main interface for persistence bean. Contains all methods required for
 * persisting/getting instances of dictionary item.
 *
 */
public interface PersistenceDataSourceSB extends Serializable {

    /**
     * Method returns <code>DictionaryInfo</code> for input dictionary id and
     * language code.
     *
     * @param id required dictionary Id
     * @param languageCode required language code
     * @param itemsStart index of first item on required list (paging)
     * @param itemsCount quantity of items on list
     * @throws ReferenceDataSourceInternalException
     * @throws NoSuchDictionaryException method throws an exception when no
     * dictionary was found for required dictionary id
     */
    DictionaryInfo getDictionaryInfo(String id, String languageCode)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException;

    /**
     * Method returns list of dictionary ids.
     *
     * @return list of String
     */
    List<String> getListOfAllIds();

    /**
     * Method persists <code>ReferenceDataAbstractDataType</code> items in
     * database.
     *
     * @param sampleData data to be stored
     * @throws ReferenceDataSourceInternalException - thrown if internal
     * exceptions occurs
     * @throws IncorrectParameterException - thrown if in database there is
     * already dictionary with given id
     */
    void persistReferenceDataAbstractDataType(
            ReferenceDataAbstractDataType sampleData)
            throws ReferenceDataSourceInternalException,
            IncorrectParameterException;

//	/**
//	 * Method gets <code>ReferenceDataAbstractDataType</code> entity from
//	 * database.
//	 *
//	 * @param id
//	 *            required dictionary id
//	 * @return instance of ReferenceDataAbstractDataType
//	 * @throws NoSuchDictionaryException
//	 *             method throws an exception when no dictionary was found for
//	 *             required dictionary id
//	 */
//	ReferenceDataAbstractDataType getReferenceDataAbstractDataType(String id)
//			throws NoSuchDictionaryException;
    /**
     * Method returns a list of <code>ReferenceDataAbstractItemEntity</code>
     * entities for required input conditions (id, column, value). Method
     * returns null or empty list if none of dictionary contains required id. In
     * case of wrong column method will throw an
     * <code>IncorrectParameterException</code> exception.
     *
     * @param id required
     * @param column required field name
     * @param value value for fields
     * @return collection of {@link ReferenceDataAbstractItemEntity}
     * @throws ReferenceDataSourceInternalException if internal exception has
     * occured
     * @throws NoSuchDictionaryException once no dictionary item was found
     */
    Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
            String id, String column, String value, Date date)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException;

//	Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
//			String id, String column, String value, Date date,Integer firstResult,Integer resultsCount)
//			throws ReferenceDataSourceInternalException,
//			NoSuchDictionaryException;
    /**
     * Method returns a list of <code>ReferenceDataAbstractItemEntity</code>
     * entities for required input conditions (id, column, value). Method
     * returns null or empty list if none of dictionary contains required id. In
     * case of wrong column method will throw an
     * <code>IncorrectParameterException</code> exception.
     *
     * @param id required
     * @param column required field name
     * @param value value for fields
     * @param startsWith true - values starting with value, false - values
     * exactly like value
     * @return collection of {@link ReferenceDataAbstractItemEntity}
     * @throws ReferenceDataSourceInternalException if internal exception has
     * occured
     * @throws NoSuchDictionaryException once no dictionary item was found
     */
    Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
            String id, String column, String value, Date date, boolean startsWith)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException;

//	Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
//			String id, String column, String value, Date date, boolean startsWith,Integer firstResult,Integer resultsCount)
//			throws ReferenceDataSourceInternalException,
//			NoSuchDictionaryException;
    /**
     * Method returns a list of <code>ReferenceDataAbstractItemEntity</code>
     * entities for required input conditions (id, column, value). Method
     * returns null or empty list if none of dictionary contains required id. In
     * case of wrong column method will throw an
     * <code>IncorrectParameterException</code> exception.
     *
     * @param id required
     * @param column required field name
     * @param value value for fields
     * @param startsWith true - values starting with value, false - values
     * exactly like value
     * @return collection of {@link ReferenceDataAbstractItemEntity}
     * @throws ReferenceDataSourceInternalException if internal exception has
     * occured
     * @throws NoSuchDictionaryException once no dictionary item was found
     */
    //public <T extends ReferenceDataAbstractItemEntity> T createInstance(Class<T> clazz, Object... par);
    <T extends ReferenceDataAbstractItemEntity> Collection<T> getReferenceDataAbstractItem(
            Class<T> clazz, String id, String column, String value, Date date, boolean startsWith)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException;

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
    public Boolean isValid(String dictionaryId, String searchColumnName, String searchValue, Date validationDate)
            throws NoSuchDictionaryException, ReferenceDataSourceInternalException;

    /**
     * Method returns a list of <code>ReferenceDataAbstractItemEntity</code>
     * entities for required input conditions (id, column, value). Method
     * returns null or empty list if none of dictionary contains required id. In
     * case of wrong column method will throw an
     * <code>IncorrectParameterException</code> exception.
     *
     * @param dictionaryId required
     * @param searchColumnName required field name
     * @param searchValue value for fields
     * @return collection of {@link ReferenceDataAbstractItemEntity}
     * @throws ReferenceDataSourceInternalException if internal exception has
     * occured
     * @throws NoSuchDictionaryException once no dictionary item was found
     */
    public Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
            String dictionaryId, String searchColumnName, String searchValue)
            throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException;

    /**
     * Method returns paged QueryResult instance for magnifying glass component
     * on web layer.
     *
     * @param dictionaryId required dictionary id
     * @param languageCode the language code
     * @param column column name
     * @param value "startwith" value of data
     * @param itemsStart points first item on required page
     * @param itemsCount number of items per page
     * @param referenceDate date of reference data
     * @param startsWith flag decides if "value" will be treated as starWith
     * condition or equal.
     * @return an instance of QueryResultImpl class
     * @throws ReferenceDataSourceInternalException if internal exception has
     * occured
     * @throws NoSuchDictionaryException once no dictionary item was found
     */
    QueryResult getItemsList(String dictionaryId, String column, String value,
            Date referenceDate, String languageCode, int itemsStart,
            int itemsCount, boolean startsWith)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException;

    /**
     * Method returns a list of <code>ReferenceDataAbstractItemEntity</code>
     * entities for required input conditions (id, column, value). Method
     * returns null or empty list if none of dictionary contains required id. In
     * case of wrong column method will throw an
     * <code>IncorrectParameterException</code> exception.
     *
     * @param id required
     * @param column required field name
     * @param value value for fields
     * @param startsWith true - values starting with value, false - values
     * exactly like value
     * @return collection of {@link ReferenceDataAbstractItemEntity}
     * @throws ReferenceDataSourceInternalException if internal exception has
     * occured
     * @throws NoSuchDictionaryException once no dictionary item was found
     */
    //public <T extends ReferenceDataAbstractItemEntity> T createInstance(Class<T> clazz, Object... par);
    <T extends ReferenceDataAbstractItemEntity> Collection<T> getReferenceDataAbstractItem(
            Class<T> clazz, String id, List<Map.Entry<String, Object>> column_value, Date date, boolean startsWith)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException;

    /**
     * Method removes DataTypeEntity from database.
     *
     *
     * @param refId reference data id
     */
    void removeDataTypeEntityByRefId(String refId);

    public IReferenceDataConfig getConfig();
}
