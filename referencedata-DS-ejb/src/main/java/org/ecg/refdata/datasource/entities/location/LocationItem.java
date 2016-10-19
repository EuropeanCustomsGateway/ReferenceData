package org.ecg.refdata.datasource.entities.location;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.LocationImpl;

/**
 * Class represents entity bean of JPA. Location's data. Unified structure used
 * for storing information about locations like warehouses, authorized locations
 * etc.
 *
 */
@Entity()
@Table(name = "ref_location_item")
@DiscriminatorValue(value = "LocationItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class LocationItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = -2139129524226403775L;
    /**
     * @serial City field
     */
    @Column(name = "city", unique = false, nullable = true, length = 35)
    private String city;
    /**
     * @serial Country code field
     */
    @Column(name = "country_code", unique = false, nullable = true, length = 2)
    private String countryCode;
    /**
     * @serial Name field
     */
    @Column(name = "name", unique = false, nullable = true, length = 35)
    private String name;
    /**
     * @serial Postal code field
     */
    @Column(name = "postal_code", unique = false, nullable = true, length = 9)
    private String postalCode;
    /**
     * @serial Reference number field
     */
    @Column(name = "reference_number", unique = false, nullable = true, length = 17)
    private String referenceNumber;
    /**
     * @serial Street and number field
     */
    @Column(name = "street_and_number", unique = false, nullable = true, length = 35)
    private String streetAndNumber;

    /**
     * For hibernate only
     */
    LocationItem() {
        super();
    }

    /**
     * Creates LocationItem Item for a given ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public LocationItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        // Locale in this type of element aren't used
        LocationImpl locationImpl = new LocationImpl(this.city, this.name,
                this.postalCode, this.referenceNumber, this.streetAndNumber,
                this.countryCode);
        return locationImpl;
    }

}
