package cn.xn.wechat.web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.mapper.WeChatQrCodeMapper;
import cn.xn.wechat.web.model.ReplyMessage;
import cn.xn.wechat.web.model.WeChatQrCode;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.HttpUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSONObject;

@Service("weChatQrCodeService")
public class WeChatQrCodeService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private WeChatQrCodeMapper weChatQrCodeMapper;
	
	@Resource
	private WechatUtil wechatUtil;
	
	/** 
	* @Title: createQrCode 
	* @Description: 调用微信二维码接口
	* @param @param channel
	* @param @return   
	* @return String   
	* @throws 
	*/
	public String createQrCode(String channel,String wechatName){
		WechatConfig wechatConfig = wechatUtil.getWeChat(wechatName);
		if(wechatConfig == null){
			return null;
		}
		if(StringUtils.isNotEmpty(channel)){
			JSONObject token = null;
			try {
				token = wechatUtil.getToken();
			} catch (Exception e) {
				logger.warn("get token error ！" , e);
				return WechatUtil.resultMessage(-1,"get token error ！");
			}
			if(token != null && StringUtils.isNotEmpty(token.getString("access_token"))){
				JSONObject postRequest = new JSONObject();
				JSONObject scene = new JSONObject();
				JSONObject scene_str = new JSONObject();
				scene_str.put("scene_str", channel);
				scene.put("scene", scene_str);
				postRequest.put("action_name", Constant.ACTION_NAME);
				postRequest.put("action_info", scene);
				try {
					logger.info("request param : " + postRequest.toString()+"\n");
					String result = HttpUtil.getInstance().phpPost(Constant.QR_CODE + token.getString("access_token"), postRequest);
					logger.info(" result : " + result);
					JSONObject resultObject = (JSONObject)JSONObject.parseObject(result);
					String ticket = resultObject.getString("ticket");
					if(StringUtils.isNotEmpty(ticket)){
						String getQrticketUrl = Constant.GET_TICKET_URL + ticket;
						JSONObject qrImage = new JSONObject();
						qrImage.put("qrImage", getQrticketUrl);
						String resultJson = WechatUtil.resultMessage(0,"success",qrImage);
						Map<String ,Object> map = new HashMap<String ,Object>();
						map.put("qrImage", getQrticketUrl);
						map.put("ticket", ticket);
						map.put("channel", channel);
						map.put("createDate", DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
						weChatQrCodeMapper.saveQRImageAndTicket(map);
						return resultJson;
					}else{
						return WechatUtil.resultMessage(-1,"ticket error");
					}
				} catch (Exception e) {
					logger.warn("qr error" , e);
					return WechatUtil.resultMessage(-1,"qr error");
				}
			}else{
				logger.warn("token is null ");
				return WechatUtil.resultMessage(-1,"token is null");
			}
		}else{
			logger.warn("channel is null");
			return WechatUtil.resultMessage(-1,"channel is null");
		}
	}
	
	
	
	/** 
	* @Title: saveQrCodeEvent 
	* @Description: 保存回调信息
	* @param @param map
	* @param @return   
	* @return int   
	* @throws 
	*/
	public int saveQrCodeEvent(Map<String,Object> map){
		return weChatQrCodeMapper.saveQrCodeEvent(map);
	}
	
	/** 
	* @Title: getWechatUser 
	* @Description: 获取用户渠道
	* @param @return   
	* @return WeChatQrCode   
	* @throws 
	*/
	public List<ReplyMessage> getWechatUser(String openId){
		return weChatQrCodeMapper.getWechatUser(openId);
	}
	
	/** 
	* @Title: getWechatUser 
	* @Description: 获取用户渠道
	* @param @return   
	* @return WeChatQrCode   
	* @throws 
	*/
	public ReplyMessage getReplyMessage(String param){
		return weChatQrCodeMapper.getReplyMessage(param);
	}
	
	/** 
	* @Title: getChannel 
	* @Description: 获取渠道
	* @param @param openId
	* @param @return   
	* @return String   
	* @throws 
	*/
	public String getChannel(String openId){
		if(StringUtils.isEmpty(openId)){
			return WechatUtil.resultMessage(-1,"openId is null");
		}
		WeChatQrCode weChatQrCode = null;
		try {
			weChatQrCode = weChatQrCodeMapper.getWechatPayAttentionInformation(openId);
			if(weChatQrCode != null){
				String channel = weChatQrCode.getEventKey();
				if(StringUtils.isEmpty(channel)){
					return WechatUtil.resultMessage(-1,"channel is null");
				}
				JSONObject data = new JSONObject();
				data.put("channel", channel);
				return WechatUtil.resultMessage(0,"获取渠道成功",data);
			}else{
				return WechatUtil.resultMessage(-1,"channel is null");
			}
		} catch (Exception e) {
			logger.warn("openId 不正确！", e );
			return WechatUtil.resultMessage(-1,"openId 不正确！");
		}
	}
}
