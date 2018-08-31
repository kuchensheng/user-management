package com.mermaid.application.user.controller;

import com.mermaid.application.user.service.LoginService;
import com.mermaid.framework.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
