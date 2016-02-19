package cn.xn.wechat.web.model;

import java.io.Serializable;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：WechatConfig.java
 * 
 * 类描述：微信配置属性
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年7月30日 下午3:04:31
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class WechatConfig implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -1231910219356843410L;
	
	private int id;
	/** 
	* @Fields ticket : 微信签名
	*/ 
	private String ticket;
	/** 
	* @Fields token : 微信token
	*/ 
	private String token;
	
	/** 
	* @Fields type :  数据类型 ticket
	*/ 
	private String type;
	
	/** 
	* @Fields updateTime : 更新时间
	*/ 
	private String updateTime;
	
	/** 
	* @Fields appId : 微信ID
	*/ 
	private String appId;
	
	/** 
	* @Fields appSecret : 微信密钥
	*/ 
	private String appSecret;
	
	/** 
	* @Fields wechatName : 微信名称
	*/ 
	private String wechatName;
	
	/** 
	* @Fields wechatType : 微信类型
	*/ 
	private String wechatType;
	
	/** 
	* @Fields wecahtStatus : 微信启用状态
	*/ 
	private String wecahtStatus;
	
	private String access_token;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getWechatType() {
		return wechatType;
	}
	public void setWechatType(String wechatType) {
		this.wechatType = wechatType;
	}
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}
	public String getWecahtStatus() {
		return wecahtStatus;
	}
	public void setWecahtStatus(String wecahtStatus) {
		this.wecahtStatus = wecahtStatus;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "WechatConfig [id=" + id + ", ticket=" + ticket + ", token="
				+ token + ", type=" + type + ", updateTime=" + updateTime
				+ ", appId=" + appId + ", appSecret=" + appSecret
				+ ", wechatName=" + wechatName + ", wechatType=" + wechatType
				+ ", wecahtStatus=" + wecahtStatus + ", access_token="
				+ access_token + "]";
	}

	
}
