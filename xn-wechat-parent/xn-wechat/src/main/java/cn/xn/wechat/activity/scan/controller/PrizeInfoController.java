package cn.xn.wechat.activity.scan.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.json.JsonMessage;
import cn.xn.wechat.activity.model.AddressVo;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.ShareLog;
import cn.xn.wechat.activity.service.IFansUserInfoService;
import cn.xn.wechat.activity.service.IOrderService;
import cn.xn.wechat.activity.service.IPrizeInfoService;
import cn.xn.wechat.web.base.BaseController;

@Controller
public class PrizeInfoController extends BaseController {
	@Resource
	private IPrizeInfoService prizeInfoService;
	@Resource
	private IFansUserInfoService fansUserInfoService;
	@Resource
	private IOrderService orderService;

	@RequestMapping("/activity/prize/findPrizeInfoList")
	@ResponseBody
	public JsonMessage findPrizeInfoList(@RequestParam String userId) {
		logger.info("奖品列表查询 userId={}", userId);
		FansUserInfo userInfo = fansUserInfoService.getFansUserInfo(userId,
				null, null);
		int material = userInfo.getMaterial();
		int userRank = fansUserInfoService.getUserRankById(userId);
		Map<String, Object> rltMap = prizeInfoService.findPrizeInfoList(userId,
				material, userRank);
		return JsonMessage.getSuccessJson(rltMap);
	}

	@RequestMapping("/activity/prize/getPrizeInfoById")
	@ResponseBody
	public JsonMessage getPrizeInfoById(@RequestParam String prizeId,
			@RequestParam String userId) {
		logger.info("奖品详情查询 prizeId={},userId={}", prizeId, userId);
		PrizeInfo prizeInfo = prizeInfoService.getPrizeInfoDetail(prizeId,
				userId);
		return JsonMessage.getSuccessJson(prizeInfo);
	}

	/**
	 * 立即兑换产品
	 */
	@RequestMapping("/activity/prize/confirmChangePrize")
	@ResponseBody
	public JsonMessage confirmChangePrize(@RequestParam String prizeId,
			@RequestParam int userId, AddressVo vo) {
		if (StringUtils.isBlank(vo.getUsername())) {
			return JsonMessage.getErrorJson("请输入用户姓名!");
		}
		if (StringUtils.isBlank(vo.getMobile())) {
			return JsonMessage.getErrorJson("手机号码不能为空!");
		}
		if (!isMobile(vo.getMobile())) {
			return JsonMessage.getErrorJson("手机号码格式不正确!");
		}
		return orderService.confirmChangePrize(prizeId, userId, vo,
				PrizeInfoConstant.WXSS_ACTIVITY_TYPE);
	}

	/**
	 * 分享日志记录
	 */
	@RequestMapping("/activity/scan/shareLogRecord")
	@ResponseBody
	public JsonMessage shareLogRecord(@RequestParam String userId,
			String content, String activityType, HttpServletRequest request) {
		logger.info("分享二维码回调日志:userId={},record={}", userId, content);
		ShareLog sharelog = new ShareLog();
		sharelog.setUserId(userId);
		sharelog.setActivityType(activityType);
		String ipAddress = getIpAddr(request);
		sharelog.setIpAddress(ipAddress);
		sharelog.setContent(content);
		sharelog.setCreateDate(new Date());
		shareLogMapper.add(sharelog);
		return JsonMessage.getSuccessJson("success");
	}

	/**
	 * 活动详情
	 */
	@RequestMapping("/activity/scan/getActivity")
	@ResponseBody
	public JsonMessage getActivityByType() {
		return JsonMessage.getSuccessJson(wechatActivityService
				.getActivityByType(PrizeInfoConstant.WXSS_ACTIVITY_TYPE));
	}

}
