package cn.xn.wechat.web.base;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.xn.wechat.activity.mapper.ShareLogMapper;
import cn.xn.wechat.activity.service.IWechatActivityService;

/**
 * Controller 基类， 所有的controller 需要从此继承
 * 
 * @author Administrator
 */
@Controller
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	protected IWechatActivityService wechatActivityService;
	@Resource
	protected ShareLogMapper shareLogMapper;

	private static final String RESULT_KEY_ERROR = "error";
	private static final String R_KEY_CODE = "code";
	private static final String R_KEY_MSG = "msg";

	/**
	 * 返回值KEY
	 */
	private static final String RESULT_DATA_KEY = "rows";
	private static final String RESULT_DATA_PAGE = "currentPage";
	private static final String RESULT_DATA_TOTAL = "total";
	private static final String RESULT_DATA_PAGENUM = "pageNum";

	private static final String RESULT_DATA_TRADESTART = "tradeStart";
	private static final String RESULT_DATA_TRADEEND = "tradeEnd";

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	public ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext()
				.getServletContext();
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}

	protected boolean isMobile(String str) {
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public String encode(String string) {
		try {
			string = URLEncoder.encode(string, "utf-8");
		} catch (UnsupportedEncodingException e1) {
		}
		return string;
	}

	public String getString(String name, boolean decode) {
		return getString(name, null, decode);
	}

	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		return getString(name, defaultValue, true);
	}

	public String getString(String name, String defaultValue, boolean decode) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr)
				|| "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			if (decode) {
				try {
					resultStr = URLDecoder.decode(resultStr, "utf-8");
				} catch (UnsupportedEncodingException e) {
				}
			}
			return resultStr;
		}
	}

	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		boolean result = defaultValue;
		String resultStr = getString(name);
		if ("true".equals(resultStr)) {
			result = true;
		}
		return result;
	}

	public void outPrint(HttpServletResponse response, Object result) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIpAddr(HttpServletRequest request) {
		// 首先获得用户的代理服务
		String fromIp = request.getHeader("x-forwarded-for");
		// 再获取nginx的ip
		if (StringUtils.isBlank(fromIp) || "unknown".equals(fromIp.trim())) {
			fromIp = request.getHeader("X-Real-IP");
		}
		// 再获取用户的请求ip
		if (StringUtils.isBlank(fromIp)) {
			fromIp = request.getRemoteAddr();
		}
		// 取最前面的IP地址
		String[] fromIpArr = fromIp.split(",");
		if (fromIpArr.length > 0) {
			fromIp = fromIpArr[0];
		}
		if (StringUtils.isBlank(fromIp))
			fromIp = "unknown";
		return fromIp;
	}

	public String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC 地址") + 14,
								str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	/**
	 * 放入错误消息
	 * 
	 * @param modelMap
	 * @param validateMsg
	 * @return
	 */
	protected ModelMap putErrorMsg(ModelMap modelMap, String validateMsg) {
		Map<String, Object> vailMsg = new HashMap<String, Object>();
		vailMsg.put(this.R_KEY_MSG, validateMsg);
		modelMap.put(this.RESULT_KEY_ERROR, vailMsg);
		return modelMap;
	}

	
}
