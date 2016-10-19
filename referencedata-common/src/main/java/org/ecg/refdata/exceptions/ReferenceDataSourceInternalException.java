package org.ecg.refdata.exceptions;

/**
 * An exception which is throwed in case of any problems with data source
 * initialization (for instance - invalid configuration or lack of network
 * connection).
 *
 */
public class ReferenceDataSourceInternalException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Basic default constructor of <CODE>InitializationException</CODE> class
     */
    public ReferenceDataSourceInternalException() {
        super();
    }

    /**
     * Another constructor with additional arguments
     *
     * @param msg a warning message
     * @param src instance of root exception
     */
    public ReferenceDataSourceInternalException(String msg, Throwable src) {
        super(msg, src);
    }

    /**
     * Additional constructor with warning parameter.
     *
     * @param msg a warning message
     */
    public ReferenceDataSourceInternalException(String msg) {
        super(msg);
    }

    /**
     * Another constructor which takes one parameter of root exception
     *
     * @param src instance of root exception
     */
    public ReferenceDataSourceInternalException(Throwable src) {
        super(src);
    }

}
