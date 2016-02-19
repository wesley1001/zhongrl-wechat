package cn.xn.wechat.activity.util;

import java.io.IOException;
import java.util.Date;

import cn.xn.wechat.activity.json.JsonMessage;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;

public class Base64Util {

	public static String encode(String src) {
		return new sun.misc.BASE64Encoder().encode(src.getBytes());
	}

	public static String decode(String src) {
		try {
			byte[] data = new sun.misc.BASE64Decoder().decodeBuffer(src);
			return new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return src;
	}

	public static void main(String[] args) {

		 System.out.println(decode("6KW/6KW/"));
		
		
		
	}
}
