package com.bryant.traffic.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理类
 * 使用@Component标注的类，该类实现 HandlerExceptionResolver接口中的 resolveException()方法
 */
@Component
public class WebExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(WebExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(MessageFormat.format("resolveException, ex={0}", ex));
        //这里可根据不同异常引起的类做不同处理方式
        String exceptionName = ClassUtils.getShortName(ex.getClass());
        if (exceptionName.equals("TestException")) {
            logger.info("TestException...");
        } else if (exceptionName.equals("UndeclaredThrowableException")) {
            logger.info("UndeclaredThrowableException...");
        }

        //向前台返回错误信息
        Map model = new HashMap();
        model.put("stackTrace", ex.getStackTrace());
        model.put("errorMessage", ex.getMessage());
        model .put ("url", request.getRequestURI());
        return new ModelAndView();
    }
}
