package com.qg.dao.impl;

import com.qg.dao.UserDao;
import com.qg.po.User;
import com.qg.util.impl.PoHandler;
import com.qg.util.impl.PoolUtil;
import com.qg.util.impl.UserHandler;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 描述：用户数据访问实现类
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class UserDaoImpl implements UserDao {
    private UserDaoImpl() throws SQLException {

    }
    @Override
    public User selectByNameAndPassword(String userName, String passWord) {
        String sql = "select * from `user` where `username`=? and`password`=?";
        try {
            List<User> list = PoolUtil.Tquery(sql, new UserHandler(), userName, passWord);
            if(list.isEmpty()){
                return null;
            }else{
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public User selectByName(String userName) {
        String sql = "select * from `user` where `username`=?";
        try {
            return PoolUtil.Tquery(sql, new PoHandler<>(User.class), userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insertNewOne(String userName, String password) throws SQLException {
        String sql = "insert into `user`(`username`,`password`) values(?,?)";
        return PoolUtil.update(sql,userName,password);
    }


}
