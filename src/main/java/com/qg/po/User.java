package com.qg.po;


/**
 * 描述：用户
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class User {
    /**
     * 身份id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 电话
     */
    private String phone;
    /**
     * 封禁
     */
    private Boolean block;
    /**
     * 钱
     */
    private Integer money;
    /**
     * 头像
     */
    private String headPhoto;
    /**
     * 签名
     */
    private String signature;
    /**
     * 在线
     */




    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public User(Integer id, String userName, String passWord, String phone, Boolean block, Integer money, String headPhoto, String signature) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.block = block;
        this.money = money;
        this.headPhoto = headPhoto;
        this.signature = signature;
    }

    public User(Integer id, String userName, String passWord) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
    }
}