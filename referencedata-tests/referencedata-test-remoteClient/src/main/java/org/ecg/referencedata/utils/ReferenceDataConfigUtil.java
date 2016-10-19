package org.ecg.referencedata.utils;

import java.util.Properties;

public class ReferenceDataConfigUtil {
    private static  boolean     ibMessage;
    private static Properties   configProperties;
    static {
        try {
            configProperties = FileHelper.loadProperties("ReferenceDataWSClient.properties");
            ibMessage = true;
        } catch(Exception e) {
            e.printStackTrace();
            ibMessage = false;
        }
    }
    
    public static int getIntParam(String key, int defaultValue) {
        if (ibMessage) {
            try {
                String stringValue = configProperties.getProperty(key,String.valueOf(defaultValue));
                int returnValue = Integer.parseInt(stringValue);
                return returnValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }
    
    public static String getStringParam(String key, String defaultValue) {
        if (ibMessage) {
            try {
                String stringValue = configProperties.getProperty(key,defaultValue);
                return stringValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }
}
