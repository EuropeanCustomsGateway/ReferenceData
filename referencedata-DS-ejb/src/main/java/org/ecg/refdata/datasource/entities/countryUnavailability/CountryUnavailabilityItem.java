package org.ecg.refdata.datasource.entities.countryUnavailability;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.CountryUnavailability.SystemUnavailability;
import org.ecg.refdata.query.model.impl.CountryUnavailabilityImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity()
@Table(name = "ref_country_unavailability_it")
@DiscriminatorValue(value = "CountryUnavailabilityItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class CountryUnavailabilityItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static Log log = LogFactory.getLog(CountryUnavailabilityItem.class);
    private static final long serialVersionUID = 1L;
    /**
     * Country code.
     */
    @Column(name = "country_code", unique = false, nullable = true, length = 2)
    private String countryCode;

    /**
     * @serial Set of {@link SystemUnavailabilityItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "countryUnavailabilityItem", targetEntity = SystemUnavailabilityItem.class)
    private Set<SystemUnavailabilityItem> systemUnavailabilityItems = new HashSet<SystemUnavailabilityItem>();

    /**
     * for hibernate only
     */
    public CountryUnavailabilityItem() {
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        List<SystemUnavailability> tsystemUnavailabilityItems = new ArrayList<SystemUnavailability>();
        for (SystemUnavailabilityItem systemUnavailabilityItem : systemUnavailabilityItems) {
            tsystemUnavailabilityItems.add(systemUnavailabilityItem.getSystemUnavailability());
        }
        return new CountryUnavailabilityImpl(countryCode, tsystemUnavailabilityItems);
    }

    public CountryUnavailabilityItem(ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    // SystemUnavailabilityItem
    public Set<SystemUnavailabilityItem> getSystemUnavailabilityItems() {
        return Collections.unmodifiableSet(systemUnavailabilityItems);
    }

    public boolean addSystemUnavailabilityItem(
            SystemUnavailabilityItem systemUnavailabilityItem) {
        return systemUnavailabilityItems
                .add(systemUnavailabilityItem);
    }

    public boolean removeSystemUnavailabilityItem(
            SystemUnavailabilityItem systemUnavailabilityItem) {
        return systemUnavailabilityItems
                .remove(systemUnavailabilityItem);
    }

    public void removeAllSystemUnavailabilityItems() {
        systemUnavailabilityItems.clear();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
