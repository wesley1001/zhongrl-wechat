package cn.xn.wechat.activity.service.handler;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.xn.wechat.activity.constant.EggsConstant;
import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.exception.ChangePrizeException;
import cn.xn.wechat.activity.mapper.ChangeRecordMapper;
import cn.xn.wechat.activity.mapper.EggsMapper;
import cn.xn.wechat.activity.mapper.FansUserInfoMapper;
import cn.xn.wechat.activity.mapper.PrizeInfoMapper;
import cn.xn.wechat.activity.mapper.ScanRecordMapper;
import cn.xn.wechat.activity.model.AddressVo;
import cn.xn.wechat.activity.model.ChangeRecordVo;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.LMOrder;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.service.EggsService;
import cn.xn.wechat.activity.service.IFansUserInfoService;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.activity.util.IdUtil;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoniu.sms.req.SendMsgReq;
import com.xiaoniu.sms.service.ISmsService;

public abstract class AbstractPrizeChangeHandler implements PrizeChangeHandler {
	private Logger logger = LoggerFactory
			.getLogger(AbstractPrizeChangeHandler.class);
	@Resource
	protected PrizeInfoMapper prizeInfoMapper;
	@Resource
	protected EggsService eggsService;
	@Resource
	protected ChangeRecordMapper changeRecordMapper;
	@Resource
	protected FansUserInfoMapper fansUserInfoMapper;
	@Resource
	protected ScanRecordMapper scanRecordMapper;
	@Resource
	protected ISmsService iSmsService;
	@Resource
	protected IWechatActivityService wechatActivityService;
	@Resource
	protected IFansUserInfoService fansUserInfoService;
	
	private String partnerId = "QGZ";

	private PrizeChangeSmsProcess prizeChangeSmsProcess;
	
	@Resource
	private EggsMapper eggsMapper;
	
	/**
	 * 获取用户信息
	 */
	private FansUserInfo getUserInfoId(int userId, String activityType) {
		if (activityType.equals(EggsConstant.SD_ACTIVITY_TYPE)) {
			return eggsService.getUserInfoById(Integer.valueOf(userId));
		} else if (activityType.equals(PrizeInfoConstant.WXSS_ACTIVITY_TYPE)) {
			return fansUserInfoMapper.getFansUserInfoById(userId);
		}
		return null;
	}

	/**
	 * 兑换产品
	 * 
	 * @param userId
	 * @param prizeInfo
	 * @param vo
	 * @return
	 * @throws ChangePrizeException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String changePrizeHandle(Integer userId, String prizeId,
			AddressVo vo, WechatActivity activity) throws ChangePrizeException {
		// 获取用户物料
		PrizeInfo prizeInfo = prizeInfoMapper.getPrizeInfoLockById(prizeId);
		FansUserInfo userinfo = getUserInfoId(userId,
				activity.getActivityType());
		// 查询产品信息
		logger.info("确认兑换 用户信息,{}", JSON.toJSONString(userinfo));
		changeBeforeCheckParam(userinfo, prizeInfo, vo, activity);
		if (PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_1 == getProcutType()) {
			String investId = upateUserPrizeRecordAfterHandle(userinfo,
					prizeInfo, vo);
			prizeChangeSmsProcess.sendMsg(partnerId, getModuleId(),
					vo.getMobile(), userId);
			return investId;
		} else if (PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_2 == getProcutType()) {
			String investId = upateUserPrizeRecordAfterHandle(userinfo,
					prizeInfo, vo);
			prizeChangeSmsProcess.sendMsgAndUpdateTicket(partnerId,
					getModuleId(), vo.getMobile(), userId, vo.getUsername(),
					investId);
			return investId;
		} else if (PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_3 == getProcutType()) {
			//原料进行业务处理
			upateEggsHandle(userinfo,prizeInfo, vo);
			String orderNumber = "EGGS" + DateUtil.DateToString(new Date(), DateStyle.YYYYMMDDHHMMSS) + StringUtil.randomClickNumber();
			String result = eggsService.sendRateToUser(vo.getMobile(), userId, prizeId,orderNumber);
			logger.info("发送流量的结果集 result : " + result);
			JSONObject resultJson = JSONObject.parseObject(result);
			int code = resultJson.getInteger("code");
			LMOrder lmOrder = new LMOrder();
			lmOrder.setEggsId(userId);
			lmOrder.setCreateDate(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			lmOrder.setMobile(vo.getMobile());
			//暂用产品描述作为产品流量的值
			lmOrder.setFlowValue(Integer.parseInt(prizeInfo.getPrizeDesc()));
			lmOrder.setPrizeId(prizeId);
			//所需金蛋
			lmOrder.setGoldenEggsNumber(prizeInfo.getBuyMaterial());
			lmOrder.setOrderNmuber(orderNumber);
			if(code == 0){
				lmOrder.setCarrieroperator(resultJson.getString("carrieroperator"));
				lmOrder.setSatats("1");
				lmOrder.setOrderNoLM(resultJson.getString("orderNO"));
				//返回订单编号
			}else{
				lmOrder.setSatats("0");
			}
			eggsMapper.saveEggsOrder(lmOrder);
			return orderNumber;
		} else {
			logger.info("产品不存在,prizeId={}", prizeId);
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "产品不存在");
		}
	}
	
	/**
	 * 处理兑换业务，更新用户，奖品，兑换记录等
	 */
	public int upateEggsHandle(FansUserInfo userinfo,
			PrizeInfo prizeInfo, AddressVo vo) {
		// 修改用户原料信息
		updateEggsUserMateral(userinfo, prizeInfo);
		// 修改产品库存信息
		prizeInfo.setUpdateDate(new Date());
		int buyedtotal = prizeInfo.getBuyedTotal() + 1;
		prizeInfo.setBuyedTotal(buyedtotal);
		if (buyedtotal >= prizeInfo.getBuyTotal()) {
			// 已经售完
			prizeInfo.setStatus(PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_3);
		}
		prizeInfoMapper.updatePrizeInfo(prizeInfo);
		// 生成兑换记录
		return 0;
	}
	
	/**
	 * 修改用户原料
	 */
	private void updateEggsUserMateral(FansUserInfo userinfo, PrizeInfo prizeInfo) {
		userinfo.setUpdateDate(new Date());
		userinfo.setMaterial(userinfo.getMaterial()
				- prizeInfo.getBuyMaterial());
		eggsMapper.updateEggsUserInfo(userinfo);
	}

	/**
	 * 处理兑换业务，更新用户，奖品，兑换记录等
	 */
	public String upateUserPrizeRecordAfterHandle(FansUserInfo userinfo,
			PrizeInfo prizeInfo, AddressVo vo) {
		// 修改用户原料信息
		updateUserMateral(userinfo, prizeInfo);
		// 修改产品库存信息
		prizeInfo.setUpdateDate(new Date());
		int buyedtotal = prizeInfo.getBuyedTotal() + 1;
		prizeInfo.setBuyedTotal(buyedtotal);
		if (buyedtotal >= prizeInfo.getBuyTotal()) {
			// 已经售完
			prizeInfo.setStatus(PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_3);
		}
		prizeInfoMapper.updatePrizeInfo(prizeInfo);
		// 生成兑换记录
		return addOrderRecord(prizeInfo, userinfo,
				PrizeInfoConstant.PrizeOrderConstant.PRIZE_ORDER_SUCCESS,
				"兑换成功", vo);
	}
	
	

	/**
	 * 修改用户原料
	 */
	private void updateUserMateral(FansUserInfo userinfo, PrizeInfo prizeInfo) {
		userinfo.setUpdateDate(new Date());
		userinfo.setMaterial(userinfo.getMaterial()
				- prizeInfo.getBuyMaterial());
		fansUserInfoMapper.updateFansUserInfo(userinfo);
	}

	/**
	 * 校验产品售完状态，用户原料是否满足兑换条件等
	 */
	protected void checkMaterialAndPrizeStatus(FansUserInfo userinfo,
			PrizeInfo prizeInfo, AddressVo vo) throws ChangePrizeException {
		if (userinfo.getMaterial() <= 0
				|| userinfo.getMaterial() < prizeInfo.getBuyMaterial()) {
			String recordId = addOrderRecord(prizeInfo, userinfo,
					PrizeInfoConstant.PrizeOrderConstant.PRIZE_ORDER_FAIL,
					"原料不足,领取失败", vo);
			logger.info("recordId={},原料不足,领取失败", recordId);
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "原料不足,领取失败");
		}
		if (PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2 != prizeInfo
				.getStatus() || prizeInfo.getRemaNum() <= 0) {
			String recordId = addOrderRecord(prizeInfo, userinfo,
					PrizeInfoConstant.PrizeOrderConstant.PRIZE_ORDER_FAIL,
					"产品已经兑换完", vo);
			logger.info("recordId={},产品已经销售完，兑换失败", recordId);
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "该产品不支持兑换");
		}

	}

	/**
	 * 新增兑换记录
	 * 
	 * @param prizeInfo
	 * @param userinfo
	 * @param status
	 * @param remark
	 * @return
	 */
	protected String addOrderRecord(PrizeInfo prizeInfo, FansUserInfo userinfo,
			String status, String remark, AddressVo vo) {
		String recordId = IdUtil.generateUUID();
		ChangeRecordVo record = new ChangeRecordVo();
		record.setRecordId(recordId);
		record.setPrizeId(prizeInfo.getPrizeId());
		record.setPrizeName(prizeInfo.getPrizeName());
		record.setStatus(status);
		record.setChangeDate(new Date());
		record.setIsVirtual(prizeInfo.getIsVirtual());
		record.setUserId(userinfo.getUserId());
		record.setMaterial(prizeInfo.getBuyMaterial());
		record.setRemark(remark);
		record.setMobile(vo.getMobile());
		record.setUsername(vo.getUsername());
		record.setAddress(vo.getAddress());
		changeRecordMapper.addChangeRecord(record);
		return recordId;
	}

	/**
	 * 兑换之前的参数校验
	 */
	public abstract void changeBeforeCheckParam(FansUserInfo userinfo,
			PrizeInfo prizeInfo, AddressVo vo, WechatActivity activity)
			throws ChangePrizeException;

	public abstract String getModuleId();

}
