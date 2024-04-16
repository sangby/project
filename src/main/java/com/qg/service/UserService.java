package com.qg.service;

import com.qg.constant.Result;
import com.qg.po.User;

import java.sql.SQLException;

/**
 * 描述：用户服务
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public interface UserService {


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    Result<User> login(String username, String password) throws SQLException;

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     *
     * @return 结果<用户>
     */
    Result<User> select(String userName);


    /**
     * 注册
     *
     * @param userName 用户名
     * @param passWord 密码
     *
     * @return 结果<用户>
     */

    Result<User> register(String userName, String passWord) throws SQLException;

    /**
     * 登录初始化,传入用户名,返回用户所有信息
     *
     * @param userName 用户名
     *
     * @return 结果<用户>
     */

    Result<User> loginInit(String userName);

    /**
     * 更新个人信息
     *
     * @param user 使用者
     *
     * @return 结果＜object＞
     */

    Result <Object> updatePersonInfo(User user);
}
