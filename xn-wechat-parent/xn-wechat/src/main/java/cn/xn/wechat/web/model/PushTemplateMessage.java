package cn.xn.wechat.web.model;

import java.io.Serializable;

public class PushTemplateMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -100858890638786523L;

	private String url;
	
	private String paymentTemplateId;//回款提醒
	
	private String withdrawalsTemplateId;//提现审核
	
	private String accountremindTemplateId;//提现成功
	
	private String backnowredTemplateId; //红包返现

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPaymentTemplateId() {
		return paymentTemplateId;
	}

	public void setPaymentTemplateId(String paymentTemplateId) {
		this.paymentTemplateId = paymentTemplateId;
	}

	public String getWithdrawalsTemplateId() {
		return withdrawalsTemplateId;
	}

	public void setWithdrawalsTemplateId(String withdrawalsTemplateId) {
		this.withdrawalsTemplateId = withdrawalsTemplateId;
	}

	public String getAccountremindTemplateId() {
		return accountremindTemplateId;
	}

	public void setAccountremindTemplateId(String accountremindTemplateId) {
		this.accountremindTemplateId = accountremindTemplateId;
	}

	public String getBacknowredTemplateId() {
		return backnowredTemplateId;
	}

	public void setBacknowredTemplateId(String backnowredTemplateId) {
		this.backnowredTemplateId = backnowredTemplateId;
	}

	@Override
	public String toString() {
		return "PushTemplateMessage [url=" + url + ", paymentTemplateId="
				+ paymentTemplateId + ", withdrawalsTemplateId="
				+ withdrawalsTemplateId + ", accountremindTemplateId="
				+ accountremindTemplateId + ", backnowredTemplateId="
				+ backnowredTemplateId + "]";
	}
	
	
	
}
