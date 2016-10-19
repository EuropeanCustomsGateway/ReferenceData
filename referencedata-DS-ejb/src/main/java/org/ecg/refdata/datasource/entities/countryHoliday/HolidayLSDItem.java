package org.ecg.refdata.datasource.entities.countryHoliday;

import org.ecg.refdata.datasource.entities.LocalizedItem;
import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Class represents entity bean of JPA. Haliday - Local Specific Data.
 *
 */
@Entity()
@Table(name = "ref_lsd_it")
public class HolidayLSDItem implements LocalizedItem {

    private static final long serialVersionUID = 976401849766231972L;

    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_lsd_it")
    @SequenceGenerator(name = "seq_ref_lsd_it", sequenceName = "seq_ref_lsd_it", allocationSize = 25)
    private Long id;

    /**
     * @serial Reference to {@link HolidayItem}
     */
    @ManyToOne
    @JoinColumn(name = "holiday_item_id", referencedColumnName = "id")
    private HolidayItem holidayItem;

    /**
     * @serial Language code field
     */
    @Column(name = "language_code", unique = false, nullable = true, length = 2)
    private String languageCode;

    /**
     * @serial Name field
     */
    @Column(name = "name", unique = false, nullable = true, length = 35)
    private String name;

    /**
     * @see org.ecg.refdata.query.model.country.HolidayLSD#getLanguageCode()
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @see org.ecg.refdata.query.model.country.HolidayLSD#getName()
     */
    public String getName() {
        return name;
    }

    public HolidayItem getHolidayItem() {
        return holidayItem;
    }

    public void setHolidayItem(HolidayItem holidayItem) {
        this.holidayItem = holidayItem;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns holidayName in a given requestedLanguageCode from the collection
     * of CountryRegionLSDItem. If a holidayName with the given code is found it
     * is returned else if holidayName with DEFAULT_LANGUAGE_CODE is found it is
     * returned else if any holidayName exists in the collection it is returned
     * else empty string is returned
     *
     * @param holidayNameItems - list of holidayNames to be search over
     * @param requestedLanguageCode - requested language code of the description
     * @return code description in the preferred language code (if found)
     */
    public static String getLocalizedHolidayName(
            Collection<HolidayLSDItem> holidayNameItems,
            String requestedLanguageCode) {

        HolidayLSDItem item = NameAndDescriptionItem.getLocalizedItem(
                holidayNameItems, requestedLanguageCode);
        if (item != null) {
            return item.getName();
        }

        return "";
    }
}
