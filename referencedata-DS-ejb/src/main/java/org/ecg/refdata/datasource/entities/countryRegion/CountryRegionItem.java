package org.ecg.refdata.datasource.entities.countryRegion;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.datasource.entities.countryHoliday.HolidayItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.CountryRegionImpl;

/**
 * Class represents entity bean of JPA. Country - regions and holidays data.
 *
 */
@Entity()
@Table(name = "ref_country_region_it")
@DiscriminatorValue(value = "CountryRegionItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class CountryRegionItem extends
        ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = 4278107853575702663L;
    /**
     * @serial Set of {@link CountryRegionLSDItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "countryRegionItem", targetEntity = CountryRegionLSDItem.class)
    private Set<CountryRegionLSDItem> countryRegionLSDItems = new HashSet<CountryRegionLSDItem>();

    /**
     * @serial Set of {@link HolidayItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "countryRegionItem", targetEntity = HolidayItem.class)
    private Set<HolidayItem> holidayItems = new HashSet<HolidayItem>();

    /**
     * @serial Country code field
     */
    @Column(name = "country_code", unique = false, nullable = true, length = 2)
    private String countryCode;

    /**
     * @serial Country region code
     */
    @Column(name = "country_region_code", unique = false, nullable = true, length = 3)
    private String countryRegionCode;

    /**
     * For hibernate only
     */
    CountryRegionItem() {
        super();
    }

    /**
     * Creates CountryRegionItem Item for a given
     * ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public CountryRegionItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    // CountryRegionLSDItem
    public Set<CountryRegionLSDItem> getCountryRegionLSDItems() {
        return Collections.unmodifiableSet(countryRegionLSDItems);
    }

    public boolean addCountryRegionLSDItems(
            CountryRegionLSDItem countryRegionLSDItem) {
        return countryRegionLSDItems.add(countryRegionLSDItem);
    }

    public boolean removeCountryRegionLSDItem(
            CountryRegionLSDItem countryRegionLSDItem) {
        return countryRegionLSDItems.remove(countryRegionLSDItem);
    }

    public void removeAllCountryRegionLSDItems() {
        countryRegionLSDItems.clear();
    }

    // HolidayItem
    public Set<HolidayItem> getHolidayItems() {
        return Collections.unmodifiableSet(holidayItems);
    }

    public boolean addHolidayItems(HolidayItem holidayItem) {
        return holidayItems.add(holidayItem);
    }

    public boolean removeHolidayItem(HolidayItem holidayItem) {
        return holidayItems.remove(holidayItem);
    }

    public void removeAllHolidayItems() {
        holidayItems.clear();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryRegionCode() {
        return countryRegionCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryRegionCode(String countryRegionCode) {
        this.countryRegionCode = countryRegionCode;
    }

    @Override
    public DictionaryItem getDictionaryItem(String requestedLanguageCode) {

        return new CountryRegionImpl(this.countryCode, this.countryRegionCode,
                CountryRegionLSDItem.getLocalizedCountryRegionName(
                        getCountryRegionLSDItems(), requestedLanguageCode));
    }

}
