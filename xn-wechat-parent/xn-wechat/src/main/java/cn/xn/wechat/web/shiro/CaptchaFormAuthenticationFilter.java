package cn.xn.wechat.web.shiro;

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
 * <p>验证码过滤器 </p>
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

	public static final String CAPTCHA_FLAG="_captcha_flag";
    private Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    /**
     * 是否验证验证码:默认false
     */
    private boolean validateCaptcha;

    private String successUrl = "/index.do";

    public void setValidateCaptcha(boolean validateCaptcha) {
        this.validateCaptcha = validateCaptcha;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, "captcha");
    }

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		super.setSuccessUrl(this.successUrl);
        return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        return new CaptchaToken(getUsername(request), getPassword(request), isRememberMe(request), getHost(request), getCaptcha(request));
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		CaptchaToken token = (CaptchaToken) createToken(request, response);

        try {
            if (token == null) {
                String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                        "must be created in order to execute a login attempt.";
                throw new TokenAuthenticationException(msg, "token");
            }

            if (StringUtils.isEmpty(token.getUsername())
                    || StringUtils.isEmpty(token.getPassword())) {

                String msg = "用户名/密码/验证码 不能为空!";
                throw new TokenAuthenticationException(msg, "token");
            }

            //进行登录
            Subject subject = getSubject(request, response);

            //调用登录Realm中认证
            subject.login(token);

            //登录成功跳转页面
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            if (logger.isInfoEnabled()) {
                logger.info("Authentication fail,token:" + token, e);

            }
			//认证错误后，才需要输入验证码
			//((HttpServletRequest)request).getSession().setAttribute(CAPTCHA_FLAG, new Object());

            return onLoginFailure(token, e, request, response);
        }
    }


    /*protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        setFailureAttribute(request, e);
        //login failed, let request continue back to the login page:
        *//*try {
            WebUtils.issueRedirect(request, response, "http://www.baidu.com");
        } catch (IOException e1) {
            e1.printStackTrace();
        }*//*

        return true;
    }*/

    /**
     * 子类实现
     * @param request
     * @return
     */
	public boolean doValidateCaptcha(HttpServletRequest request){
		return false;
	}


	/**
	 * 是否需要验证验证码
	 * @param request 请求对象
	 * @return 是否需要进行验证码验证
	 */
	private boolean isCaptchaCheck(HttpServletRequest request) {
		boolean check = validateCaptcha && request.getSession().getAttribute(CAPTCHA_FLAG) != null ;
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

	public class CsrfAuthenticationException extends AuthenticationException {
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

	public class CaptchaAuthenticationException extends CsrfAuthenticationException {

        public CaptchaAuthenticationException(String message, String code) {
			super(message, code);
		}
	}


    public class TokenAuthenticationException extends CsrfAuthenticationException {

        public TokenAuthenticationException(String message, String code) {
            super(message, code);
        }
    }
}
