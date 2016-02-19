package cn.xn.wechat.web.mapper;

import java.util.List;
import java.util.Map;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.web.model.ClickNumber;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.model.WechatConfig;

public interface ActivityMapper extends BaseMapper{

	public int updateClickNumber(ClickNumber clickNumber);
	
	public ClickNumber getClickNumber(ClickNumber clickNumber);
	
	public int updateWechatConfig(WechatConfig wechatConfig);
	
	public WechatConfig getWechatConfig(WechatConfig wechatConfig);
	
	public int saveQRImageAndTicket(Map<String,Object> map);
	
	public int saveQrCodeEvent(Map<String,Object> map);
	
	public void saveIMToken(Map<String,String> map);
	
	public String getIMToken();
	
	public List<Wechat> getWechatUser(Map<String,Object> map);
	
	public void saveWechat(List<Wechat> list);
}
