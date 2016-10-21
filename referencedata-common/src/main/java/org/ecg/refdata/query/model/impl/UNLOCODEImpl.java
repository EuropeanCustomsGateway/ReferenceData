package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.UNLOCODE;

/**
 * United Nations Codes for Trade and Transport Locations.
 *
 *
 */
public class UNLOCODEImpl implements UNLOCODE {

    private static final long serialVersionUID = 8901008194616794215L;
    /**
     * @serial Country code
     */
    private String countryCode;
    /**
     * @serial UnLocode Id
     */
    private String unLocodeId;
    /**
     * @serial UnLocode Name
     */
    private String unLocodeName;

    /**
     * Basic constructor takes few following parameters
     *
     * @param countryCode country code
     * @param unLocodeId United Nations code id
     * @param unLocodeName United Nations
     */
    public UNLOCODEImpl(String countryCode, String unLocodeId,
            String unLocodeName) {
        super();
        this.countryCode = countryCode;
        this.unLocodeId = unLocodeId;
        this.unLocodeName = unLocodeName;
    }

    /**
     *
     * @see org.ecg.refdata.query.model.UNLOCODE#getCountryCode()
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     *
     * @see org.ecg.refdata.query.model.UNLOCODE#getUnLocodeId()
     */
    public String getUnLocodeId() {
        return unLocodeId;
    }

    /**
     *
     * @see org.ecg.refdata.query.model.UNLOCODE#getUnLocodeName()
     */
    public String getUnLocodeName() {
        return unLocodeName;
    }

    public String getCode() {
        return getUnLocodeId();
    }

    public String getDescription() {
        return getUnLocodeName();
    }
}
