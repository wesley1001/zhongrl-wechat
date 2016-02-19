package cn.xn.wechat.web.controller.backmanage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.xn.freamwork.common.ShiroConstants;
import cn.xn.freamwork.exception.WebException;
import cn.xn.freamwork.support.shiro.ShiroPermission;
import cn.xn.wechat.web.base.BaseController;

/**
 *
 * 项目名称：xn-wechat-activity
 * 
 * 类名称：LoginController.java
 * 
 * 类描述：微信后台登录界面
 * 
 * 创建人： Rod Zhong
 * 
 * 创建时间：2015年8月24日 下午2:57:56
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@RequestMapping("/")
@Controller
public class LoginController  extends BaseController{

	@RequestMapping(value="login", method = RequestMethod.GET)
	public String login(){
		
		return "back-stage-manage/login";
	}
	
	@RequestMapping("index")
	public String index(){
		
		return "back-stage-manage/index";
	}
	
	  @RequestMapping(value = "/login", method = RequestMethod.POST)
	  public String login(HttpServletRequest req,
	                        HttpServletResponse response,
	                        ModelMap model) throws WebException {

	        //是否登录
	        if(StringUtils.isNotEmpty(ShiroPermission.getAttribute(ShiroConstants.KEY_SHIRO_UID)))
	            return "/back-stage-manage/login";

	        Object failure = req.getAttribute("shiroLoginFailure");
	        String exceptionClassName = (null != failure) ? String.valueOf(failure):"";
	        String error = null;
	        if(StringUtils.isNotEmpty(exceptionClassName)) {

	            if (UnknownAccountException.class.getName().equals(exceptionClassName)
	                    || AuthenticationException.class.getName().equals(exceptionClassName)
	                    || exceptionClassName.indexOf(IncorrectCredentialsException.class.getName()+":") >= 0
	                    || exceptionClassName.indexOf(ExcessiveAttemptsException.class.getName()) >= 0) {

	                error = "用户名/密码错误";
	            } else {
	                String[] errMsg = exceptionClassName.split(":");
	                error = (null != errMsg ? errMsg[errMsg.length-1]:"");
	                req.getSession().setAttribute("username", exceptionClassName);
	            }

	        }
	        model.put("error_msg", error);
	        //this.putErrorMsg(model, error);
	        return "/back-stage-manage/login";
	    }


	    @RequestMapping(value = "/logout")
	    public String logout() throws Exception {

	        SecurityUtils.getSubject().logout();
	        return "redirect:/";
	    }
}
