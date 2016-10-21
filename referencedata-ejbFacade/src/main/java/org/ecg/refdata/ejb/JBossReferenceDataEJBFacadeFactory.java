package org.ecg.refdata.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.exceptions.ReferenceDataSourceInitializationException;
import org.ecg.refdata.factory.AbstractReferenceDataFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class implements factory pattern and creates an instance which represents
 * specific data source.
 *
 */
public class JBossReferenceDataEJBFacadeFactory extends
        AbstractReferenceDataFactory {

    private static final Log log = LogFactory.getLog(JBossReferenceDataEJBFacadeFactory.class);

    /**
     * @throws ReferenceDataSourceInitializationException
     *
     */
    public ReferenceDataSource getReferenceDataSource(String jndiReference)
            throws ReferenceDataSourceInitializationException {

        log.info("Obtaining reference to EJBFacade Datasource, using jndiReference: "
                + jndiReference);

        Context ctx;

        try {

            int slashIndex = jndiReference.indexOf("/");

            // shouldn't return 0, 1, 2 because there should be additional space for host, ':' and port
            if (slashIndex < 3) {

                log.error("Path to jndi reference is wrong. Please use proper path for jndi reference");

                throw new ReferenceDataSourceInitializationException(
                        "Cannot find slash \"/\" character at proper place in jndi Reference path: " + jndiReference);

            }

            String hostAndIp = jndiReference.substring(0, slashIndex);

            String jndiNameOfBean = jndiReference.substring(slashIndex + 1, jndiReference.length());

            log.info("Parsing connection information for "
                    + this.getClass().getName() + " remote host: " + hostAndIp
                    + ", jndi path: " + jndiNameOfBean + ".");

            Properties properties = new Properties();

            properties.put("java.naming.factory.initial",
                    "org.jnp.interfaces.NamingContextFactory");

            properties.put("java.naming.factory.url.pkgs",
                    "org.jboss.naming rg.jnp.interfaces");
            properties.put("java.naming.provider.url", "jnp://" + hostAndIp);

            ctx = new InitialContext(properties);

            ReferenceDataSB referenceData = (ReferenceDataSB) ctx
                    .lookup(jndiNameOfBean);

            log.info("Reference to EJBFacade Datasource successfully obtained");

            return referenceData;

        } catch (Exception e) {

            log.error("Could not access ReferenceDataSB using: "
                    + jndiReference + ", reason: " + e.getMessage());
            throw new ReferenceDataSourceInitializationException(
                    "Could not access ReferenceDataSB using: " + jndiReference,
                    e);

        }

    }
}
