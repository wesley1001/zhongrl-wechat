package cn.xn.wechat.web.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
 *  环信获取token 的定时器 
 * @author rod zhong
 *
 */
@Component
public class IMTokenQuartz {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(cron="0 0 0 0/5 * ?")   
	public void execute(){
//		logger.info("....................环信配置定时器start....................");
//		ActivityService activityService = (ActivityService) SpringContextHolder.getBean("activityService");
//		Map<String,String> map = new HashMap<String,String>();
//		String token = Utils.getEasemobAdminToken();
//		if(StringUtils.isNotEmpty(token)){
//			map.put("token", Utils.getEasemobAdminToken());
//			map.put("createTime", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
//			activityService.saveIMToken(map);
//			logger.info("token = " + token);
//		}else{
//			logger.info("....................获取token失败....................");
//		}
//		logger.info("....................环信配置定时器end....................");
		
	}
}
