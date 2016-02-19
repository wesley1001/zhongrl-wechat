package cn.xn.wechat.web.job;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xiaoniu.sms.req.SendMsgReq;
import com.xiaoniu.sms.service.ISmsService;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.mapper.TicketInfoMapper;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.web.util.DateUtil;

@Component
public class TicketWarnJob {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private TicketInfoMapper ticketInfoMapper;
	@Resource
	private ISmsService iSmsService;
	@Resource
	private IWechatActivityService wechatActivityService;
	
	private boolean isSend;

	@Scheduled(cron = "0 30 * * * ?")
	public void execute() {
//		WechatActivity activity = wechatActivityService
//				.getAvailableActivityByType(PrizeInfoConstant.WXSS_ACTIVITY_TYPE); 
//		
//		logger.info("....................检查电影票定时器start....................");
//		if(activity == null){
//			return ;
//		}
//		if (isSend) {
//			return;
//		}
//		double alertCount = ticketInfoMapper.countAlert();//700
//		double count = ticketInfoMapper.count();//1000
//		double alertValue = alertCount / count;
//		if (alertValue >= 0.7) {
//			alertValue = alertCount / count;
//			SendMsgReq req = new SendMsgReq("QGZ","TICKETLEFT","15118811426,13691865186","");
//			try {
//				String currentDate = DateUtil.DateToString(new Date(),
//						"yyyy-MM-dd HH:mm:ss");
//				int mycount=(int) (count-alertCount);
//				req.setValue("扫码邀请活动,"+currentDate+","+mycount);
//				iSmsService.sendMsg(req);
//				isSend=true;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			logger.info("....................发送短信通知，到目前为止还剩("
//					+ (count - alertCount) + "张)未被领取....................");
//		}
//		logger.info("....................检查电影票定时器end....................");
	}
	 
}
