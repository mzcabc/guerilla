package com.mindy.gettjuseatinfo.util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class GetWebPage {
	/**
	 * 读取url的xml资源 转成String
	 * 
	 * @param url
	 * @return 返回 读取url的xml字符串
	 */
	public static String getStringByUrl(String url) {
		String outputString = "";
		// DefaultHttpClient
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// HttpGet
		HttpGet httpget = new HttpGet(url);
		// ResponseHandler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			outputString = httpclient.execute(httpget, responseHandler);
			// outputString = new String(outputString.getBytes("ISO-8859-1"),
			// "utf-8"); // 解决中文乱码
			outputString = new String(outputString.getBytes("utf-8"), "utf-8");

			Log.i("HttpClientConnector", "连接成功");
		} catch (Exception e) {
			Log.i("HttpClientConnector", "连接失败");
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return outputString;
	}
}
