package cn.xn.wechat.web.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xn.cache.service.ICommonRedisService;
import cn.xn.wechat.activity.constant.EggsConstant;
import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.service.EggsService;
import cn.xn.wechat.activity.service.IScanRecordService;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.customer.service.SendMessageIM;
import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.constant.MsgType;
import cn.xn.wechat.web.model.ImageMessage;
import cn.xn.wechat.web.model.ImageText;
import cn.xn.wechat.web.model.InputMessage;
import cn.xn.wechat.web.model.Item;
import cn.xn.wechat.web.model.KeyWord;
import cn.xn.wechat.web.model.MusicMessage;
import cn.xn.wechat.web.model.OutputMessage;
import cn.xn.wechat.web.model.ReplyMessage;
import cn.xn.wechat.web.model.VideoMessage;
import cn.xn.wechat.web.model.VoiceMessage;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.service.WeChatQrCodeService;
import cn.xn.wechat.web.service.WechatConfigService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;

/**
 * 微信接口工具类
 *
 */
public class WechatUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);
	
	private final String BID = "XNWECHAT";
	
	private final String KEY = "wechat_token";
	
	private final int TIME = 50;
	
	private final String TOKNE= "access_token";
	
	private String appId;
	
	private String appSecret;
	
	private String wechatName;
	
	public String eggsLmServerUrl;
	
	public  String lmAppKey;
	
	public String lmAppSecret;
	
	public String getLmAppKey() {
		return lmAppKey;
	}

	public void setLmAppKey(String lmAppKey) {
		this.lmAppKey = lmAppKey;
	}

	public String getLmAppSecret() {
		return lmAppSecret;
	}

	public void setLmAppSecret(String lmAppSecret) {
		this.lmAppSecret = lmAppSecret;
	}



	@Resource
	private ICommonRedisService commonRedisService;
	
	@Resource
	private EggsService eggsService;
	
	public static ExecutorService poolThreadExecutor = Executors.newFixedThreadPool(5);
	
	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getWechatName() {
		return wechatName;
	}
	
	public String getEggsLmServerUrl() {
		return eggsLmServerUrl;
	}

	public void setEggsLmServerUrl(String eggsLmServerUrl) {
		this.eggsLmServerUrl = eggsLmServerUrl;
	}



	/**
	 * 获取access token 接口凭证url
	 */
	public static final String url_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	/**
	 * 用户信息
	 */
	public static final String url_user_info = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
	
	@Resource
	private WeChatQrCodeService weChatQrCodeService;
	
	@Resource
	private WechatConfigService wechatConfigService;
	
	@Resource
	private IScanRecordService scanRecordService;
	
	@Resource
	private IWechatActivityService wechatActivityService;
	
	@Resource
	private ActivityService activityService;
	
	@Resource
	private SendMessageIM sendMessageIM;
	
	public static final class WechatResp {
		
		public boolean success = true;
		public String msg;
		private Map<String, Object> data = new HashMap<String, Object>();
		
		public void putData(String key, Object val) {
			data.put(key, val);
		}
		
		public Object getObject(String key) {
			return data.get(key);
		}
		
		public String getString(String key) {
			Object obj = data.get(key);
			return obj == null ? null : obj.toString();
		}
		
		public boolean isSuccess() {
			return success;
		}
		
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
		public String getMsg() {
			return msg;
		}
		
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}
	
	/**
	 * 使用微信code换取access_token 
	 * @param code
	 * @return
	 */
	public WechatResp wechatAccessToken(final String code) {
		WechatResp resp = new WechatResp();
		String url = MessageFormat.format(WechatUtil.url_access_token,this.appId,this.appSecret, code);
		logger.info("微信获取url :" + url);
		String accessTokenResp = HttpClientUtil.get(url);
		
		
		logger.info("微信获取access_token,code={},返回结果={}", code, accessTokenResp);
		JSONObject accessTokenRespJson = JSONObject.parseObject(accessTokenResp);
		
		if (accessTokenRespJson.get("errcode") == null) {
			resp.putData("access_token", accessTokenRespJson.get("access_token"));
			resp.putData("openid", accessTokenRespJson.getString("openid"));
			resp.putData("nickname", accessTokenRespJson.getString("nickname"));
			resp.putData("unionid", accessTokenRespJson.getString("unionid"));
			resp.putData("headimgurl", accessTokenRespJson.getString("headimgurl"));
			resp.putData("sex", accessTokenRespJson.getString("sex"));
			resp.putData("city", accessTokenRespJson.getString("city"));
			resp.putData("country", accessTokenRespJson.getString("country"));
		} else {
			resp.setSuccess(false);
			resp.setMsg(accessTokenRespJson.get("errmsg") == null ? "无效的code" : accessTokenRespJson.get("errmsg").toString());
		}
		
		return resp;
	}
	
	/**
	 * 获取微信用户个人信息  
	 * @param accessToken	调用凭证
	 * @param openid	用户标识,对当前开发者帐号唯一
	 * @return
	 */
	public WechatResp wechatUserinfo(final String accessToken, final String openid) {
		WechatResp resp = new WechatResp();
		String url = MessageFormat.format(WechatUtil.url_user_info, accessToken, this.appId);
		String userinfoResp = HttpClientUtil.get(url);
		logger.info("微信获取userinfo,access_token={},openid={},返回结果={}", 
				new Object[] { accessToken, openid, userinfoResp });
		JSONObject userinfoRespJson = JSONObject.parseObject(userinfoResp);
		
		if (userinfoRespJson.get("errcode") == null) {
			resp.putData("access_token", userinfoRespJson.getString("access_token"));
			resp.putData("openid", userinfoRespJson.getString("openid"));
			resp.putData("nickname", userinfoRespJson.getString("nickname"));
			resp.putData("unionid", userinfoRespJson.getString("unionid"));
			resp.putData("headimgurl", userinfoRespJson.getString("headimgurl"));
			resp.putData("sex", userinfoRespJson.getString("sex"));
			resp.putData("city", userinfoRespJson.getString("city"));
			resp.putData("country", userinfoRespJson.getString("country"));
		} else {
			resp.setSuccess(false);
			resp.setMsg(userinfoRespJson.get("errmsg") == null ? "无效的access_token" : userinfoRespJson.get("errmsg").toString());
		}
		
		return resp;
	}
	
	
	/**
	 *  根据openId 获取用户信息
	 * @param openid
	 * @return
	 */
	public JSONObject getWeixin(String openid) {
		try {
			String access_token = getToken().getString(TOKNE);//getAccessToken();
			
			return getWechatOpenId(access_token, openid);
		} catch (Exception e) {
			logger.warn("can not getWeixinNickName,exception = {}",e.getMessage());
		}
		return null;
	} 
	
	public JSONObject getWechatOpenId(final String accessToken, final String openid){
		String url = MessageFormat.format(Constant.GET_WEIXIN_OPENID, accessToken, openid);
		String userinfoResp = HttpClientUtil.get(url);
		logger.info(userinfoResp);
		logger.info("微信获取userinfo,access_token={},openid={},返回结果={}", 
				new Object[] { accessToken, openid, userinfoResp});
//		JSONObject userinfoRespJson = JSONObject.parseObject(userinfoResp);
		JSONObject result = WechatUtil.resultMessageObject(0, "返回成功", JSONObject.parseObject(userinfoResp));
//		WechatResp resp = new WechatResp();
//		if (userinfoRespJson.get("errcode") == null) {
//			logger.info("获取微信用户信息成功=================");
//			resp.putData("access_token", userinfoRespJson.getString("access_token"));
//			resp.putData("openid", userinfoRespJson.getString("openid"));
//			resp.putData("nickname", userinfoRespJson.getString("nickname"));
//			resp.putData("unionid", userinfoRespJson.getString("unionid"));
//			resp.putData("headimgurl", userinfoRespJson.getString("headimgurl"));
//			resp.putData("sex", userinfoRespJson.getString("sex"));
//			resp.putData("city", userinfoRespJson.getString("city"));
//			resp.putData("country", userinfoRespJson.getString("country"));
//		} else {
//			resp.setSuccess(false);
//			resp.setMsg(userinfoRespJson.get("errmsg") == null ? "无效的access_token" : userinfoRespJson.get("errmsg").toString());
//		}
		return result;
	}
	
	/** 
	* @Title: getToken 
	* @Description: 获取到token
	* @param @return
	* @param @throws Exception   
	* @return JSONObject   
	* @throws 
	*/
	public JSONObject getToken(){
		String value = null ;
		try {
			value = commonRedisService.get(BID, KEY);
			if(StringUtils.isNotEmpty(value)){
				JSONObject json = new JSONObject();
				json.put("access_token", value);
				return json;
			}else{
				JSONObject json = JSONObject.parseObject(JSON.toJSONString(activityService.getWechatConfig()));
				commonRedisService.set(BID, KEY, json.getString(TOKNE), TIME);
				return json;
			}
		} catch (Exception e) {
			logger.warn("获取token失败" ,e);
			return JSONObject.parseObject(JSON.toJSONString(activityService.getWechatConfig()));
		}
//		return JSONObject.parseObject(JSON.toJSONString(activityService.getWechatConfig()));
//		return JSONObject.parseObject(HttpClientUtil.get(Constant.SING_NAME_URL+"?appId=wx0b2f357a1ee329e0&secret=1986cb85efb2446b307a20aceb9fbcc6&grant_type="+Constant.CLIENT_CREDENTIAL));
	}
	
	/** 
	* @Title: getAccessToken 
	* @Description: 获取token  用户信息模块
	* @param @param code
	* @param @return   
	* @return String   
	* @throws 
	*/
	public JSONObject getAccessToken(String code){
		String tokenUrl = Constant.USER_TOKEN_URL + "appid="+this.appId+"&secret="+this.appSecret+"&code="+code+"&grant_type=authorization_code";
		logger.info("用户授权获取的url = " + tokenUrl);
		String result =  HttpClientUtil.get(tokenUrl);//HttpClientUtil.get(tokenUrl); //HttpUtil.getInstance().httpGet(tokenUrl);
		logger.info("用户授权返回的result = " + result);
		if(StringUtils.isNotEmpty(result)){
			JSONObject jsonObject = JSONObject.parseObject(result);
			if(jsonObject ==null ){
				logger.warn("用户授权获取token失败 ");
				return StringUtil.getJson(-1,"用户授权获取token失败",JSONObject.parseObject(result));
			}
			if(StringUtils.isNotEmpty(jsonObject.getString("access_token"))){
				//刷新token 
				return refreshToken(jsonObject);
			}else{
				logger.warn("用户授权获取token失败 ");
				return StringUtil.getJson(-1,"用户授权获取token失败",jsonObject);
			}
		}else{
			logger.warn("用户授权获取token失败 ");
			return StringUtil.getJson(-1,"用户授权获取token失败",null);
			
		}
	}
	
	/** 
	* @Title: refreshToken 
	* @Description: 刷新token
	* @param @param jsonObject
	* @param @return   
	* @return String   
	* @throws 
	*/
	public JSONObject refreshToken(JSONObject jsonObject){
		String refreshUrl = Constant.REFRESH_TOKEN_URL + "appid="+this.appId+"&grant_type=refresh_token&refresh_token="+jsonObject.getString("refresh_token");
		logger.info("用户刷新token的url = " + refreshUrl);
		String result = HttpClientUtil.get(refreshUrl);//HttpUtil.getInstance().httpGet(refreshUrl);//HttpClientUtil.get(refreshUrl);
		logger.info("用户刷新token的result = " + result);
		if(StringUtils.isNotEmpty(result)){
			JSONObject json = JSONObject.parseObject(result);
			String accessToken = json.getString("access_token");
			if(StringUtils.isNotEmpty(accessToken)){
				//获取用户信息
				return getWechatUser(json);
			}else{
				logger.warn("用户刷新token失败 ");
				return StringUtil.getJson(-1,"用户刷新token失败",json);
			}
		}else{
			logger.warn("用户刷新token失败 ");
			return StringUtil.getJson(-1,"用户刷新token失败",null);
		}
	}
	
	/** 
	* @Title: getWechatUser 
	* @Description: 拉取用户信息
	* @param @param json
	* @param @return   
	* @return String   
	* @throws 
	*/
	public JSONObject getWechatUser(JSONObject json){
		String getUserUrl = Constant.GET_USER_URL + "access_token="+json.getString("access_token")+"&openid="+json.getString("openid")+"&lang=zh_CN";
		logger.info("获取用户信息的url = " + getUserUrl);
		String result = HttpClientUtil.get(getUserUrl);
		logger.info("获取用户信息的result = " + result);
		final JSONObject jsonObject = JSONObject.parseObject(result);
		if(jsonObject==null){
			logger.warn("获取用户信息失败 ");
			return StringUtil.getJson(-1,"获取用户信息失败",jsonObject);
		}
		String openId = jsonObject.getString("openid");
		if(StringUtils.isNotEmpty(openId)){
			JSONObject nickName = new JSONObject();
			nickName.put("nickname", jsonObject.getString("nickname"));
			nickName.put("openid", jsonObject.getString("openid"));
			nickName.put("unionid", jsonObject.getString("unionid"));
			nickName.put("headimgurl", jsonObject.getString("headimgurl"));
			poolThreadExecutor.execute(new Runnable() {
				@Override
				public void run() {
					Wechat wechat = new Wechat();
					//以后取t_wechat表的nickname 需要 decode才能转码
					wechat.setNickname(StringUtil.encode(jsonObject.getString("nickname")));
					wechat.setOpenid(jsonObject.getString("openid"));
					wechat.setUnionid(jsonObject.getString("unionid"));
					wechat.setHeadimgurl(jsonObject.getString("headimgurl"));
					wechat.setCity(jsonObject.getString("city"));
					wechat.setSex(jsonObject.getString("sex"));
					wechat.setCountry(jsonObject.getString("country"));
					logger.info("map = " + wechat.toString());
					Wechat wc = wechatConfigService.getWechatUserDetails(wechat);
					if(wc == null){
						wechatConfigService.saveWechatUserDetails(wechat);
					}else{
						wechatConfigService.updateWechatUserDetails(wechat);
					}
					logger.info("该用户存在wechat  = " + wechat.toString());
				}
			});
			return StringUtil.getJson(0,"获取用户信息成功",nickName);
		}else{
			logger.warn("获取用户信息失败 ");
			return StringUtil.getJson(-1,"获取用户信息失败",jsonObject);
		}
	}
	
	
	/** 
	* @Title: acceptMessage 
	* @Description: 微信发送图文消息
	* @param @param request
	* @param @param response
	* @param @throws IOException   
	* @return void   
	* @throws 
	*/
	public void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        // 处理接收消息  
        ServletInputStream in = request.getInputStream();  
        // 将POST流转换为XStream对象  
        XStream xs = SerializeXmlUtil.createXstream();  
        xs.processAnnotations(InputMessage.class);  
        xs.processAnnotations(OutputMessage.class);  
        // 将指定节点下的xml节点数据映射为对象  
        xs.alias("xml", InputMessage.class);  
        // 将流转换为字符串  
        StringBuilder xmlMsg = new StringBuilder();  
        byte[] b = new byte[4096];  
        for (int n; (n = in.read(b)) != -1;) {  
            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
        }  
        // 将xml内容转换为InputMessage对象  
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString()); 
        //发送时间
        inputMsg.setCreateTime(new Date().getTime());
        // 取得消息类型  
        String msgType = inputMsg.getMsgType(); 
        logger.info("msgType : " + msgType);
        if(StringUtils.isNotEmpty(inputMsg.getFromUserName())){
        	 try {
             	logger.info("插入的 openId = " + inputMsg.getFromUserName());
     			getWeixin(inputMsg.getFromUserName());
     		} catch (Exception e1) {
     			logger.warn("插入失败");
     		}
        }
        if("event".equals(msgType)){
        	//扫码事件
        	event(inputMsg,response,xs);
        }else if ("text".equals(msgType)) {  
        	logger.info("推送文本的消息类型 集合 ： inputMsg : " + inputMsg.toString());
        	//发消息给IM 环信客服
        	try {
				sendMessageIM.sendTextMessageToEasemob(inputMsg.getContent(),inputMsg.getFromUserName(), inputMsg.getToUserName());
			} catch (Exception e) {
				logger.warn("推送消息失败");
			}
//			//拿到扫码后关注推送的消息
//			List<ReplyMessage> replyMessages= weChatQrCodeService.getWechatUser(inputMsg.getFromUserName());
//			logger.info("推送文本的消息类型 集合 ： replyMessage : " + replyMessages.toString());
//			for (ReplyMessage rm : replyMessages) {
//				if(StringUtils.isNotEmpty(rm.getText())){
//					if(StringUtil.indexOfString(inputMsg.getContent(), rm.getText())){
//						inputMsg.setContent(rm.getText());
//						break;
//					}
//				}
//			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("wechatName",this.wechatName);
			map.put("content", inputMsg.getContent());
			List<KeyWord> keyWords = wechatConfigService.getKeyNames(map);
			if(!keyWords.isEmpty() && keyWords.size() > 0){
				KeyWord kw = keyWords.get(0);
				logger.info("后台匹配上的消息 ： kw : " + kw.toString());
				if("text".equals(kw.getType())){
					inputMsg.setMsgType("text");
					inputMsg.setContent(kw.getText());
					// 根据消息类型获取对应的消息内容
		        	text(inputMsg,response);
				}else if("image".equals(kw.getType())){
//					inputMsg.setMsgType("image");
//					image(inputMsg,response,xs);
					pushText(inputMsg,response);
				}else if("voice".equals(kw.getType())){
//					inputMsg.setMsgType("voice");
//					voice(inputMsg,response,xs);
					pushText(inputMsg,response);
				}else if("video".equals(kw.getType())){
//					inputMsg.setMsgType("video"); 
//					video(inputMsg,response,xs);
					pushText(inputMsg,response);
				}else if("music".equals(kw.getType())){
//					inputMsg.setMsgType("music");
//					music(inputMsg,response,xs);
					pushText(inputMsg,response);
				}else if("news".equals(kw.getType())){
					inputMsg.setMsgType("news");
					inputMsg.setTitle(kw.getTitle());
					inputMsg.setDescription(kw.getDescription());
					inputMsg.setPicUrl(kw.getPicUrl());
					inputMsg.setURL(kw.getUrl());
					textImage(inputMsg,response,xs);
				}else{
					pushText(inputMsg, response);
				}
				logger.info("推送过后的的消息类型 集合 ： inputMsg : " + inputMsg.toString());
			}
			
        }else if ("image".equals(msgType)) {  
        	// 图片消息
//        	image(inputMsg,response,xs);
        	//发消息给IM 环信客服
        	sendMessageIM.sendImageMessageToEasemob(inputMsg.getFromUserName(), inputMsg.getToUserName(),inputMsg.getPicUrl());
//        	pushText(inputMsg,response);
        }else if("voice".equals(msgType)){
        	//回复语音
//        	voice(inputMsg,response,xs);
        	String voiceMediaId = inputMsg.getMediaId();// 用该mediaId去微信多媒体接口上下载文件
			//此处的逻辑应该是先把微信的语音文件上传到一个文件服务器中，可用url访问的地方，这里以上传到环信服务器为例
			String accessToken = null;
			try {
				accessToken = getToken().getString("access_token");//sendMessageIM.getAccessToken();
				logger.info("IM 获取微信的token");
				//此处的数值应该使用clientId和clientSecret到微信服务器调用API生成，并且是有有效期的
				String filePath = sendMessageIM.downloadMedia(accessToken,voiceMediaId,"/data/apache-tomcat-wechat/logs");//这个只是示例目录"/data/apps/opt/apache-tomcat-7.0.63/logs"
				logger.info("IM 获取微信的 voiceMediaId的url = " + filePath);
				logger.info("发送给IM 的参数 {filePath: " + filePath+",voiceMediaId:" + voiceMediaId +",FromUserName:" +inputMsg.getFromUserName() +",ToUserName:"+inputMsg.getToUserName()+"}");
				sendMessageIM.sendVoiceMessageToEasemob(filePath,inputMsg.getFromUserName(), inputMsg.getToUserName(),voiceMediaId);
			} catch (Exception e) {
				logger.warn("获取token 或者发送消息失败 : " , e);
			}
			//默认推送钱罐子的关注消息
//        	pushText(inputMsg,response);
        }else if("video".equals(msgType)){//回复视频
//        	video(inputMsg,response,xs);
        	pushText(inputMsg,response);
        }else if("music".equals(msgType)){//回复音乐
//        	music(inputMsg,response,xs);
        	pushText(inputMsg,response);
        }else if("news".equals(msgType)){//回复图文 news
//        	textImage(inputMsg,response,xs);
        	pushText(inputMsg,response);
        }
        //转发给客服
//        if(!"subscribe".equals(inputMsg.getEvent()) || !"unsubscribe".equals(inputMsg.getEvent())){
//        	inputMsg.setMsgType("transfer_customer_service");
//        	multiCustomerService(inputMsg,response,xs);
//        }
    	
    }  
	
	/**
	 * 推送默认文本消息
	 * @param inputMsg
	 * @param response
	 */
	private void pushText(InputMessage inputMsg,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("wechatName",this.wechatName);
		map.put("content", "DEFAULT");
		inputMsg.setMsgType("text");
		List<KeyWord> list  = wechatConfigService.getKeyNames(map);
		if(list.isEmpty() || list.size() < 0){
			return ;
		}
		inputMsg.setContent(wechatConfigService.getKeyNames(map).get(0).getText());
		text(inputMsg,response);//关注推送消息
	}
	
	/** 
	* @Title: multiCustomerService 
	* @Description: 多客服
	* @param @param inputMsg
	* @param @param response
	* @param @param xs   
	* @return void   
	* @throws 
	*/
	private void multiCustomerService(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		try {
			 
            Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
    		// 文本消息  
            StringBuffer str = new StringBuffer();  
            str.append("<xml>");  
            str.append("<ToUserName><![CDATA[" + inputMsg.getFromUserName() + "]]></ToUserName>");  
            str.append("<FromUserName><![CDATA[" + inputMsg.getToUserName() + "]]></FromUserName>");  
            str.append("<CreateTime>" + returnTime + "</CreateTime>");  
            str.append("<MsgType><![CDATA[" + inputMsg.getMsgType() + "]]></MsgType>");  
//            str.append("<TransInfo><KfAccount><![CDATA["+customerService+"]]></KfAccount></TransInfo>");//客服
            str.append("</xml>");  
			response.getWriter().write(str.toString());
			logger.info("text = xml转换：/n" + str);
		} catch (IOException e) {
			logger.warn("text 回复消息异常" , e);
		} 
	}
	
	
	/** 
	* @Title: event 
	* @Description: 扫码事件处理
	* @param @param inputMsg   
	* @return void   
	* @throws 
	*/
	private void event(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		String event = inputMsg.getEvent();
		logger.info("event " + event);
		String eventKey = inputMsg.getEventKey();
	    if(eventKey.contains("_")){
	    	String[] events = eventKey.split("_");
	    	eventKey = events[1];
	    }
	    final JSONObject wxObject= getWechatOpenId(getToken().getString("access_token"), inputMsg.getFromUserName());
	    try {
			if(wxObject != null){
				logger.info("微信接口拿到用户信息的json数据openId="+inputMsg.getFromUserName()+" ,jsonObject = " + wxObject);
				poolThreadExecutor.execute(new Runnable() {
					@Override
					public void run() {
						Wechat wechat = new Wechat();
						JSONObject jsonObject = wxObject.getJSONObject("data");
						//以后取t_wechat表的nickname 需要 decode才能转码
						
						if(StringUtils.isNotEmpty(jsonObject.getString("nickname"))){
							wechat.setNickname(StringUtil.encode(jsonObject.getString("nickname")));
							wechat.setOpenid(jsonObject.getString("openid"));
							wechat.setUnionid(jsonObject.getString("unionid"));
							wechat.setHeadimgurl(jsonObject.getString("headimgurl"));
							wechat.setSex(jsonObject.getString("sex"));
							logger.info("map = " + wechat.toString());
							Wechat wc = wechatConfigService.getWechatUserDetails(wechat);
							if(wc == null){
								wechatConfigService.saveWechatUserDetails(wechat);
							}else{
								logger.info("wc 存在的数据 " + JSON.toJSONString(wc));
								wechatConfigService.updateWechatUserDetails(wechat);
							}
						}
					}
				});
			}
		} catch (Exception e) {
			logger.warn("保存微信用户异常", e);
		}
		
    	if("subscribe".equals(event) || (StringUtils.isNotEmpty(eventKey) && StringUtils.isNumeric(eventKey))){
    		// 根据消息类型获取对应的消息内容
        	if(StringUtils.isNotEmpty(inputMsg.getTicket())){ //扫码 记录来源
        		Map<String,Object> map = new HashMap<String,Object>();
    		    //获取节点下名为show的子节点，如果节点下有多个show子节点，只获取第一个为show的子节点 
//    		    map.put("toUserName", inputMsg.getToUserName());
//    		    map.put("fromUserName", inputMsg.getFromUserName());
//    		    map.put("createTime", inputMsg.getCreateTime());
//    		    map.put("msgType", inputMsg.getMsgType());
//    		    map.put("event", event);
//    		    String eventKey = inputMsg.getEventKey();
//    		    if(eventKey.contains("_")){
//    		    	String[] events = eventKey.split("_");
//    		    	 eventKey = events[1];
//    		    }
    		    //微信扫扫活动
//    		    WechatActivity wxssActivity = wechatActivityService.getAvailableActivityByType(PrizeInfoConstant.WXSS_ACTIVITY_TYPE);
//    		    if(wxssActivity != null && PrizeInfoConstant.WXSS_ACTIVITY_TYPE.equals(wxssActivity.getActivityType())){
//    		    	logger.info("最新活动: wechatActivity = " + JSON.toJSONString(wxssActivity));
//		    		//eventKey = eventKey.replaceAll(wechatActivity.getActivityType(), "");
//    		    	logger.info("微信回调的参数:{userId = " + eventKey +", openId = " + inputMsg.getFromUserName()+"}");
//    		    	 if(StringUtil.isNumeric(eventKey)){
//    		    		 scanRecordService.addScanRecord(Integer.parseInt(eventKey) - 10, inputMsg.getFromUserName());
//    	    		 }else{
//    	    			 if(eventKey.contains("SMYQHD")){
//    	    				String userId = eventKey.replaceAll("SMYQHD", "");
//    	    				scanRecordService.addScanRecord(userId, inputMsg.getFromUserName());
//    	    			 }
//    	    		 }
//		    		// 添加扫码记录
//    		    	 if(StringUtil.isNumeric(eventKey)){
//    		    		 scanRecordService.addScanRecord(Integer.parseInt(eventKey) - Integer.parseInt(PrizeInfoConstant.WXSS_ACTIVITY_TYPE), inputMsg.getFromUserName());
//    	    		 }else{
//    	    			 if(eventKey.contains("SMYQHD")){
//    	    				String userId = eventKey.replaceAll("SMYQHD", "");
//    	    				scanRecordService.addScanRecord(userId, inputMsg.getFromUserName());
//    	    			 }
//    	    		 }
//    		    	logger.info("添加扫码记录成功！");
//    		    	//push临时二维码活动消息
//    		    	pushTemporaryMessage(inputMsg,response,xs,wxssActivity.getActivityType());
//    		    	logger.info("推送活动图文扫码记录成功！");
//    	    	}
//    		    //圣诞元旦活动
//    		    final WechatActivity sdActivity = wechatActivityService.getAvailableActivityByType(EggsConstant.SD_ACTIVITY_TYPE);
//    		    if(sdActivity != null && EggsConstant.SD_ACTIVITY_TYPE.equals(sdActivity.getActivityType())){
//    	    		logger.info("最新活动: sdActivity = " + JSON.toJSONString(sdActivity));
//		    		//eventKey = eventKey.replaceAll(wechatActivity.getActivityType(), "");
//    		    	logger.info("微信回调的参数:{userId = " + eventKey +", openId = " + inputMsg.getFromUserName()+"}");
//		    		// 添加扫码记录
//    		    	final int userId = Integer.parseInt(eventKey) - Integer.parseInt(EggsConstant.SD_ACTIVITY_TYPE);
//    		    	final String fromUserName = inputMsg.getFromUserName();
//    		    	poolThreadExecutor.execute(new Runnable() {
//    		    		@Override
//						public void run() {
//							eggsService.addEggs(userId, fromUserName,sdActivity.getActivityType());
//						}
//    		    	});
//    		    	logger.info("添加生蛋记录成功！");
//     		    	//push临时二维码活动消息
//     		    	pushTemporaryMessage(inputMsg,response,xs,sdActivity.getActivityType());
//     		    	logger.info("推送活动图文生蛋记录成功！");
//    	    	} else{//渠道永久二维码
	    		    map.put("eventKey", eventKey);
	    		    map.put("ticket", inputMsg.getTicket());
	    	    	weChatQrCodeService.saveQrCodeEvent(map);
	    	    	pushMessage(inputMsg,response,xs);
//    	    	}
        	}else{
        		pushText(inputMsg, response);
        	}
    	}
	}
	
	/**
	 *  获取微信用户信息
	 * @param openid
	 * @return
	 */
	public  JSONObject getWeixinUser(String openid,String token) {
		try {
			String url = MessageFormat.format(Constant.GET_WECHAT_USER, token, openid);
			logger.info("根据openid查询用户信息 =" + url);
			return JSON.parseObject(HttpClientUtil.get(url));
		} catch (Exception e) {
			logger.warn("can not getWeixinNickName,exception = {}",e.getMessage());
		}
		return null;
	}
	
	/**
	 *  推送临时消息
	 * @param inputMsg
	 * @param response
	 * @param xs
	 */
	private void pushTemporaryMessage(InputMessage inputMsg,HttpServletResponse response,XStream xs,String type){
		
		ReplyMessage replyMessage= weChatQrCodeService.getReplyMessage(type);
		if(replyMessage != null){
			if("text".equals(replyMessage.getMessageType())){ // 推送文本
				if(StringUtils.isNotEmpty(replyMessage.getMessageType())){
    				inputMsg.setMsgType(replyMessage.getMessageType());
    			}else{
    				inputMsg.setMsgType("text");
    			}
    			inputMsg.setContent(replyMessage.getText());
        		text(inputMsg,response);//关注推送消息
        		logger.info("text inputMsg = " + inputMsg);
    		}else if("news".equals(replyMessage.getMessageType())){//推送图文
    			inputMsg.setMsgType("news");
    			inputMsg.setTitle(replyMessage.getTitle());
    			inputMsg.setDescription(replyMessage.getDescription());
    			inputMsg.setPicUrl(replyMessage.getPicUrl());
    			inputMsg.setURL(replyMessage.getUrl());
    			logger.info("news inputMsg = " + inputMsg);
    			textImage(inputMsg,response,xs);
    		}
		}else{ //没有渠道 推送小牛钱罐子的消息
			pushText(inputMsg, response);
		}
	}
	
	/** 
	* @Title: pushMessage 
	* @Description: 推送微信消息
	* @param @param inputMsg
	* @param @param response
	* @param @param xs   
	* @return void   
	* @throws 
	*/
	private void pushMessage(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		logger.info("渠道推送的消息类型 集合 ： inputMsg : " + inputMsg.toString());
		//拿到扫码后关注推送的消息
		List<ReplyMessage> replyMessages= weChatQrCodeService.getWechatUser(inputMsg.getFromUserName());
		logger.info("渠道推送的消息类型 集合 ： replyMessage : " + replyMessages.toString());
		ReplyMessage replyMessage = null;
		if(!replyMessages.isEmpty() && replyMessages.size() > 0){
    		for (ReplyMessage rm :replyMessages) {
				if(StringUtils.isNotEmpty(rm.getEventKey())){
					replyMessage = rm;
					break;
				}
			}
		}
		
		if(replyMessage != null){
			pushText(inputMsg, response);
			if("text".equals(replyMessage.getMessageType())){ // 推送文本
    			inputMsg.setMsgType(replyMessage.getMessageType());
    			inputMsg.setContent(replyMessage.getText());
        		text(inputMsg,response);//关注推送消息
    		}else if("news".equals(replyMessage.getMessageType())){//推送图文
    			if(StringUtils.isNotEmpty(replyMessage.getMessageType())){
    				inputMsg.setMsgType(replyMessage.getMessageType());
    			}else{
    				inputMsg.setMsgType("news");
    			}
    			inputMsg.setTitle(replyMessage.getTitle());
    			inputMsg.setDescription(replyMessage.getDescription());
    			inputMsg.setPicUrl(replyMessage.getPicUrl());
    			inputMsg.setURL(replyMessage.getUrl());
    			textImage(inputMsg,response,xs);
    		}
		}else{ //没有渠道 推送小牛钱罐子的消息
			pushText(inputMsg, response);
		}
	}
	
	/** 
	* @Title: text 
	* @Description: 发送文本
	* @param @param inputMsg
	* @param @param response   
	* @return void   
	* @throws 
	*/
	private void text(InputMessage inputMsg,HttpServletResponse response){
		 try {
			 
            Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
    		// 文本消息  
            StringBuffer str = new StringBuffer();  
            str.append("<xml>");  
            str.append("<ToUserName><![CDATA[" + inputMsg.getFromUserName() + "]]></ToUserName>");  
            str.append("<FromUserName><![CDATA[" + inputMsg.getToUserName() + "]]></FromUserName>");  
            str.append("<CreateTime>" + returnTime + "</CreateTime>");  
            str.append("<MsgType><![CDATA[" + inputMsg.getMsgType() + "]]></MsgType>");  
            str.append("<Content><![CDATA[" + inputMsg.getContent() + "]]></Content>");  
            str.append("</xml>");  
			response.getWriter().write(str.toString());
			logger.info("text = xml转换：/n" + str);
		} catch (IOException e) {
			logger.warn("text 回复消息异常" , e);
		} 
	}
	
	/** 
	* @Title: image 
	* @Description: 回复图文消息
	* @param @param inputMsg
	* @param @param response
	* @param @param xs   
	* @return void   
	* @throws 
	*/
	private void textImage(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		
        try {
        	Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间 
    		OutputMessage outputMsg = new OutputMessage();  
            outputMsg.setFromUserName(inputMsg.getToUserName()); //服务器用户
            outputMsg.setToUserName(inputMsg.getFromUserName());  //客服端用户
            outputMsg.setCreateTime(returnTime);  
            outputMsg.setMsgType(inputMsg.getMsgType());  
            int count = inputMsg.getArticleCount() == 0 ? 1 : inputMsg.getArticleCount();
            outputMsg.setArticleCount(count);
            ImageText imageText = new ImageText(); 
            List<Item> items = new ArrayList<Item>();
            Item item = null;
            for (int i = 0; i < count; i++) {
            	item = new Item();
            	item.setTitle(inputMsg.getTitle());
            	item.setDescription(inputMsg.getDescription());
            	item.setPicUrl(inputMsg.getPicUrl());
            	item.setUrl(inputMsg.getURL());
            	items.add(item);
			}
            
            imageText.setItmes(items);
            outputMsg.setArticles(imageText);
            logger.info("image = xml转换：/n" + outputMsg.toString());
            logger.info("image = xml转换：/n" + xs.toXML(outputMsg));
			response.getWriter().write(xs.toXML(outputMsg));
		} catch (IOException e) {
			logger.warn("image 回复消息异常" , e);
		}  
	}
	
	/** 
	* @Title: image 
	* @Description: 回复语音消息
	* @param @param inputMsg
	* @param @param response
	* @param @param xs   
	* @return void   
	* @throws 
	*/
	private void music(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		
        try {
        	Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间 
    		OutputMessage outputMsg = new OutputMessage();  
            outputMsg.setFromUserName(inputMsg.getToUserName()); //服务器用户
            outputMsg.setToUserName(inputMsg.getFromUserName());  //客服端用户
            outputMsg.setCreateTime(returnTime);  
            outputMsg.setMsgType(inputMsg.getMsgType());  
            MusicMessage music = new MusicMessage(); 
            music.setTitle(inputMsg.getTitle());
            music.setDescription(inputMsg.getDescription());
            music.setMusicUrl(inputMsg.getMusicUrl());
            music.setHQMusicUrl(inputMsg.getHQMusicUrl());
            music.setThumbMediaId(inputMsg.getThumbMediaId());
            outputMsg.setMusic(music);
			response.getWriter().write(xs.toXML(outputMsg));
			logger.info("image = xml转换：/n" + xs.toXML(outputMsg));
		} catch (IOException e) {
			logger.warn("image 回复消息异常" , e);
		}  
	}
	
	/** 
	* @Title: image 
	* @Description: 回复音乐消息
	* @param @param inputMsg
	* @param @param response
	* @param @param xs   
	* @return void   
	* @throws 
	*/
	private void voice(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		
        try {
        	Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间 
    		OutputMessage outputMsg = new OutputMessage();  
            outputMsg.setFromUserName(inputMsg.getToUserName()); //服务器用户
            outputMsg.setToUserName(inputMsg.getFromUserName());  //客服端用户
            outputMsg.setCreateTime(returnTime);  
            outputMsg.setMsgType(inputMsg.getMsgType());  
            VoiceMessage voice = new VoiceMessage();  
            voice.setMediaId(inputMsg.getMediaId());  
            outputMsg.setVoice(voice);
			response.getWriter().write(xs.toXML(outputMsg));
			logger.info("image = xml转换：/n" + xs.toXML(outputMsg));
		} catch (IOException e) {
			logger.warn("image 回复消息异常" , e);
		}  
	}
	
	/** 
	* @Title: image 
	* @Description: 回复视频消息
	* @param @param inputMsg
	* @param @param response
	* @param @param xs   
	* @return void   
	* @throws 
	*/
	private void video(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		
        try {
        	Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间 
    		OutputMessage outputMsg = new OutputMessage();  
            outputMsg.setFromUserName(inputMsg.getToUserName()); //服务器用户
            outputMsg.setToUserName(inputMsg.getFromUserName());  //客服端用户
            outputMsg.setCreateTime(returnTime);  
            outputMsg.setMsgType(inputMsg.getMsgType());  
            VideoMessage video = new VideoMessage();  
            video.setMediaId(inputMsg.getMediaId());  
            video.setTitle(inputMsg.getTitle());
            video.setDescription(inputMsg.getDescription());
            outputMsg.setVideo(video);
			response.getWriter().write(xs.toXML(outputMsg));
			logger.info("image = xml转换：/n" + xs.toXML(outputMsg));
		} catch (IOException e) {
			logger.warn("image 回复消息异常" , e);
		}  
	}
	
	/** 
	* @Title: image 
	* @Description: 回复图片消息
	* @param @param inputMsg
	* @param @param response
	* @param @param xs   
	* @return void   
	* @throws 
	*/
	private void image(InputMessage inputMsg,HttpServletResponse response,XStream xs){
		
        try {
        	Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间 
    		OutputMessage outputMsg = new OutputMessage();  
            outputMsg.setFromUserName(inputMsg.getToUserName()); //服务器用户
            outputMsg.setToUserName(inputMsg.getFromUserName());  //客服端用户
            outputMsg.setCreateTime(returnTime);  
            outputMsg.setMsgType(inputMsg.getMsgType());  
            ImageMessage images = new ImageMessage();  
//            String imageUrl = MessageFormat.format(Constant.UPLOAD_IMAGE_URL,getToken().getString("access_token"),MsgType.IMAGE);
//            HttpClientUtil.post(imageUrl);
            images.setMediaId(inputMsg.getMediaId());
            outputMsg.setImage(images);  
            String xml = xs.toXML(outputMsg);
            xml = xml.replaceAll("<ArticleCount>[0]</ArticleCount>", "").trim();
			response.getWriter().write(xml);
			logger.info("image = xml转换：/n" + xml);
		} catch (IOException e) {
			logger.warn("image 回复消息异常" , e);
		}  
	}
	
	/** 
	* @Title: getWeChat 
	* @Description: 得到所要需要的微信信息
	* @param @return   
	* @return WechatConfig   
	* @throws 
	*/
	public WechatConfig getWeChat(String wechatType){
		
		return wechatConfigService.getWeChat(wechatType);
	}
	
	/** 
	* @Title: createTemporaryQrCode 
	* @Description: 生成临时带参数二维码  保留时间 7天
	* @param param  用户ID
	* @return String   
	* @throws 
	*/
	public String createTemporaryQrCode(int param,int expireSeconds){
		if(param > 0){
			JSONObject token = null;
			try {
				token = getToken();
			} catch (Exception e) {
				logger.warn("get token warn ！" , e);
				return resultMessage(-1,"token is null");
			}
			if(token != null && StringUtils.isNotEmpty(token.getString("access_token"))){
				JSONObject postRequest = new JSONObject();
				JSONObject scene = new JSONObject();
				JSONObject scene_str = new JSONObject();
				scene_str.put("scene_id", param);
				
				postRequest.put("expire_seconds", expireSeconds);
				scene.put("scene", scene_str);
				//临时二维码
				postRequest.put("action_name", Constant.TEMPORARY_ACTION_NAME);//Constant.ACTION_NAME);
				postRequest.put("action_info", scene);
				try {
					logger.info("request param : " + postRequest.toString()+"\n");
					String result = HttpUtil.getInstance().phpPost(Constant.QR_CODE + token.getString("access_token"), postRequest);
					logger.info("微信生成临时二维码result : " + result);
					JSONObject resultObject = (JSONObject)JSONObject.parseObject(result);
					if(resultObject.get("errcode") !=null || "40001".equals(resultObject.getString("errcode"))){
						JSONObject jsonObj = JSONObject.parseObject(HttpClientUtil.get(Constant.SING_NAME_URL+"?appId="+this.appId+"&secret="+this.appSecret+"&grant_type="+Constant.CLIENT_CREDENTIAL));
						
						String ticket = jsonObj.getString("ticket");
						if(StringUtils.isNotEmpty(ticket)){
							String getQrticketUrl = Constant.GET_TICKET_URL + ticket;
							logger.info(" 生成临时二维码返回的数据 : " + getQrticketUrl);
							return resultMessage(0,"success",getQrticketUrl);
						}else{
							return resultMessage(-1,"ticket warn");
						}
					}else{
						String ticket = resultObject.getString("ticket");
						if(StringUtils.isNotEmpty(ticket)){
							String getQrticketUrl = Constant.GET_TICKET_URL + ticket;
							logger.info(" 生成临时二维码返回的数据 : " + getQrticketUrl);
							return resultMessage(0,"success",getQrticketUrl);
						}else{
							return resultMessage(-1,"ticket warn");
						}
					}
				} catch (Exception e) {
					logger.warn("qr warn" , e);
					return resultMessage(-1,"qr warn");
				}
			}else{
				logger.warn("token is null ");
				return resultMessage(-1,"token is null");
			}
		}else{
			logger.warn("channel is null");
			return resultMessage(-1,"channel is null");
		}
	}
	
	/**
	 * 返回的结果集
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static String resultMessage(int code,String msg,JSONObject data){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("data", data == null ? null : data);
		return json.toJSONString();
	}
	
	/**
	 * 返回的结果集
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static JSONObject resultMessageObject(int code,String msg,JSONObject data){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("data", data == null ? null : data);
		return json;
	}
	
	/**
	 * 返回的结果集
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static String resultMessage(int code,String msg,String image){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("qrImage",image);
		return json.toJSONString();
	}
	
	/**
	 * 返回的结果集
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static String resultMessage(int code,String msg){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}
	
	/**
	 * 返回的结果集
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static String resultMessage(int code,String msg,JSONArray data){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}
	
	/**
	 *  生成短链接
	 * @param url
	 * @return
	 */
	public String createShortLink(String url){
		if(StringUtils.isEmpty(url)){
			logger.warn("url is null ");
			return url;
		}else if(url.length() < 120){
			logger.warn("url 长度不够规则生成短链接");
			return url;
		}
		JSONObject token = null;
		try {
			token = getToken();
		} catch (Exception e) {
			logger.warn("get token warn ！" , e);
			return url;
		}
		if(token != null && StringUtils.isNotEmpty(token.getString("access_token"))){
			JSONObject postRequest = new JSONObject();
			postRequest.put("action", "long2short");
			postRequest.put("long_url", url);
			try {
				String result = HttpUtil.getInstance().phpPost(Constant.SHORT_LINK + token.getString("access_token"), postRequest);
				JSONObject jsonResult = (JSONObject)JSONObject.parseObject(result);
				if(jsonResult != null && jsonResult.getInteger("errcode") == 0){
					logger.info("生成短链接成功 : " + jsonResult);
					return jsonResult.getString("short_url");
				}else{
					logger.warn("生成短链接失败 : " + jsonResult);
					return url;
				}
			} catch (Exception e) {
				logger.warn("生成短链接失败",e);
				return url;
			} 
		}else{
			logger.warn("token is null ");
			return url;
		}
	}

	
	public static void main(String[] args) {
//		String s= "aaa<ArticleCount>0</ArticleCount>sss";
//		s = s.replaceAll("<ArticleCount>[0]</ArticleCount>", "");
//		System.out.println(s.trim());0119cc88534f4ad02d545c98beeb5228
//		System.out.println("=====================");
//		System.out.println(createTemporaryQrCode("5555SMYQXNHD"));
//		WechatUtil wechat = new WechatUtil();
//		System.out.println(wechat.createTemporaryQrCode(12311,2592000));
		
//		ExecutorService singleThreadExecutor=Executors.newSingleThreadExecutor();
//		for(int i=0;i<10;i++)
//		{
//		    final int index=i;
//		    singleThreadExecutor.execute(new Runnable(){
//					@Override
//					public void run(){
//					   try{
//				            System.out.println(index);
//				            Thread.sleep(2000);
//					    }catch(InterruptedException e){
//				            //TODO Auto-generated catch block
//				            e.printStackTrace();
//						}
//				   }
//		    });
//		}
		 String imageUrl = MessageFormat.format(Constant.UPLOAD_IMAGE_URL,"SpE8oHD0I5cz0s1b8Ow5a9V6U3nHQhzWNK6X_8Vrri-ran_jS0pjKKU7leGAFXJPwBoxwwFr8YFliEey2BGuqbvVpTNAvzW1zNey8JFfyScKBKfAIAULU");
//        json  HttpClientUtil.post(imageUrl);
		
	}
}