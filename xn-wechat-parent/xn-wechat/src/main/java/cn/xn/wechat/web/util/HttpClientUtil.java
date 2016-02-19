package cn.xn.wechat.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.StringHolder;

import com.alibaba.fastjson.JSON;


/**
 * HTTP 请求工具
 * @author 
 *
 */
public class HttpClientUtil {

	private static final Log logger = LogFactory.getLog(HttpClientUtil.class);
	
	private static final HttpClient httpClient = new HttpClient();
	

	
	public static String get(String url,Map<String,Object> maps){
		HttpURLConnection conn = null;
		String result ="";
		try {
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
			GetMethod get = new GetMethod(url+mapJsonGet(maps));
			get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 50000);
			get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,	new DefaultHttpMethodRetryHandler());
			httpClient.executeMethod(get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while((str = reader.readLine())!=null){
				stringBuffer.append(str);
			}
			result = stringBuffer.toString();
			logger.debug("接收到回 " + result);
		    return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GET远程请求连接 " + url + " 异常" + e);
			
		} finally {
			if (conn != null){
				conn.disconnect();
			}
		}
		
		return null;
	}
	
	public static String post(String url,Map<String,Object> maps){
		HttpURLConnection conn = null;
		String result ="";
		try {
		     
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(180000);
			PostMethod post =  mapJsonPost(url,maps);
			HttpMethodParams param = post.getParams();  
		    param.setContentCharset("UTF-8"); 
		    logger.debug("请求 " + maps);
			httpClient.executeMethod(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while((str = reader.readLine())!=null){
				stringBuffer.append(str);
			}
			result = stringBuffer.toString();
			logger.debug("接收到回 " + result);
		    return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("POST远程请求连接 " + url + " 异常" + e);
		} finally {
			if (conn != null){
				conn.disconnect();
			}
		}
		
		return null;
	}
	
	 public static String mapJsonGet(Map<String,Object> maps) { 
	        StringBuilder json = new StringBuilder(); 
	        if(maps!=null && !maps.isEmpty()){
	        	json.append("?"); 
				for (Map.Entry<String,Object> map : maps.entrySet()) {
					json.append(map.getKey()).append("=").append(map.getValue()).append("&");
				}
			}
	        return json.toString(); 
	 } 
	 
	 public static PostMethod mapJsonPost(String url , Map<String,Object> maps) { 
		 	PostMethod post = new PostMethod(url);
	        if(maps!=null && !maps.isEmpty()){
				for (Map.Entry<String,Object> map : maps.entrySet()) {
					post.addParameter(map.getKey(), (String) map.getValue());
				}
			}
	        return post; 
	 } 
	 
	
    /**
     * 上传图片
     * @param url
     * @param filename
     * @param filepath
     */
    public static String submitPostImage(String httpUrl,String filename, String filepath){  
    	
    	String result = null ;  
    	logger.info("请求路径："+httpUrl);
        logger.info("请求参数：filename = "+filename+" , filepath = "+filepath);
    	org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();  
        try {  
        	
            HttpPost httppost = new HttpPost(httpUrl);  
            FileBody bin = new FileBody(new File(filepath));  
            StringBody comment = new StringBody(filename);  
            MultipartEntity reqEntity = new MultipartEntity();  
            reqEntity.addPart("image", bin);//file1为请求后台的File upload;属性      
            reqEntity.addPart("image", comment);//filename1为请求后台的普通参数;属性    
            httppost.setEntity(reqEntity);  
            HttpResponse response = httpclient.execute(httppost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            
            if(statusCode == HttpStatus.SC_OK)
            { 
            	logger.debug("上传图片服务器正常响应.....");
                HttpEntity resEntity = response.getEntity();  
                result = EntityUtils.toString(resEntity);//返回的参数   EntityUtils 自带的返回参数的工具类
                logger.debug("全媒体响应返回的数据:" + result);
                resEntity.getContent();
                EntityUtils.consume(resEntity);  
               
            }else
            {
            	logger.error("上传图片服务器响应失败.....");
            }  
           
         } catch (Exception e) {  
        	 logger.error("上传图片异常：" + e);
             e.printStackTrace();  
         }finally {  
                try {   
                    httpclient.getConnectionManager().shutdown();   
                } catch (Exception ignore) {  
                	logger.error("上传图片异常：" + ignore);
                }  
            }
        return result;
     }  
    
    public static String get(String url) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(),"utf-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			return stringBuffer.toString();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return null;
	}
	 
	 public static String httpsGet(String url) {
			// 构造HttpClient的实例
			HttpClient httpClient = new HttpClient();
			Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);   
			Protocol.registerProtocol("https", myhttps);
			// 创建GET方法的实例
			GetMethod getMethod = new GetMethod(url);
			// 使用系统提供的默认的恢复策略
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			try {
				// 执行getMethod
				int statusCode = httpClient.executeMethod(getMethod);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + getMethod.getStatusLine());
				}
				// 读取内容
				byte[] responseBody = getMethod.getResponseBody();
				// 处理内容
				String respStr = new String(responseBody, "utf-8");
				return respStr;
			} catch (HttpException e) {
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				System.out.println("Please check your provided http address!");
				e.printStackTrace();
			} catch (IOException e) {
				// 发生网络异常
				e.printStackTrace();
			} finally {
				// 释放连接
				getMethod.releaseConnection();
			}
			return null;
		}	 

	 public static String post(String url) {
			// 构造HttpClient的实例
			HttpClient httpClient = new HttpClient();
			// 创建GET方法的实例
			GetMethod getMethod = new GetMethod(url);
			// 使用系统提供的默认的恢复策略
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			try {
				// 执行getMethod
				int statusCode = httpClient.executeMethod(getMethod);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + getMethod.getStatusLine());
				}
				// 读取内容
				byte[] responseBody = getMethod.getResponseBody();
				// 处理内容
				String respStr = new String(responseBody, "utf-8");
				return respStr;
			} catch (HttpException e) {
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				System.out.println("Please check your provided http address!");
				e.printStackTrace();
			} catch (IOException e) {
				// 发生网络异常
				e.printStackTrace();
			} finally {
				// 释放连接
				getMethod.releaseConnection();
			}
			return null;
		}
	 
	 /**
	   * 获取一个可关闭的HTTP连接客户端
	   * 
	   * @return
	   */
	  public static CloseableHttpClient getHttpClient() {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    return httpclient;
	  }

	  /**
	   * 关闭HTTP连接客户端
	   * 
	   * @param httpclient
	   */
	  public static void closeHttpClient(CloseableHttpClient httpclient) {
	    if (httpclient == null) {
	      return;
	    }
	    try {
	      httpclient.close();
	    } catch (IOException e) {
	      logger.error(e.getMessage(), e);
	    } finally {
	      httpclient = null;
	    }
	  }

	  /**
	   * 发送HTTP请求到指定的URL
	   * 
	   * @param url
	   * @param request
	   * @return
	   */
	  public static int sendRequest(String url, HttpUriRequest request, StringHolder strh) {
	    if (StringUtils.isBlank(url)) {
	      strh.value = " req url is empty ";
	      return -1;
	    } else if (request == null) {
	      strh.value = " HttpUriRequest object is null ";
	      return -1;
	    }
	    CloseableHttpClient httpclient = getHttpClient();
	    try {
	      request.setHeader("Accept-Encoding", "gzip, deflate");
	      request.setHeader("Accept-Language", "zh-CN");
	      request.setHeader("Accept", "application/json, application/xml, text/html, text/*");
	      request.setHeader("USER_AGENT",
	          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
	      HttpResponse response = httpclient.execute(request);
	      int stateCode = response.getStatusLine().getStatusCode();
	      HttpEntity entity = response.getEntity();
	      if (entity == null) {
	        strh.value = "";
	        return stateCode;
	      }
	      String str = EntityUtils.toString(entity);
	      return stateCode;
	    } catch (Exception e) {
	      strh.value = e.getMessage();
	      logger.error(e.getMessage(), e);
	    } finally {
	      closeHttpClient(httpclient);
	    }
	    return -1;
	  }
	  
	  public static String get(String url, StringHolder strh) {
		    HttpGet httpgets = new HttpGet(url);
		    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(5000).build();// 设置请求和传输超时时间
		    httpgets.setConfig(requestConfig);
		    return sendRequestGet(url, httpgets, strh);
	  }
	  
	  public static String sendRequestGet(String url, HttpUriRequest request, StringHolder strh) {
		  	String result = "";
		    if (StringUtils.isBlank(url)) {
		      strh.value = " req url is empty ";
		      return "-1";
		    } else if (request == null) {
		      strh.value = " HttpUriRequest object is null ";
		      return "-1";
		    }
		    CloseableHttpClient httpclient = getHttpClient();
		    try {
		      request.setHeader("Accept-Encoding", "gzip, deflate");
		      request.setHeader("Accept-Language", "zh-CN");
		      request.setHeader("Accept", "application/json, application/xml, text/html, text/*");
		      request.setHeader("USER_AGENT",
		          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
		      HttpResponse response = httpclient.execute(request);
		      int stateCode = response.getStatusLine().getStatusCode();
		      HttpEntity entity = response.getEntity();
		      if (entity == null) {
		        return "-1";
		      }
		      result =  new String(EntityUtils.toString(entity).getBytes(),"utf-8");
		    } catch (Exception e) {
		      strh.value = e.getMessage();
		      logger.error(e.getMessage(), e);
		    } finally {
		      closeHttpClient(httpclient);
		    }
		    return result;
		  }

	  public static int doGet(String url, StringHolder strh) {
	    HttpGet httpgets = new HttpGet(url);
	    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(5000).build();// 设置请求和传输超时时间
	    httpgets.setConfig(requestConfig);
	    return sendRequest(url, httpgets, strh);
	  }

	  public static int doDelete(String url, StringHolder strh) {
	    HttpDelete httpgets = new HttpDelete(url);
	    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(5000).build();// 设置请求和传输超时时间
	    httpgets.setConfig(requestConfig);
	    return sendRequest(url, httpgets, strh);
	  }

	  public static int doPost(String url, Map<String, String> map, StringHolder strh) {
	    try {
	      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	      if (map != null) {
	        Set<String> keySet = map.keySet();
	        for (String key : keySet) {
	          nvps.add(new BasicNameValuePair(key, map.get(key)));
	        }
	      }
	      HttpPost post = new HttpPost(url);
	      try {
	        post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	      } catch (UnsupportedEncodingException e) {
	        logger.error(e.getMessage(), e);
	      }
	      return sendRequest(url, post, strh);
	    } catch (Exception e) {
	      strh.value = e.getMessage();
	      logger.error(e.getMessage(), e);
	    }
	    return -1;
	  }

	  /**
	   * 发送HTTP PUT请求
	   * 
	   * @param url
	   * @param map
	   *          参数集合
	   * @return
	   */
	  public static int doPut(String url, Map<String, String> map, StringHolder strh) {
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    if (map != null) {
	      Set<String> keySet = map.keySet();
	      for (String key : keySet) {
	        nvps.add(new BasicNameValuePair(key, map.get(key)));
	      }
	    }
	    return doPut(url, nvps, strh);
	  }

	  /**
	   * 发送HTTP PUT请求
	   * 
	   * @param url
	   * @param params
	   *          (将对象转换成JSON数据)
	   * @return
	   * @throws Exception
	   */
	  public static int doPut(String url, Object params, StringHolder strh) {
	    String paramJsons = "";
	    if (params != null) {
	      paramJsons = JSON.toJSONString(params);
	    }
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("data", paramJsons));
	    return doPut(url, nvps, strh);
	  }

	  /**
	   * 发送HTTP PUT请求
	   * 
	   * @param url
	   * @param nvps
	   * @return
	   */
	  private static int doPut(String url, List<NameValuePair> nvps, StringHolder strh) {
	    HttpPut put = new HttpPut(url);
	    try {
	      put.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
	    } catch (UnsupportedEncodingException e) {
	      logger.error(e.getMessage(), e);
	    }
	    return sendRequest(url, put, strh);
	  }

	  /**
	   * 发送HTTP DELETE请求
	   * 
	   * @param url
	   * @return
	   */
	  public static int doDeleteNotSet(String url, StringHolder strh) {
	    HttpDelete delete = new HttpDelete(url);
	    return sendRequest(url, delete, strh);
	  }

	  /**
	   * 发送短信
	   * @param url
	   * @param mobile
	   * @param content
	   * @return
	   */
	  public static String smsPost(String url, String mobile, String content) {
	    if (StringUtils.isBlank(url) || StringUtils.isBlank(mobile) || StringUtils.isBlank(content)) {
	      return null;
	    }
	    Map<String, String> param = new HashMap<>();
	    param.put("mobile", mobile);
	    param.put("content", content);
	    StringHolder strh = new StringHolder("");
	    int code = doPost(url, param, strh);
	    logger.info(code + "\t" + url + ":" + mobile + ":" + content);
	    return strh.value;
	  }
	 
	 public static void main(String[] args) {
//		 Map<String,Object> map = new HashMap<String,Object>();
//		 map.put("mobile", "13686441896");
//		 map.put("context", "测试小牛普惠短信接口");
//		 String url = "http://183.56.166.83:8080/xnphws/sendSms";
//		 post(url,map);
		//String url = "http://localhost:8080/test/TestDemo";
		/*Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", "abc");
		map.put("zhangsan", "lisi");
		String json = post(url,null);
		JSONArray jsonarray = JSONArray.parseArray(json);
		for (int i = 0; i < jsonarray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonarray.get(i);
			System.err.println(jsonObject.get("name") +","+jsonObject.get("age"));
		}*/
//		 String url = "http://10.4.62.41:18021/fileupload/fileupload?appid=tcl_dmt";
//		 submitPostImage(url,"test.jpg","C:\\Users\\zhongrulei\\Desktop\\test.jpg");
		 
		 String url_long = "https%3A%2F%2Fmapp.xiaoniuapp.com%2Fpages%2Fuser%2Fnw_register.html%3Freferrer%3D15899760934%26source%3D3180958896";
		 String url = "https://api.weibo.com/2/short_url/shorten.json";
		 String source = "3180958896";
		 System.out.println(get(url+"?url_long="+url_long+"&source="+source));
	}
}
