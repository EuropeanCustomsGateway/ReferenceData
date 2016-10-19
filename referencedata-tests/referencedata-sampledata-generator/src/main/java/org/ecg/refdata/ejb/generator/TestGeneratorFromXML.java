package org.ecg.refdata.ejb.generator;

import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB;
import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;
import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.ecg.refdata.datasource.entities.simpleItem.SimpleItemDataType;
import org.ecg.refdata.datasource.entities.simpleItem.SimpleItemItem;
import org.ecg.refdata.datasource.xml.ReferenceData;
import org.ecg.refdata.datasource.xml.ReferenceData.NameAndDescription;
import org.ecg.refdata.datasource.xml.ReferenceData.SimpleItem;
import org.ecg.refdata.datasource.xml.ReferenceData.SimpleItem.CodeDescription;
import org.ecg.refdata.datasource.xml.XMLFileDataSource;
import org.ecg.refdata.exceptions.ReferenceDataSourceInternalException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Class contains incorrect column exception declaration.
 *
 */
public class TestGeneratorFromXML {

    private PersistenceDataSourceSB ejbReferenceData;

    

//            properties.put("java.naming.factory.url.pkgs",
//                    "org.jboss.naming rg.jnp.interfaces");
//            http://127.0.0.1:8080/tomee/ejb
    //tomee ejb address
    // private String hostAddress ="10.173.54.198:1099";
    private String hostAddress = "http://localhost:8080/tomee/ejb";

    private String jnpPersistanceBeanLocation = hostAddress
            + "PersistenceDataSourceSBBean";

    /**
     * @return the ejbReferenceData
     */
    public PersistenceDataSourceSB getEjbReferenceData() {
        return ejbReferenceData;
    }

    public TestGeneratorFromXML() {

        Context ctx;

        String jndiNameOfBean, hostAndIp;

        try {

            int slashIndex = jnpPersistanceBeanLocation.indexOf("/");
            if (slashIndex < 3) {

                throw new Exception(
                        "Cannot find slash \"/\" character at proper place in jndi Reference path: "
                                + jnpPersistanceBeanLocation);

            }

            hostAndIp = jnpPersistanceBeanLocation.substring(0, slashIndex);

            jndiNameOfBean = jnpPersistanceBeanLocation.substring(
                    slashIndex + 1, jnpPersistanceBeanLocation.length());

            Properties properties = new Properties();

            //for tomee
            properties.put("java.naming.factory.initial",
                    "org.apache.openejb.client.RemoteInitialContextFactory");
//            properties.put("java.naming.factory.initial",
//                    "org.jnp.interfaces.NamingContextFactory");

            properties.put("java.naming.factory.url.pkgs",
                    "org.jboss.naming rg.jnp.interfaces");
            properties.put("java.naming.provider.url", "jnp://" + hostAndIp);

            ctx = new InitialContext(properties);

            ejbReferenceData = (PersistenceDataSourceSB) ctx
                    .lookup(jndiNameOfBean);
            System.out.println("Connection to EJB established");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Date convertXMLDate(XMLGregorianCalendar xCalendar) {
        if (xCalendar == null)
            return null;
        else
            return xCalendar.toGregorianCalendar().getTime();
    }

    public void generateDataXML() {

        XMLFileDataSource xmlFileDataSource = new XMLFileDataSource();
        xmlFileDataSource.setUseEmbeddedData(false);
        xmlFileDataSource
                .setFilesSource("src/main/resources/com/skg/refdata/defaultsXml");
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

            simType.setRefDataId(dictionaryId);
            simType.setOrigin("origin");
            simType.setSource("source");
            simType.setLastChange(new Date());
            simType.setItemCodeType("itemCodeType");
            simType.setItemType("itemType");

            // public SimpleItemItem(
            // ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
            // super(referenceDataTypeEntity);
            // }


            for (SimpleItem simpleItem : xmlReferenceData.getSimpleItem()) {
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

                for (CodeDescription codeDescription : simpleItem.getCodeDescription()) {
                    CodeDescriptionItem codeDescriptionItem = new CodeDescriptionItem(
                            codeDescription.getDescription(), codeDescription
                            .getLanguageCode(), simpleItemItem);
                    simpleItemItem.addCodeDescriptionItems(codeDescriptionItem);

                }
                simType.addReferenceDataAbstractItemEntity(simpleItemItem);
                //simpleItemItem.setReferenceDataAbstractDataTypeEntity(simType)
                // ;

            }

            for (NameAndDescription nameAndDescription : xmlReferenceData
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
                ejbReferenceData.persistReferenceDataAbstractDataType(ref);
                System.out.println("Dictionary with id: " + ref.getRefDataId()
                        + " added to database");
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        // Only for generated data
        TestGeneratorFromXML dataGeneratorFromXML = new TestGeneratorFromXML();
        dataGeneratorFromXML.generateDataXML();
        System.out.print("Added to DataSource finished");
        PersistenceDataSourceSB persistenceDataSourceSB = dataGeneratorFromXML
                .getEjbReferenceData();

        System.out.println(persistenceDataSourceSB.getListOfAllIds() + "\n\n");

        // try {
        //
        // Collection<ReferenceDataAbstractItemEntity> list =
        // persistenceDataSourceSB.getReferenceDataAbstractItem("13", "code",
        // "1");
        //
        // for(ReferenceDataAbstractItemEntity item : list){
        // System.out.println( ">> " + item.getId() + "  >>  " +
        // item.getItemType() );
        // }
        //
        // } catch (NoSuchColumnException e) {
        // e.printStackTrace();
        // }

    }

}
