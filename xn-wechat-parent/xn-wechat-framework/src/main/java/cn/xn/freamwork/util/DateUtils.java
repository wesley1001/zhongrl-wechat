/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换工具类
 *
 * @author lcl 2014/08/21
 * @version 1.0.0
 */
public class DateUtils
{

    public static final int overTime = 5;

    public static final String FORMAT_CURRENT_DAY  = "yyyyMMdd";

    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static final String FORMAT_DATE_CN = "yyyy 年 MM 月 dd 日";

    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";

    //时间以yyyyMMDDHHmmss的方式表示
    public static final String FORMAT_PAY = "yyyyMMddHHmmss";

    public static final String START_TIME_SUFFIX = " 00:00:00";
    public static final String END_TIME_SUFFIX = " 23:59:59";

    private static SimpleDateFormat sf = null;


    public static String format(Date date, String format) {
        if (null == date)
            return null;

        sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    public static Date format(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr))
            return null;

        try{
            sf = new SimpleDateFormat(format);
            return sf.parse(dateStr);
        } catch (ParseException e) {
        }

        return null;
    }

    /**
     * 指定时间处理
     * @param time
     * @param day
     * @return
     */
    public static Date later(Date time, int day){
        Calendar cal=Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 开始结束时间拼接
     * @param date
     * @param format
     * @param suffixFlag 是否添加时间后缀
     * @param timeFlag (1/2,开始时间/结束时间)
     * @return String(Date)
     */
    public static String format(Date date, String format, boolean suffixFlag, int timeFlag) {
        if (null == date)
            return null;

        sf = new SimpleDateFormat(format);
        return suffixFlag ? (timeFlag == 1 ? sf.format(date)+START_TIME_SUFFIX:sf.format(date)+END_TIME_SUFFIX)
                :sf.format(date);
    }

    public static String formatMill(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(date);
    }


    /**
     * 超时校验
     * @param time
     * @param overtime
     * @return
     */
    public static boolean verifyOvertime(Long time, int overtime) {
        if (overtime == 0)
            overtime = overTime;

        //取出时间
        long timeNow = System.currentTimeMillis();
        long n = (timeNow - time) / 1000 / 60;
        //超时
        if (n > overtime)
            return true;

        return false;
    }

    public static void main(String[] args) {

    }

}
