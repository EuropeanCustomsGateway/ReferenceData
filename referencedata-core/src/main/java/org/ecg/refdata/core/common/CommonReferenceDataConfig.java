package org.ecg.refdata.core.common;

import org.ecg.refdata.ConfiguredReferenceDataSource;
import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.IDictionaryConfig;
import org.ecg.refdata.IReferenceDataConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonReferenceDataConfig implements IReferenceDataConfig {

    private static Log log = LogFactory.getLog(CommonReferenceDataConfig.class);
    private Map<String, ReferenceDataSource> datasources = new HashMap<String, ReferenceDataSource>();
    private Map<String, IDictionaryConfig> dictionaryConfigMap;
    private List<IDictionaryConfig> dictionaryConfigList;

    public Map<String, IDictionaryConfig> getDictionaryConfigMap() {
        Set<String> ids;
        if (dictionaryConfigMap == null) {
            dictionaryConfigMap = new HashMap<String, IDictionaryConfig>();

            for (ReferenceDataSource rds : datasources.values()) {
                if (rds instanceof ConfiguredReferenceDataSource) {
                    ConfiguredReferenceDataSource crds = (ConfiguredReferenceDataSource) rds;
                    for (Entry<String, IDictionaryConfig> ent : crds.getConfig().getDictionaryConfigMap().entrySet()) {
                        ent.getValue().setRefDataSource(rds); // setting reference data source because it is not aviable
                        // via spring here
                        dictionaryConfigMap.put(ent.getKey(), ent.getValue());
                    }
                }
            }
            for (IDictionaryConfig config : dictionaryConfigList) {
                for (String dictionaryId : config.getDictionariesIds()) {
                    if (dictionaryConfigMap.containsKey(dictionaryId)) {
                        log.warn("Common configuration contains definition for dictionary: " + dictionaryId + ", replacing from direct configuration.");
                        dictionaryConfigMap.remove(dictionaryId);
                    }
                    dictionaryConfigMap.put(dictionaryId, config.getDictionaryConfigForDictionary(dictionaryId));
                }
            }
        }
        return dictionaryConfigMap;
    }

    public void setDatasources(Map<String, ReferenceDataSource> datasources) {
        this.datasources = datasources;
    }

    public void setDictionaryConfigList(List<IDictionaryConfig> dictionaryConfigList) {
        this.dictionaryConfigList = dictionaryConfigList;
    }
}
