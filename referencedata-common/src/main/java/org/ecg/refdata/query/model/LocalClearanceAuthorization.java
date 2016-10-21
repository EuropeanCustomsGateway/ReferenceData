package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Local Clearance authorization data
 *
 */
public interface LocalClearanceAuthorization extends DictionaryItem {

    /**
     * Authorization identifier
     *
     * @return the reference
     */
    public String getReference();

    /**
     * Exporter's tin
     *
     * @return the tin
     */
    public String getTin();

    /**
     * Number of minutes allowed to make a decision by customs whether movement
     * should be released or controlled
     *
     * @return the goodsReleaseTimeLimit
     */
    public Integer getGoodsReleaseTimeLimit();
}
