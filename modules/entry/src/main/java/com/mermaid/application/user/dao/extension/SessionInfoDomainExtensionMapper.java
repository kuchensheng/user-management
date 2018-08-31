package com.mermaid.application.user.dao.extension;

import com.mermaid.application.user.dao.basic.SessionInfoDomainMapper;
import com.mermaid.application.user.model.SessionInfoDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionInfoDomainExtensionMapper extends SessionInfoDomainMapper {
    int deleteBySessionId(@Param("sessionId") String sessionId);

    /**
     * 根据sessionid 获取session信息
     * @param sessionId
     * @return
     */
    SessionInfoDomain selectBySessionId(@Param("sessionId") String sessionId);
}