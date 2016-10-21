package org.ecg.refdata.datasource.entities.customsOffice;

import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeTimetableLine.CustomsOfficeRoleTraffic;
import org.ecg.refdata.query.model.impl.CustomsOfficeImpl.CustomsOfficeTimetableImpl.CustomsOfficeTimetableLineImpl.CustomsOfficeRoleTrafficImpl;
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
 * Class represents entity bean of JPA. Customs Office - role and traffic
 * competence.
 *
 */
@Entity()
@Table(name = "ref_cust_off_role_traff_it")
public class CustomsOfficeRoleTrafficItem implements Serializable {

    private static final long serialVersionUID = -1015535292480769877L;
    /**
     * @serial Id of primary key
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_cust_off_role_traff_it")
    @SequenceGenerator(name = "seq_ref_cust_off_role_traff_it", sequenceName = "seq_ref_cust_off_role_traff_it", allocationSize = 25)
    private Long id;
    /**
     * @serial Reference to {@link CustomsOfficeTimetableItem} field
     */
    @ManyToOne
    @JoinColumn(name = "cust_off_time_line_id", referencedColumnName = "id")
    private CustomsOfficeTimetableLineItem customsOfficeTimetableLineItem;
    /**
     * @serial Traffic type field
     */
    @Column(name = "traffic_type", unique = false, nullable = true, length = 3)
    private String trafficType;
    /**
     * @serial Role field
     */
    @Column(name = "role", unique = false, nullable = true, length = 3)
    private String role;

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeRoleTraffic#getRole()
     */
    public String getRole() {
        return role;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.customsOffice.CustomsOfficeRoleTraffic#getTrafficType()
     */
    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomsOfficeTimetableLineItem getCustomsOfficeTimetableLineItem() {
        return customsOfficeTimetableLineItem;
    }

    public void setCustomsOfficeTimetableLineItem(
            CustomsOfficeTimetableLineItem customsOfficeTimetableLineItem) {
        this.customsOfficeTimetableLineItem = customsOfficeTimetableLineItem;
    }

    public CustomsOfficeRoleTraffic getCustomsOfficeRoleTraffic() {
        return new CustomsOfficeRoleTrafficImpl(role, trafficType);
    }

}
