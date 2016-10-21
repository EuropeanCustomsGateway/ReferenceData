package org.ecg.refdata.datasource.entities.localClearanceAuthorization;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity()
@Table(name = "ref_local_clearance_authorization_dt")
@DiscriminatorValue(value = "LocalClearanceAuthorizationDataType")
public class LocalClearanceAuthorizationDataType extends ReferenceDataAbstractDataType {

    private static Log log = LogFactory.getLog(LocalClearanceAuthorizationDataType.class);
    private static final long serialVersionUID = 1L;

    public LocalClearanceAuthorizationDataType() {
        super(LocalClearanceAuthorizationItem.class);
    }

}
