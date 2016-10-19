package org.ecg.refdata.ui.painter;

import org.ecg.refdata.servlet.RefDataServlet;
import org.ecg.refdata.ui.model.RefDataCommonTextDesignModel;
import org.apache.ecs.ConcreteElement;
import org.apache.ecs.html.Div;
import org.apache.ecs.html.IMG;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Class creates HTML content which contains specific button with magnifying
 * glass on it.
 * 
 */
class LoupeCreator {

    private static Logger logger = Logger.getLogger(LoupeCreator.class.getName());

    private LoupeCreator() {
    }
    private static String errorCode = "RefDataSupport.showInfoDivImg(RefDataSupport.messageInfo(\"error_taglib\"),this);";

    /**
     * Method generates HTML content ehich contains magnifying glass on it and
     * works like standard HTML burron.
     *
     * @param servletRequest
     *            <code>HttpServletRequest</code> instace
     * @param designModel
     *            reference data text design model
     * @param constantValue
     *            additional value
     */
    static ConcreteElement createButton(ServletContext servletContext,
            HttpServletRequest servletRequest,
            RefDataCommonTextDesignModel designModel, String constantValue) {

        Div div = new Div();

        if (!((constantValue == null || constantValue.length() == 0) && !designModel.getRefDataFormEditable())) {

            String code = LoupeCreator.createJavascriptCode(servletRequest,
                    designModel, constantValue);

            String style = "display: inline; vertical-align: middle;margin-left:3px;";
            IMG img = new IMG();
            img.setOnClick(code);
            img.setSrc("fw/refdata/image/img1.gif");
            img.setStyle(style);

            div.setStyle("cursor:pointer; vertical-align: middle;");
            div.addElement(img);
            Div getRLang = new Div(
                    "\t<script type='text/javascript' language='JavaScript'>" + "RefDataSupport.importsLocalizedTexts('" + LoupeCreator.getRefDataServletURL(servletRequest) + "')</script>");
            div.addElement(getRLang);
        }

        return div;
    }

    /**
     * Method generates javascript code to be used once main button was clicked.
     *
     * @param servletRequest
     *            <code>HttpServletRequest</code> instace
     * @param designModel
     *            reference data text design model
     * @param constantValue
     *            additional value
     */
    private static String createJavascriptCode(
            HttpServletRequest servletRequest,
            RefDataCommonTextDesignModel designModel, String constantValue) {

        int itemsOnPage = designModel.getRefDataItemsOnPage();
        int minTextSearchLength = designModel.getRefDataMinTextSearchLength();
        String searchColumn = designModel.getRefDataSearchColumn();
        String columns = designModel.getRefDataColumns();
        String languageCode = getCurrentLanguageCode(servletRequest);

        StringBuilder sb = new StringBuilder();
        try {
            sb.append("RefDataSupport.process(this, ");
            // base url for reference data servlet
            sb.append("data = { currentUrl:");
            sb.append('\'');
            if (designModel.getRefDataServletURL() != null) {
                sb.append(designModel.getRefDataServletURL());
            } else {
                sb.append(LoupeCreator.getRefDataServletURL(servletRequest));
            }
            sb.append("', ");
            // dictionary id
            sb.append("currentSlw: '");
            sb.append(designModel.getRefDataSlwNo());
            // items start
            sb.append("', ");
            sb.append("metadata: '");
            sb.append(designModel.getMetadata());
            // items start
            sb.append("', ");
            sb.append("currentItemsStart: ");
            sb.append("0, ");
            // items on page
            sb.append("currentItemsOnPage: ");
            sb.append(itemsOnPage);
            // form name
            sb.append(", ");
            sb.append("currentFormName: ");
            if (designModel.getRefDataFormName() != null) {
                sb.append('\'');
                sb.append(designModel.getRefDataFormName());
                sb.append('\'');
            } else {
                sb.append("null");
            }
            sb.append(", ");
            // is editable
            sb.append("currentEditable: '");
            sb.append(designModel.getRefDataFormEditable());
            // constant value (if form is not editable) or property (if form is
            // editable)
            sb.append("', ");
            //is myStyle applied
            sb.append("mystyle: '");
            sb.append(designModel.getMyStyle());
            sb.append("', ");
            //is strictValue applied
            sb.append("strictValue: '");
            sb.append(designModel.getStrictValue());
            sb.append("', ");
            sb.append("currentProperty: '");
            if (constantValue != null) {
                sb.append(constantValue);
            } else {
                sb.append(designModel.getProperty());
            }
            sb.append("', ");
            // minimum text search length
            sb.append("currentMinTextLength: ");
            sb.append(minTextSearchLength);
            sb.append(", ");
            // search column
            sb.append("currentSearchColumn: '");
            sb.append(searchColumn);
            sb.append("', ");
            // search columns
            sb.append("currentColumns: '");
            sb.append(columns);
            sb.append("', ");
            // language code
            sb.append("currentLanguageCode: '");
            sb.append(languageCode);
            sb.append("', ");
            // column mapping
            sb.append("currentMultipleColumnsMapping: '");
            sb.append(formatJavaStriptData(designModel.getRefDataMultipleColumnsMapping()));
            sb.append("'");
            sb.append("})");
        } catch (Exception e) {
            logger.warning("Error while trying create link for Loupe");
            e.printStackTrace();
            return errorCode;
        }
        return sb.toString();
    }

    /**
     * Returns link to {@link RefDataServlet}
     *
     * @param servletRequest
     *            input HttpServletRequest
     * @return URL as string
     */
    private static String getRefDataServletURL(HttpServletRequest servletRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(servletRequest.getScheme());
        sb.append("://");
        sb.append(servletRequest.getServerName());
        sb.append(':');
        sb.append(servletRequest.getServerPort());
        sb.append(servletRequest.getContextPath());
        sb.append('/');
        sb.append(RefDataServlet.getDefaultServletName());
        return sb.toString();
    }

    /**
     * Returns locale of currently loged in user if no locale is set for struts,
     * request (browser) locale will be used)
     *
     * @param request
     * @return locale of current user
     */
    private static String getCurrentLanguageCode(HttpServletRequest request) {
        Object localeObj = request.getSession().getAttribute(
                org.apache.struts.Globals.LOCALE_KEY);
        Locale locale = request.getLocale();
        if (localeObj != null && localeObj instanceof Locale) {
            locale = (Locale) localeObj;
        }
        return locale.toString();
    }

    private static String formatJavaStriptData(String input) {
        String output = input;
        if (input != null) {
            output = output.replaceAll("\r\n", "");
            output = output.replaceAll("\n", "");
            output = output.replaceAll("\r", "");
            output = output.replaceAll("\t", "");
            output = output.replaceAll(" ", "");
            output = output.replaceAll("\"", "\\\\u0022");
            output = output.replaceAll("'", "\\\\u0027");
        }
        return output;
    }
}
