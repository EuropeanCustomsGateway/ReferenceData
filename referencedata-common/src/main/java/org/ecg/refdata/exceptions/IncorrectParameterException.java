package org.ecg.refdata.exceptions;

/**
 * Class contains incorrect parameter exception declaration.
 *
 */
public class IncorrectParameterException extends ReferenceDataSourceInternalException {

    private static final long serialVersionUID = -5217151862044576663L;

    /**
     * Basic default constructor of <CODE>IncorrectParameterException</CODE>
     * class
     */
    public IncorrectParameterException() {
        super();
    }

    /**
     * Another constructor with additional arguments
     *
     * @param msg a warning message
     * @param src instance of root exception
     */
    public IncorrectParameterException(String msg, Throwable src) {
        super(msg, src);
    }

    /**
     * Additional constructor with warning parameter.
     *
     * @param msg a warning message
     */
    public IncorrectParameterException(String msg) {
        super(msg);
    }

    /**
     * Another constructor which takes one parameter of root exception
     *
     * @param src instance of root exception
     */
    public IncorrectParameterException(Throwable src) {
        super(src);
    }

}
