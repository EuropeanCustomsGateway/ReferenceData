package org.ecg.refdata;

import java.util.Map;

public interface IReferenceDataConfig {

    /**
     * Return map of dictionary configs indexed with dictionaryId
     *
     * @return map of dictionary configs indexed with dictionaryId
     */
    Map<String, IDictionaryConfig> getDictionaryConfigMap();

}
