package org.ecg.refdata.validation.impl;

import org.ecg.refdata.validation.PeriodicalValidity;
import org.ecg.refdata.validation.ValidationResult;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidationResultImpl implements ValidationResult {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(ValidationResultImpl.class);
    private String dictionaryId;
    private String value;
    private boolean exists;
    private List<PeriodicalValidity> validity;

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public ValidationResultImpl(String dictionaryId, String value, boolean exists) {
        this.dictionaryId = dictionaryId;
        this.value = value;
        this.exists = exists;
    }

    public void addValidity(Date validFrom, Date validTo) {
        if (validity == null) {
            validity = new LinkedList<PeriodicalValidity>();
        }
        //TODO: optimize arrangement ... order by valid from or to...
        validity.add(new PeriodicalValidityImpl(validFrom, validTo));
    }

    public boolean isValid(Date date) {
        if (!exists) {
            return false;
        }
        if (date == null) {
            return true;
        }
        if (validity == null || validity.isEmpty()) {
            return true;
        }
        for (PeriodicalValidity object : validity) {
            if (object.isBetween(date)) {
                return true;
            }
        }
        return false;
    }
}
