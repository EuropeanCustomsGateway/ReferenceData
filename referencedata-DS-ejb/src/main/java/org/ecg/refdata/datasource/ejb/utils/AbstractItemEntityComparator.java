package org.ecg.refdata.datasource.ejb.utils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractItemEntity;

/**
 * Comparator class for <code>ReferenceDataAbstractItemEntity</code> items.
 *
 */
public class AbstractItemEntityComparator implements
        Comparator<ReferenceDataAbstractItemEntity>, Serializable {

    private static final long serialVersionUID = 4270761289464977834L;
    /**
     * @serial By passing mode parameter
     */
    private Boolean bypassingMode = false;
    /**
     * @serial Required getter
     */
    private Method requiredGetter = null;

    /**
     * Main constructor of comparator takes two arguments.
     *
     * @param nameOfRequiredField name of field in instance.
     * @param sampleItem item of required comparable item (in constructor system
     * is reading available fields).
     */
    public AbstractItemEntityComparator(String nameOfRequiredField,
            ReferenceDataAbstractItemEntity sampleItem) {

        super();

        /*
		 * If there's no required column or column type is not supported, main
		 * compare method will always return 0 (bypassingMode - true).
         */
        Method[] methods = sampleItem.getClass().getMethods();

        // get (is as boolean will not be supported)
        for (Method element : methods) {

            if (element.getName().compareTo(
                    "get" + firstCharToUpperCase(nameOfRequiredField)) == 0) {
                requiredGetter = element;
                break;
            }

        }

        if (requiredGetter == null) {
            this.bypassingMode = true;
            return;
        }

        Object[] input = {}; // there are no input parameters for getters

        Object output = null;

        try {

            output = (Object) requiredGetter.invoke(sampleItem, input);

        } catch (Exception e) {
            // in case of any trouble
            this.bypassingMode = true;
            return;
        }

        if (!(output instanceof Comparable)) {
            this.bypassingMode = true;
            return;
        }

    }

    /**
     * Main comparer
     *
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(
            ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity1,
            ReferenceDataAbstractItemEntity referenceDataAbstractItemEntity2) {

        if (this.bypassingMode) {

            return 0;

        } else {

            /*
			 * If only return object implements Comparable comparator will
			 * compare it, otherwise will leave it as at the beginning
             */
            Object[] input = new Object[]{}; // there are no input parameters
            // for getters

            try {

                Object output1 = requiredGetter.invoke(
                        referenceDataAbstractItemEntity1, input);

                Object output2 = requiredGetter.invoke(
                        referenceDataAbstractItemEntity2, input);

                // integer, date, string implements comparable so we can use it
                if (output1 instanceof Date) {
                    Date date1 = (Date) output1;
                    Date date2 = (Date) output2;
                    return (int) date1.compareTo(date2);
                } else if (output1 instanceof String) {
                    String string1 = (String) output1;
                    String string2 = (String) output2;
                    return (int) string1.compareTo(string2);
                } else if (output1 instanceof Integer) {
                    Integer integer1 = (Integer) output1;
                    Integer integer2 = (Integer) output2;
                    return (int) integer1.compareTo(integer2);
                }
                return 0;

            } catch (Exception e) {
                // there's no need to notice about troubles, exceptions could be
                // permanent
                return 0;
            }

        }

    }

    /**
     * Method changes first character to upper case
     *
     * @param input input character
     * @return returns input value if it's null
     */
    private static String firstCharToUpperCase(String input) {

        if (input.length() > 0) {

            return Character.toUpperCase(input.charAt(0))
                    + input.substring(1, input.length());

        } else {

            return input;

        }

    }

}
