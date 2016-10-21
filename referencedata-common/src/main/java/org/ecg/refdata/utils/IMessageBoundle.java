package org.ecg.refdata.utils;

import java.util.Locale;

/**
 * Interface for resoure boundles. Extension can accept parameter and put it
 * inside localized string instead {x}. Where x is number of parameter starting
 * from 0.
 *
 */
public interface IMessageBoundle {

    /**
     * Get localized string for key. Use default locale.
     *
     * @param key - message key
     * @param par - parameters to fill
     * @return localized string
     */
//    String getString(String key, Object... par);
    /**
     * Get localized string for key. Use passed locale.
     *
     * @param locale - locale where key should be serach
     * @param key - message key
     * @param par - parameters to fill
     * @return localized string
     */
    String getString(Locale locale, String key, Object... par);

}
