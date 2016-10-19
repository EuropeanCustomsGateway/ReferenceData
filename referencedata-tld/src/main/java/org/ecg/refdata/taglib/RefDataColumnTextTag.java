package org.ecg.refdata.taglib;

import java.sql.Date;

import com.cc.framework.taglib.controls.ColumnBaseTag;
import com.cc.framework.ui.model.ColumnDesignModel;
import org.ecg.refdata.ui.model.RefDataColumnTextDesignModel;
import org.ecg.refdata.ui.model.impl.RefDataColumnTextDesignModelImpl;

/**
 * Current class extends <code>ColumnBaseTag</code> from Common-Controls. Adds
 * couple of new required features. It contains AJAX control which invokes HTTP
 * request, obtains dictionary data and displays them on client side.
 *
 */
public class RefDataColumnTextTag extends ColumnBaseTag {

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
     * Method stands how many items can be displayed on page
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
     * Sets reference data servlet URL
     */
    public void setRefDataServletURL(String servletURL) {
        getRefDataDesignModel().setRefDataServletURL(servletURL);
    }

    /**
     * @see com.cc.framework.taglib.controls.ColumnBaseTag#doCreateDesignModel()
     */
    @Override
    public ColumnDesignModel doCreateDesignModel() {
        return new RefDataColumnTextDesignModelImpl();
    }

    protected RefDataColumnTextDesignModel getRefDataDesignModel() {
        return (RefDataColumnTextDesignModel) getDesignModel();
    }

}
