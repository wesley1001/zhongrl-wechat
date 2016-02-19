package cn.xn.wechat.activity.model;

import org.apache.commons.lang.StringUtils;

import cn.xn.wechat.activity.util.Base64Util;

/**
 * 主要是为了处理微信中昵称带有特殊字符编码后插入到数据库中, 查询时候需要解码操作，这里采用模板模式,相关需要返回昵称的类都要extends该类，
 * 减少代码开发量
 * 
 * @author yiqiang
 */
public abstract class BaseDecodeResp {
	public abstract String getNickname();

	/**
	 * 对nickname进行base64解码
	 */
	public String getNicknameDisplay() {
		String nickname = getNickname();
		if (StringUtils.isNotBlank(nickname)) {
			return Base64Util.decode(nickname);
		}
		return nickname;
	}

}
