package org.ecg.refdata.datasource.entities.localClearanceAuthorization;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.LocalClearanceAuthorizationImpl;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity()
@Table(name = "ref_local_clear_author_it")
@DiscriminatorValue(value = "LocalClearanceAuthorizationItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class LocalClearanceAuthorizationItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static Log log = LogFactory.getLog(LocalClearanceAuthorizationItem.class);
    private static final long serialVersionUID = 1L;

    /**
     * Authorization identifier
     */
    @Column(name = "reference", unique = false, nullable = true, length = 35)
    private String reference;
    /**
     * Exporter's tin
     */
    @Column(name = "tin", unique = false, nullable = true, length = 17)
    private String tin;
    /**
     * Number of minutes allowed to make a decision by customs whether movement
     * should be released or controlled
     */
    @Column(name = "goods_release_time_limit", unique = false, nullable = true, precision = 3)
    private Integer goodsReleaseTimeLimit;

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        return new LocalClearanceAuthorizationImpl(getReference(), getTin(), getGoodsReleaseTimeLimit());
    }

    public LocalClearanceAuthorizationItem(ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public LocalClearanceAuthorizationItem() {
    }

    /**
     * Authorization identifier
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Authorization identifier
     *
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Exporter's tin
     *
     * @return the tin
     */
    public String getTin() {
        return tin;
    }

    /**
     * Exporter's tin
     *
     * @param tin the tin to set
     */
    public void setTin(String tin) {
        this.tin = tin;
    }

    /**
     * Number of minutes allowed to make a decision by customs whether movement
     * should be released or controlled
     *
     * @return the goodsReleaseTimeLimit
     */
    public Integer getGoodsReleaseTimeLimit() {
        return goodsReleaseTimeLimit;
    }

    /**
     * Number of minutes allowed to make a decision by customs whether movement
     * should be released or controlled
     *
     * @param goodsReleaseTimeLimit the goodsReleaseTimeLimit to set
     */
    public void setGoodsReleaseTimeLimit(Integer goodsReleaseTimeLimit) {
        this.goodsReleaseTimeLimit = goodsReleaseTimeLimit;
    }

}
