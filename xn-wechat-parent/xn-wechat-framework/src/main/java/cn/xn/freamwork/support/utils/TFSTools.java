package cn.xn.freamwork.support.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TFSTools
{
    private static final Logger logger = LoggerFactory.getLogger(TFSTools.class);

	// 上传图片到FastDFS路径
	private static String ImagesUploadPathTFS = "http://172.17.1.64:7500/v1/tfs?suffix=.ppt&simple_name=0";

	public static String Upload()
	{
		// TODO Auto-generated method stub

		//String str = "http://172.17.1.64:7500/v1/tfs?suffix=.jpg&simple_name=0";
		String str = "http://172.17.1.64:7500/v1/tfs?suffix=.ppt&simple_name=0";
		String line = "";
		final StringBuilder stringBuilder = new StringBuilder();
		final String filePath = "F://Node.js_简介.ppt";
		try
		{
			final URL postUrl = new URL(str);
			// 打开连接
			final HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			// 打开读写属性，默认均为false
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty(" Content-Type ", " application/x-www-form-urlencoded ");
			final DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            //读文件
			final File file = new File(filePath);
			final FileInputStream fileInputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int numReadByte = 0;
			while ((numReadByte = fileInputStream.read(bytes, 0, 1024)) > 0)
			{
				out.write(bytes, 0, numReadByte);
			}
			out.flush();
			out.close(); // flush and close
			fileInputStream.close();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			while ((line = reader.readLine()) != null)
			{
				stringBuilder.append(line);
			}
			reader.close();
			connection.disconnect();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}


    /**
     * 上传图片到TFS
     *
     * @param bytes
     * @return
     */
    public static String Upload(final byte[] bytes, String url)
    {
        String line = "";
        final StringBuilder stringBuilder = new StringBuilder();
        try
        {
            final URL postUrl = new URL(url);
            // 打开连接
            final HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            // 打开读写属性，默认均为false
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty(" Content-Type ", " application/x-www-form-urlencoded ");
            final DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(bytes);
            out.flush();
            out.close(); // flush and close
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            reader.close();
            connection.disconnect();
        }catch (final Exception e) {
            e.printStackTrace(); // 深层次打异常，非常耗性能
            logger.error("======>>> file output read Err: "+ e);
        }

        return stringBuilder.toString();
    }

	public static void main(final String[] args)
	{

        String imagePath = Upload();
        String picData = "{\"Error\":0,\"fileName\": \"" + "\",\"remoteFilename\":\""
                + imagePath + "\",\"Url\":\""  + ImagesUploadPathTFS+imagePath +"}";

        System.out.println(picData);
    }

}
