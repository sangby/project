package com.qg.service.impl;

import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.dao.impl.FirmDaoImpl;
import com.qg.dao.impl.OtherDaoImpl;
import com.qg.dao.impl.UserDaoImpl;
import com.qg.po.Firm;
import com.qg.po.User;
import com.qg.service.FirmService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class FirmServiceImpl implements FirmService {
    private FirmServiceImpl() {

    }

    /**
     * 搜索群组
     *
     * @param findFirmName 搜索群组名称
     *
     * @return 结果＜list＜firm＞＞
     */

    public  Result<List<Firm>> findFirm(String findFirmName){
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();
        List<Firm> firm = firmDaoSingleton.findFirmByFirmName(findFirmName);
        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),firm);

    }

    /**
     * 申请群组
     *
     * @param userId 用户身份证件
     * @param firmId 群组身份证件
     *
     * @return 结果＜object＞
     */

    public Result<Object> joinFirm (int userId, int firmId){

        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();
        if (otherDaoSingleton.isUserExistInFirm(userId,firmId)) {
            //返回用户已存在
            return new Result<>(ResultEnum.USER_ALREADY_EXIST.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg());
        }else{
            otherDaoSingleton.insertNewMember(firmId,userId);
            firmDaoSingleton.firmMemberNumAddOne(firmId);
            //返回成功
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
        }
    }

    /**
     * 创造群组
     *
     * @param firmName     群组名称
     * @param introduction 介绍
     * @param uid          uid
     *
     * @return 结果＜object＞
     */

    public Result<Object> createFirm(String firmName,String introduction,int uid){
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();
        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();
//判断企业是否已经存在
        int idByFirmName = firmDaoSingleton.findIdByFirmName(firmName);
        if (idByFirmName == -1){
            //添加到群组里
            firmDaoSingleton.addFirm(firmName,introduction);
//            再查一遍id
            int fid = firmDaoSingleton.findIdByFirmName(firmName);
            otherDaoSingleton.insertNewAdmin(fid,uid);

            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
        }else{

            return new Result<>(ResultEnum.FIRM_ALREADY_EXIST.getCode(),ResultEnum.FIRM_ALREADY_EXIST.getMsg());
        }

    }

    /**
     * 搜索负责群组
     *
     * @param uid uid
     *
     * @return 结果＜list＜firm＞＞
     */

    public Result<List<Firm>> findResponseFirm(int uid){
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();
        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();

        List<Firm> responseFirmByFid = new ArrayList<>();
        int[] fid = otherDaoSingleton.findResponseFidByUid(uid);
        for (int j : fid) {
            Firm firmByFid = firmDaoSingleton.findFirmByFid(j);

            responseFirmByFid.add(firmByFid);
        }

        if(!responseFirmByFid.isEmpty()){
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),responseFirmByFid);
        }
        return new Result<>( ResultEnum.FIRM_NOT_EXIST.getCode(),ResultEnum.FIRM_NOT_EXIST.getMsg());

    }

    public Result<List<Firm>> findMemberFirm(int uid){
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();
        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();

        List<Firm> MemberFirmByFid = new ArrayList<>();
        int[] fid = otherDaoSingleton.findMemberFidByUid(uid);
        for (int j : fid) {
            Firm firm = firmDaoSingleton.findFirmByFid(j);
            MemberFirmByFid.add(firm);
        }


        if(!MemberFirmByFid.isEmpty()){
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),MemberFirmByFid);
        }
        return new Result<>( ResultEnum.FIRM_NOT_EXIST.getCode(),ResultEnum.FIRM_NOT_EXIST.getMsg());

    }


    /**
     * 更新群组信息
     *
     * @param firm 群组
     *
     * @return 结果＜object＞
     */

    public Result<Object> updateFirmInfo(Firm firm){
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();

         int fid = firmDaoSingleton.findIdByFirmName(firm.getFirmName());
        //如果查找到id不相同,说明此名称已存在
        if(fid!=firm.getFid()){
            return new Result<>(ResultEnum.FIRM_NAME_EXIST.getCode(),ResultEnum.FIRM_NAME_EXIST.getMsg());
        }
        firmDaoSingleton.updateFirmInfo(firm);

        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    /**
     * 获得群组成员信息
     *
     * @param fid fid
     *
     * @return 结果＜list＜user＞＞
     */

    public Result<List<User>> getFirmMemberInfo(int fid){

        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();
        UserDaoImpl userDaoSingleton = SingletonFactory.getUserDaoSingleton();

        List<User> memberInfoByFid = new ArrayList<>();

        int[] uid = otherDaoSingleton.findUidByFid(fid);
        for (int i : uid) {
            User user = userDaoSingleton.selectById(i);
            memberInfoByFid.add(user);
        }
        //如果没有成员
        if(memberInfoByFid.isEmpty()){
            return new Result<>(ResultEnum.USER_NOT_EXIST.getCode(),ResultEnum.USER_NOT_EXIST.getMsg());
        }


        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),memberInfoByFid);

    }

    /**
     * 分配,这笔资金权限转移就不用订单记录了
     *
     * @param pid   pid
     * @param aid   帮助
     * @param money 余额
     *
     * @return 结果＜object＞
     */

    public Result<Object> distribute(int uid,int pid, int aid ,int money) {
        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();
        int distribute;

        synchronized (this) {
            if (otherDaoSingleton.adminFirmFindMoneyByFidAndUid(pid, uid) < money) {
                return new Result<>(ResultEnum.MONEY_NOT_ENOUGH.getCode(), ResultEnum.MONEY_NOT_ENOUGH.getMsg());
            }
            distribute = otherDaoSingleton.distribute(pid, aid, money);
        }

        if (distribute == 1) {
            return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), money);
        } else {
            //错误
            return new Result<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }

    }
    public Result<Object> transfer(String userName,int pid, int aid, int money,int uid,String pass){
        //同样需要余额校验,密码
        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();
        UserDaoImpl userDaoSingleton = SingletonFactory.getUserDaoSingleton();
        boolean isAdmin = true;
//检查收款人是否存在
        if(userDaoSingleton.selectById(aid)==null){
            return new Result<>(ResultEnum.USER_NOT_EXIST.getCode(),ResultEnum.USER_NOT_EXIST.getMsg());
        }
        //检查付款群组是否存在
        if(firmDaoSingleton.findFirmByFid(pid)==null){
            return new Result<>(ResultEnum.FIRM_NOT_EXIST.getCode(),ResultEnum.FIRM_NOT_EXIST.getMsg());
        }

        int id1 = 0;
        int id2= 0;
        //判断是否为管理员
        int[] memberFidByUid = otherDaoSingleton.findMemberFidByUid(uid);
        for (int i : memberFidByUid) {
            if(i == pid ){
                isAdmin = false;
            }
        }
        //校验密码
        if (userDaoSingleton.selectByNameAndPassword(userName,pass)!=null){
            if(isAdmin){
                //校验余额
                synchronized (this){
                    if(otherDaoSingleton.adminFirmFindMoneyByFidAndUid(pid,uid)>=money){
                        //扣钱生订单
                        if(otherDaoSingleton.insertIndentAndUpdateAdminMoney(pid,aid,money)==1){
                            //给群组本身扣钱
                            otherDaoSingleton.updateFirmFund(pid,money);
                            //记录订单编号
                            id1 = otherDaoSingleton.getIndentId(aid, pid, money);

                        }else {
                            return new Result<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
                        }

                    }else{
                        return new Result<>(ResultEnum.MONEY_NOT_ENOUGH.getCode(), ResultEnum.MONEY_NOT_ENOUGH.getMsg());
                    }
                }
                //生钱消订单
                if(otherDaoSingleton.updateIndentAndUserMoney(aid,money,id1)==1){
                    return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
                }else{
                    return new Result<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
                }

            }else{
                synchronized (this){
                    if(otherDaoSingleton.memberFirmFindMoneyByFidAndUid(pid,uid)>=money){
                        if(otherDaoSingleton.insertIndentAndUpdateMemberMoney(pid,aid,money,uid)==1){
                            //给群组本身扣钱
                            otherDaoSingleton.updateFirmFund(pid,money);
                            id2 = otherDaoSingleton.getIndentId(aid, pid, money);


                        }
                    }else{
                        return new Result<>(ResultEnum.MONEY_NOT_ENOUGH.getCode(), ResultEnum.MONEY_NOT_ENOUGH.getMsg());
                    }
                }
                //生钱消订单
                if(otherDaoSingleton.updateIndentAndUserMoney(aid,money,id2)==1){
                    return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
                }else{
                    return new Result<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
                }
            }

        }else{
            return new Result<>(ResultEnum.PASSWORD_ERROR.getCode(), ResultEnum.PASSWORD_ERROR.getMsg());
        }

    }
}
