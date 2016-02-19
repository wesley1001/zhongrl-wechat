package cn.xn.wechat.web.shiro.realm;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import cn.xn.freamwork.common.ShiroConstants;
import cn.xn.freamwork.util.DateUtils;
import cn.xn.wechat.web.model.UserInfo;
import cn.xn.wechat.web.service.IUserService;

@Component
public class UserRealm extends AuthorizingRealm
{

    @Resource
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        Map<String, Object> userMap = new HashMap<String, Object>();
        SimpleAuthenticationInfo authenticationInfo = null;
        //获取 AuthenticatingFilter 过滤其中传入的 token
        //file method: return onLoginSuccess(token, subject, request, response);
        String username = (String) token.getPrincipal();

        //CaptchaToken tokenContent = (CaptchaToken) token;
        UserInfo user = userService.findInfoAllByUser(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        //用户信息放入 LinkedHashMap 集合维持
        userMap.put(ShiroConstants.KEY_SHIRO_USER_INFO, user);
        userMap.put(ShiroConstants.KEY_SHIRO_UID, user.getUserId());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_NAME, user.getUserLoginName());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_AS, user.getUserAs());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_EMAIL, user.getEmail() == null ? "":user.getEmail());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_PHONE, user.getPhoneNumber() == null ? "":user.getPhoneNumber());
        userMap.put(ShiroConstants.KEY_SHIRO_USER_REALNAME,
                user.getIsCertification() == null ? 0:user.getIsCertification());
        userMap.put(ShiroConstants.KEY_SHIRO_QUESTION,
                user.getIsQuestion() == null ? 0:user.getIsQuestion());
        userMap.put(ShiroConstants.KEY_SHIRO_IS_PAYMENTPWD, user.getIsPaymentPwd());

        userMap.put(ShiroConstants.KEY_SHIRO_LOGIN_TIME,
                DateUtils.format(user.getLaseLogintime(), DateUtils.FORMAT_TIME));

        authenticationInfo = new SimpleAuthenticationInfo(
                userMap, //用户信息
                user.getLoginPwd(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
