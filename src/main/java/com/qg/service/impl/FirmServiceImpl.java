package com.qg.service.impl;

import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.dao.impl.FirmDaoImpl;
import com.qg.dao.impl.OtherDaoImpl;
import com.qg.po.Firm;
import com.qg.service.FirmService;

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
}
