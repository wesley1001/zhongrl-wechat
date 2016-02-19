package cn.xn.wechat.activity.util;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.web.service.WeChatQrCodeService;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Component
public class UserInfoUtil {

	private Logger logger = LoggerFactory.getLogger(UserInfoUtil.class);

	@Resource
	private WechatUtil wechatUtil;
	@Resource
	private WeChatQrCodeService weChatQrCodeService;

	public FansUserInfo getWechatToUserInfoByCode(String code) {
		JSONObject jsonResult = wechatUtil.getAccessToken(code);
		logger.info("wx -----  result : " + jsonResult);
		if (jsonResult.getIntValue("code") != 0) {
			return null;
		}
		JSONObject jsonObject = (JSONObject) JSONObject.parseObject(jsonResult
				.getString("data"));
		String nickname = jsonObject.getString("nickname");
		String openid = jsonObject.getString("openid");
		String unionid = jsonObject.getString("unionid");
		String headimgurl = jsonObject.getString("headimgurl");
		FansUserInfo userInfo = new FansUserInfo();
		userInfo.setNickname(Base64Util.encode(nickname));
		userInfo.setOpenid(openid);
		userInfo.setUnionid(unionid);
		userInfo.setHeadimgurl(headimgurl);
		return userInfo;
	}

	/**
	 * @param uid 用户Id
	 * @param activityType 活动类型
	 * @param expireSeconds 二维码生成时间
	 * @return
	 */
	public String getQrCodeUrl(int uid,int activityType,int expireSeconds) {
		int param=uid + activityType;
		logger.info("wx ----- 生成临时二维码---param: " + param);
		String json = wechatUtil.createTemporaryQrCode(param, expireSeconds);
		logger.info("wx ----- temp---获取生成临时二维码图片 : " + json);
		JSONObject jsonObject = JSON.parseObject(json);
		if (jsonObject.getIntValue("code") != 0) {
			return null;
		}
		return jsonObject.getString("qrImage");
	}
	
}
