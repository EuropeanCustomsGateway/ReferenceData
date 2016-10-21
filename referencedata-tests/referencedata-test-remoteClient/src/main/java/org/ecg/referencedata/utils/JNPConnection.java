package org.ecg.referencedata.utils;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * utility to get connection to remote bean
 *
 */
public class JNPConnection<T> {

    private static Log log = LogFactory.getLog(JNPConnection.class);
    String namingContextFactory = "org.jnp.interfaces.NamingContextFactory";
    String jnpNamingUrlPackages = "org.jboss.naming rg.jnp.interfaces";
    String jnpLocation = "";
    private T ejbBean;

    public JNPConnection(String jnpLocation) {
        this(jnpLocation, null, null);
    }

    public JNPConnection(String jnpLocation, String namingContextFact) {
        this(jnpLocation, namingContextFact, null);
    }

    public JNPConnection(String jnpLocation, String namingContextFact, String jnpNamingUrlPkgs) {
        if (namingContextFact != null) this.namingContextFactory = namingContextFact;
        if (jnpNamingUrlPkgs != null) this.jnpNamingUrlPackages = jnpNamingUrlPkgs;
        if (jnpLocation != null) this.jnpLocation = jnpLocation;
    }

    public T getEjbBean() {

        if (ejbBean == null) {
            
            Context ctx;
            String jndiNameOfBean, hostAndPort;
            try {

                if (jnpLocation.startsWith("jnp://")) {
                    jnpLocation = jnpLocation.substring(6);
                }
                int slashIndex = jnpLocation.indexOf("/");
                if (slashIndex < 3) {
                    throw new Exception("Cannot find slash \"/\" character at proper place in jndi Reference path: " + jnpLocation);
                }

                hostAndPort = jnpLocation.substring(0, slashIndex);
                jndiNameOfBean = jnpLocation.substring(slashIndex + 1, jnpLocation.length());

                Properties properties = new Properties();
                properties.put("java.naming.factory.initial", namingContextFactory);
                properties.put("java.naming.factory.url.pkgs", jnpNamingUrlPackages);
                properties.put("java.naming.provider.url", "jnp://" + hostAndPort);

                ctx = new InitialContext(properties);

                ejbBean = (T) ctx.lookup(jndiNameOfBean);

                if(log.isTraceEnabled()) log.trace("Connection to "+jnpLocation+" established");

            } catch (Exception e) {
                throw new RuntimeException("Connection error to " + jnpLocation, e);
            }
        }

        return ejbBean;
    }

    public String getJnpLocation() {
        return jnpLocation;
    }

    


}
