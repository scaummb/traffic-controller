package com.bryant.traffic.config.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnviromentContext implements EnvironmentAware {
    private Logger logger = LoggerFactory.getLogger(EnviromentContext.class);
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        logger.info("setEnvironment...");
        this.environment = environment;
    }
}
