package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Correlation table - unified structure used for defining possible combination
 * of two codes from any reference data lists (e.g. requested and previous
 * procedures).
 *
 */
public interface Correlation extends DictionaryItem {

    /**
     * First correlation code.
     *
     * @return first code
     */
    String getCode1();

    /**
     * Second correlation code.
     *
     * @return second code
     */
    String getCode2();

    /**
     * Third'th correlation code.
     *
     * @return third code
     */
    String getCode3();

    /**
     * Four'th correlation code.
     *
     * @return third code
     */
    String getCode4();
}
