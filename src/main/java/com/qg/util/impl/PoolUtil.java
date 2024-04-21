package com.qg.util.impl;

import com.qg.util.MyHandler;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 *
 * 描述：连接池工具集
 * 创建人: Sangby
 * 创建时间: 2024/04/12
 */

public abstract class PoolUtil {
    private static String username = "";
    private static String password = "";
    private static String url = "";

    private static Mypool pool;
    // 加载配置文件
    static {


        Properties properties = new Properties();
        InputStream is = PoolUtil.class.getClassLoader().getResourceAsStream("Mypool.properties");
        try {

            properties.load(is);

            username = properties.getProperty("username");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动失败");
            throw new RuntimeException(e);
        } finally {

                try {
                    if (is != null) {
                        is.close();
                        System.out.println("参数读取成功");
                    }else{
                        System.out.println("参数读取失败");
                    }

            } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }finally {
                    //

                }
        }
    }

    /**
     * 获取池
     *
     * @return 描述：
     *///连接池初始化,随便获取连接池对象
    public static Mypool getPool() throws SQLException {
        if(pool == null){
            pool = new Mypool(username,password,url);
            pool.createPool();
        }
        return pool;
    }

    /**
     * 关闭
     *///关闭连接池
    public static void closeMyPool() throws SQLException {
        pool.closePool();
    }


    /**
     * 处理占位符
     *
     * @param sql   sql
     * @param param 参数
     *
     * @return 预编译指令
     *///处理预编译对象
    public static PreparedStatement params(String sql, Object...param) throws SQLException {
        Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        for (int i = 0; i < param.length; i++) {

            ps.setObject(i+1, param[i]);
        }

        pool.returnConnection(connection);
        return ps;
    }

    /**
     * 通用增删改
     *
     * @param sql   sql
     * @param param 参数
     * @param sql   sql
     * @param param 参数
     *
     * @return int
     */ //通用增删改
    public static int update(String sql, Object...param) throws SQLException {
//执行sql,返回改变数据条数
        PreparedStatement ps = params(sql,param);
        int update = ps.executeUpdate();
        ps.close();
        return update;

    }

    /**
     * 早期查询
     *
     * @param sql   sql
     * @param param 参数
     *
     * @return 结果集
     *///通用查询
    public static ResultSet query(String sql, Object...param) throws SQLException {
        PreparedStatement ps = params(sql, param);
        ResultSet rs = ps.executeQuery();
        ps.close();
        return rs;
    }

    /**
     * 真*通用查询
     *
     * @param sql     sql
     * @param handler 处理器
     * @param param   参数
     *
     * @return t
     */

    public static <T> T Tquery(String sql, MyHandler<T> handler, Object...param) throws Exception {
        PreparedStatement pstm = params(sql, param);
        T t = handler.handler(pstm);
        pstm.close();
        return t;
    }



}
