package org.ecg.refdata.datasource.entities.customsOffice;

import org.ecg.refdata.datasource.entities.LocalizedItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Class represents entity bean of JPA. Customs Office Local Specific Data.
 *
 */
@Entity()
@Table(name = "ref_cust_off_lsd_it")
public class CustomsOfficeLSDItem implements LocalizedItem {

    private static final long serialVersionUID = 1L;
    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_cust_off_lsd_it")
    @SequenceGenerator(name = "seq_ref_cust_off_lsd_it", sequenceName = "seq_ref_cust_off_lsd_it", allocationSize = 25)
    private Long id;
    /**
     * @serial Reference to {@link CustomsOfficeItem} field
     */
    @ManyToOne
    @JoinColumn(name = "customs_office_id", referencedColumnName = "ref_item_mapping_id")
    private CustomsOfficeItem customsOfficeItem;
    /**
     * @serial City field
     */
    @Column(name = "city", unique = false, nullable = true, length = 35)
    private String city;
    /**
     * @serial Language code field
     */
    @Column(name = "language_code", unique = false, nullable = true, length = 2)
    private String languageCode;
    /**
     * @serial Prefix suffix level field
     */
    @Column(name = "prefix_suffix_level", unique = false, nullable = true, length = 1)
    private String prefixSuffixLevel;
    /**
     * @serial Prefix suffix name field
     */
    @Column(name = "prefix_suffix_name", unique = false, nullable = true, length = 35)
    private String prefixSuffixName;
    /**
     * @serial Street and number field
     */
    @Column(name = "street_and_number", unique = false, nullable = true, length = 35)
    private String streetAndNumber;
    /**
     * @serial Usual name field
     */
    @Column(name = "usual_name", unique = false, nullable = true, length = 35)
    private String usualName;
    /**
     * @serial Prefix suffix flag field
     */
    @Column(name = "prefix_suffix_flag", unique = false, nullable = true)
    private Boolean prefixSuffixFlag;
    /**
     * @serial Space to add field
     */
    @Column(name = "space_to_add", unique = false, nullable = true)
    private Boolean spaceToAdd;

    /**
     * @see org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#getCity()
     */
    public String getCity() {
        return city;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#getLanguageCode()
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#getPrefixSuffixLevel()
     */
    public String getPrefixSuffixLevel() {
        return prefixSuffixLevel;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#getPrefixSuffixName()
     */
    public String getPrefixSuffixName() {
        return prefixSuffixName;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#getStreetAndNumber()
     */
    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#getUsualName()
     */
    public String getUsualName() {
        return usualName;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#isPrefixSuffixFlag()
     */
    public Boolean isPrefixSuffixFlag() {
        return prefixSuffixFlag;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeLSD#isSpaceToAdd()
     */
    public Boolean isSpaceToAdd() {
        return spaceToAdd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setPrefixSuffixLevel(String prefixSuffixLevel) {
        this.prefixSuffixLevel = prefixSuffixLevel;
    }

    public void setPrefixSuffixName(String prefixSuffixName) {
        this.prefixSuffixName = prefixSuffixName;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public void setUsualName(String usualName) {
        this.usualName = usualName;
    }

    public void setPrefixSuffixFlag(Boolean prefixSuffixFlag) {
        this.prefixSuffixFlag = prefixSuffixFlag;
    }

    public void setSpaceToAdd(Boolean spaceToAdd) {
        this.spaceToAdd = spaceToAdd;
    }

    public CustomsOfficeItem getCustomsOfficeItem() {
        return customsOfficeItem;
    }

    public void setCustomsOfficeItem(CustomsOfficeItem customsOfficeItem) {
        this.customsOfficeItem = customsOfficeItem;
    }

}
