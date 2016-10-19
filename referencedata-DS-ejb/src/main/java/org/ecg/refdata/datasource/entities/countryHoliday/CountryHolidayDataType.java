package org.ecg.refdata.datasource.entities.countryHoliday;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of CountryHolidayDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_country_holiday_type")
@DiscriminatorValue(value = "CountryHolidayDataType")
public class CountryHolidayDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = -7287767819447511827L;

    public CountryHolidayDataType() {
        super(CountryHolidayItem.class);
    }
}
