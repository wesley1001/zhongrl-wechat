package cn.xn.freamwork.support.shiro;

import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.util.WebUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 企业访问权限过滤器
 *
 * @author lcl 2014/08/15
 * @version 1.0.0
 */
public class EnterpriseRealNameFilter extends OncePerRequestFilter
{
    private String redirectURL = "/business";


    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException
    {

        //非企业用户跳转页面
        /*if(!PayUtil.isEwtEnterpriseUser(null)) {
            WebUtils.redirectToSavedRequest(request, response, redirectURL);
            return;
        }*/

        chain.doFilter(request,response);
    }


    public void setRedirectURL(String redirectURL)
    {
        this.redirectURL = redirectURL;
    }
}
