package cn.xn.wechat.web.controller;

import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xn.wechat.web.model.KeyWord;
import cn.xn.wechat.web.model.ReplyMessage;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.service.WechatConfigService;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.SHA1;
import cn.xn.wechat.web.util.StringUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：WeChatUserController.java
 * 
 * 类描述：获取微信用户信息
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年8月13日 上午10:06:55
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@RequestMapping("/wechat/")
@Controller
public class WeChatController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private WechatConfigService wechatConfigService;
	
	@Autowired
	private ActivityService activityService;
	
	@Resource
	private WechatUtil wechatUtil;
	
	@RequestMapping("code")
	public String getCode(){
		
		
		return "code";
	}
	
	@RequestMapping("wx-config")
	public String wxConfig(){
		
		return "back-stage-manage/wx_config";
	}
	
	/** 
	* @Title: getWeChatUser 
	* @Description: 获取用户信息
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping(value="getWeChatUser")
	@ResponseBody
	public JSONObject getWeChatUser(HttpServletRequest request,HttpServletResponse response){
		try {
			String code = request.getParameter("code");
			if(StringUtils.isNotEmpty(code)){
				logger.info("code = " + code);
				return wechatUtil.getAccessToken(code);
			}
		} catch (Exception e) {
			logger.warn("用户不同意授权",e);
		}
		return StringUtil.getJson(-1,"用户不同意授权",null);
	}
	
	/** 
	* @Title: saveWechatConfig 
	* @Description: 新增配置
	* @param @param modelMap
	* @param @param wechatConfig
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("saveWechatConfig")
	@ResponseBody
	public void saveWechatConfig(ModelMap modelMap,HttpServletRequest request,WechatConfig wechatConfig){
		try {
			if(wechatConfig != null){
				logger.info("wechatConfig : " + wechatConfig.toString());
				wechatConfig.setWecahtStatus("0");
				wechatConfigService.saveWechatConfig(wechatConfig);
			}
			
		} catch (Exception e) {
			logger.warn("微信配置失败" , e);
		}
		
	}
	
	/** 
	* @Title: configs 
	* @Description: 微信列表
	* @param @param modelMap
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("configs-list")
	public String configs(ModelMap modelMap,String page){
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		modelMap.put("wechatConfigs", wechatConfigService.getWechatConfig());
		modelMap.put("totalpage", wechatConfigService.getWechatConfigCount());
		modelMap.put("page", Integer.parseInt(page));
		modelMap.put("pagesize", 10);
		return "back-stage-manage/config_list";
	}
	
	/**
	 * @Title: signatureVerification
	 * @Description: 微信签名验证
	 * @param @param type
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("signatureVerification")
	@ResponseBody
	public String signatureVerification(String url,HttpServletResponse response) {
		if(StringUtils.isEmpty(url)){
			return WechatUtil.resultMessage(-1,"error : url is null");
		}
		logger.info("分享主页 : " + url);
		// 解决跨域问题
		//response.addHeader("Access-Control-Allow-Origin", "*");
		/*
		 * if (StringUtils.isNotEmpty(type)) { // 如果type 有值说明验证签名已过期 重新调用接口， //
		 * 否则直接取数据的token return
		 * JSON.toJSONString(activityService.getWechatTicket()); }
		 */
		WechatConfig wechatConfig = null;
		try {
			wechatConfig = activityService.getWechatConfig();
			JSONObject jsonWechatConfig = new JSONObject();
			// 随机字符串
			String noncestr = StringUtil.getRandomString(16);
			//时间
			String timestamp = String.valueOf(new Date().getTime());
			//拼接的URL
			String[] paramArr = new String[] { "jsapi_ticket=" + wechatConfig.getTicket(),
	                "timestamp=" + timestamp, "noncestr=" + noncestr, "url=" + url };
			Arrays.sort(paramArr);
			// 将排序后的结果拼接成一个字符串
			String content = paramArr[0].concat("&"+paramArr[1]).concat("&"+paramArr[2])
			                .concat("&"+paramArr[3]);
			logger.info("content : " + content);
			
			//签名算法
			String signature = new SHA1().getDigestOfString(content.getBytes());
			//jsonWechatConfig.put("ticket", wechatConfig.getTicket());
			jsonWechatConfig.put("signature", signature);
			jsonWechatConfig.put("noncestr", noncestr);
			jsonWechatConfig.put("timestamp", timestamp);
			jsonWechatConfig.put("appId",  wechatUtil.getAppId());
			jsonWechatConfig.put("url", url);
			return WechatUtil.resultMessage(0,"success",jsonWechatConfig);
		} catch (Exception e) {
			return WechatUtil.resultMessage(-1,"error");
		}
	}
	
	/** 
	* @Title: replyMessage 
	* @Description: 自定义回复消息页面
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("replyMessage")
	public String replyMessage(){
		
		return "back-stage-manage/message_text_image";
	}
	
	@RequestMapping("saveReplyMessage")
	@ResponseBody
	public void saveReplyMessage(ReplyMessage replyMessage){
		
		if(replyMessage != null ){
			try {
				logger.info("保存的参数 ： replyMessage = " + replyMessage);
				replyMessage.setCreateTime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
				wechatConfigService.saveReplyMessage(replyMessage);
			} catch (Exception e) {
				logger.warn("消息配置保存失败" , e);
			}
		}
	}
	
	@RequestMapping("replyMessages")
	public String replyMessages(ModelMap modelMap,String page){
		if(StringUtils.isEmpty(page)){
			page = "0";
		}
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("page", Integer.parseInt(page));
		map.put("pageSize", 10);
		List<ReplyMessage> replyMessages = wechatConfigService.replyMessages(map);
		modelMap.put("replyMessages", replyMessages);
		modelMap.put("totalPage", wechatConfigService.replyMessagesCount());
		modelMap.put("page", Integer.parseInt(page));
		modelMap.put("pageSize", 10);
		return "back-stage-manage/replyMessage_list";
	}
	
	@RequestMapping("deleteReplyMessage")
	@ResponseBody
	public void deleteReplyMessage(String id){
		logger.info("删除ID = " + id);
		if(StringUtils.isNotEmpty(id)){
			wechatConfigService.deleteReplyMessage(Integer.parseInt(id));
		}
	}
	
	/** 
	* @Title: editReplyMessage 
	* @Description: 微信消息修改页面
	* @param @param id
	* @param @param modelMap
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("editReplyMessage")
	public String editReplyMessage(String id,ModelMap modelMap){
		
		int editId = Integer.parseInt(id);
		modelMap.put("replyMessage", wechatConfigService.editReplyMessage(editId));
		modelMap.put("wechatConfigs", wechatConfigService.getWechatConfig());
		return "back-stage-manage/editReplyMessage";
	}
	
	/** 
	* @Title: updateReplyMessage 
	* @Description: 微信消息修改
	* @param @param replyMessage   
	* @return void   
	* @throws 
	*/
	@RequestMapping("updateReplyMessage")
	@ResponseBody
	public void updateReplyMessage(ReplyMessage replyMessage){
		wechatConfigService.updateReplyMessage(replyMessage);
	}
	
	/** 
	* @Title: keyWords 
	* @Description: 关键词列表
	* @param @param page
	* @param @param wechatName
	* @param @param modelMap
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("keyWords")
	public String keyWords(String page,String wechatName,ModelMap modelMap){
		if(StringUtils.isEmpty(page)){
			page = "0";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", Integer.parseInt(page));
		map.put("pageSize", 10);
		map.put("wechatName", wechatName);
		List<KeyWord> keyWords = wechatConfigService.getKeyWords(map);
		modelMap.put("keyWords", keyWords);
		modelMap.put("totalPage", wechatConfigService.getKeyWordCount(map));
		modelMap.put("page", Integer.parseInt(page));
		modelMap.put("pageSize", 10);
		modelMap.put("wechatName", wechatName);
		modelMap.put("wechatConfigs", wechatConfigService.getWechatConfig());
		return "back-stage-manage/keyWordList";
	}
	
	/** 
	* @Title: deleteKeyWord 
	* @Description: 删除
	* @param @param id   
	* @return void   
	* @throws 
	*/
	@RequestMapping("deleteKeyWord")
	@ResponseBody
	public void deleteKeyWord(String id){
		logger.info("删除ID = " + id);
		if(StringUtils.isNotEmpty(id)){
			wechatConfigService.deleteKeyWord(Integer.parseInt(id));
		}
	}
	
	/** 
	* @Title: getKeyWord 
	* @Description: 获取关键字
	* @param @param wechatName
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("editKeyWord")
	public String getKeyWord(String id,ModelMap modelMap){
		modelMap.put("wechatConfigs", wechatConfigService.getWechatConfig());
		modelMap.put("keyWord", wechatConfigService.getKeyWord(Integer.parseInt(id)));
		return "back-stage-manage/editKeyWord";
	}
	
	/** 
	* @Title: addKeyWord 
	* @Description: 新增页面
	* @param @param keyWord
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("addKeyWord")
	public String addKeyWord(ModelMap modelMap){
		modelMap.put("wechatConfigs", wechatConfigService.getWechatConfig());
		return "back-stage-manage/addKeyWord";
	}
	
	/** 
	* @Title: saveKeyWord 
	* @Description: 保存新增关键字
	* @param @param keyWord   
	* @return void   
	* @throws 
	*/
	@RequestMapping("saveKeyWord")
	@ResponseBody
	public void saveKeyWord(KeyWord keyWord){
		keyWord.setCreateTime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		wechatConfigService.saveKeyWord(keyWord);
	}
	
	/** 
	* @Title: updateKeyWord 
	* @Description: 修改关键字
	* @param @param keyword
	* @param @param wechatName   
	* @return void   
	* @throws 
	*/
	@RequestMapping("updateKeyWord")
	@ResponseBody
	public void updateKeyWord(KeyWord keyWord){
		wechatConfigService.updateKeyWord(keyWord);
	}
	
	/** 
	* @Title: updateKeyWord 
	* @Description: 获取token
	* @return void   
	* @throws 
	*/
	@RequestMapping("getAccessToken")
	@ResponseBody
	public String getAccessToken(){
		return JSON.toJSONString(activityService.getWechatConfig());
	}
}
