package cn.xn.wechat.web.model;

import java.io.Serializable;

/**
 *
 * 项目名称：xn-wechat
 * 
 * 类名称：ReplyMessage.java
 * 
 * 类描述：消息配置
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年8月26日 下午2:27:18
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class ReplyMessage implements Serializable{

	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 860074714794650436L;
	private Integer Id;
	private String wechatName;
	private String messageType;
	private String channel;
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	private String text;
	private String createTime;
	private String eventKey;
	
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "ReplyMessage [Id=" + Id + ", wechatName=" + wechatName
				+ ", messageType=" + messageType + ", channel=" + channel
				+ ", Title=" + Title + ", Description=" + Description
				+ ", PicUrl=" + PicUrl + ", Url=" + Url + ", text=" + text
				+ ", createTime=" + createTime + ", eventKey=" + eventKey + "]";
	}
	
}
