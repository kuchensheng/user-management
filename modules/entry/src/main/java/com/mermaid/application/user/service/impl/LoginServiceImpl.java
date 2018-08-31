package com.mermaid.application.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
import com.mermaid.application.user.util.EnumHelperUtil;
import com.mermaid.application.user.util.HttpRequestDeviceUtils;
import com.mermaid.application.user.util.IPUtil;
import com.mermaid.application.user.util.StringUtil;
import com.mermaid.framework.mvc.BusinessException;
import com.mermaid.framework.mvc.QueryResult;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    public Boolean login(String userName, String password, Date loginTime, String appI) {
        logger.info("用户登录，userName={}，clientIP={}",userName);
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        EnumLoginResult result = EnumLoginResult.FAILURE;
        String clientIp = getIpAddress(request);
        UserInfoDomain userInfoDomain = userInfoDomainExtensionMapper.selectUserInfoByNameAndPassword(userName, StringUtil.parse2UUID(password),null);
        if(null == userInfoDomain) {
            throw BusinessException.withErrorCode("NOT_FOUND_USER").withErrorMessageArguments("用户不存在");
        }
        HttpSession session = request.getSession();;
        if(null != userInfoDomain) {
            logger.info("判断用户是否已经登录了");
            if(existedSession(session.getId())) {
                throw BusinessException.withErrorCode("CANNOT_REPEAT_LOGIN").withErrorMessageArguments("请勿重复登录");
            }
            session.setMaxInactiveInterval(sessionExpired * 60);
            session.setAttribute("id",userInfoDomain.getId());
            session.setAttribute("name",userInfoDomain.getName());
            logger.info("将session数据保存到sessionInfo表");
            String sessionInfo = JSONObject.toJSONString(session);
            SessionInfoDomain sessionInfoDomain = new SessionInfoDomain();
            sessionInfoDomain.setSessionId(session.getId());
//            sessionInfoDomain.setSessionInfo(sessionInfo);
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
        return true;
    }

    @Override
    public void loginOut() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        logger.info("用户登出，sessionId={}",request.getSession().getId());
        if(existedSession(request.getSession().getId())) {
            sessionInfoDomainExtensionMapper.deleteBySessionId(request.getSession().getId());
            request.getSession().removeAttribute("id");
            request.getSession().removeAttribute("name");
        }
    }

    /**
     * 判断session是否存在
     * @param sessionId
     * @return
     */
    private boolean existedSession(String sessionId) {
        SessionInfoDomain sessionInfoDomain = sessionInfoDomainExtensionMapper.selectBySessionId(sessionId);
        return null != sessionInfoDomain;
    }

    @Override
    public QueryResult<LoginLogDTO> selectLoginLogList(Integer userId, String appId, Date startTime, Date endTime, Integer pageNum, Integer pageSize) {
        logger.info("查询登录日志，userId={}，appId={}");
        Page<LoginLogDomain> loginLogDomainList = null;
        PageHelper.startPage(pageNum,pageSize);
        if(null != userId) {
            loginLogDomainList =loginLogDomainExtensionMapper.selectUserLoginLog(userId,startTime,endTime);
        }else if(org.springframework.util.StringUtils.hasText(appId)) {
            List<UserInfoDomain> userList = userInfoDomainExtensionMapper.selectUserInfoList(null, appId);
            if(null == userList) {
                return null;
            }
            List<Integer> userIdList = new ArrayList<>(userList.size());
            for (UserInfoDomain userInfo : userList) {
                userIdList.add(userInfo.getId());
            }
            loginLogDomainList = loginLogDomainExtensionMapper.selectUsersLoginLog(userIdList,startTime,endTime);
        } else {
            loginLogDomainList = loginLogDomainExtensionMapper.selectUsersLoginLog(null,startTime,endTime);
        }
        return parseListLogDomain2DTO(loginLogDomainList);
    }

    private QueryResult<LoginLogDTO> parseListLogDomain2DTO(Page<LoginLogDomain> loginLogDomainList) {
        if(CollectionUtils.isEmpty(loginLogDomainList)) {
            return null;
        }
        logger.info("LoginLogDomain 列表转为LoginLogDTO列表");
        List<LoginLogDTO> result = new ArrayList<>(loginLogDomainList.size());
        for (LoginLogDomain domain : loginLogDomainList){
            result.add(parseLogDomain2DTO(domain));
        }
        return new QueryResult<>(loginLogDomainList.getTotal(),result);

    }

    public static LoginLogDTO parseLogDomain2DTO(LoginLogDomain domain) {
        if (domain == null) {
            return null;
        }
        LoginLogDTO loginLogDTO = new LoginLogDTO();
        loginLogDTO.setId(domain.getId());
        loginLogDTO.setType((EnumHelperUtil.getByIntegerTypeCode(EnumLoginType.class,"getValue",Integer.valueOf(domain.getType()))).name());
        loginLogDTO.setResult(EnumHelperUtil.getByIntegerTypeCode(EnumLoginResult.class,"getValue",Integer.valueOf(domain.getResult())).name());
        loginLogDTO.setAddress(domain.getAddress());
        loginLogDTO.setUserName(domain.getUserName());
        loginLogDTO.setUserId(domain.getUserId());
        loginLogDTO.setClientIp(domain.getClientIp());
        loginLogDTO.setLogTime(domain.getCreateTime());
        return loginLogDTO;
    }

    @Override
    public LoginLogDTO selectLastLoginLog(Integer userId, EnumLoginResult result) {
        logger.info("获取用户={}最近一次登录【{}】信息，不包含本次登录信息",userId,result);
        if(null == result) {
            logger.info("默认读取最近一次登录成功的信息");
            result = EnumLoginResult.SUCCESS;
        }
        QueryResult<LoginLogDTO> loginLogList = selectLoginLogList(userId, null, null, null, 1, 2);
        List<LoginLogDTO> items = loginLogList.getItems();
        if(null != items) {
            if(items.size() >1 ) {
                return items.get(1);
            }else {
                logger.info("第一次登录信息，则没有上一次登录信息");
                throw BusinessException.withErrorCode("IS_FIRST_LOGIN").withErrorMessageArguments("第一次登录");
            }
        }else {
            throw BusinessException.withErrorCode("DATA_ERROR").withErrorMessageArguments("查询数据异常");
        }
    }

    @Override
    public Boolean getHttpSessionBySessionId() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String sessionId = request.getSession().getId();
        return existedSession(sessionId);
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
