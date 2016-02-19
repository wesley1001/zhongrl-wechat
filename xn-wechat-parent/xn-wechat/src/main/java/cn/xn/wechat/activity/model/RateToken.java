package cn.xn.wechat.activity.model;

import java.io.Serializable;

public class RateToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 747031966699092255L;

	private int id;
	
	private String createDate;
	
	private String updateTime;
	
	private String token;
	
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RateToken [id=" + id + ", createDate=" + createDate
				+ ", updateTime=" + updateTime + ", token=" + token + ", type="
				+ type + "]";
	}
}
