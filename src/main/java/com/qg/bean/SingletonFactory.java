package com.qg.bean;

import com.qg.dao.impl.FirmDaoImpl;
import com.qg.dao.impl.OtherDaoImpl;
import com.qg.dao.impl.UserDaoImpl;
import com.qg.service.impl.FirmServiceImpl;
import com.qg.service.impl.UserServiceImpl;
import com.qg.util.impl.PoolUtil;

import javax.print.attribute.standard.MediaSize;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例管理
 *
 * @author Sangby
 */
public class SingletonFactory {


    private static UserServiceImpl userServiceSingleton;

    private static UserDaoImpl userDaoSingleton;
    private static FirmServiceImpl firmServiceSingleton;
    private static FirmDaoImpl firmDaoSingleton;
    private static OtherDaoImpl otherDaoSingleton;

    static{
        try{
            //初始化连接池
            PoolUtil.getPool();
            userServiceSingleton = (UserServiceImpl) getSingleByName(UserServiceImpl.class);

            userDaoSingleton = (UserDaoImpl) getSingleByName(UserDaoImpl.class);

            firmDaoSingleton = (FirmDaoImpl)getSingleByName(FirmDaoImpl.class);

            firmServiceSingleton = (FirmServiceImpl)getSingleByName(FirmServiceImpl.class);

            otherDaoSingleton = (OtherDaoImpl) getSingleByName(OtherDaoImpl.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 按名称构造单例
     *
     * @param clazz 字节码
     *
     * @return 对象
     *
     */
    private static Object getSingleByName(Class clazz) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //反射获取class字节码
//        Class<?> clazz = Class.forName(name);
        //根据字节码中的构造方法获取构造器
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        //设置构造器可访问
        constructor.setAccessible(true);
        //通过构造器创建对象
        return constructor.newInstance();
    }
    public static UserServiceImpl getUserServiceSingleton() {
        return userServiceSingleton;
    }

    public static UserDaoImpl getUserDaoSingleton() {
        return userDaoSingleton;
    }

    public static FirmServiceImpl getFirmServiceSingleton() {
        return firmServiceSingleton;
    }

    public static FirmDaoImpl getFirmDaoSingleton() {
        return firmDaoSingleton;
    }
    public static OtherDaoImpl getOtherDaoSingleton() {
        return otherDaoSingleton;
    }
}
