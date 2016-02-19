package cn.xn.wechat.web.model;

import java.io.Serializable;

/**
 *  微信 字段
 * @author rod zhong 
 *
 */
public class Wechat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7667700959207572481L;

	private int id;
	
	private String subscribe;
	
	private String openid;
	
	private String nickname;
	
	private String sex;
	
	private String language;
	
	private String province;
	
	private String city;
	
	private String country;
	
	private String headimgurl;
	
	private String subscribe_time;
	
	private String unionid;
	
	private String remark;
	
	private String groupid;
	
	private int rowNumber;
	
	private String memberName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	@Override
	public String toString() {
		return "Wechat [id=" + id + ", subscribe=" + subscribe + ", openid="
				+ openid + ", nickname=" + nickname + ", sex=" + sex
				+ ", language=" + language + ", province=" + province
				+ ", city=" + city + ", country=" + country + ", headimgurl="
				+ headimgurl + ", subscribe_time=" + subscribe_time
				+ ", unionid=" + unionid + ", remark=" + remark + ", groupid="
				+ groupid + ", rowNumber=" + rowNumber + ", memberName="
				+ memberName + "]";
	}
}
