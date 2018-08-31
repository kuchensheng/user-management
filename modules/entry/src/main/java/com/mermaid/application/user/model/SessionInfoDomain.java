package com.mermaid.application.user.model;

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

    public String getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(String sessionInfo) {
        this.sessionInfo = sessionInfo == null ? null : sessionInfo.trim();
    }
}