package org.ecg.refdata.datasource.ejb.config;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.IDictionaryConfig;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EjbDictionaryConfig implements IDictionaryConfig, Serializable {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(EjbDictionaryConfig.class);

    RefDataConfiguration mapping;
    List<String> dictionaries;
    ReferenceDataSource refDataSource = null;

    public EjbDictionaryConfig(RefDataConfiguration mapping, List<String> dictionaries) {
        this.mapping = mapping;
        this.dictionaries = dictionaries;
    }

    public Collection<String> getDictionariesIds() {
        return dictionaries;
    }

    public IDictionaryConfig getDictionaryConfigForDictionary(String dictionaryId) {
        return this;
    }

    public boolean getDefForceUpperCase() {
        return mapping.getDefForceUpperCase();
    }

    public int getDefItemsOnPage() {
        return mapping.getDefItemsOnPage();
    }

    public int getDefMinTextSearchLength() {
        return mapping.getDefMinTextSearchLength();
    }

    public String getDefSearchColumn() {
        return mapping.getDefSearchColumn();
    }

    public String[] getPreferredColumns() {
        return mapping.getPreferredColumns();
    }

    public ReferenceDataSource getRefDataSource() {
        return refDataSource;
    }

    public void setRefDataSource(ReferenceDataSource ds) {
        refDataSource = ds;
    }

}
