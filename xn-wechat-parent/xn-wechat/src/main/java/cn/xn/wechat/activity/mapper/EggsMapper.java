package cn.xn.wechat.activity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.EggsQRcode;
import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.LMOrder;
import cn.xn.wechat.activity.model.LmHttpLog;
import cn.xn.wechat.activity.model.RateToken;
import cn.xn.wechat.activity.model.ScanQRCodeRecord;
import cn.xn.wechat.activity.model.ShareLog;
import cn.xn.wechat.activity.resp.EggsResp;

public interface EggsMapper extends BaseMapper{

	/**
	 * 新增扫码记录
	 */
	public int addEggsRecord(ScanQRCodeRecord record);

	/**
	 * 查询扫码记录是否已经存在
	 */
	public ScanQRCodeRecord findEggsQRCodeRecordByMap(Map<String, Object> map);
	
	/**
	 * 查询用户信息
	 */
	public FansUserInfo getEggsUserInfoByMap(Map<String, Object> map);
	
	public FansUserInfo getEggsUserInfoById(@Param("id")Integer id);
	
	
	/**
	 * 修改用户信息
	 */
	public int updateEggsUserInfo(FansUserInfo info);

	/**
	 * 新增用户信息
	 */
	public int addEggsUserInfo(FansUserInfo info);
	
	public int saveRateToken(Map<String,Object> map);
	
	public int updateRateToken(Map<String,Object> map);
	
	public RateToken getRateToken(@Param("type")String type);
	
	public int saveEggsOrder(LMOrder lmOrder);
	
	public List<ScanQRCodeRecord> getBuddyList(@Param("userId")Integer userId);
	
	public List<LMOrder> getRecordOfConversion(Map<String,Object> map);
	
	public int addEggsQrCode(Map<String,Object> map);
	
	public List<EggsResp> queryEggsScanQRCodes(Map<String,Object> map);
	
	public int countqueryEggsScanQRCodes(@Param("userId")Integer userId);
	
	public int saveApiLog(LmHttpLog lmHttpLog);
	
	public int updateNumber(Map<String,Object> map);
	
	public int getNumber(Map<String,Object> map);
	
	public int insertShareLog(ShareLog shareLog);
	
	public int updateTotleNumber(Map<String,Object> map);
	
	public EggsQRcode getEggsQrCode(Map<String,Object> map);
	
	public int updateEggsQrCode(Map<String,Object> map);
	
}
