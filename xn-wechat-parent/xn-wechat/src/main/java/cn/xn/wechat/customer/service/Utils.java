package cn.xn.wechat.customer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("all")
public class Utils {
	public static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public  static ConcurrentHashMap<String, Token> tokenMap = new ConcurrentHashMap<String, Token>();
	
    public static String getWeixinAccessToken() throws URISyntaxException, ClientProtocolException, IOException{
		Token acessToken = tokenMap.get(CustomerConstant.OPENID);
		if(acessToken != null && !acessToken.isExpired()){
			return acessToken.getAccessToken();
		}  	
    	
    	DefaultHttpClient httpclient = new DefaultHttpClient();
		enableSSLDefaultHttpClient(httpclient);// 使之忽略SSL验证
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("grant_type", "client_credential"));
		params.add(new BasicNameValuePair("appid", CustomerConstant.CLIENTID));
		params.add(new BasicNameValuePair("secret", CustomerConstant.CLIENTSECRET));

		String param = URLEncodedUtils.format(params, "UTF-8");
		URI uri = URIUtils.createURI("https", "api.weixin.qq.com", -1, "/cgi-bin/token", param, null);

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String jsonString = EntityUtils.toString(entity);
			JSONObject fromObject = com.alibaba.fastjson.JSONObject.parseObject(jsonString);
			String access_token = fromObject.getString("access_token");
			Long expires_in = fromObject.getLong("expires_in")*1000+System.currentTimeMillis();;
			
			Token token = new Token(access_token,expires_in);				
			tokenMap.putIfAbsent(CustomerConstant.OPENID, token);	
			return access_token;
		}
		httpclient.getConnectionManager().shutdown();
		return null;
    }
    
	public static String getEasemobAdminToken() {
		Token acessToken = tokenMap.get(CustomerConstant.EASEMOB_CLIENTID);
		if(acessToken != null && !acessToken.isExpired()){
			return acessToken.getAccessToken();
		}
		short port = 80;
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("grant_type", "client_credentials");
		jsonRequest.put("client_id", CustomerConstant.EASEMOB_CLIENTID);
		jsonRequest.put("client_secret", CustomerConstant.EASEMOB_CLIENTSECRET);

		String url = String.format("http://%s:%s/%s/%s/token", CustomerConstant.DOMAIN, String.valueOf(port), CustomerConstant.ORGNAME, CustomerConstant.APPNAME);
		logger.info("获取token url : " + url);
		String requestBody = jsonRequest.toJSONString();
		logger.info("获取token 参数 : " + jsonRequest.toJSONString());
		HttpURLConnection conn;
		try {
			conn = post(url, "application/json", requestBody);
			logger.info("HttpURLConnection ResponseCode ： " + conn.getResponseCode());
			if (conn.getResponseCode() == 200) {
				StringBuffer sb = new StringBuffer();
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					sb.append(inputLine);
				}
				in.close();
				JSONObject messages =JSONObject.parseObject(sb.toString());
				String easemobToken = messages.getString("access_token");
				Long expires_in = messages.getLong("expires_in")*1000+System.currentTimeMillis();			
				Token token = new Token(easemobToken,expires_in);				
				tokenMap.putIfAbsent(CustomerConstant.EASEMOB_CLIENTID, token);				
				return easemobToken;
			}
			logger.warn("{} login failed,may be password is not correct");
		} catch (IOException e) {
			logger.warn("check password failed,error message {}", e);
			logger.warn("check password failed,error message {}", e.getMessage());
		}
		return null;
	}

	protected static HttpURLConnection post(String url, String body) throws IOException {
		return post(url, "application/x-www-form-urlencoded;charset=UTF-8", body);
	}
	protected static HttpURLConnection post(String url, String contentType, String body) throws IOException {
		if (url == null || body == null) {
			throw new IllegalArgumentException("arguments cannot be null");
		}
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setFixedLengthStreamingMode(bytes.length);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", contentType);
		OutputStream out = conn.getOutputStream();
		out.write(bytes);
		out.close();
		return conn;
	}
	   
	public static DefaultHttpClient enableSSLDefaultHttpClient(DefaultHttpClient client) {
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			client.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return client;
	}
}
