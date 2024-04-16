package com.qg.service.impl;

import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.dao.UserDao;
import com.qg.po.User;
import com.qg.service.UserService;
import com.qg.util.impl.PoolUtil;

import java.sql.SQLException;

/**
 * 描述：用户服务实现
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class UserServiceImpl implements UserService {
    private UserServiceImpl(){

    }

    @Override
    public Result<User> login(String username, String password)  {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        User user = userDao.selectByNameAndPassword(username, password);
        if (user==null){
            //用户名或者密码错误
            return new Result<>(ResultEnum.USER_OR_PASSWORD_ERROR.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg(),null);
        }else{
            //不能把密码返回
            user.setPassWord("");
            return new Result<User>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),user);
        }
    }

    @Override
    public Result<User> select(String userName) {
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        User user = userDao.selectByName(userName);
        if(user==null){

            return new Result<>(ResultEnum.USER_ALREADY_EXIST.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg(),null);
        }else{
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),user);
        }
    }

    @Override
    public Result<User> register(String userName, String passWord) throws SQLException {
        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //如果用户已经存在那么注册失败
        User user = userDao.selectByName(userName);
        if(user!=null){
            return new Result<>(ResultEnum.USER_ALREADY_EXIST.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg(),null);
        }
        int i = userDao.insertNewOne(userName,passWord);
        if (i>0){

            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
        }else{
            return new Result<>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @Override
    public Result<User> loginInit(String userName) {
        return null;
    }

    @Override
    public Result<Object> updatePersonInfo(User user) {
        return null;
    }
}