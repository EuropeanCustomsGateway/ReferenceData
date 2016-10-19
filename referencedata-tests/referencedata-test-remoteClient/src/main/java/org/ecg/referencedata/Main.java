package org.ecg.referencedata;

import org.ecg.refdata.IDictionaryConfig;
import org.ecg.refdata.IReferenceDataConfig;
import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB;
import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSBBean;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;
import org.ecg.refdata.ejb.ReferenceDataSB;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.QueryResult;
import org.ecg.refdata.query.impl.QueryResultImpl;
import org.ecg.referencedata.utils.JNPConnection;
import org.ecg.referencedata.utils.Show;
import org.ecg.referencedata.utils.XmlToDatabase;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {

    private static Log log = LogFactory.getLog(Main.class);

    public static void main(String[] args) {
        new Main().run(args);
    }

    public class XmlFilter implements FilenameFilter {

        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".xml");
        }
    }

    PersistenceDataSourceSBBean localInstance;
    PersistenceDataSourceSB remoteInstance;
    IReferenceDataConfig config;
    XmlToDatabase dataGeneratorFromXMLlocal;
    String path;

    String perisstentDS;
    String JNPDS;
    String referenceDS;
    String mdasRefDataWsdl;

    boolean realInserts;

    public PersistenceDataSourceSBBean getLocalInstance() {
        if (localInstance == null) {
            localInstance = (PersistenceDataSourceSBBean) dataGeneratorFromXMLlocal.getEjbReferenceData();
        }
        return localInstance;
    }

    public PersistenceDataSourceSB getRemoteInstance() {
        if (remoteInstance == null) {
            XmlToDatabase dataGeneratorFromXMLremote = new XmlToDatabase(JNPDS, realInserts); // insertMode = true =>  insert into db
            remoteInstance = dataGeneratorFromXMLremote.getEjbReferenceData();
        }
        return remoteInstance;
    }

    public XmlToDatabase getDataGeneratorFromXMLlocal() {
        if (dataGeneratorFromXMLlocal == null) {
            dataGeneratorFromXMLlocal = new XmlToDatabase(perisstentDS, realInserts); // insertMode = true =>  insert into db
        }
        return dataGeneratorFromXMLlocal;
    }

    public IReferenceDataConfig getConfig() {
        if (config == null) {
            config = getLocalInstance().getConfig();
        }
        return config;
    }

    void run(String[] args) {
        Params params = new Params(args);

        try {
            Properties p = new Properties();
            p.load(getClass().getResourceAsStream("/RefDataLoader.properties"));
            path = params.get("-arg1", "path to directory with xml reference data");

            realInserts = params.isSet("-realInserts", "if the program should only pretend to put data (no data is stored in target db)");
            perisstentDS = params.getOpt("-dbds", p.getProperty("dbds"), "direct_jdbc_oracle connection to PersistenceDataSourceSBBean (used by this program to put data do database)");
            JNPDS = params.getOpt("-remote", p.getProperty("remote"), "jnpconnection to PersistenceDataSourceSBBean (used by this program to put data do database)");
            referenceDS = params.getOpt("-xmlds", p.getProperty("xmlds"), "jnp to ReferenceDataSBBean (used to get stored dictionaries)");
            mdasRefDataWsdl = params.getOpt("-wsdl", p.getProperty("wsdl"), "jnp to ReferenceDataSBBean (used to get stored dictionaries)");

            boolean loadDataFromXml = params.isSet("-loadDataFromXml", "if program should do the update of data from xml dictionaries ");
            boolean testConfig = params.isSet("-testConfig", "if program should run test Dict Configuration");
            boolean test16LT = params.isSet("-test16LT", "if program should run specific test for 16LT dict");
            boolean testRefreshCache = params.isSet("-testRefreshCache", "if program should run test for cache refreshing");
            boolean testIsValid = params.isSet("-testIsValid", "if program should run test for 'isValid'  function for all dictionaries in refdata");
            boolean showRemoteDicts = params.isSet("-showRemoteDicts", "if program should show remote dictionaries ( run after load)");

            File file = new File("./lib");

            for (File lib : file.listFiles()) {
                ClassLoaderUtil.addFile(lib);
            }

            if (!params.isValid()) {
                throw new Exception("wrong command line parameters ");
            } else {

                if (testConfig) {
                    testConfig();
                }
                if (test16LT) {
                    test16LT();
                }
                if (testRefreshCache) {
                    testRefreshCache();
                }
                if (testIsValid) {
                    testIsValid();
                }

                if (loadDataFromXml) {
                    loadDataFromXml();
                }

                if (showRemoteDicts) {
                    showRemoteDicts();
                }

            }

        } catch (Exception ex) {
            System.err.println("-----------------------");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.err.println("-----------------------");
            params.usage("java -Xmx1024m -jar thisFile.jar </path/to/xml/ref/data/dir> [-checkMode|-runTest] [-insertMode] [-dbds <path1>] [-xmlds <path2>]");
            System.err.println("-----------------------");
        }
    }

//    private void testXmlMessages(boolean valid) throws Exception{
//        File dir ;
//        System.out.println("-------------------------------------------------------------");
//        System.out.println("testing if messages are "+(valid ?"valid":"invalid"));
//        System.out.println("-------------------------------------------------------------");
//        if(valid){
//           dir = new File(path_xml_valid);
//        }else{
//           dir = new File(path_xml_invalid);
//        }
//        for (File file : dir.listFiles()) {
//            int times = new BigDecimal(send_one_times).intValue();
//            if(times > 1) System.out.println("validatng the same message ~'at once' " + times + " times");
//            for (int i = 0; i < times; i++) {
//                Thread t = new Checker(file, valid);
//                t.start();
//            }
//            
//        }
//
//        System.out.println("-------------------------------------------------------------");
//        
//    }
//    class Checker extends Thread{
//        File file;
//        boolean valid;
//
//        public Checker(File file, boolean valid) {
//            this.file = file;
//            this.valid = valid;
//        }
//        public void run() {
//            byte[] xmlMessage = null;
//            String messageType = null;
//            ValidationResult result = null;
//            try {
//                xmlMessage = IOUtils.toByteArray(new FileInputStream(file));
//            messageType = ParserHelper.parseNamespace(new String(xmlMessage));
//            } catch (Exception ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            result = new RefDataValidationClient_v1r0(Main.class.getResource("/RefDataValidationService.wsdl"))
//                    .getService(mdasRefDataWsdl,  null, null).validate(messageType, xmlMessage, new Date());
//            System.out.println("Result = " + result.getResult().getResultCode());
//            System.out.println("Results = " + result.getResultInfo().size());
//            if(valid){
//                if(result.getResultInfo().size() > 0){
//                    System.out.println("message was validated as incorrect while it should be correct");
//                    System.out.println("*******");
//                    for (ResultInfo resultInfo : result.getResultInfo()) {
//                        System.out.println("**");
//                        System.out.println("Description:"+resultInfo.getDescription());
//                        System.out.println("Originalvalue:"+resultInfo.getOriginalvalue());
//                        System.out.println("Pointer:"+resultInfo.getPointer());
//                        System.out.println("ResultType:"+resultInfo.getResultType());
//                        System.out.println("Rule:"+resultInfo.getRule());
//                    }
//                    System.out.println("*******");
//                    throw new RuntimeException("message was validated as incorrect while it should be correct");
//                }
//            }else{
//                if(result.getResultInfo().size() == 0 && result.getResult().getResultCode() == 1){
//                    System.out.println("message was validated as correct while it should be incorrect");
//                    throw new RuntimeException("message was validated as correct while it should be incorrect");
//                }
//            }
//        }        
//    }
    private void loadDataFromXml() throws Exception {
        Set<String> added = getDataGeneratorFromXMLlocal().fillDatabase(path);
        System.out.print("Added to DataSource finished adding :");
        String list[] = new String[0];
        list = added.toArray(list);
        Arrays.sort(list);
        System.out.println(Arrays.toString(list));
        list = new String[0];
        list = getDataGeneratorFromXMLlocal().getEjbReferenceData().getListOfAllIds().toArray(list);
        if (list != null && list.length > 0) {
            Arrays.sort(list);
            System.out.println(Arrays.toString(list));
        } else {
            System.out.println("database on server is empty... something went wrong.");
        }
    }

    private void showRemoteDicts() {
        ReferenceDataSB rdsb = new JNPConnection<ReferenceDataSB>(referenceDS).getEjbBean();
        System.out.println("referenceDataSB: " + rdsb);
        String dicts[] = new String[getConfig().getDictionaryConfigMap().keySet().size()];
        getConfig().getDictionaryConfigMap().keySet().toArray(dicts);
        Arrays.sort(dicts);
        StringBuffer res = new StringBuffer();
        for (String id : dicts) {
            id = id.trim();
            try {
                QueryResult qr = rdsb.getUniqueItem(id, null, "", new Date(), "EN", null, false);
                Show.object(qr, "dictionaryId");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                res.append(ex.getMessage());
                res.append("\n");
            }
        }
        if (res.length() > 0) {
            System.out.println(res);
            throw new RuntimeException();
        }
    }

    private void testConfig() {
        try {
            System.out.println("test getConfig");
            System.out.println("testing dictionaries : " + Arrays.toString(getConfig().getDictionaryConfigMap().keySet().toArray()));

            for (Entry<String, IDictionaryConfig> ent : getConfig().getDictionaryConfigMap().entrySet()) {
                if (ent.getValue().getRefDataSource() != null) {
                    System.out.println("error:  ent.getValue().getRefDataSource() = " + ent.getValue().getRefDataSource());
                };
                System.out.println("==================================================");
                System.out.println(ent.getKey());
                System.out.println("==================================================");
                System.out.println("getDefSearchColumn      : " + ent.getValue().getDefSearchColumn());
                System.out.println("getDefItemsOnPage       : " + ent.getValue().getDefItemsOnPage());
                System.out.println("getDefItemsOnPage       : " + ent.getValue().getDefItemsOnPage());
                System.out.println("getDefMinTextSearchLengt: " + ent.getValue().getDefMinTextSearchLength());
                System.out.println("getDictionariesIds      : " + ent.getValue().getDictionariesIds());
                System.out.println("getPreferredColumns     : " + Arrays.toString(ent.getValue().getPreferredColumns()));
                System.out.println("----");
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private void test16LT() {
        try {
            if (getConfig().getDictionaryConfigMap().containsKey("16LT")) {
                System.out.println("---------------------------------------------------------------");
                System.out.println("test 16LT");

//                System.out.println("id " + getRemoteInstance().getReferenceDataAbstractDataType("16LT").getId());
                System.out.println("id " + getRemoteInstance().getDictionaryInfo("16LT", "EN").getDescription());

                QueryResultImpl result = (QueryResultImpl) getRemoteInstance().getItemsList("16LT", null, "0103", null, "0001", 0, 10, true);
                System.out.println(result.getSearchColumn());
                System.out.println(result.getTotalCount());
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private void testRefreshCache() {

        try {
            System.out.println("--------------------------------------------------");
            System.out.println("--------------------------------------------------");
            System.out.println("isValid");

            List<String[]> codeValue = new ArrayList<String[]>();

            for (String id : getConfig().getDictionaryConfigMap().keySet()) {
                QueryResultImpl qi = getLocalInstance().getItemsList(id, null, "", null, "EN", 0, 4, true);
                for (DictionaryItem dictionaryItem : qi.getItems()) {
                    codeValue.add(new String[]{id, (String) BeanUtils.getProperty(dictionaryItem, (getConfig().getDictionaryConfigMap().get(id)).getDefSearchColumn())});
                }
            }
//            codeValue.add(new String[]{"8E", "LT"});
//            codeValue.add(new String[]{"8E", "LT"});
//            codeValue.add(new String[]{"8E", "LT"});
//            codeValue.add(new String[]{"8E", "LT"});
//            codeValue.add(new String[]{"87", "G"});
//            codeValue.add(new String[]{"87", "G"});
//            codeValue.add(new String[]{"87", "G"});
//            codeValue.add(new String[]{"87", "G"});

            StringBuffer results = new StringBuffer();

            System.out.println("--------------------------------------------------");
            System.out.println("--------------------------------------------------");
            System.out.println("-----------------first  time----------------------");
            System.out.println("--------------------------------------------------");
            System.out.println("--------------------------------------------------");

            testIsValidIns(codeValue, remoteInstance, results);

            Thread.sleep(1000 * 10);

            System.out.println("--------------------------------------------------");
            System.out.println("--------------------------------------------------");
            System.out.println("-----------------second time----------------------");
            System.out.println("--------------------------------------------------");
            System.out.println("--------------------------------------------------");

            testIsValidIns(codeValue, remoteInstance, results);
            getLocalInstance().getEntityManager().getTransaction().begin();

            getLocalInstance().getEntityManager().createNativeQuery("UPDATE REF_TYPE_NAME_AND_DESC_MAPP "
                    + "SET VERSIONNUMBER = (VERSIONNUMBER + 1) ").executeUpdate();

            getLocalInstance().getEntityManager().getTransaction().commit();

            Thread.sleep(1000 * 10);

            System.out.println("--------------------------------------------------");
            System.out.println("--------------------------------------------------");
            System.out.println("-----------------third  time----------------------");
            System.out.println("--------------------------------------------------");
            System.out.println("--------------------------------------------------");

            testIsValidIns(codeValue, remoteInstance, results);
            getLocalInstance().getEntityManager().getTransaction().begin();

            getLocalInstance().getEntityManager().createNativeQuery("UPDATE REF_TYPE_NAME_AND_DESC_MAPP "
                    + "SET VERSIONNUMBER = (VERSIONNUMBER - 1) ").executeUpdate();

            getLocalInstance().getEntityManager().getTransaction().commit();

            if (results.length() > 0) {
                System.err.println(results.toString());
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private void testIsValid() {
        try {
            System.out.println("--------------------------------------------------");
            System.out.println("getListOfAllIds");
            System.out.println("--------------------------------------------------");
            Date date = new Date();

            List<TestCheck> list = createDictTestList2(localInstance, true);

            for (TestCheck check : list) {
                check.checkIsValid(remoteInstance, date);
            }

            System.out.println("----------------------------=-----------------------------");
            System.out.println("results that should be valid: ");
            System.out.println("----------------------------=-----------------------------");
            for (TestCheck qit : list) {
                qit.showTime();
            }

            list = createDictTestList2(localInstance, false);

            for (TestCheck check : list) {
                check.checkIsValid(remoteInstance, date);
            }

            System.out.println("----------------------------=-----------------------------");
            System.out.println("results that should be invalid : ");
            System.out.println("----------------------------=-----------------------------");
            for (TestCheck qit : list) {
                qit.showTime();
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    List<TestCheck> createDictTestList2(PersistenceDataSourceSB instance, boolean valid) throws Exception {
        List<TestCheck> list = new LinkedList<TestCheck>();

        IReferenceDataConfig result = instance.getConfig();
        Set<String> dictids = result.getDictionaryConfigMap().keySet();

        Collection<ReferenceDataAbstractItemEntity> col;
        Iterator<ReferenceDataAbstractItemEntity> it;
        DictionaryItem di;
        System.out.println("testing dictionaries : " + Arrays.toString(dictids.toArray()));

        for (String dict : dictids) {
            if (dict.matches("16LT")) {
                continue;
            }
            String column = "code";
            try {

                if (result.getDictionaryConfigMap().get(dict) == null) {
                    System.out.println("dictionary with id '" + dict + "' is not configured");
                    continue;
                }
                column = (result.getDictionaryConfigMap().get(dict)).getDefSearchColumn(); //dicts.getKey();
                System.out.println(">>>>>>>> getting data from '" + dict + "'");
                col = instance.getReferenceDataAbstractItem(dict, null, null, null);
                System.out.println(">>>>>>> col.size = " + col.size());
//                assert col.size() <= 10;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            try {
                it = col.iterator();
                while (it.hasNext()) {
                    ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity = it.next();
                    di = referenceDataAbstractItemEntity.getDictionaryItem(dict);
                    list.add(new TestCheck(
                            dict,
                            column,
                            valid ? BeanUtils.getProperty(di, column) : "inv-" + BeanUtils.getProperty(di, column) + "@!#$!@#!",
                            new Date())
                    );

                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return list;

    }

    void testIsValidIns(List<String[]> codeValue, PersistenceDataSourceSB instance, StringBuffer results) throws Exception {
        for (String[] codes : codeValue) {
            String dict = codes[0];
            String code = codes[1];
            String col = null;
            boolean isValid;
            long start = System.nanoTime();
            isValid = instance.isValid(dict, col, code, new Date());
            System.out.println("isValid dt = " + (((double) (System.nanoTime() - start)) / 1000000) + " ms");
            if (!isValid) {
                results.append("value '" + code + "' should be valid for dict '" + dict + "' ");

                QueryResult res = instance.getItemsList(dict, col, code, null, "EN", 0, 100000, false);

                if (res.getItems().size() > 0) {
                    results.append("what is worse the result exist in databse");
                } else {
                    results.append("result is not present id db");
                }
                results.append("\n");
            }
            QueryResult res = instance.getItemsList(dict, col, code, null, "EN", 0, 100000, false);

            if (res.getItems().isEmpty()) {
                results.append("value '" + code + "' should be present for dict '" + dict + "' ");
            } else {
                DictionaryItem di = res.getItems().iterator().next();
                System.out.println(ReflectionToStringBuilder.toString(di));
            }
        }
    }

    /**
     * <p>
     * Simple class to ease command line arguments handling.
     * </p>
     * <p>
     * <b>Usage </b><br><br>
     * 1. construct <code> Params params = new Params(args); </code> <br>
     * 2. then specify arguments using <code>get(..), getOpt(..), isSet(..)
     * </code> <br>
     * 3. check if f arguments are valid using <code>isValid()</code> <br>
     * 4. if not valid then print out usage with method
     * <code>usage("string with example usage")</code>
     *
     * </p>
     *
     */
    public class Params {

        HashMap<String, String> m = new HashMap<String, String>();
        private boolean valid = true;
        TreeMap<String, String> required = new TreeMap<String, String>();
        TreeMap<String, String> optional = new TreeMap<String, String>();
        TreeMap<String, String> defaults = new TreeMap<String, String>();
        TreeMap<String, String> flags = new TreeMap<String, String>();

        public Params(String args[]) {
            for (int i = 0; i < args.length;) {
                String name = args[i];
                if (name.startsWith("-")) {
                    String value = ((i + 1) < args.length) ? args[i + 1] : null;
                    if (value != null && !value.startsWith("-")) {
                        m.put(name, value);
                        i += 2;
                    } else {
                        m.put(name, null);
                        i += 1;
                    }
                } else {
                    i += 1;
                    m.put("-arg" + i, name);
                }
            }
        }

        /**
         * gets required parameter
         *
         * @param key parmeter name with eg -param
         * @param argDescription parameter description
         * @return
         */
        public String get(String key, String argDescription) {
            required.put(key, argDescription);
            if (!m.containsKey(key)) {
                valid = false;
            }
            return m.get(key);
        }

        /**
         * gets required parameter
         *
         * @param key parmeter name with eg -param
         * @param argDescription parameter description
         * @param ignore_required if for some reason reguirement sould be
         * ingored
         * @return
         */
        public String get(String key, String argDescription, boolean ignore_required) {
            required.put(key, argDescription);
            if (!m.containsKey(key) && !ignore_required) {
                valid = false;
            }
            return m.get(key);
        }

        /**
         * gets optional parameter
         *
         * @param key parmeter name with eg -param
         * @param defaultValue default value for the parameter
         * @param argDescription parameter description
         * @return
         */
        public String getOpt(String key, String defaultValue, String argDescription) {
            optional.put(key, argDescription);
            defaults.put(key, defaultValue);
            if (!m.containsKey(key)) {
                m.put(key, defaultValue);
            }
            return m.get(key);
        }

        /**
         * checks if flag is set
         *
         * @param key a flag eg -flag
         * @param argDescription
         * @return
         */
        public boolean isSet(String key, String argDescription) {
            flags.put(key, argDescription);
            return m.containsKey(key);
        }

        /**
         * Checks if specified parameters are valid
         *
         * @return returns true if valid, false if errors occured
         */
        public boolean isValid() {
            if (m.containsKey("-v")) {
                showDefaults();
            }
            if (m.containsKey("-h") || m.containsKey("--help") || m.containsKey("-help")) {
                return false;
            }
            return valid;
        }

        /**
         * prints out usage based on specified parameters
         *
         * @param usage
         */
        public void usage(String usage) {
            System.out.println("Usage: \n");
            System.out.println("  " + usage);
            System.out.println("  [-h |--help ]: show this help ");
            System.out.println("  [-v ]:         show default values");
            System.out.println("\nRequired arguments: \n");
            for (Entry e : required.entrySet()) {
                System.out.println("\t" + e.getKey() + ":\t" + e.getValue());
            }
            System.out.println("\nOptional arguments: \n");
            for (Entry e : optional.entrySet()) {
                System.out.println("\t" + e.getKey() + ":\t" + e.getValue() + ", default value = '" + defaults.get(e.getKey()) + "'");
            }
            System.out.println("\nflags: \n");
            for (Entry e : flags.entrySet()) {
                System.out.println("\t" + e.getKey() + ":\t" + e.getValue());
            }
        }

        /**
         * prints out default values for optional parameters
         */
        public void showDefaults() {
            System.out.println("Defaults : ");
            for (Entry e : defaults.entrySet()) {
                System.out.println(e.getKey() + " : '" + e.getValue() + "'");
            }
        }

    }

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

        void checkGetItemsList(PersistenceDataSourceSB instance) throws Exception {
            checkTime = System.nanoTime();
            instance.getItemsList(dictionaryId, searchColumnName, value, referenceDate, "en", 0, 1, false);
            checkTime = System.nanoTime() - checkTime;
        }

        void checkIsValid(PersistenceDataSourceSB instance, Date date) throws Exception {
            checkTime = System.nanoTime();
            valid = instance.isValid(dictionaryId, searchColumnName, value, date);
            checkTime = System.nanoTime() - checkTime;
        }

        void showTime() {
            System.out.println("check for " + (valid ? "valid" : "invalid") + " value '" + value + "' in '" + dictionaryId + "." + searchColumnName + "' took : " + (((double) checkTime / (1000 * 1000))) + " ms ");
        }
    }

}
