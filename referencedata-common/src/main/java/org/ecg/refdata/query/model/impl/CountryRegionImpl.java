package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.CountryRegion;

/**
 * Class stores an information about country region.
 *
 */
public class CountryRegionImpl implements CountryRegion {

    private static final long serialVersionUID = -5755973840870687086L;
    /**
     * @serial Country code
     */
    private String countryCode;
    /**
     * @serial Country region code
     */
    private String countryRegionCode;
    /**
     * @serial Country region name
     */
    private String countryRegionName;

    /**
     * Basic constructor takes following parameters
     *
     * @param countryCode the country codes
     * @param countryRegionCode the country region code
     * @param countryRegionName the country region name
     */
    public CountryRegionImpl(String countryCode, String countryRegionCode,
            String countryRegionName) {
        this.countryCode = countryCode;
        this.countryRegionCode = countryRegionCode;
        this.countryRegionName = countryRegionName;
    }

    /**
     * @see org.ecg.refdata.query.model.CountryRegion#getCountryCode()
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @see org.ecg.refdata.query.model.CountryRegion#getCountryRegionCode()
     */
    public String getCountryRegionCode() {
        return countryRegionCode;
    }

    /**
     * The Country region name
     *
     * @return the countryRegionName
     */
    public String getCountryRegionName() {
        return countryRegionName;
    }

    public String getCode() {
        return getCountryRegionCode();
    }

    public String getDescription() {
        return getCountryRegionName();
    }
}
