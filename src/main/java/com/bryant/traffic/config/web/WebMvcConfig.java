package com.bryant.traffic.config.web;

import com.bryant.traffic.config.interceptor.WebHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 1、继承了WebMvcConfigurationSupport
 * 2、然后通过重写以下配置方法 :“dCorsMappings()、 addFormatters()、 addlnterceptors()、 addViewControllers()实现自定义的配置 。
 */
@Configuration //Spring配置类
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index");
        super.addInterceptors(registry);
    }
}
