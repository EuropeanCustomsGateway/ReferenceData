package org.ecg.refdata;

import java.util.Collection;

public interface IDictionaryConfig {

    /**
     * @return a flag which stands if upper case characters should be send
     */
    boolean getDefForceUpperCase();

    /**
     * @return default quantity of items on page
     */
    int getDefItemsOnPage();

    /**
     * @return the default minimum text search length
     */
    int getDefMinTextSearchLength();

    /**
     * @return default search column
     */
    String getDefSearchColumn();

    /**
     * Returns collection of dictionaries ids handled by this Spring dictionary
     * config
     *
     * @return list of dictionaries ids
     */
    Collection<String> getDictionariesIds();

//    /**
//     * Method gets <code>dictionaryTypes</code> instance.
//     *
//     * @return the dictionariesTypes
//     */
//    DictionariesTypes getDictionariesTypes();

    /**
     * Returns collection of dictionaries ids handled by this Spring dictionary
     * config
     *
     * @return list of dictionaries ids
     */
    IDictionaryConfig getDictionaryConfigForDictionary(String dictionaryId);

    /**
     * @return columns method sets valid column
     * @see org.ecg.refdata.IDictionaryConfig#getValidColumns()
     */
    String[] getPreferredColumns();

    /**
     * Method gets <code>refDataSource</code> instance.
     *
     * @return the refDataSource
     */
    ReferenceDataSource getRefDataSource();
    
    /**
     * Method set <code>refDataSource</code> instance. used in remote
     * config 
     *
     * @return the refDataSource
     */
    void setRefDataSource(ReferenceDataSource ds);

}
