package com.qg.util.impl;


import com.qg.po.User;
import com.qg.util.MyHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：用户处理程序,早期Handler使用,现已废弃
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class UserHandler implements MyHandler<List<User>> {
    @Override
    public List<User> handler(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        List<User> list = new ArrayList<>();
        while(rs.next()){
            Integer id = rs.getInt("uid");
            String username = rs.getString("username");
            String password = rs.getString("password");
            User user = new User(id,username,password);
            list.add(user);
        }
        return list;
    }
}
