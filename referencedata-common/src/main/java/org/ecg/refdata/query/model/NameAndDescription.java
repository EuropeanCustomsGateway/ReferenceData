package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * File provides an access for all information about name and descriptions of
 * dictionaries.
 *
 */
public interface NameAndDescription extends DictionaryItem {

    /**
     * Method gets name of item
     *
     * @return the name
     */
    String getName();

    /**
     * Method gets description of item
     *
     * @return the description
     */
    String getDescription();

    /**
     * Returns language code
     *
     * @return language code
     */
    String getLanguageCode();

}
