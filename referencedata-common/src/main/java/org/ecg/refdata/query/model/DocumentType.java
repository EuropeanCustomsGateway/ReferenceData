package org.ecg.refdata.query.model;

import org.ecg.refdata.query.DictionaryItem;

/**
 * File stands an interface for getting an information about document types.
 *
 */
public interface DocumentType extends DictionaryItem {

    /**
     * Document type code.
     *
     * @return document type
     */
    String getDocumentType();

    /**
     * Transport document flag
     *
     * @return document flag.
     */
    Boolean getTransportDocument();

    /**
     * Nationally defined document flag (only value "true" is used, lack of this
     * value me
     *
     * @return nationally flag
     */
    Boolean getNational();

    /**
     * Description.
     *
     * @return description
     */
    String getDescription();
}
