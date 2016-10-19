package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Value adjustment codes definitions.
 *
 */
public interface ValueAdjustment extends DictionaryItem {

    /**
     * Value adjustment code
     *
     * @return the code
     */
    public String getCode();

    /**
     * Value adjustment description
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Additional costs ( 1 - plus, 0 - minus)
     *
     * @return the additionalCosts
     */
    public String getAdditionalCosts();

    /**
     * Apportionment mode. Posible values: 1 - apportionment by value, 2 -
     * apportionment by weight. xs:string minLenght 1 , maxLength 1
     *
     * @return the apportionmentMode
     */
    public String getApportionmentMode();

    /**
     * Apportionment. Posible values: 1 - by value, 2 - by weight, 3 - not
     * applicable. xs:string minLenght 1 , maxLength 1
     *
     * @return the apportionment
     */
    public String getApportionment();
}
