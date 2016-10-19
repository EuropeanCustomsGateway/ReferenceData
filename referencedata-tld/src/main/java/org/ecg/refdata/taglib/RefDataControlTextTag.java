package org.ecg.refdata.taglib;

import java.sql.Date;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;

import com.cc.framework.taglib.controls.TextTag;
import com.cc.framework.ui.control.Control;
import com.cc.framework.ui.model.ClientEvent;
import org.ecg.refdata.ui.control.RefDataTextControl;
import org.ecg.refdata.ui.model.RefDataTextDesignModel;
import org.ecg.refdata.ui.model.impl.RefDataTextDesignModelImpl;

/**
 * Current class extends <code>TextTag</code> from Common-Controls. Adds couple
 * of new required features. It contains AJAX control which invokes HTTP
 * request, obtains dictionary data and displays them on client side.
 *
 */
public class RefDataControlTextTag extends TextTag {

    private static final long serialVersionUID = 1L;

    /**
     * Method sets reference data dictionary numer
     */
    public void setRefDataSlwNo(String slwNo) {
        getRefDataDesignModel().setRefDataSlwNo(slwNo);
    }

    /**
     * Method sets reference data columns on current page
     */
    public void setRefDataColumns(String columns) {
        getRefDataDesignModel().setRefDataColumns(columns);
    }

    /**
     * Method sets reference data items on current page
     */
    public void setRefDataItemsOnPage(String itemsOnPage) {
        if (itemsOnPage != null) {
            getRefDataDesignModel().setRefDataItemsOnPage(
                    Integer.parseInt(itemsOnPage));
        }
    }

    /**
     * Method sets reference data form name
     */
    public void setRefDataFormName(String formName) {
        getRefDataDesignModel().setRefDataFormName(formName);
    }

    /**
     * Makes reference data form editable
     */
    public void setRefDataFormEditable(String editable) {
        if (editable != null) {
            boolean isEditable = Boolean.parseBoolean(editable);
            getRefDataDesignModel().setRefDataFormEditable(isEditable);
            if (!isEditable) {
                getRefDataDesignModel().setDisabled(true);
            }
        }
    }

    /**
     * Sets reference data search column
     */
    public void setRefDataSearchColumn(String searchColumn) {
        getRefDataDesignModel().setRefDataSearchColumn(searchColumn);
    }

    /**
     * Sets reference data date
     */
    public void setRefDataDate(String date) {
        if (date != null) {
            getRefDataDesignModel().setRefDataDate(Date.valueOf(date));
        }
    }

    /**
     * Sets reference data language code
     */
    public void setRefDataLanguageCode(String languageCode) {
        getRefDataDesignModel().setRefDataLanguageCode(languageCode);
    }

    /**
     * Sets reference data minimum text search length
     */
    public void setRefDataMinTextSearchLength(String minTextSearchLength) {
        if (minTextSearchLength != null) {
            getRefDataDesignModel().setRefDataMinTextSearchLength(
                    Integer.parseInt(minTextSearchLength));
        }
    }

    /**
     * Sets reference data multiple columns mapping
     */
    public void setRefDataMultipleColumnsMapping(String multipleColumnsMapping) {
        getRefDataDesignModel().setRefDataMultipleColumnsMapping(
                multipleColumnsMapping);
    }

    /**
     * Sets reference data force upper case
     */
    public void setRefDataForceUpperCase(String forceUpperCase) {
        if (forceUpperCase != null) {
            getRefDataDesignModel().setRefDataForceUpperCase(
                    Boolean.parseBoolean(forceUpperCase));
        }
    }

    /**
     * Sets reference data servlet URL
     */
    public void setRefDataServletURL(String servletURL) {
        getRefDataDesignModel().setRefDataServletURL(servletURL);
    }

    /*
	 * // CC 1.5, conflicts with CC 1.6 protected Control createControl() throws
	 * JspException { return doCreateControl(); }
     */
    // CC 1.6
    protected Control doCreateControl() throws JspException {
        RefDataTextControl ctrl = new RefDataTextControl(
                getRefDataDesignModel());

        Object value = lookupBean();
        if (value != null) {
            ctrl.setValue(value != null ? value.toString() : null);
        }
        return ctrl;
    }

    /**
     * @see com.cc.framework.taglib.controls.TextTag#doCreateDesignModel()
     */
    public RefDataTextDesignModel doCreateDesignModel() {
        return new RefDataTextDesignModelImpl();
    }

    /**
     * @see com.cc.framework.taglib.controls.TextTag#getDesignModel()
     */
    protected RefDataTextDesignModel getRefDataDesignModel() {
        return (RefDataTextDesignModel) getDesignModel();
    }

    @Override
    public int doStartTag() throws JspException {
        // when we have an editable form then form name must be provided
        if (getRefDataDesignModel().getRefDataFormEditable()) {
            if (GenericValidator.isBlankOrNull(getRefDataDesignModel()
                    .getRefDataFormName())) {
                throw new JspException(
                        "One must provide refDataFormName when refDataFormEditable is set to true.");
            }
        }

        boolean upperCase = getRefDataDesignModel().getRefDataForceUpperCase() == null ? true
                : getRefDataDesignModel().getRefDataForceUpperCase();
        getClientHandler().setHandler(ClientEvent.ONKEYPRESS, "RefDataSupport.openSlw(this, event);");
        getClientHandler().setHandler(ClientEvent.ONCHANGE, "RefDataSupport.changeCase(this, " + upperCase + ");");

        return super.doStartTag();
    }

}
