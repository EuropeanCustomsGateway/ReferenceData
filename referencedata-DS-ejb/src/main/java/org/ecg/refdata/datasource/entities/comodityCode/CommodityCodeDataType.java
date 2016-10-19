package org.ecg.refdata.datasource.entities.comodityCode;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class represents a type of CommodityCodeDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_commodity_code_dt")
@DiscriminatorValue(value = "CommodityCodeDataType")
public class CommodityCodeDataType extends ReferenceDataAbstractDataType {

    private static Log log = LogFactory.getLog(CommodityCodeDataType.class);
    private static final long serialVersionUID = 1L;

    public CommodityCodeDataType() {
        super(CommodityCodeItem.class);
    }
}
