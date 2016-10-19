package org.ecg.refdata.datasource.entities.customsOffice;

import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable;
import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeTimetableLine;
import org.ecg.refdata.query.model.impl.CustomsOfficeImpl.CustomsOfficeTimetableImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents entity bean of JPA. Customs office - timetable.
 *
 */
@Entity()
@Table(name = "ref_cust_off_time_it")
public class CustomsOfficeTimetableItem implements Serializable {

    private static final long serialVersionUID = 3814563473898341139L;
    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_cust_off_time_it")
    @SequenceGenerator(name = "seq_ref_cust_off_time_it", sequenceName = "seq_ref_cust_off_time_it", allocationSize = 25)
    private Long id;
    /**
     * @serial Reference to {@link CustomsOfficeItem} field
     */
    @ManyToOne
    @JoinColumn(name = "customs_office_id", referencedColumnName = "ref_item_mapping_id")
    private CustomsOfficeItem customsOfficeItem;
    /**
     * @serial Set of {@link CustomsOfficeTimetableItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customsOfficeTimetableItem", targetEntity = CustomsOfficeTimetableLineItem.class)
    private Set<CustomsOfficeTimetableLineItem> customsOfficeTimetableLineItems = new HashSet<CustomsOfficeTimetableLineItem>();
    /**
     * @serial Season code field
     */
    @Column(name = "season_code", unique = false, nullable = true, length = 1)
    private String seasonCode;
    /**
     * @serial Season end date field
     */
    @Column(name = "season_end_date", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date seasonEndDate;
    /**
     * @serial Season name field
     */
    @Column(name = "season_name", unique = false, nullable = true, length = 35)
    private String seasonName;
    /**
     * @serial Season name LNG field
     */
    @Column(name = "season_name_lng", unique = false, nullable = true, length = 2)
    private String seasonNameLNG;
    /**
     * @serial Season start date field
     */
    @Column(name = "season_start_date", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date seasonStartDate;

    // CustomsOfficeTimetableLineItem
    public Set<CustomsOfficeTimetableLineItem> getCustomsOfficeTimetableLineItems() {
        return Collections.unmodifiableSet(customsOfficeTimetableLineItems);
    }

    public boolean addCustomsOfficeTimetableLineItem(
            CustomsOfficeTimetableLineItem customsOfficeTimetableLineItem) {
        return customsOfficeTimetableLineItems
                .add(customsOfficeTimetableLineItem);
    }

    public boolean removeCustomsOfficeTimetableLineItem(
            CustomsOfficeTimetableLineItem customsOfficeTimetableLineItem) {
        return customsOfficeTimetableLineItems
                .remove(customsOfficeTimetableLineItem);
    }

    public void removeAllCustomsOfficeTimetableLineItems() {
        customsOfficeTimetableLineItems.clear();
    }

    public CustomsOfficeItem getCustomsOfficeItem() {
        return customsOfficeItem;
    }

    public void setCustomsOfficeItem(CustomsOfficeItem customsOfficeItem) {
        this.customsOfficeItem = customsOfficeItem;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetable#getSeasonCode()
     */
    public String getSeasonCode() {
        return seasonCode;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetable#getSeasonEndDate()
     */
    public Date getSeasonEndDate() {
        return seasonEndDate;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetable#getSeasonName()
     */
    public String getSeasonName() {
        return seasonName;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetable#getSeasonNameLNG()
     */
    public String getSeasonNameLNG() {
        return seasonNameLNG;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetable#getSeasonStartDate()
     */
    public Date getSeasonStartDate() {
        return seasonStartDate;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public void setSeasonEndDate(Date seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public void setSeasonNameLNG(String seasonNameLNG) {
        this.seasonNameLNG = seasonNameLNG;
    }

    public void setSeasonStartDate(Date seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomsOfficeTimetable getCustomsOfficeTimetable() {
        List<CustomsOfficeTimetableLine> list = new ArrayList<CustomsOfficeTimetableLine>();

        for (CustomsOfficeTimetableLineItem cotli : customsOfficeTimetableLineItems) {
            list.add(cotli.getCustomsOfficeTimetableLine());
        }

        return new CustomsOfficeTimetableImpl(
                seasonCode,
                seasonName,
                seasonNameLNG,
                seasonStartDate,
                seasonEndDate,
                list);
    }
}
