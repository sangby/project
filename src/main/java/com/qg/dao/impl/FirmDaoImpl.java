package com.qg.dao.impl;

import com.qg.dao.FirmDao;
import com.qg.po.Firm;
import com.qg.util.impl.PoListHandler;
import com.qg.util.impl.PoolUtil;


import java.util.List;

public class FirmDaoImpl implements FirmDao {
    public  List<Firm> findFirmByFirmName(String firmName){
        //群组不被封禁,公开才可被搜索
        String sql = "select * from firm where firmName = ?and block = 0 and open=1";
        try {
            return PoolUtil.Tquery(sql, new PoListHandler<>(Firm.class), firmName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
