package com.qg.dao.impl;

import com.qg.util.impl.PoolUtil;

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

    public boolean isUserExistInFirm(int fid, int uid){
        String sql = "select * from memberFirm where fid = ? and uid = ?";
        try {
            ResultSet rs = PoolUtil.query(sql, fid, uid);
            if (!rs.isClosed()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "select * from adminFirm where fid = ? and uid = ?";
        try {
            ResultSet r = PoolUtil.query(sql, fid, uid);
            if(!r.isClosed()){
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
            if(!rs.isClosed()) {
                while (rs.next()) {
                    int fid = rs.getInt("fid");
                    fidList.add(fid);
                }
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
            if(!rs.isClosed()) {
                while (rs.next()) {
                    int fid = rs.getInt("fid");
                    fidList.add(fid);
                }
            }
            return fidList.stream().mapToInt(Integer::intValue).toArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}

