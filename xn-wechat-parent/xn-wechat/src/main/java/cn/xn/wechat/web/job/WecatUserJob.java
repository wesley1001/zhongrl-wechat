package cn.xn.wechat.web.job;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.util.SpringContextHolder;

/**
 * 微信获取用户定时器 
 * @author rod zhong 
 *
 */
@Component
public class WecatUserJob {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	//@PostConstruct
	public void init(){
		cn.xn.wechat.web.util.WechatUtil.poolThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
					ActivityService activityService = (ActivityService) SpringContextHolder.getBean("activityService");
					try {
						
						activityService.wechatUser();
						} catch (Exception e) {
//						logger.warn("重新跑一次" ,e );
//						activityService.wechatUser();
						}
					} catch (Exception e1) {
				}
			}
		});
	}
	
	@Scheduled(cron="0 0 2 * * ?")  
	public void execute(){
		logger.info("....................微信拉取用户信息定时器start....................");
		ActivityService activityService = (ActivityService) SpringContextHolder.getBean("activityService");
		activityService.wechatUser();
		logger.info("....................微信拉取用户信息定时器end....................");
		
	}
	
	public static void main(String[] args) {
		int a = 100000;
		int b = 0;
		int i = 1;
		do {
			if(b > a){
				break;
			}
			b+= 10000;
			
			System.out.println("第"+i+"次 ;" + b);
			i++;
		} while (a > b);
	}
}
