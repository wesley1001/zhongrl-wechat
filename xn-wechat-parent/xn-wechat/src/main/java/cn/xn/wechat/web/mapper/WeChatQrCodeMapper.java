package cn.xn.wechat.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.web.model.ReplyMessage;
import cn.xn.wechat.web.model.WeChatQrCode;

public interface WeChatQrCodeMapper extends BaseMapper{

	public int saveQRImageAndTicket(Map<String,Object> map);
	
	public int saveQrCodeEvent(Map<String,Object> map);
	
	public WeChatQrCode getWechatPayAttentionInformation(@Param("openId")String openId);
	
	public List<ReplyMessage> getWechatUser(@Param("openId")String openId);
	
	public ReplyMessage getReplyMessage(@Param("param")String param);
	
}
