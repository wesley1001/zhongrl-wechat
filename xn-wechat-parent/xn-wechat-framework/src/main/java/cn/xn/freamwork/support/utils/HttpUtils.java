package cn.xn.freamwork.support.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtils {
	private static String charset = "UTF-8";
	private static int timeout = 20 * 1000;
	private static HttpClient client = new HttpClient();
	private static int tryTime = 1;

	public static String doPost(String url, FilePart filepart[], StringPart... parts) throws IOException {
		if (filepart == null) {
			return doPost(url, parts);
		}
		PostMethod method = new PostMethod(url);
		try {
			int size = filepart.length;
			if (parts != null) {
				size = size + parts.length;
			}
			Part[] ps = new Part[size];
			int index = 0;
			for (int i = 0; i < filepart.length; i++) {
				ps[index] = filepart[i];
				index++;
			}
			if (parts != null) {
				for (StringPart sp : parts) {
					sp.setCharSet(charset);
					ps[index] = sp;
					index++;
				}
			}
			MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
			method.setRequestEntity(entity);
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(tryTime, false));
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				return method.getResponseBodyAsString();
			} else {
				throw new HTTPException(status);
			}
		} finally {
			method.releaseConnection();
		}
	}

	public static String doPost(String url, StringPart... parts) throws IOException {
		if (parts == null) {
			return doGet(url, null);
		}
		PostMethod method = new PostMethod(url);
		try {
			for (StringPart stringPart : parts) {
				stringPart.setCharSet(charset);
			}
			Part[] ps = parts;
			MultipartRequestEntity entity = new MultipartRequestEntity(ps, method.getParams());
			method.setRequestEntity(entity);
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(tryTime, false));
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				return method.getResponseBodyAsString();
			} else {
				throw new HTTPException(status);
			}
		} finally {
			method.releaseConnection();
		}
	}

	public static String doGet(String url, Map<String, String> parts) throws IOException {
		StringBuilder surl = new StringBuilder();
		surl.append(url);
		if (parts != null) {
			surl.append("?");
			Iterator<Map.Entry<String, String>> iter = parts.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				surl.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), charset));
				if (iter.hasNext()) {
					surl.append("&");
				}
			}
		}
		GetMethod method = new GetMethod(surl.toString());
		try {
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(tryTime, false));
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				return method.getResponseBodyAsString();
			} else {
				throw new HTTPException(status);
			}
		} finally {
			method.releaseConnection();
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		//StringPart a = new StringPart("a", "ad中文 加空格");
		//System.out.println(URLEncoder.encode("ad中文 加空格", "gbk"));
		HttpUtils u = new HttpUtils();
		try {
			System.out.println(u.doGet("http://my.wt.com/webservice/sso.ashx?a=login&user=903050&fromurl=http%3A%2F%2Fwww.ewt.cc%2F&pwd=c8837b23ff8aaa8a2dde915473ce0991&&md5=true", null));
			
			u.doPost("",null);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
