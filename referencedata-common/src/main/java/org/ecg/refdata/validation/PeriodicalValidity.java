package org.ecg.refdata.validation;

import java.util.Date;

/**
 * @TODO : description...
 *
 */
public interface PeriodicalValidity {

    public Date getValidFrom();

    public Date getValidTo();

    public boolean isBetween(Date date);
}
