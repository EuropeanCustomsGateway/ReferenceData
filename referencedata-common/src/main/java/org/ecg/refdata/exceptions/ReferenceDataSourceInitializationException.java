package org.ecg.refdata.exceptions;

/**
 * General initialization exception for ReferenceData source.
 *
 */
public class ReferenceDataSourceInitializationException extends
        ReferenceDataSourceInternalException {

    private static final long serialVersionUID = 3406785082202037078L;

    /**
     * Basic default constructor of current class
     */
    public ReferenceDataSourceInitializationException() {
        super();
    }

    /**
     * Additional constructor which takes couple of additional parameters
     *
     * @param msg a warning message
     * @param cause contains root of exception
     */
    public ReferenceDataSourceInitializationException(String msg,
            Throwable cause) {
        super(msg, cause);
    }

    /**
     * Additional constructor
     *
     * @param msg a warning message
     */
    public ReferenceDataSourceInitializationException(String msg) {
        super(msg);
    }

    /**
     * Another additional constructor
     *
     * @param cause contains root of exception
     */
    public ReferenceDataSourceInitializationException(Throwable cause) {
        super(cause);
    }

}
