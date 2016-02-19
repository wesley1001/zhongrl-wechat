package cn.xn.wechat.web.model;

import java.io.Serializable;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：WechatMenu.java
 * 
 * 类描述：微信菜单
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年8月25日 下午2:59:31
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class WechatMenu implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -7222889672891679739L;

	private int id;
	
	private String type;
	
	private String json;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
