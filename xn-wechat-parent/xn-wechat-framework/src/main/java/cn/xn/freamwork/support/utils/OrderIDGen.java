package cn.xn.freamwork.support.utils;

import org.slf4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单号生成器
 * @version 1.0.0
 */

public class OrderIDGen
{
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/**
	 * 格式化订单
	 */
	private final static String DATE_FORMAT_STRING = "yyyyMMddHHmmssSSS";
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private int maxIndex = 10;

	private String host = "0";  //主机号，主要用于集群区分

	public String generate(String suffix, String channel, Date date) {
        if (suffix == null)
            suffix = "";

		if (channel == null)
			channel = "0";

        try {
            Thread.sleep(1);
        } catch (Exception e) {
            logger.error("======>>> generate Order number Err: "+ e.getMessage());
        }

		return suffix + channel + getHost() + getSequence() + getDateString(date);
	}


	public static String generate() {
		return new OrderIDGen().generate(null, null, null);
	}

	private String getDateString(Date date) {
		if (date == null) {
			date = new Date();
		}
		return new SimpleDateFormat(DATE_FORMAT_STRING).format(date);
	}


	private int getSequence() {
		int intValue = atomicInteger.incrementAndGet();
		if (intValue == maxIndex) {
			atomicInteger.set(intValue);
		}
		return intValue;
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setMaxIndex(int maxIndex) {
		this.maxIndex = maxIndex;
	}


    public static void main (String[] args) {
        OrderIDGen o = new OrderIDGen();
        System.out.println(o.generate().length());

    }

}

