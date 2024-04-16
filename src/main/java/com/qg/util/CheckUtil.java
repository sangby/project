package com.qg.util;

import com.qg.constant.data;

/**
 * 描述：检查数据工具类
 * 创建人: Sangby
 * 创建时间: 2024/04/16
 */

public class CheckUtil {
    /**
     * 检查名称
     *
     * @param name 名称
     *
     * @return 布尔值
     */

    public static Boolean checkName(String name){

        //不为null长度不为0
        if(name != null || name.length() != 0){
            //对长度进行限制
            if (name.length() < data.MAX_NAME_LENGTH.getValue()){
                return true;
            }
        }
        return false;
    }
}
