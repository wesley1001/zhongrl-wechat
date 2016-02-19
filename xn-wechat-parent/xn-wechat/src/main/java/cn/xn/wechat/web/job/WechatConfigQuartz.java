package cn.xn.wechat.web.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
public class WechatConfigQuartz{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	public void init(){
		cn.xn.wechat.web.util.WechatUtil.poolThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
					ActivityService activityService = (ActivityService) SpringContextHolder.getBean("activityService");
					try {
						activityService.getWechatTicket();
						} catch (Exception e) {
						logger.error("重新跑一次" ,e );
						activityService.getWechatTicket();
						}
					} catch (Exception e1) {
				}
			};
		});
	}
	
	@Scheduled(cron = "0 0/55 * * * ?")
	public void execute(){
		logger.info("....................微信配置定时器start....................");
		ActivityService activityService = (ActivityService) SpringContextHolder.getBean("activityService");
		try {
			activityService.getWechatTicket();
		} catch (Exception e) {
			logger.error("重新跑一次" ,e );
			activityService.getWechatTicket();
		}
		logger.info("....................微信配置定时器end....................");
		
	}

}
