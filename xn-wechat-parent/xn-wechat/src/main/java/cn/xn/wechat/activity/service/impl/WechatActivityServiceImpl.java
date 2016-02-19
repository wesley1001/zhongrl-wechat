package cn.xn.wechat.activity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.xn.wechat.activity.mapper.WechatActivityMapper;
import cn.xn.wechat.activity.model.WechatActivity;
import cn.xn.wechat.activity.service.IWechatActivityService;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;

@Service("wechatActivityService")
public class WechatActivityServiceImpl implements IWechatActivityService {
	@Resource
	private WechatActivityMapper wechatActivityMapper;

	@Override
	public WechatActivity getAvailableActivityByType(String activityType) {
		String currentDate = DateUtil.DateToString(new Date(),
				DateStyle.YYYY_MM_DD_HH_MM_SS);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("currentDate", currentDate);
		paramMap.put("activityType", activityType);
		return wechatActivityMapper.getActivityByMap(paramMap);
	}

	@Override
	public WechatActivity getActivityByType(String activityType) {
		return wechatActivityMapper.getActivityByType(activityType);
	}

}
