package cn.xn.wechat.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.dubbo.config.support.Parameter;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.web.model.BiScanCode;
import cn.xn.wechat.web.model.KeyWord;
import cn.xn.wechat.web.model.PushMessageLog;
import cn.xn.wechat.web.model.ReplyMessage;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.model.WechatMenu;

public interface WechatConfigMapper extends BaseMapper{

	public WechatConfig getWeChat(@Param("wechatType")String wechatType);
	
	public void updateWechatMenu(WechatMenu wechatMenu);
	
	public int saveWechatConfig(WechatConfig wechatConfig);
	
	public List<WechatConfig> getWechatConfig();
	
	public int getWechatConfigCount();
	
	public int saveReplyMessage(ReplyMessage replyMessage);
	
	public List<ReplyMessage> replyMessages(Map<String,Integer> map);
	
	public int deleteReplyMessage(@Param("id")int id);
	
	public ReplyMessage getReplyMessage(@Param("id")int id);
	
	public int updateReplyMessage(ReplyMessage replyMessage);
	
	public int replyMessagesCount();
	
	public List<BiScanCode> statisticalScanCodes(Map<String,Integer> map);
	
	public int statisticalScanCodeCount();
	
	public List<KeyWord> getKeyWords(Map<String,Object> map);
	
	public void updateKeyWord(KeyWord keyWord);
	
	public void saveKeyWord(KeyWord keyWord);
	
	public int getKeyWordCount(Map<String,Object> map);
	
	public KeyWord getKeyWord(Integer id);
	
	public void deleteKeyWord(@Param("id")Integer id);
	
	public List<KeyWord> getKeyNames(Map<String,Object> map);
	
	public void saveWechatUserDetails(Wechat wechat);
	
	public Wechat getWechatUserDetails(Wechat wechat);
	
	public void savePushMessageLog(PushMessageLog pushMessageLog);
	
	public List<Wechat> getWechatUserList();
	
	public void updateWechatUserDetails(Wechat wechat);
}
