package com.mermaid.application.user.dao.basic;

import com.mermaid.application.user.model.SessionInfoDomain;

public interface SessionInfoDomainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SessionInfoDomain record);

    int insertSelective(SessionInfoDomain record);

    SessionInfoDomain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SessionInfoDomain record);

    int updateByPrimaryKeyWithBLOBs(SessionInfoDomain record);

    int updateByPrimaryKey(SessionInfoDomain record);
}