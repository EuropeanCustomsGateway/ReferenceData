package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.LocalClearanceAuthorization;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Local Clearance authorization data
 *
 */
public class LocalClearanceAuthorizationImpl implements LocalClearanceAuthorization {

    private static Log log = LogFactory.getLog(LocalClearanceAuthorizationImpl.class);
    private static final long serialVersionUID = 1L;
    /**
     * Authorization identifier
     */
    private String reference;
    /**
     * Exporter's tin
     */
    private String tin;
    /**
     * Number of minutes allowed to make a decision by customs whether movement
     * should be released or controlled
     */
    private Integer goodsReleaseTimeLimit;

    /**
     * Authorization identifier
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
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
     * Number of minutes allowed to make a decision by customs whether movement
     * should be released or controlled
     *
     * @return the goodsReleaseTimeLimit
     */
    public Integer getGoodsReleaseTimeLimit() {
        return goodsReleaseTimeLimit;
    }

    public LocalClearanceAuthorizationImpl(String reference, String tin, Integer goodsReleaseTimeLimit) {
        this.reference = reference;
        this.tin = tin;
        this.goodsReleaseTimeLimit = goodsReleaseTimeLimit;
    }

    public String getCode() {
        return getReference();
    }

    public String getDescription() {
        return getTin();
    }

}
