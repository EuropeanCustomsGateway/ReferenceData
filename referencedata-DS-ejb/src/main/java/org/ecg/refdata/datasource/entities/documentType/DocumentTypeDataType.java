package org.ecg.refdata.datasource.entities.documentType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of DocumentTypeDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_document_type_dt")
@DiscriminatorValue(value = "DocumentTypeDataType")
public class DocumentTypeDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = -4722292735579385011L;

    public DocumentTypeDataType() {
        super(DocumentTypeItem.class);
    }
}
