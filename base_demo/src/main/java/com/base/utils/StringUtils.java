package com.base.utils;

import org.apache.commons.lang3.ArrayUtils;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Title: StringUtils.java
 * @author zxc
 * @time 2018/4/24 17:39
 */

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String byteToStringUTF8(byte[]bytes){
        return new String (bytes, StandardCharsets.UTF_8);
    }

    public static byte[] stringToByteUTF8(String s){
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public static String getFileSuffix(String fileName){
        int pos=fileName.lastIndexOf(".");
        if(pos>=0){
            if(pos+1!=fileName.length()){
                return fileName.substring(pos + 1).toLowerCase();
            }
        }
        return "";
    }

    public static String getFileNameWithoutSuffix(String fileName){
        int pos=fileName.lastIndexOf(".");
        if(pos>=0){
            if(pos+1!=fileName.length()){
                return fileName.substring(0,pos);
            }
        }
        return "";
    }

    /**
     * 判断某对象是否为空
     *
     * @param object
     * @return
     * @author huangwenhua
     */
    public static boolean isNull(Object object) {
        if (object == null || "null".equals(object) || "".equals(object)) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }


    /**
     *
     * @param ids 传入字符串  1,2,3
     * @return  List<Integer>
     */
    public static List<Integer> toIntList(String ids) {
        return Arrays.asList(ArrayUtils.toObject(Arrays.stream(ids.split(",")).mapToInt(i -> Integer.parseInt(i)).toArray()));
    }

}
