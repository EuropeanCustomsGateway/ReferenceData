package org.ecg.refdata.datasource.entities.documentType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.DocumentTypeImpl;

/**
 * Class represents entity bean of JPA. Map table of document types.
 *
 */
@Entity()
@Table(name = "ref_document_type_it")
@DiscriminatorValue(value = "DocumentTypeItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class DocumentTypeItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = 3072448410447552056L;
    /**
     * @serial Set of {@link CodeDescriptionItem} field
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "documentTypeItem", targetEntity = CodeDescriptionItem.class)
    private Set<CodeDescriptionItem> codeDescriptionItems = new HashSet<CodeDescriptionItem>();
    /**
     * @serial Document type field
     */
    @Column(name = "document_type", unique = false, nullable = true, length = 4)
    private String documentType;
    /**
     * @serial National field
     */
    @Column(name = "national", unique = false, nullable = true)
    private Boolean national;
    /**
     * @serial Transport document field
     */
    @Column(name = "transport_document", unique = false, nullable = true)
    private Boolean transportDocument;

    /**
     * For hibernate only
     */
    DocumentTypeItem() {
        super();
    }

    /**
     * Creates DocumentTypeItem Item for a given
     * ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public DocumentTypeItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public Set<CodeDescriptionItem> getCodeDescriptionItems() {
        return Collections.unmodifiableSet(codeDescriptionItems);
    }

    public boolean addCodeDescriptionItem(
            CodeDescriptionItem codeDescriptionItem) {
        return codeDescriptionItems.add(codeDescriptionItem);
    }

    public boolean removeCodeDescriptionItem(
            CodeDescriptionItem codeDescriptionItem) {
        return codeDescriptionItems.remove(codeDescriptionItem);
    }

    public void removeAllCodeDescriptionItem() {
        codeDescriptionItems.clear();
    }

    public String getDocumentType() {
        return documentType;
    }

    public Boolean getNational() {
        return national;
    }

    public Boolean getTransportDocument() {
        return transportDocument;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setNational(Boolean national) {
        this.national = national;
    }

    public void setTransportDocument(Boolean transportDocument) {
        this.transportDocument = transportDocument;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {

        // return DictionaryItem with localized codeDescription
        return new DocumentTypeImpl(this.documentType, this.transportDocument,
                this.national, CodeDescriptionItem
                .getLocalizedDescription(this.codeDescriptionItems, locale));

    }

}
