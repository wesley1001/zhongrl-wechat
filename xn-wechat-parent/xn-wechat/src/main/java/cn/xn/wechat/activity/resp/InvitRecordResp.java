package cn.xn.wechat.activity.resp;

import java.util.Date;

import com.xiaoniuapp.activity.mact.common.utils.DateUtils;

import cn.xn.wechat.activity.model.BaseDecodeResp;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;

public class InvitRecordResp extends BaseDecodeResp{

	private Date createDate;
	private String headimgurl;
	private int material;
	private String nickname;

	public Date getCreateDate() {
		return createDate;
	}

	public int getHours() {
		int hours = DateUtils.countHours(createDate, new Date());
		return (int) hours;
	}

	public String getCreateDateStr() {
		return DateUtil.DateToString(createDate, DateStyle.YYYY_MM_DD_HH_MM_SS);
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
