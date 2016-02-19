package cn.xn.wechat.web.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.activity.util.SendRateUtil;
import cn.xn.wechat.customer.service.Utils;
import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.SpringContextHolder;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：WechatConfigQuartz.java
 * 
 * 类描述：微信配置定时器  每一个班小时执行一次
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年7月30日 下午3:33:02
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@Component
public class EggsQuartz{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private IWechatActivityService wechatActivityService;
	
	@Resource
	private SendRateUtil sendRateUtil;
	
	@PostConstruct
	public void init(){
//		cn.xn.wechat.web.util.WechatUtil.poolThreadExecutor.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(10000);
//					//SendRateUtil sendRateUtil = (SendRateUtil) SpringContextHolder.getBean("sendRateUtil");
//					try {
//						sendRateUtil.getHttpToken();
//						} catch (Exception e) {
//						logger.warn("重新跑一次" ,e );
//						sendRateUtil.getHttpToken();
//						}
//					} catch (Exception e1) {
//				}
//			}
//		});
		
		
	}
	
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void execute(){
		logger.info("....................流米配置定时器start....................");
		try {
			sendRateUtil.getHttpToken();
		} catch (Exception e) {
			logger.warn("重新跑一次" ,e );
			sendRateUtil.getHttpToken();
		}
		logger.info("....................流米配置定时器end....................");
		
	}

}
