package org.ecg.refdata.datasource.entities.countryHoliday;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.countryRegion.CountryRegionItem;

/**
 * Class represents entity bean of JPA. Maps table of holiday data.
 *
 */
@Entity()
@Table(name = "ref_holiday_it")
public class HolidayItem implements Serializable {

    private static final long serialVersionUID = 8671629538409891093L;

    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_holiday_it")
    @SequenceGenerator(name = "seq_ref_holiday_it", sequenceName = "seq_ref_holiday_it", allocationSize = 25)
    private Long id;

    /**
     * @serial Reference to {@link CountryRegionItem}
     */
    @ManyToOne
    @JoinColumn(name = "country_region_item_id", referencedColumnName = "ref_item_mapping_id")
    private CountryRegionItem countryRegionItem;

    /**
     * @serial Reference to {@link CountryHolidayItem}
     */
    @ManyToOne
    @JoinColumn(name = "country_holiday_item_id", referencedColumnName = "ref_item_mapping_id")
    private CountryHolidayItem countryHolidayItem;

    /**
     * @serial Set of {@link HolidayLSDItem} field
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "holidayItem", targetEntity = HolidayLSDItem.class)
    private Set<HolidayLSDItem> holidayLSDItems = new HashSet<HolidayLSDItem>();

    /**
     * @serial Day field
     */
    @Column(name = "day", unique = false, nullable = true, length = 2)
    private String day;

    /**
     * @serial Month field
     */
    @Column(name = "month", unique = false, nullable = true, length = 2)
    private String month;

    /**
     * @serial Year field
     */
    @Column(name = "year", unique = false, nullable = true)
    private Integer year;

    /**
     * @serial Variable field
     */
    @Column(name = "cariable", unique = false, nullable = true)
    private Boolean variable;

    // HolidayLSDItem
    public Set<HolidayLSDItem> getHolidayLSDItems() {
        return Collections.unmodifiableSet(holidayLSDItems);
    }

    public boolean addHolidayLSDItems(HolidayLSDItem holidayLSDItem) {
        return holidayLSDItems.add(holidayLSDItem);
    }

    public boolean removeHolidayLSDItem(HolidayLSDItem holidayLSDItem) {
        return holidayLSDItems.remove(holidayLSDItem);
    }

    public void removeAllHolidayLSDItems() {
        holidayLSDItems.clear();
    }

    /**
     * @see org.ecg.refdata.query.model.country.Holiday#getDay()
     */
    public String getDay() {
        return day;
    }

    /**
     * @see org.ecg.refdata.query.model.country.Holiday#getMonth()
     */
    public String getMonth() {
        return month;
    }

    /**
     * @see org.ecg.refdata.query.model.country.Holiday#getYear()
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @see org.ecg.refdata.query.model.country.Holiday#isVariable()
     */
    public Boolean isVariable() {
        return variable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CountryRegionItem getCountryRegionItem() {
        return countryRegionItem;
    }

    public void setCountryRegionItem(CountryRegionItem countryRegionItem) {
        this.countryRegionItem = countryRegionItem;
    }

    public CountryHolidayItem getCountryHolidayItem() {
        return countryHolidayItem;
    }

    public void setCountryHolidayItem(CountryHolidayItem countryHolidayItem) {
        this.countryHolidayItem = countryHolidayItem;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setVariable(Boolean variable) {
        this.variable = variable;
    }

}
