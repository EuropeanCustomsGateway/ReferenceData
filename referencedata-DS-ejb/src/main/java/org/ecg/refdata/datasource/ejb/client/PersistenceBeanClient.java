package org.ecg.refdata.datasource.ejb.client;

import org.ecg.refdata.ConfiguredReferenceDataSource;
import org.ecg.refdata.IReferenceDataConfig;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB;
import org.ecg.refdata.datasource.ejb.config.FileReferenceDataConfig;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;
import org.ecg.refdata.exceptions.CommunicationException;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.QueryResult;
import org.ecg.refdata.utils.StringHelper;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class contains client of persistence EJB <code>EjbDataSourceBean</code>.
 *
 */
public class PersistenceBeanClient implements ConfiguredReferenceDataSource {

    private static final long serialVersionUID = 6382435617567330709L;
    private static final Log logger = LogFactory.getLog(PersistenceBeanClient.class);
    private Map<String, String> connectionProperties = new HashMap<String, String>();
    /**
     * @serial Reference to {@link PersistenceDataSourceSB}
     */
    private PersistenceDataSourceSB ejbReferenceData = null;

    /**
     * Method initialize main mapping object for specific data source. In case
     * of EJB bean data source it creates a new connection with a bean.
     *
     * @throws CommunicationException
     * @throws ReferenceDataSourceInitializationException
     *
     *
     */
    void initialize() throws CommunicationException,
            ReferenceDataSourceInitializationException {

        if (ejbReferenceData != null) {
            return;
        }

        String namingFactory = connectionProperties.get("namingFactory");
        String providerUrl = connectionProperties.get("providerUrl");
        String jndiName = connectionProperties.get("jndiName");
        if (StringHelper.emptyStringToNull(namingFactory) == null
                || StringHelper.emptyStringToNull(providerUrl) == null
                || StringHelper.emptyStringToNull(jndiName) == null) {
            throw new ReferenceDataSourceInitializationException(
                    "Could not initilize referense data source, one of parameters: namingFactory, providerUrl, jndiName wasn't set !!!");
        }

        if (!(providerUrl.indexOf("://") >= 0)) {
            providerUrl = "jnp://" + providerUrl;
        }

        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, namingFactory);
        env.put(Context.PROVIDER_URL, providerUrl);
        try {
            Context ctx = new InitialContext(env);
            logger.trace("Context initialized, quering for: " + jndiName);

            ejbReferenceData = (PersistenceDataSourceSB) ctx.lookup(jndiName);

            logger.info("Reference to EJBFacade " + "Datasource successfully obtained");

        } catch (Exception e) {

            logger.error(
                    "Could not access  PersistenceDataSourceSB using: " + connectionProperties.toString() + ", reason: " + e.getMessage(), e);

            throw new CommunicationException(
                    "Could not access  PersistenceDataSourceSB using: " + connectionProperties.toString() + ", reason: " + e.getMessage(), e);
        }

    }

    /**
     * Method returns complete set of information regarding specific dictionary.
     * The <code>QueryResult</code> instance consist of language code, list of
     * dictionary items, and the nuber of start element on the list.
     *
     * @see org.ecg.refdata.ConcreteRefDataSource#getItemsList(java.lang.String,
     * java.lang.String, java.lang.String, java.util.Date, java.lang.String,
     * int, int)
     */
    public QueryResult getItemsList(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode,
            int itemsStart, int itemsCount, String metadata, boolean strictValue)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {
        return getItemsList(dictionaryId, column, value, refDataDate,
                languageCode, itemsStart, itemsCount, true, strictValue);
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
     * @throws NoSuchDictionaryException
     */
    private QueryResult getItemsList(String dictionaryId, String column,
            String value, Date refDataDate, String languageCode,
            int itemsStart, int itemsCount, boolean startsWith, boolean strictValue)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {

        initialize();

        return ejbReferenceData.getItemsList(dictionaryId,
                column, value, refDataDate, languageCode, itemsStart, itemsCount, startsWith);

    }

    /**
     * Method returns an unique item for required input parameters
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
        // simply get list of items paged by 1 starting from 0
        QueryResult queryResult = this.getItemsList(dictionaryId, column,
                value, refDataDate, languageCode, 0, 1, startsWith, strictValue);
        return queryResult;
    }

    /**
     * Method returns an unique item for required input parameters
     *
     * @see
     * org.ecg.refdata.ConcreteRefDataSource#getUniqueItem(java.lang.String,
     * java.lang.String, java.lang.String, java.util.Date, java.lang.String)
     */
    public Boolean isValid(String dictionaryId, String column,
            String value, Date refDataDate, String metadata, boolean strictValue)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {
        initialize();
        return ejbReferenceData.isValid(dictionaryId, column, value, refDataDate);
    }

//	/**
//	 * Method returns descriptions of dictionaries with dictionaries item
//	 * collection in it.
//	 *
//	 * @param id
//	 *            required dictionary id
//	 * @return dictionary description information with items.
//	 */
//	public ReferenceDataAbstractDataType getReferenceDataAbstractDataType(
//			String id) throws ReferenceDataSourceInternalException,
//			NoSuchDictionaryException {
//		initialize();
//		return ejbReferenceData.getReferenceDataAbstractDataType(id);
//	}
    /**
     * Method returns a collection of entity items for required input
     * conditions.
     *
     * @param id required id
     * @param column column name
     * @param value condition which represents startWith value of "column"
     * argument
     * @return collection of items
     */
    public Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
            String id, String searchColumn, String searchValue,
            Date referenceDate) throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {

        initialize();

        return ejbReferenceData.getReferenceDataAbstractItem(id, searchColumn,
                searchValue, referenceDate);

    }

    /**
     * Returns reference to the EJB bean this Client wraps
     *
     * @return reference to the EJB session bean
     */
    PersistenceDataSourceSB getEjbReferenceData() {
        return ejbReferenceData;
    }

    /**
     * for testing only
     *
     * @param ejbReferenceData
     */
    protected void setEjbReferenceData(PersistenceDataSourceSB ejbReferenceData) {
        this.ejbReferenceData = ejbReferenceData;
    }

    public IReferenceDataConfig getConfigStatic() {
        return new FileReferenceDataConfig();
    }

    public IReferenceDataConfig getConfig() {
        try {
            initialize();
        } catch (CommunicationException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } catch (ReferenceDataSourceInitializationException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return ejbReferenceData.getConfig();
    }

    /**
     * Method returns parameters of connection to internal EJB config
     *
     * @return parameters of connection to internal EJB config
     */
    public Map<String, String> getConnectionProperties() {
        return connectionProperties;
    }

    /**
     * Method set parameters of connection to internal EJB config
     *
     * @param connectionProperties parameters of connection to set
     */
    public void setConnectionProperties(Map<String, String> connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
