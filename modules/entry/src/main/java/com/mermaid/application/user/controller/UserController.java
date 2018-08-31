package com.mermaid.application.user.controller;

import com.mermaid.application.constant.EnumSex;
import com.mermaid.application.constant.EnumUserStatus;
import com.mermaid.application.dto.UserInfoDTO;
import com.mermaid.application.user.service.UserService;
import com.mermaid.framework.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Desription:
 *
 * @author:Hui CreateDate:2018/8/31 1:30
 * version 1.0
 */
@RestController
@Api(value = "用户中心管理",tags = "用户中心管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "/app/user",method = RequestMethod.POST)
    public APIResponse<Integer> createUser(
            @ApiParam(required = true,name = "name",value = "用户名") @RequestParam(value = "name") String name,
            @ApiParam(required = true,name = "password",value = "密码") @RequestParam(value = "password") String password,
            @ApiParam(name = "age",value = "年龄") @RequestParam(required = false,value = "age") String age,
            @ApiParam(required = true,name = "status",value = "角色") @RequestParam(value = "status")EnumUserStatus status,
            @ApiParam(required = true,name = "sex",value = "性别") @RequestParam(value = "sex")EnumSex sex,
            @ApiParam(name = "phone",value = "电话") @RequestParam(required = false,value = "phone") String phone,
            @ApiParam(name = "email",value = "邮箱") @RequestParam(required = false,value = "email") String email,
            @ApiParam(name = "qq",value = "qq") @RequestParam(required = false,value = "qq") String qq,
            @ApiParam(name = "avatarId",value = "用户头像") @RequestParam(required = false,value = "avatarId") Integer avatarId,
            @ApiParam(name = "appId",value = "应用Id") @RequestParam(required = false,value = "appId") String appId,
            @ApiParam(name = "createTime",value = "创建时间") @RequestParam(required = false,value = "createTime")Date createTime
            ) {
        return APIResponse.success(userService.createUser(name,password,age,sex,status,phone,email,avatarId,qq,createTime,appId));
    }

    @ApiOperation(value = "更新用户信息")
    @RequestMapping(value = "/app/user/{userId}",method = RequestMethod.PUT)
    public APIResponse<Boolean> updateUserInfo(
            @ApiParam(required = true,name = "userId",value = "用户Id") @PathVariable(value = "userId") Integer userId,
            @ApiParam(required = true,name = "name",value = "用户名") @RequestParam(value = "name") String name,
            @ApiParam(required = true,name = "password",value = "密码") @RequestParam(value = "password") String password,
            @ApiParam(name = "age",value = "年龄") @RequestParam(required = false,value = "age") String age,
            @ApiParam(required = true,name = "status",value = "角色") @RequestParam(value = "status")EnumUserStatus status,
            @ApiParam(required = true,name = "sex",value = "性别") @RequestParam(value = "sex")EnumSex sex,
            @ApiParam(name = "phone",value = "电话") @RequestParam(required = false,value = "phone") String phone,
            @ApiParam(name = "email",value = "邮箱") @RequestParam(required = false,value = "email") String email,
            @ApiParam(name = "qq",value = "qq") @RequestParam(required = false,value = "qq") String qq,
            @ApiParam(name = "avatarId",value = "用户头像") @RequestParam(required = false,value = "avatarId") Integer avatarId,
            @ApiParam(name = "appId",value = "应用Id") @RequestParam(required = false,value = "appId") String appId,
            @ApiParam(name = "updateTime",value = "更新时间") @RequestParam(required = false,value = "updateTime") Date updateTime
    ) {
        return APIResponse.success(userService.updateUser(userId,name,password,age,sex,status,phone,email,avatarId,qq,updateTime,appId));
    }

    @ApiOperation(value = "删除用户信息")
    @RequestMapping(value = "/app/user/{userId}",method = RequestMethod.DELETE)
    public APIResponse<Boolean> deleteUserInfo(
            @ApiParam(required = true,name = "userId",value = "用户id") @PathVariable(value = "userId") Integer userId
    ) {
        return APIResponse.success(userService.deleteUsers(new Integer[]{userId}));
    }

    @ApiOperation(value = "批量删除用户信息")
    @RequestMapping(value = "/app/user/batch",method = RequestMethod.DELETE)
    public APIResponse<Boolean> deleteUsers(
            @ApiParam(required = true,name = "userIds",value = "用户Id数组") @RequestParam(value = "userIds") Integer[] userIds
    ) {
        return APIResponse.success(userService.deleteUsers(userIds));
    }

    @ApiOperation(value = "获取Id用户详细信息")
    @RequestMapping(value = "/app/user/{userId}",method = RequestMethod.GET)
    public APIResponse<UserInfoDTO> getUserInfoDTO(
            @ApiParam(required = true,name = "userId",value = "用户Id") @PathVariable(value = "userId") Integer userId
    ) {
        return APIResponse.success(userService.selectUserInfoDetail(userId));
    }

    @ApiOperation(value = "批量获取用户详情")
    @RequestMapping(value = "/app/user/list",method = RequestMethod.GET)
    public APIResponse<List<UserInfoDTO>> getUserInfoList(
            @ApiParam(required = true,name = "userIds",value = "用户Id数组") @RequestParam(value = "userIds") Integer[] userIds
    ) {
        return APIResponse.success(userService.selectUserInfos(userIds,null));
    }

    @ApiOperation(value = "获取应用下的用户列表")
    @RequestMapping(value = "/app/user/list/{appId}",method = RequestMethod.GET)
    public APIResponse<List<UserInfoDTO>> getUserInfoListByAppId(
            @ApiParam(required = true,name = "appId",value = "应用Id") @PathVariable(value = "appId") String appId
    ) {
        return APIResponse.success(userService.selectUserInfos(null,appId));
    }
}
