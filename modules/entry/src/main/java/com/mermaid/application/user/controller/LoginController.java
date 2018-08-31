package com.mermaid.application.user.controller;

import com.mermaid.application.constant.EnumLoginResult;
import com.mermaid.application.dto.LoginLogDTO;
import com.mermaid.application.user.service.LoginService;
import com.mermaid.framework.mvc.APIResponse;
import com.mermaid.framework.mvc.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Chensheng.Ku
 * @version 创建时间：2018/8/31 15:58
 */
@RestController
@Api(value = "登录信息管理",tags = "用户中心管理")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "检查session是否还存在")
    @RequestMapping(value = "/app/user/session/exist",method = RequestMethod.GET)
    public APIResponse<Boolean> checkSession() {
        return APIResponse.success(loginService.getHttpSessionBySessionId());
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/app/user/login",method = RequestMethod.POST)
    public APIResponse<HttpSession> login(
            @ApiParam(required = true,name = "name",value = "用户名") @RequestParam(value = "name") String name,
            @ApiParam(required = true,name = "password",value = "密码") @RequestParam(value = "password") String password,
            @ApiParam(name = "loinTime",value = "登录时间") @RequestParam(required = false,value = "loginTime") Date loginTime,
            @ApiParam(name = "appId",value = "应用Id") @RequestParam(required = false,value = "appId") String appId
    ) {
        return APIResponse.success(loginService.login(name,password,null,appId));
    }

    @ApiOperation(value = "登出")
    @RequestMapping(value = "/app/user/login",method = RequestMethod.DELETE)
    public APIResponse loginOut() {
        loginService.loginOut();
        return APIResponse.success();
    }

    @ApiOperation(value = "上一次登录信息")
    @RequestMapping(value = "/app/login/last/{userId}",method = RequestMethod.GET)
    public APIResponse<LoginLogDTO> getLastLoginInfo(
            @ApiParam(required = true,name = "userId",value = "用户id") @PathVariable(value = "userId") Integer userId,
            @ApiParam(name = "result",value = "状态") @RequestParam(value = "result",required = false,defaultValue = "SUCCESS")EnumLoginResult result
            ) {
        return APIResponse.success(loginService.selectLastLoginLog(userId,result));
    }

    @ApiOperation(value = "获取登陆信息列表")
    @RequestMapping(value = "/app/login/list",method = RequestMethod.GET)
    public APIResponse<QueryResult<LoginLogDTO>> getLoginInfoList(
            @ApiParam(name = "userId",value = "用户ID") @RequestParam(required = false,value = "userId") Integer userId,
            @ApiParam(name = "appId",value = "应用Id") @RequestParam(required = false,value = "appId") String appId,
            @ApiParam(name = "startTime",value = "开始时间") @RequestParam(required = false,value = "startTime") Date startTime,
            @ApiParam(name = "endTime",value = "结束时间") @RequestParam(required = false,value = "endTime") Date endTime,
            @ApiParam(name = "pageNum",value = "页码") @RequestParam(required = false,value = "pageNum",defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize",value = "页面大小") @RequestParam(required = false,value = "pageSize",defaultValue = "10") Integer pageSize
    ) {
        return APIResponse.success(loginService.selectLoginLogList(userId,appId,startTime,endTime,pageNum,pageSize));
    }
}
