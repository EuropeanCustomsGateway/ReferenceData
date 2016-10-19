package org.ecg.refdata.ui.model.impl;

import java.util.Date;

import com.cc.framework.ui.model.imp.TextDesignModelImp;
import org.ecg.refdata.ui.model.RefDataTextDesignModel;

/**
 * Class extends <code>ColumnDesignModelImp</code> from Common-Controls. Adds
 * couple of new required features. It simply change CC
 * <code>ColumnDesignModelImp</code> to SKG
 * <code>RefDataColumnTextDesignModelImpl</code>.
 * 
 */
public class RefDataTextDesignModelImpl extends TextDesignModelImp implements
		RefDataTextDesignModel {

	/**
	 * strict value in query search
	 */
	private String strictValue;
	
	public String getStrictValue() {
		return strictValue;
	}

	public void setStrictValue(String strictValue) {
		this.strictValue = strictValue;
	}
	
	/**
	 * custom style applied to ref query result
	 */
	private String myStyle;
	
	public String getMyStyle() {
		return myStyle;
	}

	public void setMyStyle(String myStyle) {
		this.myStyle = myStyle;
	}

    private String imageOnClick;

    @Override
    public void setImageOnClick(String imageOnClick) {
        this.imageOnClick = imageOnClick;
    }

    @Override
    public String getImageOnClick() {
        return imageOnClick;
    }

    private String javascriptImageOnClick;

    @Override
    public void setJavascriptImageOnClick(String javascriptImageOnClick) {
        this.javascriptImageOnClick = javascriptImageOnClick;
    }

    @Override
    public String getJavascriptImageOnClick() {
        return javascriptImageOnClick;
    }

    private static final long serialVersionUID = 1L;
	/**
	 * @serial Dictionary number
	 */
	private String slwNo;
	/**
	 * @serial Columns which will be shown
	 */
	private String columns;
	/**
	 * @serial Items on page
	 */
	private int itemsOnPage = -1;
	/**
	 * @serial Name of form
	 */
	private String formName;
	/**
	 * @serial Editable form
	 */
	private boolean formEditable = true;
	/**
	 * @serial Date
	 */
	private Date date;
	/**
	 * @serial ForceUpperCase
	 */
	private Boolean forceUpperCase;
	/**
	 * @serial Language code
	 */
	private String languageCode;
	/**
	 * @serial Minimum text search length
	 */
	private int minTextSearchLength = -1;
	/**
	 * @serial Multiple columns mapping
	 */
	private String multipleColumnsMapping;
	/**
	 * @serial Search column
	 */
	private String searchColumn;
	/**
	 * @serial URL to servlet
	 */
	private String servletURL;
    /**
      * @serial flag of requirement
     */
    private boolean customRequired = false;

    private String metadata;

    /**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#getRefDataSlwNo()
	 */
	public String getRefDataSlwNo() {
		return slwNo;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#setRefDataSlwNo(java.lang.String)
	 */
	public void setRefDataSlwNo(String slwNo) {
		this.slwNo = slwNo;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#getRefDataColumns()
	 */
	public String getRefDataColumns() {
		return columns;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#setRefDataColumns(java.lang.String)
	 */
	public void setRefDataColumns(String columns) {
		this.columns = columns;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#setRefDataItemsOnPage(int)
	 */
	public void setRefDataItemsOnPage(int itemsOnPage) {
		this.itemsOnPage = itemsOnPage;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#getRefDataItemsOnPage()
	 */
	public int getRefDataItemsOnPage() {
		return itemsOnPage;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#getRefDataFormName()
	 */
	public String getRefDataFormName() {
		return formName;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#setRefDataFormName(java.lang.String)
	 */
	public void setRefDataFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#getRefDataFormEditable()
	 */
	public boolean getRefDataFormEditable() {
		return formEditable;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataTextDesignModel#setRefDataFormEditable(boolean)
	 */
	public void setRefDataFormEditable(boolean editable) {
		this.formEditable = editable;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#getRefDataDate()
	 */
	public Date getRefDataDate() {
		return date;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setRefDataDate(java.util.Date)
	 */
	public void setRefDataDate(Date date) {
		this.date = date;

	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#getRefDataForceUpperCase()
	 */
	public Boolean getRefDataForceUpperCase() {
		return forceUpperCase;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setRefDataForceUpperCase(Boolean)
	 */
	public void setRefDataForceUpperCase(Boolean forceUpperCase) {
		this.forceUpperCase = forceUpperCase;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#getRefDataLanguageCode()
	 */
	public String getRefDataLanguageCode() {
		return languageCode;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setRefDataLanguageCode(java.lang.String)
	 */
	public void setRefDataLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#getRefDataMinTextSearchLength()
	 */
	public int getRefDataMinTextSearchLength() {
		return minTextSearchLength;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setRefDataMinTextSearchLength(int)
	 */
	public void setRefDataMinTextSearchLength(int minTextSearchLength) {
		this.minTextSearchLength = minTextSearchLength;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#getRefDataMultipleColumnsMapping()
	 */
	public String getRefDataMultipleColumnsMapping() {
		return multipleColumnsMapping;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setRefDataMultipleColumnsMapping(java.lang.String)
	 */
	public void setRefDataMultipleColumnsMapping(String multipleColumnsMapping) {
		this.multipleColumnsMapping = multipleColumnsMapping;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#getRefDataSearchColumn()
	 */
	public String getRefDataSearchColumn() {
		return searchColumn;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setRefDataSearchColumn(java.lang.String)
	 */
	public void setRefDataSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;

	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#getRefDataServletURL()
	 */
	public String getRefDataServletURL() {
		return servletURL;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setRefDataServletURL(java.lang.String)
	 */
	public void setRefDataServletURL(String servletURL) {
		this.servletURL = servletURL;
	}

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#isCustomRequired()
	 */
    public void setCustomRequired(boolean customRequired) {
        this.customRequired = customRequired;
    }

	/**
	 * @see org.ecg.refdata.ui.model.RefDataCommonTextDesignModel#setCustomRequired(boolean)
	 */
    public boolean isCustomRequired() {
        return  customRequired;
    }

    @Override
    public String getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
