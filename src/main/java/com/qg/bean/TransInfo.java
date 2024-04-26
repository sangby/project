package com.qg.bean;

/**
 * 描述：转账信息
 * 创建人: Sangby
 * 创建时间: 2024/04/26
 */

public class TransInfo {
    private PayInfo payInfo;
    private Boolean isPayFirm;

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public Boolean getIsPayFirm() {
        return isPayFirm;
    }

    public void setIsPayFirm(Boolean isPayFirm) {
        this.isPayFirm = isPayFirm;
    }

    public TransInfo(PayInfo payInfo, Boolean isPayFirm) {
        this.payInfo = payInfo;
        this.isPayFirm = isPayFirm;
    }

    public TransInfo() {
    }
}
