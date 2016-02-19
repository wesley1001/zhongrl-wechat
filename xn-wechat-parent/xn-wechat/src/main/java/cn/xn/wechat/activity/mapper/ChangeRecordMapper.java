package cn.xn.wechat.activity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.ChangeRecordVo;

public interface ChangeRecordMapper extends BaseMapper{
	/**
	 * 新增兑换记录
	 */
	public int addChangeRecord(ChangeRecordVo vo);

	/**
	 * 修改兑换记录
	 */
	public int modifyChangeRecordByMap(Map<String,Object> map);

	/**
	 * 查询用户的兑换记录
	 */
	public List<ChangeRecordVo> findChangePrizeListByMap(Map<String,Object> map);
	/**
	 * 查询用户已经兑换的原料
	 */
	public Integer sumChangeMaterialByMap(Map<String,Object> map);
	
	/**
	 * 查询用户的兑奖记录
	 */
	public int getUserChangeRecordByUserId(@Param("userId") String userId); 
}
