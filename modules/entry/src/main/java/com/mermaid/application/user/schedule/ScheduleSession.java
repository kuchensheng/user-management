package com.mermaid.application.user.schedule;

import com.mermaid.application.user.model.SessionInfoDomain;
import com.mermaid.application.user.service.LoginService;
import com.mermaid.framework.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Desription:
 * 采用redis实现分布式锁，保证总是只有一个线程去轮询删除session信息
 * 避免并发问题
 * @author:Hui CreateDate:2018/9/1 10:47
 * version 1.0
 */
@Component
public class ScheduleSession {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleSession.class);

    private static final String THREAD_LOCK="threadLock";
    @Autowired
    private RedisService redisService;

    @Autowired
    private LoginService loginService;
    /**
     * 设定定时执行时长，10s
     */
    @Scheduled(fixedDelay = 10000)
    public void clearSession() {
        logger.info("启动session轮询清理进程");
        //判断是否已经有线程在运行
        if(!redisService.exists(THREAD_LOCK)) {
            try {
                logger.info("启动轮询,启动轮询前加锁");
                redisService.lock(THREAD_LOCK,10);
                List<SessionInfoDomain> sessionInfoDomains = loginService.selectAllSessionInfo();
                if(null != sessionInfoDomains && sessionInfoDomains.size() > 0) {
                    for (SessionInfoDomain sessionInfo : sessionInfoDomains) {
                        Date updateTime = sessionInfo.getUpdateTime();
                        if(null != updateTime) {
                            long createTimeLong = updateTime.getTime();
                            long currentTimeMillis = System.currentTimeMillis();
                            if((currentTimeMillis - createTimeLong) > sessionInfo.getExpire() * 60 * 1000) {
                                logger.info("session超时，将失效，被删除，sessionId={}",sessionInfo.getSessionId());
                                loginService.deleteById(sessionInfo.getId());
                            }
                        }else {
                            logger.info("session没有创建时间，视为脏数据，删除，sessionId={}",sessionInfo.getSessionId());
                            loginService.deleteById(sessionInfo.getId());
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("轮询异常",e);
            } finally {
                logger.info("执行结束解锁");
                redisService.unlock(THREAD_LOCK);
            }
        } else {
            logger.info("已有线程在执行，等待下次轮询");
        }
    }
}
