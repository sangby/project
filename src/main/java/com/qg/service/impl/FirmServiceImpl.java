package com.qg.service.impl;

import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.dao.impl.FirmDaoImpl;
import com.qg.po.Firm;
import com.qg.service.FirmService;

import java.util.List;

public class FirmServiceImpl implements FirmService {
    private FirmServiceImpl() {

    }

    public  Result<List<Firm>> findFirm(String findFirmName){
        FirmDaoImpl firmDaoSingleton = SingletonFactory.getFirmDaoSingleton();
        List<Firm> firm = firmDaoSingleton.findFirmByFirmName(findFirmName);
        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),firm);

    }
}
