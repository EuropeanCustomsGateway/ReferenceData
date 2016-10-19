package org.ecg.refdata.datasource.entities.countryUnavailability;

import org.ecg.refdata.query.model.CountryUnavailability.SystemUnavailability;
import org.ecg.refdata.query.model.impl.CountryUnavailabilityImpl.SystemUnavailabilityImpl;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity()
@Table(name = "ref_system_unavailability_it")
public class SystemUnavailabilityItem implements Serializable {

    private static Log log = LogFactory.getLog(SystemUnavailabilityItem.class);
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "seq_ref_system_unavail_it")
    @SequenceGenerator(name = "seq_ref_system_unavail_it", sequenceName = "seq_ref_system_unavail_it", allocationSize = 25)
    private Long id;

    /**
     * Business functionality.
     */
    @Column(name = "business_functionality", unique = false, nullable = true, length = 1)
    private String businessFunctionality;

    /**
     * Downtime from.
     */
    @Column(name = "downtime_from", unique = false, nullable = true, length = 12)
    private String downtimeFrom;

    /**
     * System unavailability type
     */
    @Column(name = "system_unavailability_type", unique = false, nullable = true, length = 1)
    private String systemUnavailabilityType;

    /**
     * Downtime to
     */
    @Column(name = "downtime_to", unique = false, nullable = true, length = 12)
    private String downtimeTo;

    /**
     * Explanation
     */
    @Column(name = "explanation", unique = false, nullable = true, length = 350)
    private String explanation;

    /**
     * Explanation language code
     */
    @Column(name = "explanation_lng", unique = false, nullable = true, length = 2)
    private String explanationLNG;

    /**
     * @serial {@link CountryUnavailabilityItem} field
     */
    @ManyToOne
    @JoinColumn(name = "country_unavailability_item_id", referencedColumnName = "ref_item_mapping_id")
    private CountryUnavailabilityItem countryUnavailabilityItem;

    /**
     * Business functionality.
     *
     * @return the businessFunctionality
     */
    public String getBusinessFunctionality() {
        return businessFunctionality;
    }

    /**
     * Business functionality.
     *
     * @param businessFunctionality the businessFunctionality to set
     */
    public void setBusinessFunctionality(String businessFunctionality) {
        this.businessFunctionality = businessFunctionality;
    }

    /**
     * Downtime from.
     *
     * @return the downtimeFrom
     */
    public String getDowntimeFrom() {
        return downtimeFrom;
    }

    /**
     * Downtime from.
     *
     * @param downtimeFrom the downtimeFrom to set
     */
    public void setDowntimeFrom(String downtimeFrom) {
        this.downtimeFrom = downtimeFrom;
    }

    /**
     * System unavailability type
     *
     * @return the systemUnavailabilityType
     */
    public String getSystemUnavailabilityType() {
        return systemUnavailabilityType;
    }

    /**
     * System unavailability type
     *
     * @param systemUnavailabilityType the systemUnavailabilityType to set
     */
    public void setSystemUnavailabilityType(String systemUnavailabilityType) {
        this.systemUnavailabilityType = systemUnavailabilityType;
    }

    /**
     * Downtime to
     *
     * @return the downtimeTo
     */
    public String getDowntimeTo() {
        return downtimeTo;
    }

    /**
     * Downtime to
     *
     * @param downtimeTo the downtimeTo to set
     */
    public void setDowntimeTo(String downtimeTo) {
        this.downtimeTo = downtimeTo;
    }

    /**
     * Explanation
     *
     * @return the explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Explanation
     *
     * @param explanation the explanation to set
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * Explanation language code
     *
     * @return the explanationLNG
     */
    public String getExplanationLNG() {
        return explanationLNG;
    }

    /**
     * Explanation language code
     *
     * @param explanationLNG the explanationLNG to set
     */
    public void setExplanationLNG(String explanationLNG) {
        this.explanationLNG = explanationLNG;
    }

    /**
     * @serial {@link CountryUnavailabilityItem} field
     * @return the countryUnavailabilityItem
     */
    public CountryUnavailabilityItem getCountryUnavailabilityItem() {
        return countryUnavailabilityItem;
    }

    /**
     * @serial {@link CountryUnavailabilityItem} field
     * @param countryUnavailabilityItem the countryUnavailabilityItem to set
     */
    public void setCountryUnavailabilityItem(CountryUnavailabilityItem countryUnavailabilityItem) {
        this.countryUnavailabilityItem = countryUnavailabilityItem;
    }

    public SystemUnavailability getSystemUnavailability() {
        return new SystemUnavailabilityImpl(businessFunctionality, downtimeFrom, systemUnavailabilityType, downtimeTo, explanation, explanationLNG);
    }
}
