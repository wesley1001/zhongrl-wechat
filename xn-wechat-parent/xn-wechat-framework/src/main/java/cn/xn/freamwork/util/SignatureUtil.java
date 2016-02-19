package cn.xn.freamwork.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加签验签相关工具类
 *
 */
public class SignatureUtil
{
	/**
	 * @param para
	 * @return
	 * @return boolean
	 */
	public static boolean verifySignature(final Map<String, String> para, final String securityKey)
	{
		String respSignature = para.get("signature");
		// 除去数组中的空值和签名参数
		Map<String, String> filteredReq = paraFilter(para);
		String signature = buildSignature(filteredReq, securityKey);
		if (null != respSignature && respSignature.equals(signature))
		{
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成签名
	 * 
	 * @param req 需要签名的要素
	 * @return 签名结果字符串
	 */
	public static String buildSignature(Map<String, String> req, final String securityKey)
	{
		req = paraFilter(req);

		String prestr = createLinkString(req, true, false);
		prestr = prestr + "&" + MD5util.md5(securityKey);
		return MD5util.md5(prestr);
	}


    /**
     * 宝付支付接口生成签名
     *
     * @param req 需要签名的要素
     * @return 签名结果字符串
     */
    public static String baofuSignature(Map<String, String> req, final String securityKey)
    {
        req = paraFilter(req);

        String prestr = createLinkString(req, true, false);
        prestr = prestr + "|" + MD5util.md5(securityKey);
        return MD5util.md5(prestr);
    }

	/**
	 * 把请求要素按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param para 请求要素
	 * @param sort 是否需要根据key值作升序排列
	 * @param encode 是否需要URL编码
	 * @return 拼接成的字符串
	 */
	public static String createLinkString(final Map<String, String> para, final boolean sort, final boolean encode)
	{

		List<String> keys = new ArrayList<String>(para.keySet());

		if (sort)
		{
			Collections.sort(keys);
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			String value = para.get(key);

			if (encode)
			{
				try
				{
					value = URLEncoder.encode(value, "UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
				}
			}

			if (i == keys.size() - 1)
			{// 拼接时，不包括最后一个&字符
				sb.append(key).append("=").append(value);
			}
			else
			{
				sb.append(key).append("=").append(value).append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * 除去请求要素中的空值和签名参数
	 * 
	 * @param para 请求要素
	 * @return 去掉空值与签名参数后的请求要素
	 */
	public static Map<String, String> paraFilter(final Map<String, String> para)
	{

		Map<String, String> result = new HashMap<String, String>();

		if (para == null || para.size() <= 0)
		{
			return result;
		}

		for (String key : para.keySet())
		{
			String value = para.get(key);

			if (value == null || value.equals("") || key.equalsIgnoreCase("signature")
					|| key.equalsIgnoreCase("signMethod"))
			{
				continue;
			}

			result.put(key, value);
		}

		return result;
	}

	/**
	 * 解析应答字符串，生成应答要素
	 * 
	 * @param str 需要解析的字符串
	 * @return 解析的结果map
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(final String str) throws UnsupportedEncodingException
	{

		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;

		for (int i = 0; i < len; i++)
		{// 遍历整个带解析的字符串
			curChar = str.charAt(i);// 取当前字符

			if (curChar == '&')
			{// 如果读取到&分割符
				putKeyValueToMap(temp, isKey, key, map);
				temp.setLength(0);
				isKey = true;
			}
			else
			{
				if (isKey)
				{// 如果当前生成的是key
					if (curChar == '=')
					{// 如果读取到=分隔符
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					}
					else
					{
						temp.append(curChar);
					}
				}
				else
				{// 如果当前生成的是value
					temp.append(curChar);
				}
			}
		}

		putKeyValueToMap(temp, isKey, key, map);

		return map;
	}

	private static void putKeyValueToMap(final StringBuilder temp, final boolean isKey, String key,
			final Map<String, String> map) throws UnsupportedEncodingException
	{
		if (isKey)
		{
			key = temp.toString();
			if (key.length() == 0)
			{
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		}
		else
		{
			if (key.length() == 0)
			{
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, URLDecoder.decode(temp.toString(), "UTF-8"));
		}
	}

	// public static void main(String[] args)
	// {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("1", "11");
	// map.put("12", "12");
	// map.put("11", "13");
	// map.put("4", "14");
	// map.put("05", "");
	//
	// System.out.println(buildSignature(map, "123456"));
	//
	// map.put("signature", buildSignature(map, "123456"));
	// map.put("signMethod", "MD5");
	//
	// System.out.println(verifySignature(map, "123456"));
	//
	// }
}
