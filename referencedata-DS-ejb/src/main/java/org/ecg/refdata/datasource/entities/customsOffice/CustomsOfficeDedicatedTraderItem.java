package org.ecg.refdata.datasource.entities.customsOffice;

import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeDedicatedTrader;
import org.ecg.refdata.query.model.impl.CustomsOfficeImpl.CustomsOfficeDedicatedTraderImpl;
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
 * Class represents entity bean of JPA. Dedicated trader's data.
 *
 */
@Entity()
@Table(name = "ref_cust_off_ded_tr_it")
public class CustomsOfficeDedicatedTraderItem implements Serializable {

    private static final long serialVersionUID = -2627416824024586557L;

    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_cust_off_ded_tr_it")
    @SequenceGenerator(name = "seq_ref_cust_off_ded_tr_it", sequenceName = "seq_ref_cust_off_ded_tr_it", allocationSize = 25)
    private Long id;

    /**
     * @serial Reference to {@link CustomsOfficeItem}
     */
    @ManyToOne
    @JoinColumn(name = "customs_office_id", referencedColumnName = "ref_item_mapping_id")
    private CustomsOfficeItem customsOfficeItem;
    /**
     * @serial Language code field
     */
    @Column(name = "language_code", unique = false, nullable = true, length = 2)
    private String languageCode;
    /**
     * @serial Name field
     */
    @Column(name = "name", unique = false, nullable = true, length = 35)
    private String name;
    /**
     * @serial Tin field
     */
    @Column(name = "tin", unique = false, nullable = true, length = 17)
    private String tin;

    public String getLanguageCode() {
        return languageCode;
    }

    public String getName() {
        return name;
    }

    public String getTin() {
        return tin;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTin(String tin) {
        this.tin = tin;
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

    public CustomsOfficeDedicatedTrader getCustomsOfficeDedicatedTrader() {
        return new CustomsOfficeDedicatedTraderImpl(name, tin, languageCode);
    }
}
