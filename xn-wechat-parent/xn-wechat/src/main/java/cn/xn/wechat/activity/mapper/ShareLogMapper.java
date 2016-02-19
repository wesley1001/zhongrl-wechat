package cn.xn.wechat.activity.mapper;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.ShareLog;

public interface ShareLogMapper extends BaseMapper {
	public void add(ShareLog log);
}
