package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.Trader;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Trader data implementation
 * @see Trader
 *
 */
public class TraderImpl implements Trader {
    private static final long serialVersionUID = 7560339273001028770L;
    /**
     * trader TIN number
     */
    private String tin;
    /**
     * trader name (full name)
     */
    private String name;
    /**
     * trader street and number
     */
    private String streetAndNumber;
    /**
     * trader postal code
     */
    private String postalCode;
    /**
     * trader city
     */
    private String city;
    /**
     * trader country code
     */
    private String countryCode;
    /**
     * trader lang
     */
    private String nadLNG;
    /**
     * trader vat number
     */
    private String vatNumber;
    /**
     * trader AEO code
     */
    private String aeoCertificateTypeCode;
    /**
     * trader CustBroker flag
     */
    private boolean custBroker;
    /**
     * trader AdHocNumber flag
     */
    private boolean adHocNumber;

    /**
     * Default constructor
     */
    public TraderImpl() {
    }

    /**
     * trader TIN number
     * @return the tin
     */
    public String getTin() {
        return tin;
    }

    /**
     * trader TIN number
     * @param tin the tin to set
     */
    public void setTin(String tin) {
        this.tin = tin;
    }

    /**
     * trader name (full name)
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * trader name (full name)
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * trader street and number
     * @return the streetAndNumber
     */
    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    /**
     * trader street and number
     * @param streetAndNumber the streetAndNumber to set
     */
    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    /**
     * trader postal code
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * trader postal code
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * trader city
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * trader city
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * trader country code
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * trader country code
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * trader lang
     * @return the nadLNG
     */
    public String getNadLNG() {
        return nadLNG;
    }

    /**
     * trader lang
     * @param nadLNG the nadLNG to set
     */
    public void setNadLNG(String nadLNG) {
        this.nadLNG = nadLNG;
    }

    /**
     * trader vat number
     * @return the vatNumber
     */
    public String getVatNumber() {
        return vatNumber;
    }

    /**
     * trader vat number
     * @param vatNumber the vatNumber to set
     */
    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    /**
     * trader AEO code
     * @return the aeoCertificateTypeCode
     */
    public String getAEOCertificateTypeCode() {
        return aeoCertificateTypeCode;
    }

    /**
     * trader AEO code
     * @param aeoCertificateTypeCode the aeoCertificateTypeCode to set
     */
    public void setAEOCertificateTypeCode(String aeoCertificateTypeCode) {
        this.aeoCertificateTypeCode = aeoCertificateTypeCode;
    }

    /**
     * trader CustBroker flag
     * @return the custBroker
     */
    public boolean isCustBroker() {
        return custBroker;
    }

    /**
     * trader CustBroker flag
     * @param custBroker the custBroker to set
     */
    public void setCustBroker(boolean custBroker) {
        this.custBroker = custBroker;
    }

    /**
     * trader AdHocNumber flag
     * @return the adHocNumber
     */
    public boolean isAdHocNumber() {
        return adHocNumber;
    }

    /**
     * trader AdHocNumber flag
     * @param adHocNumber the adHocNumber to set
     */
    public void setAdHocNumber(boolean adHocNumber) {
        this.adHocNumber = adHocNumber;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
