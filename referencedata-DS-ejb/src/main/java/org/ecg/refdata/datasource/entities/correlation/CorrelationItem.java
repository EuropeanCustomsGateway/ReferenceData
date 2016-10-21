package org.ecg.refdata.datasource.entities.correlation;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.CorrelationImpl;

/**
 * Class represents entity bean of JPA. Correlation table - unified structure
 * used for defining possible combination of two codes from any reference data
 * lists (e.g. requested and previous procedures).
 *
 */
@Entity()
@Table(name = "ref_corelation_it")
@DiscriminatorValue(value = "CorrelationItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class CorrelationItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = -488692363285140782L;

    /**
     * @serial Code1 field
     */
    @Column(name = "code1", unique = false, nullable = true, length = 20)
    private String code1;

    /**
     * @serial Code2 field
     */
    @Column(name = "code2", unique = false, nullable = true, length = 20)
    private String code2;

    /**
     * @serial Code3 field
     */
    @Column(name = "code3", unique = false, nullable = true, length = 20)
    private String code3;

    /**
     * @serial Code3 field
     */
    @Column(name = "code4", unique = false, nullable = true, length = 20)
    private String code4;

    /**
     * For hibernate only
     */
    CorrelationItem() {
        super();
    }

    /**
     * Creates CorrelationItem Item for a given
     * ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public CorrelationItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public String getCode1() {
        return code1;
    }

    public String getCode2() {
        return code2;
    }

    public String getCode3() {
        return code3;
    }

    public String getCode4() {
        return code4;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public void setCode4(String code4) {
        this.code4 = code4;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        // Locale in this type of element aren't used
        CorrelationImpl correlationImpl = new CorrelationImpl(this.code1, this.code2, this.code3, this.code3);
        return correlationImpl;
    }

}
