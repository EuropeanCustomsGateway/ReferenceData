package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;
import java.util.List;

/**
 * File contain an interface which provides country holiday data information.
 *
 */
public interface CountryHoliday extends DictionaryItem {

    /**
     * Interface for holiday of Country Holiday
     */
    public static interface Holiday extends DictionaryItem {

        String getDay();

        String getMonth();

        Integer getYear();

        Boolean getVariable();

        String getName(); // from LSD by lang code

    }

    /**
     * Country code.
     *
     * @return Country code.
     */
    String getCountryCode();

    /**
     * list of holidays for country
     *
     * @return
     */
    List<Holiday> getHolidays();

}
