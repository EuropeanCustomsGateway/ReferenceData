package org.ecg.refdata.datasource.entities.commons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.ecg.refdata.query.DictionaryItem;

/**
 * ReferenceDataAbstractItemEntity is basic class which maps elements of all
 * entity item types.
 *
 */
@Entity
@Table(name = "ref_item_mapping")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "itemType")
public abstract class ReferenceDataAbstractItemEntity implements Serializable {

    private static final long serialVersionUID = -2980774201403163682L;

    /**
     * @serial Item type
     */
    @Column(name = "itemType")
    private String itemType = this.getClass().getSimpleName(); // just because
    // hibernate is
    // not perfect

    // and you are stupit that you do it your way
    /**
     * @serial Id for primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_item_mapping")
    @SequenceGenerator(name = "seq_ref_item_mapping", sequenceName = "seq_ref_item_mapping", allocationSize = 25)
    private Long id;

    /**
     * @serial Reference to {@link ReferenceDataAbstractDataTypeEntity}
     */
    @ManyToOne
    @JoinColumn(name = "ref_data_abstr_data_id", referencedColumnName = "id")
    private ReferenceDataAbstractDataTypeEntity referenceDataAbstractDataTypeEntity;

    /**
     * For hibernate only
     */
    protected ReferenceDataAbstractItemEntity() {
    }

    /**
     * Initialize newly creating item with ReferenceDataAbstractDataTypeEntity
     * to which this item belongs.
     *
     * @param referenceDataAbstractDataTypeEntity
     */
    protected ReferenceDataAbstractItemEntity(
            ReferenceDataAbstractDataTypeEntity referenceDataAbstractDataTypeEntity) {
        this.referenceDataAbstractDataTypeEntity = referenceDataAbstractDataTypeEntity;
    }

    public Long getId() {
        return id;
    }

    public ReferenceDataAbstractDataTypeEntity getReferenceDataAbstractDataTypeEntity() {
        return referenceDataAbstractDataTypeEntity;
    }

    public String getItemType() {
        return itemType;
    }

    /**
     * Returns DictionaryItem transport object filled with data from a given
     * Item
     *
     * @param locale - language code in which data are to be returned
     * @return - appropriate DictionaryItem subclass
     */
    public abstract DictionaryItem getDictionaryItem(String locale);

}
