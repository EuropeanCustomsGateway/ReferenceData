package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Interface represents trader data
 *
 */
public interface Trader extends DictionaryItem {

    /**
     * Returns trader TIN number.
     *
     * @return TIN number
     */
    public String getTin();

    /**
     * Returns trader name (full name).
     *
     * @return name (full name)
     */
    public String getName();

    /**
     * Returns trader street and number.
     *
     * @return street and number
     */
    public String getStreetAndNumber();

    /**
     * Returns trader postal code.
     *
     * @return postal code
     */
    public String getPostalCode();

    /**
     * Returns trader city.
     *
     * @return city
     */
    public String getCity();

    /**
     * Returns trader country code.
     *
     * @return country code
     */
    public String getCountryCode();

    /**
     * Returns trader lang.
     *
     * @return lang
     */
    public String getNadLNG();

    /**
     * Returns trader vat number.
     *
     * @return vat number
     */
    public String getVatNumber();

    /**
     * Returns trader AEO code.
     *
     * @return AEO code
     */
    public String getAEOCertificateTypeCode();

    /**
     * Returns trader CustBroker flag.
     *
     * @return CustBroker flag
     */
    public boolean isCustBroker();

    /**
     * Returns trader AdHocNumber flag.
     *
     * @return AdHocNumber flag
     */
    public boolean isAdHocNumber();

}
