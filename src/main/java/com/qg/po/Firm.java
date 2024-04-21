package com.qg.po;

public class Firm {

    /**
     * 群组id
     */
    private Integer fid;
    /**
     * 群组名称
     */
    private String firmName;

    /**
     * 介绍
     */
    private String introduction;
    /**
     * 公开
     */
    private Boolean open;
    /**
     * 成员数量
     */
    private Integer memberNum;
    /**
     * 基金
     */
    private Integer fund;
    /**
     * 封锁
     */
    private Boolean block;
    /**
     * 头像
     */
    private String headPhoto;


    public Firm(Integer fid, String firmName, String introduction, Boolean open, Integer memberNum, Integer fund, Boolean block, String headPhoto) {
        this.fid = fid;
        this.firmName = firmName;
        this.introduction = introduction;
        this.open = open;
        this.memberNum = memberNum;
        this.fund = fund;
        this.block = block;
        this.headPhoto = headPhoto;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public Integer getFund() {
        return fund;
    }

    public void setFund(Integer fund) {
        this.fund = fund;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public Firm() {
    }
}
