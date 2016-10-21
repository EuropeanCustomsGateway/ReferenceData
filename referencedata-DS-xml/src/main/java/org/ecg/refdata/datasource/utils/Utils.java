package org.ecg.refdata.datasource.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Class contains couple of utilities for date conversion and comparison. Most
 * of them are implementations of singleton design pattern.
 *
 */
public class Utils {

    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(Utils.class.getName());

    /**
     * Method converts <code>Date</code> instance into a
     * <code>XMLGregorianCalendar</code> item.
     *
     * @param d input date
     * @return converted output date
     * @throws DatatypeConfigurationException an exception which is throwed if
     * something goes wring while conversion
     */
    public static XMLGregorianCalendar convertXMLDate(Date d)
            throws DatatypeConfigurationException {
        Calendar c = new GregorianCalendar();
        if (d == null) {
            return null;
        } else {
            c.setTime(d);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            int year = c.get(Calendar.YEAR);
            DatatypeFactory df = DatatypeFactory.newInstance();
            XMLGregorianCalendar x = df.newXMLGregorianCalendarDate(year,
                    month, day, c.getTimeZone().getOffset(d.getTime())
                    / (60 * 60 * 1000));
            return x;
        }

    }

    /**
     * Converts <code>XMLGregorianCalendar</code> into <code>Date</code> format.
     *
     * @param xmlGregorianCalendar
     * @return Date converted from xmlGregorianCalendar
     */
    public static Date convertXMLDate(XMLGregorianCalendar xmlGregorianCalendar) {

        if (xmlGregorianCalendar == null) {
            return null;
        } else {
            return xmlGregorianCalendar.toGregorianCalendar().getTime();
        }

    }

    /**
     * Checks if refDataDate is between validFrom and validTo
     *
     * @param validFrom
     * @param refDataDate
     * @param validTo
     * @return boolean if refDataDate is between other dates
     */
    public static boolean compareDatesXML(XMLGregorianCalendar validFrom,
            Date refDataDate, XMLGregorianCalendar validTo) {
        // in version 1.2 it will not exists
        // we are going to remove XML datasource support

        GregorianCalendar validFromGregorian = new GregorianCalendar();
        GregorianCalendar validToGregorian = new GregorianCalendar();
        GregorianCalendar currentDate = new GregorianCalendar();
        boolean afterValidFrom = false;
        boolean beforeValidTo = false;

        if (refDataDate != null) {
            currentDate.setTime(refDataDate);
        }

        if (validFrom != null) {
            validFromGregorian = validFrom.toGregorianCalendar();
        } else {
            afterValidFrom = true;
        }

        if (validTo != null) {
            validToGregorian = validTo.toGregorianCalendar();
        } else {
            beforeValidTo = true;
        }

        if (afterValidFrom && beforeValidTo) {
            return true;
        }
        if (afterValidFrom && !beforeValidTo) {
            if (currentDate.compareTo(validToGregorian) <= 0) {
                return true;
            } else {
                return false;
            }

        }
        if (!afterValidFrom && beforeValidTo) {
            if (currentDate.compareTo(validFromGregorian) >= 0) {
                return true;
            } else {
                return false;
            }
        }
        if (!afterValidFrom && !beforeValidTo) {
            if (currentDate.compareTo(validFromGregorian) >= 0
                    && currentDate.compareTo(validToGregorian) <= 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Method unpacks files from ziped file to given directory
     *
     * @param pathToJarFile path to input jar file
     * @param filesSource - directory in zip containing files
     * @param tmpDir - place to unpack files output temporary directory
     * @throws Exception is thrown if operation cannot be performed
     */
    public static void unzipFiles(String pathToJarFile, String filesSource,
            String tmpDir) throws Exception {
        logger.info("Unzip jar: " + pathToJarFile + " to " + tmpDir);

        byte[] tmpByte = new byte[8092];
        try {
            JarFile jarFile;
            SortedSet<String> dirTreeSet = new TreeSet<String>();
            jarFile = new JarFile(pathToJarFile);
            Enumeration<? extends JarEntry> element = jarFile.entries();
            while (element.hasMoreElements()) {
                JarEntry entry = (JarEntry) element.nextElement();
                String jarName = entry.getName();
                if (jarName.startsWith("/")) {
                    jarName = jarName.substring(1);
                }
                if (jarName.endsWith("/")) {
                    continue;
                }
                int index = jarName.lastIndexOf('/');
                if (index > 0) {
                    String dirName = jarName.substring(0, index);
                    if (dirName.contains(filesSource.substring(1))) {
                        if (!dirTreeSet.contains(tmpDir + dirName)) {
                            File dir = new File(tmpDir + dirName);
                            if (!(dir.exists() && dir.isDirectory())) {
                                dir.mkdirs();
                                logger.finer("Creatings dir: " + dirName);
                                dirTreeSet.add(dirName);
                            }
                        }
                    }
                }

                if (jarName.contains(filesSource.substring(1))) {
                    logger.finer("Creating " + jarName);
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            tmpDir + jarName);
                    InputStream is = jarFile.getInputStream((JarEntry) entry);
                    int stat = 0;
                    while ((stat = is.read(tmpByte)) > 0) {
                        fileOutputStream.write(tmpByte, 0, stat);
                    }
                    is.close();
                    fileOutputStream.close();
                }
            }

        } catch (Exception err) {
            logger.info("IO Error: " + err);
            throw err;
        }
    }
}
