package org.ecg.refdata.datasource.entities;

import java.io.Serializable;

/**
 * Interface for localizable items, it simplify searching for an item in a given
 * localeCode
 *
 */
public interface LocalizedItem extends Serializable {

    public String getLanguageCode();
}
