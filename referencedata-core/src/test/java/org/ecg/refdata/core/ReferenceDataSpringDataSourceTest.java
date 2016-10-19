package org.ecg.refdata.core;

import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.ecg.refdata.factory.AbstractReferenceDataFactory;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.QueryResult;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-beans.xml"})
public class ReferenceDataSpringDataSourceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    ReferenceDataSpringDataSource instance;

    public ReferenceDataSpringDataSourceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws ReferenceDataSourceInitializationException {
        AbstractReferenceDataFactory ardf = ReferenceDataFactory.instantiate("org.ecg.refdata.core.ReferenceDataFactory");
        instance = (ReferenceDataSpringDataSource) ardf.getReferenceDataSource("/spring-beans.xml");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getItemsList method, of class ReferenceDataSpringDataSource.
     */
    //@Test
    public void testGetItemsList() throws Exception {

    }

    /**
     * Test of getUniqueItem method, of class ReferenceDataSpringDataSource.
     */
    //@Test
    public void testGetUniqueItem() throws Exception {

    }

    /**
     * Test of getDictionarydonfigMap method, of class ReferenceDataSpringDataSource.
     */
    //@Test
    public void testGetDictionarydonfigMap() {

    }


    //@Test
    public void testRewriting() throws Exception {
        Collection<String> dictionaries = Arrays.asList(
                "MRN".split("\\s*,\\s*")
        );


        for (String dict : dictionaries) {
            QueryResult res = instance.getItemsList(dict,
                    "tin", "%", null, "EN", 0, 10, null, false);
            logger.debug("*****************************************");
            logger.debug("LanguageCode : " + res.getLanguageCode());
            logger.debug("SearchColumn : " + res.getSearchColumn());
            logger.debug("items : ");
            logger.debug("------------------------------------------");
            for (DictionaryItem item : res.getItems()) {
                String prop = BeanUtils.getProperty(item, res.getSearchColumn());
                logger.debug("item " + item + "'");
                logger.debug("code '" + prop + "' is " + (instance.isValid(dict, null, prop, new Date(), null, false) ? "valid" : "invalid"));
            }

        }

    }

    //    @Test
    public void testIsValid() throws Exception {
        List<String[]> codeValue = new ArrayList<String[]>();

        codeValue.add(new String[]{"101E", "0001"});
        codeValue.add(new String[]{"101I", "0001"});
        codeValue.add(new String[]{"8E", "LT"});
        codeValue.add(new String[]{"12", "LT"});
        codeValue.add(new String[]{"17", "BC"});
        codeValue.add(new String[]{"49LT", "AEOS"});
        codeValue.add(new String[]{"36LT_13E", "A001"});
        codeValue.add(new String[]{"36LT_13E", "D005"});

        StringBuffer results = new StringBuffer();

        for (String[] codes : codeValue) {
            String dict = codes[0];
            String code = codes[1];
            if (!instance.isValid(dict, null, code, new Date(), null, false)) {
                results.append("value '" + code + "' should be valid for dict '" + dict + "' ");
                QueryResult qr = instance.getUniqueItem(dict, null, code, null, "LT", null, false);
                if (qr.getItems().size() > 0) {
                    results.append("what is worse the result exist in databse");
                } else {
                    results.append("result is not present id db");
                }
                results.append("\n");
            }
            QueryResult qr = instance.getUniqueItem(dict, null, code, null, "LT", null, false);
            if (qr.getItems().size() < 1) {
                results.append("value '" + code + "' should be present for dict '" + dict + "' ");
            }
        }

        if (results.length() > 0) {
            fail(results.toString());
        }


    }

}