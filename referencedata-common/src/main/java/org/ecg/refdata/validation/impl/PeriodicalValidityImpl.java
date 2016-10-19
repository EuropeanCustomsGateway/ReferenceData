package org.ecg.refdata.validation.impl;

import org.ecg.refdata.validation.PeriodicalValidity;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PeriodicalValidityImpl implements PeriodicalValidity {

    private static Log log = LogFactory.getLog(PeriodicalValidityImpl.class);
    private static final long serialVersionUID = 1L;

    public Date validFrom;
    public Date validTo;

    public PeriodicalValidityImpl(Date validFrom, Date validTo) {
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public boolean isBetween(Date date) {
        boolean beetwen = ((validFrom == null || (validFrom != null && (validFrom.getTime() <= date.getTime())))
                && (validTo == null || (validTo != null && (date.getTime() <= validTo.getTime()))));
        return beetwen;
    }

}
