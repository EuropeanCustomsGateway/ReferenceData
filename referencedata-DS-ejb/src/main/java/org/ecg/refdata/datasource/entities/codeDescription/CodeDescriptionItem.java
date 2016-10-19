package org.ecg.refdata.datasource.entities.codeDescription;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.LocalizedItem;
import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;
import org.ecg.refdata.datasource.entities.comodityCode.CommodityCodeItem;
import org.ecg.refdata.datasource.entities.country.CountryItem;
//import org.ecg.refdata.datasource.entities.currencyCode.CurrencyCodeItem;
import org.ecg.refdata.datasource.entities.documentType.DocumentTypeItem;
import org.ecg.refdata.datasource.entities.simpleItem.SimpleItemItem;
import org.ecg.refdata.datasource.entities.valueAdjustment.ValueAdjustmentItem;

/**
 * Class represents entity bean of JPA, description of the code in particular
 * language.
 *
 */
@Entity()
@Table(name = "ref_code_description")
public class CodeDescriptionItem implements LocalizedItem, Serializable {

    private static final long serialVersionUID = -7120526049688459161L;

    /**
     * @serial Id for primary key
     */
    @Id
    @GeneratedValue(generator = "seq_ref_code_description")
    @SequenceGenerator(name = "seq_ref_code_description", sequenceName = "seq_ref_code_description", allocationSize = 25)
    @Column(name = "CODE_DESC_ID")
    protected long id;

    /**
     * @serial Country item field
     */
    @ManyToOne
    @JoinColumn(name = "country_item_id", referencedColumnName = "ref_item_mapping_id")
    protected CountryItem countryItem;

    /**
     * @serial ValueAdjustment item field
     */
    @ManyToOne
    @JoinColumn(name = "value_adjustment_item_id", referencedColumnName = "ref_item_mapping_id")
    protected ValueAdjustmentItem valueAdjustmentItem;

    /**
     * @serial CommodityCode item field
     */
    @ManyToOne
    @JoinColumn(name = "commodity_code_item_id", referencedColumnName = "ref_item_mapping_id")
    protected CommodityCodeItem commodityCodeItem;

//	/**
//	 * @serial Currency code item field
//	 */
//	@ManyToOne
//	@JoinColumn(name = "currency_code_item_id", referencedColumnName = "ref_item_mapping_id")
//	protected CurrencyCodeItem currencyCodeItem;
    /**
     * @serial Document Type Item field
     */
    @ManyToOne
    @JoinColumn(name = "document_type_item_id", referencedColumnName = "ref_item_mapping_id")
    protected DocumentTypeItem documentTypeItem;

    /**
     * @serial Simple Item field
     */
    @ManyToOne
    @JoinColumn(name = "simple_item_item_id", referencedColumnName = "ref_item_mapping_id")
    protected SimpleItemItem simpleItemItem;

    /**
     * @serial Description field
     */
    @Column(name = "description", unique = false, nullable = true, length = 3072)
    protected String description;

    /**
     * @serial Language Code field
     */
    @Column(name = "language_code", unique = false, nullable = true, length = 2)
    protected String languageCode;
//
//	/**
//	 * Creates Code Description Item for CurrencyItem
//	 *
//	 * @param currencyCodeItem
//	 *            - referred currency code item
//	 * @param description
//	 *            - description
//	 * @param languageCode
//	 *            - language code
//	 */
//	public CodeDescriptionItem(
////            CurrencyCodeItem currencyCodeItem,
//			String description, String languageCode) {
//		super();
////		this.currencyCodeItem = currencyCodeItem;
//		this.description = description;
//		this.languageCode = languageCode;
//	}

    /**
     * For Hibernate only
     */
    public CodeDescriptionItem() {

    }

    /**
     * Creates Code Description Item for CurrencyItem
     *
     * @param countryItem - referred country item
     * @param description - description
     * @param languageCode - language code
     */
    public CodeDescriptionItem(CountryItem countryItem, String description,
            String languageCode) {
        super();
        this.countryItem = countryItem;
        this.description = description;
        this.languageCode = languageCode;
    }

    /**
     * Creates Code Description Item for CurrencyItem
     *
     * @param documentTypeItem - referred documentType item
     * @param description - description
     * @param languageCode - language code
     */
    public CodeDescriptionItem(String description,
            DocumentTypeItem documentTypeItem, String languageCode) {
        super();
        this.description = description;
        this.documentTypeItem = documentTypeItem;
        this.languageCode = languageCode;
    }

    /**
     * Creates Code Description Item for CurrencyItem
     *
     * @param description - description
     * @param languageCode - language code
     * @param simpleItemItem - referred simple item
     */
    public CodeDescriptionItem(String description, String languageCode,
            SimpleItemItem simpleItemItem) {
        super();
        this.description = description;
        this.languageCode = languageCode;
        this.simpleItemItem = simpleItemItem;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Returns codeDescription in a given requestedLanguageCode from the
     * collection of CodeDescriptionItems. If a description with the given code
     * is found it is returned else if description with DEFAULT_LANGUAGE_CODE is
     * found it is returned else if any description exists in the collection it
     * is returned else empty string is returned
     *
     * @param codeDescriptionItems - list of codeDescriptions to be search over
     * @param requestedLanguageCode - requested language code of the description
     * @return code description in the preferred language code (if found)
     */
    public static String getLocalizedDescription(
            Collection<CodeDescriptionItem> codeDescriptionItems,
            String requestedLanguageCode) {

        CodeDescriptionItem item = NameAndDescriptionItem.getLocalizedItem(
                codeDescriptionItems, requestedLanguageCode);
        if (item != null) {
            return item.description;
        }

        return "";
    }

    public CountryItem getCountryItem() {
        return countryItem;
    }

    public void setCountryItem(CountryItem countryItem) {
        this.countryItem = countryItem;
    }

//	 public CurrencyCodeItem getCurrencyCodeItem() {
//	 return currencyCodeItem;
//	 }
//
//	 public void setCurrencyCodeItem(CurrencyCodeItem currencyCodeItem) {
//	 this.currencyCodeItem = currencyCodeItem;
//	 }
    public DocumentTypeItem getDocumentTypeItem() {
        return documentTypeItem;
    }

    public void setDocumentTypeItem(DocumentTypeItem documentTypeItem) {
        this.documentTypeItem = documentTypeItem;
    }

    public SimpleItemItem getSimpleItemItem() {
        return simpleItemItem;
    }

    public void setSimpleItemItem(SimpleItemItem simpleItemItem) {
        this.simpleItemItem = simpleItemItem;
    }

    public void setValueAdjustmentItem(ValueAdjustmentItem valueAdjustmentItem) {
        this.valueAdjustmentItem = valueAdjustmentItem;
    }

    public ValueAdjustmentItem getValueAdjustmentItem() {
        return valueAdjustmentItem;
    }

    public void setCommodityCodeItem(CommodityCodeItem commodityCodeItem) {
        this.commodityCodeItem = commodityCodeItem;
    }

    public CommodityCodeItem getCommodityCodeItem() {
        return commodityCodeItem;
    }

}
