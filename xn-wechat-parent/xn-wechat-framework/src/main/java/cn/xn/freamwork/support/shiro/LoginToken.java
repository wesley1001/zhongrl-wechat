package cn.xn.freamwork.support.shiro;

/**
 * 登陆Token
 *
 * @author lcl
 *
 */
public class LoginToken extends CaptchaToken {
    /**
     * 是否自动登录token
     */
    private boolean autoLogin;

    public LoginToken(String username) {
        super(username, null, null);
        autoLogin=true;
    }
    public LoginToken(String username, String password, String captcha) {
        super(username, password, captcha);
    }

    public LoginToken(String username, String password, String captcha, boolean encrypt) {
        super(username, password, captcha, encrypt);
    }

    public LoginToken(String username, String password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host, captcha);
    }

    public LoginToken setAuto(){
        this.autoLogin=true;
        return this;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }
}
