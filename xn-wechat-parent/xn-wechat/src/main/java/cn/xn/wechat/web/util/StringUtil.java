package cn.xn.wechat.web.util;

import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONObject;

public class StringUtil {

	/**
	 * @Title: randomClickNumber
	 * @Description: 随机数字 0 - 40
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int randomClickNumber() {
		int random = new Random().nextInt(40);
		if (random == 0) {
			random++;
		}
		return random;
	}
	
	/**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
 
    }
    

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
	
	/**
	 * 替换特殊字符
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str)   throws   PatternSyntaxException   {     
       // 只允许字母和数字       
       // String   regEx  =  "[^a-zA-Z0-9]";                     
       // 清除掉所有特殊字符  
	   str = str.trim();
	   String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？? ]";  
	   Pattern   p   =   Pattern.compile(regEx);     
	   Matcher   m   =   p.matcher(str);     
	   return   m.replaceAll("").trim();     
   } 

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @Title: getRandomString
	 * @Description: 随机字符串
	 * @param @param length
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * @Title: getJson
	 * @Description: 组装返回格式
	 * @param @param code
	 * @param @param msg
	 * @param @param date
	 * @param @return
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject getJson(int code, String msg, JSONObject data) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		if (data != null) {
			try {
				json.put("data", data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	public static void main(String[] args) {
		System.out.println(getRandomString(23));
	}

	/*
	 * 判断字符串是否包含一些字符 contains
	 */
	public static boolean containsString(String src, String dest) {
		boolean flag = false;
		if (src.contains(dest)) {
			flag = true;
		}
		return flag;
	}

	/*
	 * 判断字符串是否包含一些字符 indexOf
	 */
	public static boolean indexOfString(String src, String dest) {
		boolean flag = false;
		if (src.indexOf(dest) != -1) {
			flag = true;
		}
		return flag;
	}
	
	public static String EncoderByMd5(String str){
        //确定计算方法
        MessageDigest md5 = null ;
		try {
			md5 = MessageDigest.getInstance("MD5");
			 //加密后的字符串
			BASE64Encoder base64en = new BASE64Encoder();
	        //加密后的字符串
	        return  base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
       
      return null;
	}
	
	/**
     *  编码
     * @param src
     * @return
     */
    public static String encode(String src) {
		return new sun.misc.BASE64Encoder().encode(src.getBytes());
	}

	/**
	 * @param 解码
	 * @return
	 */
	public static String decode(String src) {
		try {
			byte[] data = new sun.misc.BASE64Decoder().decodeBuffer(src);
			return new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return src;
	}
	
	/**
	 * double数字类型格式化,小数点后N位直接截取,不采用四舍五入
	 * 
	 * @param obj
	 * @param pattern
	 * @return
	 */
	public static String format(double obj, String pattern) {
		DecimalFormat format = new DecimalFormat(pattern);
		format.setRoundingMode(RoundingMode.FLOOR);
		return format.format(obj);
	}

	public static int[] stringtoIntArray(String str,String delim) {
		int ret[] = new int[str.length()];
		StringTokenizer toKenizer = new StringTokenizer(str, delim);
		int i = 0;
		while (toKenizer.hasMoreElements()) {
			int data = Integer.valueOf(toKenizer.nextToken());
			ret[i++] = data;
		}
		return ret;
	}
	
	/**
	 * 字符串排序
	 * @param s
	 * @return
	 */
	public static String stringSort(String[] s) {
		List<String> list = new ArrayList<String>(s.length);
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		Collections.sort(list);
		String sortStr = "";
		for (int i = 0; i < s.length; i++) {
			sortStr = sortStr + s[i];
		}
		return sortStr;
	}
	

}
