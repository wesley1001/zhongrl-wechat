package cn.xn.wechat.activity.scan.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.json.JsonMessage;
import cn.xn.wechat.activity.model.ChangeRecordVo;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.resp.InvitRecordResp;
import cn.xn.wechat.activity.resp.MaterialResp;
import cn.xn.wechat.activity.resp.UserInfoResp;
import cn.xn.wechat.activity.service.IFansUserInfoService;
import cn.xn.wechat.activity.service.IPrizeInfoService;
import cn.xn.wechat.activity.util.DataPage;
import cn.xn.wechat.web.base.BaseController;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;

@Controller
public class FansUserInfoController extends BaseController {
	@Autowired
	private IFansUserInfoService fansUserInfoService;
	@Autowired
	private IPrizeInfoService prizeInfoService;

	/**
	 * 查询用户的邀请记录
	 */
	@RequestMapping("/activity/user/queryInviteFriendRecord")
	@ResponseBody
	public JsonMessage queryInviteFriendRecord(@RequestParam String userId,
			DataPage<InvitRecordResp> page) {
		if (page == null) {
			page = new DataPage<InvitRecordResp>();
		}
		page = fansUserInfoService.queryInviteRecordByPage(userId, page);
		return JsonMessage.getSuccessJson(page);
	}

	/**
	 * 获取首页信息
	 */
	@RequestMapping("/activity/user/myUserInfoHome")
	@ResponseBody
	public JsonMessage myUserInfoHome(String code, String userId, String channel) {
		WechatActivity activity = wechatActivityService
				.getActivityByType(PrizeInfoConstant.WXSS_ACTIVITY_TYPE);
		if (activity == null) {
			return JsonMessage.getErrorJson(120, "活动已经过期");
		}
		if (activity != null) {
			long starLongTime = activity.getStartDate().getTime();
			long endLongTime = activity.getEndDate().getTime();
			long currentTime = new Date().getTime();
			if (currentTime < starLongTime) {// 当前时间小于开始时间
				return JsonMessage.getErrorJson(
						121,
						"活动还未开始,开始时间为:"
								+ DateUtil.DateToString(
										activity.getStartDate(),
										DateStyle.YYYY_MM_DD_HH_MM_SS));
			}
			if (currentTime > endLongTime) {// 当前时间大于结束时间
				return JsonMessage.getErrorJson(120, "活动已经过期");
			}
		}
		logger.info("扫码活动查询首页信息:code={},userId={},channel={}", new Object[] {
				code, userId, channel });
		if (StringUtils.isBlank(userId) && StringUtils.isBlank(code)) {
			return JsonMessage.getErrorJson("userId和code传其中之一");
		}
		UserInfoResp userInfo = fansUserInfoService.myUserInfoHome(code,
				userId, channel);
		if (userInfo == null) {
			logger.info("获取首页数据发生异常");
			return JsonMessage.getErrorJson("获取首页数据发生异常");
		}
		return JsonMessage.getSuccessJson(userInfo);
	}

	/**
	 * 全网用户邀请好友排名
	 */
	@RequestMapping("/activity/user/findInviteNumberRank")
	@ResponseBody
	public JsonMessage findInviteNumberRank(@RequestParam String userId) {
		UserInfoResp resp = fansUserInfoService.getUserInfoResp(userId);
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("userRank", resp.getRank());// 用户排名
		respMap.put("invitateNumber", resp.getInvitNum());// 邀请人数
		respMap.put("topTen", fansUserInfoService.findRankByTopNum(10));// 前10名
		return JsonMessage.getSuccessJson(respMap);
	}

	/**
	 * 用户兑换奖品列表
	 */
	@RequestMapping("/activity/user/findChangePrizeList")
	@ResponseBody
	public JsonMessage findChangePrizeList(@RequestParam String userId) {
		List<ChangeRecordVo> list = fansUserInfoService
				.findChangePrizeList(userId);
		return JsonMessage.getSuccessJson(list);
	}

	/**
	 * 我的原料和已兑换原料
	 */
	@RequestMapping("/activity/user/getMaterialResp")
	@ResponseBody
	public JsonMessage getMaterialResp(@RequestParam String userId) {
		MaterialResp materResp = fansUserInfoService.getMaterialResp(userId);
		return JsonMessage.getSuccessJson(materResp);
	}

	/**
	 * 查询我的二维码等其他信息
	 */
	@RequestMapping("/activity/user/getQrCodeUrlById")
	@ResponseBody
	public JsonMessage getQrCodeUrlById(@RequestParam String userId) {
		FansUserInfo userInfo = fansUserInfoService.getFansUserInfo(userId,
				null, null);
		if (userInfo != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("nickname", userInfo.getNickname());
			map.put("headimgurl", userInfo.getHeadimgurl());
			map.put("qrCodeUrl", fansUserInfoService.getUserQrCodeById(userId));
			List<PrizeInfo> list = prizeInfoService.findPrizeInfoList();
			if (list != null && list.size() > 0) {
				map.put("buyMaterial",
						String.valueOf(list.get(0).getBuyMaterial()));
				map.put("prizeName", String.valueOf(list.get(0).getPrizeName()));
			}
			return JsonMessage.getSuccessJson(map);
		}
		return JsonMessage.getErrorJson("用户信息存在");
	}

}
