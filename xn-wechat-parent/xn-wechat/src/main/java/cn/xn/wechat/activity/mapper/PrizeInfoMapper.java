package cn.xn.wechat.activity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.PrizeInfo;

public interface PrizeInfoMapper extends BaseMapper {

	/***
	 * 查询奖品列表
	 */
	public List<PrizeInfo> findPrizeInfoList(Map<String, Object> map);

	public PrizeInfo getPrizeInfoById(@Param("prizeId") String prizeId);

	/**
	 * 行级锁查询产品信息
	 */
	public PrizeInfo getPrizeInfoLockById(@Param("prizeId") String prizeId);

	public int updatePrizeInfo(PrizeInfo prizeInfo);

}
