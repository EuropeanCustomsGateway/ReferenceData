package org.ecg.refdata.factory;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <CODE>AbstractReferenceDataFactory</CODE> class create a new instance of
 * current class by class name from parameter.
 *
 */
public abstract class AbstractReferenceDataFactory {

    private static final long serialVersionUID = 1L;

    private static final Log logger = LogFactory.getLog(AbstractReferenceDataFactory.class);

    /**
     * Create ReferenceDataSource using given configuration string
     *
     * @param configuration data source configuration parameter
     * @return instance of ReferenceDataSource
     * @throws ReferenceDataSourceInitializationException is thrown if data
     * source cannot be accessed/initialized
     */
    public abstract ReferenceDataSource getReferenceDataSource(String configuration)
            throws ReferenceDataSourceInitializationException;

    /**
     * Create AbstractReferenceDataFactory instance by instantiating class name
     * given as a parameter
     *
     * @param className name of a concrete factory class to instantiate
     * @return instance of AbstractReferenceDataFactory class or throws
     * exception, should never return null!
     */
    @SuppressWarnings("unchecked")
    public static AbstractReferenceDataFactory instantiate(String className)
            throws ReferenceDataSourceInitializationException {

        Class clazz;

        try {

            logger.info("Creating instance of " + className + " class");

            clazz = Class.forName(className);
            Object factoryInstance = clazz.newInstance();
            logger.info("Instantiation of " + className + " has been finished");
            return (AbstractReferenceDataFactory) factoryInstance;

        } catch (ClassNotFoundException e) {
            logger.error("Cannot find a class: " + className, e);
            throw new ReferenceDataSourceInitializationException(e);
        } catch (InstantiationException e) {
            logger.error("Instantiation of class: " + className
                    + " failed", e);
            throw new ReferenceDataSourceInitializationException(e);
        } catch (IllegalAccessException e) {
            logger.error("Illegal access during instantiation for class: "
                    + className, e);
            throw new ReferenceDataSourceInitializationException(e);
        }

    }

}
