package com.qg.constant;


/**
 * 描述：结果集
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class Result<T> {
    /**
     * 编号
     */
    private Integer code;
    /**
     * 状态
     */
    private String msg;
    /**
     * 数据
     */
    private T data;


    public Result(String msg) {
        this.msg = msg;
    }

    public Result() {

    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
