package cn.xn.wechat.activity.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.WechatActivity;

public interface WechatActivityMapper extends BaseMapper{
	/**
	 * 获取扫码活动
	 */
	public WechatActivity getActivityByMap(Map<String,Object> paramMap);
	
	/**
	 * 根据活动编码查询
	 * @param activityType
	 * @return
	 */
	public WechatActivity getActivityByType(@Param("activityType")String activityType);
}
