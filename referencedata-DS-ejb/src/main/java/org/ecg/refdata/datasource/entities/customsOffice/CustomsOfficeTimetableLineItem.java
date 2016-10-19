package org.ecg.refdata.datasource.entities.customsOffice;

import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeTimetableLine;
import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeTimetableLine.CustomsOfficeRoleTraffic;
import org.ecg.refdata.query.model.impl.CustomsOfficeImpl.CustomsOfficeTimetableImpl.CustomsOfficeTimetableLineImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

/**
 * Class represents entity bean of JPA. Customs office - timetable line.
 *
 */
@Entity()
@Table(name = "ref_cust_off_time_line_it")
public class CustomsOfficeTimetableLineItem implements Serializable {

    private static final long serialVersionUID = 3263568691166887900L;
    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_cust_off_time_line_it")
    @SequenceGenerator(name = "seq_ref_cust_off_time_line_it", sequenceName = "seq_ref_cust_off_time_line_it", allocationSize = 25)
    private Long id;
    /**
     * @serial Reference to {@link CustomsOfficeTimetableItem}
     */
    @ManyToOne
    @JoinColumn(name = "customs_office_time_id", referencedColumnName = "id")
    private CustomsOfficeTimetableItem customsOfficeTimetableItem;
    /**
     * @serial Set of {@link CustomsOfficeRoleTrafficItem} field
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customsOfficeTimetableLineItem", targetEntity = CustomsOfficeRoleTrafficItem.class)
    private Set<CustomsOfficeRoleTrafficItem> customsOfficeRoleTrafficItems = new HashSet<CustomsOfficeRoleTrafficItem>();
    /**
     * @serial Begin day field
     */
    @Column(name = "begin_day", unique = false, nullable = true, length = 1)
    private String beginDay;
    /**
     * @serial End day field
     */
    @Column(name = "end_day", unique = false, nullable = true, length = 1)
    private String endDay;
    /**
     * @serial Hour from field
     */
    @Column(name = "hour_from", unique = false, nullable = true, length = 4)
    private String hourFrom;
    /**
     * @serial Hour to field
     */
    @Column(name = "hour_to", unique = false, nullable = true, length = 4)
    private String hourTo;
    /**
     * @serial Second hour from field
     */
    @Column(name = "second_hour_from", unique = false, nullable = true, length = 4)
    private String secondHourFrom;
    /**
     * @serial Second hour to field
     */
    @Column(name = "second_hour_to", unique = false, nullable = true, length = 4)
    private String secondHourTo;

    // CustomsOfficeRoleTrafficItem
    public Set<CustomsOfficeRoleTrafficItem> getCustomsOfficeRoleTrafficItems() {
        return Collections.unmodifiableSet(customsOfficeRoleTrafficItems);
    }

    public boolean addCustomsOfficeRoleTrafficItem(
            CustomsOfficeRoleTrafficItem customsOfficeRoleTrafficItem) {
        return customsOfficeRoleTrafficItems.add(customsOfficeRoleTrafficItem);
    }

    public boolean removeCustomsOfficeRoleTrafficItem(
            CustomsOfficeRoleTrafficItem customsOfficeRoleTrafficItem) {
        return customsOfficeRoleTrafficItems
                .remove(customsOfficeRoleTrafficItem);
    }

    public void removeAllCustomsOfficeRoleTrafficItems() {
        customsOfficeRoleTrafficItems.clear();
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetableLine#getBeginDay()
     */
    public String getBeginDay() {
        return beginDay;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetableLine#getEndDay()
     */
    public String getEndDay() {
        return endDay;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetableLine#getHourFrom()
     */
    public String getHourFrom() {
        return hourFrom;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetableLine#getHourTo()
     */
    public String getHourTo() {
        return hourTo;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetableLine#getSecondHourFrom()
     */
    public String getSecondHourFrom() {
        return secondHourFrom;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeTimetableLine#getSecondHourTo()
     */
    public String getSecondHourTo() {
        return secondHourTo;
    }

    public void setBeginDay(String beginDay) {
        this.beginDay = beginDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public void setHourFrom(String hourFrom) {
        this.hourFrom = hourFrom;
    }

    public void setHourTo(String hourTo) {
        this.hourTo = hourTo;
    }

    public void setSecondHourFrom(String secondHourFrom) {
        this.secondHourFrom = secondHourFrom;
    }

    public void setSecondHourTo(String secondHourTo) {
        this.secondHourTo = secondHourTo;
    }

    public CustomsOfficeTimetableItem getCustomsOfficeTimetableItem() {
        return customsOfficeTimetableItem;
    }

    public void setCustomsOfficeTimetableItem(
            CustomsOfficeTimetableItem customsOfficeTimetableItem) {
        this.customsOfficeTimetableItem = customsOfficeTimetableItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomsOfficeTimetableLine getCustomsOfficeTimetableLine() {
        List<CustomsOfficeRoleTraffic> list = new ArrayList<CustomsOfficeRoleTraffic>();
        for (CustomsOfficeRoleTrafficItem corti : customsOfficeRoleTrafficItems) {
            list.add(corti.getCustomsOfficeRoleTraffic());
        }
        return new CustomsOfficeTimetableLineImpl(beginDay, hourFrom, hourTo, endDay, secondHourFrom, secondHourTo, list);
    }
}
