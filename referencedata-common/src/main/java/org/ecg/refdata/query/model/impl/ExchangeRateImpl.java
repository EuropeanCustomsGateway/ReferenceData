package org.ecg.refdata.query.model.impl;

import java.math.BigDecimal;

import org.ecg.refdata.query.model.ExchangeRate;

/**
 * Exchange rate Implementation - represents unified structure used for storing
 * any kinds of exchange rates.
 *
 */
public class ExchangeRateImpl implements ExchangeRate {

    private static final long serialVersionUID = -1197223175007283380L;

    /**
     * @serial Currency
     */
    private String currency;

    /**
     * @serial Multiplier
     */
    private Integer multiplier;

    /**
     * @serial Rate LTL
     */
    private BigDecimal rateLTL;

    /**
     * @serial Rate EUR
     */
    private BigDecimal rateEUR;

    /**
     * Basic constructor takes few following parameters
     *
     * @param currency currency code
     * @param multiplier Multiplier
     * @param rate Rate
     */
    public ExchangeRateImpl(String currency, Integer multiplier, BigDecimal rateLTL, BigDecimal rateEUR) {
        this.currency = currency;
        this.multiplier = multiplier;
        this.rateLTL = rateLTL;
        this.rateEUR = rateEUR;
    }

    /**
     * @see org.ecg.refdata.query.model.ExchangeRate#getCurrency()
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @see org.ecg.refdata.query.model.ExchangeRate#getMultiplier()
     */
    public Integer getMultiplier() {
        return multiplier;
    }

    /**
     * @see org.ecg.refdata.query.model.ExchangeRate#getRateLTL()
     */
    public BigDecimal getRateLTL() {
        return rateLTL;
    }

    /**
     * @see org.ecg.refdata.query.model.ExchangeRate#getRateEUR()
     */
    public BigDecimal getRateEUR() {
        return rateEUR;
    }

    public String getCode() {
        return getCurrency();
    }

    public String getDescription() {
        return getRateLTL().toString();
    }
}
