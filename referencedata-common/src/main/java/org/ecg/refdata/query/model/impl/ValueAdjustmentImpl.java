package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.ValueAdjustment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @see ValueAdjustment
 *
 */
public class ValueAdjustmentImpl implements ValueAdjustment {

    private static Log log = LogFactory.getLog(ValueAdjustmentImpl.class);
    private static final long serialVersionUID = 1L;

    /**
     * Apportionment. Posible values: 1 - by value, 2 - by weight, 3 - not
     * applicable. xs:string minLenght 1 , maxLength 1
     */
    private String apportionmentMode;
    /**
     * Apportionment. Posible values: 1 - by value, 2 - by weight, 3 - not
     * applicable. xs:string minLenght 1 , maxLength 1
     */
    private String apportionment;

    /**
     * Apportionment mode. Posible values: 1 - apportionment by value, 2 -
     * apportionment by weight. xs:string minLenght 1 , maxLength 1
     *
     * @return the apportionmentMode
     */
    public String getApportionmentMode() {
        return apportionmentMode;
    }

    /**
     * Apportionment. Posible values: 1 - by value, 2 - by weight, 3 - not
     * applicable. xs:string minLenght 1 , maxLength 1
     *
     * @return the apportionment
     */
    public String getApportionment() {
        return apportionment;
    }

    /**
     * Value adjustment code
     */
    private String code;
    /**
     * Value adjustment description
     */
    private String description;
    /**
     * Additional costs ( 1 - plus, 0 - minus)
     */
    private String additionalCosts;

    /**
     * Value adjustment code
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Additional costs ( 1 - plus, 0 - minus)
     *
     * @return the additionalCosts
     */
    public String getAdditionalCosts() {
        return additionalCosts;
    }

    public ValueAdjustmentImpl(String code, String additionalCosts, String apportionmentMode, String apportionment, String description) {
        this.code = code;
        this.additionalCosts = additionalCosts;
        this.apportionmentMode = apportionmentMode;
        this.apportionment = apportionment;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
