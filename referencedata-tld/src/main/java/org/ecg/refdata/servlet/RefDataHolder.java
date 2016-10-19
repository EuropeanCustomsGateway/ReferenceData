package org.ecg.refdata.servlet;

import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.apache.commons.validator.GenericValidator;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.ejb.JBossReferenceDataEJBFacadeFactory;
import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.ecg.refdata.factory.AbstractReferenceDataFactory;

public class RefDataHolder {

    private static final long serialVersionUID = 1L;

    private static ReferenceDataSource referenceDataSourceInstance = null;

    private static Logger logger = Logger.getLogger(RefDataServlet.class
            .getName());

    // keys
    private static final String REFERENCE_DATA_FACTORY_CLASS_KEY = "referenceDataFactoryClass";
    private static final String REFERENCE_DATA_FACTORY_CONFIG_KEY = "referenceDataFactoryConfigString";

    // default values
    private static final Class<? extends AbstractReferenceDataFactory> DEFAULT_REFERENCE_DATA_FACTORY_CLASS = JBossReferenceDataEJBFacadeFactory.class;
    private static final String DEFAULT_REFERENCE_DATA_FACTORY_CONFIG = "localhost:1099/ReferenceDataSBBean/remote";

    // current config
    private static String config;
    private static String factoryClassName;

    /**
     * Initialize referenceDataSource
     */
    public static final void configureDataFactory(
            ServletContext servletContext, RefDataServlet refDataServlet) {

        if (referenceDataSourceInstance != null) {
            logger
                    .warning("reference data source can be initalize only once - will not override settings!");
        } else {

            // obtaining REFERENCE_DATA_FACTORY_CONFIG_KEY and
            config = refDataServlet
                    .getInitParameter(REFERENCE_DATA_FACTORY_CONFIG_KEY);

            if (GenericValidator.isBlankOrNull(config)) {
                config = servletContext
                        .getInitParameter(REFERENCE_DATA_FACTORY_CONFIG_KEY);
            }

            if (GenericValidator.isBlankOrNull(config)) {
                config = DEFAULT_REFERENCE_DATA_FACTORY_CONFIG;
                logger
                        .info("Cannot find ReferenceDataSource configuration. Default init source "
                                + config);
            } else {
                logger
                        .warning("ReferenceDataSource configuration found. And will be initialysed from "
                                + config);
            }

            factoryClassName = refDataServlet
                    .getInitParameter(REFERENCE_DATA_FACTORY_CLASS_KEY);

            if (GenericValidator.isBlankOrNull(factoryClassName)) {
                factoryClassName = servletContext
                        .getInitParameter(REFERENCE_DATA_FACTORY_CLASS_KEY);
            }

            if (GenericValidator.isBlankOrNull(factoryClassName)) {
                factoryClassName = DEFAULT_REFERENCE_DATA_FACTORY_CLASS.getName();
                logger.info("Cannot find ReferenceDataSource configuration. Instance will be created from default "
                                + factoryClassName + " class");
            } else {
                logger
                        .warning("ReferenceDataSource configuration found. And will be initialysed using "
                                + factoryClassName + " class");
            }
        }
    }

    /**
     * Method initialize connection based on configuration strings:
     * <code>referenceDataFactoryClass</code> and
     * <code>referenceDataFactoryConfigString</code>
     *
     * @throws ReferenceDataSourceInitializationException
     */
    private synchronized static void initializeConnection()
            throws ReferenceDataSourceInitializationException {

        if (config == null || factoryClassName == null) {
            throw new ReferenceDataSourceInitializationException(
                    "Could not initialize connection, config or factoryClassName is not set, "
                    + "configureDataFactoryMethod must be called first!!!! ");
        }

        try {

            AbstractReferenceDataFactory refDataFactory = AbstractReferenceDataFactory.instantiate(factoryClassName);
            logger.finer("AbstractReferenceDataFactory instantiated from: " + factoryClassName);

            referenceDataSourceInstance = refDataFactory.getReferenceDataSource(config);
            logger.fine("ReferenceDataSource initialzed from: " + config);

        } catch (Exception e) {
            logger.severe("Could not instantiate AbstractReferenceDataFactory from " + factoryClassName);
            throw new ReferenceDataSourceInitializationException("Could not instantiate AbstractReferenceDataFactory from " + factoryClassName, e);
        }

    }

    /**
     * Method gets reference do data source. If it's 'null', then initialize.
     *
     * @return reference to data source
     * @throws ReferenceDataSourceInitializationException
     */
    public static final ReferenceDataSource getReferenceDataSource()
            throws ReferenceDataSourceInitializationException {

        if (referenceDataSourceInstance == null) {
            initializeConnection();
        }

        if (referenceDataSourceInstance == null) {
            logger.severe("Problem with initialisation datasource instance");
            throw new ReferenceDataSourceInitializationException(
                    "Problem with initialisation datasource instance");
        }

        return referenceDataSourceInstance;
    }
}
