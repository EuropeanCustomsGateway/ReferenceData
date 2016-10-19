package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Goods nomenclature commodity codes.
 *
 */
public interface CommodityCode extends DictionaryItem {

    /**
     * Commodity code.
     *
     * @return the code
     */
    public String getCode();

    /**
     * Method gets code description of item
     *
     * @return the code description
     */
    String getDescription();

    /**
     * Excise object flag.
     *
     * @return the excise
     */
    public Boolean getExcise();

    /**
     * Information whether commodity code can be used in import declarations (1
     * - yes, 0 - no).
     *
     * @return the importFlag
     */
    public Boolean getImportFlag();

    /**
     * Information whether commodity code can be used in export declarations (1
     * - yes, 0 - no).
     *
     * @return the exportFlag
     */
    public Boolean getExportFlag();

    public String getSuffix();

    public Long getIdent();

}
