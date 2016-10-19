package org.ecg.refdata.datasource.ejb.config;

import org.ecg.refdata.datasource.ejb.config.RefDataConfiguration;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;

public class RefDataConfigurationTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public RefDataConfigurationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDictionaryIds method, of class RefDataConfiguration.
     */
    @Test
    public void testGetDictionaryIds() {

    }

    /**
     * Test of getTypeForDictId method, of class RefDataConfiguration.
     */
    @Test
    public void testGetTypeForDictId() {
    }

    File toplevel = null;
    String errors = new String();

    @Test
    public void testForRightConfig() throws IOException, ClassNotFoundException {
        Enumeration<URL> res;
        URL u;
        try {
            res = ClassLoader.getSystemClassLoader().getResources("");

            while (res.hasMoreElements()) {
                u = res.nextElement();
                if (u.getFile().matches(".*/test-.*")) {
                    continue;
                }
                File f = toplevel = new File(u.getFile());
                recursiveCheck(f);
            }

            if (errors.length() > 0) {
                logger.info(errors);
                fail("error(s)");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void recursiveCheck(File f) throws ClassNotFoundException {

        if (f.isDirectory()) {
            for (File f2 : f.listFiles()) {
                recursiveCheck(f2);
            }
        } else if (f.isFile()) {
            if (f.getName().endsWith("class") && !f.getName().startsWith("JiBX")) {

                String clazz = f.getAbsolutePath().substring(toplevel.getAbsolutePath().length() + 1).replace(File.separator, ".");
                clazz = clazz.substring(0, clazz.length() - 6);
                Pattern p = Pattern.compile(".+\\.([\\w]+)DataType$");
                Matcher m = p.matcher(clazz);
                if (!m.matches()) {
                    return;
                }

                if (clazz.endsWith("AbstractDataType")) {
                    return;
                }

                try {
                    String type = m.group(1);
                    RefDataConfiguration configuration = new RefDataConfiguration(type);
                    logger.info(type + ": " + Arrays.toString(configuration.getDictionaries()));
                } catch (Exception e) {
                    errors += e.getMessage();
                }

                Class c = ClassLoader.getSystemClassLoader().loadClass(clazz);
                if (c.isInterface()) {
                    return;
                }

            } else {

            }
        }
    }

    /**
     * Test of getIstance method, of class RefDataConfiguration.
     */
    @Test
    public void testGetIstance() throws Exception {

    }

    /**
     * Test of getDictionaries method, of class RefDataConfiguration.
     */
    @Test
    public void testGetDictionaries() {
    }

    /**
     * Test of getDefForceUpperCase method, of class RefDataConfiguration.
     */
    @Test
    public void testGetDefForceUpperCase() {

    }

    /**
     * Test of getDefItemsOnPage method, of class RefDataConfiguration.
     */
    @Test
    public void testGetDefItemsOnPage() {
        ;
    }

    /**
     * Test of getDefMinTextSearchLength method, of class RefDataConfiguration.
     */
    @Test
    public void testGetDefMinTextSearchLength() {

    }

    /**
     * Test of getDefSearchColumn method, of class RefDataConfiguration.
     */
    @Test
    public void testGetDefSearchColumn() {

    }

    /**
     * Test of getRefDataSource method, of class RefDataConfiguration.
     */
    @Test
    public void testGetRefDataSource() {

    }

    /**
     * Test of getPreferredColumns method, of class RefDataConfiguration.
     */
    @Test
    public void testGetPreferredColumns() {

    }

    /**
     * Test of getCache method, of class RefDataConfiguration.
     */
    @Test
    public void testGetCache() {

    }

}
