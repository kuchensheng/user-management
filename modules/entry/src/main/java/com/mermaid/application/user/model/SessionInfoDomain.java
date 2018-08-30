package com.mermaid.application.user.model;

public class SessionInfoDomain {
    private String sessionId;

    private String sessionInfo;

    /**
     * 失效时间,单位：分钟，默认30分钟失效
     */
    private Integer expire;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public String getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(String sessionInfo) {
        this.sessionInfo = sessionInfo == null ? null : sessionInfo.trim();
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }
}