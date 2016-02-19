package cn.xn.wechat.activity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.exception.WechatException;
import cn.xn.wechat.activity.mapper.ChangeRecordMapper;
import cn.xn.wechat.activity.mapper.FansUserInfoMapper;
import cn.xn.wechat.activity.mapper.UserQRcodeMapper;
import cn.xn.wechat.activity.model.ChangeRecordVo;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.UserQRcode;
import cn.xn.wechat.activity.resp.InvitRecordResp;
import cn.xn.wechat.activity.resp.MaterialResp;
import cn.xn.wechat.activity.resp.UserInfoResp;
import cn.xn.wechat.activity.resp.UserRankResp;
import cn.xn.wechat.activity.service.IFansUserInfoService;
import cn.xn.wechat.activity.service.IScanRecordService;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.activity.service.handler.UserWechatHandler;
import cn.xn.wechat.activity.util.DataPage;

@Service
public class FansUserInfoServiceImpl implements IFansUserInfoService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FansUserInfoMapper fansUserInfoMapper;
	@Autowired
	private UserQRcodeMapper userQRcodeMapper;
	@Resource
	private IWechatActivityService wechatActivityService;
	@Autowired
	private ChangeRecordMapper changeRecordMapper;
	@Resource
	private IScanRecordService scanRecordService;
	@Resource
	private UserWechatHandler userWechatHandler;

	@Override
	public FansUserInfo getFansUserInfo(String userId, String openid,
			String unionid) {
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
		return fansUserInfoMapper.getFansUserInfoByMap(hashMap);
	}

	/**
	 * 查询用户的邀请总数
	 * 
	 * @param userId
	 * @return
	 */
	private int getUserInvitNumById(String userId) {
		return fansUserInfoMapper.countInviteRecordByUserId(userId);
	}

	/**
	 * 获取用户的目前排名
	 */
	public int getUserRankById(String userId) {
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

	@Override
	public UserInfoResp myUserInfoHome(String code, String userId,
			String channel) {
		try {
			return userWechatHandler.getFansUserInfoByCodeAndUserId(code,
					userId);
		} catch (WechatException e) {
			logger.error("获取用户首页信息发生异常!code=" + code + ",userId=" + userId
					+ ",errorInfo={}", e.getMsg());
			return null;
		}
	}

	public UserInfoResp getUserInfoResp(String userId) {
		UserInfoResp resp = new UserInfoResp();
		resp.setRank(getUserRankById(userId));
		resp.setInvitNum(getUserInvitNumById(userId));
		return resp;
	}

	@Override
	public DataPage<InvitRecordResp> queryInviteRecordByPage(String userId,
			DataPage<InvitRecordResp> dataPage) {
		dataPage.setTotalCount(getUserInvitNumById(userId));
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("pageIndex", dataPage.getPageIndex());
		hashMap.put("pageSize", dataPage.getPageSize());
		dataPage.setList(fansUserInfoMapper.queryInviteRecordByMap(hashMap));
		return dataPage;
	}

	@Override
	public List<ChangeRecordVo> findChangePrizeList(String userId) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("status",
				PrizeInfoConstant.PrizeOrderConstant.PRIZE_ORDER_SUCCESS);
		return changeRecordMapper.findChangePrizeListByMap(hashMap);
	}

	@Override
	public MaterialResp getMaterialResp(String userId) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("status",
				PrizeInfoConstant.PrizeOrderConstant.PRIZE_ORDER_SUCCESS);
		Integer changeMaterial = changeRecordMapper
				.sumChangeMaterialByMap(hashMap);
		FansUserInfo userinfo = getFansUserInfo(userId, null, null);
		MaterialResp resp = new MaterialResp();
		resp.setSurplusMaterial(changeMaterial);
		resp.setUserId(userId);
		resp.setMaterial(userinfo.getMaterial());
		return resp;
	}

	@Override
	public List<UserRankResp> findRankByTopNum(int top) {
		return fansUserInfoMapper.findRankTopNum(top);
	}

	@Override
	public String getUserQrCodeById(String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserQRcode codeinfo = userQRcodeMapper.getUserQRcodeByMap(paramMap);
		if (codeinfo != null) {
			return codeinfo.getQrCodeUrl();
		}
		return null;
	}

	@Override
	public boolean isCheckUserNoOne(String userId) {
		List<UserRankResp> list = findRankByTopNum(1);// 查询前一名是否为当前用户
		if (list != null && list.size() > 0
				&& list.get(0).getUserId().equals(userId)) {
			return true;
		}
		return false;
	}
}
