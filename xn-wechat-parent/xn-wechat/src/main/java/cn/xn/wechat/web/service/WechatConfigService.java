package cn.xn.wechat.web.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.xn.wechat.web.mapper.WechatConfigMapper;
import cn.xn.wechat.web.model.BiScanCode;
import cn.xn.wechat.web.model.KeyWord;
import cn.xn.wechat.web.model.PushMessageLog;
import cn.xn.wechat.web.model.ReplyMessage;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.model.WechatMenu;

@Service("wechatConfigService")
public class WechatConfigService {

	@Resource
	private WechatConfigMapper wechatConfigMapper;
	
	public WechatConfig getWeChat(String wechatType){
		if(StringUtils.isNotEmpty(wechatType) ){
			return wechatConfigMapper.getWeChat(wechatType);
		}
		return null;
	}
	
	public void updateWechatMenu(WechatMenu wechatMenu){
		wechatConfigMapper.updateWechatMenu(wechatMenu);
	}
	
	public int saveWechatConfig(WechatConfig wechatConfig){
		return wechatConfigMapper.saveWechatConfig(wechatConfig);
	}
	
	public List<WechatConfig> getWechatConfig(){
		return wechatConfigMapper.getWechatConfig();
	}
	
	public int getWechatConfigCount(){
		return wechatConfigMapper.getWechatConfigCount();
	}
	
	public int saveReplyMessage(ReplyMessage replyMessage){
		return wechatConfigMapper.saveReplyMessage(replyMessage);
	}
	
	public List<ReplyMessage> replyMessages(Map<String,Integer> map){
		return wechatConfigMapper.replyMessages(map);
	}
	
	public int deleteReplyMessage(int id){
		return wechatConfigMapper.deleteReplyMessage(id);
	}
	
	public ReplyMessage editReplyMessage(int id){
		return wechatConfigMapper.getReplyMessage(id);
	}
	
	public int updateReplyMessage(ReplyMessage replyMessage){
		return wechatConfigMapper.updateReplyMessage(replyMessage);
	}
	
	public int replyMessagesCount(){
		return wechatConfigMapper.replyMessagesCount();
	}
	
	public List<BiScanCode> statisticalScanCodes(Map<String,Integer> map){
		
		return wechatConfigMapper.statisticalScanCodes(map);
	}
	
	public int statisticalScanCodeCount(){
		
		return wechatConfigMapper.statisticalScanCodeCount();
	}
	
	public List<KeyWord> getKeyWords(Map<String,Object> map){
		
		return wechatConfigMapper.getKeyWords(map);
	}
	
	public KeyWord getKeyWord(Integer id){
		return wechatConfigMapper.getKeyWord(id);
	}
	
	public int getKeyWordCount(Map<String,Object> map){
		
		return wechatConfigMapper.getKeyWordCount(map);
	}
	
	public void updateKeyWord(KeyWord keyWord){
		wechatConfigMapper.updateKeyWord(keyWord);
	}
	
	public void saveKeyWord(KeyWord keyWord){
		wechatConfigMapper.saveKeyWord(keyWord);
	}
	
	public void deleteKeyWord(Integer id){
		wechatConfigMapper.deleteKeyWord(id);
	}
	
	public List<KeyWord> getKeyNames(Map<String,Object> map){
		return wechatConfigMapper.getKeyNames(map);
	}
	
	public void saveWechatUserDetails(Wechat wechat){
		wechatConfigMapper.saveWechatUserDetails(wechat);
	}
	
	public Wechat getWechatUserDetails(Wechat wechat){
		return wechatConfigMapper.getWechatUserDetails(wechat);
	}
	
	public void savePushMessageLog(PushMessageLog pushMessageLog){
		wechatConfigMapper.savePushMessageLog(pushMessageLog);
	}
	
	public void updateWechatUserDetails(Wechat wechat){
		wechatConfigMapper.updateWechatUserDetails(wechat);
	}
}
