package org.ecg.refdata.query;

import java.io.Serializable;
import java.util.Date;

/**
 * Detailed information about a dictionary.
 *
 */
public interface DictionaryInfo extends Serializable {

    /**
     * Returns Dictionary Id of current dictionary
     *
     * @return dictionary id
     */
    String getDictionaryId();

    /**
     * Returns Dictionary name of current dictionary
     *
     * @return dictionary name
     */
    String getName();

    /**
     * Returns Dictionary description of current dictionary
     *
     * @return dictionary description
     */
    String getDescription();

    /**
     * Returns Dictionary total count of elements in current dictionary
     *
     * @return total number of items in the dictionary
     */
    Integer getTotalCount();

    /**
     * Returns last modification date of current dictionary
     *
     * @return dictionary last modification date
     */
    Date getLastModificationDate();

    /**
     * Returns list of valid columns names for this dictionary
     *
     * @return list of columns names
     */
    public String[] getValidColumns();

    /**
     * Returns minimum length of search text
     *
     * @return the default minimum text search length
     */
    public Integer getMinTextSearchLength();

    /**
     * Returns information for search engine.
     *
     * @return a flag which stands if upper case characters should be send
     */
    public Boolean isForceUpperCase();

    /**
     * Returns count items which should be shown on page
     *
     * @return preferred quantity of items on page
     */
    public Integer getItemsOnPage();

    /**
     * Sets minimum length of search text - used to complete configuration form
     * spring file
     *
     * @param minTextSearchLength minimum text search length
     */
    public void setMinTextSearchLength(Integer minTextSearchLength);

    /**
     * Sets columns which will be shown by tag library - used to complete
     * configuration form spring file
     *
     * @param validColumns valid columns
     */
    public void setValidColumns(String[] validColumns);

    /**
     * Sets count items which should be shown on page - used to complete
     * configuration form spring file
     *
     * @param itemsOnPage count items on page
     */
    public void setItemsOnPage(Integer itemsOnPage);

}
