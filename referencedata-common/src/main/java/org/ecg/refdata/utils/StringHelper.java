package org.ecg.refdata.utils;

/**
 * StringHelper
 *
 */
public class StringHelper {

    /**
     * Zamienia pusty zadany ciąg znakowy na null
     *
     * @param input zamieniany ciąg znakowy
     * @return null - jeśli zadany ciąg znakowy jest "" lub null'em, parametr
     * wejściowy - w każdym innym przypadku
     */
    public static final String emptyStringToNull(String input) {
        //log.info("emptyStringToNull - String");
        return "".equals(input) || (input != null && "".equals(input.trim())) ? (String) null : input;
    }

}
