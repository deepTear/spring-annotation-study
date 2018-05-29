package com.mouchina.web.base.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliDaYuSMSUtils {
	private static final Logger logger = LoggerFactory.getLogger(AliDaYuSMSUtils.class);
	public static void main(String[] args) {
		
		String url = "https://eco.taobao.com/router/rest";
		String appkey = "23485707";
		String secret = "a1e8f4efd4062af22d404362f1e4ece1";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		String paramStr = "{\"number\" : \"1234\"}";

		String phone = "18293460282";
		String templateCode = "SMS_20230072";
		
		boolean flag = sendSmsNew(url, appkey, secret, paramStr, phone, templateCode);
		System.out.println(flag);
//		sendSmsLoginConfirm("15771770206", "1232");
	}
	
	/**
	 * 阿里大鱼发送 登陆确认 验证码 方法
	 * @param phone 目标电话号码
	 * @param code 验证码值
	 * @return true 表示发送成功 false 发送失败
	 */
	public static boolean sendSmsLoginConfirm(String phone,String code){
		boolean flag = false;
		String url = "https://eco.taobao.com/router/rest";
		String appkey = "23485707";
		String secret = "a1e8f4efd4062af22d404362f1e4ece1";
		String paramStr = "{'code' : " + "\"" + code + "\"" +  "," + "'product' : " + "牛牛福袋" + "}";
		String templateCode = "SMS_12585100";
		flag = sendSmsNew(url, appkey, secret, paramStr, phone, templateCode);
			
		return flag;
	}
	
	
	/**
	 * 
	 * @param url 阿里大于短信接口url : https://eco.taobao.com/router/rest
	 * @param appkey 阿里大于appkey : 23485707
	 * @param secret appkey对应的密钥 : a1e8f4efd4062af22d404362f1e4ece1
	 * @param paramStr 发送的模板中的变量json(验证码模板只有变量number)
	 * @param phone 要发送的电话号码
	 * @param templateCode 模板id : SMS_20230072(注册验证码模板)   SMS_21255089(忘记密码模板)   SMS_31975020(绑定手机模板)
	 * @return
	 */
	public static boolean sendSmsNew(String url,String appkey,String secret,String paramStr,String phone,String templateCode){
		boolean flag = false;
		try {
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setExtend("123456");
			req.setSmsType("normal");
			req.setSmsFreeSignName("哞哞科技");
			req.setSmsParamString(paramStr);
			req.setRecNum(phone);
			req.setSmsTemplateCode(templateCode);
			AlibabaAliqinFcSmsNumSendResponse rsp;
			rsp = client.execute(req);
			
			if(rsp != null){
				if(rsp.isSuccess()){
					flag = rsp.getResult().getSuccess();
				}else{
					flag = false;
				}
			}else{
				flag = false;
			}
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 
	 * @param sms_template_code
	 *            SMS_4997011
	 * @param signName
	 *            注册验证
	 * @param content
	 *            {'code':'8987','product':'哞哞科技'}
	 * @param phone
	 * @return
	 */
	public static boolean sendSms(String key,String secret, String templateCode, String signName,
			String content, String phone) {
		boolean flag = false;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			//
			HttpUriRequest httpGet = RequestBuilder
					.get()
					.setUri("https://ca.aliyuncs.com/gw/alidayu/sendSms")
					.addHeader("X-Ca-Key", "23307811")
					.addHeader("X-Ca-Secret",
							"e88d62b8d6830e189a57014f8399fad0")
					.addParameter("rec_num", phone)
					.addParameter("sms_template_code", templateCode)
					.addParameter("sms_free_sign_name", signName)
					.addParameter("sms_type", "normal")
					.addParameter("sms_param", content).build();

			ResponseHandler<String> responsHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}

			};
			String responseBody = httpClient.execute(httpGet, responsHandler);
			System.out.println(responseBody);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

}
