package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.Correlation;

/**
 * Class stores all information about correlation codes.
 *
 */
public class CorrelationImpl implements Correlation {

    private static final long serialVersionUID = 4042267683575572307L;
    /**
     * @serial Code 1
     */
    private String code1;
    /**
     * @serial Code 2
     */
    private String code2;
    /**
     * @serial Code 3
     */
    private String code3;
    /**
     * @serial Code 4
     */
    private String code4;

    /**
     * Basic constructor takes three parameters
     *
     * @param code1 the first correlation code
     * @param code2 the second correlation code.
     * @param code3 the third'th correlation code.
     * @param code4 the four'th correlation code.
     */
    public CorrelationImpl(String code1, String code2, String code3, String code4) {
        this.code1 = code1;
        this.code2 = code2;
        this.code3 = code3;
        this.code4 = code4;
    }

    public String getCode() {
        return getCode1();
    }

    public String getDescription() {
        return null;
    }

    /**
     * @see org.ecg.refdata.query.model.Correlation#getCode1()
     */
    public String getCode1() {
        return code1;
    }

    /**
     * @see org.ecg.refdata.query.model.Correlation#getCode2()
     */
    public String getCode2() {
        return code2;
    }

    /**
     * @see org.ecg.refdata.query.model.Correlation#getCode3()
     */
    public String getCode3() {
        return code3;
    }

    /**
     * @see org.ecg.refdata.query.model.Correlation#getCode4()
     */
    public String getCode4() {
        return code4;
    }

}
