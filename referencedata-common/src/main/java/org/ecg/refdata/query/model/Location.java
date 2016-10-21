package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Location's data. Unified structure used for storing information about
 * locations like warehouses, authorized locations etc.
 *
 */
public interface Location extends DictionaryItem {

    /**
     * Reference number.
     *
     * @return reference number
     */
    String getReferenceNumber();

    /**
     * Name.
     *
     * @return name
     */
    String getName();

    /**
     * Street name and number.
     *
     * @return street name and number
     */
    String getStreetAndNumber();

    /**
     * Postal code.
     *
     * @return postal code
     */
    String getPostalCode();

    /**
     * City name.
     *
     * @return city name
     */
    String getCity();

    /**
     * Country code.
     *
     * @return country code
     */
    String getCountryCode();
}
