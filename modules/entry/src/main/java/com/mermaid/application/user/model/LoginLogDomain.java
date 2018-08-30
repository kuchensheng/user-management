package com.mermaid.application.user.model;

import java.util.Date;

public class LoginLogDomain {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 登陆类型，1-网页登陆，2-微信登陆，3-APP登陆，4-ios登陆
     */
    private String type;

    /**
     * 登陆结果
     */
    private String result;

    /**
     * 登陆地址
     */
    private String address;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 登陆时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}