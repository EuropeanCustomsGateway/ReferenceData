package org.ecg.refdata.datasource.entities.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;

/**
 * ReferenceDataAbstractDataTypeEntity represents entity which contains
 * dictionary description for all particular dictionary types.
 *
 */
@Entity
@Table(name = "ref_type_name_and_desc_mapp")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
public abstract class ReferenceDataAbstractDataTypeEntity implements
        Serializable {

    private static final long serialVersionUID = -6577626281384674178L;

    @Transient
    private transient Class<? extends ReferenceDataAbstractItemEntity> itemClass;

    /**
     * @serial Id for primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_type_name_and_desc")
    @SequenceGenerator(name = "seq_ref_type_name_and_desc", sequenceName = "seq_ref_type_name_and_desc", allocationSize = 25)
    private Long id;

    /**
     * @serial Version number field
     */
    @Version
    private Integer versionNumber;

    /**
     * @serial Set of {@link NameAndDescriptionItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "referenceDataAbstractDataTypeEntity", targetEntity = NameAndDescriptionItem.class)
    private Set<NameAndDescriptionItem> nameAndDescriptionItems = new HashSet<NameAndDescriptionItem>();

    /**
     * @serial Set of {@link ReferenceDataAbstractItemEntity}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "referenceDataAbstractDataTypeEntity", targetEntity = ReferenceDataAbstractItemEntity.class)
    private Set<ReferenceDataAbstractItemEntity> referenceDataAbstractItemEntities = new HashSet<ReferenceDataAbstractItemEntity>();

    /**
     * Base constructor for all Dictionary types
     *
     * @param itemClass - item type contained by this dictionary
     */
    protected ReferenceDataAbstractDataTypeEntity(
            Class<? extends ReferenceDataAbstractItemEntity> itemClass) {
        this.itemClass = itemClass;
    }

    // NameAndDescriptionItem
    public Set<NameAndDescriptionItem> getNameAndDescriptionItems() {
        return Collections.unmodifiableSet(nameAndDescriptionItems);
    }

    public boolean addNameAndDescriptionItem(
            NameAndDescriptionItem nameAndDescriptionItem) {
        return nameAndDescriptionItems.add(nameAndDescriptionItem);
    }

    public boolean removeNameAndDescriptionItem(
            NameAndDescriptionItem nameAndDescriptionItem) {
        return nameAndDescriptionItems.remove(nameAndDescriptionItem);
    }

    public void removeAllNameAndDescriptionItem() {
        nameAndDescriptionItems.clear();
    }

    public Set<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItemEntities() {
        return Collections.unmodifiableSet(referenceDataAbstractItemEntities);
    }

    public boolean addReferenceDataAbstractItemEntity(
            ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity) {
        return referenceDataAbstractItemEntities
                .add(referenceDataAbstractItemEntity);
    }

    public boolean removeReferenceDataAbstractItemEntity(
            ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity) {
        return referenceDataAbstractItemEntities
                .remove(referenceDataAbstractItemEntity);
    }

    public void removeAllReferenceDataAbstractItemEntities() {
        referenceDataAbstractItemEntities.clear();
    }

    public Collection<ReferenceDataAbstractItemEntity> getItems() {
        return new ArrayList<ReferenceDataAbstractItemEntity>(this
                .getReferenceDataAbstractItemEntities());
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        this.id = id;
    }

    public Class<? extends ReferenceDataAbstractItemEntity> getItemClass() {
        return itemClass;
    }

    /**
     * Equals uses itemType and refDataId for comparison
     *
     */
}
