package cn.xn.freamwork.support.shiro.parent;
import cn.xn.freamwork.support.shiro.CaptchaToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录信息校验过滤
 *
 * @author lcl 2014/08/25
 * @version 1.0.0
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter
{

    public static final String CAPTCHA_FLAG="_captcha_flag";
    private Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    /**
     * 是否验证验证码:默认false
     */
    private boolean validateCaptcha;

    public void setValidateCaptcha(boolean validateCaptcha) {
        this.validateCaptcha = validateCaptcha;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, "captcha");
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        return new CaptchaToken(getUsername(request), getPassword(request), isRememberMe(request), getHost(request), getCaptcha(request));
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        CaptchaToken token = (CaptchaToken) createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }

        try {

            if(token.isCsrfAttack()){
                logger.warn("may be is csrf attack ip:{}",request.getRemoteAddr());
                throw new CsrfAuthenticationException("请求出错,请重试","csrf");
            }

            //校验验证码
            if (isCaptchaCheck((HttpServletRequest) request) &&
                    !doValidateCaptcha((HttpServletRequest) request)) {
                if (logger.isInfoEnabled()) {
                    logger.info("captcha error");
                }
                throw new CaptchaAuthenticationException("验证码出错","captcha");
            }

            //进行登录
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            if (logger.isInfoEnabled()) {
                logger.info("Authentication fail,token:" + token, e);

            }
            //认证错误，输入验证码
            ((HttpServletRequest)request).getSession().setAttribute(CAPTCHA_FLAG,new Object());
            return onLoginFailure(token, e, request, response);
        }
    }


    public  boolean doValidateCaptcha(HttpServletRequest request){
        return false;
    }


    /**
     * 是否需要验证验证码
     * @param request 请求对象
     * @return 是否需要进行验证码验证
     */
    private boolean isCaptchaCheck(HttpServletRequest request) {
        boolean check= validateCaptcha
                &&
                request.getSession().getAttribute(CAPTCHA_FLAG) != null ;
        request.getSession().removeAttribute(CAPTCHA_FLAG);
        return check;
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String redirectUrl = WebUtils.getCleanParam(request, "redirectUrl");

        if (!StringUtils.isEmpty(redirectUrl)) {
            WebUtils.issueRedirect(request, response, redirectUrl);
        } else {
            WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
        }

    }


    public class CsrfAuthenticationException extends AuthenticationException{
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public CsrfAuthenticationException(String message) {
            super(message);    //To change body of overridden methods use File | Settings | File Templates.
        }

        public CsrfAuthenticationException(String message, String code) {
            super(message);
            setCode(code);
        }
    }

    public class CaptchaAuthenticationException extends CsrfAuthenticationException{

        public CaptchaAuthenticationException(String message, String code) {
            super(message, code);
        }
    }
}
