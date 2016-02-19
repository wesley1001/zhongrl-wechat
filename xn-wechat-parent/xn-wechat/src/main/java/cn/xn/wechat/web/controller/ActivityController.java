package cn.xn.wechat.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;
import cn.xn.wechat.web.base.BaseController;
import cn.xn.wechat.web.constant.Constant;
import cn.xn.wechat.web.model.ClickNumber;
import cn.xn.wechat.web.model.WechatConfig;
import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.util.DateStyle;
import cn.xn.wechat.web.util.DateUtil;
import cn.xn.wechat.web.util.SHA1;
import cn.xn.wechat.web.util.StringUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：ActivityController.java
 * 
 * 类描述：微信活动控制层
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年7月30日 上午10:50:18
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/activity/")
public class ActivityController extends BaseController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActivityService activityService;
	
	@Resource
	private WechatUtil wechatUtil;

	@RequestMapping("test")
	public String test() {

		return "uploadImage";
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
	public String createQrCode(String channel){
		try {
			getRequest().setCharacterEncoding("utf-8");
			return activityService.createQrCode(channel);
		} catch (Exception e) {
			logger.warn("编码错误",e);
		}
		return null;
	}
	
	
	/** 
	* @Title: createQrCode 
	* @Description: 生成永久二维码
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("createTemporaryQrCode")
	@ResponseBody
	public String createTemporaryQrCode(String channel){
		try {
			getRequest().setCharacterEncoding("utf-8");
			return wechatUtil.createTemporaryQrCode(Integer.parseInt(channel),604800);
		} catch (Exception e) {
			logger.warn("编码错误",e);
		}
		return null;
	}
	
	@RequestMapping("updateToken")
	@ResponseBody
	public String updateToken(){
		try {
			getRequest().setCharacterEncoding("utf-8");
			return JSON.toJSONString(activityService.getWechatTicket());
		} catch (Exception e) {
			logger.warn("编码错误",e);
		}
		return null;
	}
	
	
	/**
	 * @Title: recordClickNumber
	 * @Description: 计算点击次数
	 * @param @param number
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("recordClickNumber")
	@ResponseBody
	public String recordClickNumber(HttpServletResponse response) {
		// 解决跨域问题
		//response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("record number start ");
		ClickNumber clickNumber = new ClickNumber();
		clickNumber.setActivityType(Constant.ACTIVITY_TYPE);
		clickNumber.setShowClickNumber(StringUtil.randomClickNumber());
		try {
			clickNumber = activityService.updateClickNumber(clickNumber);
			clickNumber.setCode("success");
			JSONObject jsonClickNumber = new JSONObject();
			jsonClickNumber.put("showClickNumber",
					clickNumber.getShowClickNumber());
			return WechatUtil.resultMessage(0,"success",jsonClickNumber);
		} catch (Exception e) {
			clickNumber.setCode("error");
			logger.warn("数据库查询错误！", e);
			return WechatUtil.resultMessage(-1,"number is null");
		}
	}

	/**
	 * @Title: fileUpload
	 * @Description: 上传文件
	 * @param @param multipartFile
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("uploadImage")
	@ResponseBody
	public String uploadImage(HttpServletResponse response,String image) {
		// 解决跨域问题
		//response.addHeader("Access-Control-Allow-Origin", "*");
		return uploadImage(image);
	}

	private static int COUNT = 1;
	
	/**
	 * @Title: uploadImage
	 * @Description: 上传图片操作
	 * @param @param req
	 * @param @return
	 * @return String
	 * @throws
	 */
	
	private String uploadImage(String image) {
		if (StringUtils.isEmpty(image)) {
			return WechatUtil.resultMessage(-1,"image is null");
		}
		// 只允许jpg
		String header = "data:image/jpeg;base64,";
		if (image.indexOf(header) != 0) {
			logger.warn("图片格式不正确，必须是jpg,jpeg");
			return WechatUtil.resultMessage(-1,"图片格式不正确，必须是jpg,jpeg");
		}
		// 去掉头部
		image = image.substring(header.length());
		// 写入磁盘
		BASE64Decoder decoder = new BASE64Decoder();
		JSONObject jsonObjectImage = new JSONObject();
		try {
			byte[] decodedBytes = decoder.decodeBuffer(image);
			// 写入文件
			String fileUrl = "wechatActivity/"
					+ DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD);
			String imageName = "/"
					+ DateUtil.DateToString(new Date(),
							DateStyle.YYYYMMDDHHMMSS)+new Random().nextInt(9999)+"_"+(COUNT++)+ ".jpg";
			String tomcatPath = getRequest().getSession().getServletContext()
					.getRealPath("/")
					+ fileUrl; // 得到保存的路径
			File file = new File(tomcatPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			tomcatPath = tomcatPath + imageName;
			FileOutputStream fileOutputStream = new FileOutputStream(tomcatPath);
			fileOutputStream.write(decodedBytes);
			fileOutputStream.flush();
			fileOutputStream.close();
			String urlPath = "http://" + getRequest().getServerName() + ":"
					+ getRequest().getServerPort()
					+ getRequest().getContextPath() + "/" + fileUrl + imageName;

			jsonObjectImage.put("imageUrl", urlPath);
			return WechatUtil.resultMessage(0,"success",jsonObjectImage);
		} catch (Exception e) {
			logger.warn("上传图片异常", e);
			return WechatUtil.resultMessage(-1,"图片上传失败！");
		}
	}
}
