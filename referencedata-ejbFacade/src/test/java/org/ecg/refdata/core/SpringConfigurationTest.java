package org.ecg.refdata.core;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.IDictionaryConfig;
import org.ecg.refdata.exceptions.CommunicationException;
import org.ecg.refdata.exceptions.NoSuchDictionaryException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;

/**
 * Current class tests production configuration of <CODE>spring-beans.xml</CODE>
 * . It uses basic business interface <CODE>ReferenceDataSource</CODE> to
 * communicate with bean. Class tests configuration by invoking methods of EJB
 * business interface <CODE>ReferenceDataSource</code> for all dictionary types
 * available in spring configuration file.
 * 
 * This test cannot be made in referencedata-core as no DS are available
 * there
 * 
 * 
 * TO MAKE TEST RUNNABLE IN BADLY CONFIGURED ENVIRNOMET
 * ignoreCommunicationException flag has been introduced TO BE SURE YOUR
 * CONFIGURATION IS CORRECT SET ignoreCommunicationException TO FALSE YOU WILL
 * NEED TO HAVE ALL DS (especially remote via EJB) AVAILABLE!!!
 * 
 * 
 * 
 */
public class SpringConfigurationTest {

	private static boolean ignoreCommunicationException = true;

	/**
	 * Logger.
	 */
	private static Logger logger = Logger
			.getLogger(SpringConfigurationTest.class.getName());

	/**
	 * Location of production configuration of spring beans.
	 */
	private final static String CONFIGURATION_FILE = "/org/ecg/refdata/spring-beans.xml";

	private final static String[] languageCodesToVerify = new String[] { "PL",
			"EN" };

	/**
	 * Reference to persistence bean.
	 */
	private static ReferenceDataSource REFERENCE_DATA_SOURCE;

	/**
	 * map with dictionaries configuration loaded by the SpringReferenceData
	 */
	private static Map<String, IDictionaryConfig> mapDictionaryConfig;

	/**
	 * Loading spring configuration from configuration file.
	 */
	@BeforeClass
	public static void initialize() {
		try {
			// it must be SpringReferenceData
			ReferenceDataSpringDataSource referenceDataSpringDataSource = (ReferenceDataSpringDataSource) new ReferenceDataFactory()
					.getReferenceDataSource(CONFIGURATION_FILE);
			// use ReferenceDataFactory

			mapDictionaryConfig = referenceDataSpringDataSource
					.getDictionarydonfigMap();
			REFERENCE_DATA_SOURCE = referenceDataSpringDataSource;

		} catch (ReferenceDataSourceInitializationException e) {
			logger.log(Level.SEVERE, "Could not initialize datasource, "
					+ e.getMessage(), e);
			if (!ignoreCommunicationException)
				Assert.fail("Could not initialize datasource, "
						+ e.getMessage());
		}

		logger.info("Reference to Spring configuration "
				+ "successfully obtained from " + CONFIGURATION_FILE);

	}

	/**
	 * Method uses basic business interface <CODE>ReferenceDataSource</CODE> to
	 * communicate with bean. Class tests configuration by invoking
	 * <CODE>getItemsList</CODE> method of EJB business interface
	 * <CODE>ReferenceDataSource</code>.
	 */
	@Test
	public void getItemsListTest() {

		/*
		 * It doesn't matter what kind of respond do we get as long as we can
		 * predict and catch appropriate exceptions. We are testing spring
		 * configuration via ReferenceDataSource business interface.
		 */

		Set<String> keysConfigItem = mapDictionaryConfig.keySet();

		logger
				.info("Test of spring configuration (testing method: getItemsList)");

		// test all dictionaries - all configured dictionariesIds
		for (String id : keysConfigItem) {

			IDictionaryConfig cnf = mapDictionaryConfig.get(id);

			// test search columns
			String columnName = cnf.getDefSearchColumn();

			// check all supported languages
			for (String langCode : languageCodesToVerify) {

				try {
					REFERENCE_DATA_SOURCE.getItemsList(id, columnName, "",
							new Date(), langCode, 1, 2, null, false);
				} catch (NoSuchDictionaryException e) {

					Assert
							.fail("No such dictionary exception"
									+ e.getMessage());
				} catch (CommunicationException e) {
					if (!ignoreCommunicationException)
						Assert
								.fail("Reference data source communication exception"
										+ e.getMessage());
					else {
						logger
								.warning("Communication exception occured - ignored according to test configuration ");
					}

				} catch (ReferenceDataSourceInternalException e) {

					Assert.fail("Reference data source exception"
							+ e.getMessage());

				}

			}

		}

		logger.info("Tested successfully");

	}

	/**
	 * Method uses basic business interface <CODE>ReferenceDataSource</CODE> to
	 * communicate with underlying referenceDataSources. Class tests
	 * configuration by invoking <CODE>getUniqueItem</CODE> method of *
	 * <CODE>ReferenceDataSource</code>.
	 */
	@Test
	public void getUniqueItemTest() {

		/*
		 * It doesn't matter what kind of respond do we get as long as we can
		 * predict and catch appropriate exceptions. We are testing spring
		 * configuration via ReferenceDataSource business interface.
		 */

		Set<String> keysConfigItem = mapDictionaryConfig.keySet();

		logger
				.info("Test of spring configuration (testing method - getUniqueItem)");

		for (String id : keysConfigItem) {

			IDictionaryConfig cnf = mapDictionaryConfig.get(id);

			// test search columns
			String columnName = cnf.getDefSearchColumn();

			// check all supported languages
			for (String langCode : languageCodesToVerify) {

				try {

					// we do not know value for given searchColumn - hence we
					// can only check value that is surely not valid
					// so should not return any result in any language
					REFERENCE_DATA_SOURCE.getUniqueItem(id, columnName,
							"noSuchValue", new Date(), langCode, null, false);

				} catch (NoSuchDictionaryException e) {
					Assert
							.fail("No such dictionary exception"
									+ e.getMessage());
				} catch (CommunicationException e) {
					if (!ignoreCommunicationException)
						Assert
								.fail("Reference data source communication exception"
										+ e.getMessage());
					else {
						logger
								.warning("Communication exception occured - ignored according to test configuration");
					}

				} catch (ReferenceDataSourceInternalException e) {
					Assert.fail("Reference data source exception"
							+ e.getMessage());
				}

			}

		}

	}

}
