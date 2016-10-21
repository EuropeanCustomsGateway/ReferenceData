package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.CountryUnavailability;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Country unavailability.
 *
 */
public class CountryUnavailabilityImpl implements CountryUnavailability {

    public static class SystemUnavailabilityImpl implements SystemUnavailability {

        private static final long serialVersionUID = 1L;

        /**
         * Business functionality.
         */
        private String businessFunctionality;

        /**
         * Downtime from.
         */
        private String downtimeFrom;

        /**
         * System unavailability type
         */
        private String systemUnavailabilityType;

        /**
         * Downtime to
         */
        private String downtimeTo;

        /**
         * Explanation
         */
        private String explanation;

        /**
         * Explanation language code
         */
        private String explanationLNG;

        /**
         * Business functionality.
         *
         * @return the businessFunctionality
         */
        public String getBusinessFunctionality() {
            return businessFunctionality;
        }

        /**
         * Downtime from.
         *
         * @return the downtimeFrom
         */
        public String getDowntimeFrom() {
            return downtimeFrom;
        }

        /**
         * System unavailability type
         *
         * @return the systemUnavailabilityType
         */
        public String getSystemUnavailabilityType() {
            return systemUnavailabilityType;
        }

        /**
         * Downtime to
         *
         * @return the downtimeTo
         */
        public String getDowntimeTo() {
            return downtimeTo;
        }

        /**
         * Explanation
         *
         * @return the explanation
         */
        public String getExplanation() {
            return explanation;
        }

        /**
         * Explanation language code
         *
         * @return the explanationLNG
         */
        public String getExplanationLNG() {
            return explanationLNG;
        }

        public String getCode() {
            return getBusinessFunctionality();
        }

        public String getDescription() {
            return getExplanation();
        }

        public SystemUnavailabilityImpl(String businessFunctionality, String downtimeFrom, String systemUnavailabilityType, String downtimeTo, String explanation, String explanationLNG) {
            this.businessFunctionality = businessFunctionality;
            this.downtimeFrom = downtimeFrom;
            this.systemUnavailabilityType = systemUnavailabilityType;
            this.downtimeTo = downtimeTo;
            this.explanation = explanation;
            this.explanationLNG = explanationLNG;
        }

    }
    private static final long serialVersionUID = 1L;
    /**
     * Country code.
     */
    private String countryCode;

    /**
     * Country code.
     *
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    public CountryUnavailabilityImpl(String countryCode, Collection<SystemUnavailability> systemUnavailabilities) {
        this.countryCode = countryCode;
        this.systemUnavailabilities.addAll(systemUnavailabilities);
    }

    List<SystemUnavailability> systemUnavailabilities = new ArrayList<SystemUnavailability>();

    public List<SystemUnavailability> getSystemUnavailabilities() {
        return systemUnavailabilities;
    }

    public String getCode() {
        return getCountryCode();
    }

    public String getDescription() {
        return null;
    }
}
