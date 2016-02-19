package cn.xn.wechat.web.model;

import java.io.Serializable;

/**
 *
 * 项目名称：xn-wechat
 * 
 * 类名称：KeyWord.java
 * 
 * 类描述：关键词
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年9月15日 上午9:46:01
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class KeyWord implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -3343188138973293221L;
	
	private int Id; //ID
	
	private String type;//推送类型
	
	private String keyName;//关键词
	
	private String Title;//标题名称
	
	private String Description;//描述
	
	private String PicUrl;//图片url
	
	private String Url;//图文url
	
	private String text;//文本
	
	private String createTime;
	
	private String wechatName;
	
	private String imageName;
	
	private String media;
	
	private String times;//时间戳
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
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
	
	

}
