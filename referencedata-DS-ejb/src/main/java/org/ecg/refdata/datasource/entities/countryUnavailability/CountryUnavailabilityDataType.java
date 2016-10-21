package org.ecg.refdata.datasource.entities.countryUnavailability;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity()
@Table(name = "ref_country_unavailability_dt")
@DiscriminatorValue(value = "CountryUnavailabilityDataType")
public class CountryUnavailabilityDataType extends ReferenceDataAbstractDataType {

    private static Log log = LogFactory.getLog(CountryUnavailabilityDataType.class);
    private static final long serialVersionUID = 1L;

    public CountryUnavailabilityDataType() {
        super(CountryUnavailabilityItem.class);
    }

}
