/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.common;

/**
 * Shiro常量类
 *
 * @author lcl 2014/11/08.
 * @version 1.0.0
 */
public class ShiroConstants {

    /**
     * ================= Shiro 用户身份 ==================
     */
    public static final String SHIRO_USER_IDENTITY_ADMIN = "0";
    public static final String SHIRO_USER_IDENTITY_CUSTOMER= "1";


    /**
     * ================= Shiro ==================
     */
    public static final String KEY_SHIRO_USER_INFO = "userInfo";
    //用户编号
    public static final String KEY_SHIRO_UID = "uid";
    //userLoginName
    public static final String KEY_SHIRO_USER_NAME = "userName";
    //userAs
    public static final String KEY_SHIRO_USER_AS = "userAs";
    //email
    public static final String KEY_SHIRO_USER_EMAIL = "email";
    //phone
    public static final String KEY_SHIRO_USER_PHONE = "phone";
    //实名认证
    public static final String KEY_SHIRO_USER_REALNAME = "isRealname";
    //是否设置密保问题
    public static final String KEY_SHIRO_QUESTION = "isQuestion";
    //是否修改支付密码
    public static final String KEY_SHIRO_IS_PAYMENTPWD = "isPaymentPwd";

    //最后登录时间
    public static final String KEY_SHIRO_LOGIN_TIME = "loginLastTime";


    //实名认证中文名
    public static final String KEY_SHIRO_AUTH_NAME = "cname";
    //实名认证身份证
    public static final String KEY_SHIRO_AUTH_ID = "cid";


    /**
     * ================= 认证状常量 ==================
     */
    public static final String AUTH_STATE_PHONE = "isNotPhone";

    public static final String AUTH_STATE_REALNAME = "isNotRealName";

}
