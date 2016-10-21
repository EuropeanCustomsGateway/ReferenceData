package org.ecg.refdata.datasource.entities.simpleItem;

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

import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.SimpleItemImpl;

/**
 * Class represents entity bean of JPA. The structure which carries values of
 * simple code lists.
 *
 */
@Entity()
@Table(name = "ref_simple_it")
@DiscriminatorValue(value = "SimpleItemItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class SimpleItemItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = 674519519628827069L;

    /**
     * @serial Set of {@link CodeDescriptionItem} field
     */
    @OneToMany(mappedBy = "simpleItemItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CodeDescriptionItem.class)
    private Set<CodeDescriptionItem> codeDescriptionItems = new HashSet<CodeDescriptionItem>();

    /**
     * @serial Code field
     */
    @Column(name = "code", unique = false, nullable = true, length = 20)
    private String code;

    /**
     * @serial National field
     */
    @Column(name = "national", unique = false, nullable = true)
    private Boolean national;

    /**
     * For hibernate only
     */
    public SimpleItemItem() {
    }

    /**
     * Creates Simple Item for a given ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public SimpleItemItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public Set<CodeDescriptionItem> getCodeDescriptionItems() {
        return Collections.unmodifiableSet(codeDescriptionItems);
    }

    public void addCodeDescriptionItems(CodeDescriptionItem codeDescriptionItem) {
        this.codeDescriptionItems.add(codeDescriptionItem);
    }

    public void removeCodeDescriptionItem(
            CodeDescriptionItem codeDescriptionItem) {
        this.codeDescriptionItems.remove(codeDescriptionItem);
    }

    public void removeAllCodeDescriptionItems() {
        codeDescriptionItems.clear();
    }

    public Boolean isNational() {
        return national;
    }

    public void setNational(Boolean national) {
        this.national = national;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        // returns DictioanryItem with localized description
        return new SimpleItemImpl(this.getCode(), CodeDescriptionItem
                .getLocalizedDescription(this.codeDescriptionItems, locale), national);

    }

}
