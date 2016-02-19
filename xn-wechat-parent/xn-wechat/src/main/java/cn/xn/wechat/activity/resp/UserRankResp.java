package cn.xn.wechat.activity.resp;

import java.util.Date;

import cn.xn.wechat.activity.model.BaseDecodeResp;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;

public class UserRankResp extends BaseDecodeResp{

	private String headimgurl;
	private String nickname;
	private String userId;
	private int friends;
	private Date createDate;

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getFriends() {
		return friends;
	}

	public void setFriends(int friends) {
		this.friends = friends;
	}

	public String getCreateDateStr() {
		return DateUtil.DateToString(createDate, DateStyle.YYYY_MM_DD_HH_MM_SS);
	}
}
