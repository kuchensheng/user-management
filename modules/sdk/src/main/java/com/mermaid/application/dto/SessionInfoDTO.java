package com.mermaid.application.dto;

import lombok.Data;

import java.util.Date;

/**
 * Desription:
 *
 * @author:Hui CreateDate:2018/9/2 22:25
 * version 1.0
 */
@Data
public class SessionInfoDTO {

    private String sessionId;

    /**
     * 失效时间,单位：分钟，默认30分钟失效
     */
    private Integer expire;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户Id
     */
    private Integer userId;
}
