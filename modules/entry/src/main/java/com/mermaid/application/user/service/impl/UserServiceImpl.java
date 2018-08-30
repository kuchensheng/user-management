package com.mermaid.application.user.service.impl;

import com.mermaid.application.constant.EnumSex;
import com.mermaid.application.constant.EnumUserStatus;
import com.mermaid.application.dto.UserInfoDTO;
import com.mermaid.application.user.dao.extension.SessionInfoDomainExtensionMapper;
import com.mermaid.application.user.dao.extension.UserInfoDomainExtensionMapper;
import com.mermaid.application.user.model.UserInfoDomain;
import com.mermaid.application.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Desription:
 *
 * @author:Hui CreateDate:2018/8/31 1:12
 * version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInfoDomainExtensionMapper userInfoDomainExtensionMapper;

    @Autowired
    private SessionInfoDomainExtensionMapper sessionInfoDomainExtensionMapper;

    @Override
    public Integer createUser(String name, String password, String age, EnumSex sex, EnumUserStatus status, String phone, String email, Integer avatarId, String qq, Date createTime, String appId) {
        logger.info("在应用APPId={}添加[{}]成员name={}",appId,status,name);
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        userInfoDomain.setAge(age);
        userInfoDomain.setAge(appId);
        userInfoDomain.setAvatarId(avatarId);
        userInfoDomain.setCreateTime(createTime);
        userInfoDomain.setEmail(email);
        userInfoDomain.setName(name);
        userInfoDomain.setPassword(parse2UUID(password));
        userInfoDomain.setPhone(phone);
        userInfoDomain.setQq(qq);
        userInfoDomain.setSex(String.valueOf(sex.getValue()));
        userInfoDomain.setStatus(String.valueOf(status.getValue()));
        userInfoDomainExtensionMapper.insertSelective(userInfoDomain);
        return userInfoDomain.getId();
    }

    private String parse2UUID(String password) {
        return UUID.nameUUIDFromBytes(password.getBytes()).toString();
    }

    @Override
    public Boolean updateUser(Integer userId, String name, String password, String age, EnumSex sex, EnumUserStatus status, String phone, String email, Integer avatarId, String qq, Date updateTime, String appId) {
        return null;
    }

    @Override
    public Boolean deleteUsers(Integer[] userIds) {
        return null;
    }

    @Override
    public List<UserInfoDTO> selectUserInfos(Integer[] userIds) {
        return null;
    }

    @Override
    public UserInfoDTO selectUserInfoDetail(Integer userId) {
        return null;
    }

    @Override
    public HttpSession login(String name, String password, String appId) {
        return null;
    }

    @Override
    public HttpSession getHttpSessionBySessionId(String sessionId) {
        return null;
    }
}
