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
    APP(3,"客户端"),

    /**
     * 苹果客户端请求
     */
    IPAD(4,"IPAD"),
    ;
    private Integer value;

    private String code;

    EnumLoginType(Integer value, String code) {
        this.value = value;
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
