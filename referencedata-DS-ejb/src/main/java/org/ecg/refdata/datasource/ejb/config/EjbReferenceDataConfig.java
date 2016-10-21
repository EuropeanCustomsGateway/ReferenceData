package org.ecg.refdata.datasource.ejb.config;

import org.ecg.refdata.datasource.ejb.config.RefDataConfiguration.UndefinedTypeException;
import org.ecg.refdata.IDictionaryConfig;
import org.ecg.refdata.IReferenceDataConfig;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EjbReferenceDataConfig implements IReferenceDataConfig, Serializable {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(EjbReferenceDataConfig.class);

    Map<String, IDictionaryConfig> dictionaryConfigMap;
    Map<String, String> dictId2Type;

    public EjbReferenceDataConfig(Map<String, String> dictId2Type) {
        this.dictId2Type = dictId2Type;
    }

    public Map<String, IDictionaryConfig> getDictionaryConfigMap() {
        Set<String> ids;
        if (dictionaryConfigMap == null) {
            Map<String, IDictionaryConfig> dictionaryConfigMap2 = new HashMap<String, IDictionaryConfig>();

            for (Entry<String, String> ent : dictId2Type.entrySet()) {
                String id = ent.getKey();
                String type = ent.getValue();
                try {
                    dictionaryConfigMap2.put(id, new EjbDictionaryConfig(new RefDataConfiguration(type), Arrays.asList(new String[]{id})));
                } catch (UndefinedTypeException ex) {
                    log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! internal error !!!", ex);
                    continue;
                }
            }
            dictionaryConfigMap = dictionaryConfigMap2;
        }
        return dictionaryConfigMap;
    }

}
