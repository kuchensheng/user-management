package com.mermaid.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Desription:
 *
 * @author:Hui CreateDate:2018/8/31 0:42
 * version 1.0
 */
@ApiModel
@Data
public class UserInfoDTO {

    @ApiModelProperty(name = "id",value = "用户id")
    private Integer id;

    @ApiModelProperty(name = "name",value = "用户名")
    private String name;

    @ApiModelProperty(name = "age",value = "年龄")
    private String age;

    /**
     * 性别,1-男，0-女
     */
    @ApiModelProperty(name = "sex",value = "性别",allowableValues = "男，女")
    private String sex;

    /**
     * 用户身份标记，1-超级管理员，2-普通管理员，3-普通用户
     */
    @ApiModelProperty(name = "status",value = "身份标识")
    private String status;

    @ApiModelProperty(name = "phone",value = "电话")
    private String phone;

    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;

    /**
     * 资源Id
     */
    @ApiModelProperty(name = "avatarId",value = "用户头像Id")
    private Integer avatarId;

    @ApiModelProperty(name = "qq",value = "qq")
    private String qq;

    /**
     * 注册时间
     */
    @ApiModelProperty(name = "createTime",value = "注册时间")
    private Date createTime;

    @ApiModelProperty(name = "appId",value = "所属应用/公司")
    private String appId;
}
