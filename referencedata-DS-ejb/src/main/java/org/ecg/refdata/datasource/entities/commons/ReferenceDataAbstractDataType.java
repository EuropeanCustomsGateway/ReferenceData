package org.ecg.refdata.datasource.entities.commons;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Current class extends <code>ReferenceDataAbstractDataTypeEntity</code> and
 * adds couple of additional parameters which contains all dictionary
 * description information for any particular dictionaries types.
 *
 */
@MappedSuperclass
public abstract class ReferenceDataAbstractDataType extends
        ReferenceDataAbstractDataTypeEntity {

    private static final long serialVersionUID = 5424601171276428257L;

    /**
     * @serial Reference data ID field
     *
     * It my opinion it should be unique - DR - as it is not on the diagram we
     * left this open, but consider this at least on ehte database level
     */
    @Column(name = "ref_data_id", length = 20)
    private String refDataId;

    /**
     * @serial Source field
     */
    @Column(name = "source", unique = false, nullable = true, length = 35)
    private String source;

    /**
     * @serial Origin field
     */
    @Column(name = "origin", unique = false, nullable = true, length = 5)
    private String origin;

    /**
     * @serial Item type field
     */
    @Column(name = "item_type", unique = false, nullable = true, length = 35)
    private String itemType;

    /**
     * @serial Item code type field
     */
    @Column(name = "item_code_type", unique = false, nullable = true, length = 6)
    private String itemCodeType;

    /**
     * @serial Publics field
     */
    @Column(name = "publics", unique = false, nullable = true)
    private Boolean publics;

    /**
     * @serial Last change field
     */
    @Column(name = "last_change", unique = false, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChange;

    /**
     * Base constructor for all Dictionary types
     *
     * @param itemClass - item type contained by this dictionary
     */
    protected ReferenceDataAbstractDataType(
            Class<? extends ReferenceDataAbstractItemEntity> itemClass) {
        super(itemClass);
    }

    public String getItemCodeType() {
        return itemCodeType;
    }

    public String getItemType() {
        return itemType;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public String getOrigin() {
        return origin;
    }

    public String getSource() {
        return source;
    }

    public String getRefDataId() {
        return refDataId;
    }

    public void setRefDataId(String refDataId) {
        this.refDataId = refDataId;
    }

    public Boolean getPublics() {
        return publics;
    }

    public void setPublics(Boolean publics) {
        this.publics = publics;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemCodeType(String itemCodeType) {
        this.itemCodeType = itemCodeType;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

}
