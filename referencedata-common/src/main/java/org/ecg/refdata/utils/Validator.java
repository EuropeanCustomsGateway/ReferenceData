package org.ecg.refdata.utils;

/**
 * Class contains validator for column values
 *
 */
public class Validator {

    private static final long serialVersionUID = 1L;

    /**
     * It use for verification because there is a problem with exchange data
     * which is 'null' and in our application is interpreted as string. It
     * returns true if column name is valid, false in other way.
     *
     * @param columnName column name to be validated
     * @return true if column name is valid, false in other way
     */
    public static boolean isColumnNameNullOrEmpty(String columnName) {
        if (columnName == null) {
            return true;
        }
        // yes - java scipt use string 'null' for nulls marking!
        if (columnName.toLowerCase().equals("null")) {
            return true;
        }
        if (columnName.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
