package cn.xn.wechat.activity.model;

import java.io.Serializable;


public class LMOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6458712218268121807L;
	
	private int id;
	//本地订单编码
	private String orderNmuber;
	//手机号
	private String mobile;
	//运营商
	private String carrieroperator;
	//流量值
	private int flowValue; 
	//流米订单号
	private String orderNoLM;
	//状态
	private String satats;
	//消耗兑换金蛋
	private int goldenEggsNumber;
	//创建时间
	private String createDate;
	//用户ID
	private int eggsId;
	//修改时间
	private String updateDate;
	//奖品Id
	private String prizeId;
	
	private String prizeName;
	
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderNmuber() {
		return orderNmuber;
	}
	public void setOrderNmuber(String orderNmuber) {
		this.orderNmuber = orderNmuber;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCarrieroperator() {
		return carrieroperator;
	}
	public void setCarrieroperator(String carrieroperator) {
		this.carrieroperator = carrieroperator;
	}
	public int getFlowValue() {
		return flowValue;
	}
	public void setFlowValue(int flowValue) {
		this.flowValue = flowValue;
	}
	public String getOrderNoLM() {
		return orderNoLM;
	}
	public void setOrderNoLM(String orderNoLM) {
		this.orderNoLM = orderNoLM;
	}
	public String getSatats() {
		return satats;
	}
	public void setSatats(String satats) {
		this.satats = satats;
	}
	public int getGoldenEggsNumber() {
		return goldenEggsNumber;
	}
	public void setGoldenEggsNumber(int goldenEggsNumber) {
		this.goldenEggsNumber = goldenEggsNumber;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getEggsId() {
		return eggsId;
	}
	public void setEggsId(int eggsId) {
		this.eggsId = eggsId;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}
	@Override
	public String toString() {
		return "LMOrder [id=" + id + ", orderNmuber=" + orderNmuber
				+ ", mobile=" + mobile + ", carrieroperator=" + carrieroperator
				+ ", flowValue=" + flowValue + ", orderNoLM=" + orderNoLM
				+ ", satats=" + satats + ", goldenEggsNumber="
				+ goldenEggsNumber + ", createDate=" + createDate + ", eggsId="
				+ eggsId + ", updateDate=" + updateDate + ", prizeId="
				+ prizeId + ", prizeName=" + prizeName + "]";
	}
	
}
