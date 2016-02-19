package cn.xn.wechat.activity.service;

import cn.xn.wechat.activity.model.ScanQRCodeRecord;

public interface IScanRecordService {

	/**
	 * 添加扫码记录,邀请积分加1
	 * 
	 * @param userId
	 *            邀请者Id
	 * @param openid
	 *            被邀请者openid
	 */
	public void addScanRecord(int userId, String openid);
	public void addScanRecord(String userId, String openid);
	/**
	 * 查询扫码记录
	 * 
	 * @param openid
	 * @param userId
	 * @return
	 */
	public ScanQRCodeRecord getScanQRCodeRecordById(String openid, String userId);
}
