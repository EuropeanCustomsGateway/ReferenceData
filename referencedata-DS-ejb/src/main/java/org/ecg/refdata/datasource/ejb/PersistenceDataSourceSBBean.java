package org.ecg.refdata.datasource.ejb;

import org.ecg.refdata.IReferenceDataConfig;
import org.ecg.refdata.datasource.ejb.config.EjbReferenceDataConfig;
import org.ecg.refdata.datasource.ejb.config.RefDataConfiguration;
import org.ecg.refdata.datasource.ejb.config.RefDataConfiguration.UndefinedTypeException;
import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.exceptions.IncorrectParameterException;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.DictionaryInfo;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.impl.DictionaryInfoImpl;
import org.ecg.refdata.query.impl.QueryResultImpl;
import org.ecg.refdata.utils.DateHelper;
import org.ecg.refdata.utils.Validator;
import org.ecg.refdata.validation.ValidationResult;
import org.ecg.refdata.validation.impl.ValidationResultImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Main class of bean which provides direct access to EJB persistence.
 *
 */
@Stateless(mappedName = "PersistenceDataSourceSBBean")
public class PersistenceDataSourceSBBean implements PersistenceDataSourceSBLocal, PersistenceDataSourceSBRemote {

    private static final long serialVersionUID = -6354093594255032536L;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * We need specific keywords to find out if column contains date value and
     * we can dynamically create query for date comparison.
     */
    @PersistenceContext(unitName = "EjbDataSourcePersistenceUnit")
    EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    private static final Log log = LogFactory.getLog(PersistenceDataSourceSBBean.class);

    /**
     * @see
     * org.ecg.refdata.datasource.ejb.client.EjbPersistence#getListOfAllIds()
     */
    public List<String> getListOfAllIds() {

        Query q = entityManager.createQuery("SELECT ent.refDataId FROM "
                + "org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType ent");
        @SuppressWarnings("unchecked")
        List<String> list = q.getResultList();
        return list;

    }

    /**
     * @throws ReferenceDataSourceInternalException
     *
     * @see
     * org.ecg.refdata.datasource.ejb.client.EjbPersistence#persistReferenceDataAbstractDataType(org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType)
     */
    public void persistReferenceDataAbstractDataType(
            ReferenceDataAbstractDataType sampleData)
            throws ReferenceDataSourceInternalException,
            IncorrectParameterException {

        String dictionaryId = sampleData.getRefDataId();
        if (log.isInfoEnabled()) {
            log.info("Call for bean (persistReferenceDataAbstractDataType), required refDataId: " + dictionaryId);
        }

        if (getId2typeNoCache().get(dictionaryId) != null) {
            throw new IncorrectParameterException(
                    "Reference Data Type with id:'" + dictionaryId + "' already stored ");
        } else {
            // OK no such dictionary - we can save
            // yes - we need to merge first - for unsaved instances it is not a
            // problem
            // for instanced modified and updated we need to merge first
            sampleData = entityManager.merge(sampleData);
            // persist
            entityManager.persist(sampleData);
            if (log.isInfoEnabled()) {
                log.info("An instance of ReferenceDataAbstractDataType has been persisted successfuly");
            }
            return;
        }
    }

    /**
     * @see
     * org.ecg.refdata.datasource.ejb.client.EjbPersistence#getNameAndDescrpiton(java.lang.String,
     * java.lang.String)
     */
    public DictionaryInfo getDictionaryInfo(String dictionaryId,
            String languageCode) throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {

        if (log.isTraceEnabled()) {
            log.trace("Call for bean (getDictionaryInfo), " + "required id: " + dictionaryId + "");
        }

        // if no date could be found throw an exception
        if (getConfig().getDictionaryConfigMap().get(dictionaryId) == null) {
            throw new NoSuchDictionaryException(dictionaryId);
        }

        RefDataConfiguration config;
        try {
            config = new RefDataConfiguration(getId2type().get(dictionaryId));
        } catch (UndefinedTypeException ex) {
            ex.printStackTrace();
            throw new NoSuchDictionaryException(dictionaryId);
        }

        String queryString1 = "select nad.NAME ,nad.DESCRIPTION, nad.LANGUAGE_CODE , m.LAST_CHANGE from REF_TYPE_NAME_AND_DESC_MAPP m , REF_NAME_AND_DESCRIPTION nad"
                + " where nad.REF_DATA_ABSTR_DATA_ID = m.ID"
                + " and m.REF_DATA_ID = '" + dictionaryId + "' ";
        String queryString2 = ""
                + " select count(*) from REF_TYPE_NAME_AND_DESC_MAPP m , REF_ITEM_MAPPING i , REF_NAME_AND_DESCRIPTION nad"
                + " where i.REF_DATA_ABSTR_DATA_ID = m.ID and nad.REF_DATA_ABSTR_DATA_ID = m.ID"
                + " and m.REF_DATA_ID = '" + dictionaryId + "'";

        List<Object[]> resQ1 = (List<Object[]>) entityManager.createNativeQuery(queryString1).getResultList();
        String name = "";
        String descr = "";
        String lang = "";
        Date lastChange = null;
//        boolean any = false;
//        boolean def = false;
        String langCodes[] = {languageCode, NameAndDescriptionItem.DEFAULT_LANGUAGE_CODE};

        // searching result by lang code (for given priority) to find best result
        boolean lbFound = false;

        langLoop:
        for (String langCode : langCodes) {
            for (Object[] objects : resQ1) {
                if (langCode.equalsIgnoreCase((String) objects[2])) {
                    name = (String) objects[0];
                    descr = (String) objects[1];
                    lang = (String) objects[2];
                    lastChange = (Date) objects[3];
                    lbFound = true;
                    break langLoop;
                }
            }
        }

        // if not found best result try to find any (first)
        if (!lbFound
                && resQ1 != null
                && resQ1.size() > 0) {
            name = (String) resQ1.get(0)[0];
            descr = (String) resQ1.get(0)[1];
            lang = (String) resQ1.get(0)[2];
            lastChange = (Date) resQ1.get(0)[3];
        }
//        for (Object[] objects : resQ1) {
//            if (((String) objects[2]) == null && !any) {
//                name = (String) objects[0];
//                descr = (String) objects[1];
//                lang = (String) objects[2];
//                lastChange = (Date) objects[3];
//                continue;
//            }
//            if (!any && ((String) objects[2]).equalsIgnoreCase(NameAndDescriptionItem.DEFAULT_LANGUAGE_CODE)) {
//                name = (String) objects[0];
//                descr = (String) objects[1];
//                lang = (String) objects[2];
//                lastChange = (Date) objects[3];
//            }
//            if (!def && !((String) objects[2]).equalsIgnoreCase(languageCode)) {
//                name = (String) objects[0];
//                descr = (String) objects[1];
//                lang = (String) objects[2];
//                lastChange = (Date) objects[3];
//                continue;
//            }
//
//            name = (String) objects[0];
//            descr = (String) objects[1];
//            lang = (String) objects[2];
//            lastChange = (Date) objects[3];
//            break;
//        }

        BigInteger res2 = (BigInteger) entityManager.createNativeQuery(queryString2).getSingleResult();

        if (res2 != null) {
            if (log.isTraceEnabled()) {
                log.trace("DictionaryInfo obtained successfully for dictionaryId" + dictionaryId);
            }

            DictionaryInfoImpl dii = new DictionaryInfoImpl(name,
                    descr, lastChange, res2.intValue());
            dii.setDictionaryId(dictionaryId);
            dii.setItemsOnPage(config.getDefItemsOnPage());
            dii.setValidColumns(config.getPreferredColumns());
            return dii;

        }

        log.error("Cannot find a NameAndDescriptionItem for dictionary with dictionaryId " + dictionaryId);

        throw new ReferenceDataSourceInternalException(
                "Cannot find a NameAndDescriptionItem for dictionary with dictionaryId " + dictionaryId);

    }

    /**
     * @throws ReferenceDataSourceInternalException
     *
     * @see
     * org.ecg.refdata.datasource.ejb.client.EjbPersistence#getReferenceDataAbstractItem(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
            String dictionaryId, String searchColumnName, String vsearchValue,
            Date referenceDate) throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {
        return getReferenceDataAbstractItem(dictionaryId, searchColumnName,
                vsearchValue, referenceDate, true);
    }

    public <T extends ReferenceDataAbstractItemEntity> Collection<T> getReferenceDataAbstractItem(Class<T> clazz,
            String dictionaryId, String searchColumnName, String searchValue,
            Date referenceDate, boolean startsWith)
            throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {

        // try to get default column if null
        RefDataConfiguration config;
        try {
            config = new RefDataConfiguration(getId2type().get(dictionaryId));
            // try to get default column if null
            if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
                searchColumnName = config.getDefSearchColumn();
            }
        } catch (UndefinedTypeException ex) {
            ex.printStackTrace();
            throw new NoSuchDictionaryException(dictionaryId);
        }

        if (log.isInfoEnabled()) {
            log.info("getReferenceDataAbstractItem called for"
                    + "item type: " + clazz.getSimpleName() + " dictionary id:  '" + dictionaryId + "' with column: '" + searchColumnName + "' starts/match: '" + searchValue + "', referenceDate: " + referenceDate);
        }

        if (dictionaryId == null) {
            log.warn("DictionaryID is null");
            throw new IncorrectParameterException(
                    "Incorrect parameter: dictionaryId cannot be null");
        }

        if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
            log.warn("Column isn't correctly defined, value: '" + searchColumnName + "' is incorrect");
            throw new IncorrectParameterException(
                    "Incorrect parameter: search column name cannot be null");
        }

        // if date is null use date set to now
        if (referenceDate == null) {
            referenceDate = DateHelper.clearTime(new java.util.Date());
        } else {
            referenceDate = DateHelper.clearTime(referenceDate);
        }

        /**
         * First of all we need to know which dictionary type we are playing
         * with.
         */
        // could not detect dictionary type by id - means no dictionary with
        // given id is supported by this datasource
        if (getConfig().getDictionaryConfigMap().get(dictionaryId) == null) {
            throw new NoSuchDictionaryException(dictionaryId);
        }

        /**
         * Check if we can search by a given column
         */
        if (!isSearchColumnSupportedByItem(clazz,
                searchColumnName)) {
            log.error("Invalid column name: '" + searchColumnName + "', for dictionary with id '" + dictionaryId + "' of type: " + clazz.getName());
            searchColumnName = config.getDefSearchColumn();
        } else {
        }

        /**
         * Prepare HQL substring for periodic validity - limit result to the
         * given date
         */
        String limitDateSubQuery = "";
        if (ReferenceDataPeriodicValidityAbstractItem.class.isAssignableFrom(clazz)) {
            limitDateSubQuery = " AND (" + "( :date BETWEEN item.validFrom AND item.validTo )  " + " OR (:date >= item.validFrom AND item.validTo IS NULL)" + " OR (:date <= item.validTo AND item.validFrom IS NULL)" + " OR (item.validFrom IS NULL AND item.validTo IS NULL)" + ")";
        }

        /**
         * If searchValue is not blank- limit results
         */
        String limitColumnValueSubQuery = "";
        // pity - cannot set searchColumn as parameter
        // look at
        // http://www.stpe.se/2008/07/hibernate-hql-like-query-named-
        // parameters/
        // no SQL injection as searchColumnName has be validated as existing
        // item attribute
        if (!GenericValidator.isBlankOrNull(searchValue)) {
            limitColumnValueSubQuery = " AND item." + searchColumnName // safe
                    + " LIKE :value ";
        }

        /**
         * Preparing HQL statement
         */
        String queryString = "SELECT item FROM org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType type, " + clazz.getName() + " item WHERE type.refDataId = :refDataId " + " AND item.referenceDataAbstractDataTypeEntity = type " + limitColumnValueSubQuery + limitDateSubQuery // safe
                + " ORDER BY item." + searchColumnName + "";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("refDataId", dictionaryId);

        // inject data object if limitDateSubquery was used
        if (!GenericValidator.isBlankOrNull(limitDateSubQuery)) {
            query.setParameter("date", referenceDate);
        }

        // inject value object if limitColumnValueSubQuery was used
        if (!GenericValidator.isBlankOrNull(limitColumnValueSubQuery)) {
            // if startsWith is set to true extends search from exact matching
            if (startsWith) {
                query.setParameter("value", searchValue + "%");
            } else {
                query.setParameter("value", searchValue);
            }
        }

//		if (logger.isLoggable(Level.FINEST)) {
//			logger.finest("Query: " + queryString);
//		}
//
//		if(logger.isInfoEnabled()) logger.info("Query: " + queryString);
        List<T> outputList = (List<T>) query.getResultList();

        if (log.isTraceEnabled()) {
            log.trace("List of " + clazz.getSimpleName() + " items obtained successfully, found: " + outputList.size() + " items");
        }

        return outputList;
    }

    //	@SuppressWarnings("unchecked")
    public Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
            String dictionaryId, String searchColumnName, String searchValue,
            Date referenceDate, boolean startsWith)
            throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {

        RefDataConfiguration config;
        try {
            config = new RefDataConfiguration(getId2type().get(dictionaryId));
            // try to get default column if null
            if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
                searchColumnName = config.getDefSearchColumn();
            }
        } catch (UndefinedTypeException ex) {
            ex.printStackTrace();
            throw new NoSuchDictionaryException(dictionaryId);
        }

        if (log.isTraceEnabled()) {
            log.trace("getReferenceDataItems called for dictionary id:  " + dictionaryId + " with column: " + searchColumnName + " starts/match: " + searchValue + ", referenceDate: " + referenceDate);
        }

        if (dictionaryId == null) {
            log.warn("DictionaryID is null");
            throw new IncorrectParameterException(
                    "Incorrect parameter: dictionaryId cannot be null");
        }

        // if date is null use date set to now
        if (referenceDate == null) {
            referenceDate = DateHelper.clearTime(new java.util.Date());
        } else {
            referenceDate = DateHelper.clearTime(referenceDate);
        }

        if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
            log.warn("Column isn't correctly defined, value: " + searchColumnName + " is incorrect");
            throw new IncorrectParameterException("Incorrect parameter: search column name cannot be null");
        }

        // could not detect dictionary type by id - means no dictionary with
        // given id is supported by this datasource
        /**
         * First of all we need to know which dictionary type we are playing
         * with.
         */
        if (getConfig().getDictionaryConfigMap().get(dictionaryId) == null) {
            throw new NoSuchDictionaryException(dictionaryId);
        }

        /**
         * Check if we can search by a given column
         */
        if (!isSearchColumnSupportedByItem(config.getReferenceDataAbstractItemEntityClass(),
                searchColumnName)) {
            log.error("Invalid column name: " + searchColumnName + ", for dictionary with id " + dictionaryId + " of type: " + config.getReferenceDataAbstractItemEntityClass().getName());
            searchColumnName = config.getDefSearchColumn();
        } else {
        }

        /**
         * Prepare HQL substring for periodic validity - limit result to the
         * given date
         */
        String limitDateSubQuery = "";
        if (ReferenceDataPeriodicValidityAbstractItem.class.isAssignableFrom(config.getReferenceDataAbstractItemEntityClass())) {
            limitDateSubQuery = " AND (" + "( :date BETWEEN item.validFrom AND item.validTo )  " + " OR (:date >= item.validFrom AND item.validTo IS NULL)" + " OR (:date <= item.validTo AND item.validFrom IS NULL)" + " OR (item.validFrom IS NULL AND item.validTo IS NULL)" + ")";
        }

        /**
         * If searchValue is not blank- limit results
         */
        String limitColumnValueSubQuery = "";
        // pity - cannot set searchColumn as parameter
        // look at
        // http://www.stpe.se/2008/07/hibernate-hql-like-query-named-
        // parameters/
        // no SQL injection as searchColumnName has be validated as existing
        // item attribute
        if (!GenericValidator.isBlankOrNull(searchValue)) {
            limitColumnValueSubQuery = " AND item." + searchColumnName // safe
                    + " LIKE :value ";
        }

        /**
         * Preparing HQL statement
         */
        String queryString = "SELECT item FROM "
                + "org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType type, " + config.getReferenceDataAbstractItemEntityClass().getName() + " item " + " WHERE type.refDataId = :refDataId " + " AND item.referenceDataAbstractDataTypeEntity = type " + limitColumnValueSubQuery + limitDateSubQuery // safe
                + " ORDER BY item." + searchColumnName + "";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("refDataId", dictionaryId);

        // inject data object if limitDateSubquery was used
        if (!GenericValidator.isBlankOrNull(limitDateSubQuery)) {
            query.setParameter("date", referenceDate);
        }

        // inject value object if limitColumnValueSubQuery was used
        if (!GenericValidator.isBlankOrNull(limitColumnValueSubQuery)) {
            // if startsWith is set to true extends search from exact matching
            if (startsWith) {
                query.setParameter("value", searchValue + "%");
            } else {
                query.setParameter("value", searchValue);
            }
        }
//        Query q2 = entityManager.createQuery("SELECT item FROM "
//                + "org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType type, " + config.getReferenceDataAbstractItemEntityClass().getName() + " item " + " WHERE type.refDataId = :refDataId " + " AND item.referenceDataAbstractDataTypeEntity = type " + limitColumnValueSubQuery);
//        q2.setParameter("refDataId", dictionaryId);
//        q2.setParameter("value", searchValue + "%");
//
//        q2.getResultList();
//		if (logger.isLoggable(Level.FINEST)) {
//			logger.finest("Query: " + queryString);
//		}
//
//		if(logger.isInfoEnabled()) logger.info("Query: " + queryString);
        List<ReferenceDataAbstractItemEntity> outputList = query.getResultList();

        if (log.isTraceEnabled()) {
            log.trace("List of referenceDataAbstractItem items obtained successfully, found: " + outputList.size() + " items");
        }

        return outputList;

    }

    public Boolean isValid(String dictionaryId, String searchColumnName, String searchValue, Date validationDate)
            throws NoSuchDictionaryException, ReferenceDataSourceInternalException {

        return new Boolean(isValid(dictionaryId, searchColumnName, searchValue).isValid(DateHelper.clearTime(validationDate)));
    }

    // key = <dictionaryId>;<searchColumnName>
    // value = set of valid values searchValue;
    final static HashMap<String, Set<String>> notPeriodical = new HashMap<String, Set<String>>();
    // set of validation results
    final static HashMap<String, HashMap<String, ValidationResultImpl>> periodical = new HashMap<String, HashMap<String, ValidationResultImpl>>();
    // key = <dictionaryId>;<searchColumnName> ; value =<lastChangeDate>;
    final static HashMap<String, Integer> changes = new HashMap<String, Integer>();
    /* key <dictionaryId>;<searchColumnName> / next timestamp to chec */
    final static HashMap<String, Long> nextTimestampToCheck = new HashMap<String, Long>();
    /* key finction name  / next timestamp to chec */
    final static HashMap<String, Long> nextTimespamtToCheckOther = new HashMap<String, Long>();

    private static final int DT = (1000 * 60 * 1);
//    private static final int DT = (1000 * 1 * 1);

    static boolean checkForModSetNextTime(String key) {
        synchronized (nextTimestampToCheck) {
            long now_ms = System.currentTimeMillis();
            long nextTime = DT + (now_ms - now_ms % DT);
            if (nextTimestampToCheck.get(key) == null) {
                nextTimestampToCheck.put(key, nextTime);

                if (log.isDebugEnabled()) {
                    log.debug("next time to check, setting it to '" + key + "'= " + DateHelper.dateToString(new Date(nextTime), "yyyy-MM-dd HH:mm:ss"));
                }
                return true;
            }
            long lastTime = nextTimestampToCheck.get(key);

            if (lastTime < now_ms) {
                nextTimestampToCheck.put(key, nextTime);
                if (log.isDebugEnabled()) {
                    log.debug("next time to check, setting it to '" + key + "'= " + DateHelper.dateToString(new Date(nextTime), "yyyy-MM-dd HH:mm:ss"));
                }
                return true;
            }
            return false;
        }
    }

//    static boolean checkForMod(String key) {
//        long now_ms = System.currentTimeMillis();
//        long nextTime = DT + (now_ms - now_ms % DT);
//        if (nextTimestampToCheck.get(key) == null) {
////            nextTimestampToCheck.put(key, nextTime);
////
//            if (log.isDebugEnabled()) {
//                log.debug("next time to check, just checking not setting next time '" + key + "'= " + DateHelper.dateToString(new Date(nextTime), "yyyy-MM-dd HH:mm:ss"));
//            }
//            return true;
//        }
//        long lastTime = nextTimestampToCheck.get(key);
//
//        if (lastTime < now_ms) {
////             nextTimestampToCheck.put(key, nextTime);
//            if (log.isDebugEnabled()) {
//                log.debug("next time to check, just checking not setting next time '" + key + "'= " + DateHelper.dateToString(new Date(nextTime), "yyyy-MM-dd HH:mm:ss"));
//            }
//            return true;
//        }
//        return false;
//    }
    static boolean checkForOtherModSetNextTime(String key) {
        long now_ms = System.currentTimeMillis();
        long nextTime = DT + (now_ms - now_ms % DT);

        if (nextTimespamtToCheckOther.get(key) == null) {
            nextTimespamtToCheckOther.put(key, nextTime);
            if (log.isDebugEnabled()) {
                log.debug("next time to check '" + key + "'= " + new Date(nextTime));
            }
            return true;
        }
        long lastTime = nextTimespamtToCheckOther.get(key);

        if (lastTime <= now_ms) {
            nextTimespamtToCheckOther.put(key, nextTime);
            if (log.isDebugEnabled()) {
                log.debug("next time to check '" + key + "'= " + new Date(nextTime));
            }
            return true;
        }
        return false;
    }

    static boolean checkForOtherMod(String key) {
        long now_ms = System.currentTimeMillis();
        long nextTime = DT + (now_ms - now_ms % DT);
        if (nextTimespamtToCheckOther.get(key) == null) {
            if (log.isDebugEnabled()) {
                log.debug("next time to check, just checking not setting next time '" + key + "'= " + DateHelper.dateToString(new Date(nextTime), "yyyy-MM-dd HH:mm:ss"));
            }
            return true;
        }
        long lastTime = nextTimespamtToCheckOther.get(key);

        if (lastTime < now_ms) {
            if (log.isDebugEnabled()) {
                log.debug("next time to check, just checking not setting next time '" + key + "'= " + DateHelper.dateToString(new Date(nextTime), "yyyy-MM-dd HH:mm:ss"));
            }
            return true;
        }
        return false;
    }

    static Integer CONST_lastChange = -1;
    static boolean dont_use_cache_for_a_while = false;

    public ValidationResult isValid(String dictionaryId, String searchColumnName, String searchValue)
            throws NoSuchDictionaryException, ReferenceDataSourceInternalException {
        boolean cache = false;
        try {
            RefDataConfiguration tmp = new RefDataConfiguration(getId2type().get(dictionaryId));
            if (searchColumnName == null) {
                searchColumnName = tmp.getDefSearchColumn();
            }
            cache = tmp.getCache();
        } catch (UndefinedTypeException ex) {
            ex.printStackTrace();
        }
        boolean duct = dont_use_cache_for_a_while;
        if (cache && !duct) {
            return isValidCache(dictionaryId, searchColumnName, searchValue);
        } else {
            long start = System.nanoTime();
            ValidationResult res = isValidNoCache(dictionaryId, searchColumnName, searchValue);
            if (log.isInfoEnabled()) {
                log.info("took me " + (((double) (System.nanoTime() - start)) / 1000000) + " ms to check not cached value '" + searchValue + "' for dict " + dictionaryId + " under '" + searchColumnName + "'" + (duct ? " because of not using cache  for a while" : " because the dict is configured so"));
            }
            return res;
        }
    }

    public Integer getLastChange(String dictionaryId) throws ReferenceDataSourceInternalException {
        Integer lastChange = CONST_lastChange;
        try {
            Query queryA = getQueryForVersion(dictionaryId);
            lastChange = ((BigDecimal) queryA.getSingleResult()).intValue();
            if (lastChange == null) {
                throw new ReferenceDataSourceInternalException("last change date is null for dict with id  " + dictionaryId);
            }
        } catch (NonUniqueResultException ex) {
            log.error("Duplicated dictionaty with id: " + dictionaryId);
            throw new ReferenceDataSourceInternalException("Duplicated dictionaty with id: " + dictionaryId, ex);
        } catch (NoResultException e) {
            log.error("Invalid id there is no data related to reference data (id: " + dictionaryId + ")" + e.getMessage());
            throw new ReferenceDataSourceInternalException("Duplicated dictionaty with id: " + dictionaryId, e);
        }
        return lastChange;
    }

    static ReentrantReadWriteLock isValidLock = new ReentrantReadWriteLock();

    public ValidationResult isValidCache(String dictionaryId, String searchColumnName, String searchValue)
            throws NoSuchDictionaryException, ReferenceDataSourceInternalException {

        if (searchValue == null) {
            throw new NullPointerException("searchValue was null");
        }
        // caching
        String key = dictionaryId + ";" + (searchColumnName == null ? "<default>" : searchColumnName);

        boolean refresh = false;

        try {
//            log.info("waiting for read lock for id;column '" + key + "'...");
            isValidLock.readLock().lock();
//            log.info("got read lock for id;column '" + key + "'!");

            if (!changes.containsKey(key)) {
                if (log.isInfoEnabled()) {
                    log.info("dictionary with id;column '" + key + "' was not cached  yet... caching it ");
                }
                refresh = true;
            } else if (checkForModSetNextTime(key)) {
                Integer lastChange = getLastChange(dictionaryId);
                if (!changes.get(key).equals(lastChange)) {// if the dictrionary was updated
                    if (log.isInfoEnabled()) {
                        log.info("dictionary with id;column '" + key + "' needs to be refreshed(before = " + changes.get(key) + ", now = " + lastChange + "). Marking to cache it again");
                    }
                    refresh = true;
                } else if (log.isDebugEnabled()) {
                    log.debug("dictionary with id;column '" + key + "' does not need to be refreshed (before = " + changes.get(key) + ", now = " + lastChange + ").");
                }
            }

            if (refresh) {
                // getting values matching criteria
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("waiting for writeLock to modify mapid;column'" + key + "'...");
                    }
                    dont_use_cache_for_a_while = true;
                    isValidLock.readLock().unlock();
                    isValidLock.writeLock().lock();
                    if (log.isDebugEnabled()) {
                        log.debug("got write lock begin to make changes to mapid;column '" + key + "'!!!");
                    }

                    Collection<ReferenceDataAbstractItemEntity> abstractItemEntitys
                            = getReferenceDataAbstractItem(dictionaryId, searchColumnName, null);

                    // return if no results
                    if (abstractItemEntitys == null || abstractItemEntitys.isEmpty()) {
                        log.debug("dont have to make changes to mapid;column other thread has done it already / no items for dict '" + key + "'");
                    } else {
                        Integer lastChange = getLastChange(dictionaryId);
                        if (log.isDebugEnabled()) {
                            log.debug("setting changes lastChange of mapid;column '" + key + "' to " + lastChange);
                        }
                        changes.put(key, lastChange);
                        // for sure there are some matching results
                        RefDataConfiguration config;
                        try {
                            config = new RefDataConfiguration(getId2type().get(dictionaryId));
                            // try to get default column if null
                            if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
                                searchColumnName = config.getDefSearchColumn();
                            }
                        } catch (UndefinedTypeException ex) {
                            ex.printStackTrace();
                            throw new ReferenceDataSourceInternalException(
                                    "Problem with getting porperty searchColumnName  of item of dict" + dictionaryId, ex);
                        }

                        if (!ReferenceDataPeriodicValidityAbstractItem.class.isAssignableFrom(config.getReferenceDataAbstractItemEntityClass())) {
                            Set<String> set = new HashSet<String>(abstractItemEntitys.size());

                            for (ReferenceDataAbstractItemEntity item : abstractItemEntitys) {
                                try {
                                    set.add(BeanUtils.getProperty(item, searchColumnName));
                                } catch (Exception ex) {
                                    throw new ReferenceDataSourceInternalException(
                                            "Problem with getting porperty " + searchColumnName + " of item " + item, ex);
                                }
                            }
                            if (log.isDebugEnabled()) {
                                log.debug("saving changes to notPeriodical mapid;column '" + key + "'");
                            }
                            notPeriodical.put(key, set);
                        } else {
                            HashMap<String, ValidationResultImpl> map = new HashMap<String, ValidationResultImpl>(abstractItemEntitys.size());

                            for (ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity : abstractItemEntitys) {
                                ReferenceDataPeriodicValidityAbstractItem item = (ReferenceDataPeriodicValidityAbstractItem) referenceDataAbstractItemEntity;
                                String value;
                                try {
                                    value = BeanUtils.getProperty(item, searchColumnName);
                                } catch (Exception ex) {
                                    throw new ReferenceDataSourceInternalException(
                                            "Problem with getting porperty " + searchColumnName + " of item " + item, ex);
                                }
                                if (map.containsKey(value)) {
                                    map.get(value).addValidity(item.getValidFrom(), item.getValidTo());
                                } else {
                                    ValidationResultImpl vri = new ValidationResultImpl(dictionaryId, searchValue, true);
                                    vri.addValidity(item.getValidFrom(), item.getValidTo());
                                    map.put(value, vri);

                                }
                            }
                            if (log.isDebugEnabled()) {
                                log.debug("saving changes to periodical mapid;column '" + key + "'");
                            }
                            periodical.put(key, map);
                        }

                    }
                } finally {
                    if (log.isDebugEnabled()) {
                        log.debug("releasing writeLock after modify mapid;column'" + key + "'");
                    }
                    isValidLock.readLock().lock();
                    dont_use_cache_for_a_while = false;
                    isValidLock.writeLock().unlock();
                }
            }

            if (periodical.containsKey(key)) {
                HashMap<String, ValidationResultImpl> val = periodical.get(key);
                if (val.containsKey(searchValue)) {
                    return val.get(searchValue);
                } else {
                    return new ValidationResultImpl(dictionaryId, searchValue, false);
                }

            } else if (notPeriodical.containsKey(key)) {
                Set<String> val = notPeriodical.get(key);
                if (val.contains(searchValue)) {
                    return new ValidationResultImpl(dictionaryId, searchValue, false);
                } else {
                    return new ValidationResultImpl(dictionaryId, searchValue, true);
                }
            }
        } finally {
            isValidLock.readLock().unlock();
//            log.info("released read lock for id;column '" + key + "' ");
        }
        return new ValidationResultImpl(dictionaryId, searchValue, false);

    }

    public ValidationResult isValidNoCache(String dictionaryId, String searchColumnName, String searchValue)
            throws NoSuchDictionaryException, ReferenceDataSourceInternalException {

        if (searchValue == null) {
            throw new NullPointerException("searchValue was null");
        }

        // getting values matching criteria
        Collection<ReferenceDataAbstractItemEntity> abstractItemEntitys
                = getReferenceDataAbstractItem(dictionaryId, searchColumnName, searchValue);

        // return if no results
        if (abstractItemEntitys == null || abstractItemEntitys.isEmpty()) {
            return new ValidationResultImpl(dictionaryId, searchValue, false);
        }

        // for sure there are some matching results
        RefDataConfiguration config;
        try {
            config = new RefDataConfiguration(getId2type().get(dictionaryId));
            // try to get default column if null
            if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
                searchColumnName = config.getDefSearchColumn();
            }
        } catch (UndefinedTypeException ex) {
            ex.printStackTrace();
            throw new NoSuchDictionaryException(dictionaryId);
        }

        // if this item is not periodical -> always valid
        if (!ReferenceDataPeriodicValidityAbstractItem.class.isAssignableFrom(config.getReferenceDataAbstractItemEntityClass())) {
            return new ValidationResultImpl(dictionaryId, searchValue, true);
        }

        // for sure this is periodically valid and there are som values
        ValidationResultImpl vri = new ValidationResultImpl(dictionaryId, searchValue, true);
        for (ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity : abstractItemEntitys) {
            ReferenceDataPeriodicValidityAbstractItem item = (ReferenceDataPeriodicValidityAbstractItem) referenceDataAbstractItemEntity;
            vri.addValidity(item.getValidFrom(), item.getValidTo());
        }

        return vri;
    }

    public Collection<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItem(
            String dictionaryId, String searchColumnName, String searchValue)
            throws NoSuchDictionaryException,
            ReferenceDataSourceInternalException {

        RefDataConfiguration config;
        try {
            config = new RefDataConfiguration(getId2type().get(dictionaryId));
        } catch (UndefinedTypeException ex) {
            ex.printStackTrace();
            throw new NoSuchDictionaryException(dictionaryId);
        }
        // try to get default column if null
        if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
            searchColumnName = config.getDefSearchColumn();
        }

        if (log.isTraceEnabled()) {
            log.trace("getReferenceDataItems called for dictionary id:  " + dictionaryId + " with column: " + searchColumnName + " starts/match: " + searchValue);
        }

        if (dictionaryId == null) {
            log.warn("DictionaryID is null");
            throw new IncorrectParameterException(
                    "Incorrect parameter: dictionaryId cannot be null");
        }

        /**
         * First of all we need to know which dictionary type we are playing
         * with.
         */
        // could not detect dictionary type by id - means no dictionary with
        // given id is supported by this datasource
        if (getConfig().getDictionaryConfigMap().get(dictionaryId) == null) {
            log.error("no such dictionary : " + dictionaryId + " in " + getConfig().getDictionaryConfigMap().keySet());
            throw new NoSuchDictionaryException(dictionaryId);
        }

        if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
            log.warn("Column isn't correctly defined, value: " + searchColumnName + " is incorrect");
            throw new IncorrectParameterException(
                    "Incorrect parameter: search column name cannot be null");
        }

        /**
         * Check if we can search by a given column
         */
        if (!isSearchColumnSupportedByItem(config.getReferenceDataAbstractItemEntityClass(),
                searchColumnName)) {
            log.error("Invalid column name: " + searchColumnName + ", for dictionary with id " + dictionaryId + " of type: " + config.getReferenceDataAbstractItemEntityClass().getName());
            searchColumnName = config.getDefSearchColumn();
        } else {
        }

        /**
         * If searchValue is not blank- limit results
         */
        String limitColumnValueSubQuery = "";

        if (!GenericValidator.isBlankOrNull(searchValue)) {
            limitColumnValueSubQuery = " AND item." + searchColumnName // safe
                    + " LIKE :value ";
        }

        /**
         * Preparing HQL statement
         */
        String queryString = "SELECT item FROM org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType type, " + config.getReferenceDataAbstractItemEntityClass().getName() + " item WHERE type.refDataId = :refDataId " + " AND item.referenceDataAbstractDataTypeEntity = type " + limitColumnValueSubQuery // safe
                + " ORDER BY item." + searchColumnName + "";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("refDataId", dictionaryId);

        // inject value object if limitColumnValueSubQuery was used
        if (!GenericValidator.isBlankOrNull(limitColumnValueSubQuery)) {
            query.setParameter("value", searchValue);
        }

//        if(logger.isInfoEnabled()) logger.info("Query: " + queryString);
        List<ReferenceDataAbstractItemEntity> outputList = query.getResultList();

        if (log.isTraceEnabled()) {
            log.trace("List of referenceDataAbstractItem items obtained successfully, found: " + outputList.size() + " items");
        }

        return outputList;

    }

    /**
     * Method checks if given searchColumnName is supported by given ItemClass
     *
     * @param itemClass - class of the item to be verified
     * @param searchColumnName - name of the column to be verified
     */
    private boolean isSearchColumnSupportedByItem(
            Class<? extends ReferenceDataAbstractItemEntity> itemClass,
            String searchColumnName) {
        // Checking if required field exists
        for (Method method : itemClass.getMethods()) {
            if (convertGetterIntoField(method.getName()).compareTo(
                    searchColumnName) == 0) {
                return true;
            }
        }

        log.error("Invalid column name: " + searchColumnName + ", for item: " + itemClass);

        return false;

    }

    private Query getQueryForVersion(String dictionaryId) {
        String queryString = "SELECT VERSIONNUMBER FROM " + "REF_TYPE_NAME_AND_DESC_MAPP " + "WHERE REF_DATA_ID = :refDataId";
        Query query = entityManager.createNativeQuery(queryString);

        query.setParameter("refDataId", dictionaryId);
        return query;
    }

    /**
     * @see
     * org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB#getItemsList(String,
     * String, String, String, int, int, Date, boolean)
     */
    public QueryResultImpl getItemsList(String dictionaryId,
            String searchColumnName, String value, Date referenceDate,
            String languageCode, int itemsStart, int itemsCount,
            boolean startsWith) throws ReferenceDataSourceInternalException,
            NoSuchDictionaryException {

        // try to get default column if null
        RefDataConfiguration config;
        try {
            config = new RefDataConfiguration(getId2type().get(dictionaryId));
            // try to get default column if null
            if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
                searchColumnName = config.getDefSearchColumn();
            }
        } catch (UndefinedTypeException ex) {
            ex.printStackTrace();
            throw new NoSuchDictionaryException(dictionaryId);
        }

        if (log.isTraceEnabled()) {
            log.trace("Call for bean (getPagedQueryResultDirectly) details: id " + dictionaryId + ", languageCode " + languageCode + ", column " + searchColumnName + ", value " + value + ", itemsStart " + itemsStart + ", itemsCount " + itemsCount + ", refDataDate " + referenceDate + "");
        }

        /**
         * First of all get general dictionary information
         */
        DictionaryInfo dictionaryInfo;
        dictionaryInfo = this.getDictionaryInfo(dictionaryId, languageCode);

        /**
         * Then get list of items that match the query
         */
        Collection<ReferenceDataAbstractItemEntity> refCollection = this.getReferenceDataAbstractItem(dictionaryId, searchColumnName,
                value, referenceDate, startsWith);

        /**
         * As results returns only subset of them converted to DictionaryItem
         *
         * Paging of dictionary items. Selects just necessary elements for
         * specific page of magnifying glass component.
         */
        List<DictionaryItem> dictionaryItemList = new LinkedList<DictionaryItem>();
        int numberOfElements = refCollection.size();
        // if number of elements in collection is less then its size
        if (itemsStart >= numberOfElements) {
            itemsStart = numberOfElements - 1;
        }
        // itemstarts cannot be less 0
        if (itemsStart < 0) {
            itemsStart = 0;
        }

        // if items count from itemsStart exceeds number of elements decrease
        // itemsCount
        if (itemsStart + itemsCount > numberOfElements) {
            itemsCount = numberOfElements - itemsStart;
        }

        if (log.isTraceEnabled()) {
            log.trace("Using item start: " + itemsStart + ", and items count: " + itemsCount);
        }

        if (refCollection != null && refCollection.size() > 0) {
            int counter = 0;
            for (ReferenceDataAbstractItemEntity referenceDataAbstractItem : refCollection) {
                // if counter is in selected scope - between itemsStart and
                // itemsStart
                if (counter >= itemsStart && counter < itemsStart + itemsCount) {
                    // add DictionaryItem created from persistent item to the
                    // list
                    dictionaryItemList.add(referenceDataAbstractItem.getDictionaryItem(languageCode));
                }
                counter++;
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Returned collection size:  " + dictionaryItemList.size());
        }

        return new QueryResultImpl(dictionaryId, dictionaryInfo.getName(),
                dictionaryInfo.getDescription(), referenceDate,
                numberOfElements, languageCode, searchColumnName,
                dictionaryItemList, itemsStart);
    }

    /**
     * @see
     * org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB#removeDataTypeEntityByRefId(java.lang.String)
     */
    public void removeDataTypeEntityByRefId(String dictionaryId) {

        if (log.isTraceEnabled()) {
            log.trace("Remote call for bean (removeDataTypeEntityByRefId), required refId: " + dictionaryId + "");
        }

        if (getId2typeNoCache().get(dictionaryId) == null) {
            log.error("Cannot remove an entity of ReferenceDataAbstractDataType, data doesn't exist in database.");
        } else {
            // find object to remove
            String queryString = "SELECT ent FROM " + "org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType " + "ent WHERE ent.refDataId = :refDataId";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("refDataId", dictionaryId);
            ReferenceDataAbstractDataType type = (ReferenceDataAbstractDataType) query.getSingleResult();
            entityManager.remove(type);
            if (log.isInfoEnabled()) {
                log.info("An instance of ReferenceDataAbstractDataType has been removed");
            }
        }

    }

    /**
     * Method converts getter method into a field name. In case of any problems
     * with input String method will return input value.
     *
     * @param getter name of getter method
     * @return text which represents field name of database table column
     */
    private static String convertGetterIntoField(String getter) {

        if (getter != null && getter.length() > 3 && getter.compareTo("get") != 0 && getter.compareTo("is") != 0) {

            if (getter.startsWith("get")) {

                String tmp = getter.substring(3, getter.length());

                return Character.toLowerCase(tmp.charAt(0)) + tmp.substring(1, tmp.length());

            } else if (getter.startsWith("is")) {

                String tmp = getter.substring(2, getter.length());

                return Character.toLowerCase(tmp.charAt(0)) + tmp.substring(1, tmp.length());

            } else {

                return getter;

            }

        } else {

            return getter;

        }

    }

    static EjbReferenceDataConfig ejbReferenceDataConfig;
    static HashMap<String, String> id2type = null;

    public HashMap<String, String> getId2type() {
        if (id2type == null || checkForOtherModSetNextTime("id2type")) {
            HashMap<String, String> id2type2 = new HashMap<String, String>();

            String queryString = "SELECT TYPE, REF_DATA_ID FROM REF_TYPE_NAME_AND_DESC_MAPP ";
            Query query = entityManager.createNativeQuery(queryString);
            List list = query.getResultList();

            for (Object o : list) {
                try {
                    Object[] ob = (Object[]) o;
                    id2type2.put((String) ob[1], ((String) ob[0]).substring(0, ((String) ob[0]).length() - 8));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            id2type = id2type2;
        }
        return id2type;
    }

    public HashMap<String, String> getId2typeNoCache() {

        HashMap<String, String> id2type = new HashMap<String, String>();

        String queryString = "SELECT TYPE, REF_DATA_ID FROM REF_TYPE_NAME_AND_DESC_MAPP ";
        Query query = entityManager.createNativeQuery(queryString);
        List list = query.getResultList();

        for (Object o : list) {
            try {
                Object[] ob = (Object[]) o;
                id2type.put((String) ob[1], ((String) ob[0]).substring(0, ((String) ob[0]).length() - 8));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return id2type;
    }

    public IReferenceDataConfig getConfig() {
        if (ejbReferenceDataConfig == null || checkForOtherModSetNextTime("ejbReferenceDataConfig")) {
//            log.info(Thread.currentThread().getName() +": begin ejbReferenceDataConfig creating ejbReferenceDataConfig");
            ejbReferenceDataConfig = new EjbReferenceDataConfig(getId2type());
//            log.info(Thread.currentThread().getName() +": end ejbReferenceDataConfig created - " + ejbReferenceDataConfig.getDictionaryConfigMap().keySet());
        }
        return ejbReferenceDataConfig;
    }

    public <T extends ReferenceDataAbstractItemEntity> Collection<T> getReferenceDataAbstractItem(Class<T> clazz, String dictionaryId,
            List<Map.Entry<String, Object>> column_value, Date referenceDate, boolean startsWith)
            throws ReferenceDataSourceInternalException, NoSuchDictionaryException {

        if (log.isInfoEnabled()) {
            log.info("getReferenceDataAbstractItem called for"
                    + "item type: " + clazz.getSimpleName() + " dictionary id:  '" + dictionaryId + "' with column: '" + Arrays.toString(column_value.toArray()) + "' starts/match: '" + Arrays.toString(column_value.toArray()) + "', referenceDate: " + referenceDate);
        }

        if (dictionaryId == null) {
            log.warn("DictionaryID is null");
            throw new IncorrectParameterException(
                    "Incorrect parameter: dictionaryId cannot be null");
        }
        for (Map.Entry<String, Object> entry : column_value) {
            String searchColumnName = entry.getKey();
            if (Validator.isColumnNameNullOrEmpty(searchColumnName)) {
                log.warn("Column isn't correctly defined, value: '" + searchColumnName + "' is incorrect");
                throw new IncorrectParameterException(
                        "Incorrect parameter: search column name cannot be null");
            }
        }

        // if date is null use date set to now
        if (referenceDate == null) {
            referenceDate = DateHelper.clearTime(new java.util.Date());
        } else {
            referenceDate = DateHelper.clearTime(referenceDate);
        }

        /**
         * First of all we need to know which dictionary type we are playing
         * with.
         */
        // could not detect dictionary type by id - means no dictionary with
        // given id is supported by this datasource
        if (getConfig().getDictionaryConfigMap().get(dictionaryId) == null) {
            throw new NoSuchDictionaryException(dictionaryId);
        }

        /**
         * Check if we can search by a given column
         */
        for (Map.Entry<String, Object> entry : column_value) {
            String searchColumnName = entry.getKey();
            if (!isSearchColumnSupportedByItem(clazz, searchColumnName)) {
                throw new IncorrectParameterException("Invalid column name: '" + searchColumnName + "', for dictionary with id '" + dictionaryId + "' of type: " + clazz.getName());
            }
        }
        /**
         * Prepare HQL substring for periodic validity - limit result to the
         * given date
         */
        String limitDateSubQuery = "";
        if (ReferenceDataPeriodicValidityAbstractItem.class.isAssignableFrom(clazz)) {
            limitDateSubQuery = " AND (" + "( :date BETWEEN item.validFrom AND item.validTo )  " + " OR (:date >= item.validFrom AND item.validTo IS NULL)" + " OR (:date <= item.validTo AND item.validFrom IS NULL)" + " OR (item.validFrom IS NULL AND item.validTo IS NULL)" + ")";
        }

        /**
         * If searchValue is not blank- limit results
         */
        String limitColumnValueSubQuery = "";
        // pity - cannot set searchColumn as parameter
        // look at
        // http://www.stpe.se/2008/07/hibernate-hql-like-query-named-
        // parameters/
        // no SQL injection as searchColumnName has be validated as existing
        // item attribute
        String firstColumn = null;
        int i = 1;
        for (Map.Entry<String, Object> entry : column_value) {
            if (firstColumn == null) {
                firstColumn = entry.getKey();
            }
            i++;
            if (!GenericValidator.isBlankOrNull(entry.getKey())) {
                limitColumnValueSubQuery += " AND item." + entry.getKey() // safe
                        + " LIKE :value" + i + " ";
            }
        }
        logger.info("----------------------------------------------------");
        logger.info("limitColumnValueSubQuery = " + limitColumnValueSubQuery);
        logger.info("----------------------------------------------------");

        /**
         * Preparing HQL statement
         */
        String queryString = "SELECT item FROM org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType type, " + clazz.getName() + " item WHERE type.refDataId = :refDataId " + " AND item.referenceDataAbstractDataTypeEntity = type " + limitColumnValueSubQuery + limitDateSubQuery // safe
                + " ORDER BY item." + firstColumn + "";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("refDataId", dictionaryId);

        // inject data object if limitDateSubquery was used
        if (!GenericValidator.isBlankOrNull(limitDateSubQuery)) {
            query.setParameter("date", referenceDate);
        }

        i = 1;
        for (Map.Entry<String, Object> entry : column_value) {
            i++;
            // inject value object if limitColumnValueSubQuery was used
            if (!GenericValidator.isBlankOrNull(limitColumnValueSubQuery)) {
                // if startsWith is set to true extends search from exact matching
                if (startsWith) {
                    query.setParameter("value" + i, entry.getValue() + "%");
                } else {
                    query.setParameter("value" + i, entry.getValue());
                }
            }
        }

//		if (logger.isLoggable(Level.FINEST)) {
//			logger.finest("Query: " + queryString);
//		}
//
//		if(logger.isInfoEnabled()) logger.info("Query: " + queryString);
        List<T> outputList = (List<T>) query.getResultList();

        if (log.isTraceEnabled()) {
            log.trace("List of " + clazz.getSimpleName() + " items obtained successfully, found: " + outputList.size() + " items");
        }

        return outputList;
    }
}
