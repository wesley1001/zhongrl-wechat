package cn.xn.wechat.web.model;

import java.io.Serializable;

public class PushMessageLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5723493174772756167L;

	private int Id;
	private String user_id;
	private String type;
	private String message;
	private String mq_topic;
	private String push_time;
	private String openId;
	private String unionId;
	private String real_name;
	private String state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMq_topic() {
		return mq_topic;
	}
	public void setMq_topic(String mq_topic) {
		this.mq_topic = mq_topic;
	}
	public String getPush_time() {
		return push_time;
	}
	public void setPush_time(String push_time) {
		this.push_time = push_time;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	@Override
	public String toString() {
		return "PushMessageLog [Id=" + Id + ", user_id=" + user_id + ", type="
				+ type + ", message=" + message + ", mq_topic=" + mq_topic
				+ ", push_time=" + push_time + ", openId=" + openId
				+ ", unionId=" + unionId + ", real_name=" + real_name
				+ ", state=" + state + "]";
	}
	
}
