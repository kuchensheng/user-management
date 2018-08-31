package com.mermaid.application.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mermaid.application.constant.EnumLoginResult;
import com.mermaid.application.constant.EnumLoginType;
import com.mermaid.application.dto.LoginLogDTO;
import com.mermaid.application.user.dao.extension.LoginLogDomainExtensionMapper;
import com.mermaid.application.user.dao.extension.SessionInfoDomainExtensionMapper;
import com.mermaid.application.user.dao.extension.UserInfoDomainExtensionMapper;
import com.mermaid.application.user.model.LoginLogDomain;
import com.mermaid.application.user.model.SessionInfoDomain;
import com.mermaid.application.user.model.UserInfoDomain;
import com.mermaid.application.user.service.LoginService;
import com.mermaid.application.user.service.UserService;
import com.mermaid.application.user.util.HttpRequestDeviceUtils;
import com.mermaid.application.user.util.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Chensheng.Ku
 * @version 创建时间：2018/8/31 12:44
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private LoginLogDomainExtensionMapper loginLogDomainExtensionMapper;

    @Autowired
    private SessionInfoDomainExtensionMapper sessionInfoDomainExtensionMapper;

    @Autowired
    private UserInfoDomainExtensionMapper userInfoDomainExtensionMapper;

    private final Integer sessionExpired = 30;

    @Override
    @Transactional
    public HttpSession login(String userName, String password, Date loginTime, String appId,HttpServletRequest request) {
        logger.info("用户登录，userName={}，clientIP={}",userName);
        EnumLoginResult result = EnumLoginResult.FAILURE;
        String clientIp = getIpAddress(request);
        UserInfoDomain userInfoDomain = userInfoDomainExtensionMapper.selectUserInfoByNameAndPassword(userName,password,null);
        HttpSession session = null;
        if(null != userInfoDomain) {
            session = request.getSession();
            session.setMaxInactiveInterval(sessionExpired * 60);
            session.setAttribute("id",userInfoDomain.getId());
            session.setAttribute("name",userInfoDomain.getName());
            logger.info("将session数据保存到sessionInfo表");
            String sessionInfo = JSONObject.toJSONString(session);
            SessionInfoDomain sessionInfoDomain = new SessionInfoDomain();
            sessionInfoDomain.setSessionId(session.getId());
            sessionInfoDomain.setSessionInfo(sessionInfo);
            sessionInfoDomain.setExpire(sessionExpired);
            sessionInfoDomainExtensionMapper.insertSelective(sessionInfoDomain);
            result = EnumLoginResult.SUCCESS;
        }
        logger.info("记录此次登录信息");
        LoginLogDomain loginLogDomain = new LoginLogDomain();
        loginLogDomain.setClientIp(getIpAddress(request));
        loginLogDomain.setAddress(IPUtil.getAddress(loginLogDomain.getClientIp()));
        if(null == loginTime) {
            loginTime = new Date();
        }
        loginLogDomain.setCreateTime(loginTime);
        loginLogDomain.setResult(String.valueOf(result.getValue()));
        EnumLoginType type = EnumLoginType.WEBSITE;
        boolean mobileDevice = HttpRequestDeviceUtils.isMobileDevice(request);
        boolean iPadDevice = HttpRequestDeviceUtils.isIPadDevice(request);
        if(mobileDevice) {
           type = EnumLoginType.APP;
        }
        if(iPadDevice) {
            type = EnumLoginType.IPAD;
        }
        logger.info("此次登录是={}",type.getCode());
        loginLogDomain.setType(String.valueOf(type.getValue()));
        loginLogDomain.setUserId(userInfoDomain.getId());
        loginLogDomain.setUserName(userInfoDomain.getName());
        loginLogDomainExtensionMapper.insertSelective(loginLogDomain);
        logger.info("登录完成，登录结果={}",result.toString());
        return session;
    }

    @Override
    public void loginOut(String sessionId) {
        logger.info("用户登出，sessionId={}",sessionId);
        if(existedSession(sessionId)) {
            sessionInfoDomainExtensionMapper.deleteByPrimaryKey(sessionId);
        }
    }

    /**
     * 判断session是否存在
     * @param sessionId
     * @return
     */
    private boolean existedSession(String sessionId) {
        SessionInfoDomain sessionInfoDomain = sessionInfoDomainExtensionMapper.selectByPrimaryKey(sessionId);
        return null != sessionInfoDomain;
    }

    @Override
    public List<LoginLogDTO> selectLoginLogList(Integer userId, String appId, Date startTime, Date endTime, Integer pageNum, Integer pageSize) {
        logger.info("查询登录日志，userId={}，appId={}");

        return null;
    }

    @Override
    public LoginLogDTO selectLastLoginLog(Integer userId, EnumLoginResult result) {
        return null;
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
