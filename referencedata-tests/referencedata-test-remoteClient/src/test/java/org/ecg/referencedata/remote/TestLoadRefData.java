/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ecg.referencedata.remote;

import org.ecg.refdata.ejb.ReferenceDataSB;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.QueryResult;
import org.ecg.referencedata.utils.JNPConnection;
import org.ecg.referencedata.utils.Show;
import org.ecg.referencedata.utils.XmlToDatabase;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.util.file.FileSuffixFilter;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class TestLoadRefData {

    enum Config {
        TEST("10.133.12.197:1299", "ICDTS_TEST", "sasasa", false),
        DEV("10.133.12.198:1299", "ICDTS_DEV", "sasasa", false),
        LOCAL("127.0.0.1:1299", "REFDATA2", "sasasa", false),
        komp254("komp254:1299", "REFDATA2", "sasasa", false),
        TEST_DIRECT("10.133.12.197:1299", "ICDTS_TEST", "sasasa", true),
        DEV_DIRECT("10.133.12.198:1299", "ICDTS_DEV", "sasasa", true),
        LOCAL_DIRECT("localhost:1299", "REFDATA2", "sasasa", true),
        //MDASTEST("mdastest.skg.pl:1299","REFDATA2","sasasa",false);
        MDASTEST("195.116.105.139:1299", "REFDATA2", "sasasa", false);

        String dir;
        String dictionaries;

        String host;
        String user;
        String pass;
        boolean direct;

        private Config(String host, String user, String pass, boolean direct) {
            dictionaries = "CUN,LocalClearance,16LT,19LT,20LT,21LT,33LT,35LT,10,8I,CH,CRH,COL,23LT,1LT,2LT,1," +
                    "101E,101I,103,104,105,106I,108I,109I,10LT,11,116E,116I,11LT,12,12LT,13LT,14LT,15,15LT,17,17LT," +
                    "18,18LT,20,21,22,22LT,23,24,24LT,25LT,26,27LT,28LT,29LT,30,30LT,31E,31LT,32,32LT,33,34,34LT," +
                    "35,36,36LT_13E,36LT_13I,37,37LT_14E,37LT_14I,38LT_39E,38LT_39I,39LT_45E,39LT_45I,3LT,40LT_47," +
                    "41,58,42LT_98,43E,43I,43LT_102E,43LT_102I,44LT_91,45LT_49,46E,46I,47,47LT,49,51,53,56,5LT,60E," +
                    "60I,67,68E,68I,69,70,79,7LT,86,87,89,8E,8LT,90,92E,92I,93E,93I,94,95,96E,96I,97E,97I,99,9LT,UNL,6LT";

            dir = "D:/Projects/Od Roberta/reference data/";


            this.host = host;
            this.user = user;
            this.pass = pass;
            this.direct = direct;
        }

        String getReferenceDataSB() {
            return "" + host + "/skg-referencedata-ear-1.2-SNAPSHOT/ReferenceDataSBBean/remote";
        }

        String getPersistenceDataSB() {
            if (direct == false) {
                return "" + host + "/referencedata-ear/PersistenceDataSourceSBBean/remote";
            } else {
                return "jdbc:oracle:thin:@10.133.12.191:1521:ecs/" + user + ":" + pass + "";
            }
        }

    }

    public TestLoadRefData() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    private String ReferenceDataSB;
    private String PersistenceDataSB;
    private String XMLDir;
    private String dictionaries_include;
    private String dictionaries_exclude;
    boolean fill = false;
    boolean show = false;


    @Before
    public void setUp() {

        Config config = Config.LOCAL_DIRECT;
//       Config config = Config.DEV_DIRECT;
//       Config config = Config.TEST_DIRECT;
//       Config config = Config.MDASTEST;
        dictionaries_exclude = "16LT";
//       dictionaries_include = "16LT";
        dictionaries_include = "36LT_13E";

        ReferenceDataSB = config.getReferenceDataSB();
        PersistenceDataSB = config.getPersistenceDataSB();
        XMLDir = config.dir;
//       fill = true;
        show = true;
    }

    @Test
    public void fillDatabase() throws Exception {
        if (fill) {
            // Use for data import from xml
            //        XmlToDatabase dataGeneratorFromXML = new XmlToDatabase(PersistenceDataSB,false); //false => do not inserts into db.
            XmlToDatabase dataGeneratorFromXML = new XmlToDatabase(PersistenceDataSB, true); //true =>  insert into db

            File[] xsds = new File(XMLDir).listFiles(new FileSuffixFilter("xml"));
            for (File file : xsds) {
                if (dictionaries_exclude != null && file.toString().matches(".*(" + dictionaries_exclude.replace(",", "|") + ")\\.xml"))
                    continue;
                if (dictionaries_include != null && !file.toString().matches(".*(" + dictionaries_include.replace(",", "|") + ")\\.xml"))
                    continue;
                String id = dataGeneratorFromXML.fillDatabase(file);
                System.out.println("id = " + id);
            }
        }
    }

    @Test
    public void testGetDict() {
        if (show) {
            System.out.println("==================================");
            ReferenceDataSB rdsb = new JNPConnection<ReferenceDataSB>(ReferenceDataSB).getEjbBean();
            String dicts[] = new String[]{};
            String exclude[] = new String[]{};
            if (dictionaries_include != null) {
                dicts = StringUtils.split(dictionaries_include, "\\s*,\\s*");
            } else {
                File[] xsds = new File(XMLDir).listFiles(new FileSuffixFilter("xml"));
                List<String> aldicts = new ArrayList<String>();
                for (File file : xsds) {
                    aldicts.add(StringUtils.substring(file.getName(), 0, -4));
                }
                dicts = new String[aldicts.size()];
                dicts = aldicts.toArray(dicts);
            }
            Arrays.sort(dicts);
            if (dictionaries_exclude != null) {
                exclude = StringUtils.split(dictionaries_exclude, "\\s*,\\s*");
                Arrays.sort(exclude);
            }

//        ReferenceDataAbstractDataType at;
            StringBuffer res = new StringBuffer();
            for (String id : dicts) {
                id = id.trim();
                if (Arrays.binarySearch(exclude, id) >= 0) continue;
                try {
                    System.out.println("getting item for id = " + id);

                    QueryResult qr = rdsb.getItemsList(id, null, "3040", new Date(), "LT", 0, 1000, null, false);
                    Show.collection(qr.getItems());

                    for (DictionaryItem item : qr.getItems()) {
                        String prop = BeanUtils.getProperty(item, qr.getSearchColumn());
                        System.out.println("code '" + prop + "' is " + (rdsb.isValid(id, null, prop, new Date(), null, false) ? "valid" : "invalid"));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                    res.append(id + " : " + ex.getMessage());
                    res.append("\n");
                }

            }
            if (res.length() > 0) {
                System.out.println(res);
                assert res.length() == 0;

            }
        }
    }

    public class XmlFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".xml");
        }
    }

}
