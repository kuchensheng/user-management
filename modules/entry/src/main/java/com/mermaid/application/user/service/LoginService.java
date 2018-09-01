package com.mermaid.application.user.service;

import com.mermaid.application.constant.EnumLoginResult;
import com.mermaid.application.dto.LoginLogDTO;
import com.mermaid.application.user.model.SessionInfoDomain;
import com.mermaid.framework.mvc.QueryResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Chensheng.Ku
 * 创建时间 2018-08-31 12:32
 * 描述：登录信息服务
 */
public interface LoginService {

    /**
     * 用户登录
     * @param userName 用户名
     * @param password 登录密码
     * @param loginTime 登录时间
     * @return
     */
    Boolean login(String userName, String password, Date loginTime, String appId);

    /**
     * 用户登出
     *
     */
    void loginOut();

    /**
     * 查询登录日志
     * @param userId 用户Id
     * @param appId 应用Id
     * @param startTime 查询开始时间
     * @param endTime 查询结束时间
     * @param pageNum 页码
     * @param pageSize 条数
     * @return
     */
    QueryResult<LoginLogDTO> selectLoginLogList(Integer userId, String appId, Date startTime, Date endTime, Integer pageNum, Integer pageSize);

    /**
     * 查询上一次成功登录信息
     * @param userId
     * @return
     */
    LoginLogDTO selectLastLoginLog(Integer userId,EnumLoginResult result);

    /**
     * 检查session是否存在
     * @return
     */
    Boolean getHttpSessionBySessionId();

    List<SessionInfoDomain> selectAllSessionInfo();

    Boolean deleteById(Integer sessionId);
}
