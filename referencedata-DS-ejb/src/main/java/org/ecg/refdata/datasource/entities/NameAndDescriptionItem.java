package org.ecg.refdata.datasource.entities;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Class represents entity bean of JPA. Name and description of the reference
 * data type in particular language.
 *
 */
@Entity
@Table(name = "ref_name_and_description")
public class NameAndDescriptionItem implements LocalizedItem {

    /**
     * name of the default language code, used if no description with a given
     * language code can be found
     */
    public static String DEFAULT_LANGUAGE_CODE = "EN";

    private static final long serialVersionUID = -214233861354678968L;

    /**
     * @serial Id for primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_name_and_desc")
    @SequenceGenerator(name = "seq_ref_name_and_desc", sequenceName = "seq_ref_name_and_desc", allocationSize = 25)
    protected long id;

    /**
     * @serial Reference to {@link ReferenceDataAbstractItemEntity}
     */
    @ManyToOne
    @JoinColumn(name = "ref_data_abstr_data_id", referencedColumnName = "id")
    ReferenceDataAbstractDataTypeEntity referenceDataAbstractDataTypeEntity;

    /**
     * @serial Name field
     */
    @Column(name = "name", unique = false, nullable = true, length = 70)
    protected String name;

    /**
     * @serial Language code field
     */
    @Column(name = "language_code", unique = false, nullable = true, length = 2)
    protected String languageCode;

    /**
     * @serial Description
     */
    @Column(name = "description", unique = false, nullable = true, length = 350)
    protected String description;

    /**
     * For Hibernate only
     */
    NameAndDescriptionItem() {
    }

    public NameAndDescriptionItem(String name, String description,
            String languageCode, ReferenceDataAbstractDataTypeEntity entity) {
        this.name = name;
        this.description = description;
        this.languageCode = languageCode;
        this.referenceDataAbstractDataTypeEntity = entity;
    }

    public NameAndDescriptionItem(ReferenceDataAbstractDataTypeEntity entity) {
        this.referenceDataAbstractDataTypeEntity = entity;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReferenceDataAbstractDataTypeEntity getReferenceDataAbstractDataTypeEntity() {
        return referenceDataAbstractDataTypeEntity;
    }

    public void setReferenceDataAbstractDataTypeEntity(
            ReferenceDataAbstractDataTypeEntity referenceDataAbstractDataTypeEntity) {
        this.referenceDataAbstractDataTypeEntity = referenceDataAbstractDataTypeEntity;
    }

    /**
     * Returns item in a given requestedLanguageCode from the collection of
     * items. If an item with the given code is found it is returned else if an
     * item with DEFAULT_LANGUAGE_CODE is found it is returned else if any item
     * exists in the collection it is returned else null is returned
     *
     * @param items - list of items to be search over
     * @param requestedLanguageCode - requested language code of the item
     * @return code item in the preferred language code (if found)
     */
    public static <T extends LocalizedItem> T getLocalizedItem(
            Collection<T> items, String requestedLanguageCode) {

        T itemInDefaultLocale = null;
        T anyItem = null;
        for (T item : items) {
            if (item.getLanguageCode().equalsIgnoreCase(requestedLanguageCode)) {
                return item;
            }
            if (item.getLanguageCode().equalsIgnoreCase(DEFAULT_LANGUAGE_CODE)) {
                itemInDefaultLocale = item;
            }

            if (anyItem == null) {
                anyItem = item;
            }
        }

        if (itemInDefaultLocale != null) {
            return itemInDefaultLocale;
        }
        if (anyItem != null) {
            return anyItem;
        }
        // return null description
        return null;
    }

}
