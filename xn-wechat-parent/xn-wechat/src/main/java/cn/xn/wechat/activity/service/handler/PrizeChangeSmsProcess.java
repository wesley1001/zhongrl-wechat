package cn.xn.wechat.activity.service.handler;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.xn.wechat.activity.constant.PrizeInfoConstant;
import cn.xn.wechat.activity.exception.ChangePrizeException;
import cn.xn.wechat.activity.mapper.TicketInfoMapper;
import cn.xn.wechat.activity.model.TicketInfo;

import com.xiaoniu.sms.req.SendMsgReq;
import com.xiaoniu.sms.service.ISmsService;
import com.xiaoniu.sms.util.SmsResult;

@Component
public class PrizeChangeSmsProcess {
	private Logger logger = LoggerFactory
			.getLogger(PrizeChangeSmsProcess.class);
	@Resource
	protected ISmsService iSmsService;
	@Resource
	protected TicketInfoMapper ticketInfoMapper;

	public void sendMsg(String partnerId, String moduleId, String mobile,
			Integer userId) throws ChangePrizeException {
		SendMsgReq req = new SendMsgReq(partnerId, moduleId, mobile, null);
		try {
			SmsResult<Object> result = iSmsService.sendMsg(req);
			if (result == null || 0 != result.getReturnCode()) {
				this.logger.error("兑换iwatch发送短信失败,mobile=" + mobile);
				throw new ChangePrizeException(
						PrizeInfoConstant.PrizeRespConstant.ERROR,
						"iwatch兑换失败,短信发送失败");
			}
			logger.info("iwatch兑换成功,userId={}", userId);

		} catch (Exception e) {
			this.logger.error(
					"发送短信失败出现异常-->,iwatch兑换失败，当前事物回滚操作" + e.getMessage(), e);
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR,
					"iwatch兑换失败,短信发送失败");
		}

	}

	public void sendMsgAndUpdateTicket(String partnerId, String moduleId,
			String mobile, Integer userId, String username, String investId)
			throws ChangePrizeException {
		TicketInfo ticketInfo = ticketInfoMapper.getNotDistributeToTicketInfo();
		if (ticketInfo == null) {
			this.logger.info("已经没有可兑换的电影票了");
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "电影票已经兑换完,兑换失败!");
		}
		SendMsgReq req = new SendMsgReq(partnerId, moduleId, mobile, null);
		String value = getSMSValue(ticketInfo.getTicket());
		req.setValue(value);
		ticketInfo
				.setStatus(PrizeInfoConstant.PrizeDeliveryConstant.STATUS_SEND);
		ticketInfo.setInvestId(investId);
		ticketInfo.setMobile(mobile);
		ticketInfo.setUpdateDate(new Date());
		ticketInfo.setRemark("短信发送成功");
		ticketInfo.setUsername(username);
		try {
			SmsResult<Object> result = iSmsService.sendMsg(req);
			if (result == null || 0 != result.getReturnCode()) {
				this.logger.error("兑换电影票发送短信失败,mobile=" + mobile);
				throw new ChangePrizeException(
						PrizeInfoConstant.PrizeRespConstant.ERROR,
						"兑换失败,短信发送失败");
			}
			ticketInfoMapper.updateBatchTicket(ticketInfo);
			this.logger.info("发送短信成功-->" + result.getReturnMsg());
		} catch (Exception e) {
			this.logger
					.error("发送短信失败出现异常-->,兑换失败，当前事物回滚操作" + e.getMessage(), e);
			throw new ChangePrizeException(
					PrizeInfoConstant.PrizeRespConstant.ERROR, "兑换失败,短信发送失败");
		}
	}

	private String getSMSValue(String ticket) {
		StringBuilder builder = new StringBuilder();
		builder.append(ticket);
		return builder.toString();
	}
}
