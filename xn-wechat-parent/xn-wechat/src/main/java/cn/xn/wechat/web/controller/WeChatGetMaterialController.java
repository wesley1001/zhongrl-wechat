package cn.xn.wechat.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xn.wechat.web.service.WeChatGetMaterialService;


/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：WeChatGetMaterialController.java
 * 
 * 类描述：获取所有素材
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年8月24日 上午10:32:37
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@RequestMapping("/getMaterial/")
@Controller
public class WeChatGetMaterialController {

	private static Logger logger = LoggerFactory.getLogger(WeChatGetMaterialController.class);
	
	@Resource
	private WeChatGetMaterialService weChatGetMaterialService;
	
	@RequestMapping("materials")
	@ResponseBody
	public String getMaterials(String wechatName){
		String result = weChatGetMaterialService.getMaterials(wechatName);
		if(StringUtils.isNotEmpty(result)){
			return result;
		}
		return null;
	}
	
	
}
