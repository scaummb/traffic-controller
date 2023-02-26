package com.bryant.traffic.config.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SpringContext implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(SpringContext.class);

    private ApplicationContext applicationContext;
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("setApplicationContext...");
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }
}
