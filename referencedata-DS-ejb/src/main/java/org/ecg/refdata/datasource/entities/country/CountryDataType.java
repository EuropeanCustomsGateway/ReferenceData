package org.ecg.refdata.datasource.entities.country;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of CountryDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_country_dt")
@DiscriminatorValue(value = "CountryDataType")
public class CountryDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = 8826648705587294208L;

    public CountryDataType() {
        super(CountryItem.class);
    }
}
