package org.ecg.refdata.datasource.ejb.config;

import org.ecg.refdata.datasource.ejb.config.RefDataConfiguration.UndefinedTypeException;
import org.ecg.refdata.ReferenceDataSource;
import org.ecg.refdata.IDictionaryConfig;
import org.ecg.refdata.IReferenceDataConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileReferenceDataConfig implements IReferenceDataConfig {

    private static Log log = LogFactory.getLog(EjbReferenceDataConfig.class);

    Map<String, ReferenceDataSource> datasources = new HashMap<String, ReferenceDataSource>();
    Map<String, IDictionaryConfig> dictionaryConfigMap;

    public Map<String, IDictionaryConfig> getDictionaryConfigMap() {
        Set<String> ids;
        if (dictionaryConfigMap == null) {
            dictionaryConfigMap = new HashMap<String, IDictionaryConfig>();

            ids = RefDataConfiguration.getDictionaryIds();
            for (String id : ids) {
                String type = RefDataConfiguration.getTypeForDictId(id);
                RefDataConfiguration typemaping;
                try {
                    typemaping = new RefDataConfiguration(type);
                } catch (UndefinedTypeException ex) {
                    log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! internal error !!!", ex);
                    continue;
                }
                dictionaryConfigMap.put(id, new FileDictionaryConfig(typemaping));
            }

        }
        return dictionaryConfigMap;
    }

    public void setDatasources(Map<String, ReferenceDataSource> datasources) {
        this.datasources = datasources;
    }

}
