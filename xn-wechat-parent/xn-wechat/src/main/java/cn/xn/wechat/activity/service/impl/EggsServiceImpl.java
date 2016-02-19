package cn.xn.wechat.activity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.xn.wechat.activity.constant.EggsConstant;
import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.mapper.EggsMapper;
import cn.xn.wechat.activity.mapper.FansUserInfoMapper;
import cn.xn.wechat.activity.mapper.PrizeInfoMapper;
import cn.xn.wechat.activity.model.Carrieroperator;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.ScanQRCodeRecord;
import cn.xn.wechat.activity.resp.EggsResp;
import cn.xn.wechat.activity.service.EggsService;
import cn.xn.wechat.activity.util.DataPage;
import cn.xn.wechat.activity.util.IdUtil;
import cn.xn.wechat.activity.util.SendRateUtil;
import cn.xn.wechat.web.job.WecatUserJob;
import cn.xn.wechat.web.mapper.ActivityMapper;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.util.DateFormat;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.StringUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSONObject;
import com.xiaoniu.sms.req.SendMsgReq;
import com.xiaoniu.sms.service.ISmsService;

@Service("eggsService")
public class EggsServiceImpl implements EggsService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private EggsMapper eggsMapper;
	
	@Resource
	private FansUserInfoMapper fansUserInfoMapper;
	
	@Resource
	private WechatUtil wechatUtil;
	
	@Resource
	private SendRateUtil sendRateUtil;
	
	
	private String success = "SUCCESS";
	
	private String fail = "FAIL";
	
	@Resource
	private PrizeInfoMapper prizeInfoMapper;
	
	@Resource
	private ActivityMapper activityMapper;
	
	@Resource
	private ISmsService iSmsService;
	
	private int getUserIdToInt(int userId) {
		return eggsMapper.getEggsUserInfoById(userId).getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEggs(int uid, String openid,String activicty_code) {
		int userId = getUserIdToInt(uid);
		this.addEggsRecod(userId, openid,activicty_code);
	}

	private FansUserInfo getEggsUserInfo(Integer userId , String openid,
			String unionid) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if(userId != null && userId > 0){
			hashMap.put("id", userId);
		}
		if (openid != null) {
			hashMap.put("openid", openid);
		}
		if (unionid != null) {
			hashMap.put("unionid", unionid);
		}
		return eggsMapper.getEggsUserInfoByMap(hashMap);
	}

	@Override
	public ScanQRCodeRecord getEggsQRCodeRecordById(int userId,String openid, String activicty_code) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if(userId > 0){
			hashMap.put("userId", userId);
		}
		if (StringUtils.isNotEmpty(activicty_code)) {
			hashMap.put("activicty_code", activicty_code);
		}
		if (StringUtils.isNotBlank(openid)) {
			hashMap.put("openid", openid);
		}
		return eggsMapper.findEggsQRCodeRecordByMap(hashMap);
	}
	
	public int addMenuEggs(JSONObject jsonObject){
		FansUserInfo fansUserInfo = new FansUserInfo();
		fansUserInfo.setOpenid(jsonObject.getString("openid"));
		fansUserInfo.setChannel("eggs");
		fansUserInfo.setSource("wechat");
		fansUserInfo.setMaterial(0);
		fansUserInfo.setStatus(1);
		fansUserInfo.setCreateDate(new Date());
		fansUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
		fansUserInfo.setNickname(StringUtil.encode(jsonObject.getString("nickname")));
		fansUserInfo.setUnionid(jsonObject.getString("unionid"));
		logger.info("非扫码注册：fansUserInfo " + fansUserInfo.toString());
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("openid", jsonObject.getString("openid"));
//		map.put("unionid", jsonObject.getString("unionid"));
//		FansUserInfo fansUser = eggsMapper.getEggsUserInfoByMap(map);
//		if(fansUser == null){
		eggsMapper.addEggsUserInfo(fansUserInfo);
//		}
		return fansUserInfo.getId();
	}
	
	private void addEggsRecod(int userId, String openid,String activicty_code) {
		
			// 被邀请者
			FansUserInfo beInvitUserInfo = getEggsUserInfo(null,openid, activicty_code);
			String unionId = "";
				if (beInvitUserInfo != null) {
					logger.info("被邀请者已经存在,邀请者userId={},被邀请者userId={}", userId,
							beInvitUserInfo.getUserId());
				}else{
					FansUserInfo fansUserInfo = new FansUserInfo();
					fansUserInfo.setOpenid(openid);
					fansUserInfo.setChannel("eggs");
					fansUserInfo.setSource("wechat");
					fansUserInfo.setMaterial(0);
					fansUserInfo.setStatus(1);
					fansUserInfo.setCreateDate(new Date());
					JSONObject jsonObject= wechatUtil.getWechatOpenId(wechatUtil.getToken().getString("access_token"), openid);
					logger.info("微信接口拿到用户信息的json数据openId="+openid+" ,jsonObject = " + jsonObject);
					int success = jsonObject.getInteger("code");
					FansUserInfo fansUser = new FansUserInfo();
					if(success == 0){
						JSONObject jsondData = jsonObject.getJSONObject("data");
						unionId = jsondData.getString("unionid");
						fansUserInfo.setHeadimgurl(jsondData.getString("headimgurl"));
						fansUserInfo.setNickname(StringUtil.encode(jsondData.getString("nickname")));
						fansUserInfo.setUnionid(jsondData.getString("unionid"));
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("openid", jsondData.getString("openid"));
						map.put("unionid", jsondData.getString("unionid"));
						fansUser = eggsMapper.getEggsUserInfoByMap(map);
					}else{
						throw new RuntimeException("生蛋活动获取微信用户信息异常。。。，插入用户数据失败");
					}
					fansUserInfo.setReferee_id(String.valueOf(userId));
					if(fansUser == null){
						eggsMapper.addEggsUserInfo(fansUserInfo);
					}
				}
				ScanQRCodeRecord record = new ScanQRCodeRecord();
				ScanQRCodeRecord eggRecord = getEggsQRCodeRecordById(0,openid, activicty_code);
//				String msg="";
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("unionid", unionId);
				List<Wechat> wechats = activityMapper.getWechatUser(map);
				logger.info("wechats ===========================" + wechats.size());
				if(!wechats.isEmpty() && wechats.size() > 0){
					record.setUser_type(EggsConstant.NO);//老用户
					record.setMaterial(0);
//					msg = EggsConstant.MSG;
				}else{
					if (eggRecord != null ) {
//						ScanQRCodeRecord eggRecordcode = getEggsQRCodeRecordById(userId,openid, activicty_code);
//						if(eggRecordcode == null){
//							logger.info("该用户在扫吗记录存在，但是不属于该用户邀请的 userId={} " , userId);
//							
//						}else{
//							logger.info("该用户扫码记录已经存在, 属于该邀请者userId={},被邀请者openid={} 不需要新增扫吗记录", userId,
//									openid);
//							msg = EggsConstant.MSG;
//						}
//					
						record.setUser_type(EggsConstant.NO);//老用户
						record.setMaterial(0);
					}else{
						record.setUser_type(EggsConstant.NEW); //新用户
						record.setMaterial(PrizeInfoConstant.PrizeMaterialConstant.FREQUENCY);
					}
				}
				
				// 邀请者
				FansUserInfo invitUserInfo = getEggsUserInfo(userId, null, null);
				if (invitUserInfo == null) {
					logger.info("邀请者数据已经被删除,邀请者userId={},被邀请者openid={}", userId, openid);
					return;
				}
				record.setCreate_date(new Date());
				record.setOpenid(openid);
				record.setActivicty_code(activicty_code);
				record.setId(IdUtil.generateUUID());
				record.setEggs_user_id(userId);
				
				//新用户才新增
//				if((StringUtils.isNotEmpty(msg) && EggsConstant.MSG.equals(msg)) || EggsConstant.NEW.equals(record.getUser_type())){
					logger.info("新增扫码记录record = " + record);
					eggsMapper.addEggsRecord(record);
//				}
				//老用户金蛋 +1
				if (beInvitUserInfo == null && eggRecord == null){
					//用户未关注过小牛钱罐子
						if(EggsConstant.NEW.equals(record.getUser_type())){
							logger.info("=================新增彩蛋======= +1=============");
							invitUserInfo.setMaterial(invitUserInfo.getMaterial()
									+ PrizeInfoConstant.PrizeMaterialConstant.FREQUENCY);
						}
					logger.info("邀请者昵称 nickname={} ,邀请者 userId={},被邀请者 openid={},新增扫码记录 ",
							new String[]{invitUserInfo.getNicknameDisplay(), String.valueOf(userId),
									openid});
					invitUserInfo.setId(userId);
					invitUserInfo.setUpdateDate(new Date());
					eggsMapper.updateEggsUserInfo(invitUserInfo);
				}
	}
	

	@Override
	public String sendRateToUser(String phone,int userId,String prizeId,String orderNumber) {
		
		String operators = SendRateUtil.searchOperators(phone);
		String postpackage = "";
		if(StringUtils.isNotEmpty(operators)){
			PrizeInfo prizeInfo = prizeInfoMapper.getPrizeInfoById(prizeId);
			String type = prizeInfo.getPrizeDesc();
			JSONObject json  = null;
			if(Carrieroperator.DX.getDesc().equals(operators)){
				if("10".equals(type)){
					postpackage = "DX10";
				}else if("30".equals(type)){
					postpackage = "DX30";
				}else if("50".equals(type)){
					postpackage = "DX50";
				}else if("150".equals(type)){
					postpackage = "DX200";
				}
				json= sendRateUtil.sendLMOrder(phone, postpackage,orderNumber);
			}else if(Carrieroperator.YD.getDesc().equals(operators)){
				if("10".equals(type)){
					postpackage = "YD10";
				}else if("30".equals(type)){
					postpackage = "YD30";
				}else if("50".equals(type)){
					postpackage = "YD70";
				}else if("150".equals(type)){
					postpackage = "YD150";
				}
				json = sendRateUtil.sendLMOrder(phone, postpackage,orderNumber);
			}else if(Carrieroperator.LT.getDesc().equals(operators)){
				if("10".equals(type)){
					postpackage = "LT20";
				}else if("30".equals(type)){
					postpackage = "LT50";
				}else if("50".equals(type)){
					postpackage = "LT50";
				}else if("150".equals(type)){
					postpackage = "LT200";
				}
				json = sendRateUtil.sendLMOrder(phone, postpackage,orderNumber);
			}
			if(json == null){
				sendShortSms(String.valueOf(prizeInfo.getBuyMaterial()),prizeInfo.getPrizeDesc(),operators,orderNumber);
				return WechatUtil.resultMessage(-1, "流米"+postpackage+"手机流量发送流量包失败");
			}
			json.put("orderNumber", orderNumber);
			json.put("carrieroperator", operators + "@" + postpackage +"手机流量包");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("updateDate", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			map.put("number", Integer.parseInt(type));
			map.put("type", "LM");
			eggsMapper.updateNumber(map);
			int number = eggsMapper.getNumber(map);
			if(number < 200000  && number > 150000){ //发送告警短信
				SendMsgReq req = new SendMsgReq("QGZ","TICKETLEFT","13686441896,13691865186","");
					String currentDate = DateUtil.DateToString(new Date(),
							"yyyy-MM-dd HH:mm:ss");
					req.setValue("生蛋活动,"+currentDate+",流米账户中心还剩"+number+"M手机流量！");
					try {
						iSmsService.sendMsg(req);
					} catch (Exception e) {
						logger.warn("告警短信发送失败" ,e);
					}
			}
			return WechatUtil.resultMessage(0, "流米"+ operators + "@" + postpackage +"发送流量包成功",json);
		}
		return WechatUtil.resultMessage(-1, "手机号不存在");
	}

	private void sendShortSms(String value,String value2,String value3,String orderNumber){
		SendMsgReq req = new SendMsgReq("QGZ","TICKETLEFT","13686441896,13691865186","");
		String currentDate = DateUtil.DateToString(new Date(),
				"yyyy-MM-dd HH:mm:ss");
		req.setValue("生蛋活动,"+currentDate+",该用户"+value+"金蛋兑换"+value2+"M流量失败 ("+value3+") -- >订单号为: "+orderNumber);
		logger.info("短信内容:" + req.getValue());
		try {
			iSmsService.sendMsg(req);
		} catch (Exception e) {
			logger.warn("告警短信发送失败" ,e);
		}
	}
	
	@Override
	public FansUserInfo getUserInfoById(int userId) {
		return  eggsMapper.getEggsUserInfoById(userId);
	}

	@Override
	public DataPage<EggsResp> queryEggsScanQRCodes(Integer userId,
			DataPage<EggsResp> dataPage) {
		int count = countqueryEggsScanQRCodes(userId);
		dataPage.setTotalCount(count);
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("userId", userId);
		hashMap.put("pageIndex", (dataPage.getPageIndex()-1) * 7);
		hashMap.put("pageSize", 7);
		logger.info("pageIndex =================" + (dataPage.getPageIndex()-1) * 7);
		logger.info("pageSize ==================  " + hashMap.get("pageSize"));
		logger.info("count =================" + count);
		List<EggsResp> list = eggsMapper.queryEggsScanQRCodes(hashMap);
		if(!list.isEmpty() && list.size() > 0){
			for (EggsResp eggsResp : list) {
				if(StringUtils.isNotEmpty(eggsResp.getCreate_date())){
					String value = eggsResp.getCreate_date();
					if(value.length() > 19){
						value = value.substring(0,value.length()-2);
					}
					eggsResp.setCreate_date(DateFormat.format(DateUtil.StringToDate(value)));
				}
				eggsResp.setNickname(StringUtil.decode(eggsResp.getNickname()));
			}
			dataPage.setList(list);
		}
		dataPage.setUserId(userId);
		return dataPage;
	}
	
	/**
	 * 查询用户的邀请总数
	 * 
	 * @param userId
	 * @return
	 */
	private int countqueryEggsScanQRCodes(Integer userId) {
		return eggsMapper.countqueryEggsScanQRCodes(userId);
	}
	public static void main(String[] args) {
		String value = "2015-12-12 12:14:00.0";
		System.out.println(value.length());
		value = value.substring(0,value.length()-2);
		System.out.println(value.length());
		System.out.println(value);
		System.out.println(DateFormat.format(DateUtil.StringToDate(value)));
		System.out.println(DateUtil.StringToString(value, DateStyle.YYYY_MM_DD_HH_MM_SS));
	}
}
