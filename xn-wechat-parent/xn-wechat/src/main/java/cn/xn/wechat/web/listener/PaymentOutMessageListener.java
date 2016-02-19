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

import cn.xn.cache.service.ICommonRedisService;
import cn.xn.wechat.web.constant.Constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *  推送模板消息 回款提现的 监听
 * @author rod zhong 
 *
 */
@Service("paymentOutMessageListener")
public class PaymentOutMessageListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentOutMessageListener.class);
	
	private TextMessage textMsg;
	
	private String PAYMENT = "payment";
	
	@Resource
	private ICommonRedisService commonRedisService; 
	
	
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
			logger.error("MQ消息异常",e);
		}
		logger.info("onMessage@，接收到一个纯文本消息:{}",value);
		if (StringUtils.isBlank(value)) {
			logger.warn("接收到的消息为空");
			return;
		}
		JSONObject jsonObject = JSON.parseObject(value);
		if(jsonObject != null && StringUtils.isNotEmpty(jsonObject.getString("msgType"))){
			String msgType = jsonObject.getString("msgType");
			if(PAYMENT.equals(msgType.toLowerCase())){
				try {
					StringBuffer sb = new StringBuffer();
					String parments = commonRedisService.get(Constant.PAYMENT_BID, Constant.PAYMENT_KEY);
					if(StringUtils.isEmpty(parments)){
						sb.append(value).append("@");
					}else{
						logger.info("parments is length  = " + parments.split("@").length);
						sb.append(parments).append("@").append(value);
					}
					commonRedisService.set(Constant.PAYMENT_BID, Constant.PAYMENT_KEY, sb.toString(), Constant.PAYMENT_EXPIRE);
				} catch (Exception e) {
					logger.warn("redis is exception MQ Message Callback",e);
					return ;
				}
			}else{
				logger.warn("回款的MQ消息格式不匹配推送规则");
			}
		}
	}
}