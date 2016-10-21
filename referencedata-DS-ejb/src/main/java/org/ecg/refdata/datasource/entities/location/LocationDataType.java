package org.ecg.refdata.datasource.entities.location;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of LocationDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_location_dt")
@DiscriminatorValue(value = "LocationDataType")
public class LocationDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = -4689071056374121204L;

    public LocationDataType() {
        super(LocationItem.class);
    }

}
