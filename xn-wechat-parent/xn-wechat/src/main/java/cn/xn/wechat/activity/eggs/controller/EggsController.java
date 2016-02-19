package cn.xn.wechat.activity.eggs.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xn.wechat.activity.constant.EggsConstant;
import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.exception.WechatException;
import cn.xn.wechat.activity.json.JsonMessage;
import cn.xn.wechat.activity.mapper.EggsMapper;
import cn.xn.wechat.activity.mapper.PrizeInfoMapper;
import cn.xn.wechat.activity.model.AddressVo;
import cn.xn.wechat.activity.model.EggsQRcode;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.LMOrder;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.ShareLog;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.resp.EggsResp;
import cn.xn.wechat.activity.service.EggsService;
import cn.xn.wechat.activity.service.IOrderService;
import cn.xn.wechat.activity.util.DataPage;
import cn.xn.wechat.activity.util.UserInfoUtil;
import cn.xn.wechat.web.base.BaseController;
import cn.xn.wechat.web.mapper.WechatConfigMapper;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.util.DateFormat;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.StringUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 生蛋活动
 */
/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/activity/eggs/")
public class EggsController extends BaseController {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private EggsMapper eggsMapper;
	@Resource
	private EggsService eggsService;
	@Resource
	private PrizeInfoMapper prizeInfoMapper;
	@Resource
	private IOrderService orderService;
	@Resource
	private WechatUtil wechatUtil;
	@Resource
	private UserInfoUtil userInfoUtil;
	@Resource
	private WechatConfigMapper wechatConfigMapper;
	
	/**
	 *  获取用户
	 * @param userId
	 * @return
	 */
	@RequestMapping("getEggsUserInfo")
	@ResponseBody
	public JsonMessage getEggsUserInfo(Integer userId,String code,HttpServletResponse response){
//		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			if(StringUtils.isNotEmpty(code) && (userId != null && userId > 0)){
				logger.info("code Id= " + code);
				Map<String,Object> map = new HashMap<String, Object>();
				JSONObject jsonObject = wechatUtil.getAccessToken(code);
				logger.info("微信返回的数据 : " + jsonObject.toJSONString());
				if(jsonObject.getInteger("code") != 0){ 
					logger.info("user Id =" + userId);
					FansUserInfo fansUserInfo = eggsMapper.getEggsUserInfoById(userId);
					fansUserInfo.setNickname(StringUtil.decode(fansUserInfo.getNickname()));
					fansUserInfo.setUserId(String.valueOf(userId));
					return JsonMessage.getSuccessJson(fansUserInfo);
				}
				JSONObject jsonData = jsonObject.getJSONObject("data");
				if(jsonData != null){
					logger.info("微信用户信息 code = "+code+", jsonObject = " + jsonData.toJSONString());
					map.put("openid", jsonData.getString("openid"));
					map.put("unionid", jsonData.getString("unionid"));
					FansUserInfo fansUserInfo = eggsMapper.getEggsUserInfoByMap(map);
					if(fansUserInfo == null){
						//注册新用户
						Integer id= eggsService.addMenuEggs(jsonData);
						logger.info("新用户自增ID = " + id );
						map.put("id", id);
						fansUserInfo = eggsMapper.getEggsUserInfoById(id);
					}
					fansUserInfo.setNickname(StringUtil.decode(fansUserInfo.getNickname()));
					fansUserInfo.setUserId(String.valueOf(fansUserInfo.getId()));
					return JsonMessage.getSuccessJson(fansUserInfo);
				}
			}else if(userId != null && userId > 0){
				logger.info("user Id = " + userId);
				FansUserInfo fansUserInfo = eggsMapper.getEggsUserInfoById(userId);
				fansUserInfo.setNickname(StringUtil.decode(fansUserInfo.getNickname()));
				fansUserInfo.setUserId(String.valueOf(userId));
				return JsonMessage.getSuccessJson(fansUserInfo);
			}else if(StringUtils.isNotEmpty(code)){
				logger.info("code Id =" + code);
				Map<String,Object> map = new HashMap<String, Object>();
				JSONObject jsonObject = wechatUtil.getAccessToken(code);
				JSONObject jsonData = jsonObject.getJSONObject("data");
				if(jsonData != null){
					logger.info("微信用户信息 code = "+code+", jsonObject = " + jsonData.toJSONString());
					map.put("openid", jsonData.getString("openid"));
					map.put("unionid", jsonData.getString("unionid"));
					FansUserInfo fansUserInfo = eggsMapper.getEggsUserInfoByMap(map);
					if(fansUserInfo == null){
						//注册新用户
						Integer id= eggsService.addMenuEggs(jsonData);
						logger.info("新用户自增ID = " + id +"");
						map.put("id", id);
						fansUserInfo = eggsMapper.getEggsUserInfoById(id);
					}
					fansUserInfo.setNickname(StringUtil.decode(fansUserInfo.getNickname()));
					fansUserInfo.setUserId(String.valueOf(fansUserInfo.getId()));
					return JsonMessage.getSuccessJson(fansUserInfo);
				}
			}
			logger.warn("用户code ,userId is null");
			return JsonMessage.getErrorJson(-1,"用户不存在");
		}catch(Exception e){
			logger.warn("用户code ,userId is null");
			return JsonMessage.getErrorJson(-1,"用户不存在");
		}
	}
	
	private FansUserInfo getEggsUserInfoById(Integer userId) throws Exception {
		try {
			FansUserInfo userInfo = eggsMapper.getEggsUserInfoById(userId);
			if (userInfo != null) {
				return userInfo;
			}
			throw new WechatException(-1, "用户不存在");
		} catch (Exception e) {
			logger.info("查询用户出现异常,userId={},et={}", userId, e);
			throw new WechatException(-1, "系统异常");
		}
	}

	/**
	 * 立即兑换产品
	 */
	@RequestMapping("confirmChangePrize")
	@ResponseBody
	public JsonMessage confirmChangePrize(String prizeId,
			Integer userId,String mobile,HttpServletResponse response) {
		logger.info("user Id  " + userId +",mobile"+mobile); 
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			AddressVo vo = new AddressVo();
			if (StringUtils.isBlank(mobile)) {
				return JsonMessage.getErrorJson(-1,"手机号码不能为空!");
			}
			if (!isMobile(mobile)) {
				return JsonMessage.getErrorJson(-1,"手机号码格式不正确!");
			}
			vo.setMobile(mobile);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mobile", mobile);
//			List<LMOrder> list = eggsMapper.getRecordOfConversion(map);
//			if(list.size() > 20){
//				return JsonMessage.getErrorJson(1002, "亲，您的兑换次数已经上限，每个用户只能兑换10次流量包！谢谢您对这次活动的支持！");
//			}
			return orderService.confirmChangePrize(prizeId, userId, vo,
					EggsConstant.SD_ACTIVITY_TYPE);
		}catch(Exception e){
			logger.warn("兑换产品失败 : " );
			return JsonMessage.getErrorJson(-1,"兑换产品失败");
		}
	}
	
	@RequestMapping("eggss")
	@ResponseBody
	public String eggss(){
		String str = "INSERT INTO t_scan_activity_userinfo(nickname,emoji,unionid,openid,headimgurl,"
				+ "referee_id,channel,material,create_date,source,status,invitnum) VALUES (";
		List<Wechat> list = wechatConfigMapper.getWechatUserList();
		int size = list.size() > 0 ? list.size() : 0;
		 BufferedWriter writer = null ;
		 try {
			 writer = new BufferedWriter(new FileWriter(new File("D:\\Result.txt")));
			 logger.info("size = " + size );
			for(int i = 0; i < size ; i++){
				logger.info("第"+i+"条数据");
				String strss = "";
				strss = str + "'"+StringUtil.encode(list.get(i).getNickname())+"',2,'"+list.get(i).getUnionid()+"','"+list.get(i).getOpenid()+StringUtil.getRandomString(3)+"','"+list.get(i).getHeadimgurl()+"',42,'phwxsm',"
						+ "1,NOW(),'phwechat',1,0);";
					writer.write(strss + "\r\n");
					strss = "";
				}
			writer.close();
		} catch (IOException e1) {
		}
		return null;
	}
	

	/**
	 * 查询奖品记录
	 */
	@RequestMapping("findPrizeInfoList")
	@ResponseBody
	public JsonMessage findPrizeInfoList(@RequestParam Integer userId,HttpServletResponse response) {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			logger.info("生蛋活动.奖品列表查询 userId={}", userId);
			FansUserInfo userInfo = null;
			try {
				userInfo = getEggsUserInfoById(userId);
			} catch (Exception e) {
				return JsonMessage.getErrorJson(-1,"用户不存在!");
			}
			int material = userInfo.getMaterial();// 彩蛋数量
			WechatActivity wechatActivity = wechatActivityService
					.getAvailableActivityByType(EggsConstant.SD_ACTIVITY_TYPE);
			if(wechatActivity == null){
				logger.info("该活动没有配置产品");
				return JsonMessage.getErrorJson(-1,"没有配置产品");
			}
			String prizeIds = wechatActivity.getProductIds();
			logger.info("生蛋活动.奖品列表Ids={}", prizeIds);
			Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
			paramMap.put("productIds", prizeIds.split(","));
			paramMap.put("multipleStates", new int[] {
					PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2,
					PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_3 });
			List<PrizeInfo> prizes = prizeInfoMapper.findPrizeInfoList(paramMap);
			for (PrizeInfo prizeInfo : prizes) {
				prizeInfo.setUserMaterial(material);
				if (prizeInfo.getIsVirtual() == PrizeInfoConstant.PrizeTypeConstant.PRIZE_VIRTUAL_2) {// 虚拟物品
					if (prizeInfo.getStatus() == PrizeInfoConstant.PrizeStatusConstant.PRIZE_INFO_STATUS_2) {// 审核通过状态
						if (prizeInfo.getRemaNum() > 0
								&& material >= prizeInfo.getBuyMaterial()) {
							prizeInfo.setCanEhange(2);// 可以兑换
						}
					}
				}
			}
			return JsonMessage.getSuccessJson(prizes);
		}catch(Exception e){
			logger.warn("查询奖品记录 : " , e);
			return JsonMessage.getErrorJson(-1,"查询奖品记录失败");
		}
	}

	/**
	 * 分享日志记录
	 */
	@RequestMapping("shareLogRecord")
	@ResponseBody
	public JsonMessage shareLogRecord(@RequestParam String userId,
			String content, String activityType, HttpServletRequest request,HttpServletResponse response) {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			logger.info("分享二维码回调日志:userId={},record={}", userId, content);
			ShareLog sharelog = new ShareLog();
			sharelog.setUserId(userId);
			sharelog.setActivityType("11");
			String ipAddress = getIpAddr(request);
			sharelog.setIpAddress(ipAddress);
			sharelog.setContent(content);
			sharelog.setCreateDate(new Date());
			logger.info("分享二维码保存:sharelog={}", sharelog);
			try {
				eggsMapper.insertShareLog(sharelog);
				return JsonMessage.getSuccessJson("success");
			} catch (Exception e) {
				logger.warn("分享日志插入异常",e);
				return JsonMessage.getErrorJson("分享插入异常");
			}
		}catch(Exception e){
			logger.warn("分享日志记录 : ");
			return JsonMessage.getErrorJson(-1,"分享日志记录失败");
		}
	}

	/**
	 * 活动详情
	 */
	@RequestMapping("getActivity")
	@ResponseBody
	public JsonMessage getActivityByType(HttpServletResponse response) {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			return JsonMessage.getSuccessJson(wechatActivityService
				.getActivityByType(EggsConstant.SD_ACTIVITY_TYPE));
		}catch(Exception e){
			logger.warn("活动详情 : " );
			return JsonMessage.getErrorJson(-1,"活动详情失败");
		}
	}
	
	/**
	 * 首页数据， 获取好友列表  ，拿到用户信息
	 * @param code
	 * @param userId
	 * @return
	 */
	@RequestMapping("getBuddyList")
	@ResponseBody
	public JsonMessage getBuddyList(String code,String userId,Integer pageIndex,HttpServletResponse response){
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			logger.info("user Id ====================== " + userId ); 
			logger.info("pageIndex ==================  " + pageIndex);
			DataPage<EggsResp> data  = new DataPage<EggsResp>();
			if(pageIndex != null && pageIndex > 0){
				data.setPageIndex(pageIndex);
			}else{
				data.setPageIndex(1);
			}
			if(StringUtils.isNotEmpty(code)){//初始化页面
				Map<String,Object> map = new HashMap<String, Object>();
				JSONObject jsonObject = wechatUtil.getAccessToken(code);
				JSONObject jsonData = jsonObject.getJSONObject("data");
				if(jsonData != null){
					logger.info("微信用户信息 code = "+code+", jsonObject = " + jsonData.toJSONString());
					map.put("openid", jsonData.getString("openid"));
					map.put("unionid", jsonData.getString("unionid"));
					FansUserInfo fansUserInfo = eggsMapper.getEggsUserInfoByMap(map);
					if(fansUserInfo == null){
						//注册新用户
						int id= eggsService.addMenuEggs(jsonData);
						logger.info("==========getBuddyList=======新用户自增ID = " + id+"==============");
						return JsonMessage.getSuccessJson(eggsService.queryEggsScanQRCodes(Integer.parseInt(userId), data));
					}
					logger.info("用户信息 code = "+code+", fansUserInfo = " + fansUserInfo.toString());
					return JsonMessage.getSuccessJson(eggsService.queryEggsScanQRCodes(Integer.parseInt(userId), data));
				}else{
					logger.info("微信用户信息 code = "+code);
					return JsonMessage.getErrorJson("微信接口异常!");
				}
			}else{//用户ID 存在的情况
				if(StringUtils.isEmpty(userId)){
					logger.warn("用户userId is null");
					return JsonMessage.getErrorJson("用户不存在!");
				}
				FansUserInfo fansUserInfo = null;
				try {
					fansUserInfo = getEggsUserInfoById(Integer.parseInt(userId));
					logger.info("用户信息 userId = "+userId+", fansUserInfo = " + fansUserInfo);
				} catch (Exception e) {
					return JsonMessage.getErrorJson("用户不存在!");
				}
				if(fansUserInfo == null){
					return JsonMessage.getErrorJson("用户不存在!");
				}
				return JsonMessage.getSuccessJson(eggsService.queryEggsScanQRCodes(Integer.parseInt(userId), new DataPage<EggsResp>()));
			}
		}catch(Exception e){
			logger.warn("首页数据， 获取好友列表  ，拿到用户信息 : ");
			return JsonMessage.getErrorJson(-1,"首页数据， 获取好友列表  ，拿到用户信息失败");
		}
	}
	
	/**
	 * 用户兑换记录
	 * @param userId
	 * @return
	 */
	@RequestMapping("recordOfConversion")
	@ResponseBody
	public JsonMessage recordOfConversion(String userId,HttpServletResponse response){
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			if(StringUtils.isEmpty(userId)){
				logger.warn("用户userId is null");
				return JsonMessage.getErrorJson("用户不存在!");
			}
			logger.info("user Id ====================== " + userId); 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", Integer.parseInt(userId));
			List<LMOrder> list = eggsMapper.getRecordOfConversion(map);
			if(!list.isEmpty() && list.size() > 0){
				for(LMOrder lmOrder:list){
					if(StringUtils.isNotEmpty(lmOrder.getCreateDate())){
						String value = lmOrder.getCreateDate();
						if(value.length() > 19){
							value = value.substring(0,value.length()-2);
						}
						lmOrder.setCreateDate(DateFormat.format(DateUtil.StringToDate(value)));
					}
				}
			}
			return JsonMessage.getSuccessJson(list);
		}catch(Exception e){
			logger.warn("用户兑换记录 : ",e);
			return JsonMessage.getErrorJson(-1,"用户兑换记录失败");
		}
	}
	
	/**
	 * 查询我的二维码等其他信息
	 */
	@RequestMapping("getQrCodeUrlById")
	@ResponseBody
	public JsonMessage getQrCodeUrlById(@RequestParam String userId,HttpServletResponse response) {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			FansUserInfo userInfo = null;
			try {
				logger.info("user Id ====================== " + userId); 
				userInfo = getEggsUserInfoById(Integer.parseInt(userId));
				userInfo.setNickname(StringUtil.decode(userInfo.getNickname()));
			} catch (Exception e) {
				return JsonMessage.getErrorJson("用户不存在!");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (userInfo != null) {
				map.put("nickname", userInfo.getNickname());
				map.put("headimgurl", userInfo.getHeadimgurl());
				map.put("userId", Integer.parseInt(userId));
				map.put("activityType", EggsConstant.SD_ACTIVITY_TYPE);
				if(StringUtils.isNotEmpty(userInfo.getQrCodeUrl())){
					map.put("qrCodeUrl", userInfo.getQrCodeUrl());
				}else{
					int expireSeconds = 24 * 3600 * 30;
					String qrCodeUrl = userInfoUtil.getQrCodeUrl(Integer.parseInt(userId), Integer.parseInt(EggsConstant.SD_ACTIVITY_TYPE), expireSeconds);
					map.put("qrCodeUrl", qrCodeUrl);
					map.put("userId", Integer.parseInt(userId));
					map.put("activityType", EggsConstant.SD_ACTIVITY_TYPE);
					Date startDate = new Date();
					map.put("startDate", startDate);
					Calendar cal = Calendar.getInstance();
					cal.setTime(startDate);
					cal.add(Calendar.DATE, 30);
					Date endDate = cal.getTime();
					map.put("endDate", endDate);
					map.put("userId", Integer.parseInt(userId));
					EggsQRcode eggsQRcode = eggsMapper.getEggsQrCode(map);
					if(eggsQRcode == null){
						eggsMapper.addEggsQrCode(map);
					}else{
						eggsMapper.updateEggsQrCode(map);
					}
				}
			}
			return JsonMessage.getSuccessJson(map);
		}catch(Exception e){
			logger.warn("查询我的二维码等其他信息 : ",e);
			return JsonMessage.getErrorJson(-1,"查询我的二维码等其他信息失败");
		}
	}
	
	/**
	 * 查询用户的邀请记录 分页
	 */
	@RequestMapping("queryEggsScanQRCodes")
	@ResponseBody
	public JsonMessage queryEggsScanQRCodes(@RequestParam Integer userId,
			DataPage<EggsResp> page,HttpServletResponse response) {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			logger.info("user Id ====================== " + userId); 
			if (page == null) {
				page = new DataPage<EggsResp>();
			}
			logger.info("+ page.getPageIndex()====================== " + page.getPageIndex());
			page = eggsService.queryEggsScanQRCodes(userId, page);
			return JsonMessage.getSuccessJson(page);
		}catch(Exception e){
			logger.warn("查询用户的邀请记录: " );
			return JsonMessage.getErrorJson(-1,"查询用户的邀请记录失败");
		}
	}
	
	/**
	 *  补发流量
	 * @param userId
	 * @param mobile
	 * @param prizeId
	 * @return
	 */
	@RequestMapping("sendLm")
	@ResponseBody
	public JsonMessage sendLm(Integer userId,String mobile,String prizeId,HttpServletResponse response){
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			if(StringUtils.isEmpty(mobile)){
				return JsonMessage.getErrorJson("mobile is null");
			}
			if(StringUtils.isEmpty(prizeId)){
				return JsonMessage.getErrorJson("prizeId is null");
			}
			if(userId == null || userId <= 0){
				return JsonMessage.getErrorJson("userId is null");
			}
			logger.info("+ userId====================== " +userId +",prizeId====="+prizeId);
			 AddressVo vo = new AddressVo();
			 vo.setMobile(mobile);
			return orderService.confirmChangePrize(prizeId, userId, vo,
					EggsConstant.SD_ACTIVITY_TYPE);
		}catch(Exception e){
			logger.warn("补发流量: " );
			return JsonMessage.getErrorJson(-1,"补发流量失败");
		}
	}
	
	/**
	 * 创建临时二维码
	 * @param id
	 * @return
	 */
	@RequestMapping("createTemporaryQrCode")
	@ResponseBody
	public String createTemporaryQrCode(Integer id,HttpServletResponse response){
		//response.addHeader("Access-Control-Allow-Origin", "*");
		try{
			return wechatUtil.createTemporaryQrCode(id, EggsConstant.LONG_TIME);
		}catch(Exception e){
			logger.warn("补发流量: " , e);
			return WechatUtil.resultMessage(-1,"补发流量失败");
		}
	}
	
	@RequestMapping("encode")
	@ResponseBody
	public JsonMessage encodes(String text){
		if(StringUtils.isEmpty(text)){
			return JsonMessage.getErrorJson("text is null");
		}
		return JsonMessage.getSuccessJson(StringUtil.encode(text));
	}
	
	@RequestMapping("decode")
	@ResponseBody
	public JsonMessage decode(String text){
		if(StringUtils.isEmpty(text)){
			return JsonMessage.getErrorJson("text is null");
		}
		return JsonMessage.getSuccessJson(StringUtil.decode(text));
	}
	
	@RequestMapping("updateNumber")
	@ResponseBody
	public JsonMessage updateNumber(Integer number){
		if(number != null && number > 0){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("number", number);
			map.put("type", "LM");
			map.put("totle", number);
			map.put("updateDate", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			eggsMapper.updateTotleNumber(map);
		}
		return JsonMessage.getErrorJson("number is null");
	}
	
}
