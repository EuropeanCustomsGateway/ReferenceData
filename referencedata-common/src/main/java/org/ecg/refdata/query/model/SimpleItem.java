package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Current file provides interface to get codes and descriptions from
 * <code>SimpleItemImpl</code> implementations.
 *
 */
public interface SimpleItem extends DictionaryItem {

    /**
     * Method gets code of item
     *
     * @return the code
     */
    String getCode();

    /**
     * Method gets code description of item
     *
     * @return the code description
     */
    String getDescription();

    Boolean getNational();
}
