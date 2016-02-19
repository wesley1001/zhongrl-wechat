package cn.xn.freamwork.support.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

public class HttpUtil
{
	public static String encoding;

	private static final HttpConnectionManager connectionManager;

	private static final HttpClient client;

	static
	{
		final HttpConnectionManagerParams params = loadHttpConfFromFile();

		connectionManager = new MultiThreadedHttpConnectionManager();

		connectionManager.setParams(params);

		client = new HttpClient(connectionManager);
	}

	private static HttpConnectionManagerParams loadHttpConfFromFile()
	{
		final PropertiesConfiguration p = new PropertiesConfiguration();

		try
		{
			p.load("httputil.properties");
		}
		catch (final ConfigurationException e)
		{
		}

		encoding = p.getString("http.content.encoding");

		final HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(Integer.parseInt(p.getString("http.connection.timeout")));
		params.setSoTimeout(Integer.parseInt(p.getString("http.so.timeout")));
		params.setStaleCheckingEnabled(Boolean.parseBoolean(p.getString("http.stale.check.enabled")));
		params.setTcpNoDelay(Boolean.parseBoolean(p.getString("http.tcp.no.delay")));
		params.setDefaultMaxConnectionsPerHost(Integer.parseInt(p.getString("http.default.max.connections.per.host")));
		params.setMaxTotalConnections(Integer.parseInt(p.getString("http.max.total.connections")));
		params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		return params;
	}

	public static String post(final String url, final String encoding, final String content)
	{
		try
		{
			final byte[] resp = post(url, content.getBytes(encoding));
			if (null == resp)
			{
				return null;
			}
			return new String(resp, encoding);
		}
		catch (final UnsupportedEncodingException e)
		{
			return null;
		}
	}

	public static String post(final String url, final String content)
	{
		return post(url, encoding, content);
	}

	public static byte[] post(final String url, final byte[] content)
	{
		try
		{
			final byte[] ret = post(url, new ByteArrayRequestEntity(content));
			return ret;
		}
		catch (final Exception e)
		{
			return null;
		}
	}

	public static byte[] post(final String url, final RequestEntity requestEntity) throws Exception
	{
		final PostMethod method = new PostMethod(url);
		method.addRequestHeader("Connection", "Keep-Alive");
		method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		method.setRequestEntity(requestEntity);
		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		try
		{
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK)
			{
				return null;
			}
			return method.getResponseBody();

		}
		catch (final SocketTimeoutException e)
		{
			return null;
		}
		catch (final Exception e)
		{
			return null;
		}
		finally
		{
			method.releaseConnection();
		}
	}
}
