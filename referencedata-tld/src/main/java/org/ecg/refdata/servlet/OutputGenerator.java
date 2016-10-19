package org.ecg.refdata.servlet;

import com.cc.framework.security.StaticPermission;
import com.cc.framework.ui.SimpleImageMap;
import com.cc.framework.ui.control.ControlButton;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ColumnDrilldownDesignModel;
import com.cc.framework.ui.model.ColumnTextDesignModel;
import com.cc.framework.ui.model.ListDesignModel;
import com.cc.framework.ui.model.imp.ColumnDrilldownDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnTextDesignModelImp;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.model.imp.ListDesignModelImp;
import com.cc.framework.ui.painter.ControlPainter;
import com.cc.framework.ui.painter.PainterFactory;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.QueryResult;
import org.ecg.refdata.utils.MessageBoundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.PageContext;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Simple class which contains one method to generate HTML content
 * (commons-controle table) for <code>RefDataServlet</code>.
 *
 */
class OutputGenerator {

    private static MessageBoundle messageBoundle = new MessageBoundle("referencedata");
    private static Logger logger = LoggerFactory.getLogger(OutputGenerator.class.getName());
    private static String[] listOfMethodPrefixes = {"get", "is"};

    /**
     * Simple default constructor
     */
    OutputGenerator() {
    }

    /**
     * Method which generates HTML content with all reference data.
     *
     * @param pageContext  instance of <code>PageContext</code>
     * @param printWriter  <code>PrintWriter</code> instance, we can write here dynamic
     *                     HTML content.
     * @param queryResult  instance of <code>QueryResult</code> class contains all
     *                     required reference data.
     * @param searchColumn
     * @param columns      array of column names
     * @param itemsOnPage  number of items per page
     */
    void generate(PageContext pageContext, OutputStream outputStream,
                  QueryResult queryResult, String searchColumn, String[] columns,
                  int itemsOnPage, boolean displayOnly) throws IOException {

        SimpleListControl listControl = new SimpleListControl();
        listControl.setDataModel(new RefDataDataModel(queryResult));
        listControl.setCurrentPage(queryResult.getItemsStart() / itemsOnPage);

        boolean paginationVisible = queryResult.getTotalCount() > itemsOnPage;

        ListDesignModel designModel = new ListDesignModelImp();
        designModel.setWidth("100%");
        designModel.setPageButtons(5);
        designModel.setFormElement(true);
        designModel.setRowCount(itemsOnPage);
        if (queryResult.getDictionaryId().startsWith("traderDict") || queryResult.getDictionaryId().startsWith("grnDataDict") ||
                queryResult.getDictionaryId().startsWith("materialsDict")) {
            designModel.setTitle(queryResult.getName() + " ");
        } else {
            designModel.setTitle(queryResult.getName() + " (" + queryResult.getDictionaryId() + ")");
        }
        if (!paginationVisible) {
            // insert "create" button which will be later chnaged to "close"
            designModel.setButtonPermission(ControlButton.CREATE,
                    StaticPermission.GRANTED);
        }

        for (String column : columns) {
            if (!displayOnly) {
                ColumnTextDesignModel columnModel = new ColumnTextDesignModelImp();
                columnModel.setTitle(localizeColumnTitle(column, queryResult));
                if (column.equalsIgnoreCase("sendECSResponseByEmail")) { //checkbox imagemap
                    SimpleImageMap im = new SimpleImageMap();
                    im.addImage("true", new ImageModelImp("app/img/checked.png"));
                    im.addImage("false", new ImageModelImp("app/img/checkbox.png"));
                    columnModel.setImageMap(im);
                    columnModel.setImageProperty(column);
                } else {
                    columnModel.setProperty(column);
                }
                designModel.addColumn(columnModel);
            } else {
                ColumnDrilldownDesignModel columnModel = new ColumnDrilldownDesignModelImp();
                columnModel.setTitle(localizeColumnTitle(column, queryResult));
                if (column.equalsIgnoreCase("sendECSResponseByEmail")) { //checkbox imagemap
                    SimpleImageMap im = new SimpleImageMap();
                    im.addImage("true", new ImageModelImp("app/img/checked.png"));
                    im.addImage("false", new ImageModelImp("app/img/checkbox.png"));
                    columnModel.setImageMap(im);
                    columnModel.setImageProperty(column);
                } else {
                    columnModel.setProperty(column);
                }
                designModel.addColumn(columnModel);
            }
        }

        listControl.setDesignModel(designModel);

        ControlPainter listPainter = PainterFactory.createPainter(pageContext,
                listControl);

        CharArrayWriter bufferStream = new CharArrayWriter();

        PrintWriter bufferWriter = new PrintWriter(bufferStream);

        listPainter.paint(bufferWriter);

        StringBuilder sb = OutputGenerator.generateJavaScriptDataField(
                queryResult, searchColumn);

        String temps = sb.toString();

        bufferWriter.append(temps);

        bufferWriter.close();
        bufferStream.close();

        String str = new String(bufferStream.toCharArray());
        // replace last page index (form CC -1 index to calculated)
        if (queryResult.getTotalCount() != null &&
                queryResult.getTotalCount().intValue() > 0 &&
                itemsOnPage > 0) {
            int lastPageIdx = queryResult.getTotalCount().intValue() / itemsOnPage;
            if (queryResult.getTotalCount().intValue() % itemsOnPage == 0) {
                lastPageIdx--;
            }
            if (lastPageIdx < 0) {
                lastPageIdx = 0;
            }
            str = str.replace("CCUtility.crtCtrla(this, \"null=Page=-1",
                    "RefDataSupport.changePage(\"" + lastPageIdx);
        }
        // selecting a page: CCUtility.crtCtrla(this, "null=Page=...
        str = str.replace("CCUtility.crtCtrla(this, \"null=Page=",
                "RefDataSupport.changePage(\"");
        // selecting a row: CCUtility.crtCtrla(this, "null=Drilldown=...
        str = str.replace("CCUtility.crtCtrla(this, \"null=Drilldown=",
                "RefDataSupport.selectItem(\"");

        int index1 = str.indexOf(BTN_NEXT);
        int index2 = str.indexOf(BTN_LAST);
        if (paginationVisible && index1 >= 0 && index2 >= 0) {
            // inject "close" button right after "last page" button
            index1 += BTN_NEXT.length();
            index2 += BTN_LAST.length();
            str = str.substring(0, index2) + str.substring(index1).replace("changePage", "closeNewDiv").replace(
                    BTN_LAST, BTN_CLOSE);
            if (str.indexOf(BTN_LAST_TITLE) > 0) { //"goto last page"
                str = str.replace(BTN_LAST_TITLE, BTN_CLOSE_TITLE);
            } else if (str.indexOf(BTN_LAST_TITLE_BIG) > 0) { //replace second "Goto last page"
                int fistLastTitleBig = str.indexOf(BTN_LAST_TITLE_BIG) + BTN_LAST_TITLE_BIG.length();
                if (str.indexOf(BTN_LAST_TITLE_BIG, fistLastTitleBig) > 0) {
                    str = str.subSequence(0, fistLastTitleBig) + str.substring(fistLastTitleBig).replace(BTN_LAST_TITLE_BIG, BTN_CLOSE_TITLE);
                }
            }
        } else {
            // try to change fake "create" button into "close" one
            str = str.replace("CCUtility.crtCtrla(this, \"null=Create",
                    "RefDataSupport.closeNewDiv(\"").replace(BTN_CREATE,
                    BTN_CLOSE).replace(BTN_CREATE_TITLE, BTN_CLOSE_TITLE);
            // add close button [X] to the last page of dictionary
            str = str.replace("RefDataSupport.closeNewDiv(\"\", null, null);' title='close' class='feact'></span>"
                    , "RefDataSupport.closeNewDiv(\"\", null, null);' title='close' class='feact'><img width='15' src='fw/def2/image/buttons/" + BTN_CLOSE + "' height='15' border='0' align='absmiddle' id='btn' vspace='0'></img></span>");
        }

        outputStream.write(str.getBytes("UTF-8"));

    }

    private static final String BTN_NEXT = "btnNext1.gif";
    private static final String BTN_LAST = "btnLast1.gif";
    private static final String BTN_LAST_TITLE = "goto last page";
    private static final String BTN_LAST_TITLE_BIG = "Goto last page";
    private static final String BTN_CREATE = "btnCreate1.gif";
    private static final String BTN_CREATE_TITLE = "create new item";
    private static final String BTN_CLOSE = "btnClose1.gif";
    private static final String BTN_CLOSE_TITLE = "close";

    /**
     * Column title should contain native text
     *
     * @param columnName  column name for which we want to find localized name
     * @param queryResult input query result
     * @return localized name
     */
    private String localizeColumnTitle(String columnName,
                                       QueryResult queryResult) {
        String langCode = queryResult.getLanguageCode();
        Locale locale = new Locale(langCode.toLowerCase(), langCode.toUpperCase());
        //w kolumnie name jest adres urzedu - wiec w nalgowke chce miec napis 'adres' zamiast 'nazwa'
        if("TC_TP_CUSTOMS".equals(queryResult.getDictionaryId()) && "name".equals(columnName)){
            return messageBoundle.getString(locale, "address");
            //analogicznie jak wyzej, ale dla kodu taryfowego tu mam kody jednostek uzupelniajacych
        }else if("TC_TP_TNVED".equals(queryResult.getDictionaryId()) && "descriptionEN".equals(columnName)){
            return messageBoundle.getString(locale, "suppUnit");
        }
        return messageBoundle.getString(locale, columnName);
    }

    /**
     * Generate data to fill field
     *
     * @param queryResult  input query result
     * @param searchColumn search column - if 'null' get default search column
     * @return generated string with mapping fields
     */
    private static StringBuilder generateJavaScriptDataField(
            QueryResult queryResult, String searchColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id='RefDataArray' " + " style=\"visibility:hidden; position: absolute;\">");
        if (searchColumn == null || searchColumn.compareTo("null") == 0) {
            sb.append("__refDataCCT__DATA.currentSearchColumn='" + queryResult.getSearchColumn() + "';");
        }
        sb.append("items_array= new Array();");
        int count = 0;

        for (DictionaryItem dictItem : queryResult.getItems()) {
            Class<? extends DictionaryItem> cl = dictItem.getClass();
            Method[] methods = cl.getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (!isMethodOK(method) || methodName.equalsIgnoreCase("getInvalidForUE") || methodName.equalsIgnoreCase("getDictionaryItem") || methodName.equalsIgnoreCase("getInvalidForCountries")) {
                    continue;
                }
                String fieldName = extractFieldNameFromMethodName(methodName);
                fieldName = fieldName != null ? fieldName.toLowerCase() : null;
                try {
                    Object[] parameters = new Object[]{};
                    Object object = method.invoke(dictItem, parameters);
                    sb.append("items_array[\"" + fieldName + count + "\"]=\"" + formatJavaStriptData(object != null ? object.toString() : "") + "\";");
                } catch (Exception e) {
                    logger.warn("There is a problem with invoke method " + methodName + e.getMessage());
                }
            }
            count++;
        }
        sb.append("</div>");
        return sb;
    }

    private static String formatJavaStriptData(String input) {
        String output = input;
        output = output.replaceAll("\r\n", " ");
        output = output.replaceAll("\n", " ");
        output = output.replaceAll("\r", " ");
        output = output.replaceAll("\"", "\\\\u0022");
        output = output.replaceAll("'", "\\\\u0027");
        output = output.replaceAll("<", "\\\\u003C");
        output = output.replaceAll(">", "\\\\u003E");
        output = output.replaceAll("/", "\\\\u002F");
        output = output.replaceAll("&", "\\\\u0026");
        logger.debug("output: " + output);
        return output;
    }

    /**
     * Indicate if given method (by name) should be used for getting data
     *
     * @param methodName name of method
     * @return <tt>true</tt> if method should be used in other case <tt>false</tt>
     */
    private static boolean isMethodOK(Method method) {
        // only use get and is method
        boolean methodPrefix = false;
        for (String prefix : listOfMethodPrefixes) {
            if (method.getName().toLowerCase().startsWith(prefix)) {
                methodPrefix = true;
                break;
            }
        }

        // ignore std java methods
        if (methodPrefix &&
                !java.lang.Class.class.equals(method.getDeclaringClass())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method return field name for given method name
     *
     * @param methodName method name
     * @return field name for given method name
     */
    private static String extractFieldNameFromMethodName(String methodName) {
        String fieldName = methodName;
        for (String prefix : listOfMethodPrefixes) {
            if (methodName.toLowerCase().startsWith(prefix)) {
                fieldName = methodName.substring(prefix.length());
                break;
            }
        }

        return fieldName;
    }
}