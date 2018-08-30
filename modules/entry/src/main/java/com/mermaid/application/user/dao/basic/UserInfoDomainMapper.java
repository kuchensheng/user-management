package com.mermaid.application.user.dao.basic;

import com.mermaid.application.user.model.UserInfoDomain;

public interface UserInfoDomainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoDomain record);

    int insertSelective(UserInfoDomain record);

    UserInfoDomain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoDomain record);

    int updateByPrimaryKey(UserInfoDomain record);
}