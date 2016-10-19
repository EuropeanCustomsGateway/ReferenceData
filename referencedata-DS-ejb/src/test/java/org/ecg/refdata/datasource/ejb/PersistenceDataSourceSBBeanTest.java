package org.ecg.refdata.datasource.ejb;

import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSBBean;
import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB;
import org.ecg.refdata.datasource.ejb.config.RefDataConfiguration;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;
import org.ecg.refdata.datasource.entities.countryUnavailability.CountryUnavailabilityItem;
import org.ecg.refdata.datasource.entities.countryUnavailability.SystemUnavailabilityItem;
import org.ecg.refdata.datasource.entities.exchangeRate.ExchangeRateItem;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;
import org.ecg.refdata.query.DictionaryInfo;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.impl.QueryResultImpl;
import org.ecg.refdata.query.model.CustomsOffice;
import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable;
import org.ecg.refdata.validation.ValidationResult;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

import static org.junit.Assert.*;

public class PersistenceDataSourceSBBeanTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EntityManagerFactory emf;
    private EntityManager em;
    private PersistenceDataSourceSBBean localInstance;
    private PersistenceDataSourceSB remoteInstance;

    public PersistenceDataSourceSBBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    PersistenceDataSourceSBBean getInstance() {
        HashMap map = new HashMap();
        PersistenceDataSourceSBBean inst;
        System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        map.put("javax.persistence.transactionType", "RESOURCE_LOCAL");

        emf = Persistence.createEntityManagerFactory("EjbDataSourcePersistenceUnit", map);

        em = emf.createEntityManager();

        inst = new PersistenceDataSourceSBBean();
        inst.setEntityManager(em);
        return inst;
    }

    @Before
    public void setUp() throws NamingException {
        localInstance = getInstance();
    }

    @Test
    public void Dict23LT() throws Exception {
        PersistenceDataSourceSBBean instance = getInstance();
        Collection<ExchangeRateItem> exl = (Collection<ExchangeRateItem>) (Collection) instance.getReferenceDataAbstractItem("23LT", "currency", "USD");
        Object[] dicts = instance.getListOfAllIds().toArray();
        Arrays.sort(dicts);
        logger.info("dicts:" + Arrays.toString(dicts));

        for (ExchangeRateItem ex : exl) {
            logger.info(ex.getCurrency()
                    + ", multiplier " + ex.getMultiplier() + ", rateLTL " + ex.getRateLTL() + ", rateEUR " + ex.getRateEUR());

        }
        logger.info("exl" + Arrays.toString(exl.toArray()));
        ArrayList<Map.Entry<String, Object>> cols = new ArrayList<Map.Entry<String, Object>>();
//        cols.add(new SimpleEntry<String, Object>("rateLTL", rate));
        cols.add(new SimpleEntry<String, Object>("currency", "USD"));
        Collection<ExchangeRateItem> res = instance.getReferenceDataAbstractItem(ExchangeRateItem.class, "23LT", cols, new Date(), false);
        logger.info("res" + Arrays.toString(res.toArray()));
        for (ExchangeRateItem ex : res) {
            logger.info(ex.getCurrency()
                    + ", multiplier " + ex.getMultiplier() + ", rateLTL " + ex.getRateLTL() + ", rateEUR " + ex.getRateEUR());

        }
    }

    static int insti = 0;
//    @Test

    public void isValid() throws Exception {
        int cnt = 10;
        Thread[] tds = new Thread[cnt];
        for (int i = 0; i < cnt; i++) {
            Thread t = new Thread() {

                PersistenceDataSourceSBBean instance = getInstance();

                @Override
                @SuppressWarnings("static-access")
                public void run() {
                    try {
                        for (int j = 0; j < 10; j++) {
                            logger.info(Thread.currentThread().getName() + ":"
                                    + " instance.isValid(\"38LT_39I\",\"N952\",null) =  " + instance.isValid("38LT_39I", null, "N952", null));
                            Thread.currentThread().sleep(1000);
                        }
                    } catch (InterruptedException ex) {
                        logger.info(Thread.currentThread().getName() + " ERROR :");
                        ex.printStackTrace();
                    } catch (NoSuchDictionaryException ex) {
                        logger.info(Thread.currentThread().getName() + " ERROR :");
                        ex.printStackTrace();
                    } catch (ReferenceDataSourceInternalException ex) {
                        logger.info(Thread.currentThread().getName() + " ERROR :");
                        ex.printStackTrace();
                    }
                }
            };
            tds[i] = t;
            t.setName("Thread " + i);
        }
        for (int i = 0; i < tds.length; i++) {
            Thread thread = tds[i];
            thread.start();
        }

        logger.info("sleep...");
        Thread.currentThread().sleep(1000 * 30);
        logger.info("done...");
    }

    /**
     * Test of getConfig method, of class PersistenceBeanClient.
     */
//    @Test
    public void testGetConfig() throws InterruptedException, Exception {
        logger.info("getConfig");

//        IReferenceDataConfig result = localInstance.getConfig();
//
//        for (Entry <String,IDictionaryConfig> ent: result.getDictionaryConfigMap().entrySet()) {
////            logger.info(ent.getKey());
//            assert ent.getValue().getRefDataSource() == null;
//            logger.info(ent.getKey()+":"+ent.getValue().getDefSearchColumn());
//        }
//        assert result.getDictionaryConfigMap().size() > 0;
        long t = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            t = System.currentTimeMillis();
            localInstance.getId2type().keySet();
            logger.info(">>>>>dt n2= " + (System.currentTimeMillis() - t));
//            Thread.sleep(1000*3);

        }
        logger.info(".....................");
        for (String id : localInstance.getId2type().keySet()) {
            DictionaryInfo di = localInstance.getDictionaryInfo(id, "LT");
            logger.info("______________________________________________________");
            logger.info("getDescription:" + di.getDescription());
            logger.info("getDictionaryId:" + di.getDictionaryId());
            logger.info("getName:" + di.getName());
            logger.info("getItemsOnPage:" + di.getItemsOnPage());
            logger.info("LastModificationDate():" + di.getLastModificationDate());
            logger.info("TotalCount():" + di.getTotalCount());
            logger.info("getValidColumns():" + Arrays.toString(di.getValidColumns()));
            logger.info("______________________________________________________");

        }

    }
//    @Test

    public void testUNL() throws Exception {
        logger.info("testUNL");
        Collection<CountryUnavailabilityItem> result
                = localInstance.getReferenceDataAbstractItem(
                        CountryUnavailabilityItem.class, "CUN", null, "FR", null, false);

        for (CountryUnavailabilityItem countryUnavailabilityItem : result) {
            logger.info(countryUnavailabilityItem.getCountryCode());
            for (SystemUnavailabilityItem systemUnavailabilityItem : countryUnavailabilityItem.getSystemUnavailabilityItems()) {
                logger.info("from" + systemUnavailabilityItem.getDowntimeFrom() + " to " + systemUnavailabilityItem.getDowntimeTo());
            }
        }

    }
//    @Test

    public void testCOL() throws Exception {
        logger.info("testCOL");
//        DictionaryInfo res = localInstance.getDictionaryInfo("COL", "EN");
//        logger.info("-------------------------------------------");
//        logger.info("name =" + res.getName());
//        logger.info("totalitems =" + res.getTotalCount());
//        logger.info("-------------------------------------------");
        QueryResultImpl res2 = localInstance.getItemsList("COL", null, "LTKG3000", null, "EN", 0, 10, false);

        for (DictionaryItem dictionaryItem : res2.getItems()) {
            CustomsOffice co = (CustomsOffice) dictionaryItem;
            for (CustomsOfficeTimetable cot : co.getCustomsOfficeTimetables()) {
                for (CustomsOfficeTimetable.CustomsOfficeTimetableLine cotl : cot.getCustomsOfficeTimetableLines()) {
                    for (CustomsOfficeTimetable.CustomsOfficeTimetableLine.CustomsOfficeRoleTraffic cort : cotl.getCustomsOfficeRoleTraffics()) {
                        logger.info("ROLE    = " + cort.getRole());
                        logger.info("TRAFFIC = " + cort.getTrafficType());
                    }
                }
            }
        }
    }
//    @Test

    public void test16LT() throws Exception {
        logger.info("test16LT");

        logger.info("id " + localInstance.getDictionaryInfo("16LT", "EN").getDescription());

        QueryResultImpl result = localInstance.getItemsList("16LT", null, "0103", null, "0001", 0, 10, true);
        logger.info(result.getSearchColumn());
        logger.info(result.getTotalCount() + "");

    }
//    @Test
//    public void mapValues()  throws Exception{
//        logger.info("mapValues");
//        Collection<CorrelationItem> res;
//        ArrayList<Map.Entry<String, String>> cols = new ArrayList<Map.Entry<String, String>>();
//        //code1="10" code2="00" code3="C51" code4="0"
//        // code1="10" code2="00" code3="C52" code4="1"
//
//        cols.add(new SimpleEntry<String, String>("code1","40"));
//        cols.add(new SimpleEntry<String, String>("code2","00"));
//        cols.add(new SimpleEntry<String, String>("code3","000"));
//
//        res = localInstance.getReferenceDataAbstractItem(CorrelationItem.class, "20LT", cols, null, false);
//
//
//            logger.info("[code1,code2,code3,code4]");
//        for (CorrelationItem correlationItem : res) {
//
//            logger.info("["+correlationItem.getCode1()+","+correlationItem.getCode2()+","+correlationItem.getCode3()+","+correlationItem.getCode4()+"]");
//        }
////
////        cols = new ArrayList<Map.Entry<String, String>>();
////            //code1="10" code2="00" code3="C51"
////            // code1="10" code2="00" code3="C52"
////        cols.add(new SimpleEntry<String, String>("code1","10"));
////        cols.add(new SimpleEntry<String, String>("code2","00"));
////        cols.add(new SimpleEntry<String, String>("code3","C52"));
////
////        res = instance.getReferenceDataAbstractItem(CorrelationItem.class, "20LT", cols, null, false);
////
////        for (CorrelationItem correlationItem : res) {
////            assert correlationItem.getCode4().equalsIgnoreCase("1");
////        }
//    }
//    @Test

    public void testIsVersionChange() throws Exception {
        logger.info("isValid");

        List<String[]> codeValue = new ArrayList<String[]>();

//        codeValue.add(new String[]{"101E", "0001"});
//        codeValue.add(new String[]{"101I", "0001"});
        codeValue.add(new String[]{"8E", "LT"});
        codeValue.add(new String[]{"8E", "LT"});
        codeValue.add(new String[]{"8E", "LT"});
        codeValue.add(new String[]{"8E", "LT"});
        codeValue.add(new String[]{"87", "G"});
        codeValue.add(new String[]{"87", "G"});
        codeValue.add(new String[]{"87", "G"});
        codeValue.add(new String[]{"87", "G"});
//        codeValue.add(new String[]{"12", "LT"});
//        codeValue.add(new String[]{"17", "BC"});
//        codeValue.add(new String[]{"49LT", "AEOS"});
//        codeValue.add(new String[]{"36LT_13E", "A001"});
//        codeValue.add(new String[]{"36LT_13E", "D005"});

        StringBuffer results = new StringBuffer();

        testIsValidIns(codeValue, remoteInstance, results);

        Thread.sleep(1000 * 10);

        logger.info("--------------------------------------------------");
        logger.info("--------------------------------------------------");
        logger.info("-----------------second time----------------------");
        logger.info("--------------------------------------------------");
        logger.info("--------------------------------------------------");

        testIsValidIns(codeValue, remoteInstance, results);
        localInstance.entityManager.getTransaction().begin();
        localInstance.entityManager.createNativeQuery("UPDATE REF_TYPE_NAME_AND_DESC_MAPP "
                + "SET VERSIONNUMBER = (VERSIONNUMBER + 1) WHERE  REF_DATA_ID = '87'").executeUpdate();
        localInstance.entityManager.getTransaction().commit();

        Thread.sleep(1000 * 10);

        logger.info("--------------------------------------------------");
        logger.info("--------------------------------------------------");
        logger.info("-----------------third  time----------------------");
        logger.info("--------------------------------------------------");
        logger.info("--------------------------------------------------");

        testIsValidIns(codeValue, remoteInstance, results);
//        localInstance.entityManager.getTransaction().begin();
//        localInstance.entityManager.createNativeQuery("UPDATE REF_TYPE_NAME_AND_DESC_MAPP " +
//                "SET VERSIONNUMBER = (VERSIONNUMBER - 1) WHERE  REF_DATA_ID = '87'").executeUpdate();
//        localInstance.entityManager.getTransaction().commit();

        if (results.length() > 0) {
            fail(results.toString());
        }
    }

    void testIsValidIns(List<String[]> codeValue, PersistenceDataSourceSB instance, StringBuffer results) throws Exception {
        for (String[] codes : codeValue) {
            String dict = codes[0];
            String code = codes[1];
            String col = null;
            boolean isValid;
            long start = System.nanoTime();
            isValid = instance.isValid(dict, col, code, new Date());
            logger.info("isValid dt = " + (((double) (System.nanoTime() - start)) / 1000000) + " ms");
            if (!isValid) {
                results.append("value '" + code + "' should be valid for dict '" + dict + "' ");
                Collection<ReferenceDataAbstractItemEntity> res = instance.getReferenceDataAbstractItem(dict, col, code);
                if (res.size() > 0) {
                    results.append("what is worse the result exist in databse");
                } else {
                    results.append("result is not present id db");
                }
                results.append("\n");
            }
            Collection<ReferenceDataAbstractItemEntity> res = instance.getReferenceDataAbstractItem(dict, col, code);
            if (res.isEmpty()) {
                results.append("value '" + code + "' should be present for dict '" + dict + "' ");
            } else {
                DictionaryItem di = res.iterator().next().getDictionaryItem("EN");
                logger.info(ReflectionToStringBuilder.toString(di));
            }
        }
    }

    //    @Test
    public void testSpeed() throws Exception {

//        Collection<ReferenceDataAbstractItemEntity> res =
//                instance.getReferenceDataAbstractItem("16LT", null, "0101901100");
//        logger.info("items = " + res.size());
        Collection<ReferenceDataAbstractItemEntity> res
                = localInstance.getReferenceDataAbstractItem("87", null, "");
        for (ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity : res) {
            logger.info(ReflectionToStringBuilder.toString(referenceDataAbstractItemEntity.getDictionaryItem("EN")));
        }
//
////        em.createNativeQuery("update")
//
//        res = instance.getReferenceDataAbstractItem("101E", null, "0004");
//        logger.info("items = " + res.size());
//        res = instance.getReferenceDataAbstractItem("101I", null, "0001");
//        logger.info("items = " + res.size());
//
//        res = instance.getReferenceDataAbstractItem("36LT_13E", null, "A001");
//        logger.info("items = " + res.size());

    }

    /**
     * Test of getListOfAllIds method, of class PersistenceDataSourceSBBean.
     */
//    @Test
    public void testGetListOfAllIds() throws Exception {
        logger.info("getListOfAllIds");
        Date date = new Date();
        List<TestCheck> list = createDictTestList2(localInstance, true);
        ValidationResult res;

        for (TestCheck check : list) {
            check.checkIsValid(localInstance, date);
        }

        logger.info("--------------------------------");
        logger.info("results : ");
        for (TestCheck qit : list) {
            qit.showTime();
            assertTrue("value should be valid", qit.valid);
//            Thread.currentThread().sleep(900);
        }

        list = createDictTestList2(localInstance, false);

        for (TestCheck check : list) {
            check.checkIsValid(localInstance, date);
//            Thread.currentThread().sleep(200);
        }

        logger.info("--------------------------------");
        logger.info("results 2: ");
        for (TestCheck qit : list) {
            qit.showTime();
            assertFalse("value should be invalid", qit.valid);
        }

    }

    List<TestCheck> createDictTestList2(PersistenceDataSourceSBBean instance, boolean valid) throws Exception {
        List<TestCheck> list = new LinkedList<TestCheck>();

        File dir = new File("D:/Projects/Od Roberta/reference data");

        StringBuffer sb = new StringBuffer();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".xml")) {
                sb.append(f.getName().substring(0, f.getName().length() - 4));
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);

        Collection<ReferenceDataAbstractItemEntity> col;
        Iterator<ReferenceDataAbstractItemEntity> it;
        DictionaryItem di;
        String[] dictids = sb.toString().split(",");
        logger.info("testing for existance of dictionaries : " + sb.toString());

        for (String dict : dictids) {
            if (dict.matches("16LT")) {
                continue;
            }
            if (RefDataConfiguration.getTypeForDictId(dict) == null) {
                logger.info("dictionary with id :" + dict + "is not configured");
                continue;
            }
            String column = (new RefDataConfiguration(RefDataConfiguration.getTypeForDictId(dict))).getDefSearchColumn(); //dicts.getKey();
            try {
                logger.info(">>>>>>>>getting data from '" + dict + "' default column 0, 10 ");
                col = instance.getReferenceDataAbstractItem(dict, null, null, null);
                logger.info(">>>>>>>col.size = " + col.size());
//                assert col.size() <= 10;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }

            it = col.iterator();
            while (it.hasNext()) {
                ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity = it.next();
                di = referenceDataAbstractItemEntity.getDictionaryItem(dict);
                list.add(new TestCheck(
                        dict,
                        column,
                        valid ? BeanUtils.getProperty(di, column) : "inv-" + BeanUtils.getProperty(di, column) + "@!#$!@#!",
                        new Date()));

            }
        }

        return list;

    }

    //    List<TestCheck> createDictTestList3(PersistenceDataSourceSBBean instance,boolean valid ) throws Exception{
//        HashMap<String,String> columnCode = new HashMap<String, String>();
//         List<TestCheck> list = new LinkedList<TestCheck>();
//
//        columnCode.put(
//                "code",
//                "16LT"+
//                 ",1,101E,101I,103,104,105,106I,108I,109I,10LT,11,116E,116I,11LT,12,12LT" +
//                ",13LT,14LT,15,15LT,17,17LT,18,18LT,20" +
//                  ",21,21" +
//                ",22,22LT,23,24,24LT,25LT,26,27LT,28LT,29LT,30,31E,32" +
//                ",32LT,33,34,34LT,35,36,36LT_13E,36LT_13I,37,37LT_14E,37LT_14I,38LT_39E,38LT_39I,39LT_45E,39LT_45I" +
//                ",3LT,40LT_47,41,58,42LT_98,43E,43I,43LT_102E,43LT_102I,44LT_91,45LT_49,46E,46I,47,48LT,49,51" + //,47LT
//                ",53,56,5LT,60E,60I,67,68E,68I,69,70,79,7LT,86,87,89,8LT,90,92E,92I,93E,93I,94,95,96E,96I,97E,97I,99,9LT" +
//                ",6LT"+
//                ""
//                );
//        columnCode.put("code1", "19LT,20LT,21LT,33LT,35LT,30LT,31LT");
//        columnCode.put("countryCode", "10,8I,8E,CH" +
//                ",CUN");
//        columnCode.put("countryRegionCode", "CRH");
//        columnCode.put("referenceNumber", "COL,1LT,2LT");
//        columnCode.put("rateLTL", "23LT");
//        columnCode.put("unLocodeId", "UNL");
//        columnCode.put("reference", "LocalClearance");
//        Collection<ReferenceDataAbstractItemEntity> col;
//        Iterator<ReferenceDataAbstractItemEntity> it;
//        DictionaryItem di;
//
//        for (Entry<String,String> dicts : columnCode.entrySet()) {
//            String column = null; //dicts.getKey();
//            String[] dictids = dicts.getValue().split(",");
//            for (String dict : dictids) {
//                col = instance.getReferenceDataAbstractItem(dict, column,null);
//                column = RefDataMappingEnum.forClass(instance.getReferenceDataAbstractDataType(dict).getClass()).code;
//                it = col.iterator();
//                while (it.hasNext()) {
//                    ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity = it.next();
//                    di = referenceDataAbstractItemEntity.getDictionaryItem(dict);
//                    list.add(new TestCheck(
//                                dict,
//                                column,
//                                valid ? BeanUtils.getProperty(di,column) : "inv-"+BeanUtils.getProperty(di,column) +"@!#$!@#!",
//                                new Date())
//                            );
//
//                }
//            }
//        }
//        return list;
//
//    }
    private class TestCheck {

        String dictionaryId;
        String searchColumnName;
        String value;
        Date referenceDate;
        String languageCode;
        int itemsStart;
        int itemsCount;
        long checkTime;
        Boolean valid;

        public TestCheck(String dictionaryId, String searchColumnName, String value, Date referenceDate) {
            this.dictionaryId = dictionaryId;
            this.searchColumnName = searchColumnName;
            this.value = value;
            this.referenceDate = referenceDate;
        }

        void checkGetItemsList(PersistenceDataSourceSBBean instance) throws Exception {
            checkTime = System.nanoTime();
            instance.getItemsList(dictionaryId, searchColumnName, value, referenceDate, "en", 0, 1, false);
            checkTime = System.nanoTime() - checkTime;
        }

        void checkIsValid(PersistenceDataSourceSBBean instance, Date date) throws Exception {
            checkTime = System.nanoTime();
            ValidationResult res = instance.isValid(dictionaryId, searchColumnName, value);
            valid = res.isValid(date);
            checkTime = System.nanoTime() - checkTime;
            logger.info("Exists : " + res.isExists() + ", valid: " + valid);
        }

        void showTime() {
            logger.info("check for " + (valid ? "valid" : "invalid") + " value '" + value + "' in '" + dictionaryId + "." + searchColumnName + "' took : " + (((double) checkTime / (1000 * 1000))) + " ms ");
        }
    }
}
