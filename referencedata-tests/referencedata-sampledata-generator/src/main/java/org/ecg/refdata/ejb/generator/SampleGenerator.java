package org.ecg.refdata.ejb.generator;

import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;
import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.ecg.refdata.datasource.entities.simpleItem.SimpleItemDataType;
import org.ecg.refdata.datasource.entities.simpleItem.SimpleItemItem;
import org.ecg.refdata.datasource.xml.ReferenceData;
import org.ecg.refdata.datasource.xml.XMLFileDataSource;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;

/**
 * Testing servlet for generating sample data for EJB persistence
 *
 */
public class SampleGenerator extends javax.servlet.http.HttpServlet implements
        javax.servlet.Servlet {

    private static final long serialVersionUID = 1L;

    // private static Logger logger = Logger.getLogger(SampleGenerator.class
    // .getName());
    private final static String XML_SUFFIX = "xml";

    private String filesSource = "C:\\workspaceEE\\skg-referencedata\\skg-referencedata-tests\\skg-referencedata-sampledata-generator\\src\\main\\resources\\com\\skg\\refdata\\defaultsXml";

    PersistenceDataSourceSB persistenceBean;

    public SampleGenerator() {
        super();
    }

    /*
      * (non-Java-doc)
      *
      * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
      * HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    /*
      * (non-Java-doc)
      *
      * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
      * HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("</HEAD>");

        out.println("<BODY>");

        String requiredIp = request.getParameter("ip");

        String jnpName = request.getParameter("jnp");

        String requiredInterface = request.getParameter("group1");
        
        filesSource = request.getParameter("src");

        out.println("<H3>requiredIp</H3>" + requiredIp);
        out.println("<H3>jnpName</H3>" + jnpName);
        out.println("<H3>RequiredInterface</H3>" + requiredInterface);
        out.println("<H3>Dictionaries source</H3>" + filesSource);

        out.println("Home directory: " + System.getProperty("user.dir"));

        Context ctx;

        try {

            Properties properties = new Properties();

//            properties.put("java.naming.factory.initial",
//                    "org.jnp.interfaces.NamingContextFactory");
//            properties.put("java.naming.factory.url.pkgs",
//                    "org.jboss.naming rg.jnp.interfaces");
            properties.put("java.naming.factory.initial",
                    "org.apache.openejb.client.RemoteInitialContextFactory");
            properties.put("java.naming.provider.url", "" + requiredIp);

            ctx = new InitialContext(properties);

            out.println("Getting ejbPersistence instance<BR>");

            persistenceBean = (PersistenceDataSourceSB) ctx.lookup(jnpName);// + "/"+ requiredInterface);

            out.println("Reference to EJBFacade Datasource successfully obtained<BR>");

            out.println("<H4>List of input xml files</H4><BR>");

           

            out.println("<H4>Persisting data:</H4><BR>");

           generateDataXML(filesSource, persistenceBean);

        } catch (Exception e) {

            e.printStackTrace();

        }

        out.println("</BODY>");
        out.println("</HTML>");

        out.close();

    }

    public void generateDataXML(String path, PersistenceDataSourceSB ejbInterface) {

        XMLFileDataSource xmlFileDataSource = new XMLFileDataSource();
        xmlFileDataSource.setUseEmbeddedData(false);
        xmlFileDataSource
                .setFilesSource(path);
        try {
            xmlFileDataSource.initializeReferenceDataMap();
        } catch (ReferenceDataSourceInternalException e) {
            e.printStackTrace();
        }

        Map<String, ReferenceData> referenceDataMap = xmlFileDataSource
                .getMapReferenceData();

        for (Map.Entry<String, ReferenceData> xmlReferenceDataEntry : referenceDataMap
                .entrySet()) {

            ReferenceData xmlReferenceData = xmlReferenceDataEntry
                    .getValue();
            String dictionaryId = xmlReferenceDataEntry.getKey();

            SimpleItemDataType simType = new SimpleItemDataType();

            System.out.println("Id slownika: " + dictionaryId);
            simType.setRefDataId(dictionaryId);
            simType.setOrigin("PL");
            simType.setSource("PL");
            simType.setLastChange(new Date());
            simType.setItemCodeType("PL110");
            simType.setItemType("itemType");

            // public SimpleItemItem(
            // ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
            // super(referenceDataTypeEntity);
            // }


            for (ReferenceData.SimpleItem simpleItem : xmlReferenceData.getSimpleItem()) {
                SimpleItemItem simpleItemItem = new SimpleItemItem(simType);
                simpleItemItem.setCode(simpleItem.getCode());
                // simpleItemItem.setNational(simpleItem.getNational());
                // prefer to use our date

                SimpleDateFormat sdf = new SimpleDateFormat();
                sdf.applyPattern("MM/dd/yyyy");

                Date validFrom = new Date();
                Date validTo = new Date();

                try {
                    validFrom = sdf.parse("17/10/2007");
                    validTo = sdf.parse("17/10/2035");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                simpleItemItem.setValidFrom(validFrom);
                simpleItemItem.setValidTo(validTo);

                for (ReferenceData.SimpleItem.CodeDescription codeDescription : simpleItem.getCodeDescription()) {
                    CodeDescriptionItem codeDescriptionItem = new CodeDescriptionItem(
                            codeDescription.getDescription(), codeDescription
                            .getLanguageCode(), simpleItemItem);
                    simpleItemItem.addCodeDescriptionItems(codeDescriptionItem);

                }
                simType.addReferenceDataAbstractItemEntity(simpleItemItem);
                //simpleItemItem.setReferenceDataAbstractDataTypeEntity(simType)
                // ;

            }

            for (ReferenceData.NameAndDescription nameAndDescription : xmlReferenceData
                    .getNameAndDescription()) {
                NameAndDescriptionItem nameAndDescriptionItem = new NameAndDescriptionItem(
                        nameAndDescription.getName(), nameAndDescription
                        .getDescription(), nameAndDescription
                        .getLanguageCode(), simType);
                simType.addNameAndDescriptionItem(nameAndDescriptionItem);

            }
            ReferenceDataAbstractDataType ref = simType;
            ref.setVersionNumber(0);
            try {
                ejbInterface.persistReferenceDataAbstractDataType(ref);
                System.out.println("Dictionary with id: " + ref.getRefDataId()
                        + " added to database");
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }
}
