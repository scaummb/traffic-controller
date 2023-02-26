package com.bryant.traffic.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 不用类似new ClassPathXmlApplicationContext()的方式，从已有的spring上下文取得已实例化的bean。
 * 通过ApplicationContextAware接口进行实现。
 *
 * 当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext中的所有bean。
 * 换句话说，就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象。
 *
 * 实现原理：
 *      加载Spring配置文件时，如果Spring配置文件中所定义的Bean类实现了ApplicationContextAware 接口，
 *      那么在加载Spring配置文件时，会自动调用ApplicationContextAware 接口中的 #setApplicationContext(ApplicationContext applicationContext) 方法
 *
 * 坑：定时任务是没办法获取到项目所在Spring容器启动之后的ApplicationContext。
 */
@Component
public class CodeSpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CodeSpringContext.applicationContext = applicationContext;
        CodeSpringContext.environment = applicationContext.getEnvironment();
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        if(applicationContext==null){
            System.out.println("applicationContext是空的");
        }else{
            System.out.println("applicationContext不是空的");
        }
        return applicationContext.getBean(clazz);
    }

}
