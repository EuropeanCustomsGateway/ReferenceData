package org.ecg.refdata.datasource.entities.unLocode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of UnLocodeDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_un_locode_data_type")
@DiscriminatorValue(value = "UnLocodeDataType")
public class UnLocodeDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = 8588187640608568669L;

    public UnLocodeDataType() {
        super(UnLocodeItem.class);
    }

}
