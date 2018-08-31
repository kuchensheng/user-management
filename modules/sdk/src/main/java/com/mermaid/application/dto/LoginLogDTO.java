package com.mermaid.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Chensheng.Ku
 * @version 创建时间：2018/8/31 12:33
 */
@ApiModel
@Data
public class LoginLogDTO {
    /**
     * 主键
     */
    @ApiModelProperty(name = "id",value = "日志ID")
    private Integer id;

    /**
     * 登陆类型，1-网页登陆，2-微信登陆，3-APP登陆，4-ios登陆
     */
    @ApiModelProperty(name = "type",value = "登录类型")
    private String type;

    /**
     * 登陆结果
     */
    @ApiModelProperty(name = "result",value = "登陆结果")
    private String result;

    /**
     * 登陆地址
     */
    @ApiModelProperty(name = "address",value = "登陆地址，省市区等")
    private String address;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName",value = "用户名")
    private String userName;

    /**
     * 用户Id
     */
    @ApiModelProperty(name = "userId",value = "用户Id")
    private Integer userId;

    /**
     * 客户端IP
     */
    @ApiModelProperty(name = "clientIp",value = "客户端IP")
    private String clientIp;

    /**
     * 登陆时间
     */
    @ApiModelProperty(name = "logTime",value = "登陆时间")
    private Date logTime;
}
