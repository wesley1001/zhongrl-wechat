package cn.xn.wechat.activity.model;

import java.util.Date;

public class UserQRcode {

	private String codeId;
	private String userId;
	private Date startDate;
	private Date endDate;
	private String qrCodeUrl;
	private int eggUserId;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public int getEggUserId() {
		return eggUserId;
	}

	public void setEggUserId(int eggUserId) {
		this.eggUserId = eggUserId;
	}

}
