package com.qg.dao.impl;

import com.qg.dao.UserDao;
import com.qg.po.User;
import com.qg.util.impl.PoHandler;
import com.qg.util.impl.PoolUtil;
import com.qg.util.impl.UserHandler;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    @Override
    public int update(User user) throws SQLException {
        String sql = "update `user` set username=?,phone=?,headPhoto=?,signature=? where uid=?";

        return PoolUtil.update(sql,user.getUserName(),user.getPhone(),user.getHeadPhoto(),user.getSignature(),user.getUid());
    }

    @Override
    public int setBlockById(int id) {
        String sql = "update `user` set block=1 where uid=?";

        try {
            return PoolUtil.update(sql,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按id选择
     *
     * @param id 身份证件
     *
     * @return 描述：用户
     */

    public User selectById(int id){
        String sql = "select * from user where uid=?";
        try {
            return PoolUtil.Tquery(sql,new PoHandler<>(User.class),id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 选择余额通过身份证件
     *
     * @param id 身份证件
     *
     * @return int
     */

    public  int selectMoneyById(int id){
        String sql = "select money from user where uid = ?";
        try {
            ResultSet rs = PoolUtil.query(sql, id);
            rs.next();
            return rs.getInt("money");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入订单和更新用户余额(扣钱)
     *
     * @param pid   pid
     * @param aid   帮助
     * @param money 余额
     *
     * @return int
     */

    public int insertIndentAndUpdateUserMoney(int pid,int aid,int money){
        Connection conn = null;
        try {
            conn = PoolUtil.getConnection();
            conn.setAutoCommit(false);

            //创建订单
            String sql = "insert into indent(pid,aid,money) values(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,pid);
            ps.setInt(2,aid);
            ps.setInt(3,money);

            ps.executeUpdate();

            //更新用户余额
            String sql1 = "update user set `money` = `money` - ? where  uid = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1,money);
            ps1.setInt(2,pid);
            ps1.executeUpdate();

            //提交
            conn.commit();
            conn.setAutoCommit(true);


        } catch (SQLException e) {
            //回滚
            try {
                conn.rollback();
                conn.setAutoCommit(true);

                return 0;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        //设置事
        return 1;
    }

    /**
     * 更新订单和用户余额
     *
     * @param id    身份证件
     * @param money 余额
     * @param aid   帮助
     *
     * @return int
     */

    public int updateIndentAndUserMoney(int id,int money,int aid){
        Connection conn = null;
        try {
            conn = PoolUtil.getConnection();
            conn.setAutoCommit(false);

            //转钱
            String sql = "update user set money = money + ? where uid = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,money);
            ps.setInt(2,aid);

            ps.executeUpdate();

            //更改订单状态
            String sql1 = "update indent set `state` = '成功' where id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1,id);
            ps1.executeUpdate();

            //提交

            conn.commit();
            conn.setAutoCommit(true);


        } catch (SQLException e) {
            //回滚
            try {

                conn.rollback();
                conn.setAutoCommit(true);

                return 0;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        //设置事
        return 1;
    }

}
