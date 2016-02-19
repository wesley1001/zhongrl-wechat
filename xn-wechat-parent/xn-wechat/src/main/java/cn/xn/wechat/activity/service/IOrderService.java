package cn.xn.wechat.activity.service;

import cn.xn.wechat.activity.json.JsonMessage;
import cn.xn.wechat.activity.model.AddressVo;

public interface IOrderService {
	
	/**
	 * 兑换奖品
	 * @param prizeId 奖品Id
	 * @param userId  用户id
	 * @param vo   
	 * @param activityType 活动类型
	 * @return
	 */
	public JsonMessage confirmChangePrize(String prizeId, Integer userId,
			AddressVo vo, String activityType);
}
