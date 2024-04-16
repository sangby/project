package com.qg.constant;

/**
 * 描述：结果集枚举
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public enum ResultEnum {
    //一个情况分别对应状态码,和描述

    SUCCESS(200,"成功"),
    USER_OR_PASSWORD_ERROR(400,"用户名或密码错误"),
    USER_NOT_EXIST(401,"用户不存在"),
    USER_ALREADY_EXIST(402,"用户已存在"),
    ERROR(500,"系统错误")
    ;
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }
//可分别获取
    public String getMsg() {
        return msg;
    }

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
