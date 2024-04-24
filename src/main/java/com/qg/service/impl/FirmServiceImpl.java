package com.qg.service.impl;

import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.dao.impl.FirmDaoImpl;
import com.qg.dao.impl.OtherDaoImpl;
import com.qg.po.Firm;
import com.qg.service.FirmService;

import java.util.ArrayList;
import java.util.List;

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
            //添加
            firmDaoSingleton.addFirm(firmName,introduction);
            otherDaoSingleton.insertNewAdmin(idByFirmName,uid);

            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
        }else{

            new Result<>(ResultEnum.FIRM_ALREADY_EXIST.getCode(),ResultEnum.FIRM_ALREADY_EXIST.getMsg());
        }
        return null;

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
}
