package org.ecg.refdata.exceptions;

/**
 * Class contains communication exception declaration.
 *
 */
public class CommunicationException extends
        ReferenceDataSourceInternalException {

    private static final long serialVersionUID = -8848512259935485336L;

    /**
     * Basic default constructor of <CODE>CommunicationException</CODE> class
     */
    public CommunicationException() {
        super();
    }

    /**
     * Another constructor with additional arguments
     *
     * @param msg a warning message
     * @param src instance of root exception
     */
    public CommunicationException(String msg, Throwable src) {
        super(msg, src);
    }

    /**
     * Additional constructor with warning parameter.
     *
     * @param msg a warning message
     */
    public CommunicationException(String msg) {
        super(msg);
    }

    /**
     * Another constructor which takes one parameter of root exception
     *
     * @param src instance of root exception
     */
    public CommunicationException(Throwable src) {
        super(src);
    }

}
