package org.ecg.refdata.core;

import java.util.Collection;
import java.util.Map;

/**
 * Simple class which which helps spring keep mapping between dictionary id and
 * preferred columns names for this dictionary.
 *
 */
public class DictionariesTypes {

    private static final long serialVersionUID = -5971528762393697627L;

    /**
     * This map allows to specific for every dictionary (first string) list of
     * columns separated by char ','
     */
    private Map<String, String> dictionariesTypes;

    /**
     * Simple method which takes one parameter - map which represents a link
     * between dictionary kinds and column names.
     *
     * @param dictionariesTypes the dictionariesTypes to set
     */
    public void setDictionariesTypes(Map<String, String> dictionariesTypes) {
        this.dictionariesTypes = dictionariesTypes;
    }

    /**
     * Method returns map which represents a link between dictionary types and
     * column names.
     *
     * @return the dictionaryTypes
     */
    public Map<String, String> getDictionariesTypes() {
        return dictionariesTypes;
    }

    /**
     * Returns collection of all dictionaries ids configured for this dictionary
     * type
     *
     * @return list of dictionary ids in this type
     */
    public Collection<String> getDictionariesIds() {
        return dictionariesTypes.keySet();
    }

    /**
     * Returns preferred columns list for given dictionary id. If no specific
     * configuration exist for the dictionary defaultPreferredColumns will be
     * returned.
     *
     * @param dictionaryId - id of dictionary to return preferred columns for
     * @param defaultPreferredColumns - default preferred columns list used if
     * no specific definition exists
     * @return list of preferred columns for given dictionary
     */
    public String[] getPreferredColumns(String dictionaryId,
            String defaultPreferredColumns) {
        String preferredcolumns = this.dictionariesTypes.get(dictionaryId);
        if (preferredcolumns == null || preferredcolumns.trim().length() == 0) {
            preferredcolumns = defaultPreferredColumns;
        }
        return preferredcolumns.split(",");
    }
}
