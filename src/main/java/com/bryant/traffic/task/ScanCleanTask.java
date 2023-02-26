package com.bryant.traffic.task;


import com.bryant.traffic.service.impl.LimitServiceImpl;
import com.bryant.traffic.utils.CodeSpringContext;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
//import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component(ScanCleanTask.TASK_NAME)
public class ScanCleanTask extends BaseTask {

    public static final String TASK_NAME = "com.tencent.code.scan.task.ScanCleanTask";

    private static final Logger logger = LoggerFactory.getLogger(ScanCleanTask.class);

    @Scheduled(cron = "*/15 * * * * ?", zone = "GMT+8")
//    @SchedulerLock(name = TASK_NAME, lockAtLeastFor = "10s", lockAtMostFor = "60s")
    public void run() {
        // 1.前置操作
        preHandle();

        // 2.获取bean
        LimitServiceImpl bean = CodeSpringContext.getBean(LimitServiceImpl.class);

        logger.info("get bean = " + bean);

    }

}
