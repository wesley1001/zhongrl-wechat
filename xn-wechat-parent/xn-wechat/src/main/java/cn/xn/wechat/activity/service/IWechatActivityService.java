package cn.xn.wechat.activity.service;

import cn.xn.wechat.activity.model.WechatActivity;

public interface IWechatActivityService {
	/**
	 * 查询在有效期范围内的活动信息
	 */
	public WechatActivity getAvailableActivityByType(String activityType);

	/**
	 * 查询活动信息
	 * @return
	 */
	public WechatActivity getActivityByType(String activityType);
}
