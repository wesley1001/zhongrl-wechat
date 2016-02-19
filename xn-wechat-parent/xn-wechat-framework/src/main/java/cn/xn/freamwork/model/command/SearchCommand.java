/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.model.command;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * 查询条件公共类
 *
 * @author lcl 2014/09/08.
 * @version 1.0.0
 */
public class SearchCommand {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //开始时间
    protected String tradeStart;
    //结束时间
    protected String tradeEnd;
    //,lastWeek,lastMonth,last2Month,last3Month
    protected String period;

    public static class TimePair {

        public TimePair(Date start, Date end) {
            this.start = start;
            this.end = end;
        }

        public TimePair() {
        }

        private Date start;
        private Date end;

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }


    }

    public TimePair getTimePair(){
        //转换字符类型字符串
       /* Date startDate = StringUtils.isNotEmpty(tradeStart) ?
                internalParseDate(tradeStart + HyUtils.START_TIME_SUFFIX)
                : new Date();
        Date endDate = StringUtils.isNotEmpty(tradeEnd) ?
                internalParseDate(tradeEnd + HyUtils.END_TIME_SUFFIX)
                : new Date();

        //未指定时间查询时
        if (StringUtils.isNotEmpty(period) && !SearchConstant.S_T_CUSTOMDATE.equals(period)) {
            //今天
            if (SearchConstant.S_T_TODAY.equals(period)) {
                startDate = Calendar.getInstance().getTime();
            }
            // 最近一周
            else if (SearchConstant.S_T_LASTWEEK.equals(period)) {
                startDate = HyUtils.later(-7);
                //return new TimePair(HyUtils.later(-7),new Date());
            }
            // 最近一月
            else if (SearchConstant.S_T_LASTMONTH.equals(period)) {
                startDate = HyUtils.later(-30);
                //return new TimePair(HyUtils.later(-30),new Date());
            }
            // 最近两个月
            else if (SearchConstant.S_T_LAST2MONTH.equals(period)) {
                startDate = HyUtils.later(-30*2);
                //return new TimePair(HyUtils.later(-30),new Date());
            }
            // 最近三月
            else if (SearchConstant.S_T_LAST3MONTHS.equals(period)) {
                startDate = HyUtils.later(-30*3);
                //return new TimePair(HyUtils.later(-30*3), new Date());
            }

        }

        startDate = HyUtils.format(
                HyUtils.format(startDate, HyUtils.FORMAT_DATE, true, HyUtils.DATE_FORMAT_START_TIME)
                , HyUtils.FORMAT_TIME);
*//*
        return new TimePair(startDate, endDate);*/
        return null;
    }


    /**
     * 解析日期
     *
     * @param string
     * @return
     */
    protected Date internalParseDate(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        try {
            return DateUtils.parseDate(string, new String[]{"yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd"});
        }
        catch (ParseException e) {
            logger.warn("string[{}] parse to date fail,", string);
            return null;
        }
    }


    public String getTradeStart() {
        return tradeStart;
    }

    public void setTradeStart(String tradeStart) {
        this.tradeStart = tradeStart;
    }

    public String getTradeEnd() {
        return tradeEnd;
    }

    public void setTradeEnd(String tradeEnd) {
        this.tradeEnd = tradeEnd;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
