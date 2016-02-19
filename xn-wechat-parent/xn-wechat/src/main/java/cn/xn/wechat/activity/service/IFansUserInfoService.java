package cn.xn.wechat.activity.service;

import java.util.List;

import cn.xn.wechat.activity.model.ChangeRecordVo;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.resp.InvitRecordResp;
import cn.xn.wechat.activity.resp.MaterialResp;
import cn.xn.wechat.activity.resp.UserInfoResp;
import cn.xn.wechat.activity.resp.UserRankResp;
import cn.xn.wechat.activity.util.DataPage;

public interface IFansUserInfoService {
 
	public FansUserInfo getFansUserInfo(String userId, String openid,
			String unionid);
	/**
	 * 获取用户当前排名
	 * @param userId
	 * @return
	 */
	public int getUserRankById(String userId);
	/**
	 * 获取用户首页信息
	 * @param code 微信提供代码
	 * @return
	 */
	public UserInfoResp myUserInfoHome(String code,String userId,String channel);
	/**
	 * 用户邀请记录分页查询
	 */
	public DataPage<InvitRecordResp> queryInviteRecordByPage(String userId,DataPage<InvitRecordResp> dataPage);
	
	/**
	 * 用户兑换记录
	 */
	public List<ChangeRecordVo> findChangePrizeList(String userId);
	/**
	 * 用户的原料和剩余原料
	 */
	public MaterialResp getMaterialResp(String userId);
	/**
	 * 查询前top名
	 */
	public List<UserRankResp> findRankByTopNum(int topNum);
	
	/**
	 * 查询用户二维码
	 * @param userId
	 * @return
	 */
	public String getUserQrCodeById(String userId);

	/**
	 * 查询用户邀请人数和用户当前排名
	 * @param userId
	 * @return
	 */
	public UserInfoResp getUserInfoResp(String userId);
	
	/**
	 * 检查用户是否第一名
	 * @param userId
	 * @return
	 */
	public boolean isCheckUserNoOne(String userId);
}
