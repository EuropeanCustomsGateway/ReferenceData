package org.ecg.refdata.taglib;

import java.sql.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cc.framework.taglib.forms.FormElementTextTag;
import com.cc.framework.taglib.forms.FormTag;
import com.cc.framework.ui.FormType;
import com.cc.framework.ui.control.Control;
import com.cc.framework.ui.model.ClientEvent;
import com.cc.framework.ui.model.FormDesignModel;
import org.ecg.refdata.ui.control.RefDataTextControl;
import org.ecg.refdata.ui.model.RefDataTextDesignModel;
import org.ecg.refdata.ui.model.impl.RefDataTextDesignModelImpl;
import org.ecg.refdata.utils.StringHelper;

/**
 * Current class extends <code>FormElementTextTag</code> from Common-Controls.
 * Adds couple of new required features. It contains AJAX control which invokes
 * HTTP request, obtains dictionary data and displays them on client side.
 *
 */
public class RefDataFormTextTag extends FormElementTextTag {

    private static final long serialVersionUID = 1L;

    public void setStrictValue(String strictValue) {
        getRefDataDesignModel().setStrictValue(strictValue);
    }

    public void setMyStyle(String myStyle) {
        getRefDataDesignModel().setMyStyle(myStyle);
    }

    public void setImageOnClick(String imageOnClick) {
        getRefDataDesignModel().setImageOnClick(imageOnClick);
    }

    public void setJavascriptImageOnClick(String javascriptImageOnClick) {
        getRefDataDesignModel().setJavascriptImageOnClick(javascriptImageOnClick);
    }

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
            getRefDataDesignModel().setRefDataItemsOnPage(Integer.parseInt(itemsOnPage));
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
            getRefDataDesignModel().setRefDataMinTextSearchLength(Integer.parseInt(minTextSearchLength));
        }
    }

    /**
     * Sets reference data multiple columns mapping
     */
    public void setRefDataMultipleColumnsMapping(String multipleColumnsMapping) {
        getRefDataDesignModel().setRefDataMultipleColumnsMapping(multipleColumnsMapping);
    }

    /**
     * Sets reference data force upper case
     */
    public void setRefDataForceUpperCase(String forceUpperCase) {
        if (forceUpperCase != null) {
            getRefDataDesignModel().setRefDataForceUpperCase(Boolean.parseBoolean(forceUpperCase));
        }
    }

    /**
     * Sets reference data servlet URL
     */
    public void setRefDataServletURL(String servletURL) {
        getRefDataDesignModel().setRefDataServletURL(servletURL);
    }

    /**
     * Sets refDataFormEditable flag
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
     * Sets customRequired flag
     *
     * @param customRequired
     */
    public void setCustomRequired(String customRequired) {
        if (customRequired != null) {
            boolean isCustomRequired = Boolean.parseBoolean(customRequired);
            getRefDataDesignModel().setCustomRequired(isCustomRequired);
        }
    }

    /**
     * Overrided standard setter for disabling build in functionality of
     * required flag
     *
     * @param required
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public void setRequired(String required) throws JspException {
        this.setCustomRequired(required);
    }

    public void setRefDataMetadata(String refDataMetadata) {
        getRefDataDesignModel().setMetadata(refDataMetadata);
    }

    public String getRefDataMetadata() {
        return getRefDataDesignModel().getMetadata();
    }

    /*
    // CC 1.5, conflicts with CC 1.6
    protected Control createControl() {
    return doCreateControl();
    }
     */
    // CC 1.6
    protected Control doCreateControl() {
        return new RefDataTextControl(getRefDataDesignModel());
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
        String clientHandler = null;
        FormTag formTag = (FormTag) TagSupport.findAncestorWithClass(this, FormTag.class);
        FormDesignModel designModel = (FormDesignModel) formTag.getClientHandler();

        getRefDataDesignModel().setRefDataFormName(designModel.getFormId());
        FormType formType = designModel.getFormType();
        // as a default field is editable unless form isn't editable or itself isn't editable (in second situation it isn't changed boolaen is has always a value)
        if (formType != null && !formType.isEditable()) {
            getRefDataDesignModel().setRefDataFormEditable(false);
        }

        boolean upperCase = getRefDataDesignModel().getRefDataForceUpperCase() == null ? true
                : getRefDataDesignModel().getRefDataForceUpperCase();
        clientHandler = null;
        clientHandler = getClientHandler().getHandler(ClientEvent.ONKEYPRESS);
        getClientHandler().setHandler(ClientEvent.ONKEYPRESS, (StringHelper.emptyStringToNull(clientHandler) != null ? clientHandler + " " : "") + "RefDataSupport.openSlw(this, event);");
        clientHandler = null;
        clientHandler = getClientHandler().getHandler(ClientEvent.ONCHANGE);
        getClientHandler().setHandler(ClientEvent.ONCHANGE, (StringHelper.emptyStringToNull(clientHandler) != null ? clientHandler + " " : "") + "RefDataSupport.changeCase(this, " + upperCase + ");");

        return super.doStartTag();
    }
}
