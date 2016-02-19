package cn.xn.wechat.activity.model;

import java.io.Serializable;

public class LmHttpLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -594146289660289773L;

	private int id;
	
	private String mobile;
	
	private String orderNumber;
	
	private String param;
	
	private String url;
	
	private String result;
	
	private String createDate;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "LmHttpLog [id=" + id + ", mobile=" + mobile + ", orderNumber="
				+ orderNumber + ", param=" + param + ", url=" + url
				+ ", result=" + result + ", createDate=" + createDate + "]";
	}

}
