package cn.xn.wechat.activity.mapper;

import java.util.List;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.TicketInfo;

public interface TicketInfoMapper extends BaseMapper {

	/**
	 * 批量新增电影票(用户数据初始化)
	 */
	public void addBatchTicket(List<TicketInfo> list);

	/**
	 * 修改电影票的派发状态
	 * 
	 * @param ticketInfo
	 */
	public void updateBatchTicket(TicketInfo ticketInfo);

	/**
	 * 查询获取未派发的电影票
	 */
	public TicketInfo getNotDistributeToTicketInfo();
	
	/**
	 * 获取总记录数
	 * @return
	 */
	public int count();
	/**
	 * 已经发送电影票总记录
	 * @return
	 */
	public int countAlert();
}
