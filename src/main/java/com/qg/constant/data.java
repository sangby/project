package com.qg.constant;

/**
 * 描述：数据规范常量
 * 创建人: Sangby
 * 创建时间: 2024/04/16
 */

public enum data {
    MAX_NAME_LENGTH(20),
    MAX_PASSWORD_LENGTH(20);



    private int value;
    data(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
