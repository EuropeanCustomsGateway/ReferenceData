package org.ecg.refdata.query.impl;

import java.util.Date;

import org.ecg.refdata.query.DictionaryInfo;

/**
 * Current class contains the most important information about a dictionary. It
 * basically store the name of dictionary, short description, modification date
 * and amount of items.
 *
 */
public class DictionaryInfoImpl implements DictionaryInfo {

    /**
     * Serial for DictionaryInfo
     *
     * @serial Version UID
     */
    private static final long serialVersionUID = 1629083490241813396L;
    /**
     * @serial Dictionary Id
     */
    private String dictionaryId;
    /**
     * @serial Name
     */
    private String name;
    /**
     * @serial Description
     */
    private String description;
    /**
     * @serial Last modification Date
     */
    private Date lastModificationDate;
    /**
     * @serial Total Count
     */
    private Integer totalCount;
    /**
     * @serial Force Upper Case
     */
    private Boolean forceUpperCase;
    /**
     * @serial Items On Page
     */
    private Integer itemsOnPage;
    /**
     * @serial Minimum Text Search Length
     */
    private Integer minTextSearchLength;
    /**
     * @serial Valid Columns
     */
    private String[] validColumns;

    /**
     * Constructor gets four basic arguments.
     *
     * @param name name of current dictionary
     * @param description description of dictionary
     * @param lastModificationDate modification date
     * @param totalCount amount of items inside current dictionary
     */
    public DictionaryInfoImpl(String name, String description,
            Date lastModificationDate, int totalCount) {
        this.name = name;
        this.description = description;
        this.lastModificationDate = lastModificationDate;
        this.totalCount = totalCount;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getDictionaryId()
     */
    public String getDictionaryId() {
        return dictionaryId;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getDescription()
     */
    public String getDescription() {
        return description;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getLastModificationDate()
     */
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getTotalCount()
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#isForceUpperCase()
     */
    public Boolean isForceUpperCase() {
        return forceUpperCase;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getItemsOnPage()
     */
    public Integer getItemsOnPage() {
        return itemsOnPage;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#setItemsOnPage(int)
     */
    public void setItemsOnPage(Integer itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getMinTextSearchLength()
     */
    public Integer getMinTextSearchLength() {
        return minTextSearchLength;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#setMinTextSearchLength(int)
     */
    public void setMinTextSearchLength(Integer minTextSearchLength) {
        this.minTextSearchLength = minTextSearchLength;
    }

    /**
     * @see org.ecg.refdata.query.DictionaryInfo#getValidColumns()
     */
    public String[] getValidColumns() {
        return validColumns;
    }

    /**
     * @see
     * org.ecg.refdata.query.DictionaryInfo#setValidColumns(java.lang.String[])
     */
    public void setValidColumns(String[] validColumns) {
        this.validColumns = validColumns;
    }

    /**
     * Method allows force upper case for search engine
     *
     * @param forceUpperCase if true, force upper case is enabled
     */
    public void setForceUpperCase(Boolean forceUpperCase) {
        this.forceUpperCase = forceUpperCase;
    }

    /**
     * Method sets name of current dictionary
     *
     * @param name name of current dictionary
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method sets description of current dictionary
     *
     * @param description description of current dictionary
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method sets last modification date of current dictionary
     *
     * @param lastModificationDate last modifications date
     */
    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    /**
     * Method sets total count items inside current dictionary
     *
     * @param totalCount total count items
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Method sets dictionary id of current dictionary
     *
     * @param dictionaryId dictionary Id
     */
    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

}
