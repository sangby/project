package com.qg.util.impl;

import com.qg.util.MyHandler;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *
 * 描述：实体类处理器
 * 创建人: Sangby
 * 创建时间: 2024/04/12
 */

public class PoHandler<T> implements MyHandler<T> {
    /**
     * 字节码
     */
    private Class<T> clazz;

    public PoHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 处理程序
     *
     * @param ps ps
     *
     * @return t
     */

    @Override
    public T handler(PreparedStatement ps) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            //新建类
            T obj = clazz.newInstance();
            //类的信息
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz,Object.class);
            //类的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            //增强for遍历封装
            for(PropertyDescriptor pd : pds){
                Object object = rs.getObject(pd.getName());
                //调用setter写入
                pd.getWriteMethod().invoke(obj,object);
            }
            rs.close();
            ps.close();

            return obj;
        }
        return null;
    }

}
