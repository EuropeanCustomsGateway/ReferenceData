package org.ecg.refdata.query.impl;

import java.util.Date;
import java.util.List;

import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.QueryResult;

/**
 * Current class stores result of query.
 *
 */
public class QueryResultImpl extends DictionaryInfoImpl implements QueryResult {

    private static final long serialVersionUID = 5326817053131594223L;
    /**
     * @serial Language Code
     */
    private String languageCode;
    /**
     * @serial Search Column
     */
    private String searchColumn;
    /**
     * @serial Dictionary Items
     */
    private List<DictionaryItem> items;
    /**
     * @serial Items start
     */
    private Integer itemsStart;

    /**
     * Constructor gets couple of significant information about reference data.
     *
     * @param name name of dictionary
     * @param description description of dictionary
     * @param lastModificationDate modification date
     * @param totalCount
     * @param languageCode language code
     * @param items list of items from library
     * @param itemsStart index of item which indicates starting position on list
     */
    public QueryResultImpl(String dictionaryId, String name,
            String description, Date lastModificationDate, Integer totalCount,
            String languageCode, String searchColumn,
            List<DictionaryItem> items, Integer itemsStart) {

        super(name, description, lastModificationDate, totalCount);
        super.setDictionaryId(dictionaryId);
        this.languageCode = languageCode;
        this.items = items;
        this.itemsStart = itemsStart;
        this.searchColumn = searchColumn;
    }

    /**
     * @see org.ecg.refdata.query.QueryResult#getLanguageCode()
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @see org.ecg.refdata.query.QueryResult#getItems()
     */
    public List<DictionaryItem> getItems() {
        return items;
    }

    /**
     * @see org.ecg.refdata.query.QueryResult#getItemsStart()
     */
    public Integer getItemsStart() {
        return itemsStart;
    }

    /**
     * @see org.ecg.refdata.query.impl.DictionaryInfoImpl#getDescription()
     */
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * @see
     * org.ecg.refdata.query.impl.DictionaryInfoImpl#getLastModificationDate()
     */
    public Date getLastModificationDate() {
        return super.getLastModificationDate();
    }

    /**
     * @see org.ecg.refdata.query.impl.DictionaryInfoImpl#getName()
     */
    public String getName() {
        return super.getName();
    }

    /**
     * @see org.ecg.refdata.query.impl.DictionaryInfoImpl#getTotalCount()
     */
    public Integer getTotalCount() {
        return super.getTotalCount();
    }

    /**
     * @see org.ecg.refdata.query.QueryResult#getSearchColumn()
     */
    public String getSearchColumn() {
        return searchColumn;
    }
}
