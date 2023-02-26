package com.bryant.traffic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/**
 * 统一日志工具类
 */
public class LoggerUtils {

    private static String pattern = "\\{\\}";

    private static String BRACE = "{}";

    /**
     * Debug 输出
     *
     * @param logger  日志logger
     * @param message 输出信息
     */
    public static void debug(Logger logger, String message) {
        logger.debug(message);
    }

    /**
     * Debug 输出
     *
     * @param logger    日志logger
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtDebug(Logger logger, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = buildFormatString(fmtString, value);
        }
        debug(logger, fmtString);
    }

    /**
     * Debug 输出
     *
     * @param logger  日志logger
     * @param message 输出信息
     */
    public static void info(Logger logger, String message) {
        logger.info(message);
    }

    /**
     * Info 输出
     *
     * @param logger    日志logger
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtInfo(Logger logger, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = buildFormatString(fmtString, value);
        }
        info(logger, fmtString);
    }

    /**
     * Warn 输出
     *
     * @param logger  日志logger
     * @param message 输出信息
     */
    public static void warn(Logger logger, String message) {
        logger.warn(message);
    }

    /**
     * Warn 输出
     *
     * @param logger    日志logger
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtWarn(Logger logger, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = buildFormatString(fmtString, value);
        }
        warn(logger, fmtString);
    }


    /**
     * Error 输出
     *
     * @param logger  日志logger
     * @param message 输出信息
     * @param e       异常类
     */
    public static void error(Logger logger, String message, Exception e) {

        if (null == e) {
            error(logger, message);
            return;
        } else {
            logger.error(message, e);
        }
    }

    /**
     * Error 输出
     *
     * @param logger  日志logger
     * @param message 输出信息
     * @param e       异常类
     */
    public static void error(Logger logger, String message, Throwable e) {
        if (null == e) {
            error(logger, message);
            return;
        } else {
            logger.error(message, e);
        }
    }

    /**
     * Error 输出
     *
     * @param logger    日志logger
     * @param errorCode 错误信息
     * @param message   输出信息
     * @param e         异常类
     */
    public static void error(Logger logger, String errorCode, String message, Throwable e) {
        if (null == e) {
            error(logger, errorCode, message);
            return;
        } else {
            logger.error(message, e);
        }
    }

    /**
     * Error 输出
     *
     * @param logger  日志logger
     * @param message 输出信息
     */
    public static void error(Logger logger, String message) {
        logger.error(message);
    }

    /**
     * Error 输出
     *
     * @param logger    日志logger
     * @param errorCode 错误信息
     * @param message   输出信息
     */
    public static void error(Logger logger, String errorCode, String message) {
        logger.error(message);
    }

    /**
     * 异常填充值输出
     *
     * @param logger    日志logger
     * @param throwable 异常信息
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtError(Logger logger, Throwable throwable, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = buildFormatString(fmtString, value);
        }
        error(logger, fmtString, throwable);
    }

    /**
     * 异常填充值输出
     *
     * @param logger    日志logger
     * @param throwable 异常信息
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtError(String errorCode, Logger logger, Throwable throwable, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = buildFormatString(fmtString, value);
        }
        error(logger, errorCode, fmtString, throwable);
    }

    /**
     * 异常填充值输出
     *
     * @param logger    日志logger
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtError(Logger logger, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = buildFormatString(fmtString, value);
        }
        error(logger, fmtString);
    }

    /**
     * 异常填充值输出
     *
     * @param logger    日志logger
     * @param errorCode 错误描述
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtError(String errorCode, Logger logger, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = buildFormatString(fmtString, value);
        }
        error(logger, errorCode, fmtString);
    }

    private static String buildFormatString(String fmtString, Object[] arguments) {
        if (fmtString.indexOf(BRACE) >= 0) {
            // fmtString中有包含{}
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(fmtString);
            int i = 0; // arguments
            // 将fmtString中的{}替换为arguments
            while (m.find() && i < arguments.length) {
                fmtString = fmtString.replaceFirst(pattern, String.valueOf(arguments[i++]));
            }
            return fmtString;
        }

        fmtString = String.format(fmtString, arguments);
        return fmtString;
    }
}
