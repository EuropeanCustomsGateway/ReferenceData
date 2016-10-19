package org.ecg.refdata.datasource.entities.countryHoliday;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.CountryHoliday.Holiday;
import org.ecg.refdata.query.model.impl.CountryHolidayImpl;
import org.ecg.refdata.query.model.impl.CountryHolidayImpl.HolidayImpl;
import java.util.ArrayList;

/**
 * Class represents entity bean of JPA. Country holiday data.
 *
 */
@Entity()
@Table(name = "ref_country_holiday_item")
@DiscriminatorValue(value = "CountryHolidayItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class CountryHolidayItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = 2336822968598645118L;

    /**
     * @serial Country code field
     */
    @Column(name = "country_code", unique = false, nullable = true, length = 2)
    private String countryCode;

    /**
     * @serial Set of {@link HolidayItem} field
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "countryHolidayItem", targetEntity = HolidayItem.class)
    private Set<HolidayItem> holidayItems = new HashSet<HolidayItem>();

    /**
     * For hibernate only
     */
    CountryHolidayItem() {
        super();
    }

    /**
     * Creates CountryHolidayItem Item for a given
     * ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public CountryHolidayItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

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

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        List<Holiday> holidays = new ArrayList<Holiday>();
        for (HolidayItem holidayItem : holidayItems) {
            holidays.add(
                    new HolidayImpl(
                            holidayItem.getDay(),
                            holidayItem.getMonth(),
                            holidayItem.getYear(),
                            holidayItem.isVariable(),
                            HolidayLSDItem.getLocalizedHolidayName(holidayItem.getHolidayLSDItems(), locale)
                    )
            );
        }
        CountryHolidayImpl countryHolidayImpl = new CountryHolidayImpl(this.countryCode, holidays);
        return countryHolidayImpl;
    }

}
