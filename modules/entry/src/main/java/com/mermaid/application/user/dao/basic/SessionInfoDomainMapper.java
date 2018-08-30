package com.mermaid.application.user.dao.basic;

import com.mermaid.application.user.model.SessionInfoDomain;

public interface SessionInfoDomainMapper {
    int deleteByPrimaryKey(String sessionId);

    int insert(SessionInfoDomain record);

    int insertSelective(SessionInfoDomain record);

    SessionInfoDomain selectByPrimaryKey(String sessionId);

    int updateByPrimaryKeySelective(SessionInfoDomain record);

    int updateByPrimaryKey(SessionInfoDomain record);
}