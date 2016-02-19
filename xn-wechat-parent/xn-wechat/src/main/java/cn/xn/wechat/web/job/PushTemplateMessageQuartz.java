package cn.xn.wechat.web.job;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.xn.cache.service.ICommonRedisService;
import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.service.PushTemplateMessageService;
import cn.xn.wechat.web.util.SpringContextHolder;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

@Component
public class PushTemplateMessageQuartz {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ICommonRedisService commonRedisService; 
	
	@Resource
	private PushTemplateMessageService  pushTemplateMessageService;
	
	
	@Scheduled(cron = "0 30 9 * * ?")
	public void execute() {
		logger.info("....................回款发送微信消息定时器start....................");
		try {
			String parments =  commonRedisService.get(Constant.PAYMENT_BID, Constant.PAYMENT_KEY);
			if(StringUtils.isEmpty(parments)){
				logger.info("redis  data parments  is null ");
				return ;
			}
			logger.info("redis is data parments " + parments);
			String[] array = parments.split("@");
			int length = array.length > 0 ? array.length : 0;
			logger.info("....................总共"+length+"条MQ消息....................");
			boolean open = true;
			boolean there = false;
			String message = "";
			for (int i = 0; i < length; i++) {
				logger.info("====================处理的第" + i + "条数据==================");
				if(length > 800){
					if(open && i > length / 3){
						logger.info("====================open 1 ==================");
						Thread.sleep(1000 * 60 * 30);
						open = false;
						there =	i > (length / 2 - i)  ? true:there;
					}else if(there && i  > length / 2){
						logger.info("====================there 2 ==================");
						there = false;
						Thread.sleep(1000 * 60 * 30);
					}
				}
				message = array[i];
				if(StringUtils.isNotEmpty(message)){
					logger.info("===================="+message+"==================");
					final JSONObject jsonObject = JSONObject.parseObject(message);
					logger.info("===================="+jsonObject.toJSONString()+"==================");
					cn.xn.wechat.web.util.WechatUtil.poolThreadExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
								try {
									pushTemplateMessageService.handlePayment(jsonObject);
									} catch (Exception e) {
										logger.warn("处理不成功 " ,e );
									}
								} catch (Exception e1) {
							}
						};
					});
				}
			}
			logger.info("....................回款发送微信消息定时器end....................");
		} catch (Exception e) {
			logger.warn("....................定时器处理 exception...................." , e);
		}
	}
}
