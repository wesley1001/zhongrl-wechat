package cn.xn.wechat.activity.model;

import java.util.Date;

public class WechatActivity {

	private String id;
	private String activityName;
	private Date startDate;
	private Date endDate;
	private String productIds;
	private Date createDate;
	private Date updateDate;
	private String status;
	private String activityType;

	private String activityRule;

	public String getActivityRule() {
		return activityRule;
	}

	public void setActivityRule(String activityRule) {
		this.activityRule = activityRule;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public static enum ActivityStatus {

		NEW("new", "新建"), EXECUTING("executing", "执行中"), PAUSE("pause", "暂停"), FINISHED(
				"finished", "已完成"), DISCARDED("discarded", "已作废");

		private String text;
		private String val;

		private ActivityStatus(String val, String text) {
			this.val = val;
			this.text = text;
		}

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void SetCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
