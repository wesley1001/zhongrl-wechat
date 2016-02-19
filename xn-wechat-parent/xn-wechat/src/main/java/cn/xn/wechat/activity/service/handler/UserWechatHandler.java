package cn.xn.wechat.activity.service.handler;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.xn.wechat.activity.constant.PrizeInfoConstant.PrizeRespConstant;
import cn.xn.wechat.activity.exception.WechatException;
import cn.xn.wechat.activity.mapper.FansUserInfoMapper;
import cn.xn.wechat.activity.mapper.UserQRcodeMapper;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.ScanQRCodeRecord;
import cn.xn.wechat.activity.model.UserQRcode;
import cn.xn.wechat.activity.resp.UserInfoResp;
import cn.xn.wechat.activity.resp.UserRankResp;
import cn.xn.wechat.activity.service.IScanRecordService;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.activity.util.IdUtil;
import cn.xn.wechat.activity.util.UserInfoUtil;

import com.alibaba.fastjson.JSON;

@Component
public class UserWechatHandler {
	private String channel = "saoma";
	private Logger logger = LoggerFactory
			.getLogger(AbstractPrizeChangeHandler.class);
	@Resource
	private IWechatActivityService wechatActivityService;
	@Resource
	private FansUserInfoMapper fansUserInfoMapper;
	@Resource
	private IScanRecordService scanRecordService;
	@Resource
	private UserQRcodeMapper userQRcodeMapper;
	@Resource
	private UserInfoUtil userInfoUtil;

	/**
	 * 请求微信api接口生成二维码
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 */
	private String requestCreateUserQrcodeByUserId(String userId)
			throws WechatException {
		FansUserInfo userInfo = getFansUserInfoByUserId(userId);
		if (userInfo == null) {
			throw new WechatException(PrizeRespConstant.ERROR, "获取用户信息异常");
		}
		int expireSeconds = 24 * 3600 * 7;
		String qrCodeUrl = userInfoUtil.getQrCodeUrl(userInfo.getId(), 10,
				expireSeconds);
		if (qrCodeUrl == null) {
			logger.info("请求微信接口生成二维码失败,userId={},id={}", userInfo.getUserId(),userInfo.getId());
			throw new WechatException(PrizeRespConstant.ERROR, "请求微信接口生成二维码失败");
		}
		return qrCodeUrl;
	}

	private String requestCreateUserQrcodeById(int userId)
			throws WechatException {
		int expireSeconds = 24 * 3600 * 30;
		String qrCodeUrl = userInfoUtil.getQrCodeUrl(userId, 10, expireSeconds);
		if (qrCodeUrl == null) {
			logger.info("请求微信接口生成二维码失败,userId={}", userId);
			throw new WechatException(PrizeRespConstant.ERROR, "请求微信接口生成二维码失败");
		}
		return qrCodeUrl;
	}

	private FansUserInfo requestWechatByCode(String code)
			throws WechatException {
		FansUserInfo wechatInfo = userInfoUtil.getWechatToUserInfoByCode(code);
		if (wechatInfo == null) {
			throw new WechatException(PrizeRespConstant.ERROR,
					"获取微信用户信息发生异常,code=" + code);
		}
		return wechatInfo;
	}

	private FansUserInfo getFansUserInfoByUserId(String userId) {
		return fansUserInfoMapper.getFansUserInfoByUserId(userId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserInfoResp getFansUserInfoByCodeAndUserId(String code,
			String userId) throws WechatException {
		if (StringUtils.isNotBlank(userId)) {
			FansUserInfo userInfo = getFansUserInfoByUserId(userId);
			if (userInfo == null) {
				return getUserInfoByCode(code);
			}
			UserInfoResp resp = new UserInfoResp();
			if (StringUtils.isNotBlank(userInfo.getQrCodeUrl())) {
				resp.setCodeUrl(userInfo.getQrCodeUrl());
			} else {
				try {
					resp.setCodeUrl(requestCreateUserQrcodeById(userInfo
							.getId()));
				} catch (Exception e) {
					System.out.println("获取二维码异常!");
				}
			}
			resp.setMaterial(userInfo.getMaterial());
			resp.setUserId(userId);
			resp.setRank(getUserRankById(userId));
			resp.setInvitNum(getUserInvitNumById(userId));
			resp.setNickname(userInfo.getNickname());
			return resp;
		}
		if (StringUtils.isNotBlank(code)) {
			return getUserInfoByCode(code);
		}
		return null;
	}

	private FansUserInfo getFansUserInfo(String userId, String openid,
			String unionid, Integer id) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if (userId != null) {
			hashMap.put("userId", userId);
		}
		if (openid != null) {
			hashMap.put("openid", openid);
		}
		if (unionid != null) {
			hashMap.put("unionid", unionid);
		}
		if (id != null && id > 0) {
			hashMap.put("id", id);
		}
		return fansUserInfoMapper.getFansUserInfoByMap(hashMap);
	}

	private UserInfoResp getUserInfoByCode(String code) throws WechatException {
		FansUserInfo wechatInfo = requestWechatByCode(code);
		if (wechatInfo == null) {
			throw new WechatException(PrizeRespConstant.ERROR, "获取微信用户信息失败");
		}
		FansUserInfo fansUserInfo = getFansUserInfo(null,
				wechatInfo.getOpenid(), wechatInfo.getUnionid(), null);
		if (fansUserInfo != null) {
			FansUserInfo userInfo = getFansUserInfoByUserId(fansUserInfo
					.getUserId());
			logger.info("查询用户 qrCodeUrl={}", userInfo.getQrCodeUrl());
			UserInfoResp resp = new UserInfoResp();
			resp.setCodeUrl(userInfo.getQrCodeUrl());
			resp.setMaterial(userInfo.getMaterial());
			resp.setUserId(userInfo.getUserId());
			resp.setRank(getUserRankById(userInfo.getUserId()));
			resp.setInvitNum(getUserInvitNumById(userInfo.getUserId()));
			resp.setNickname(userInfo.getNickname());
			return resp;
		}
		String userId = IdUtil.generateUUID();
		fillUserInfo(userId, wechatInfo);
		fansUserInfoMapper.addFansUserInfo(wechatInfo);
		logger.info("新增用户 userInfo={}", JSON.toJSONString(wechatInfo));
		String qrCodeUrl = requestCreateUserQrcodeByUserId(userId);
		addUserQRcode(userId,qrCodeUrl);
		UserInfoResp resp = new UserInfoResp();
		resp.setCodeUrl(qrCodeUrl);
		resp.setMaterial(1);
		resp.setUserId(userId);
		resp.setRank(getUserRankById(userId));
		resp.setInvitNum(0);
		resp.setNickname(wechatInfo.getNickname());
		logger.info("获取首页信息成功 userInfo={}", JSON.toJSONString(wechatInfo));
		return resp;
	}

	private List<UserRankResp> findRankByTopNum(int top) {
		return fansUserInfoMapper.findRankTopNum(top);
	}

	/**
	 * 获取用户的目前排名
	 */
	private int getUserRankById(String userId) {
		List<UserRankResp> list = findRankByTopNum(200);// 查询前一名是否为当前用户
		if (list != null) {
			int userRank = 1;
			for (UserRankResp userRankResp : list) {
				if (userRankResp.getUserId().equals(userId)) {
					return userRank;
				}
				userRank++;
			}
			return userRank;
		}
		return 300;
	}

	/**
	 * 查询用户的邀请总数
	 */
	private int getUserInvitNumById(String userId) {
		return fansUserInfoMapper.countInviteRecordByUserId(userId);
	}

	/**
	 * 新增二维码到数据库
	 * 
	 * @param userId
	 * @param openid
	 * @param unionid
	 * @param qrCodeUrl
	 * @return
	 * @throws WechatException
	 */
	private int addUserQRcode(String userId,
			String qrCodeUrl) throws WechatException {
		UserQRcode userCode = new UserQRcode();
		userCode.setStartDate(new Date());
		userCode.setUserId(userId);
		userCode.setQrCodeUrl(qrCodeUrl);
		userCode.setCodeId(IdUtil.generateUUID());
		Calendar cal = Calendar.getInstance();
		cal.setTime(userCode.getStartDate());
		cal.add(Calendar.DATE, 30);
		userCode.setEndDate(cal.getTime());
		logger.info("新增二维码到数据库中 data={}", JSON.toJSONString(userCode));
		return userQRcodeMapper.addUserQRcode(userCode);
	}

	private void fillUserInfo(String userId, FansUserInfo wechatInfo) {
		wechatInfo.setUserId(userId);
		wechatInfo.setCreateDate(new Date());
		wechatInfo.setMaterial(1);
		wechatInfo.setSource("wechat");
		wechatInfo.setChannel(channel);
		// 邀请人Id
		String refereeId = getRefereeIdByOpenid(wechatInfo.getOpenid());
		wechatInfo.setReferee_id(refereeId);
		wechatInfo.setUserId(userId);
	}

	/**
	 * 查询邀请记录获取推荐人的用户Id
	 * 
	 * @param openid
	 * @return 推荐者用户Id
	 */
	private String getRefereeIdByOpenid(String openid) {
		ScanQRCodeRecord rlt = scanRecordService.getScanQRCodeRecordById(
				openid, null);
		if (rlt != null) {
			return rlt.getUser_id();
		}
		return null;
	}
}
