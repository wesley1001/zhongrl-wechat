package cn.xn.wechat.activity.service.handler;

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

/**
 * 集中处理虚拟物品购买逻辑参数校验等
 */
@Component
public class MoviePrizeChangeHandler extends AbstractPrizeChangeHandler {
	private Logger logger = LoggerFactory
			.getLogger(MoviePrizeChangeHandler.class);

	@Override
	public int getProcutType() {
		return PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_2;
	}

	@Override
	public void changeBeforeCheckParam(FansUserInfo userinfo,
			PrizeInfo prizeInfo, AddressVo vo, WechatActivity activity)
			throws ChangePrizeException {
		logger.info("兑换虚拟物品之前字段校验....movie.....");
		if (vo == null) {
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "请输入姓名和手机号");
		}
		if (StringUtils.isBlank(vo.getUsername())) {
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "请输入姓名");
		}
		if (StringUtils.isBlank(vo.getMobile())) {
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "请输入手机号");
		}
		checkMaterialAndPrizeStatus(userinfo, prizeInfo,vo);
	}

	@Override
	public String getModuleId() {
		return PrizeSmsModuleConstant.MOVIE_MODULEID;
	}
 
 

}
