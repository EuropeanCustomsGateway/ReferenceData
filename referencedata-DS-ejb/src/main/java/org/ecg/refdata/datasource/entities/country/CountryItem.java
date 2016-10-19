package org.ecg.refdata.datasource.entities.country;

import java.util.Collections;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.CountryImpl;

/**
 * Class represents entity bean of JPA. Maps table of country codes.
 *
 */
@Entity()
@Table(name = "ref_country_it")
@DiscriminatorValue(value = "CountryItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class CountryItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = 5449842018364056743L;

    /**
     * @serial Set of {@link CodeDescriptionItem} field
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "countryItem", targetEntity = CodeDescriptionItem.class)
    private Set<CodeDescriptionItem> codeDescriptionItems = new HashSet<CodeDescriptionItem>();

    /**
     * @serial Country code field
     */
    @Column(name = "country_code", unique = false, nullable = true, length = 2)
    private String countryCode;

    /**
     * @serial Country regime code field
     */
    @Column(name = "country_regime_code", unique = false, nullable = true, length = 3)
    private String countryRegimeCode;

    /**
     * @serial NCTS entry date field
     */
    @Column(name = "ncts_entry_date", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date nctsEntryDate;

    /**
     * @serial Geo Nomenclature code field
     */
    @Column(name = "geo_nomenclature_code", unique = false, nullable = true, length = 3)
    private String geoNomenclatureCode;

    /**
     * @serial TCC entry date
     */
    @Column(name = "tcc_entry_date", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date tccEntryDate;

    /**
     * For hibernate only
     */
    CountryItem() {
        super();
    }

    /**
     * Creates CountryItem Item for a given ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public CountryItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public Set<CodeDescriptionItem> getCodeDescriptionItems() {
        return Collections.unmodifiableSet(codeDescriptionItems);
    }

    public boolean addCodeDescriptionItem(
            CodeDescriptionItem codeDescriptionItem) {
        return codeDescriptionItems.add(codeDescriptionItem);
    }

    public boolean removeCodeDescriptionItem(
            CodeDescriptionItem codeDescriptionItem) {
        return codeDescriptionItems.remove(codeDescriptionItem);
    }

    public void removeAllCodeDescriptionItem() {
        codeDescriptionItems.clear();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryRegimeCode() {
        return countryRegimeCode;
    }

    public String getGeoNomenclatureCode() {
        return geoNomenclatureCode;
    }

    public Date getNctsEntryDate() {
        return nctsEntryDate;
    }

    public Date getTccEntryDate() {
        return tccEntryDate;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryRegimeCode(String countryRegimeCode) {
        this.countryRegimeCode = countryRegimeCode;
    }

    public void setNctsEntryDate(Date nctsEntryDate) {
        this.nctsEntryDate = nctsEntryDate;
    }

    public void setGeoNomenclatureCode(String geoNomenclatureCode) {
        this.geoNomenclatureCode = geoNomenclatureCode;
    }

    public void setTccEntryDate(Date tccEntryDate) {
        this.tccEntryDate = tccEntryDate;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {

        // create Dictionary Item with localized description
        return new CountryImpl(this.countryCode, this.tccEntryDate,
                this.nctsEntryDate, this.geoNomenclatureCode,
                this.countryRegimeCode, CodeDescriptionItem
                .getLocalizedDescription(this.codeDescriptionItems, locale));

    }
}
