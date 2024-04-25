package com.qg.dao.impl;

import com.qg.bean.SingletonFactory;
import com.qg.bean.UserFund;
import com.qg.constant.Result;
import com.qg.dao.FirmDao;
import com.qg.po.Firm;
import com.qg.service.impl.FirmServiceImpl;
import com.qg.util.impl.PoHandler;
import com.qg.util.impl.PoListHandler;
import com.qg.util.impl.PoolUtil;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FirmDaoImpl implements FirmDao {
    /**
     * 搜索群组通过群组名称
     *
     * @param firmName 群组名称
     *
     * @return 列表<firm>
     */

    public  List<Firm> findFirmByFirmName(String firmName){
        //群组不被封禁,公开才可被搜索
        String sql = "select * from firm where firmName = ?and block = 0 and open=1";
        try {
            return PoolUtil.Tquery(sql, new PoListHandler<>(Firm.class), firmName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 添加群组到群组表中
     *
     * @param firmName     群组名称
     * @param introduction 介绍
     *
     * @return int
     */

    public int addFirm(String firmName,String introduction){
        String sql ="insert into firm(firmName,introduction) values(?,?)";
        try {
            return PoolUtil.update(sql,firmName,introduction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 搜索身份证件通过群组名称,同时可以判断是否存在,若是不存在就返回-1
     *
     * @param firmName 群组名称
     *
     * @return int
     */

    public int findIdByFirmName(String firmName){
        String sql = "select fid from firm where firmName = ?";
        ResultSet rs = null;
        try {
            rs = PoolUtil.query(sql, firmName);
            if(rs.next()){
                return rs.getInt("fid");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    /**
     * 群组成员数量添加一
     *
     * @param fid fid
     *
     * @return int
     */

    public  int firmMemberNumAddOne(int fid){
        String sql = "update firm set memberNum = memberNum+1 where fid = ?";
        try {
            return PoolUtil.update(sql, fid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 搜索负责群组信息
     *
     * @return 列表<firm>
     */

    public Firm findFirmByFid(int fid){

        String sql ="select * from firm where fid = ?";

        try {
            Firm firm = PoolUtil.Tquery(sql, new PoHandler<>(Firm.class), fid);
            return firm;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新群组信息
     *
     * @param firm 群组
     *
     * @return int
     */

    public int updateFirmInfo(Firm firm){
        String sql ="update firm set firmName=? ,introduction=? ,headPhoto=? ,open=? where fid = ?";
        try {
            return PoolUtil.update(sql,firm.getFirmName(),firm.getIntroduction(),firm.getHeadPhoto(),firm.getOpen(),firm.getFid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获得用户在群组的资金信息
     *
     * @param uid uid
     *
     * @return 后果列表用户基金
     */




}
