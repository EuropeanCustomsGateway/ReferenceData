package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;
import java.util.List;

/**
 * Structure representing list of country unavailability items
 *
 */
public interface CountryUnavailability extends DictionaryItem {

    public static interface SystemUnavailability extends DictionaryItem {

        String getBusinessFunctionality();

        String getDowntimeFrom();

        String getSystemUnavailabilityType();

        String getDowntimeTo();

        String getExplanation();

        String getExplanationLNG();
    }

    String getCountryCode();

    List<SystemUnavailability> getSystemUnavailabilities();
}
