package cn.xn.wechat.activity.util;

import java.util.UUID;

public class IdUtil {

	/**
	 * 生成UUID
	 */
	public static String generateUUID() {
		return new StringBuffer().append(
				UUID.randomUUID().toString().replaceAll("-", "")).toString();
	}
	 
}
