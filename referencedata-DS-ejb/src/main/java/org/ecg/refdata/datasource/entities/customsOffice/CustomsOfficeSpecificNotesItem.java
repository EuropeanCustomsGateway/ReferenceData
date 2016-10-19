package org.ecg.refdata.datasource.entities.customsOffice;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Class represents entity bean of JPA. Customs Office - specific notes.
 *
 */
@Entity()
@Table(name = "ref_cust_off_spec_not_it")
public class CustomsOfficeSpecificNotesItem implements Serializable {

    private static final long serialVersionUID = 6779428142466209042L;
    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_cust_off_spec_not_it")
    @SequenceGenerator(name = "seq_ref_cust_off_spec_not_it", sequenceName = "seq_ref_cust_off_spec_not_it", allocationSize = 25)
    private Long id;
    /**
     * @serial Reference to {@link CustomsOfficeItem}
     */
    @ManyToOne
    @JoinColumn(name = "customs_office_id", referencedColumnName = "ref_item_mapping_id")
    private CustomsOfficeItem customsOfficeItem;
    /**
     * @serial Specific notes code
     */
    @Column(name = "specific_notes_code", unique = false, nullable = true, length = 6)
    private String specificNotesCode;

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeSpecificNotes#getSpecificNotesCode()
     */
    public String getSpecificNotesCode() {
        return specificNotesCode;
    }

    public void setSpecificNotesCode(String specificNotesCode) {
        this.specificNotesCode = specificNotesCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomsOfficeItem getCustomsOfficeItem() {
        return customsOfficeItem;
    }

    public void setCustomsOfficeItem(CustomsOfficeItem customsOfficeItem) {
        this.customsOfficeItem = customsOfficeItem;
    }

}
