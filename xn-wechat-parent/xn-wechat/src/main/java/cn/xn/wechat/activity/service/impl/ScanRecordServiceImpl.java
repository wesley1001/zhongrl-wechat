package cn.xn.wechat.activity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.mapper.FansUserInfoMapper;
import cn.xn.wechat.activity.mapper.ScanRecordMapper;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.ScanQRCodeRecord;
import cn.xn.wechat.activity.service.IScanRecordService;
import cn.xn.wechat.activity.util.IdUtil;

@Service("scanRecordService")
public class ScanRecordServiceImpl implements IScanRecordService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ScanRecordMapper scanRecordMapper;
	@Autowired
	private FansUserInfoMapper fansUserInfoMapper;

	private String getUserIdToInt(int userId) {
		return fansUserInfoMapper.getFansUserInfoById(userId).getUserId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addScanRecord(int uid, String openid) {
		String userId = getUserIdToInt(uid);
		this.addScanRecord(userId, openid);
	}

	private FansUserInfo getFansUserInfo(String userId, String openid,
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

	@Override
	public ScanQRCodeRecord getScanQRCodeRecordById(String openid, String userId) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(userId)) {
			hashMap.put("userId", userId);
		}
		if (StringUtils.isNotBlank(openid)) {
			hashMap.put("openid", openid);
		}
		return scanRecordMapper.findScanQRCodeRecordByMap(hashMap);
	}

	@Override
	public void addScanRecord(String userId, String openid) {
		// 被邀请者
		FansUserInfo beInvitUserInfo = getFansUserInfo(null, openid, null);
		if (beInvitUserInfo != null) {
			logger.info("被邀请者已经存在,邀请者userId={},被邀请者userId={}", userId,
					beInvitUserInfo.getUserId());
			return;
		}
		ScanQRCodeRecord record = getScanQRCodeRecordById(openid, userId);
		if (record != null) {
			logger.info("扫码记录已经存在无需再次添加, 邀请者userId={},被邀请者openid={}", userId,
					openid);
			return;
		}
		// 邀请者
		FansUserInfo invitUserInfo = getFansUserInfo(userId, null, null);
		if (invitUserInfo == null) {
			logger.info("邀请者数据已经被删除,邀请者userId={},被邀请者openid={}", userId, openid);
			return;
		}
		record = new ScanQRCodeRecord();
		record.setCreate_date(new Date());
		record.setMaterial(PrizeInfoConstant.PrizeMaterialConstant.FREQUENCY);
		record.setOpenid(openid);
		record.setUser_id(userId);
		record.setId(IdUtil.generateUUID());
		scanRecordMapper.addScanQRCodeRecord(record);
		logger.info("邀请者昵称 nickname={} ,邀请者 userId={},被邀请者 openid={},新增扫码记录 ",
				new String[] { invitUserInfo.getNicknameDisplay(), userId,
						openid });
		invitUserInfo.setUserId(userId);
		invitUserInfo.setUpdateDate(new Date());
		invitUserInfo.setMaterial(invitUserInfo.getMaterial()
				+ PrizeInfoConstant.PrizeMaterialConstant.FREQUENCY);
		fansUserInfoMapper.updateFansUserInfo(invitUserInfo);
	}

}
