package cn.xn.wechat.activity.service;

import java.util.List;
import java.util.Map;

import cn.xn.wechat.activity.model.PrizeInfo;

public interface IPrizeInfoService {
	/**
	 * 查询奖品列表
	 * 
	 * @param userId
	 *            用户Id
	 * @param material
	 *            用户原料
	 * @param rank
	 *            用户排名
	 * @return
	 */
	public Map<String, Object> findPrizeInfoList(String userId, int material,
			int rank);

	/**
	 * 查询奖品详细信息
	 */
	public PrizeInfo getPrizeInfoById(String prizeId);
	public PrizeInfo getPrizeInfoDetail(String prizeId,String userId);
	public void init();

	public List<PrizeInfo> findPrizeInfoList();
}
