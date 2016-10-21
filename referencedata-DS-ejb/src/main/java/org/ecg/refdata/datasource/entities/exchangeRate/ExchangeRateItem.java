package org.ecg.refdata.datasource.entities.exchangeRate;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.ExchangeRateImpl;

/**
 * Class represents entity bean of JPA. Exchange rate table - unified structure
 * used for storing any kinds of exchange rates.
 *
 */
@Entity()
@Table(name = "ref_exchange_rate_it")
@DiscriminatorValue(value = "ExchangeRateItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class ExchangeRateItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = 6335737422419234063L;
    /**
     * @serial Currency field
     */
    @Column(name = "currency", unique = false, nullable = true, length = 3)
    private String currency;
    /**
     * @serial Multiplier field
     */
    @Column(name = "multiplier", unique = false, nullable = true)
    private Integer multiplier;
    /**
     * @serial Rate rateLTL
     */
    @Column(name = "rate_ltl", unique = false, nullable = true, precision = 9, scale = 4)
    private BigDecimal rateLTL;
    /**
     * @serial Rate rateEUR
     */
    @Column(name = "rate_eur", unique = false, nullable = true, precision = 9, scale = 4)
    private BigDecimal rateEUR;

    /**
     * For hibernate only
     */
    ExchangeRateItem() {
        super();
    }

    /**
     * Creates ExchangeRateItem Item for a given
     * ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public ExchangeRateItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public BigDecimal getRateLTL() {
        return rateLTL;
    }

    public void setRateLTL(BigDecimal rateLTL) {
        this.rateLTL = rateLTL;
    }

    public BigDecimal getRateEUR() {
        return rateEUR;
    }

    public void setRateEUR(BigDecimal rateEUR) {
        this.rateEUR = rateEUR;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        // Locale in this type of element aren't used
        ExchangeRateImpl exchangeRateImpl = new ExchangeRateImpl(this.currency,
                this.multiplier, this.rateLTL, this.rateEUR);
        return exchangeRateImpl;
    }

}
