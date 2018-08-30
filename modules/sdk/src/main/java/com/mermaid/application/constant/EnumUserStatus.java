package com.mermaid.application.constant;

/**
 * Desription:
 * 用户身份标记
 * @author:Hui CreateDate:2018/8/31 0:21
 * version 1.0
 */
public enum EnumUserStatus {
    /**
     * 超级管理员
     */
    SUPER_ADMIN(1),

    /**
     * 管理员
     */
    ADMIN(2),

    /**
     * 会员
     */
    VIP(3),

    /**
     * 普通用户
     */
    NORMAL(4),
    ;
    private Integer value;

    EnumUserStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
