package com.mermaid.application.user.service;

import com.mermaid.application.constant.EnumSex;
import com.mermaid.application.constant.EnumUserStatus;
import com.mermaid.application.dto.UserInfoDTO;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Desription:
 * 用户中心入库
 * @author:Hui CreateDate:2018/8/31 0:33
 * version 1.0
 */
public interface UserService {

    /**
     * 创建用户
     * @param name 用户名
     * @param password 密码
     * @param age 年龄
     * @param sex 性别
     * @param status 身份/角色
     * @param phone 电话
     * @param email 邮箱
     * @param avatarId 头像Id
     * @param qq QQ
     * @param createTime 创建时间
     * @param appId 所属应用/公司
     * @return
     */
    Integer createUser(String name, String password, String age, EnumSex sex, EnumUserStatus status, String phone, String email, Integer avatarId, String qq, Date createTime,String appId);

    /**
     * 创建用户
     * @param userId 用户Id
     * @param name 用户名
     * @param password 密码
     * @param age 年龄
     * @param sex 性别
     * @param status 身份/角色
     * @param phone 电话
     * @param email 邮箱
     * @param avatarId 头像Id
     * @param qq QQ
     * @param updateTime 更新时间
     * @param appId 所属应用/公司
     * @return
     */
    Boolean updateUser(Integer userId,String name,String password,String age,EnumSex sex,EnumUserStatus status,String phone,String email,Integer avatarId,String qq,Date updateTime,String appId);

    /**
     * 删除用户信息
     * @param userIds 用户Id数组
     * @return
     */
    Boolean deleteUsers(Integer[] userIds);

    /**
     * 根据id获取用户信息
     * @param userIds 用户Id数组
     * @return
     */
    List<UserInfoDTO> selectUserInfos(Integer[] userIds,String appId);

    /**
     * 获取用户详情
     * @param userId 用户Id
     * @return
     */
    UserInfoDTO selectUserInfoDetail(Integer userId);


    /**
     * 检查session是否还在，若不在返回空
     * @param sessionId
     * @return
     */
    HttpSession getHttpSessionBySessionId(String sessionId);
}
