# 用户中心管理系统
## Introduction
- 提供用户身份认证
- 变更token
- 手机认证
- 提供用户信息查询
- 提供SSO接口

## 接入
接入方式如下
```xml
 demo地址：后续待补充
```

引入包
```xml
<dependency>
　　<groupId>com.mermaid.uc.sso</groupId>
　　<artifactId>sso-client-core</artifactId>
　　<version>1.0</version>
</dependency>
```

配置参数
```
1、com.mermaid.sso.passport.url=http://10.86.98.40:3002/
SSO统一登录页面地址，根据不同环境进行调整

2、com.mermaid.sso.service.prefix-url=http://10.86.98.40:19700/
token 校验接口地址，根据不同环境进行调整


```
