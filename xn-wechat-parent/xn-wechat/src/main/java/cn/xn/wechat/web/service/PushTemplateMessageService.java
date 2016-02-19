package cn.xn.wechat.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.xn.user.domain.BindInfoRlt;
import cn.xn.user.domain.CommonRlt;
import cn.xn.user.domain.CustomerInfoReq;
import cn.xn.user.service.ICustomerInfoService;
import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.model.PushMessageLog;
import cn.xn.wechat.web.model.PushTemplateMessage;
import cn.xn.wechat.web.model.TemplateData;
import cn.xn.wechat.web.model.Wechat;
import cn.xn.wechat.web.model.WxTemplate;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.HttpUtil;
import cn.xn.wechat.web.util.StringUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author rod zhong 
 *
 */
@Service("pushTemplateMessageService")
public class PushTemplateMessageService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private WechatUtil wechatUtil;
	
	@Resource
	private ICustomerInfoService iCustomerInfoService;
	
	@Resource
	private WechatConfigService wechatConfigService;
	
	@Resource
	private PushTemplateMessage pushTemplateMessage;
	
	private String APP_VERSION = "2.0";
	
	private String SOURCETYPE = "wechat";
	
	private String SYSTEMTYPE = "QGZ";
	
	
	/**
	 * @param url 模板链接地址
	 * @param openId 发送客户的ID
	 * @param templateId 模板ID
	 * @param title 标题
	 * @param name 名字
	 * @param remark 描述
	 * @return
	 */
	public JSONObject createTemplateMessage(String url,String openId,String templateId,Map<String,TemplateData> map){
		WxTemplate t = new WxTemplate();  
        t.setUrl(url);  
        t.setTouser(openId);  
        t.setTopcolor("#000000");  
        t.setTemplate_id(templateId);  
//        Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
//        TemplateData first = new TemplateData();  
//        first.setColor("#000000");  
//        first.setValue(mapString.get("title"));  
//        m.put("first", first);  
//        TemplateData templateData = new TemplateData();  
//        templateData.setColor("#000000");  
//        templateData.setValue(mapString.get("name"));  
//        m.put("name", templateData);  
//        TemplateData templateDataRemark = new TemplateData();  
//        templateDataRemark.setColor("blue");  
//        templateDataRemark.setValue(mapString.get("remark"));  
//        m.put("Remark", templateDataRemark);  
        
        t.setData(map); 
       return (JSONObject) JSON.toJSON(t);
	}
	
	private static void addTemplateData(String name,String value,Map<String,TemplateData> map){
		TemplateData templateData = new TemplateData();  
        templateData.setColor("#173177");  
        templateData.setValue(value);  
        map.put(name, templateData);
	}
	
	/**
	 *  获取模板ID
	 * @param token
	 * @param templateIdShort
	 * @return
	 */
	public String getTemplateId(String token,String templateIdShort){
		String url = Constant.GET_TEMPLATE_ID + token;
		JSONObject json = new JSONObject();
		json.put("template_id_short", templateIdShort);
		logger.info("获取模板IDjson =  " + json);
		try {
			String result = HttpUtil.getInstance().phpPost(url,json);
			logger.info("微信获取模板返回的数据 result = " + result);
			JSONObject jsonResult = JSONObject.parseObject(result);
			int code = jsonResult.getInteger("errcode");
			if(code != 0){
				return WechatUtil.resultMessage(-1,"获取模板ID 失败" + result);
			}
			return jsonResult.getString("template_id");
		} catch (Exception e) {
			logger.warn("获取模板ID 失败 " , e);
		}
		return null;
	}
	
	public String sendTemplateMessage(String openId,String templateId,Map<String,TemplateData> map){
		String result = "";
		try {
			if(StringUtils.isEmpty(openId)){
				logger.warn("openId is null ");
				return WechatUtil.resultMessage(-1,"openId is null");
			}else if(StringUtils.isEmpty(templateId)){
				logger.warn("templateId is null ");
				return WechatUtil.resultMessage(-1,"templateId is null");
			}
			JSONObject token = null;
			try {
				//token = JSONObject.parseObject(HttpClientUtil.get(Constant.SING_NAME_URL+"?appId="+Constant.APP_ID+"&secret="+Constant.APP_SECRET+"&grant_type="+Constant.CLIENT_CREDENTIAL));
				token =wechatUtil.getToken();
				logger.info("token ========= " + token);
			} catch (Exception e) {
				logger.warn("get token error ！" , e);
				return WechatUtil.resultMessage(-1,"token is null");
			}
//			Map<String,TemplateData> map = new HashMap<String,TemplateData>();
//			addTemplateData("first","您购买的天添牛回款通知",map);
//			addTemplateData("keyword1","尾号3456",map);
//			addTemplateData("keyword2","88000.00",map);
//			addTemplateData("keyword3","2015年11月05日",map);
//			addTemplateData("remark","224小时内到账，请注意查收，个别银行到账时间会晚1~2天。 如有疑问，请拨打咨询热线 4008-888-888",map);
			//创建模板消息
			JSONObject jsonObject = createTemplateMessage(pushTemplateMessage.getUrl(),openId,templateId,map);
			logger.info("Template 数据:  " + jsonObject.toJSONString());
			if(token != null && StringUtils.isNotEmpty(token.getString("access_token"))){
				result = HttpUtil.getInstance().phpPost(Constant.SEND_TEMPLATE_URL + token.getString("access_token"),jsonObject);
				logger.info("推送微信返回结果数据: =====================" + result);
				JSONObject  jsonResult = JSONObject.parseObject(result);
				logger.info("推送微信返回结果数据: " + result);
				int code = jsonResult.getInteger("errcode");
				if(code != 0 ){
					return WechatUtil.resultMessage(-1,"推送失败");
				}
				return WechatUtil.resultMessage(0,"推送成功");
			}else{
				return WechatUtil.resultMessage(-1,"推送失败 token is null");
			}
		} catch (Exception e) {
			logger.warn("get token error ！" , e);
			return WechatUtil.resultMessage(-1,"获取token 异常");
		}
	}
	
	
	/**
	 * 回款提醒
	 * @param jsonObject
	 */
	public synchronized void handlePayment(JSONObject jsonObject){
		logger.info("=================回款开始发送微信模板消息 =====================");
		PushMessageLog pushMessageLog = new PushMessageLog();
		Wechat wechat = shareCommon(jsonObject);
		if(wechat == null){
			logger.info("没有该微信账户信息，微信推送不了模板消息！");
			return ;
		}
		logger.info("回款提醒MQ主题的消息体: " + jsonObject.toJSONString());
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		String pushTime = DateUtil.StringToString(jsonObject.getString("timeOfPayment"), DateStyle.YYYY_MM_DD_CN);
	 	
		//标题
		addTemplateData("first","尊敬的"+wechat.getMemberName() +"：您购买的  ("+jsonObject.getString("productName")+")，"+pushTime+"有1笔回款通知",map);
		//产品名称
//		addTemplateData("keyword1",jsonObject.getString("productName"),map);
		//回款日期
//		addTemplateData("keyword2",DateUtil.StringToString(jsonObject.getString("timeOfPayment"), DateStyle.YYYY_MM_DD_CN),map);
		String paymentAmount = StringUtil.format(jsonObject.getDouble("paymentAmount")/10000.00, "0.00");
		//回款金额
		addTemplateData("keyword1","人民币"+paymentAmount+"元",map);
		String principal = StringUtil.format(jsonObject.getDouble("principal")/10000.00, "0.00");
		//本金
		addTemplateData("keyword2","人民币"+principal+"元",map);
		String profit= StringUtil.format(jsonObject.getDouble("profit")/10000.00, "0.00");
		//收益
		addTemplateData("keyword3","人民币"+profit+"元",map);
		//描述
		addTemplateData("remark","更多详情，请登录小牛钱罐子查询 >>",map);
		try {
			String message = sendTemplateMessage(wechat.getOpenid(),pushTemplateMessage.getPaymentTemplateId(),map);
			logger.info("微信发送消息回调接口消息结果集 message = " + message);
			JSONObject jsonResult = JSONObject.parseObject(message);
			if(jsonResult.get("code") != null ){
				int errcode = jsonResult.getInteger("code");
				if(errcode == 0){
					pushMessageLog.setState("1");
				}else{
					pushMessageLog.setState("0");
				}
			}else{
				pushMessageLog.setState("0");
			}
		} catch (Exception e) {
			logger.warn("微信推送(MICRO_CHANNEL_PUSH_REMINDER@回款提醒)消息发送失败",e);
			pushMessageLog.setState("0");
		}
		pushMessageLog.setUser_id(jsonObject.getString("userId"));
		pushMessageLog.setUnionId(wechat.getUnionid());
		pushMessageLog.setReal_name(wechat.getMemberName());
		pushMessageLog.setOpenId(wechat.getOpenid());
		pushMessageLog.setMq_topic("MICRO_CHANNEL_PUSH_REMINDER@回款提醒");
		pushMessageLog.setPush_time(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		String message = "标题:尊敬的"+wechat.getMemberName()+("1".equals(wechat.getSex())?"先生":"女士")+" ：您购买的  ("+jsonObject.getString("productName")+")，"+pushTime+"有1笔回款通知"+" \n" +
				"回款金额 :人民币"+paymentAmount+"元" + "\n"+
				"本金:人民币"+principal+"元"+ "\n"+
				"收益:人民币"+profit+"元" + "\n"+
				"描述:更多详情，请登录小牛钱罐子查询 >>";
		pushMessageLog.setMessage(message);
		pushMessageLog.setType("payment");
		logger.info("插入前pushMessageLog : " + pushMessageLog.toString());
		wechatConfigService.savePushMessageLog(pushMessageLog);
		logger.info("插入后pushMessageLog : " + pushMessageLog.toString());
	}
	
	/**
	 * 提现审核
	 * @param jsonObject
	 */
	public synchronized void handleWithdrawals(JSONObject jsonObject,String msgType){
		PushMessageLog pushMessageLog = new PushMessageLog();
		Wechat wechat = shareCommon(jsonObject);
		if(wechat == null){
			logger.info("没有该微信账户信息，微信推送不了模板消息！");
			return ;
		}
		logger.info("提现审核MQ主题的消息体: " + jsonObject.toJSONString());
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		String cashWithdrawal = StringUtil.format(jsonObject.getLong("cashWithdrawal")/10000.00, "0.00");
		String feeString = jsonObject.getString("fee");
		if(StringUtils.isEmpty(feeString)){
			feeString = "0";
		}
		String fee = StringUtil.format(Double.parseDouble(feeString)/10000.00, "0.00");
	 	//标题
		addTemplateData("first","尊敬的 "+wechat.getMemberName()+"：您有1笔小牛钱罐子提现，正在审核",map);
		String txtime = DateUtil.StringToString(jsonObject.getString("outDate"), DateStyle.YYYY_MM_DD_CN_HH_MM);//foms.format(fom.parse(jsonObject.getString("outDate")));
		logger.info("提现审核交易时间: " + txtime);
		//提现审核时间
		addTemplateData("keyword1",txtime,map);		
		//提现审核金额
		addTemplateData("keyword2","人民币"+cashWithdrawal+"元",map);
		//手续费
		addTemplateData("keyword3","人民币"+fee+"元",map);
		//描述
		addTemplateData("remark","温馨提示：工作日17：00前操作提现金额当天到账，17:00以后和节假日操作提现顺延至下一个工作日到账。更多详情，请登录小牛钱罐子查询 >>",map);
		
		try {
			String message = sendTemplateMessage(wechat.getOpenid(),pushTemplateMessage.getWithdrawalsTemplateId(),map);
			logger.info("微信发送消息回调接口消息结果集 message = " + message);
			JSONObject jsonResult = JSONObject.parseObject(message);
			if(jsonResult.get("code") != null ){
				int errcode = jsonResult.getInteger("code");
				if(errcode == 0){
					pushMessageLog.setState("1");
				}else{
					pushMessageLog.setState("0");
				}
			}else{
				pushMessageLog.setState("0");
			}
		} catch (Exception e) {
			logger.warn("微信推送(userOutTopic@提现审核 )消息发送失败",e);
			pushMessageLog.setState("0");
		}
		pushMessageLog.setUser_id(jsonObject.getString("userId"));
		pushMessageLog.setUnionId(wechat.getUnionid());
		pushMessageLog.setReal_name(wechat.getMemberName());
		pushMessageLog.setOpenId(wechat.getOpenid());
		pushMessageLog.setMq_topic("userOutTopic@提现审核");
		pushMessageLog.setPush_time(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		String message = "尊敬的 "+wechat.getMemberName()+("1".equals(wechat.getSex())?"先生":"女士")+"：您有1笔小牛钱罐子提现，正在审核"+" \n" +
				"提现审核时间 :"+ txtime + "\n"+
				"提现审核金额:人民币"+cashWithdrawal+"元"+ "\n"+
				"手续费:人民币"+fee+"元" + "\n"+
				"描述:温馨提示：工作日17：00前操作提现金额当天到账，17:00以后和节假日操作提现顺延至下一个工作日到账。更多详情，请登录小牛钱罐子查询 >>";
		pushMessageLog.setMessage(message);
		pushMessageLog.setType("withdrawals");
		logger.info("插入前pushMessageLog : " + pushMessageLog.toString());
		wechatConfigService.savePushMessageLog(pushMessageLog);
		logger.info("插入后pushMessageLog : " + pushMessageLog.toString());
	}
	
	/**
	 * 提现成功
	 * @param jsonObject
	 */
	public synchronized void handleAccountremind(JSONObject jsonObject,String msgType){
		PushMessageLog pushMessageLog = new PushMessageLog();
		Wechat wechat = shareCommon(jsonObject);
		if(wechat == null){
			logger.info("没有该微信账户信息，微信推送不了模板消息！");
			return ;
		}
		logger.info("提现成功MQ主题的消息体: " + jsonObject.toJSONString());
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
	 	//标题
		addTemplateData("first","尊敬的 "+wechat.getMemberName()+"：您有1笔提现到帐通知",map);
		String amount = StringUtil.format(jsonObject.getLong("amount")/10000.00, "0.00");
		addTemplateData("keyword1","尾号"+jsonObject.getString("bankCradendNumber")+jsonObject.getString("bankName"),map);
		//提现金额
		addTemplateData("keyword2","人民币"+amount +"元",map);
		String fee = StringUtil.format(jsonObject.getLong("fee")/10000.00, "0.00");
		//手续费
//		addTemplateData("keyword2",fee.toString(),map);
		String txtime = DateUtil.StringToString(jsonObject.getString("alertDate"), DateStyle.YYYY_MM_DD_CN_HH_MM);
		logger.info("提现成功交易时间: " + txtime);
		//提现时间
		addTemplateData("keyword3",txtime,map);		
		//描述
		addTemplateData("remark","亲爱的罐主，您申请的提现已成功到账，手续费人民币"+fee+"元，更多详情，请登录小牛钱罐子查询 >>",map);
		try {
			String message = sendTemplateMessage(wechat.getOpenid(),pushTemplateMessage.getAccountremindTemplateId(),map);
			logger.info("微信发送消息回调接口消息结果集 message = " + message);
			JSONObject jsonResult = JSONObject.parseObject(message);
			if(jsonResult.get("code") != null ){
				int errcode = jsonResult.getInteger("code");
				if(errcode == 0){
					pushMessageLog.setState("1");
				}else{
					pushMessageLog.setState("0");
				}
			}else{
				pushMessageLog.setState("0");
			}
		} catch (Exception e) {
			logger.warn("微信推送(userOutTopic@提现成功 )消息发送失败",e);
			pushMessageLog.setState("0");
		}
		pushMessageLog.setUser_id(jsonObject.getString("userId"));
		pushMessageLog.setUnionId(wechat.getUnionid());
		pushMessageLog.setReal_name(wechat.getMemberName());
		pushMessageLog.setOpenId(wechat.getOpenid());
		pushMessageLog.setMq_topic("userOutTopic@提现成功");
		pushMessageLog.setPush_time(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		String message = "尊敬的 "+wechat.getMemberName()+("1".equals(wechat.getSex())?"先生":"女士")+"：您有1笔提现到帐通知"+" \n" +
				"银行:"+ "尾号"+jsonObject.getString("bankCradendNumber")+jsonObject.getString("bankName") + "\n"+
				"提现金额:人民币"+amount+"元"+ "\n"+
				"手续费:人民币"+fee+"元" + "\n"+
				"提现时间 : "+txtime +"\n" + 
				"描述:亲爱的罐主，您申请的提现已成功到账，手续费人民币"+fee+"元，更多详情，请登录小牛钱罐子查询 >>";
		pushMessageLog.setMessage(message);
		pushMessageLog.setType("accountRemind");
		logger.info("插入前pushMessageLog : " + pushMessageLog.toString());
		wechatConfigService.savePushMessageLog(pushMessageLog);
		logger.info("插入后pushMessageLog : " + pushMessageLog.toString());
	}
	
	/**
	 * 红包返现
	 * @param jsonObject
	 */
	public synchronized void handleBacknowred(JSONObject jsonObject){
		PushMessageLog pushMessageLog = new PushMessageLog();
		Wechat wechat = shareCommon(jsonObject);
		if(wechat == null){
			logger.warn("微信没有记录,无法推送消息  wechat is null");
			return ;
		}
		logger.info("红包返现MQ主题的消息体: " + jsonObject.toJSONString());
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
	 	//标题
		addTemplateData("first","尊敬的 "+"：恭喜您，红包返现成功",map);
		String amt = StringUtil.format(jsonObject.getLong("amt")/10000.00, "0.00");
		//返现金额
		addTemplateData("keyword1","人民币"+amt +"元",map);
		//返现日期
		addTemplateData("keyword2",DateUtil.toTimeStampFm(new Date(jsonObject.getLong("doTimes"))),map);
		//描述
		addTemplateData("remark","亲爱的罐主，您红包返现已经发到您的小牛钱罐子账户余额中！更多详情，请登录小牛钱罐子查询 >>",map);
		try {
			String message = sendTemplateMessage(wechat.getOpenid(),pushTemplateMessage.getBacknowredTemplateId(),map);
			logger.info("微信发送消息回调接口消息结果集 message = " + message);
			JSONObject jsonResult = JSONObject.parseObject(message);
			if(jsonResult.get("code") != null ){
				int errcode = jsonResult.getInteger("code");
				if(errcode == 0){
					pushMessageLog.setState("1");
				}else{
					pushMessageLog.setState("0");
				}
			}else{
				pushMessageLog.setState("0");
			}
		} catch (Exception e) {
			logger.warn("微信推送(ACTIVITY_ACCOUNT_AMOUNT_CHANGE@红包返现 )消息发送失败",e);
			pushMessageLog.setState("0");
		}
		pushMessageLog.setUser_id(jsonObject.getString("userId"));
		pushMessageLog.setUnionId(wechat.getUnionid());
		pushMessageLog.setReal_name(wechat.getMemberName());
		pushMessageLog.setOpenId(wechat.getOpenid());
		pushMessageLog.setMq_topic("ACTIVITY_ACCOUNT_AMOUNT_CHANGE@红包返现");
		pushMessageLog.setPush_time(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		String message = "尊敬的 "+wechat.getMemberName()+("1".equals(wechat.getSex())?"先生":"女士")+"：恭喜您，红包返现成功"+" \n" +
				"返现金额:人民币"+amt+"元"+ "\n"+
				"返现日期 : "+DateUtil.toTimeStampFm(new Date(jsonObject.getLong("doTimes"))) +"\n" + 
				"描述:亲爱的罐主，您红包返现已经发到您的小牛钱罐子账户余额中！更多详情，请登录小牛钱罐子查询 >>";
		pushMessageLog.setMessage(message);
		pushMessageLog.setType("exchange_redpaper");
		logger.info("插入前pushMessageLog : " + pushMessageLog.toString());
		wechatConfigService.savePushMessageLog(pushMessageLog);
		logger.info("插入后pushMessageLog : " + pushMessageLog.toString());
	}
	
	private Wechat shareCommon(JSONObject jsonObject){
		Wechat wc =new Wechat();
		String userId = jsonObject.getString("userId");
		if(StringUtils.isEmpty(userId)){
			logger.warn("userId is null");
		}
		CustomerInfoReq customerInfoReq = new CustomerInfoReq();
		customerInfoReq.setAppVersion(APP_VERSION);
		customerInfoReq.setMemberNo(userId);
		customerInfoReq.setSourceType(SOURCETYPE);
		customerInfoReq.setSystemType(SYSTEMTYPE);
		CommonRlt<BindInfoRlt>  commonRlt  = iCustomerInfoService.getBindInfo(customerInfoReq);
		BindInfoRlt  bindInfoRlt = commonRlt.getData();
		if(bindInfoRlt == null){
			logger.warn("用户不存在，不支持推送模板消息");
			return null;
		}
		logger.info("用户中心返回的用户数据"+ JSON.toJSONString(commonRlt));
		String unionId = bindInfoRlt.getUnionId();
//		String unionId = "ozbuTtyMW9h0C8St4JGDHPtIMk28";
		if(StringUtils.isEmpty(unionId)){
			logger.warn("unionId 用户中心返回的是null，不支持推送模板消息");
			return null;
		}
		
		wc.setUnionid(unionId);
		Wechat wechat =new Wechat();
		wechat = wechatConfigService.getWechatUserDetails(wc);
		if(wechat == null){
			logger.warn("微信没有保存该用户信息，不支持推送模板消息");
			return null;
		}
		if(StringUtils.isEmpty(wechat.getOpenid())){
			logger.warn("openId is null ，不支持推送模板消息");
			return null;
		}
//		wechat.setMemberName("欧明珠");
		wechat.setMemberName(bindInfoRlt.getMemberName());
		return wechat;
	
	}
	
	public static void main(String[] args) {
//		PushTemplateMessageService pt = new PushTemplateMessageService();
//		 Map<String,TemplateData> map = new HashMap<String,TemplateData>();
//			addTemplateData("first","您购买的天添牛回款通知",map);
//			addTemplateData("keyword1","尾号3456",map);
//			addTemplateData("keyword2","88000.00",map);
//			addTemplateData("keyword3","2015年11月05日",map);
//			addTemplateData("remark","224小时内到账，请注意查收，个别银行到账时间会晚1~2天。 如有疑问，请拨打咨询热线 4008-888-888",map);
//		System.out.println(pt.sendTemplateMessage("o7Yy8uCO9GQLI1-h3Upky_WqJU5c", "hOFHMpxTc96TH_yMdfQ71C1o4iHiN9Nvf0XFCaDW1ns",map));
//		
		SimpleDateFormat fom = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat foms = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		String txtime = null;
		String s = "20151116154928";
		try {
			Date date = fom.parse(s);
			txtime = foms.format(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(txtime);
	}
	
}
