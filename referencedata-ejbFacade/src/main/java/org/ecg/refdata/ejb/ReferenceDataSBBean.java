package org.ecg.refdata.ejb;

import java.util.Date;
import javax.ejb.Stateless;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.core.ReferenceDataFactory;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.factory.AbstractReferenceDataFactory;
import org.ecg.refdata.query.QueryResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class represents basic EJB session component for communication with web
 * layer. It implements <CODE>ReferenceDataSB</CODE> interface which contains
 * all necessary methods to access all kinds of data sources. Internally it uses
 * <CODE>ReferenceDataSpringFactory</CODE> which gets referenceDataDictionaries
 * configuration from predefined configuration file.
 *
 */
@Stateless(mappedName = "ReferenceDataSBBean")
public class ReferenceDataSBBean implements ReferenceDataSBRemote, ReferenceDataSBLocal {

    private static final long serialVersionUID = 9170422050101816391L;
    private static final Log logger = LogFactory.getLog(ReferenceDataSBBean.class);

    // use ReferenceDataSpringFactory
    private final static AbstractReferenceDataFactory datasourceFactory = new ReferenceDataFactory();
    // with the given configuration
    private final static String CONFIGURATION_FILE = "/org/ecg/refdata/spring-beans.xml";

    private ReferenceDataSource referenceDataSource;

    public ReferenceDataSBBean() {
        try {
            if (referenceDataSource == null) {
                referenceDataSource = datasourceFactory.getReferenceDataSource(CONFIGURATION_FILE);
            }
        } catch (ReferenceDataSourceInitializationException e) {
            logger.error("Could not initialize datasource, "
                    + e.getMessage(), e);
        }
    }

    /**
     * Method gets instance of <CODE>DictionaryConfig</CODE>.
     *
     * @param dictionaryId -required dictionaryId
     *
     * @return <CODE>DictionaryConfig</CODE> instance
     * @throws NoSuchDictionaryException exceptions appears once no dictionary
     * is found
     * @throws ReferenceDataSourceInternalException
     */
    public QueryResult getItemsList(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode,
            int itemsStart, int itemsCount, String metadata, boolean strictValue) throws NoSuchDictionaryException, ReferenceDataSourceInternalException {
        return referenceDataSource.getItemsList(dictionaryId, column, value,
                refDataDate, languageCode, itemsStart, itemsCount, metadata, strictValue);
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
     * @return query result
     * @throws NoSuchDictionaryException Cannot find dictionary with specified
     * id.
     */
    public QueryResult getUniqueItem(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode, String metadata, boolean strictValue)
            throws NoSuchDictionaryException, ReferenceDataSourceInternalException {
        return referenceDataSource.getUniqueItem(dictionaryId, column, value,
                refDataDate, languageCode, metadata, strictValue);
    }

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
    public Boolean isValid(String dictionaryId, String column,
            String value, Date refDataDate, String metadata, boolean strictValue) throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {
        return referenceDataSource.isValid(dictionaryId, column, value,
                refDataDate, metadata, strictValue);
    }

}
