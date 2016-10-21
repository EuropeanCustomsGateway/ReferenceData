package org.ecg.refdata.datasource.ejb.config;

import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.IDictionaryConfig;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileDictionaryConfig implements IDictionaryConfig {

    private static Log log = LogFactory.getLog(EjbDictionaryConfig.class);

    RefDataConfiguration mapping;
    ReferenceDataSource refDataSource = null;

    public FileDictionaryConfig(RefDataConfiguration mapping) {
        this.mapping = mapping;
    }

    public Collection<String> getDictionariesIds() {
        return Arrays.asList(mapping.getDictionaries());
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
