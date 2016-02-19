package cn.xn.freamwork.support.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

/**
 * 相关功能号码生成规则工具类
 * @author lcl 2014/08/01
 * @version 1.0.0
 */
public class GenerateNumberUtils
{
    private static final Logger logger = LoggerFactory.getLogger(GenerateNumberUtils.class);

    //用户ID前缀, 可配置 (意义：可分批区分编号群体)
    private static String prefix = "10";

    /**
     * 订单号生成规则 (产品ID+当前时间+时间戳)
     * @param suffix
     * @return
     */
    public static String generateOrderNumber(String suffix) {

        try {
            Thread.sleep(1);
        } catch (Exception e) {
            logger.error("======>>> generate Order number Err: "+ e.getMessage());
        }

        return DateUtils.format(new Date(), "yyyyMMddHHmmssS")+(suffix == null ? "":suffix);
    }

    /**
     * 工单号生成规则 (自定义前缀+用户id+当前时间)
     * @param prefix
     * @return
     */
    public static String generateJobNumber(String prefix, Long uid) {

    return (StringUtils.isNotEmpty(prefix)) ? prefix+uid+DateUtils.format(new Date(), "yyyyMMddHHmmssS")
            :uid+DateUtils.format(new Date(), "yyyyMMddHHmmssS");
    }

    /**
     * 生成KEY
     * @param prefix
     * @return
     */
    public static String generateKey(String prefix) {

        return StringUtils.isNotEmpty(prefix) ?
                prefix+UUID.randomUUID().toString().replace("-", ""):UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成用户ID
     * @return
     */
    public static String generateUID() {
        String number = generateOrderNumber(null);
        int len = number.length();
        return getPrefix()+number.substring(len-6, len);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public static String getPrefix() {
        return prefix;
    }

    public static void main(String [] arguments) {

        /*String orderNo = generateOrderNumber("");
        System.out.println(orderNo);
        System.out.println(orderNo.length());
        System.out.println(generateOrderNumber(""));

        System.out.println(generateUID());*/

        //System.out.println(generateJobNumber("1", 0l));

        System.out.println(generateUID());

    }

}
