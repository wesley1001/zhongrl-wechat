package cn.xn.wechat.web.model;

import java.io.Serializable;

public class WeChatQrCode implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -432139838898099089L;

	/** 
	* @Fields id : id
	*/ 
	private int id;
	/** 
	* @Fields ToUserName : 开发者微信号
	*/ 
	private String toUserName;
	/** 
	* @Fields FromUserName : 发送方帐号（一个OpenID）
	*/ 
	private String fromUserName;
	/** 
	* @Fields CreateTime : 消息创建时间 （整型）
	*/ 
	private String createTime;
	/** 
	* @Fields MsgType : 消息类型，event
	*/ 
	private String msgType;
	/** 
	* @Fields Event : 事件类型，subscribe未关注,SCAN关注
	*/ 
	private String event;
	/** 
	* @Fields EventKey : 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	*/ 
	private String eventKey;
	/** 
	* @Fields Ticket : 二维码的ticket，可用来换取二维码图片
	*/ 
	private String ticket;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	@Override
	public String toString() {
		return "WeChatQrCode [id=" + id + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", msgType=" + msgType + ", event=" + event
				+ ", eventKey=" + eventKey + ", ticket=" + ticket + "]";
	}
	
}
