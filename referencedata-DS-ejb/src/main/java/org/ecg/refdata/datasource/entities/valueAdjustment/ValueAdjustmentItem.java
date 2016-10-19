package org.ecg.refdata.datasource.entities.valueAdjustment;

import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.ValueAdjustmentImpl;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity()
@Table(name = "ref_value_adjustment_it")
@DiscriminatorValue(value = "ValueAdjustmentItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class ValueAdjustmentItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static Log log = LogFactory.getLog(ValueAdjustmentItem.class);
    private static final long serialVersionUID = 1L;

    /**
     * Value adjustement code
     */
    @Column(name = "code", unique = false, nullable = true, length = 4)
    private String code;
    /**
     * Additional costs ( 1 - plus, 0 - minus)
     */
    @Column(name = "additional_costs", unique = false, nullable = true, length = 1)
    private String additionalCosts;

    /**
     * Apportionment. Posible values: 1 - by value, 2 - by weight, 3 - not
     * applicable. xs:string minLenght 1 , maxLength 1
     */
    @Column(name = "apportionment_mode", unique = false, nullable = true, length = 1)
    private String apportionmentMode;
    /**
     * Apportionment. Posible values: 1 - by value, 2 - by weight, 3 - not
     * applicable. xs:string minLenght 1 , maxLength 1
     */
    @Column(name = "apportionment", unique = false, nullable = true, length = 1)
    private String apportionment;

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        return new ValueAdjustmentImpl(getCode(), getAdditionalCosts(), getApportionmentMode(), getApportionment(), CodeDescriptionItem.getLocalizedDescription(codeDescriptionItems, locale));
    }

    public ValueAdjustmentItem(ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    /**
     * for hibernate only
     */
    public ValueAdjustmentItem() {
    }
    /**
     * @serial Set of {@link CodeDescriptionItem} field
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "valueAdjustmentItem", targetEntity = CodeDescriptionItem.class)
    private Set<CodeDescriptionItem> codeDescriptionItems = new HashSet<CodeDescriptionItem>();

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

    /**
     * @return the apportionmentMode
     */
    public String getApportionmentMode() {
        return apportionmentMode;
    }

    /**
     * @param apportionmentMode the apportionmentMode to set
     */
    public void setApportionmentMode(String apportionmentMode) {
        this.apportionmentMode = apportionmentMode;
    }

    /**
     * @return the apportionment
     */
    public String getApportionment() {
        return apportionment;
    }

    /**
     * @param apportionment the apportionment to set
     */
    public void setApportionment(String apportionment) {
        this.apportionment = apportionment;
    }

    /**
     * Value adjustement code
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Value adjustement code
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Additional costs ( 1 - plus, 0 - minus)
     *
     * @return the additionalCosts
     */
    public String getAdditionalCosts() {
        return additionalCosts;
    }

    /**
     * Additional costs ( 1 - plus, 0 - minus)
     *
     * @param additionalCosts the additionalCosts to set
     */
    public void setAdditionalCosts(String additionalCosts) {
        this.additionalCosts = additionalCosts;
    }

}
