package org.ecg.refdata.datasource.ejb.client;

import org.ecg.refdata.datasource.ejb.client.PersistenceBeanClient;
import org.ecg.refdata.ConfiguredReferenceDataSource;
import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSBBean;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.QueryResult;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class PersistenceBeanClientTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EntityManagerFactory emf;
    private EntityManager em;
    private PersistenceDataSourceSBBean dataSourceSBBean;
    private ConfiguredReferenceDataSource referenceDataSource;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws NamingException {
        HashMap map = new HashMap();

        System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        map.put("javax.persistence.transactionType", "RESOURCE_LOCAL");

        emf = Persistence.createEntityManagerFactory("EjbDataSourcePersistenceUnit", map);
        em = emf.createEntityManager();

        dataSourceSBBean = new PersistenceDataSourceSBBean();
        dataSourceSBBean.setEntityManager(em);
        PersistenceBeanClient psbc = new PersistenceBeanClient();
        psbc.setEjbReferenceData(dataSourceSBBean);
        referenceDataSource = psbc;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRewriting() throws Exception {
//        IReferenceDataConfig result = referenceDataSource.getConfig();
//        Collection<String> dictionaries = result.getDictionaryConfigMap().keySet();
        Collection<String> dictionaries = Arrays.asList(
                "CH,COL,CUN".split("\\s*,\\s*")
        );

        for (String dict : dictionaries) {
            QueryResult res = referenceDataSource.getItemsList(dict,
                    null, "", null, "LT", 0, 10, null, false);
            logger.info("*****************************************");
            logger.info("LanguageCode : " + res.getLanguageCode());
            logger.info("SearchColumn : " + res.getSearchColumn());
            logger.info("items : ");
            logger.info("------------------------------------------");
            for (DictionaryItem item : res.getItems()) {
                String prop = BeanUtils.getProperty(item, res.getSearchColumn());
                logger.info("code '" + prop + "' is " + (referenceDataSource.isValid(dict, null, prop, new Date(), null, false) ? "valid" : "invalid"));
            }
        }
    }
}
