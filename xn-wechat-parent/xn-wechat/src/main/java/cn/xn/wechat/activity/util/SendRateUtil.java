package cn.xn.wechat.activity.util;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.xn.cache.service.ICommonRedisService;
import cn.xn.wechat.activity.constant.EggsConstant;
import cn.xn.wechat.activity.mapper.EggsMapper;
import cn.xn.wechat.activity.model.Carrieroperator;
import cn.xn.wechat.activity.model.LmHttpLog;
import cn.xn.wechat.activity.model.PlaceOrderRequest;
import cn.xn.wechat.activity.model.RateToken;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.HttpUtil;
import cn.xn.wechat.web.util.MD5Util;
import cn.xn.wechat.web.util.SHA1;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * 发送流量的 工具类
 * @author rod zhong 
 *
 */
@Component
public class SendRateUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SendRateUtil.class);
	
	private static int[] dx = {133,153,1700,177,180,181,189};
	private static int[] yd = {134,135,136,137,138,139,150,151,152,157,158,159,1705,178,182,183,184,187,188,147};
	private static int[] lt = {130,131,132,155,156,1709,176,185,186,145};
	
//	private static String sendRateUrl = "http://yfbapi.liumi.com/server/";
	
	@Resource
	private ICommonRedisService commonRedisService;

	@Resource
	private EggsMapper eggsMapper;
	
	@Resource
	private WechatUtil wechatUtil;
	
	public String getRateToken(){
		String token;
		try {
			token = commonRedisService.get(EggsConstant.BID, EggsConstant.EGGSTOKEN);
			if(StringUtils.isNotEmpty(token)){
				return token;
			}else{
				RateToken rateToken = eggsMapper.getRateToken("eggs");
				if(rateToken != null ){
					commonRedisService.set(EggsConstant.BID, EggsConstant.EGGSTOKEN, rateToken.getToken(), EggsConstant.EXPIRE);
					return rateToken.getToken();
				}
				return getHttpToken();
			}
		} catch (Exception e) {
			logger.warn("redis is exception " ,e);
			return getHttpToken();
		}
	}
	
	public String getHttpToken(){
		String url = wechatUtil.eggsLmServerUrl + "getToken";
		try {
		TokenRequest tokenRequest = new TokenRequest();
		final LmHttpLog apiLog = new LmHttpLog();
		apiLog.setUrl(url);
		apiLog.setMobile("token");
		apiLog.setOrderNumber("token");
		apiLog.setCreateDate(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		tokenRequest.setAppkey(wechatUtil.lmAppKey);
		String md5AppSecret = MD5Util.string2MD5(wechatUtil.lmAppSecret);
		tokenRequest.setAppsecret(md5AppSecret);
		tokenRequest.setSign(SHA1.getSHA1(tokenRequest.toSign()));
		logger.info("tokenRequest = " + tokenRequest.toString());
		String  param = JSONObject.toJSONString(tokenRequest);
		logger.info("请求连接url = " + url);
		logger.info("请求连接参数 param= " + param);
		apiLog.setParam(param);
		String result = HttpUtil.getInstance().phpPost(url,param);
		logger.info("流米接口返回的结果集 result = "+result);
		apiLog.setResult(result);
		//记录接口发送日志
		WechatUtil.poolThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				eggsMapper.saveApiLog(apiLog);
			}
		});
		if(StringUtils.isNotEmpty(result)){
			JSONObject resultJson = JSONObject.parseObject(result);
			String code = resultJson.getString("code");
			if(StringUtils.isNotEmpty(code) && "000".equals(code)){
				JSONObject jsonData = resultJson.getJSONObject("data");
				//redis 缓存token
			    commonRedisService.set(EggsConstant.BID, EggsConstant.EGGSTOKEN, jsonData.getString("token"), EggsConstant.EXPIRE);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("token", jsonData.getString("token"));
				map.put("type", "eggs");
					RateToken rateToken = eggsMapper.getRateToken("eggs");
					if(rateToken == null){
						map.put("createDate", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
						eggsMapper.saveRateToken(map);
					}else{
						map.put("updateTime", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
						eggsMapper.updateRateToken(map);
					}
				return jsonData.getString("token");
			}else{
				logger.warn("流米token接口获取失败");
			}
		}else{
			logger.warn("流米接口请求失败result is null");
		}
		} catch (Exception e) {
			logger.warn("流米接口请求失败" ,e);
		}
		return null;
	}
	
	public static String searchOperators(String phone){
		
		if(StringUtils.isNotEmpty(phone)){
			int codes = Integer.parseInt(phone.substring(0, 4));
			if(codes == 1700){
				return Carrieroperator.DX.getDesc();
			}else if(codes == 1705){
				return Carrieroperator.YD.getDesc();
			}else if(codes == 1709){
				return Carrieroperator.LT.getDesc();
			}else{
				int code = Integer.parseInt(phone.substring(0, 3));
				return operators(code);
			}
		}
		return null;
	}
	
	public static String operators(int code){
		
		for (int i = 0; i < dx.length; i++) {
			if(code == dx[i]){
				return Carrieroperator.DX.getDesc();
			}
		}
		for (int i = 0; i < yd.length; i++) {
			if(code == yd[i]){
				return Carrieroperator.YD.getDesc();
			}
		}
		for (int i = 0; i < lt.length; i++) {
			if(code == lt[i]){
				return Carrieroperator.LT.getDesc();
			}
		}
		return null;
	}
	
	public JSONObject sendLMOrder(String mobile,String postpackage,String orderNumber){
		String url = wechatUtil.eggsLmServerUrl + "placeOrder";
		logger.info("流米订单请求url: url = " + url);
		PlaceOrderRequest placeOrderRequest = orderCommon(mobile,postpackage,getHttpToken(),orderNumber);
		final LmHttpLog apiLog = new LmHttpLog();
		apiLog.setMobile(mobile);
		apiLog.setOrderNumber(orderNumber);
		apiLog.setUrl(url);
		apiLog.setCreateDate(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		try {
			String param = JSON.json(placeOrderRequest);
			apiLog.setParam(param);
			
			logger.info("流米订单请求参数: jsonObject = " + param);
			String result = HttpUtil.getInstance().phpPost(url,param);
			apiLog.setResult(result);
			//记录接口发送日志
			WechatUtil.poolThreadExecutor.execute(new Runnable() {
				@Override
				public void run() {
					eggsMapper.saveApiLog(apiLog);
				}
			});
			logger.info("流米订单请求结果集: result = " + result);
			if(StringUtils.isNotEmpty(result)){
				JSONObject resultJson = JSONObject.parseObject(result);
				String code = resultJson.getString("code");
				if(StringUtils.isNotEmpty(code) && "000".equals(code)){
					return resultJson.getJSONObject("data");
				}else{
					logger.warn("流米订单发送请求失败");
				}
			}else{
				logger.warn("流米订单请求失败 result is null");
			}
		} catch (Exception e) {
			logger.warn("流米订单异常 ," ,e);
		}
		return null;
	}
	
	private PlaceOrderRequest  orderCommon(String mobile,String postpackage,String token,String orderNumber){
		PlaceOrderRequest orderRequest = new PlaceOrderRequest();
		orderRequest.setAppkey(wechatUtil.lmAppKey);
		orderRequest.setAppver("Http");
		orderRequest.setExtno(orderNumber);
		orderRequest.setFixtime("");
		orderRequest.setMobile(mobile);
		orderRequest.setPostpackage(postpackage);
		orderRequest.setApiver("2.0");
		orderRequest.setDes("0");
		orderRequest.setToken(token);
		try {
			orderRequest.setSign(SHA1.getSHA1(orderRequest.toSign()));
			return orderRequest;
		} catch (NoSuchAlgorithmException e) {
			logger.warn("签名失败 " ,e );
			return null;
		}
	}
	
	public static void main(String[] args) {
//		Carrieroperator[] str = Carrieroperator.values();
//		for(Carrieroperator c :str ){
//			System.out.println(c.getCode() + "," + c.getDesc());
//		}
//		String phome = "135764255";
//		System.out.println(phome.substring(0, 3));
//		System.out.println(getHttpToken());
//		System.out.println(sendLMOrder("13686441896","YD10",DateUtil.DateToString(new Date(), DateStyle.YYYYMMDDHHMMSS + ""+ StringUtil.randomClickNumber())).toJSONString());
	}
	
}
