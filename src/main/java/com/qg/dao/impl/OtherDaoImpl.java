package com.qg.dao.impl;

import com.qg.util.impl.PoolUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：还有一些其他奇怪的表的数据交互
 * 创建人: Sangby
 * 创建时间: 2024/04/21
 */

public class OtherDaoImpl {
    /**
     * 在群员表插入新数据
     *
     * @param fid fid
     * @param uid uid
     *
     * @return int
     */

    public int insertNewMember(int fid,int uid){
        String sql = "insert into memberFirm(fid,uid) values(?,?)";
//        Object[] params = {fid,uid};
        try {
            return PoolUtil.update(sql, fid, uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询用户是否已经在群中,遍历管理表和群员表
     *
     * @param fid fid
     * @param uid uid
     *
     * @return boolean
     */

    public boolean isUserExistInFirm(int uid, int fid){
        String sql = "select * from memberFirm where fid = ? and uid = ?";
        try {
            ResultSet rs = PoolUtil.query(sql, fid, uid);
            //如果rs里面有东西
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "select * from adminFirm where fid = ? and uid = ?";
        try {
            ResultSet r = PoolUtil.query(sql, fid, uid);
            if(r.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return false;
    }

    /**
     * 插入新群组负责人数据
     *
     * @param fid fid
     * @param uid uid
     *
     * @return int
     */

    public int insertNewAdmin(int fid,int uid){
        String sql = "insert into adminFirm(fid,uid) values(?,?)";
        try {
            return PoolUtil.update(sql, fid, uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 搜索负责群组fid通过uid
     *
     * @param uid uid
     *
     * @return int[]
     */

    public int[] findResponseFidByUid(int uid){
        String sql ="select * from adminFirm where uid = ?";
        //看之后能不能限制一下个人所能创建群组的个数
        List<Integer> fidList = new ArrayList<>();
        try {
            ResultSet rs = PoolUtil.query(sql, uid);
            //如果rs不是空的

                while (rs.next()) {
                    int fid = rs.getInt("fid");
                    fidList.add(fid);
                }

            return fidList.stream().mapToInt(Integer::intValue).toArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 通过成员表查找群组fid
     *
     * @param uid uid
     *
     * @return int[]
     */

    public int[] findMemberFidByUid(int uid){
        String sql ="select * from memberFirm where uid = ?";
        //看之后能不能限制一下个人所能创建群组的个数
        List<Integer> fidList = new ArrayList<>();
        try {
            ResultSet rs = PoolUtil.query(sql, uid);
            //如果rs不是空的

                while (rs.next()) {
                    int fid = rs.getInt("fid");
                    fidList.add(fid);
                }

            return fidList.stream().mapToInt(Integer::intValue).toArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int[] findUidByFid(int fid){
        String sql ="select * from memberFirm where fid = ?";
        List<Integer> uidList = new ArrayList<>();
        try {
            ResultSet rs = PoolUtil.query(sql, fid);
            //如果rs不是空的

            while (rs.next()) {
                int uid = rs.getInt("uid");
                uidList.add(uid);
            }

            return uidList.stream().mapToInt(Integer::intValue).toArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 成员群组搜索钱通过fid和uid
     *
     * @param fid fid
     * @param uid uid
     *
     * @return int
     */

    public int memberFirmFindMoneyByFidAndUid(int fid, int uid){
        String sql = "select money from memberFirm where fid= ? and uid = ?";
        try {
            ResultSet rs = PoolUtil.query(sql, fid, uid);

            rs.next();

            return rs.getInt("money");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 管理群组搜索钱通过fid和uid
     *
     * @param fid fid
     * @param uid uid
     *
     * @return int
     */

    public int adminFirmFindMoneyByFidAndUid(int fid, int uid){
        String sql = "select money from adminFirm where fid= ? and uid = ?";
        try {
            ResultSet rs = PoolUtil.query(sql, fid, uid);

            rs.next();

            return rs.getInt("money");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 插入订单和更新用户余额
     *
     * @return int
     */

    public  int insertIndentAndUpdateUserMoney(int pid ,int aid,int money)  {
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
            String sql1 = "update user set `money` = `money` - ? where uid = ?";
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
     * 插入订单和更新管理余额,pid是群组,aid是人
     *
     * @param pid   pid
     * @param aid   帮助
     * @param money 余额
     *
     * @return int
     */

    public int insertIndentAndUpdateAdminMoney(int pid ,int aid,int money){
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
            String sql1 = "update adminFirm set `money` = `money` - ? where fid = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1,money);
            ps1.setInt(2,aid);
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
     * 插入订单和更新成员余额,需要把自己id传进来,成员表需要uid,fid才能形成唯一标识
     *
     * @param pid   pid
     * @param aid   帮助
     * @param money 余额
     *
     * @return int
     */

    public int insertIndentAndUpdateMemberMoney(int pid ,int aid,int money,int uid){
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
            String sql1 = "update memberFirm set `money` = `money` - ? where fid = ? and uid = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1,money);
            ps1.setInt(2,pid);
            ps1.setInt(3,uid);
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
     * 更新(扣钱)群组基金
     *
     * @param fid   fid
     * @param money 余额
     *
     * @return int
     */

    public int updateFirmFund(int fid,int money){
        String sql = "update firm set `fund` = `fund` - ? where fid = ?";
        try {
            return PoolUtil.update(sql,money,fid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * trans余额和更新订单状态
     *
     * @param aid   帮助
     * @param money 余额
     * @param id    订单id
     * @return int
     */

    public int transMoneyToFirmAndUpdateIndentState(int aid,int money,int id){
        Connection conn = null;
        try {
            conn = PoolUtil.getConnection();
            conn.setAutoCommit(false);

            //转钱
            String sql = "update firm set `fund` = `fund` + ? where fid = ?";

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

    /**
     * 更新订单和用户余额(加钱)
     *
     * @param aid   帮助
     * @param money 余额
     * @param id    身份证件
     *
     * @return int
     */

    public int updateIndentAndUserMoney(int aid,int money,int id){
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

    /**
     * 获得订单身份证件
     *
     * @param aid   帮助
     * @param pid   pid
     * @param money 余额
     *
     * @return int
     */

    public int getIndentId(int aid,int pid, int money){
        String sql = "select id from indent where aid = ? and pid = ? and money =? and state = '等待响应'";
        try {
            ResultSet rs = PoolUtil.query(sql, aid, pid, money);
            rs.next();

            return rs.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 增加群管理员的资金
     *
     * @param uid   uid
     * @param fid   fid
     * @param money 余额
     *
     * @return int
     */

    public int addAdminFund(int uid, int fid, int money) {
        String sql = "update adminFirm set money = money + ? where uid = ? and fid = ?";
        try {
            return PoolUtil.update(sql, money, uid, fid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 分配,实际上是管理员表分配出钱
     *
     * @param fid   fid
     * @param uid   uid
     * @param money 余额
     *
     * @return int
     */

    public int distribute(int fid,int uid,int money){
        Connection conn = null;
        try {
            conn = PoolUtil.getConnection();
            conn.setAutoCommit(false);

            //转权限
            String sql = "update adminFirm set `money` = `money` - ? where fid = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,money);
            ps.setInt(2,fid);

            ps.executeUpdate();

            //收权限
            String sql1 = "update memberFirm set money = money + ? where uid = ? and fid = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1,money);
            ps1.setInt(2,uid);
            ps1.setInt(3,fid);
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

