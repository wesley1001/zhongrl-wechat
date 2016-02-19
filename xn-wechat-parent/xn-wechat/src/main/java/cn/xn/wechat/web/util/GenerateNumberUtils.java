package cn.xn.wechat.web.util;

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
public class GenerateNumberUtils {
    private static final Logger logger = LoggerFactory.getLogger(GenerateNumberUtils.class);

    /**
     * 订单号生成规则 (产品ID+当前时间+时间戳)
     * @param suffix
     * @return
     */
    public static String generateOrderNumber(String suffix) {

        try {
            Thread.sleep(1);
        } catch (Exception e) {
            logger.warn("======>>> generate Order number Err: "+ e.getMessage());
        }
        return DateUtil.DateToString(new Date(), "yyyyMMddHHmmssS")+(suffix == null ? "":suffix);
    }

    /**
     * 工单号生成规则 (自定义前缀+用户id+当前时间)
     * @param prefix
     * @return
     */
    public static String generateJobNumber(String prefix, Long uid) {

    return (StringUtils.isNotEmpty(prefix)) ? prefix+uid+DateUtil.DateToString(new Date(), "yyyyMMddHHmmssS")
            :uid+DateUtil.DateToString(new Date(), "yyyyMMddHHmmssS");
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
        return number.substring(len-6, len);
    }



    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static synchronized String generateCode(boolean numberFlag, int length) {

        String retStr;
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

}
