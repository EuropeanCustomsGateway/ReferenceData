package org.ecg.refdata.core;

import org.ecg.refdata.IReferenceDataConfig;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.ecg.refdata.factory.AbstractReferenceDataFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class is a part of abstract factory design pattern. It creates an instances
 * of particular data sources.
 *
 */
public class ReferenceDataFactory extends AbstractReferenceDataFactory {

    private IReferenceDataConfig referenceData;

    /**
     * Name of the main spring bean which implements ReferenceDataSource
     * interface.
     */
    private final static String REFERENCE_DATA_BEAN_NAME = "referenceData";
    private static final Log logger = LogFactory.getLog(ReferenceDataFactory.class);

    /**
     * Basic constructor takes just one parameter path to Spring configuration
     * file
     *
     * @see AbstractReferenceDataFactory#getReferenceDataSource(String)
     */
    public ReferenceDataSource getReferenceDataSource(String springConfigFileName)
            throws ReferenceDataSourceInitializationException {

        logger.info("Loading reference data spring configuration from " + springConfigFileName);
        AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(springConfigFileName);
        this.referenceData = (IReferenceDataConfig) appContext.getBean(REFERENCE_DATA_BEAN_NAME);

        if (referenceData == null) {
            throw new ReferenceDataSourceInitializationException(
                    "Invalid spring configuration. Please check availability of "
                    + "bean nameed: \"" + REFERENCE_DATA_BEAN_NAME
                    + "\" in \"" + springConfigFileName);
        }

        logger.info("Spring configuration successfully loaded.");

        return new ReferenceDataSpringDataSource(referenceData);

    }

}
