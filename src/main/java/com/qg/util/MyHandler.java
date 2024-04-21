package com.qg.util;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 描述：处理器接口
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public interface MyHandler<T> {

    /**
     * 处理字符集,返回一个T类型的对象
     *
     * @param ps 预编译的Statement对象
     *
     * @return 返回处理结果
     */
    T handler(PreparedStatement ps) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException, Exception;
}
