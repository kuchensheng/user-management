package com.mermaid.application.constant;

/**
 * Desription:
 * 请求来源枚举
 * @author:Hui CreateDate:2018/8/31 0:13
 * version 1.0
 */
public enum EnumLoginType {
    /**
     * 网站请求
     */
    WEBSITE(1,"网站"),

    /**
     * 微信请求
     */
    WECHAT(2,"微信"),

    /**
     * 安卓客户端请求
     */
    ANDROID(3,"安卓"),

    /**
     * 苹果客户端请求
     */
    IOS(4,"苹果"),
    ;
    private Integer value;

    private String code;

    EnumLoginType(Integer value, String code) {
        this.value = value;
        this.code = code;
    }
}
