package cn.xn.wechat.web.mapper;

import java.util.Map;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;

public interface WeChatGetMaterialMapper extends BaseMapper{

	public int saveMaterial(Map<String,Object> map);
	
}
