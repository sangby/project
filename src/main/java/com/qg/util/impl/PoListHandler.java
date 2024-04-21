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
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：实体类集成链表处理器
 * 创建人: Sangby
 * 创建时间: 2024/04/12
 */

public class PoListHandler <T> implements MyHandler<List<T>> {
    /**
     * 字节码
     */
    private Class<T> clazz;

    /**
     * 构造方法
     *
     * @param clazz 字节码
     */

    public PoListHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 处理器
     *
     * @param ps 预编译指令
     *
     * @return 列表<t>
     */

    @Override
    public List<T> handler(PreparedStatement ps) throws Exception {
        ResultSet rs = ps.executeQuery();
        //类的信息
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        //属性描述器
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        //列表
        List<T> list = new ArrayList<>();

        while(rs.next()){
            //创建对象
            T obj = clazz.newInstance();

            for(PropertyDescriptor pd : pds){

                //获取属性名
                String name = pd.getName();

                Object value = rs.getObject(name);
                //设置属性值
                pd.getWriteMethod().invoke(obj, value);
            }

            list.add(obj);
        }
        return list;
    }
}
