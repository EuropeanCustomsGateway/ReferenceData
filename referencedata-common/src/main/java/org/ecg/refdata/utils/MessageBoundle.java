package org.ecg.refdata.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * odwołanie do metoda statycznych przez klasę, 
 * dodanie konstruktora przyjmującego klasę - nazwa properties brana jest z nazwy klasy
 */
public class MessageBoundle implements IMessageBoundle {

    private static final long serialVersionUID = 1L;
    private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(MessageBoundle.class);
    protected String resourceName = null;

    /**
     * Creates a new instance of MessageBoundle
     */
    public MessageBoundle(String resourceName) {
        this.resourceName = resourceName;
    }

    public MessageBoundle(Class clazz) {
        this.resourceName = clazz.getName().replaceAll("\\.", "/");
    }

//    public String getString(String key, Object... par) {
//        return getString(LocaleAccessUtil.getLocale(), key, par);
//    }

    public String getString(Locale locale, String key, Object... par) {
        String message = "???";
        try {
            message = ResourceBundle.getBundle(this.resourceName, locale).getString(key);

            if (par.length > 0) {
                for (int i = 0; i < par.length; i++) {
                    String strReplace = (par[i] == null ? "" : par[i].toString());
                     if (strReplace.indexOf("\\") != -1) {
                        /** if replacment contains '$' exception is throw
                         *  java.lang.IllegalArgumentException: Illegal group reference
                         *      at java.util.regex.Matcher.appendReplacement(Matcher.java:713)
                         * You can check this with test MessageBoundleTest.
                         */
                        strReplace = strReplace.replaceAll("[\\\\]", "\\\\\\\\");
                    }
                    if (strReplace.indexOf("$") != -1) {
                        /** if replacment contains '$' exception is throw
                         *  java.lang.IllegalArgumentException: Illegal group reference
                         *      at java.util.regex.Matcher.appendReplacement(Matcher.java:713)
                         * You can check this with test MessageBoundleTest.
                         */
                        strReplace = strReplace.replaceAll("[\\$]", "\\\\\\$");
                    }

                    message = message.replaceAll("\\x7B" + i + "\\x7D", strReplace);
                }
            }
        } catch (java.util.MissingResourceException e) {
            log.warn("Invalid key '" + key + "' for resource '" + resourceName + "'.");
            message = "???" + key + "???";
        } catch (RuntimeException re) {
            log.error("getString( locale = " + locale + ", key = " + key + ", par = " + ToStringBuilder.reflectionToString(par));
            throw re;
        }
        return message;
    }
}
