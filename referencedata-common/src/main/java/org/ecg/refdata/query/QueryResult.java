package org.ecg.refdata.query;

import java.io.Serializable;
import java.util.List;

/**
 * Interface <code>QueryResult</code> describes results from data source query.
 *
 */
public interface QueryResult extends DictionaryInfo, Serializable {

    /**
     * Code of the language in which result is returned
     *
     * @return languageCode
     */
    String getLanguageCode();

    /**
     * List of dictionary items matching search criteria
     *
     * @return DictionaryItem list
     */
    List<DictionaryItem> getItems();

    /**
     * Absolute position from start of which listed items are taken
     *
     * @return item start
     */
    Integer getItemsStart();

    /**
     * Search column with allows to find necessary data
     *
     * @return search column
     */
    String getSearchColumn();

}
