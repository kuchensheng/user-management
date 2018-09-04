package com.mermaid.application.user.dao.extension;

import com.mermaid.application.user.dao.basic.UserInfoDomainMapper;
import com.mermaid.application.user.model.UserInfoDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoDomainExtensionMapper extends UserInfoDomainMapper {
    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    int batchDelete(@Param("userIds") Integer[] userIds);

    /**
     * 获取用户列表
     * @param userIds
     * @param appId
     * @return
     */
    List<UserInfoDomain> selectUserInfoList(@Param("userIds") Integer[] userIds, @Param("appId") String appId);

    /**
     * 根据用户名和密码获取用户信息
     * @param userName
     * @param password
     * @return
     */
    UserInfoDomain selectUserInfoByNameAndPassword(@Param("userName") String userName, @Param("password") String password,@Param("appId") String appId);

    /**
     * 获取用户数量
     * @param appId
     * @return
     */
    Integer selectUserCount(@Param("appId") String appId);
}