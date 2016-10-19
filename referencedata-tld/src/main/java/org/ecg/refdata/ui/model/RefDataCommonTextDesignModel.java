package org.ecg.refdata.ui.model;

import java.util.Date;

public interface RefDataCommonTextDesignModel {

    public String getStrictValue();

    public void setStrictValue(String strictValue);

    public String getMyStyle();

    public void setMyStyle(String myStyle);

    public void setImageOnClick(String imageOnClick);

    public String getImageOnClick();

    public void setJavascriptImageOnClick(String javascriptImageOnClick);

    public String getJavascriptImageOnClick();

    /**
     * Sets Dictionary Id number for current dictionary
     *
     * @param slwNo the dictionary number to set
     */
    void setRefDataSlwNo(String slwNo);

    /**
     * Gets Dictionary Id number for current dictionary
     *
     * @return the dictionary number
     */
    String getRefDataSlwNo();

    /**
     * Sets columns which will be shown
     *
     * @param columns comma separated columns list
     */
    void setRefDataColumns(String columns);

    /**
     * Gets columns which will be shown
     *
     * @return comma seperated columns list
     */
    String getRefDataColumns();

    /**
     * Sets number of items on a page
     *
     * @param itemsOnPage
     */
    void setRefDataItemsOnPage(int itemsOnPage);

    /**
     * Gets number of items on a page
     *
     * @return number of items
     */
    int getRefDataItemsOnPage();

    /**
     * Sets name of form
     *
     * @param formName form name
     */
    void setRefDataFormName(String formName);

    /**
     * Gets name of form
     *
     * @return form name
     */
    String getRefDataFormName();

    /**
     * Sets editable form flag
     *
     * @param editable is form editable
     */
    void setRefDataFormEditable(boolean editable);

    /**
     * Gets editable form flag
     *
     * @return is form editable
     */
    boolean getRefDataFormEditable();

    /**
     * Sets search column for current dictionary
     *
     * @param searchColumn the search column
     */
    void setRefDataSearchColumn(String searchColumn);

    /**
     * Gets search column for current dictionary
     *
     * @return the search column
     */
    String getRefDataSearchColumn();

    /**
     * Sets date to search specific data
     *
     * @param date the reference data date
     */
    void setRefDataDate(Date date);

    /**
     * Get date to search specific data
     *
     * @return the reference data date
     */
    Date getRefDataDate();

    /**
     * Sets language code
     *
     * @param languageCode the language code (ie. EN)
     */
    void setRefDataLanguageCode(String languageCode);

    /**
     * Gets language code
     *
     * @return the language code
     */
    String getRefDataLanguageCode();

    /**
     * Sets minimum text length for which search is allowed
     *
     * @param minTextSearchLength minimum text length
     */
    void setRefDataMinTextSearchLength(int minTextSearchLength);

    /**
     * Gets minimum text length for which search is allowed
     *
     * @return minimum text length
     */
    int getRefDataMinTextSearchLength();

    /**
     * Sets multiple columns mapping for editable tag library
     *
     * @param multipleColumnsMapping multiple columns mapping
     */
    void setRefDataMultipleColumnsMapping(String multipleColumnsMapping);

    /**
     * Gets multiple columns mapping for editable tag library
     *
     * @return multiple columns mapping
     */
    String getRefDataMultipleColumnsMapping();

    /**
     * Sets flag upper-case - should be forced in input text
     *
     * @param forceUpperCase whether upper-case should be forced in input text
     */
    void setRefDataForceUpperCase(Boolean forceUpperCase);

    /**
     * Gets flag upper-case - should be forced in input text
     *
     * @return whether upper-case should be forced in input text
     */
    Boolean getRefDataForceUpperCase();

    /**
     * Sets specific URL to another servlet than defined
     *
     * @param servletURL servlet url
     */
    void setRefDataServletURL(String servletURL);

    /**
     * Gets specific URL to another servlet than defined
     *
     * @return servlet url
     */
    String getRefDataServletURL();

    /**
     * Gets reference data value property
     *
     * @return value property
     */
    String getProperty();

    /**
     * Sets custom required flag
     *
     * @param customRequired required flag
     */
    void setCustomRequired(boolean customRequired);

    /**
     * Gets custom required flag
     *
     * @return
     */
    boolean isCustomRequired();

    String getMetadata();

    void setMetadata(String metadata);

}
