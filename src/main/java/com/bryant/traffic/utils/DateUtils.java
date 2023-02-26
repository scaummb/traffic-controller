package com.bryant.traffic.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class DateUtils {

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMM = "yyyyMM";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY = "yyyy";

    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getSimpleDate(Date value) {
        return getSimpleDate(value, DEFAULT_FORMAT);
    }

    public static final String[] patterns = new String[]{YYYYMMDDHHMMSS, YYYYMMDDHHMM, YYYY_MM, YYYY_MM_DD, YYYYMM, YYYYMMDD, YYYY, YYYYMMDDHHMMSSSSS, DEFAULT_FORMAT};
    /**
     * 比较date1与date2的大小
     * 在Date.compare()基础上增加了判空处理
     *
     * @param date1 待比较日期1
     * @param date2 待比较日期2
     * @return 比较结果
     */
    public static int compare(Date date1, Date date2) {
        if (date1 == date2) {
            return 0;
        }

        if (date1 == null) {
            return -1;
        }

        if (date2 == null) {
            return 1;
        }

        return date1.compareTo(date2);
    }

    /**
     * 转换Date类型为字符串类型
     */
    public static String getSimpleDate(Date value, String pattern) {
        return getSimpleDate(value, pattern, Locale.US);
    }

    public static String getSimpleDate(Date value, String pattern, Locale locale) {
        if (Objects.isNull(value)) {
            value = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
        return formatter.format(value);
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String formatCurrentByDefault() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.YYYYMMDDHHMMSSSSS);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 按指定格式格式化当前时间
     *
     * @param format
     * @return
     */
    public static String formatCurrent(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String formatDate(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        if (StringUtils.isEmpty(formatStr)) {
            formatStr = DEFAULT_FORMAT;
        }
        DateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /**
     * 格式化日期
     *
     * @param time
     * @param formatStr
     * @return
     */
    public static String formatDate(Long time, String formatStr) {
        if (time == null) {
            return null;
        }
        Date date = new Date(time);
        return formatDate(date, formatStr);
    }

    /**
     * 用来转换日期格式的公用方法
     *
     * @param dateString       需要转换的时间
     * @param sourceDateFormat 原时间格式
     * @param destDateFormat   目的时间格式
     * @return 转换后的时间
     * @throws ParseException 日期格式错误时抛出的异常
     */
    public static String formatDate(String dateString, String sourceDateFormat, String destDateFormat) {
        DateFormat dfOne = new SimpleDateFormat(sourceDateFormat);
        DateFormat dfTwo = new SimpleDateFormat(destDateFormat);
        Date date = null;
        try {
            date = dfOne.parse(dateString);
        } catch (ParseException e) {
            log.error("formatDate by dateString is error. dateString is " + dateString + ", sourceDateFormat is " +
                    sourceDateFormat + ", destDateFormat is " + destDateFormat, e);
            return null;
        }
        return dfTwo.format(date);
    }

    /**
     * 格式化时间字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parseDate(String date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        if (StringUtils.isNotEmpty(date)) {
            try {
                return format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace(); // To change body of catch statement use
                // File | Settings | File Templates.
                return null;
            }
        }
        return null;
    }

    /**
     * 获取两个月份之间的所有月份(含跨年)
     * @param fromDate
     * @param toDate
     * @return yyyy-MM格式字符串list
     */
    public static List<String> getMonthBetween(Long fromDate, Long toDate, String format) {
        if (fromDate == null || toDate == null) {
            return null;
        }
        return getMonthBetween(new Date(fromDate), new Date(toDate), format);
    }

    /**
     * 获取两个月份之间的所有月份(含跨年)
     * @param fromDate
     * @param toDate
     * @return yyyy-MM格式字符串list
     */
    public static List<String> getMonthBetween(Date fromDate, Date toDate, String format) {
        if (fromDate == null || toDate == null) {
            return null;
        }
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(fromDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(toDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        // 对月份排序
        Collections.sort(result);
        return result;
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHourOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static Date getNextMonth(Date sourceDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 当前日期相加或相减N天所得日期（+,-）操作,输入一个日期得到天数加减后的日期。
     *
     * @param
     * @return
     */
    public static Date dsDay_Date(Date date, Integer days) {
        return add(date, Calendar.DATE, days);
    }

    /**
     * 当前日期相加或相减N月所得日期（+,-）操作。
     *
     * @param
     * @return
     */
    public static Date dsMonth_Date(Date date, Integer months) {
        return add(date, Calendar.MONTH, months);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 校验字符串是否是时间格式
     * @param dateStr
     * @return
     */
    public static boolean checkIsDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return false;
        }
        try {
            Date date = org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, patterns);
            return date != null ? true : false;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 获取当天的结束时间(默认是8时区)
     * @param time
     * @param isZeroTimeZoneTime
     * @return
     */
    public static Long endOfTheDay(Long time, Boolean isZeroTimeZoneTime) {
        if (time == null) {
            return null;
        }
        Date date = new Date(time);
        Date endOfTheDay = endOfTheDay(date, isZeroTimeZoneTime);
        return endOfTheDay.getTime();
    }

    /**
     * 获取当天的结束时间(默认是8时区)
     *
     * @param date
     * @param isZeroTimeZoneTime
     * @return
     */
    public static Date endOfTheDay(Date date, Boolean isZeroTimeZoneTime) {
        if (date == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        if (isZeroTimeZoneTime && date.getHours() >= 16) {
            now.set(Calendar.DATE, now.get(Calendar.DATE) + 1);
        }
        now.set(Calendar.HOUR_OF_DAY, isZeroTimeZoneTime ? 15 : 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        return now.getTime();
    }

    /**
     * 获取当天的开始时间
     *
     * @param time
     * @return
     */
    public static Long startOfTheDay(Long time) {
        if (time == null) {
            return null;
        }
        Date date = new Date(time);
        Date startOfTheDay = startOfTheDay(date);
        return startOfTheDay.getTime();
    }

    /**
     * 获取当天的开始时间
     *
     * @param date
     * @return
     */
    public static Date startOfTheDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
