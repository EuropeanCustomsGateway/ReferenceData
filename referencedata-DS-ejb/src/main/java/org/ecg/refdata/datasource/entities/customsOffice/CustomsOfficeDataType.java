package org.ecg.refdata.datasource.entities.customsOffice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of CustomsOfficeDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_cust_off_dt")
@DiscriminatorValue(value = "CustomsOfficeDataType")
public class CustomsOfficeDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = 3604756895959817236L;

    public CustomsOfficeDataType() {
        super(CustomsOfficeItem.class);
    }

}
