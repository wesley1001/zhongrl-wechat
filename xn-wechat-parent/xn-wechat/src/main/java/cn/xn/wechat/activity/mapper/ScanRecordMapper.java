package cn.xn.wechat.activity.mapper;

import java.util.Map;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.ScanQRCodeRecord;

/**
 * 扫码记录操作
 */
public interface ScanRecordMapper extends BaseMapper {

	/**
	 * 新增扫码记录
	 */
	public int addScanQRCodeRecord(ScanQRCodeRecord record);

	/**
	 * 查询扫码记录是否已经存在
	 */
	public ScanQRCodeRecord findScanQRCodeRecordByMap(Map<String, Object> map);

}
