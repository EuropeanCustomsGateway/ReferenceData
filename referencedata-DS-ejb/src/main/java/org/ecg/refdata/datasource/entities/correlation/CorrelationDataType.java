package org.ecg.refdata.datasource.entities.correlation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of CorrelationDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_corelation_dt")
@DiscriminatorValue(value = "CorrelationDataType")
public class CorrelationDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = 829966242672782495L;

    public CorrelationDataType() {
        super(CorrelationItem.class);
    }

}
