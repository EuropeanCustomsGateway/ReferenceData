package org.ecg.refdata.datasource.entities.unLocode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.UNLOCODEImpl;

/**
 * Class represents entity bean of JPA. United Nations Codes for Trade and
 * Transport Locations.
 *
 */
@Entity()
@Table(name = "ref_un_locode_item")
@DiscriminatorValue(value = "UnLocodeItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class UnLocodeItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = -2135754126259606825L;

    /**
     * @serial Country code field
     */
    @Column(name = "country_code", unique = false, nullable = true, length = 2)
    private String countryCode;

    /**
     * @serial UnLocode Id field
     */
    @Column(name = "un_locode_id", unique = false, nullable = true, length = 3)
    private String unLocodeId;

    /**
     * @serial UnLocode name field
     */
    @Column(name = "un_locode_name", unique = false, nullable = true, length = 29)
    private String unLocodeName;

    /**
     * For hibernate only
     */
    UnLocodeItem() {
        super();
    }

    /**
     * Creates UnLocodeItem Item for a given ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public UnLocodeItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getUnLocodeId() {
        return unLocodeId;
    }

    public String getUnLocodeName() {
        return unLocodeName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setUnLocodeId(String unLocodeId) {
        this.unLocodeId = unLocodeId;
    }

    public void setUnLocodeName(String unLocodeName) {
        this.unLocodeName = unLocodeName;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        // Locale in this type of element aren't used
        UNLOCODEImpl uImpl = new UNLOCODEImpl(this.countryCode,
                this.unLocodeId, this.unLocodeName);
        return uImpl;
    }

}
