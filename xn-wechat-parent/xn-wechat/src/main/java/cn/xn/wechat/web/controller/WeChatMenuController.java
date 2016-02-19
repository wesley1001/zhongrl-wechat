package cn.xn.wechat.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.model.WechatMenu;
import cn.xn.wechat.web.service.WechatConfigService;
import cn.xn.wechat.web.util.HttpClientUtil;
import cn.xn.wechat.web.util.HttpUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * 项目名称：uc
 * 
 * 类名称：WeChatMenuController.java
 * 
 * 类描述：微信菜单
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年8月12日 下午4:14:50
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@Controller
@RequestMapping("/weChatMenu/")
public class WeChatMenuController {

	private static Logger logger = LoggerFactory.getLogger(WeChatMenuController.class);
	
	@Resource
	private WechatConfigService wechatConfigService;
	
	@Resource
	private WechatUtil wechatUtil;
	
	/** 
	* @Title: wechatMenuPage 
	* @Description: 跳转到生成的菜单页面
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("wechatMenuPage")
	public String wechatMenuPage(ModelMap modelMap){
		modelMap.put("wechatConfigs", wechatConfigService.getWechatConfig());
		return "back-stage-manage/wechat_menu_page";
	}
	
	/** 
	* @Title: createWeChatMenu 
	* @Description: 生成菜单
	* @param @param jsonMenu
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping(value="createWeChatMenu",produces="text/json;charset=UTF-8")
	@ResponseBody
	public String createWeChatMenu(String jsonMenu,String wechatName,HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		JSONObject json = new JSONObject();
		if(StringUtils.isEmpty(jsonMenu)){
			logger.warn("生成菜单失败");
			return WechatUtil.resultMessage(-1,"jsonMenu is null");
		}
		logger.info("需要生成菜单的公众号 : wechatName ="  + wechatName);
		WechatConfig wechatConfig = wechatUtil.getWeChat(wechatName);
		logger.info("需要生成菜单的公众号 : wechatConfig ="  + wechatConfig);
		if(wechatConfig != null){
			jsonMenu = jsonMenu.trim();
			try {
				JSONObject jsonObject = wechatUtil.getToken();
				if(jsonObject != null){
					String access_token = jsonObject.getString("access_token");
					if(StringUtils.isNotEmpty(access_token)){
						String deleteResult = HttpClientUtil.get(Constant.DELETE_MENU + access_token);
						logger.info("菜单删除成功："+deleteResult);
						JSONObject deleteObject =JSONObject.parseObject(deleteResult);
						if(deleteObject.getInteger("errcode") == 0){
							String menuUrl = Constant.WX_MENU + access_token;
							String result = HttpUtil.getInstance().phpPost(menuUrl, JSONObject.parseObject(jsonMenu));
							if(StringUtils.isNotEmpty(result)){
								JSONObject meunResult = JSONObject.parseObject(result);
								int code = meunResult.getInteger("errcode");
								if(code == 0 ){
									WechatMenu wechatMenu = new WechatMenu();
									wechatMenu.setJson(jsonMenu);
									wechatMenu.setType(wechatName);
									wechatConfigService.updateWechatMenu(wechatMenu);
									logger.info("生成菜单成功");
									return WechatUtil.resultMessage(0,"生成菜单成功");
								}else{
									logger.warn("生成菜单失败");
									return WechatUtil.resultMessage(-1,"生成菜单失败");
								}
							}else{
								logger.warn("微信接口失败");
								return WechatUtil.resultMessage(-1,"微信接口失败");
							}
						}else{
							logger.warn("微信菜单删除失败");
							return WechatUtil.resultMessage(-1,"微信菜单删除失败");
						}
					}else{
						logger.warn("获取token失败");
						return WechatUtil.resultMessage(-1,"获取token失败");
					}
				}
			} catch (Exception e) {
				logger.warn("生成菜单失败",e);
				return WechatUtil.resultMessage(-1,"生成菜单失败");
			}
		}else{
			logger.warn("生成菜单失败");
			return WechatUtil.resultMessage(-1,"微信后台数据库没有配置");
		}
		return json.toJSONString();
	}
	
	
	
}
