package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * File provides an access to informations about country regions.
 *
 */
public interface CountryRegion extends DictionaryItem {

    /**
     * The Country Code
     *
     * @return the countryCode
     */
    String getCountryCode();

    /**
     * The Country Region Code
     *
     * @return the countryRegionCode
     */
    String getCountryRegionCode();

}
