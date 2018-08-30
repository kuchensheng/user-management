package com.mermaid.application.constant;

/**
 * Desription:
 * 登陆结果枚举
 * @author:Hui CreateDate:2018/8/31 0:17
 * version 1.0
 */
public enum EnumLoginResult {
    /**
     * 成功
     */
    SUCCESS(1),

    /**
     * 失败
     */
    FAILURE(2),
    ;

    private Integer value;

    EnumLoginResult(Integer value) {
        this.value = value;
    }
}
