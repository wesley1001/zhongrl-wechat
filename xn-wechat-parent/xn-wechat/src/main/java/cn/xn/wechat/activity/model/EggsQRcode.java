package cn.xn.wechat.activity.model;

import java.io.Serializable;
import java.util.Date;

public class EggsQRcode implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5992698063264740624L;
	private String codeId;
	private int userId;
	private Date startDate;
	private Date endDate;
	private String qrCodeUrl;
	private String activityType;
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}
	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	
}
