package org.ecg.refdata.datasource.entities.commons;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class is a basic entity bean for all dictionaries which contains validTo and
 * validFrom data. Each entity represents dictionary which has validity dates
 * should extend this class.
 *
 */
@MappedSuperclass
public abstract class ReferenceDataPeriodicValidityAbstractItem extends
        ReferenceDataAbstractItemEntity {

    private static final long serialVersionUID = -895343778946325963L;

    /**
     * @serial Valid from field
     */
    @Column(name = "valid_from", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date validFrom;

    /**
     * @serial Valid to field
     */
    @Column(name = "valid_to", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date validTo;

    /**
     * For Hibernate only
     */
    protected ReferenceDataPeriodicValidityAbstractItem() {
    }

    /**
     * Initialize newly creating item with ReferenceDataAbstractDataTypeEntity
     * to which this item belongs
     *
     * @param referenceDataAbstractDataTypeEntity
     */
    protected ReferenceDataPeriodicValidityAbstractItem(
            ReferenceDataAbstractDataTypeEntity referenceDataAbstractDataTypeEntity) {
        super(referenceDataAbstractDataTypeEntity);
    }

    /**
     * @param validFrom the validFrom to set
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * @param validTo the validTo to set
     */
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

}
