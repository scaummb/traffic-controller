package com.bryant.traffic.service;

import com.google.common.util.concurrent.RateLimiter;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LimitServiceImpl implements LimitService{

    /**
     * 业务粒度限流
     */
    private static final Integer MAX_LIMIT = 5;

    /**
     * 全局限流
     */
    private static final Integer GLOBAL_MAX_LIMIT = 2;

    /**
     * dashboard搜索限流 （每秒）
     */
    public static final int USER_ES_DASHBOARD_SEARCH_RATE_LIMIT_PER_SECOND = 20;

    /**
     * 项目搜索限流 （每秒）
     */
    public static final int USER_ES_PROJECT_SEARCH_RATE_LIMIT_PER_SECOND = 30;

    @Override
    public void limit() {
        long t1 = System.currentTimeMillis();
        log.info(MessageFormat.format("limit thread id = {0}", Thread.currentThread().getId()));
        RateLimiter rateLimiter = RateLimiter.create(MAX_LIMIT);
        rateLimiter.acquire();
        log.info(MessageFormat.format("limit thread id = {0}, cost = {1}", Thread.currentThread().getId(), (System.currentTimeMillis() - t1)));
    }
}
