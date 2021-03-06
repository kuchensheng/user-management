package com.mermaid.application.user.model;

import java.util.Date;

public class SessionInfoDomain {
    /**
     * 主键
     */
    private Integer id;

    private String sessionId;

    /**
     * 失效时间,单位：分钟，默认30分钟失效
     */
    private Integer expire;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String sessionInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(String sessionInfo) {
        this.sessionInfo = sessionInfo == null ? null : sessionInfo.trim();
    }
}