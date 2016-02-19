package cn.xn.wechat.activity.resp;

import cn.xn.wechat.activity.model.BaseDecodeResp;

public class UserInfoResp extends BaseDecodeResp{

	/**
	 * 二维码地址
	 */
	private String codeUrl;
	/**
	 * 用户Id
	 */
	private String userId;
	/**
	 * 目前排名
	 */
	private int rank;
	/**
	 * 我的原料
	 */
	private int material;
	/**
	 * 邀请好友人数
	 */
	private int invitNum;

	/**
	 * 用户昵称
	 */
	private String nickname;
	
	
	 

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getMaterial() {
		return material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public int getInvitNum() {
		return invitNum;
	}

	public void setInvitNum(int invitNum) {
		this.invitNum = invitNum;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
