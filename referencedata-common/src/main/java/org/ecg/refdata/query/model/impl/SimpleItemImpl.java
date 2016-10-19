package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.SimpleItem;

/**
 * Class stores code of single item and related description of it.
 *
 */
public class SimpleItemImpl implements SimpleItem {

    private static final long serialVersionUID = -1217852376511203643L;
    /**
     * @serial Code
     */
    private String code;
    /**
     * @serial Description
     */
    private String description;

    private Boolean national;

    /**
     * Constructor gets two simple parameters
     *
     * @param code contains code of item
     * @param description description of item
     */
    public SimpleItemImpl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Constructor gets two simple parameters
     *
     * @param code contains code of item
     * @param description description of item
     */
    public SimpleItemImpl(String code, String description, Boolean national) {
        this.code = code;
        this.description = description;
        this.national = national;
    }

    /**
     *
     * @see org.ecg.refdata.query.model.SimpleItem#getCode()
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @see org.ecg.refdata.query.model.SimpleItem#getDescription()
     */
    public String getDescription() {
        return description;
    }

    public Boolean getNational() {
        return national;
    }
}
