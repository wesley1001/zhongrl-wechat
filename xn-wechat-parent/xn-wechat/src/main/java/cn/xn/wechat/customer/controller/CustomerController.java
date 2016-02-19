package cn.xn.wechat.customer.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import cn.xn.wechat.customer.service.CustomerConstant;
import cn.xn.wechat.web.base.BaseController;
import cn.xn.wechat.web.service.ActivityService;
import cn.xn.wechat.web.util.StringUtil;
import cn.xn.wechat.web.util.WechatUtil;

import com.alibaba.fastjson.JSONObject;

/**
 *  接入环信客服
 * @author rod zhong
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/customer/")
public class CustomerController  extends BaseController{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ActivityService activityService; 
	
	@Resource
	private WechatUtil wechatUtil;

	/**
	 *  IM 环信回调接口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="imCallBackMessage",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String imCallBackMessage(HttpServletRequest request,HttpServletResponse response){
		//response.addHeader("Access-Control-Allow-Origin", "*");
		return callBack(request,response,new JSONObject());
	}
	
	private String callBack(HttpServletRequest request,HttpServletResponse response,JSONObject json){
		//可以先在这里验证签名
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		String callId = null;
		try {
			in = request.getReader();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			String xml = sb.toString();
			logger.info("----环信回调的客服消息内容 = " + xml);//这个xml就是环信回调回来的消息
			if(StringUtils.isEmpty(xml)){
				return 	"环信回调的客服消息内容 is null ";
			}
			JSONObject message = JSONObject.parseObject(xml);
			json.put("callId", message.getString("callId"));
			callId = message.getString("callId");
			String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + wechatUtil.getToken().getString("access_token");//getAccessToken();
			logger.info("给微信发消息地址:" + url);
			DefaultHttpClient client = new DefaultHttpClient();
			enableSSLDefaultHttpClient(client);
			HttpPost httpPost = new HttpPost(url);
			JSONObject messages = new JSONObject();
			String type = ((JSONObject)(message.getJSONObject("payload").getJSONArray("bodies").get(0))).getString("type");
			switch (type) {
			case "txt":
				String content = ((JSONObject)(message.getJSONObject("payload").getJSONArray("bodies").get(0))).getString("msg");
				String toUser = message.getJSONObject("payload").getJSONObject("ext").getJSONObject("weichat").getJSONObject("visitor").getString("openid");
				JSONObject msg = new JSONObject();
				msg.put("msg", content);
				messages = processWechatTextMessage(client, httpPost, toUser, msg);
				break;
			case "img":
				String toUser2 = message.getJSONObject("payload").getJSONObject("ext").getJSONObject("weichat").getJSONObject("visitor").getString("openid");
				String urlImage = ((JSONObject)(message.getJSONObject("payload").getJSONArray("bodies").get(0))).getString("url");
				JSONObject msg2 = new JSONObject();		
				msg2.put("media_id", getMiaderId(urlImage));			
				messages = processWechatImageMessage(client, httpPost, toUser2, msg2);
				break;
			default:
				logger.warn("unknown channelType = " + type);
				break;
			}
			logger.info("messages = " + messages);
			json.put("accept", "true");
		} catch (Exception e) {
			logger.info("发送消息失败 : " ,e);
			json.put("callId", callId);
			json.put("accept","false");
			json.put("reason","发送消息失败 ");
		}
		String security = json.getString("callId") + CustomerConstant.SECRET + json.getString("accept");
		json.put("security", StringUtil.EncoderByMd5(security));
		return json.toJSONString();
	}

	public  String getMiaderId(String urlImage) throws Exception{
		//String url = "https://a1.easemob.com/easemob-demo/xuzhengli/chatfiles/5bd56cb0-3bf8-11e5-b668-431280c88f48";
        String url = urlImage;
		byte[] buffer = downloadChatFile2("application/octet-stream","secretxxxxxx", url, false);

        String mediaId = java.util.UUID.randomUUID().toString();
        String filePath = "/data/apps/log/kefu/002.jpg";
        saveFile(filePath, buffer);
		return uploadChatFileToWechat(filePath);
	}
	
	public  String uploadChatFileToWechat(String filePath) throws Exception {
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		// 因为该类会根据渠道数创建实例，在不考虑集群部署的情况下，可以放心使用_IsLogin判断是否是第一次进入，第一次进入获取token，以后根据超时时间被调用时刷新		
		url = url.replace("ACCESS_TOKEN", wechatUtil.getToken().getString("access_token")).replace("TYPE", "image");// 此处的token应该考虑一个线程定时刷新，或者说根据返回接口看一下是否需要在失败时启动刷新操作

		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		/**
		 * 第一部分
		 */
		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		/**
		 * 设置关键值
		 */
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			logger.warn("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new RuntimeException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
				con.disconnect();
			}
		}
		JSONObject jsonObj = JSONObject.parseObject(result);
		return jsonObj.getString("media_id");
	}
	
	
    public void saveFile(String filePath, byte[] buffer) {
        if (buffer == null || buffer.length == 0) {
            logger.warn("trying to save file {} but data is null", filePath);
            return;
        }
        // 文件输出流
        try {
            FileUtils.writeByteArrayToFile(new File(filePath), buffer);
        } catch (IOException e) {
            logger.warn("Failed to save file " + filePath, e);
        }
    }
	
	private byte[] downloadChatFile2( String contentType, String shareSecret, String chatfileUrl, boolean thumbnail) {

		byte[] returnVal = null;
		try {
			chatfileUrl = chatfileUrl.replace("https", "http");// 将https,替换成"http"
			logger.debug("downloading chat file from url=" + chatfileUrl);
			returnVal = downloadChatFile(contentType, shareSecret, chatfileUrl, thumbnail);
		} catch (Exception e) {
			logger.warn("Failed to download chat file from " + chatfileUrl, e);
		}

		return returnVal;
	}
	
	
	public  byte[] downloadChatFile(String contentType, String shareSecret, String chatfileUrl, boolean thumbnail) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		try {
			SSLContext ctx = SSLContext.getInstance("TLS");

			ctx.init(null, new TrustManager[] { xtm }, null);

			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

		} catch (Exception e) {
			throw new RuntimeException();
		}

		requestFactory.setHttpClient(httpClient);
		//requestFactory.setHttpClient(httpClient);
        RestTemplate rest = new RestTemplate(requestFactory);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", contentType);
        headers.add("Authorization", "Bearer " + activityService.getIMToken());
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (thumbnail) {
            //缩略图
            headers.add("thumbnail", "true");
        }

        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("easeAppOrgName", "demotest");
        urlVariables.put("easeAppName", "testuser");
        org.springframework.http.HttpEntity<MultiValueMap<String, Object>> entity = new org.springframework.http.HttpEntity<MultiValueMap<String, Object>>(headers);

        ResponseEntity<byte[]> response = rest.exchange(
                chatfileUrl,
                HttpMethod.GET,
                entity,
                byte[].class, urlVariables);

        return response.getBody();
    }
	
	public JSONObject processWechatTextMessage(DefaultHttpClient client, HttpPost httpPost, String toUser, JSONObject msg) throws UnsupportedEncodingException {
		JSONObject json = new JSONObject();
		json.put("touser", toUser);
		json.put("msgtype", "text");
		JSONObject jsonContent = new JSONObject();
		jsonContent.put("content", (String) msg.get("msg"));
		json.put("text", jsonContent);
		StringEntity params = new StringEntity(json.toString(), "UTF-8");
		httpPost.setEntity(params);

		HttpResponse httpResponse = null;
		String jsonString = "";
		try {
			httpResponse = client.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			jsonString = EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			logger.warn("send text message to wechat vistor {"+toUser+"} occurs warn，accept jsonString = {"+jsonString+"},and warn message = {}",  e);
			throw new RuntimeException("warn" + e.getMessage());
		}
		JSONObject messages = JSONObject.parseObject(jsonString);
		int errcode = messages.getInteger("errcode");
		if(errcode != 0 ){
			logger.warn("send text message to wechat vistor {} occurs warn，accept jsonString = {}",toUser,jsonString);
			throw new RuntimeException("failed to send message");
		}
		return messages;
	}
	
	
	/**
	 * 向微信公众号粉丝发送图片消息
	 */
	public JSONObject processWechatImageMessage(DefaultHttpClient client, HttpPost httpPost, String toUser, JSONObject msg) throws UnsupportedEncodingException {
		JSONObject json = new JSONObject();
		json.put("touser", toUser);
		json.put("msgtype", "image");
		JSONObject jsonImage = new JSONObject();
		jsonImage.put("media_id",msg.getString("media_id"));
		json.put("image", jsonImage);
		StringEntity params = new StringEntity(json.toString(), "UTF-8");
		httpPost.setEntity(params);

		HttpResponse httpResponse = null;
		String jsonString = "";
		try {
			httpResponse = client.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			jsonString = EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			logger.warn("send image message to wechat vistor {"+toUser+"} occurs warn，accept jsonString = {"+jsonString+"},and warn message = {}",e);
			throw new RuntimeException("warn" + e.getMessage());
		}
		JSONObject messages = JSONObject.parseObject(jsonString);
		int errcode = messages.getInteger("errcode");
		if(errcode != 0 ){
			logger.warn("send text message to wechat vistor {} occurs warn，accept jsonString = {}",toUser,jsonString);
			throw new RuntimeException("failed to send message");
		}
		return messages;
	}
	
	/**
	 * 	//!!!!!!这个只是测试时可以任性的每次去获取，生产环境中请保存token并按周期更新  ！！！！！
	 * 获取微信公众号的access_token,正式环境时请考虑token有效期，不能每次都重新生成
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
//	public String getAccessToken() throws URISyntaxException, IOException {
//		String accessToken = "";
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		enableSSLDefaultHttpClient(httpclient);// 使之忽略SSL验证
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("grant_type", "client_credential"));
//		params.add(new BasicNameValuePair("appid", CustomerConstant.CLIENTID));// IMGeek公众号
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
//        return null;
//	}

	
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
	
}
