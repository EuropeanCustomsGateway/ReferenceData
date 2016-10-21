package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * United Nations Codes for Trade and Transport Locations.
 *
 */
public interface UNLOCODE extends DictionaryItem {

    /**
     * Country code
     *
     * @return country code.
     */
    String getCountryCode();

    /**
     * Location code identifier.
     *
     * @return location id
     */
    String getUnLocodeId();

    /**
     * Location name.
     *
     * @return location name
     */
    String getUnLocodeName();
}
