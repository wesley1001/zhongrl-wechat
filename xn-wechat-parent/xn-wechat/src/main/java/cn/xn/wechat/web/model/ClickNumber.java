package cn.xn.wechat.web.model;

import java.io.Serializable;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：ClickNumber.java
 * 
 * 类描述：点击次数
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年7月30日 上午10:55:46
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
public class ClickNumber implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -8728830319693715318L;

	/** 
	* @Fields actualClickNumber : 实际点击次数
	*/ 
	private Integer actualClickNumber;
	
	/** 
	* @Fields showClickNumber : 展示的点击次数
	*/ 
	private Integer showClickNumber;
	
	private String code;
	
	/** 
	* @Fields activityType : 活动类型 :1.OODSOUND 好声音 
	*/ 
	private String activityType;
	
	

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getActualClickNumber() {
		return actualClickNumber;
	}

	public void setActualClickNumber(Integer actualClickNumber) {
		this.actualClickNumber = actualClickNumber;
	}

	public Integer getShowClickNumber() {
		return showClickNumber;
	}

	public void setShowClickNumber(Integer showClickNumber) {
		this.showClickNumber = showClickNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ClickNumber [ showClickNumber=" + showClickNumber
				+ ", code=" + code + "]";
	}
	
	
}
