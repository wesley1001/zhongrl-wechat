package cn.xn.wechat.activity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xn.wechat.activity.exception.ChangePrizeException;
import cn.xn.wechat.activity.json.JsonMessage;
import cn.xn.wechat.activity.mapper.FansUserInfoMapper;
import cn.xn.wechat.activity.mapper.PrizeInfoMapper;
import cn.xn.wechat.activity.mapper.ScanRecordMapper;
import cn.xn.wechat.activity.mapper.TicketInfoMapper;
import cn.xn.wechat.activity.model.AddressVo;
import cn.xn.wechat.activity.model.ChangeRecordVo;
import cn.xn.wechat.activity.model.PrizeInfo;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.service.IOrderService;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.activity.service.handler.PrizeChangeHandler;

import com.xiaoniu.sms.service.ISmsService;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PrizeInfoMapper prizeInfoMapper;
	//@Resource
	//private ChangeRecordMapper changeRecordMapper;
	@Resource
	private FansUserInfoMapper fansUserInfoMapper;
	@Resource
	private ScanRecordMapper scanRecordMapper;
	//@Resource
	//private ISmsService iSmsService;
	@Resource
	private TicketInfoMapper ticketInfoMapper;
	@Resource
	private IWechatActivityService wechatActivityService;

 
	private Map<String, PrizeChangeHandler> prizeChangeHandlerMap = new HashMap<String, PrizeChangeHandler>();

	@Autowired
	public void setPrizeChangeHandlerMap(List<PrizeChangeHandler> handlerList) {
		for (PrizeChangeHandler prizeHandler : handlerList) {
			prizeChangeHandlerMap.put(String.valueOf(prizeHandler.getProcutType()),
					prizeHandler);
		}
	}

	/*public ChangeRecordVo getChangeRecordById(String userId, String recordId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("recordId", recordId);
		List<ChangeRecordVo> list = changeRecordMapper
				.findChangePrizeListByMap(map);
		if (list != null) {
			return list.get(0);
		}
		return null;
	}*/


	@Override
	public synchronized JsonMessage confirmChangePrize(String prizeId, Integer userId,
			AddressVo vo, String activityType){
		logger.info("兑换产品:prizeId={},userId={}", prizeId, userId);
		WechatActivity activity = wechatActivityService.getAvailableActivityByType(activityType);
		if (activity == null) {
			return new JsonMessage(-1, "活动已经过期，兑换失败!");
		}
		long currenttime = new Date().getTime();
		long startTime = activity.getStartDate().getTime();
		long endTime = activity.getEndDate().getTime();
		if (currenttime > endTime) {
			return new JsonMessage(-1, "兑换失败,生蛋活动已经结束");
		}
		if (startTime > currenttime) {
			return new JsonMessage(-1, "兑换失败,生蛋活动还未开始");
		}
		if (!activity.getProductIds().contains(prizeId)) {
			return new JsonMessage(-1, "兑换失败,该产品不参与生蛋活动");
		}
		// 查询产品信息
		PrizeInfo prizeInfo = prizeInfoMapper.getPrizeInfoById(prizeId);
		try {
			String recordId =prizeChangeHandlerMap.get(
					prizeInfo.getTypeId()).changePrizeHandle(userId, prizeId, vo, activity);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "兑换成功");
			map.put("recordId", recordId);
			return JsonMessage.getSuccessJson(map);
		} catch (ChangePrizeException e) {
			return new JsonMessage(e.getCode(), e.getMsg());
		}
	}

}
