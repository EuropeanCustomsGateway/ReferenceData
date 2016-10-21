package org.ecg.refdata.datasource.entities.valueAdjustment;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity()
@Table(name = "ref_value_adjustment_dt")
@DiscriminatorValue(value = "ValueAdjustmentDataType")
public class ValueAdjustmentDataType extends ReferenceDataAbstractDataType {

    private static Log log = LogFactory.getLog(ValueAdjustmentDataType.class);
    private static final long serialVersionUID = 1L;

    public ValueAdjustmentDataType() {
        super(ValueAdjustmentItem.class);
    }

}
