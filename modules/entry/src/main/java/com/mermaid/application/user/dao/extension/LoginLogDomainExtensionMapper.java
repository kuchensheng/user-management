package com.mermaid.application.user.dao.extension;

import com.github.pagehelper.Page;
import com.mermaid.application.user.dao.basic.LoginLogDomainMapper;
import com.mermaid.application.user.model.LoginLogDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface LoginLogDomainExtensionMapper extends LoginLogDomainMapper {
    /**
     * 查询用户登录日志
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    Page<LoginLogDomain> selectUserLoginLog(@Param("userId") Integer userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询多个用户的登录日志
     * @param userIdList
     * @param startTime
     * @param endTime
     * @return
     */
    Page<LoginLogDomain> selectUsersLoginLog(@Param("userIdList") List<Integer> userIdList, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}