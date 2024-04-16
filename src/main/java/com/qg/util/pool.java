package com.qg.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 描述：连接池接口
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public interface pool {
    //获取连接池中的连接数量
     void getPoolNum();


    /**
     * 创建池
     */
    //创建连接池
     void createPool() throws SQLException;

    /**
     * 获取连接
     *
     * @return 连接
     *///从连接池中获取一个连接实例
    Connection getConnection() throws SQLException;

    /**
     * 返回连接
     *
     * @param conn 连接
     *///放回一个连接到连接池尾部,如果池中连接数达到poolMax则销毁
    void returnConnection(Connection conn) throws SQLException;

    /**
     * 关闭池
     *///关闭连接池
    void closePool() throws SQLException;

}
