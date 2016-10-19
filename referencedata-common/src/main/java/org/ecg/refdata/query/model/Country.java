package org.ecg.refdata.query.model;

import java.util.Date;

import org.ecg.refdata.query.DictionaryItem;

/**
 * File provides an interface which creates an access for country code
 * information.
 *
 */
public interface Country extends DictionaryItem {

    /**
     * Country code.
     *
     * @return country code
     */
    String getCountryCode();

    /**
     * Transit Community/Common customs entry date.
     *
     * @return tcc entry date.
     */
    Date getTccEntryDate();

    /**
     * NCTS entry date
     *
     * @return ncts entry date.
     */
    Date getNctsEntryDate();

    /**
     * Geo-nomenclature code.
     *
     * @return geo nomenclature code.
     */
    String getGeoNomenclatureCode();

    /**
     * Country regime code.
     *
     * @return country regime code.
     */
    String getCountryRegimeCode();

    /**
     * Description
     *
     * @return description
     */
    String getDescription();
}
