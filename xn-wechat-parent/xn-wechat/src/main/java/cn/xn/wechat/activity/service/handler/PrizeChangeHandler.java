package cn.xn.wechat.activity.service.handler;

import cn.xn.wechat.activity.exception.ChangePrizeException;
import cn.xn.wechat.activity.model.AddressVo;
import cn.xn.wechat.activity.model.WechatActivity;

public interface PrizeChangeHandler {
	/**
	 * 获取产品种类
	 */
	public abstract int getProcutType();

	/**
	 * 处理兑换奖品流程
	 */
	public String changePrizeHandle(Integer userId, String prizeId,
			AddressVo vo, WechatActivity activity) throws ChangePrizeException;
}
