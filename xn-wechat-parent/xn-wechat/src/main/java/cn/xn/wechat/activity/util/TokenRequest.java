package cn.xn.wechat.activity.util;


public class TokenRequest extends BaseRequest {

	public String toSign() {
		// 数组按key重新排序
		// 去掉sign签名
		// 拼接字符串
		return "appkey" + getAppkey() + "appsecret" + getAppsecret();
	}
}
