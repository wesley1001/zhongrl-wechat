package cn.xn.freamwork.support.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil
{

    public static void main(String[] args)
    {
        System.out.println(getLastMonthThisDay());
        System.out.println(getThisDay());
        System.out.println(getLastWeekThisDay());
        System.out.println(getLast3MonthThisDay());
        System.out.println(getDateFromStr(getLast3MonthThisDay(),null,0).toLocaleString());
        System.out.println(getDateFromStr(getLast3MonthThisDay(),null,1).toLocaleString());
    }


    public static String DATE_FORMAT_STRING = "yyyy-MM-dd";
    public static String TIME_FORMAT_STRING = "HH:mm:ss";

    /**
     * 获取上月今天的日期
     *
     * @return
     */
    public static String getLastMonthThisDay()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return new SimpleDateFormat(DATE_FORMAT_STRING).format(c.getTime());
    }

    /**
     * 获取上月今天的日期
     *
     * @return
     */
    public static String getThisDay()
    {
        Calendar c = Calendar.getInstance();
        return new SimpleDateFormat(DATE_FORMAT_STRING).format(c.getTime());
    }


    public static String getLastWeekThisDay()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        return new SimpleDateFormat(DATE_FORMAT_STRING).format(c.getTime());
    }

    public static String getLast3MonthThisDay()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -3);
        return new SimpleDateFormat(DATE_FORMAT_STRING).format(c.getTime());
    }

    public static Date getDateFromStr(String time, String formateStr,Integer i)
    {
        if (i == 0)
        {
            time = time + " 00:00:00";
        }
        if (i == 1)
        {
            time = time + " 23:59:59";
        }
        if (formateStr == null)
            formateStr = DATE_FORMAT_STRING + " "+ TIME_FORMAT_STRING;
        SimpleDateFormat format = new SimpleDateFormat(formateStr);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
