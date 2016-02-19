/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.wechat.web.shiro;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.xn.freamwork.common.ShiroConstants;
import cn.xn.freamwork.support.shiro.ShiroPermission;
import cn.xn.wechat.web.model.UserInfo;
import cn.xn.wechat.web.service.IUserService;

/**
 * 用户校验过滤
 *
 * @author lcl 2014/11/07.
 * @version 1.0.0
 */
public class CustomerFormAuthenFilter extends CaptchaFormAuthenticationFilter {

    @Autowired
    private IUserService userService;


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //最后登录时间
        UserInfo user = new UserInfo();
        user.setUserId(ShiroPermission.getAttribute(ShiroConstants.KEY_SHIRO_UID));
        user.setLaseLogintime(new Date());
        userService.updateUserByUID(user);

        //条件拦截跳转
        //WebUtils.redirectToSavedRequest(request, response, "/user/secure");
        //return false;

        super.setSuccessUrl("/home");
        return super.onLoginSuccess(token, subject, request, response);
    }


    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

}
