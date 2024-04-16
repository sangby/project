package com.qg.util;

/**
 * 描述：字符串工具集
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class StringUtil {
    /**
     * 为空
     *
     * @param str str
     *
     * @return boolean
     */

    public static boolean isEmpty(String str){
        //如果字符串为null，或者为""，则返回true
        return str == null || "".equals(str.trim());
    }




}
