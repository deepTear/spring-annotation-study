package com.mouchina.base.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * ClassName: HttpClientUtil 
 *
 * @Description http请求工具类
 *
 * @author 刘刚
 *
 * @date 2017年3月21日 上午9:19:39
 *
 */
public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	/**
	 * post请求
	 * @param url 请求地址
	 * @param header 自定义头信息
	 * @param uefEntity 请求参数
	 * @return
	 */
	public static String post(String url, String header, UrlEncodedFormEntity uefEntity) {
		//返回结果
		String result = "";
		//创建默认的httpClient实例.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//创建httppost  
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Header", header);
		try {
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error("请求Url : " + url + "出错,HttpClientUtils-->客户端协议异常");
		} catch (UnsupportedEncodingException e1) {
			logger.error("请求Url : " + url + "出错,HttpClientUtils-->不支持的编码异常");
		} catch (IOException e) {
			logger.error("请求Url : " + url + "出错,HttpClientUtils-->IO异常");
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * @author liuxin
	 * httpclient get 请求
	 * @param url 请求地址
	 * @param param 请求参数
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> param){
		// 定义返回参数
		String result = "";
		
		//创建httpclient 对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			//创建 uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for(String key : param.keySet()) {
					builder.addParameter(key, param.get(key) + "");
				}
			}
			URI uri = builder.build();
			
			// 创建httpclient  get 请求
			HttpGet httpGet = new HttpGet(uri);
			
			// 执行 httpclient 请求
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
