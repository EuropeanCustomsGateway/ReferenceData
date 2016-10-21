package org.ecg.refdata.datasource.entities.countryRegion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of CountryRegionDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_country_region_dt")
@DiscriminatorValue(value = "CountryRegionDataType")
public class CountryRegionDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = -3232188989594035536L;

    public CountryRegionDataType() {
        super(CountryRegionItem.class);
    }

}
