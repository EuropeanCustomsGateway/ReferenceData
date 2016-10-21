package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.DocumentType;

/**
 * Class stores an information about document types.
 *
 */
public class DocumentTypeImpl implements DocumentType {

    private static final long serialVersionUID = -6906447584801585152L;
    /**
     * @serial Document type
     */
    private String documentType;
    /**
     * @serial Transport document
     */
    private Boolean transportDocument;
    /**
     * @serial National
     */
    private Boolean national;
    /**
     * @serial Description
     */
    private String description;

    /**
     * Basic constructor takes few following parameters
     *
     * @param documentType the document type
     * @param transportDocument the transport document
     * @param national the national
     * @param description the description
     */
    public DocumentTypeImpl(String documentType, Boolean transportDocument,
            Boolean national, String description) {
        this.documentType = documentType;
        this.transportDocument = transportDocument;
        this.national = national;
        this.description = description;
    }

    /**
     * @see org.ecg.refdata.query.model.DocumentType#getDocumentType()
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @see org.ecg.refdata.query.model.DocumentType#getTransportDocument()
     */
    public Boolean getTransportDocument() {
        return transportDocument;
    }

    /**
     * @see org.ecg.refdata.query.model.DocumentType#getNational()
     */
    public Boolean getNational() {
        return national;
    }

    /**
     * @see org.ecg.refdata.query.model.DocumentType#getDescription()
     */
    public String getDescription() {
        return description;
    }

    public String getCode() {
        return getDocumentType();
    }

}
