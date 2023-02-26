package com.bryant.traffic.config.interceptor;

import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class WebHandlerInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(WebHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(MessageFormat.format("WebHandlerInterceptor preHandle... url = {}", request.getRequestURL()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info(MessageFormat.format("WebHandlerInterceptor postHandle... url = {}", request.getRequestURL()));
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
