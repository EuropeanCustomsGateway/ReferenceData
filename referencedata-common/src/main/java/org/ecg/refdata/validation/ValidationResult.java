package org.ecg.refdata.validation;

import java.util.Date;

public interface ValidationResult {

    public String getDictionaryId();

    public String getValue();

    public boolean isExists();

    public boolean isValid(Date date);
}
