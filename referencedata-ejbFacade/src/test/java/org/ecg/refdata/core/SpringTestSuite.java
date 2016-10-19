package org.ecg.refdata.core;

import junit.framework.TestSuite;

/**
 * Test suite for validation of Spring core
 *
 */
public class SpringTestSuite extends TestSuite {

    public static TestSuite suite() {

        TestSuite suite = new TestSuite();
        // suite.addTest(new JUnit4TestAdapter(SpringConfigurationTest.class));

        return suite;
    }
}
