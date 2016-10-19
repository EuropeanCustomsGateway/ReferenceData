package org.ecg.referencedata.utils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

public class Show {
    private static Log log = LogFactory.getLog(Show.class);
    private static final int WIDTH = 168;
    private static final int FIELD = 18;
    private static final int VALUE = WIDTH - FIELD - 4; //56;
    private static final int HALF_WIDTH = 39;
    public static void collection(Collection qr, String ... attributes) throws Exception{
        System.out.println("/"+StringUtils.repeat("-", WIDTH)+"\\");
//        System.out.println( "|"+StringUtils.rightPad(" name : " + qr.getName(),WIDTH)+ "|");
        System.out.println( "|"+StringUtils.rightPad(" size : " + qr.size(),WIDTH) + "|");
        System.out.println("*"+StringUtils.repeat(" -", HALF_WIDTH)+"*");
        int idx = 0;
        for (Object item : qr) {
            PropertyDescriptor[] pd = BeanUtils.getPropertyDescriptors(item.getClass()) ;
            for (PropertyDescriptor propertyDescriptor : pd) {
              // propertyDescriptor.get
                if(propertyDescriptor.getReadMethod() != null){
                    if(attributes.length > 0 && !ArrayUtils.contains(attributes,propertyDescriptor.getName())) continue;
                    
                    String name = StringUtils.rightPad(propertyDescriptor.getName(), FIELD);
                    
                    String desc = StringUtils.abbreviate(""+propertyDescriptor.getReadMethod().invoke(item),VALUE);
                    System.out.println(StringUtils.rightPad( "| "+ name+" : " + desc ,WIDTH+1)+ "|");
                }
            }
            idx++;
            if(qr.size() > 1 && qr.size() > idx)
                System.out.println("*"+StringUtils.repeat("~", WIDTH)+"*");
        }
        System.out.println("*"+StringUtils.repeat("-", WIDTH)+"*");
    }
    public static void object(Object item) throws Exception{
        PropertyDescriptor[] pd = BeanUtils.getPropertyDescriptors(item.getClass()) ;
        for (PropertyDescriptor propertyDescriptor : pd) {
            if(propertyDescriptor.getReadMethod() != null){
                String name = StringUtils.rightPad(propertyDescriptor.getName(), FIELD);
                String desc = ""+propertyDescriptor.getReadMethod().invoke(item);
                System.out.println(StringUtils.rightPad( " "+ name+" : " + desc ,WIDTH+1)+ "");
            }
        }
    }
    public static void object(Object item, String... attributes) throws Exception{
        PropertyDescriptor[] pd = BeanUtils.getPropertyDescriptors(item.getClass()) ;
        for (PropertyDescriptor propertyDescriptor : pd) {
            if(attributes.length > 0 && !ArrayUtils.contains(attributes,propertyDescriptor.getName())){
                continue;
            }
            if(propertyDescriptor.getReadMethod() != null){
                String name = StringUtils.rightPad(propertyDescriptor.getName(), FIELD);
                String desc = ""+propertyDescriptor.getReadMethod().invoke(item);
                System.out.println(StringUtils.rightPad( " "+ name+" : " + desc ,WIDTH+1)+ "");
            }
        }
    }
}
