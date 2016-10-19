package org.ecg.refdata.query.model.impl;

import java.util.Date;

import org.ecg.refdata.query.model.Country;

/**
 * Class stores country code information.
 *
 */
public class CountryImpl implements Country {

    private static final long serialVersionUID = 6595700247879626230L;
    /**
     * @serial Country code
     */
    private String countryCode;
    /**
     * @serial TCC entry date
     */
    private Date tccEntryDate;
    /**
     * @serial NCTS entry date
     */
    private Date nctsEntryDate;
    /**
     * @serial Geo nomenclature code
     */
    private String geoNomenclatureCode;
    /**
     * @serial Country regime code
     */
    private String countryRegimeCode;
    /**
     * @serial Description
     */
    private String description;

    /**
     * Basic constructor takes few following parameters
     *
     * @param countryCode the country code
     * @param tccEntryDate the tcc entry date
     * @param nctsEntryDate the ncts entry date
     * @param geoNomenclatureCode the geo nomenclature code
     * @param countryRegimeCode the country regime code
     */
    public CountryImpl(String countryCode, Date tccEntryDate,
            Date nctsEntryDate, String geoNomenclatureCode,
            String countryRegimeCode, String description) {
        this.countryCode = countryCode;
        this.tccEntryDate = tccEntryDate;
        this.nctsEntryDate = nctsEntryDate;
        this.geoNomenclatureCode = geoNomenclatureCode;
        this.countryRegimeCode = countryRegimeCode;
        this.description = description;
    }

    /**
     * @see org.ecg.refdata.query.model.Country#getCountryCode()
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @see org.ecg.refdata.query.model.Country#getTccEntryDate()
     */
    public Date getTccEntryDate() {
        return tccEntryDate;
    }

    /**
     * @see org.ecg.refdata.query.model.Country#getNctsEntryDate()
     */
    public Date getNctsEntryDate() {
        return nctsEntryDate;
    }

    /**
     * @see org.ecg.refdata.query.model.Country#getGeoNomenclatureCode()
     */
    public String getGeoNomenclatureCode() {
        return geoNomenclatureCode;
    }

    /**
     * @see org.ecg.refdata.query.model.Country#getCountryRegimeCode()
     */
    public String getCountryRegimeCode() {
        return countryRegimeCode;
    }

    /**
     * @see org.ecg.refdata.query.model.Country#getDescription()
     */
    public String getDescription() {
        return description;
    }

    public String getCode() {
        return getCountryCode();
    }
}
