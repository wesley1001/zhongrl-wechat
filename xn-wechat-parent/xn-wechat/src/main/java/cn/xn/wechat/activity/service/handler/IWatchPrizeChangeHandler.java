package cn.xn.wechat.activity.service.handler;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.constant.PrizeInfoConstant.PrizeSmsModuleConstant;
import cn.xn.wechat.activity.exception.ChangePrizeException;
import cn.xn.wechat.activity.model.AddressVo;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;

/**
 * 集中处理实体物品 购买逻辑参数校验等
 */
@Component
public class IWatchPrizeChangeHandler extends AbstractPrizeChangeHandler {
	private Logger logger = LoggerFactory
			.getLogger(IWatchPrizeChangeHandler.class);

	private String tips = "iWatch只能在活动结束当天才能购买";

	@Override
	public int getProcutType() {
		return PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_1;
	}

	@Override
	public void changeBeforeCheckParam(FansUserInfo userinfo,
			PrizeInfo prizeInfo, AddressVo vo, WechatActivity activity)
			throws ChangePrizeException {
		logger.info("兑换实体物品之前字段校验...iwatch...");
		if (vo == null) {
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "请输入姓名、手机号、地址");
		}
		if (StringUtils.isBlank(vo.getUsername())) {
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "请输入姓名");
		}
		if (StringUtils.isBlank(vo.getMobile())) {
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "请输入手机号");
		}
		if (StringUtils.isBlank(vo.getAddress())) {
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "请输入地址");
		}
		String currentDate = DateUtil.DateToString(new Date(),
				DateStyle.YYYYMMDD);
		String activityEndDate = DateUtil.DateToString(activity.getEndDate(),
				DateStyle.YYYYMMDD);
		if (!currentDate.equals(activityEndDate)) {// 活动最后一天，排名第一才能购买
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, tips);
		}
		checkMaterialAndPrizeStatus(userinfo, prizeInfo, vo);
		if (!fansUserInfoService.isCheckUserNoOne(userinfo.getUserId())) { // 排行第一
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR,
					"您目前不是排行第一名，现在不能兑取");
		}

	}

	@Override
	public String getModuleId() {
		return PrizeSmsModuleConstant.WATCH_MODULEID;
	}

}
