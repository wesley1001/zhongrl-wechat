package cn.xn.wechat.web.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xn.cache.service.ICommonRedisService;
import cn.xn.freamwork.support.utils.HttpUtils;
import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.mapper.ActivityMapper;
import cn.xn.wechat.web.model.ClickNumber;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.HttpClientUtil;
import cn.xn.wechat.web.util.HttpUtil;
import cn.xn.wechat.web.util.StringUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：ActivityService.java
 * 
 * 类描述：活动业务层
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年7月30日 上午10:53:22
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@Service("activityService")
public class ActivityService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Resource
	private WechatUtil wechatUtil;
	
	@Resource
	private ICommonRedisService commonRedisService;
	
	private final String BID = "XNWECHAT";
	
	private final String KEY = "wechat_token";
	
	private final String BID_IMTOKEN = "IMTOKEN";
	
	private final String KEY_IM_KEY = "wechat_token";
	
	/** 
	* @Title: updateClikckNumber 
	* @Description: 记录点击次数
	* @param @return   
	* @return int   
	* @throws 
	*/
	public ClickNumber updateClickNumber(ClickNumber clickNumber){
		 activityMapper.updateClickNumber(clickNumber);
		 return activityMapper.getClickNumber(clickNumber);
	}
	
	/** 
	* @Title: saveQrCodeEvent 
	* @Description: 保存回调信息
	* @param @param map
	* @param @return   
	* @return int   
	* @throws 
	*/
	public int saveQrCodeEvent(Map<String,Object> map){
		return activityMapper.saveQrCodeEvent(map);
	}
	
	
	/** 
	* @Title: getToken 
	* @Description: 获取到token
	* @param @return
	* @param @throws Exception   
	* @return JSONObject   
	* @throws 
	*/
	public JSONObject getToken() throws Exception{
		//调用SDK 的本地连接
		Map<String,String> map = new HashMap<String,String>();
		map.put("appId", wechatUtil.getAppId());
		map.put("secret", wechatUtil.getAppSecret());
		map.put("grant_type", Constant.CLIENT_CREDENTIAL);
		logger.info("token = " + map.toString());
		JSONObject json = (JSONObject) JSONObject.parse(HttpUtils.doGet(Constant.SING_NAME_URL, map));
		WechatConfig wecaht =new WechatConfig();
		wecaht.setToken(json.getString("access_token"));
		wecaht.setType("ticket");
		wecaht.setUpdateTime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		activityMapper.updateWechatConfig(wecaht);
		return json;
	}
	
	/** 
	* @Title: getWechatTicket 
	* @Description:  获取到的token 入库
	* @param @return   
	* @return WechatConfig   
	* @throws 
	*/
	public WechatConfig getWechatTicket(){
		WechatConfig wechatConfig = new WechatConfig();
		wechatConfig.setType("ticket");
		try {
			JSONObject jsonObject = getToken();
			if(jsonObject != null){
				logger.debug("调用微信获取token接口返回的结果集" + jsonObject.toJSONString());
				String accessToken = jsonObject.getString("access_token");
				
				if(StringUtils.isNotEmpty(accessToken)){
					// 获取ticket js 签名
					getTicket(wechatConfig,accessToken);
				}else{
					logger.warn("获取accessToken失败！ 返回结果accessToken is null ");
				}
			}else{
				logger.warn("获取token失败！ 返回结果集 is null ");
			}
		} catch (Exception e) {
			logger.warn("获取token失败！" , e );
		}
		return wechatConfig;
	}
	
	/** 
	* @Title: getTicket 
	* @Description: 获取Ticket
	* @param @param wechatConfig
	* @param @param accessToken   
	* @return void   
	* @throws 
	*/
	private void getTicket(WechatConfig wechatConfig,String accessToken){
		wechatConfig.setToken(accessToken);
		//设置token 的值
		Map<String,String> jsapMap = new HashMap<String,String>();
		jsapMap.put("access_token", accessToken);
		jsapMap.put("type", Constant.WECHAT_TYPE);
		String jsapiResult = null ;
		try {
			jsapiResult = HttpUtils.doGet(Constant.JSAPI_URL, jsapMap);
			if(jsapiResult != null){
				JSONObject jsapiResultObject = (JSONObject)JSONObject.parse(jsapiResult);
				logger.info("微信token jsapiResultObject: " + jsapiResultObject.toJSONString());
				String ticket = jsapiResultObject.getString("ticket");
				if(StringUtils.isNotEmpty(ticket)){
					try {
						
						String value = commonRedisService.get(BID, KEY);
						if(StringUtils.isNotEmpty(value)){
							commonRedisService.del(BID, KEY);
						}
						commonRedisService.set(BID, KEY, accessToken, 50);
					} catch (Exception e) {
						logger.warn("redis is null ", e);
					}
					// 设置签名的值
					wechatConfig.setTicket(ticket);
					wechatConfig.setUpdateTime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
					activityMapper.updateWechatConfig(wechatConfig);
				}else{
					logger.warn("微信签名失败 ,获取的ticket is null ");
				}
			}else{
				logger.warn("微信签名失败 ,接口返回jsapiResult is null");
			}
		} catch (IOException e) {
			logger.warn("微信签名失败 " , e);
		}
		
	}
	
	public WechatConfig getWechatConfig(){
		WechatConfig wechatConfig = new WechatConfig();
		wechatConfig.setType("ticket");
		WechatConfig wc =  activityMapper.getWechatConfig(wechatConfig);
		if(wc == null){ //如果数据库没有就重新调接口取回token
			wc = getWechatTicket();
		}
		return wc;
	}
	
	/** 
	* @Title: createQrCode 
	* @Description: 调用微信二维码接口
	* @param @param channel
	* @param @return   
	* @return String   
	* @throws 
	*/
	public String createQrCode(String channel){
		if(StringUtils.isNotEmpty(channel)){
			JSONObject token = null;
			try {
				token = JSONObject.parseObject(JSON.toJSONString(getWechatConfig()));
				logger.info("token = " + token);
			} catch (Exception e) {
				logger.warn("get token warn ！" , e);
				return WechatUtil.resultMessage(-1,"token is null");
			}
			if(token != null && StringUtils.isNotEmpty(token.getString("access_token"))){
				JSONObject postRequest = new JSONObject();
				JSONObject scene = new JSONObject();
				JSONObject scene_str = new JSONObject();
				scene_str.put("scene_str", channel);
				scene.put("scene", scene_str);
				postRequest.put("action_name", Constant.ACTION_NAME);
				postRequest.put("action_info", scene);
				try {
					logger.info("request param : " + postRequest.toJSONString()+"\n");
					String result = HttpUtil.getInstance().phpPost(Constant.QR_CODE + token.getString("access_token"), postRequest);
					logger.info(" result : " + result);
					JSONObject resultObject = (JSONObject)JSONObject.parse(result);
					String ticket = resultObject.getString("ticket");
					if(StringUtils.isNotEmpty(ticket)){
						String getQrticketUrl = Constant.GET_TICKET_URL + ticket;
						JSONObject qrImage = new JSONObject();
						String resultJson = WechatUtil.resultMessage(0,"success",qrImage);
						qrImage.put("qrImage", getQrticketUrl);
						Map<String ,Object> map = new HashMap<String ,Object>();
						map.put("qrImage", getQrticketUrl);
						map.put("ticket", ticket);
						map.put("channel", channel);
						map.put("createDate", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
						activityMapper.saveQRImageAndTicket(map);
						return resultJson;
					}else{
						logger.info("ticket warn");
						return WechatUtil.resultMessage(-1,"ticket warn");
					}
				} catch (Exception e) {
					logger.warn("qr warn" , e);
					return WechatUtil.resultMessage(-1,"qr warn");
				}
			}else{
				logger.warn("token is null ");
				return WechatUtil.resultMessage(-1,"token is null");
			}
		}else{
			logger.warn("channel is null");
			return WechatUtil.resultMessage(-1,"channel is null");
		}
	}
	
	/**
	 *  定时器获取环信的token
	 * @param map
	 */
	public void saveIMToken(Map<String,String> map){
		activityMapper.saveIMToken(map);
	}
	
	private final int expire = 7200; // 7200分钟， 5天
	/**
	 * 
	 * 获取token
	 * @return
	 */
	public String getIMToken(){
		try {
			String value = commonRedisService.get(BID_IMTOKEN, KEY_IM_KEY);
			if(StringUtils.isEmpty(value)){
				value = activityMapper.getIMToken();
				commonRedisService.set(BID_IMTOKEN, KEY_IM_KEY, value, expire);
			}
			return value;
		} catch (Exception e) {
			logger.warn("redis is null " ,e);
			String token = activityMapper.getIMToken();
			return token;
		}
	}
	
	/**
	 *  处理微信
	 */
	public void wechatUser(){
		//处理微信的用户信息
		wechatUsers();
	}
	
	
	/**
	 *  拉取用户
	 */
	private String wechatUsers(){
		String token = null;
		try {
			JSONObject jsonToken = wechatUtil.getToken();
			token = jsonToken.getString("access_token");
			if(StringUtils.isEmpty(token)){
				logger.info("获取token 失败 ,token is null");
				return WechatUtil.resultMessage(-1, "获取token失败");
			}
		} catch (Exception e) {
			logger.info("获取token失败 " , e);
			return WechatUtil.resultMessage(-1, "获取token失败");
		}
		int count = 0;
		int total =0;
		JSONObject json = null;
		int number = 0;
		String url = Constant.WECHAT_USER_INTFACES + token; 
		logger.info("拉取微信的用户的url =  " + url);
		String result = HttpClientUtil.get(url);
		json = JSON.parseObject(result);
		if(json == null){
			logger.info("获取用户列表失败json is null ");
			token = wechatUtil.getToken().getString("access_token");
			result = HttpClientUtil.get(Constant.WECHAT_USER_INTFACES + token);
			json = JSON.parseObject(result);
		}
		logger.info("微信接口返回的json数据result = " + result);
		count = json.getIntValue("count");
		total = json.getIntValue("total");
		number += count;
		logger.info("初始化加载数据 number = " + number  +",count = " + count +",total = " + total);
		JSONArray jsonArrays = json.getJSONObject("data").getJSONArray("openid");
		resultSet(jsonArrays,token);
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		// 如果 第一次加载的用户数 没有超过1W 就不用执行这个
		do {
			url = Constant.WECHAT_USER_INTFACES + token +"&next_openid="+json.getString("next_openid"); 
			logger.info("拉取微信的用户的url =  " + url);
			result = HttpClientUtil.get(url);
			jsonObject = JSON.parseObject(result);
			if(jsonObject == null){
				logger.info("获取用户列表失败json is null ");
				token = wechatUtil.getToken().getString("access_token");
				url = Constant.WECHAT_USER_INTFACES + token +"&next_openid="+json.getString("next_openid"); 
				result = HttpClientUtil.get(url);
			}
			if(jsonObject.getInteger("errcode")!=null && 40013 == jsonObject.getInteger("errcode")){
				token = wechatUtil.getToken().getString("access_token");
			}
			logger.info("微信接口返回的json数据result = " + result);
			count = jsonObject.getIntValue("count");
			total = jsonObject.getIntValue("total");
			JSONObject jsonData = jsonObject.getJSONObject("data");
			if(jsonData == null){
				break;
			}
			jsonArray = jsonData.getJSONArray("openid");
			try {
				resultSet(jsonArray,token);
			} catch (Exception e) {
				continue;
			}
			number = number + count;
			logger.info("超过1W粉丝的数据: number = " + number  +",count = " + count +",total = " + total);
			if(number > total){
				break;
			}
			logger.info("===================微信关注总数=====total===== " + total  +"===========当前处理的人数=======number============" + number);
		} while (total >= number);
		
		return WechatUtil.resultMessage(0, "数据处理成功");
	}

	/**
	 *  处理OPENID
	 * @param jsonArray
	 */
	private void resultSet(JSONArray jsonArray,String token){
		int length = jsonArray.size() > 0 ? jsonArray.size() : 0;
		final List<Wechat> list = new ArrayList<Wechat>();
		int num = 0;
		for(int i = 0 ; i < length ; i++){
			try{
				//获取用户信息
				JSONObject json = wechatUtil.getWeixinUser(jsonArray.getString(i),token);
				if(json.getInteger("errcode")!=null && json.getInteger("errcode") != 0){
					token = wechatUtil.getToken().getString("access_token");
				}
				logger.info("获取微信用户的json : " + json);
				if(json.get("nickname") == null){
					continue;
				}
				Wechat wechat  = JSON.toJavaObject(json,  Wechat.class);
				if(StringUtils.isNotEmpty(wechat.getUnionid())){
					Map<String,Object> map =new HashMap<String,Object>();
					map.put("unionid", wechat.getUnionid());
					List<Wechat> wechats = activityMapper.getWechatUser(map);
					if(wechats.size() > 0){
						logger.info("该微信用户已经关注过小牛钱罐子 unionid = {}" ,wechat.getUnionid());
						continue;
					}
				}
				Timestamp time = new Timestamp(Long.parseLong(wechat.getSubscribe_time() + "000"));
				wechat.setSubscribe_time(DateUtil.toTimeStampFm(new Date(time.getTime())));
				if(StringUtils.isEmpty(wechat.getNickname())){
					wechat.setNickname("----");
				}
				wechat.setNickname(StringUtil.encode(wechat.getNickname()));
				wechat.setSex("1".equals(wechat.getSex()) ? "男":"女");
				list.add(wechat);
				if(i > 1 && i % 1000 == 0 ){
					num = num++;
					logger.info("=============================处理第 "+ num +" 批数据 =====================");
					cn.xn.wechat.web.util.WechatUtil.poolThreadExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(5000);
								activityMapper.saveWechat(list);
							} catch (InterruptedException e) {
								logger.info("====================线程执行失败===========",e.getMessage());
							}
							list.clear();
						}
					});
				}
				logger.info("处理第 "+ i +" 条数据 =====================");
			} catch (Exception e) {
				continue;
			}
		}
		
	}
	
}
