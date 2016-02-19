package cn.xn.wechat.activity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.resp.InvitRecordResp;
import cn.xn.wechat.activity.resp.MyRankResp;
import cn.xn.wechat.activity.resp.UserRankResp;

public interface FansUserInfoMapper extends BaseMapper {

	/**
	 * 查询用户信息
	 */
	public FansUserInfo getFansUserInfoByMap(Map<String, Object> map);
	public FansUserInfo getFansUserInfoByUserId(@Param("userId") String userId);
	
	public FansUserInfo getFansUserInfoById(@Param("id") int id);
	/**
	 * 修改用户信息
	 */
	public int updateFansUserInfo(FansUserInfo info);

	/**
	 * 新增用户信息
	 */
	public int addFansUserInfo(FansUserInfo info);

	/**
	 * 查询用户的邀请记录
	 */
	public List<InvitRecordResp> queryInviteRecordByMap(Map<String, Object> map);

	/**
	 * 查询用户的邀请总数
	 */
	public int countInviteRecordByUserId(@Param("userId") String userId);

	 
	public List<UserRankResp> findRankTopNum(@Param("topNum") int topNum);

	public void setRowNo();
	public MyRankResp getMyRankById(@Param("userId") String userId);
	
	public int countUser();
	 
}
