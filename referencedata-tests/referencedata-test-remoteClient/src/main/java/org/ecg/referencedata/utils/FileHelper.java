package org.ecg.referencedata.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.apache.commons.logging.Log;

public class FileHelper {

    private static final Log log = org.apache.commons.logging.LogFactory.getLog(FileHelper.class);


    /**
     * Loads the given properties file and returns a Properties object containing the
     * key,value pairs in that file.
     * The properties files should be in the class path.
     */
    public static Properties loadProperties(String propertiesName) throws IOException {
        Properties bundle = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(propertiesName);
        if (url == null) {
            throw new IOException("Properties file " + propertiesName + " not found");
        }

        log.trace("Properties file=" + url);

        InputStream is = url.openStream();
        if (is != null) {
            bundle = new Properties();
            bundle.load(is);
        } else {
            throw new IOException("Properties file " + propertiesName + " not avilable");
        }
        return bundle;
    }
}
