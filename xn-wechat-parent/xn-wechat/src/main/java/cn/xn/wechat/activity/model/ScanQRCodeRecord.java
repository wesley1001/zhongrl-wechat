package cn.xn.wechat.activity.model;

import java.util.Date;

public class ScanQRCodeRecord {
	private String id;
	private String user_id;
	private String openid;
	private int material;
	private Date create_date;
	private String activicty_code;
	private int eggs_user_id;
	private String user_type;

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public int getEggs_user_id() {
		return eggs_user_id;
	}

	public void setEggs_user_id(int eggs_user_id) {
		this.eggs_user_id = eggs_user_id;
	}

	public String getActivicty_code() {
		return activicty_code;
	}

	public void setActivicty_code(String activicty_code) {
		this.activicty_code = activicty_code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

}
