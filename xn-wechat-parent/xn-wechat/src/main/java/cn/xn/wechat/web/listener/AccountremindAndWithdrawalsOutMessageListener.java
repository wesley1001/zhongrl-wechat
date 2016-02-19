package cn.xn.wechat.web.listener;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.xn.wechat.web.service.PushTemplateMessageService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *  推送模板消息  提现审核， 提现成功 监听
 * @author rod zhong 
 *
 */
@Service("accountremindAndWithdrawalsOutMessageListener")
public class AccountremindAndWithdrawalsOutMessageListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountremindAndWithdrawalsOutMessageListener.class);
	
	private TextMessage textMsg;
	
	private String WITHDRAWALS = "withdrawals" ; //提现审核
	
	private String USEROUT = "userout";//提现成功
	
	@Resource
	private PushTemplateMessageService  pushTemplateMessageService;
	
	@Override
	public void onMessage(Message message) {
		textMsg  = (TextMessage) message;
		if(textMsg  == null ){
			logger.warn("MQ message is null !" );
			return ;
		}
		String value = "";
		try {
			value = textMsg.getText();
		} catch (JMSException e) {
			logger.warn(" 提现审核， 提现成功 ， MQ消息异常,回滚...",e);
		}
		logger.info("onMessage@，接收到一个纯文本消息:{}",value);
		if (StringUtils.isBlank(value)) {
			logger.warn("接收到的消息为空");
			return;
		}
		JSONObject jsonObject = JSON.parseObject(value);
		String msgType = jsonObject.getString("msgType");
		if(StringUtils.isEmpty(msgType)){
			logger.warn("msgType is null 未能识别的消息格式");
			return;
		}
		JSONObject jsonData = jsonObject.getJSONObject("data");
		if(jsonData == null ){
			logger.warn("data is null 未能识别的消息格式");
			return;
		}
		if(StringUtils.isNotEmpty(jsonData.getString("partnerId")) && "10002".equals(jsonData.getString("partnerId"))){
			if(WITHDRAWALS.equals(msgType.toLowerCase())){//提现审核
				
				pushTemplateMessageService.handleWithdrawals(jsonData,msgType);
			}else if(USEROUT.equals(msgType.toLowerCase())){//提现成功
				
				pushTemplateMessageService.handleAccountremind(jsonData,msgType);
			}else{
				
				logger.warn("识别不了这个消息类型 ： " + msgType);
			}
		}
	}
}