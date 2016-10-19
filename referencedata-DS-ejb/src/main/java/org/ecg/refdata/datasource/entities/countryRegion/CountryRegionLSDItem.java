package org.ecg.refdata.datasource.entities.countryRegion;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.LocalizedItem;
import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;

/**
 * Class represents JPA entity. Country - region Local Specific Data.
 *
 */
@Entity()
@Table(name = "ref_country_region_lsd_it")
public class CountryRegionLSDItem implements LocalizedItem {

    private static final long serialVersionUID = 6403964230769360136L;
    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_country_reg_lsd_it")
    @SequenceGenerator(name = "seq_ref_country_reg_lsd_it", sequenceName = "seq_ref_country_reg_lsd_it", allocationSize = 25)
    private Long id;
    /**
     * @serial {@link CountryRegionItem} field
     */
    @ManyToOne
    @JoinColumn(name = "country_region_item_id", referencedColumnName = "ref_item_mapping_id")
    private CountryRegionItem countryRegionItem;
    /**
     * @serial Language code field
     */
    @Column(name = "language_code", unique = false, nullable = true, length = 2)
    private String languageCode;
    /**
     * @serial Country region code field
     */
    @Column(name = "country_region_code", unique = false, nullable = true, length = 35)
    private String countryRegionName;

    /**
     * Required by hibernate
     */
    public CountryRegionLSDItem() {
    }

    /**
     * Creates CountryRegionLSDItem for a given CountryRegionItem,
     * CountryRegionName and languageCode
     *
     * @param countryRegionItem - referrenced countryRegionItem
     * @param countryRegionName - name of the country region
     * @param languageCode - language code of the name
     */
    public CountryRegionLSDItem(CountryRegionItem countryRegionItem,
            String countryRegionName, String languageCode) {
        super();
        this.countryRegionItem = countryRegionItem;
        this.countryRegionName = countryRegionName;
        this.languageCode = languageCode;
    }

    public String getCountryRegionName() {
        return languageCode;
    }

    public String getLanguageCode() {
        return countryRegionName;
    }

    public CountryRegionItem getCountryRegionItem() {
        return countryRegionItem;
    }

//	@SuppressWarnings("unused")
    public void setCountryRegionItem(CountryRegionItem countryRegionItem) {
        this.countryRegionItem = countryRegionItem;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setCountryRegionName(String countryRegionName) {
        this.countryRegionName = countryRegionName;
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        this.id = id;
    }

    /**
     * Returns countryRegionName in a given requestedLanguageCode from the
     * collection of CountryRegionLSDItem. If a countryRegionName with the given
     * code is found it is returned else if countryRegionName with
     * DEFAULT_LANGUAGE_CODE is found it is returned else if any
     * countryRegionName exists in the collection it is returned else empty
     * string is returned
     *
     * @param codeDescriptionItems - list of codeDescriptions to be search over
     * @param requestedLanguageCode - requested language code of the description
     * @return code description in the preferred language code (if found)
     */
    public static String getLocalizedCountryRegionName(
            Collection<CountryRegionLSDItem> codeDescriptionItems,
            String requestedLanguageCode) {

        CountryRegionLSDItem item = NameAndDescriptionItem.getLocalizedItem(
                codeDescriptionItems, requestedLanguageCode);
        if (item != null) {
            return item.getCountryRegionName();
        }

        return "";
    }

}
