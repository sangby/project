package com.qg.bean;

/**
 * 描述：包装用户和资金信息
 * 创建人: Sangby
 * 创建时间: 2024/04/25
 */

public class UserFund {
    private int fid;
    private int money;

    public UserFund(int fid, int money) {
        this.fid = fid;
        this.money = money;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getFid() {
        return fid;
    }

    public int getMoney() {
        return money;
    }
}
