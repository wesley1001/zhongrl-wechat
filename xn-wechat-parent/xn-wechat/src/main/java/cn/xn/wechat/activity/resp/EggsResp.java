package cn.xn.wechat.activity.resp;

import cn.xn.wechat.activity.model.BaseDecodeResp;

public class EggsResp extends BaseDecodeResp{

	private String create_date;
	private String headimgurl;
	private int material;
	private String nickname;
	private String user_type;
	private int referee_id;

	public int getReferee_id() {
		return referee_id;
	}

	public void setReferee_id(int referee_id) {
		this.referee_id = referee_id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getCreate_date(){
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


}
