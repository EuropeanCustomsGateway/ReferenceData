package org.ecg.referencedata.remote;

import org.ecg.referencedata.utils.ReferenceDataConfigUtil;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReferenceDataConnection <T> {
    private static Log log = LogFactory.getLog(ReferenceDataConnection.class);

    String namingContextFactory;
    String jnpLocation;
    String jnpNamingUrlPackages;

    T referenceData;

    public ReferenceDataConnection() {
        this(null,null,null);
    }
    public ReferenceDataConnection(String jnpLocation) {
        this(jnpLocation,null,null);
    }
    public ReferenceDataConnection(String jnpLocation,String namingContextFact) {
        this(jnpLocation,namingContextFact,null);
    }
    public ReferenceDataConnection(String jnpLoc, String namingContextFact, String jnpNamingUrlPkgs) {
        this.namingContextFactory = namingContextFact;
        this.jnpLocation = jnpLoc;
        this.jnpNamingUrlPackages = jnpNamingUrlPkgs;

        if(namingContextFactory == null)
            namingContextFactory = ReferenceDataConfigUtil.getStringParam("namingContextFactory", "org.jnp.interfaces.NamingContextFactory");
        if(jnpNamingUrlPkgs == null)
            jnpNamingUrlPkgs = ReferenceDataConfigUtil.getStringParam("jnpNamingUrlPkgs", "org.jboss.naming rg.jnp.interfaces");
        if(jnpLocation == null)
            jnpLocation = ReferenceDataConfigUtil.getStringParam("jnpLocation", "localhost:1199/referencedata-ear/PersistenceDataSourceSBBean/remote");

		Context ctx;
		String jndiNameOfBean, hostAndPort;
		try {

			int slashIndex = jnpLocation.indexOf("/");
			if (slashIndex < 3) {
				throw new Exception("Cannot find slash \"/\" character at proper place in jndi Reference path: "+ jnpLocation);
			}

			hostAndPort = jnpLocation.substring(0, slashIndex);
			jndiNameOfBean = jnpLocation.substring( slashIndex + 1, jnpLocation.length());

			Properties properties = new Properties();
			properties.put("java.naming.factory.initial",namingContextFactory);
			properties.put("java.naming.factory.url.pkgs","org.jboss.naming rg.jnp.interfaces");
			properties.put("java.naming.provider.url", "jnp://" + hostAndPort);

			ctx = new InitialContext(properties);

            referenceData = (T) ctx.lookup(jndiNameOfBean);
            
			log.debug("Connection to EJB established");

		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    public T getReferenceData() {
        return referenceData;
    }
    

    
}
