package org.ecg.refdata.query.model;

import java.math.BigDecimal;

import org.ecg.refdata.query.DictionaryItem;

/**
 * Exchange rate table - unified structure used for storing any kinds of
 * exchange rates.
 *
 */
public interface ExchangeRate extends DictionaryItem {

    /**
     * Currency code (see "48" reference data list)
     *
     * @return currency code
     */
    String getCurrency();

    /**
     * Multiplier.
     *
     * @return multiplier
     */
    Integer getMultiplier();

    /**
     * LTL exchange rate..
     *
     * @return rate
     */
    BigDecimal getRateLTL();

    /**
     * EUR exchange rate..
     *
     * @return rate
     */
    BigDecimal getRateEUR();

}
