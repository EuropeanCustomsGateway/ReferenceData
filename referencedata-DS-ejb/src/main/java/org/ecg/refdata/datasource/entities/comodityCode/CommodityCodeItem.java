package org.ecg.refdata.datasource.entities.comodityCode;

import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.impl.CommodityCodeImpl;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity()
@Table(name = "ref_commodity_code_it")
@DiscriminatorValue(value = "CommodityCodeItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class CommodityCodeItem extends ReferenceDataPeriodicValidityAbstractItem {

    private static Log log = LogFactory.getLog(CommodityCodeItem.class);
    private static final long serialVersionUID = 1L;
    /**
     * Commodity code.
     */
    @Column(name = "code", unique = false, nullable = true, length = 10)
    private String code;
    /**
     * Excise object flag.
     */
    @Column(name = "excise", unique = false, nullable = true)
    private Boolean excise;
    /**
     * Commodity code.
     */
    @Column(name = "indents", unique = false, nullable = true, length = 2)
    private Long ident;
    /**
     * Excise object flag.
     */
    @Column(name = "suffix", unique = false, nullable = true, length = 2)
    private String suffix;
    /**
     * Information whether commodity code can be used in import declarations (1
     * - yes, 0 - no).
     */
    @Column(name = "import_flag", unique = false, nullable = true)
    private Boolean importFlag;
    /**
     * Information whether commodity code can be used in export declarations (1
     * - yes, 0 - no).
     */
    @Column(name = "export_flag", unique = false, nullable = true)
    private Boolean exportFlag;

    /**
     * for hibernate only
     */
    public CommodityCodeItem() {
    }

    /**
     * @serial Set of {@link CodeDescriptionItem} field
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commodityCodeItem", targetEntity = CodeDescriptionItem.class)
    private Set<CodeDescriptionItem> codeDescriptionItems = new HashSet<CodeDescriptionItem>();

    public Set<CodeDescriptionItem> getCodeDescriptionItems() {
        return Collections.unmodifiableSet(codeDescriptionItems);
    }

    public boolean addCodeDescriptionItem(
            CodeDescriptionItem codeDescriptionItem) {
        return codeDescriptionItems.add(codeDescriptionItem);
    }

    public boolean removeCodeDescriptionItem(
            CodeDescriptionItem codeDescriptionItem) {
        return codeDescriptionItems.remove(codeDescriptionItem);
    }

    public void removeAllCodeDescriptionItem() {
        codeDescriptionItems.clear();
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {
        // Locale in this type of element aren't used
        CommodityCodeImpl correlationImpl = new CommodityCodeImpl(getCode(), CodeDescriptionItem
                .getLocalizedDescription(getCodeDescriptionItems(), locale), getExcise(), getImportFlag(), getExportFlag(), getIdent(), getSuffix());
        return correlationImpl;
    }

    /**
     * Creates CommodityCodeItem Item for a given
     * ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public CommodityCodeItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the excise
     */
    public Boolean getExcise() {
        return excise;
    }

    /**
     * @param excise the excise to set
     */
    public void setExcise(Boolean excise) {
        this.excise = excise;
    }

    public Long getIdent() {
        return ident;
    }

    public void setIdent(Long ident) {
        this.ident = ident;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the importFlag
     */
    public Boolean getImportFlag() {
        return importFlag;
    }

    /**
     * @param importFlag the importFlag to set
     */
    public void setImportFlag(Boolean importFlag) {
        this.importFlag = importFlag;
    }

    /**
     * @return the exportFlag
     */
    public Boolean getExportFlag() {
        return exportFlag;
    }

    /**
     * @param exportFlag the exportFlag to set
     */
    public void setExportFlag(Boolean exportFlag) {
        this.exportFlag = exportFlag;
    }

}
