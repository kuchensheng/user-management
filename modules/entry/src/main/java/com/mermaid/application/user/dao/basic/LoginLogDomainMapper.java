package com.mermaid.application.user.dao.basic;

import com.mermaid.application.user.model.LoginLogDomain;

public interface LoginLogDomainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginLogDomain record);

    int insertSelective(LoginLogDomain record);

    LoginLogDomain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginLogDomain record);

    int updateByPrimaryKey(LoginLogDomain record);
}