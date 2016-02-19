package cn.xn.wechat.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.xn.wechat.web.base.BaseController;

@Controller
@RequestMapping("/bindCustomerService/")
public class BindCustomerServiceController extends BaseController{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** 
	* @Title: bindCustomerServices 
	* @Description: 客服列表
	* @param @return   
	* @return String   
	* @throws 
	*/
	@RequestMapping("list")
	public String bindCustomerServices(){
		logger.info("被添加的客服列表");
		
		return "back-stage-manage/bindCustomerServices";
	}
}
