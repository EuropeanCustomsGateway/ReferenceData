package org.ecg.refdata.exceptions;

/**
 * Class represents an exception which is thrown once input id of specific
 * dictionary cannot be found in reference data.
 *
 */
public class NoSuchDictionaryException extends Exception {

    private static final long serialVersionUID = 7818300616795256670L;

    /**
     * Constructor takes one argument
     *
     * @param message a warning message
     */
    public NoSuchDictionaryException(String dictionaryId) {
        super("Could not find dictionary with id: " + dictionaryId);
    }

}
