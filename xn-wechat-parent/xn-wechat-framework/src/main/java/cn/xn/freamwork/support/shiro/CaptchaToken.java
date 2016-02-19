/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.support.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户校验Token
 *
 * @author lcl 2014/08/25
 * @version 1.0.0
 */
public class CaptchaToken extends UsernamePasswordToken
{
    /**
     * Captcha
     */
    private String captcha;

    /**
     * csrf
     */
    private boolean csrf;

    /**
     * 是否密文
     */
    private boolean encrypt;

    /**
     * 用户标识 (0,true/管理员，1,false/用户)
     */
    private boolean identityFlag;


    public CaptchaToken(String username, String password, boolean rememberMe, String host) {
        super(username, password);
    }

    public CaptchaToken(String username, String password, String captcha) {
        super(username, password);
        this.captcha = captcha;
    }

    public CaptchaToken(String username, String password, String captcha,boolean encrypt) {
        super(username, password);
        this.captcha = captcha;
        this.encrypt=encrypt;
    }

    public CaptchaToken(String username, String password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public String getStringPassword(){
        return String.valueOf(getPassword());
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isCsrfAttack() {
        return csrf;
    }

    public CaptchaToken setCsrf(boolean csrf) {
        this.csrf = csrf;
        return this;
    }

    public boolean isCsrf() {
        return csrf;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public boolean isIdentityFlag() {
        return identityFlag;
    }

    public void setIdentityFlag(boolean identityFlag) {
        this.identityFlag = identityFlag;
    }
}