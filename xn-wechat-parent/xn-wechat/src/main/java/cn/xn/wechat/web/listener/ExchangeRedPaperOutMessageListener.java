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
 *  推送模板消息 , 红包返现监听
 * @author rod zhong 
 *
 */
@Service("exchangeRedPaperOutMessageListener")
public class ExchangeRedPaperOutMessageListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ExchangeRedPaperOutMessageListener.class);
	
	private TextMessage textMsg;
	
	private String EXCHANGE_REDPAPER = "exchange_redpaper";
	
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
			logger.warn("MQ消息异常",e);
		}
		logger.info("onMessage@，接收到一个纯文本消息:{}",value);
		if (StringUtils.isBlank(value)) {
			logger.warn("接收到的消息为空");
			return;
		}
		JSONObject jsonObject = JSON.parseObject(value);
		if(jsonObject != null && StringUtils.isNotEmpty(jsonObject.getString("msgType"))){
			String msgType = jsonObject.getString("msgType");
			if(EXCHANGE_REDPAPER.equals(msgType.toLowerCase())){
				
				pushTemplateMessageService.handleBacknowred(jsonObject);
			}else{
				logger.warn("MQ消息红包返现推送的规则不匹配");
			}
		}
		
	}
}