package com.qg.util.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;


/**
 * 描述：连接池实体类
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class Mypool implements com.qg.util.pool {
    //初始化默认池大小为10
    private static final int poolInitNum = 10;
    //用双向链表作容器
    private LinkedList<Connection> pool = null;
    private  static  final int poolMax = 20;
    private  final String username;
    private  final String password;
    private final String url;

    Mypool(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    @Override
    public void getPoolNum() {
        System.out.println("连接池:"+pool+"内连接数量:"+pool.size());
    }

    @Override
    public void createPool() throws SQLException {
        //保证连接池不存在
        if (pool!=null) return;
        //创建
        pool = new LinkedList<>();
        initConnection();
    }

    /**
     * init连接
     *///私有了两个方法
    //初始化
    private void initConnection() throws SQLException {
        for (int i = 0; i < poolInitNum; i++) {
            pool.add(newConnection());
        }
    }

    /**
     * 新连接
     *
     * @return 联系
     *///创建新连接
    private Connection newConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    //获取连接

    @Override
    public synchronized Connection  getConnection() throws SQLException {
        if(pool.size()>0){
            return pool.removeFirst();
        }else {
            return newConnection();
        }

    }
//放回连接
    @Override
    public void returnConnection(Connection conn) throws SQLException {
        if(pool!=null){
            if(pool.size()<poolMax){
                pool.add(conn);
            }else {
                conn.close();
            }
        }else {
            conn.close();
        }
    }

    //关闭
    @Override
    public void closePool() throws SQLException {
        for(int i = pool.size()-1; i>= 0;i--){
            Connection connection = pool.remove(i);
            connection.close();
        }
        pool = null;
    }
}
