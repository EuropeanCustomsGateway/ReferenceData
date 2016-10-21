package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.CommodityCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @see CommodityCode
 *
 */
public class CommodityCodeImpl implements CommodityCode {

    private static Log log = LogFactory.getLog(CommodityCodeImpl.class);
    private static final long serialVersionUID = 1L;

    /**
     * Information whether commodity code can be used in export declarations (1
     * - yes, 0 - no).
     */
    private String code;
    /**
     * @serial Description
     */
    private String description;
    /**
     * Excise object flag.
     */
    private Boolean excise;
    /**
     * Information whether commodity code can be used in import declarations (1
     * - yes, 0 - no).
     */
    private Boolean importFlag;
    /**
     * Information whether commodity code can be used in export declarations (1
     * - yes, 0 - no).
     */
    private Boolean exportFlag;

    private final Long ident;
    private final String suffix;

    /**
     * Commodity code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @see org.ecg.refdata.query.model.CommodityCode#getDescription()
     */
    public String getDescription() {
        return description;
    }

    /**
     * Excise object flag.
     *
     * @return the excise
     */
    public Boolean getExcise() {
        return excise;
    }

    /**
     * Information whether commodity code can be used in import declarations (1
     * - yes, 0 - no).
     *
     * @return the importFlag
     */
    public Boolean getImportFlag() {
        return importFlag;
    }

    /**
     * Information whether commodity code can be used in export declarations (1
     * - yes, 0 - no).
     *
     * @return the exportFlag
     */
    public Boolean getExportFlag() {
        return exportFlag;
    }

    public String getSuffix() {
        return suffix;
    }

    public Long getIdent() {
        return ident;
    }

    public CommodityCodeImpl(String code, String description, Boolean excise, Boolean importFlag, Boolean exportFlag, Long ident, String suffix) {
        this.code = code;
        this.description = description;
        this.excise = excise;
        this.importFlag = importFlag;
        this.exportFlag = exportFlag;
        this.ident = ident;
        this.suffix = suffix;
    }

}
