package com.mermaid.application.user.service.impl;

import com.mermaid.application.constant.EnumSex;
import com.mermaid.application.constant.EnumUserStatus;
import com.mermaid.application.dto.UserInfoDTO;
import com.mermaid.application.user.dao.extension.SessionInfoDomainExtensionMapper;
import com.mermaid.application.user.dao.extension.UserInfoDomainExtensionMapper;
import com.mermaid.application.user.model.UserInfoDomain;
import com.mermaid.application.user.service.UserService;
import com.mermaid.framework.mvc.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        userInfoDomain.setAppId(appId);
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
        logger.info("更新用户信息，userId={},appId={}",userId,appId);
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        userInfoDomain.setId(userId);
        userInfoDomain.setAge(age);
        userInfoDomain.setAvatarId(avatarId);
        userInfoDomain.setAppId(appId);
        userInfoDomain.setEmail(email);
        userInfoDomain.setName(name);
        userInfoDomain.setPassword(parse2UUID(password));
        userInfoDomain.setPhone(phone);
        userInfoDomain.setQq(qq);
        userInfoDomain.setSex(String.valueOf(sex.getValue()));
        userInfoDomain.setStatus(String.valueOf(status.getValue()));
        int i = userInfoDomainExtensionMapper.updateByPrimaryKeySelective(userInfoDomain);
        return i > 0;
    }

    @Override
    @Transactional
    public Boolean deleteUsers(Integer[] userIds) {
        logger.info("删除用户信息，Id={}",userIds.toString());
        try {
            userInfoDomainExtensionMapper.batchDelete(userIds);
            return true;
        } catch (Exception e) {
            logger.error("删除用户信息异常",e);
            throw BusinessException.withErrorCode("DELETE_ERROR").withErrorMessageArguments("删除用户信息异常");
        }
    }

    @Override
    public List<UserInfoDTO> selectUserInfos(Integer[] userIds,String appId) {
        logger.info("获取用户列表，userIds={}，appId={}",userIds.toString(),appId);
        List<UserInfoDomain> userInfoDomainList = userInfoDomainExtensionMapper.selectUserInfoList(userIds,appId);
        return parseUserInfoList2DTOList(userInfoDomainList);
    }

    private List<UserInfoDTO> parseUserInfoList2DTOList(List<UserInfoDomain> userInfoDomainList) {
        if(CollectionUtils.isEmpty(userInfoDomainList)) {
            return null;
        }
        List<UserInfoDTO> result = new ArrayList<>(userInfoDomainList.size());
        for (UserInfoDomain domain : userInfoDomainList) {
            result.add(parseUserInfoDomain2DTO(domain));
        }
        return result;
    }

    @Override
    public UserInfoDTO selectUserInfoDetail(Integer userId) {
        logger.info("获取用户详情，userId={}",userId);
        UserInfoDomain userInfoDomain = userInfoDomainExtensionMapper.selectByPrimaryKey(userId);
        return parseUserInfoDomain2DTO(userInfoDomain);
    }

    private UserInfoDTO parseUserInfoDomain2DTO(UserInfoDomain userInfoDomain) {
        if (userInfoDomain == null) {
            return null;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(userInfoDomain.getId());
        userInfoDTO.setName(userInfoDomain.getName());
        userInfoDTO.setAge(userInfoDomain.getAge());
        userInfoDTO.setSex(userInfoDomain.getSex());
        userInfoDTO.setStatus(userInfoDomain.getStatus());
        userInfoDTO.setPhone(userInfoDomain.getPhone());
        userInfoDTO.setEmail(userInfoDomain.getEmail());
        userInfoDTO.setAvatarId(userInfoDomain.getAvatarId());
        userInfoDTO.setQq(userInfoDomain.getQq());
        userInfoDTO.setCreateTime(userInfoDomain.getCreateTime());
        userInfoDTO.setAppId(userInfoDomain.getAppId());
        return userInfoDTO;
    }

    @Override
    public HttpSession getHttpSessionBySessionId(String sessionId) {
        return null;
    }
}
