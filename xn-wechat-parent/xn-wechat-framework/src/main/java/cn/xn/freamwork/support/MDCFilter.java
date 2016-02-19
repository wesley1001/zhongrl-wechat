package cn.xn.freamwork.support;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * MDC Filter
 */
public class MDCFilter implements Filter {
    public void destroy() {
        // do nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {


        insertIntoMDC(request);
        try {
            chain.doFilter(request, response);
        } finally {
            clearMDC();
        }
    }

    void insertIntoMDC(ServletRequest request) {

        MDC.put(REQUEST_REMOTE_HOST_MDC_KEY, request.getRemoteHost());

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            MDC.put(REQUEST_REQUEST_URI, httpServletRequest.getRequestURI());
            StringBuffer requestURL = httpServletRequest.getRequestURL();
            if (requestURL != null) {
                MDC.put(REQUEST_REQUEST_URL, requestURL.toString());
            }
            MDC.put(REQUEST_QUERY_STRING, httpServletRequest.getQueryString());
            MDC.put(REQUEST_USER_AGENT_MDC_KEY, httpServletRequest.getHeader("User-Agent"));
            MDC.put(REQUEST_X_FORWARDED_FOR, httpServletRequest.getHeader("X-Forwarded-For"));
            MDC.put(REQUEST_REFERER, httpServletRequest.getHeader("Referer"));
            MDC.put(REQUEST_METHOD, httpServletRequest.getMethod());
           // MDC.put(REQUEST_JSESSIONID, httpServletRequest.getRequestedSessionId());
        }

    }

    void clearMDC() {
        MDC.remove(REQUEST_REMOTE_HOST_MDC_KEY);
        MDC.remove(REQUEST_REQUEST_URI);
        MDC.remove(REQUEST_QUERY_STRING);
        // removing possibly inexistent item is OK
        MDC.remove(REQUEST_REQUEST_URL);
        MDC.remove(REQUEST_USER_AGENT_MDC_KEY);
        MDC.remove(REQUEST_X_FORWARDED_FOR);


        MDC.remove(REQUEST_METHOD);
        MDC.remove(REQUEST_REFERER);
        MDC.remove(REQUEST_JSESSIONID);
    }

    public void init(FilterConfig arg0) throws ServletException {
        // do nothing
    }


    public final static String REQUEST_REMOTE_HOST_MDC_KEY = "req.remoteHost";
    public final static String REQUEST_USER_AGENT_MDC_KEY = "req.userAgent";
    public final static String REQUEST_REQUEST_URI = "req.requestURI";
    public final static String REQUEST_QUERY_STRING = "req.queryString";
    public final static String REQUEST_REQUEST_URL = "req.requestURL";
    public final static String REQUEST_X_FORWARDED_FOR = "req.xForwardedFor";
    public final static String REQUEST_METHOD= "req.method";
    public final static String REQUEST_REFERER= "req.referer";
    public final static String REQUEST_JSESSIONID= "req.jsessionid";
}
