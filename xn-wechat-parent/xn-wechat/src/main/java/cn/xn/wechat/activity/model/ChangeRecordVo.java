package cn.xn.wechat.activity.model;

import java.util.Date;

/**
 * 兑换记录
 */
public class ChangeRecordVo {
	/**
	 * 兑换记录Id
	 */
	private String recordId;
	/**
	 * 奖品Id
	 */
	private String prizeId;
	/**
	 * 用户Id
	 */
	private String userId;
	/**
	 * 兑换日期
	 */
	private Date changeDate;
	/**
	 * 兑换状态
	 */
	private String status;
	/**
	 * 奖品名称
	 */
	private String prizeName;
	/**
	 * 所需原料
	 */
	private int material;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 1实物物品，2虚拟物品
	 */
	private int isVirtual;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 收货地址
	 */
	private String address;
	/**
	 * 用户姓名
	 */
	private String username;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(int isVirtual) {
		this.isVirtual = isVirtual;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
