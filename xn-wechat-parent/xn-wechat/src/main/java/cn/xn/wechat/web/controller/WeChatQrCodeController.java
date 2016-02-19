package cn.xn.wechat.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.model.BiScanCode;
import cn.xn.wechat.web.service.WeChatQrCodeService;
import cn.xn.wechat.web.service.WechatConfigService;
import cn.xn.wechat.web.util.SHA1;
import cn.xn.wechat.web.util.WechatUtil;

@RequestMapping("/weChatQrCode/")
@Controller
public class WeChatQrCodeController {

	private static Logger logger = LoggerFactory.getLogger(WeChatQrCodeController.class);
	
	@Resource
	private WeChatQrCodeService weChatQrCodeService;
	
	@Resource
	private WechatUtil wechatUtil;
	
	@Resource
	private WechatConfigService wechatConfigService;
	
	/** 
	* @Title: qrCodeEvent 
	* @Description: 微信扫码事件回调接口
	* @param    
	* @return void   
	* @throws 
	*/
	@RequestMapping(value="analyticalQrcodeEvent")
	@ResponseBody
	public void qrCodeEvent(HttpServletRequest request,HttpServletResponse response){
		
		//response.addHeader("Access-Control-Allow-Origin", "*");
		boolean isGet = request.getMethod().toLowerCase().equals("get");  
		if(isGet){
			checkWeChat(request,response);// 接口签名
		}else{
			try {
				wechatUtil.acceptMessage(request, response); //处理微信事件
			} catch (IOException e) {
				logger.warn("接收事件失败！");
			}
		}
	}
	
	/** 
	* @Title: getChannel 
	* @Description: 获取渠道接口
	* @param @param openId
	* @param @param response
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping(value="getChannel",produces="text/json;charset=UTF-8")
	@ResponseBody
	public String getChannel(String openId,HttpServletResponse response){
		//response.addHeader("Access-Control-Allow-Origin", "*");
		return weChatQrCodeService.getChannel(openId);
	}
	

	/** 
	* @Title: checkWeChat 
	* @Description: 校验微信是验证
	* @param @param request
	* @param @param response
	* @param @return   
	* @return boolean   
	* @throws 
	*/
	private  boolean checkWeChat(HttpServletRequest request,HttpServletResponse response){
		String signature = request.getParameter("signature");
		if(StringUtils.isEmpty(signature)){
			return false;
		}
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		return checkSignature(Constant.TOKEN,signature,timestamp,nonce,echostr,response) == 0 ? true : false;
	} 
	
	/** 
	* @Title: createQrCode 
	* @Description: 生成永久二维码
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("createQrCode")
	@ResponseBody
	public String createQrCode(String channel,String wechatName,HttpServletRequest request,HttpServletResponse response){
		try {
			//response.addHeader("Access-Control-Allow-Origin", "*");
			request.setCharacterEncoding("utf-8");
			return weChatQrCodeService.createQrCode(channel,wechatName);
		} catch (Exception e) {
			logger.warn("编码错误",e);
		}
		return null;
	}
	
	/** 
	* @Title: checkSignature 
	* @Description: 微信检验接口的校验
	* @param @param token
	* @param @param signature
	* @param @param timestamp
	* @param @param nonce
	* @param @param echostr
	* @param @param response
	* @param @return   
	* @return int   
	* @throws 
	*/
	public static int checkSignature(String token, String signature, String timestamp, String nonce, String echostr, HttpServletResponse response) {
		int iResult = 0;
		try {
			
			logger.debug("----------checkSignature-----------token="+token+",signature"+signature+",timestamp="+timestamp+",nonce="+nonce+",echostr"+echostr);
			String finallyString = "";
			String[] s = new String[] {token, timestamp, nonce};
			s = stringSort(s);
			for (int i = 0; i < s.length; i++) {
				finallyString = finallyString + s[i];
			}

			String signatureLocal = new SHA1().getDigestOfString(finallyString.getBytes());
			logger.debug("--------signatureLocal="+signatureLocal);
			if (signature.toLowerCase().equals(signatureLocal.toLowerCase())) {
				if (echostr!=null&&echostr.length() > 0) {
					OutputStream out = response.getOutputStream();
					out.write(echostr.getBytes());
					out.flush();
					out.close();
				}
				logger.debug("signature eq signatureLocal");
			}else{
				iResult=1;
				logger.debug("signature not eq signatureLocal");
			}
		} catch (Exception e) {
			iResult = 1;
			logger.warn("checkSignature error",e);
		}

		return iResult;
	}

	private static String[] stringSort(String[] s) {
		List<String> list = new ArrayList<String>(s.length);
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		Collections.sort(list);
		return list.toArray(s);
	}

	
	/** 
	* @Title: createQrPage 
	* @Description: 创建二维码页面
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("createQrPage")
	public String createQrPage(HttpServletResponse response,ModelMap modelMap){
		modelMap.put("wechatConfigs", wechatConfigService.getWechatConfig());
		return "back-stage-manage/qr_code_create";
	}
	
	@RequestMapping("statisticalScanCode")
	public String statisticalScanCode(ModelMap modelMap,String page){
		if(StringUtils.isEmpty(page)){
			page = "0";
		}
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("page", Integer.parseInt(page));
		map.put("pageSize", 10);
		List<BiScanCode> biScanCodes = wechatConfigService.statisticalScanCodes(map);
		logger.info("统计数据biScanCodes = "+biScanCodes.toString());
	
		if(!biScanCodes.isEmpty() && biScanCodes.size() > 0){
			double count= 0;
			for (int i = 0; i < biScanCodes.size(); i++) {
				count = count + biScanCodes.get(i).getNumber();
			}
			StringBuffer listObject = new StringBuffer();
			StringBuffer listYuan = new StringBuffer();
			listYuan.append("[");
			listObject.append("[");
			int num = 1;
			for (BiScanCode biScanCode : biScanCodes) {
				if("soufang".equals(biScanCode.getQrCodeName())){
					biScanCode.setQrCodeName("搜房网");
				}else if("wechat".equals(biScanCode.getQrCodeName())){
					biScanCode.setQrCodeName("微信");
				}
				if(num == biScanCodes.size()){
					listObject.append("{device:'"+biScanCode.getQrCodeName()+"',sells:"+biScanCode.getNumber()+"}");
					listYuan.append("{label:'"+biScanCode.getQrCodeName()+"',value:"+Double.parseDouble(String.format("%.2f", ((double)biScanCode.getNumber()*100.00)/count))+"}");
				}else{
					listObject.append("{device:'"+biScanCode.getQrCodeName()+"',sells:"+biScanCode.getNumber()+"},");
					listYuan.append("{label:'"+biScanCode.getQrCodeName()+"',value:"+Double.parseDouble(String.format("%.2f", ((double)biScanCode.getNumber()*100.00)/count))+"},");
				}
				num++;
			}
			listYuan.append("]");
			listObject.append("]");
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < biScanCodes.size(); i++) {
				if(i%2 == 0){
					sb.append("\"#30a1ec\",");
				}else if (i == biScanCodes.size() -1){
					sb.append("\"#c4dafe\"");
				}else{
					sb.append("\"#76bdee\",");
				}
			}
			sb.append("]");
			logger.info("曲线图的：data = "+listObject.toString());
			logger.info("饼图图的：colors = "+sb.toString());
			modelMap.put("biScanCodes", biScanCodes);
			modelMap.put("totalPage", wechatConfigService.statisticalScanCodeCount());
			modelMap.put("page", Integer.parseInt(page));
			modelMap.put("pageSize", 10);
			modelMap.put("data", listObject.toString());
			modelMap.put("dataYuan", listYuan.toString());
			modelMap.put("colors", sb.toString());
		}
		return "back-stage-manage/statisticalScanCode";
	}
}
