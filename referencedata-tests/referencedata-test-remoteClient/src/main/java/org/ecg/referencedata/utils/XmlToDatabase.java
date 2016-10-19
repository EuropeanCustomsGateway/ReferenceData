package org.ecg.referencedata.utils;

import org.ecg.refdata.datasource.xml.ReferenceData;
import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSB;
import org.ecg.refdata.datasource.ejb.PersistenceDataSourceSBBean;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.ecg.refdata.datasource.xml.XMLFileDataSource;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import org.apache.commons.lang.StringUtils;
import org.jboss.util.file.FileSuffixFilter;

/**
 * Insert data from xml to PersistenceDataSourceSB. 
 * 
 */
public class XmlToDatabase {

    private boolean real = false;
    private PersistenceDataSourceSB ejbReferenceData;
    
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    /**
     * @return the ejbReferenceData
     */
    public PersistenceDataSourceSB getEjbReferenceData() {
        return ejbReferenceData;
    }



    /**
     * constructs XmlToDatabase object
     * @param jnpPersistanceBeanLocation address of service with reference data PersistenceDataSourceSB
     * @param real if really should add defaul = false => not adding to db
     */
    public XmlToDatabase(String persistanceBeanLocation, boolean real) {
        this.real = real;
        if (persistanceBeanLocation.startsWith("jdbc:")) {
            try {
                HashMap map = new HashMap();
                String jdbc = StringUtils.substringBeforeLast(persistanceBeanLocation, "/");
                String[] userpass = StringUtils.substringAfterLast(persistanceBeanLocation, "/").split(":");
                
                System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
                map.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
                map.put("hibernate.connection.url", jdbc);
                map.put("hibernate.connection.username", userpass[0]);
                map.put("hibernate.connection.password", userpass[1]);

                emf = Persistence.createEntityManagerFactory("EjbDataSourcePersistenceUnit", map);
                em = emf.createEntityManager();

                System.out.println("using jdbc connection :" + persistanceBeanLocation);

                PersistenceDataSourceSBBean instance = new PersistenceDataSourceSBBean();
                instance.setEntityManager(em);
                em.setFlushMode(FlushModeType.AUTO);
                ejbReferenceData = instance;
                System.out.println("----------------------");
                System.out.println("ejbReferenceDataejbReferenceData = "  +ejbReferenceData);
                
                System.out.println("----------------------");
            } catch (Exception ex) {
               ex.printStackTrace();
            }


        } else {
            System.out.println("using jnp connection: " + persistanceBeanLocation);
            ejbReferenceData = new JNPConnection<PersistenceDataSourceSB>(persistanceBeanLocation).getEjbBean();
        }
    }

    /**
     * constructs XmlToDatabase object
     * @param persistanceBeanLocation address of service with reference data PersistenceDataSourceSB
     * @param real if really should add defaul = false => not adding to db
     */
    public XmlToDatabase(String persistanceBeanLocation) {
        this(persistanceBeanLocation, false);
    }

    public String fillDatabase(File refdateSinglefile) throws Exception {
//        XMLFileDataSource xmlFileDataSource = new XMLFileDataSource();
        String refId = "??unknown??";
        ReferenceDataAbstractDataType referenceDataAbstractDataType = null;
        try {
            if(em != null)  em.getTransaction().begin();
            Integer version = 0;
            ReferenceData xmlReferenceData = XMLFileDataSource.getReferenceDataFromFile(refdateSinglefile);
            referenceDataAbstractDataType = XmlToEntities.create(xmlReferenceData);

            refId = referenceDataAbstractDataType.getRefDataId();
            System.out.println("Processing file : " + refdateSinglefile + "...");

            if (ejbReferenceData.getListOfAllIds().contains(refId)) {
                System.out.println("Dictionary with id '" + refId + "' exists.. removing it before add");

                if (real) {
                    if(em != null) {
                        em.flush();
                    }
//                    ReferenceDataAbstractDataType rdadt = ejbReferenceData.getReferenceDataAbstractDataType(refId);
                   BigDecimal versionb =  ((BigDecimal) em.createNativeQuery(
                            "SELECT VERSIONNUMBER FROM "
                            + "REF_TYPE_NAME_AND_DESC_MAPP "
                            + "WHERE REF_DATA_ID = :refDataId").setParameter("refDataId", refId).getSingleResult());
                    if(versionb != null ) version = versionb.intValue();
                    if(version == null) version = 0;
                    ejbReferenceData.removeDataTypeEntityByRefId(refId);
                    if(em != null) {
                        em.flush();
                    }
                }
//                ejbReferenceData.
            }
            System.out.println("Adding dictionary with id: " + refId + "... old version = " + version);
            if (real){
                referenceDataAbstractDataType.setVersionNumber(version.intValue()+1);
                ejbReferenceData.persistReferenceDataAbstractDataType(referenceDataAbstractDataType);
            }
            if(em != null) em.getTransaction().commit();
             
            return refId;
        } catch (Exception x) {
            System.err.println("Dictionary with from file " + refdateSinglefile +
                    " with id '" + refId + "' caused a problem");
//                if(referenceDataAbstractDataType != null)
//                    Show.collection(referenceDataAbstractDataType.getItems());
            x.printStackTrace();
        }
        return null;
    }

    /**
     * fills database with reference data
     * @param directory place where xml reference data files are stored
     * @throws java.lang.Exception
     * @returns added dict id's
     */
    public Set<String> fillDatabase(String directory) throws Exception {
        Set<String> alladded = new HashSet<String>();
        File[] xsds = new File(directory).listFiles(new FileSuffixFilter("xml"));
        for (File file : xsds) {
//            if(!file.toString().matches(".*(CUN|LocalClearance)\\.xml"))
//                continue;
            alladded.add(fillDatabase(file));
        }
//		XMLFileDataSource xmlFileDataSource = new XMLFileDataSource();
//		xmlFileDataSource.setUseEmbeddedData(false);
//		xmlFileDataSource.setFilesSource(directory);
//		try {
//			xmlFileDataSource.initializeReferenceDataMap();
//		} catch (ReferenceDataSourceInternalException e) {
//			e.printStackTrace();
//		}
//
//        // adding with orginal name 8I / 8E
//		Map<String, ReferenceData> referenceDataMap = xmlFileDataSource.getMapReferenceData();
//
//		for (Map.Entry<String, ReferenceData> xmlReferenceDataEntry : referenceDataMap.entrySet()) {
//			ReferenceData xmlReferenceData = xmlReferenceDataEntry.getValue();
//            ReferenceDataAbstractDataType referenceDataAbstractDataType = XmlToEntities.create(xmlReferenceData);
//            alladded.add(""+referenceDataAbstractDataType.getRefDataId());
//			try {
//                System.out.println("Adding dictionary with id: " +  referenceDataAbstractDataType.getRefDataId() + "...");
//                long ltime = System.currentTimeMillis();
//			 if(real)
//                 ejbReferenceData.persistReferenceDataAbstractDataType(referenceDataAbstractDataType);
//				long ldt = (System.currentTimeMillis() - ltime) / 1000;
//
//                System.out.println("Dictionary with id: " +  referenceDataAbstractDataType.getRefDataId() + " added to database in " + ldt + " seconds.");
//
//			} catch (Exception x) {
//                System.err.println("Dictionary with id: " +  referenceDataAbstractDataType.getRefDataId() + " caused a problem while adding to db");
//                Show.collection(referenceDataAbstractDataType.getItems());
//				x.printStackTrace();
//			}
//		}
//        // Adding with digit only 8E -> 8 ...
//        System.out.println("---------------------------------------------------------------");
//        Pattern p = Pattern.compile("([0-9]+)([IE]|LT)");
//        HashSet<String> added = new HashSet<String>();
//
//		for (Map.Entry<String, ReferenceData> xmlReferenceDataEntry : referenceDataMap.entrySet()) {
//			String id = xmlReferenceDataEntry.getKey();
//            Matcher m = p.matcher(id);
//            if(m.matches() ){
//                Integer nr = Integer.parseInt(m.group(1));
//                String EI = m.group(2);
//                if(added.contains(id)){
//                    System.out.println("simple Dictionary with id: '" + id + "' was  added from another dictrinary");
//                    continue;
//                }
//                if(added.contains(""+nr+"E")){
//                    System.out.println("simple Dictionary with id: '" + id + "' was  added from another dictrinary");
//                    continue;
//                }
//
//                if(EI.equals("E") || ((EI.equals("I") || EI.equals("LT") )&& !referenceDataMap.containsKey(""+nr+"E"))){
//                    added.add(""+nr+"E");
//                    alladded.add(""+nr);
//
//                    ReferenceData xmlReferenceData = xmlReferenceDataEntry.getValue();
//                    ReferenceDataAbstractDataType referenceDataAbstractDataType = XmlToEntities.create(xmlReferenceData);
//
//                    referenceDataAbstractDataType.setRefDataId(""+nr);
//                    try {
//                        System.out.println("Adding dictionary with id: " +  id + "...");
//     				 if(real)
//                        ejbReferenceData.persistReferenceDataAbstractDataType(referenceDataAbstractDataType);
//                        System.out.println("Dictionary with id: '" +  id + "' added to database as '" + referenceDataAbstractDataType.getRefDataId() +"'");
//
//                    } catch (Exception x) {
//                        System.err.println("Dictionary with id: '" +  id + "' not added to database as '" + referenceDataAbstractDataType.getRefDataId() +"' caused a problem while adding to db");
//                        Show.collection(referenceDataAbstractDataType.getItems());
//                        x.printStackTrace();
//                    }
//                }else{
//                    System.out.println("simple Dictionary with id: '" + id + "' will be added from another dictrinary");
//                }
//            }else{
//                System.out.println("Dictionary with id: '" + id + "' can not be added without suffix");
//            }
//		}
        return alladded;
    }
}
