package cn.xn.wechat.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.controller.WeChatGetMaterialController;
import cn.xn.wechat.web.mapper.WeChatGetMaterialMapper;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.util.HttpUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("weChatGetMaterialService")
public class WeChatGetMaterialService {

	private static Logger logger = LoggerFactory.getLogger(WeChatGetMaterialController.class);
	
	@Resource
	private WeChatGetMaterialMapper weChatGetMaterialMapper;
	
	@Resource
	private WechatUtil wechatUtil;
	
	public String getMaterials(String wechatName){
		WechatConfig wechatConfig = wechatUtil.getWeChat(wechatName);
		if(wechatConfig == null){
			return null;
		}
		JSONObject jsonObject = null ; 
		try {
			jsonObject = wechatUtil.getToken();
		} catch (Exception e) {
			logger.warn("获取token 失败" , e );
		}
		if(jsonObject != null ){
			String accessToken = jsonObject.getString("access_token");
			if(StringUtils.isNotEmpty(accessToken)){
				String requestUrl = Constant.MATERIAL_URL + "?access_token=" + accessToken;
				JSONObject post = new JSONObject(); 
				post.put("type", "image");
				post.put("offset", 0);
				post.put("count", 20);
				logger.info("获取素材列表的请求连接 :" + requestUrl);
				logger.info("获取素材列表的请求参数 :" + post.toJSONString());
				try {
					String result = HttpUtil.getInstance().phpPost(requestUrl, post);
					JSONObject materials = JSONObject.parseObject(result);
					if(materials != null){
						Map<String,Object> map = null;
						JSONArray items = (JSONArray)materials.get("item");
						int length = items.size() > 0 ? items.size() : 0;
						JSONObject item = null;
						for (int i = 0; i < length; i++) {
							item = items.getJSONObject(i);
							map = new HashMap<String,Object>();
							map.put("mediaId", item.getString("media_id"));
							map.put("update_time", item.getString("update_time"));
							weChatGetMaterialMapper.saveMaterial(map);
						}
					}
				} catch (Exception e) {
					logger.warn("获取素材列表失败" , e );
				} 
			}
		}
		return "";
	}
}
