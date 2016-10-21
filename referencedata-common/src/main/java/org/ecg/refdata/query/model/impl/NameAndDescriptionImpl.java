package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.NameAndDescription;

/**
 * Class simply stores an information about name and descriptions of
 * dictionaries.
 *
 */
public class NameAndDescriptionImpl implements NameAndDescription {

    private static final long serialVersionUID = 2176216269725562417L;
    /**
     * @serial Name
     */
    private String name;
    /**
     * @serial Language code
     */
    private String languageCode;
    /**
     * @serial Description
     */
    private String description;

    /**
     * Basic constructor takes few following parameters
     *
     * @param name the name
     * @param languageCode the language code
     * @param description the description
     */
    public NameAndDescriptionImpl(String name, String languageCode,
            String description) {
        super();
        this.name = name;
        this.languageCode = languageCode;
        this.description = description;
    }

    /**
     * @see org.ecg.refdata.query.model.NameAndDescription#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see org.ecg.refdata.query.model.NameAndDescription#getLanguageCode()
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @see org.ecg.refdata.query.model.NameAndDescription#getDescription()
     */
    public String getDescription() {
        return description;
    }

    public String getCode() {
        return getLanguageCode();
    }
}
