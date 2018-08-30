package com.mermaid.application.api;

import com.mermaid.application.constant.EnumSex;
import com.mermaid.application.constant.EnumUserStatus;
import com.mermaid.framework.mvc.APIResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Desription:
 *
 * @author:Hui CreateDate:2018/8/31 1:39
 * version 1.0
 */
@FeignClient("user-management")
public interface UserCloudService {

    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "/app/user",method = RequestMethod.POST)
    APIResponse<Integer> createUser(
            @ApiParam(required = true,name = "name",value = "用户名") @RequestParam(value = "name") String name,
            @ApiParam(required = true,name = "password",value = "密码") @RequestParam(value = "password") String password,
            @ApiParam(name = "age",value = "年龄") @RequestParam(required = false,value = "age") String age,
            @ApiParam(required = true,name = "status",value = "角色") @RequestParam(value = "status")EnumUserStatus status,
            @ApiParam(required = true,name = "sex",value = "性别") @RequestParam(value = "sex")EnumSex sex,
            @ApiParam(name = "phone",value = "电话") @RequestParam(required = false,value = "phone") String phone,
            @ApiParam(name = "email",value = "邮箱") @RequestParam(required = false,value = "email") String email,
            @ApiParam(name = "qq",value = "qq") @RequestParam(required = false,value = "qq") String qq,
            @ApiParam(name = "avatarId",value = "用户头像") @RequestParam(required = false,value = "avatarId") Integer avatarId,
            @ApiParam(name = "appId",value = "应用Id") @RequestParam(required = false,value = "appId") String appId
    );
}
