package org.ecg.refdata.datasource.ejb.config;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RefDataConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    static ResourceBundle bundle;

    static HashMap<String, String> dictId2type = new HashMap<String, String>();
    static HashMap<String, String> dataTypeClass2type = new HashMap<String, String>();
    static HashSet<String> types = new HashSet<String>();

    public static class UndefinedTypeException extends Exception {

        public UndefinedTypeException() {
        }

        public UndefinedTypeException(String message) {
            super(message);
        }
    }

    static {
        bundle = ResourceBundle.getBundle("ejb-refdata-mapping");

        for (String key : bundle.keySet()) {
            if (key.startsWith("default")) {
                continue;
            }
            String split[] = key.split("\\.");
            types.add(split[0]);

        }

        for (String type : types) {
            if (!bundle.keySet().contains(type + ".dictionaries")) {
                continue;
            }
            String[] dicts = bundle.getString(type + ".dictionaries").split(",");
            for (String dictId : dicts) {
                dictId2type.put(dictId.trim(), type);
            }
        }
        for (String type : types) {
            if (!bundle.keySet().contains(type + ".dictionaries")) {
                continue;
            }
            String[] dicts = bundle.getString(type + ".dictionaries").split(",");
            for (String dictId : dicts) {
                dictId2type.put(dictId.trim(), type);
            }
        }
    }

    public static Set<String> getDictionaryIds() {
        return dictId2type.keySet();
    }

    public static String getTypeForDictId(String dictionaryId) {
        return dictId2type.get(dictionaryId);
    }
    //---------//---------//---------//---------//---------//---------//---------//
    //---//---------//---------//---------//---------//---------//---------//-----

    String type;
    String[] dictionaries;
    boolean defForceUpperCase;
    int defItemsOnPage;
    int defMinTextSearchLength;
    String defSearchColumn;
    String refDataSource;
    String[] defColumns;
    boolean cache;

    Class<ReferenceDataAbstractItemEntity> referenceDataAbstractItemEntityClass;
    Class<ReferenceDataAbstractDataType> referenceDataAbstractDataType;

    public RefDataConfiguration(String typeOfDict) throws UndefinedTypeException {
        if (!types.contains(typeOfDict)) {
            throw new UndefinedTypeException("configuration for reference data type with name '" + typeOfDict + "' not found.");
        }
        type = typeOfDict;
        try {
            referenceDataAbstractItemEntityClass = (Class<ReferenceDataAbstractItemEntity>) (getValue("referenceDataAbstractItemEntityClass") == null ? null : getClass().getClassLoader().loadClass(getValue("referenceDataAbstractItemEntityClass")));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RefDataConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            throw new UndefinedTypeException("configuration for reference data type with name '" + typeOfDict + "' not complete , wrong  referenceDataAbstractItemEntityClass.");
        }
        try {
            referenceDataAbstractDataType = (Class<ReferenceDataAbstractDataType>) (getValue("referenceDataAbstractDataType") == null ? null : getClass().getClassLoader().loadClass(getValue("referenceDataAbstractDataType")));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RefDataConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            throw new UndefinedTypeException("configuration for reference data type with name '" + typeOfDict + "' not complete , wrong  referenceDataAbstractItemEntityClass.");
        }
        dictionaries = getValue("dictionaries") == null ? null : getValue("dictionaries").split("\\s*,\\s*");
        defForceUpperCase = ((getValueOrDef("defForceUpperCase") == null) ? false : Boolean.valueOf(getValueOrDef("defForceUpperCase")));
        defItemsOnPage = ((getValueOrDef("defItemsOnPage") == null) ? 10 : Integer.parseInt(getValueOrDef("defItemsOnPage")));
        defMinTextSearchLength = ((getValueOrDef("defMinTextSearchLength") == null) ? 10 : Integer.parseInt(getValueOrDef("defMinTextSearchLength")));
        defSearchColumn = getValueOrDef("defSearchColumn");
        defForceUpperCase = ((getValueOrDef("defForceUpperCase") == null) ? false : Boolean.valueOf(getValueOrDef("defForceUpperCase")));
        refDataSource = getValue("refDataSource") == null ? "datasourceEJB" : getValue("refDataSource");
        defColumns = getValue("defColumns") == null ? null : getValue("defColumns").split("\\s*,\\s*");
        cache = ((getValueOrDef("cache") == null) ? true : Boolean.valueOf(getValueOrDef("cache")));
    }

    public Class<ReferenceDataAbstractDataType> getReferenceDataAbstractDataType() {
        return referenceDataAbstractDataType;
    }

    public Class<ReferenceDataAbstractItemEntity> getReferenceDataAbstractItemEntityClass() {
        return referenceDataAbstractItemEntityClass;
    }

    public String[] getDictionaries() {
        return dictionaries;
    }

    public boolean getDefForceUpperCase() {
        return defForceUpperCase;
    }

    public int getDefItemsOnPage() {
        return defItemsOnPage;
    }

    public int getDefMinTextSearchLength() {
        return defMinTextSearchLength;
    }

    public String getDefSearchColumn() {
        return defSearchColumn;
    }

    public String getRefDataSource() {
        return refDataSource;
    }

    public String[] getPreferredColumns() {
        return defColumns;
    }

    public boolean getCache() {
        return cache;
    }

    private String getValue(String name) {
        String key = type + "." + name;
        if (bundle.keySet().contains(key)) {
            return bundle.getString(key);
        } else {
            return null;
        }
    }

    private String getValueOrDef(String name) {
        String key = type + "." + name;
        String defkey = "default." + name;
        if (bundle.keySet().contains(key)) {
            return bundle.getString(key);
        } else if (bundle.keySet().contains(defkey)) {
            return bundle.getString(defkey);
        } else {
            return null;
        }
    }

}
