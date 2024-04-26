package com.qg.bean;

/**
 * 描述：支付信息
 * 创建人: Sangby
 * 创建时间: 2024/04/25
 */

public class PayInfo {
    /**
     * 付款人
     */
    private int pid;
    /**
     * 收款人
     */
    private Integer aid;
    /**
     * 钱
     */
    private Integer money;
    /**
     * 通过
     */
    private String pass;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public PayInfo() {
    }

    public PayInfo(int pid, Integer aid, Integer money, String pass) {
        this.pid = pid;
        this.aid = aid;
        this.money = money;
        this.pass = pass;
    }
}
