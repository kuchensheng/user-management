package com.mermaid.application.user.dao.extension;

import com.mermaid.application.user.dao.basic.SessionInfoDomainMapper;
import com.mermaid.application.user.model.SessionInfoDomain;
import com.mermaid.application.user.model.UserInfoDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SessionInfoDomainExtensionMapper extends SessionInfoDomainMapper {
    int deleteBySessionId(@Param("sessionId") String sessionId);

    /**
     * 根据sessionid 获取session信息
     * @param sessionId
     * @return
     */
    SessionInfoDomain selectBySessionId(@Param("sessionId") String sessionId);

    /**
     * 获取所有的session信息
     * @return
     */
    List<SessionInfoDomain> selectAllSessionInfo();

    UserInfoDomain selectUserBySessionId(@Param("sessionid") String sessionid);

    SessionInfoDomain selectByUserId(@Param("userId") Integer userId);

    void deleteByUserId(@Param("userId") Integer userId);
}