package com.bryant.traffic.config.filter;

import java.io.IOException;
import java.text.MessageFormat;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreHandlerFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(PreHandlerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info(MessageFormat.format("PreHandlerFilter init... params = {0}", filterConfig.getServletContext().getInitParameterNames()));
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info(MessageFormat.format("PreHandlerFilter doFilter... params = {0}", servletRequest.getParameterMap()));
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
