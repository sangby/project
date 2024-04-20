package com.qg.dao;

import com.qg.po.User;

import java.sql.SQLException;

/**
 * 描述：用户数据访问接口
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public interface UserDao {
    /**
     * 用用户名和密码查询用户
     *
     * @param userName 用户名
     * @param passWord 密码
     *
     * @return 用户
     */
    User selectByNameAndPassword(String userName,String passWord);


    /**
     * 按名称选择
     *
     * @param userName 用户名
     *
     * @return 用户
     */
    User selectByName(String userName);

    /**
     * 注册时输入信息
     *
     * @param userName 用户名称
     * @param password 密码
     *
     * @return int
     */

    int insertNewOne(String userName,String password) throws SQLException;

}
