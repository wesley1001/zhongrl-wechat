package cn.xn.wechat.customer.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSONObject;



/**
 *  微信回调之后写给IM 的消息
 * @author rod zhong 
 *
 */
@SuppressWarnings("all")
@Component
public class SendMessageIM {

	static Logger logger = LoggerFactory.getLogger(SendMessageIM.class);
	
	@Resource
	private ActivityService activityService;
	
	@Resource
	private WechatUtil wechatUtil;
	
	public  void sendTextMessageToEasemob(String content,String fromUserName,String mp){
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("target_type", "users");
		String[] targets = new String[] {CustomerConstant.TOUSER};
		jsonRequest.put("target", targets);
		JSONObject msg = new JSONObject();
		msg.put("type", "txt");
		msg.put("msg", content);
		jsonRequest.put("msg", msg);
		jsonRequest.put("from", CustomerConstant.FROMUSER);
		
		JSONObject ext = new JSONObject();		
		JSONObject weichat = new JSONObject();
		JSONObject visitor = new JSONObject();
		visitor.put("source","weixin");
		visitor.put("mp", mp);	
		visitor.put("openid", fromUserName);  //这个发送时已经放置在from中了,回复时将to和callback_user呼唤即可
		visitor.put("userNickname", getWeixinNickName(fromUserName));
		
		weichat.put("visitor", visitor);		
		ext.put("weichat", weichat);		
		jsonRequest.put("ext", ext);
		

		String requestBody = jsonRequest.toJSONString();

		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(CustomerConstant.SENDMESSAGEURL);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		System.out.println(activityService.getIMToken()+"----------------");
		httpPost.setHeader("Authorization", "Bearer "+ activityService.getIMToken());
		StringEntity params = new StringEntity(requestBody.toString(), "UTF-8");
		httpPost.setEntity(params);

		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpPost);
			logger.info("send message {}",requestBody);
			logger.info("send httpResponse {}",httpResponse.getStatusLine().getStatusCode());
		} catch (Exception e) {
			logger.warn("put message to rest warn {}",e.getMessage());
		}
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			logger.info("put message to rest,message{}",requestBody.toString());
		}
	}

	
	public void sendImageMessageToEasemob(String fromUserName,String toUserName,String picUrl){
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("target_type", "users");
		String[] targets = new String[] {CustomerConstant.TOUSER };
		jsonRequest.put("target", targets);
		JSONObject msg = new JSONObject();
		msg.put("type", "img");
		msg.put("url", picUrl);
		msg.put("filename", picUrl);
		msg.put("secret", CustomerConstant.SECRET);
		jsonRequest.put("msg", msg);
		jsonRequest.put("from", CustomerConstant.FROMUSER);
		
		JSONObject ext = new JSONObject();		
		JSONObject weichat = new JSONObject();
		JSONObject visitor = new JSONObject();		
		visitor.put("source","weixin");
		visitor.put("mp", toUserName);
		visitor.put("openid", fromUserName);
		visitor.put("userNickname", getWeixinNickName(fromUserName));
		weichat.put("visitor", visitor);		
		ext.put("weichat", weichat);		
		jsonRequest.put("ext", ext);
		
		String requestBody = jsonRequest.toJSONString();

		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(CustomerConstant.SENDMESSAGEURL);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", "Bearer "+ activityService.getIMToken());

		StringEntity params = new StringEntity(requestBody, "UTF-8");
		httpPost.setEntity(params);

		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpPost);
			logger.info("send message {}",requestBody);
			logger.info("send httpResponse {}",httpResponse.getStatusLine().getStatusCode());
		} catch (IOException e) {
			logger.warn("put message to rest warn {}",e.getMessage());
		}
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			logger.info("put message to rest,message{}",requestBody);
		}
	}
	
	public void sendVoiceMessageToEasemob(String filePath,String fromUserName,String toUserName,String voiceMediaId){
		JSONObject jSONObject = uploadChatFile(filePath);
		JSONObject json1 = jSONObject.getJSONArray("entities").getJSONObject(0);
		String uuid = json1.getString("uuid");
		voiceMediaId = CustomerConstant.CHATFILEURL+uuid;
		
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("target_type", "users");
		String[] targets = new String[] { CustomerConstant.TOUSER };
		jsonRequest.put("target", targets);
		JSONObject msg = new JSONObject();
		msg.put("type", "audio");
		msg.put("url", voiceMediaId);
		msg.put("filename", filePath);
		msg.put("secret", CustomerConstant.SECRET);
		jsonRequest.put("msg", msg);
		jsonRequest.put("from", CustomerConstant.FROMUSER);
		
		JSONObject ext = new JSONObject();		
		JSONObject weichat = new JSONObject();
		JSONObject visitor = new JSONObject();	
		visitor.put("source","weixin");
		visitor.put("mp", toUserName);	
		visitor.put("openid", fromUserName);
		visitor.put("userNickname", getWeixinNickName(fromUserName));		
		weichat.put("visitor", visitor);		
		ext.put("weichat", weichat);		
		jsonRequest.put("ext", ext);
		String requestBody = jsonRequest.toString();
		HttpClient client = new DefaultHttpClient();// 创建一个HttpClient
		HttpPost httpPost = new HttpPost(CustomerConstant.SENDMESSAGEURL);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", "Bearer "+ activityService.getIMToken());

		StringEntity params = new StringEntity(requestBody.toString(), "UTF-8");
		httpPost.setEntity(params);

		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpPost);
			logger.info("send message {}",requestBody);
			logger.info("send httpResponse {}",httpResponse.getStatusLine().getStatusCode());
		} catch (IOException e) {
			logger.warn("put message to rest warn {}",e.getMessage());
		}
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			logger.info("put message to rest,message{}",requestBody.toString());
		}
	}

	
	public JSONObject uploadChatFile(String filePath) {
		RestTemplate rest = new RestTemplate();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(new File(filePath));
        body.add("file", resource);
		
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.add("Authorization", "Bearer " + activityService.getIMToken());
        httpHeader.add("restrict-access", "false");//避免下载麻烦
        httpHeader.add("Content-Type", "application/json; charset=utf-8");          
        httpHeader.setContentType(MediaType.MULTIPART_FORM_DATA);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeader.setAccept(mediaTypes);
        
		Map<String, String> urlVariables= new HashMap<String,String>();
		urlVariables.put("easeAppOrgName", "demotest");
		urlVariables.put("easeAppName", "testuser");
		urlVariables.put("domain","a1.easemob.com");
		urlVariables.put("port",String.valueOf(80));

		org.springframework.http.HttpEntity<MultiValueMap<String, Object>> entity = new org.springframework.http.HttpEntity<MultiValueMap<String,Object>>(body,httpHeader);

//		String url ="http://{domain}:{port}/{easeAppOrgName}/{easeAppName}/chatfiles";
		ResponseEntity<String> response = rest.exchange(CustomerConstant.CHATFILEURL,HttpMethod.POST, entity, String.class, urlVariables );

		String result = response.getBody();
		JSONObject messages= JSONObject.parseObject(result);

		return  messages;
	}	

	/**
	   * 从微信上下载语音文件
	   * @param accessToken 接口访问凭证
	   * @param media_id 媒体文件id
	   * @param savePath 文件在服务器上的存储路径
	   * */
	  public String downloadMedia(String accessToken, String mediaId, String savePath) {
	    String filePath = null;
	    // 拼接请求地址
	    String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	    requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
	    try {
	      URL url = new URL(requestUrl);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoInput(true);
	      conn.setRequestMethod("GET");

	      if (!savePath.endsWith("/")) {
	        savePath += "/";
	      }
	      filePath = savePath + mediaId + ".amr";

	      BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	      FileOutputStream fos = new FileOutputStream(new File(filePath));
	      byte[] buf = new byte[8096];
	      int size = 0;
	      while ((size = bis.read(buf)) != -1)
	        fos.write(buf, 0, size);
	      fos.close();
	      bis.close();

	      conn.disconnect();
	      String info = String.format("下载媒体文件成功，filePath=" + filePath);
	      System.out.println(info);
	    } catch (Exception e) {
	    	e.printStackTrace();
	      filePath = null;
	      String warn = String.format("下载媒体文件失败：%s", e);
	      System.out.println(warn);
	    }
	    return filePath;
	  }

	//!!!!!!这个只是测试时可以任性的每次去获取，生产环境中请保存token并按周期更新  ！！！！！
//	public String getAccessToken() throws URISyntaxException, IOException {
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		enableSSLDefaultHttpClient(httpclient);// 使之忽略SSL验证
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("grant_type", "client_credential"));
//		params.add(new BasicNameValuePair("appid", CustomerConstant.CLIENTID));
//		params.add(new BasicNameValuePair("secret", CustomerConstant.CLIENTSECRET));
//
//		String param = URLEncodedUtils.format(params, "UTF-8");
//		URI uri = URIUtils.createURI("https", "api.weixin.qq.com", -1, "/cgi-bin/token", param, null);
//
//		HttpGet httpget = new HttpGet(uri);
//		HttpResponse response = httpclient.execute(httpget);
//		HttpEntity entity = response.getEntity();
//		if (entity != null) {
//			String jsonString = EntityUtils.toString(entity);
//			JSONObject fromObject = JSONObject.parseObject(jsonString);
//			Object access_token = fromObject.get("access_token");
//			if (access_token != null) {
//				return access_token.toString();
//			}
//		}
//		httpclient.getConnectionManager().shutdown();
//		return null;
//	}

	public String getWeixinNickName(String openid) {
		try {
			String access_token = wechatUtil.getToken().getString("access_token");//getAccessToken();
			JSONObject json =getUserInfo(access_token, openid);
			return json.getString("nickname");
		} catch (Exception e) {
			logger.warn("can not getWeixinNickName,exception = {}",e.getMessage());
		}
		return "微信网友：昵称未知";
	}

	/**
	 * get weixin User Info
	 */
	public JSONObject getUserInfo(String access_token, String openid) throws URISyntaxException, ClientProtocolException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("access_token", access_token));
		params.add(new BasicNameValuePair("openid", openid));
		params.add(new BasicNameValuePair("lang", "zh_CN"));
		String param = URLEncodedUtils.format(params, "UTF-8");
		URI uri = URIUtils.createURI("https", "api.weixin.qq.com", -1, "/cgi-bin/user/info", param, null);
		HttpGet get = new HttpGet(uri);
		return processHttpGetMethod(get);
	}
	
	/**
	 * process HttpGet Method
	 * 
	 * @param get
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private JSONObject processHttpGetMethod(HttpGet get) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		enableSSLDefaultHttpClient(client);
		HttpResponse httpResponse = client.execute(get);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			String jsonString = EntityUtils.toString(entity,"utf-8");
			JSONObject messages = JSONObject.parseObject(jsonString);
			return messages;
		}
		return null;
	}
	
	public DefaultHttpClient enableSSLDefaultHttpClient(DefaultHttpClient client) {
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

	
	public boolean checkSignature(String token, String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null && tmpStr.equals(signature.toUpperCase());
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	/**
	 * 输出返回内容
	 * 
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	private void output(HttpServletResponse response, String msg) throws IOException {
		if (msg != null) {
			response.getOutputStream().write(msg.getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	public static void main(String[] args) {
		//sendTextMessageToEasemob("你好，小牛存钱罐88888","openid001","gh_001");
		System.out.println("--------------------" + Utils.getEasemobAdminToken());
		
		//'{"name":"ziroomkefu","tos":["xnqgz-send-msg"],"msgTypes":["chat"],"hxSecret":"123456","secret":"654321","targetUrl":"http://wxoauth.xiaoniuapp.com/xn-wechat/customer/imCallBackMessage.do","status":1}
		//' -H "Authorization:Bearer 生成的环信的token"

	}
}
