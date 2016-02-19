package cn.xn.wechat.activity.service;

import cn.xn.wechat.activity.model.FansUserInfo;
import cn.xn.wechat.activity.model.ScanQRCodeRecord;
import cn.xn.wechat.activity.resp.EggsResp;
import cn.xn.wechat.activity.util.DataPage;

import com.alibaba.fastjson.JSONObject;


public interface EggsService {

	/**
	 * 添加扫码记录,邀请积分加1
	 * 
	 * @param uid  邀请者Id 
	 * @param openid  被邀请者openid
	 */
	public void addEggs(int uid, String openid,String activicty_code);
	
	/**
	 *  
	 * @param openid
	 * @param userId
	 * @return
	 */
	public ScanQRCodeRecord getEggsQRCodeRecordById(int userId,String openid, String activicty_code);
	
	/**
	 * 给用户发送流量
	 * @param phone
	 * @return
	 */
	public String sendRateToUser(String phone,int userId,String prizeId,String orderNumber);
	
	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	public FansUserInfo getUserInfoById(int userId);
	
	public int addMenuEggs(JSONObject jsonObject);
	
	public DataPage<EggsResp> queryEggsScanQRCodes(Integer userId,
			DataPage<EggsResp> dataPage);
	
}
