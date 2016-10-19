package org.ecg.refdata.datasource.entities.exchangeRate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of ExchangeRateDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_exchange_rate_dt")
@DiscriminatorValue(value = "ExchangeRateDataType")
public class ExchangeRateDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = 5803163267179226223L;

    public ExchangeRateDataType() {
        super(ExchangeRateItem.class);
    }
}
