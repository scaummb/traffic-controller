package com.bryant.traffic.config.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class HelloListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(HelloListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("HelloListener contextInitialized");
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("HelloListener contextDestroyed");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
