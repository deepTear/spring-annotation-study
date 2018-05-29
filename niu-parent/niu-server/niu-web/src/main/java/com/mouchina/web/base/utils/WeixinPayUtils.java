package com.mouchina.web.base.utils;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;

import com.mouchina.web.pay.tenpay.MD5Util;

public class WeixinPayUtils {

	private static Logger log = Logger.getLogger(WeixinPayUtils.class);
	
	@Value("${weixin_pay_appid:wx11062cf326bb09bb}")
	private static String appid = "wx11062cf326bb09bb";
	
	@Value("${weixin_pay_mch_id:1315715001}")
	private static String mch_id = "1315715001";
	
	@Value("${weixin_pay_trade_type:APP}")
	private static String trade_type = "APP";
	
	@Value("${weixin_pay_key:mmc201603201200xKxLx4o8PJ1afCtk9}")
	private static String key = "mmc201603201200xKxLx4o8PJ1afCtk9";
	
	@Value("${weixin_pay_spbill_create_ip:120.26.214.247}")
	private static String spbill_create_ip = "120.26.214.247";
	
	@Value("${weixin_pay_unifiedorder_url:https://api.mch.weixin.qq.com/pay/unifiedorder}")
	private static String weixin_pay_unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	
	/**
	 * 申请微信 支付签名
	 * @param totalMoney  订单总金额 单位分
	 * @param orderNo   系统订单号
	 * @param clientIP  客户端IP
	 * @param title  订单标题
	 * @param notityUrl  付款回调地址
	 * @param limitPay   限制是否使用信用卡 
	 * @return
	 * @throws IllegalAccessException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static Map<String,String> createWeixinPaySign(int totalMoney,String orderNo,String clientIP,String title,String weixinNotityUrl,boolean limitPay) throws IllegalAccessException, NoSuchAlgorithmException{
		
		Map<String,String> response = null;
		String random = create32bitRandomString();//请求随机值
		
		StringBuilder paySignRequestXml = new StringBuilder();
		paySignRequestXml.append("<xml>");
		paySignRequestXml.append("<appid>"+appid+"</appid>");//应用ID 必填
		paySignRequestXml.append("<mch_id>"+mch_id+"</mch_id>");//商户号 必填
		//paySignRequestXml.append("<device_info></device_info>");//设备号 非 必填
		paySignRequestXml.append("<sign_type>MD5</sign_type>");//签名类型 非 必填
		paySignRequestXml.append("<nonce_str>"+random+"</nonce_str>");//随机字符串 必填
		paySignRequestXml.append("<body>"+title+"</body>");//商品描述 必填
		//paySignRequestXml.append("<detail></detail>");//商品详情 非必填
		//paySignRequestXml.append("<attach></attach>");//附加数据 非 必填
		paySignRequestXml.append("<out_trade_no>"+orderNo+"</out_trade_no>");//商户订单号 必填
		//paySignRequestXml.append("<fee_type></fee_type>");//货币类型 非必填
		paySignRequestXml.append("<total_fee>"+totalMoney+"</total_fee>");//总金额  必填
		paySignRequestXml.append("<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>");//终端IP 必填
		//paySignRequestXml.append("<time_start></time_start>");//交易起始时间 非 必填
		//paySignRequestXml.append("<time_expire></time_expire>");//交易结束时间 非 必填
		//paySignRequestXml.append("<goods_tag></goods_tag>");//订单优惠标记 非 必填
		paySignRequestXml.append("<notify_url>"+weixinNotityUrl+"</notify_url>");//通知地址  必填
		paySignRequestXml.append("<trade_type>"+trade_type+"</trade_type>");//交易类型 必填
		
		if(limitPay){//不支持信用卡
			paySignRequestXml.append("<limit_pay>no_credit</limit_pay>");//指定支付方式 非 必填
		}
		
		//paySignRequestXml.append("<scene_info></scene_info>");//场景信息 非 必填
		
		Map<String,String> param = new HashMap<>();
		param.put("appid", appid);
		param.put("mch_id", mch_id);
		param.put("sign_type", "MD5");
		param.put("nonce_str", random);
		param.put("body", title);
		param.put("out_trade_no", orderNo);
		param.put("total_fee", totalMoney + "");
		param.put("spbill_create_ip", spbill_create_ip);
		param.put("notify_url", weixinNotityUrl);
		param.put("trade_type", trade_type);
		
		if(limitPay){//不支持信用卡
			param.put("limit_pay", "no_credit");
		}
		
		String stringA = getKeyValueString(param);
		String stringSignTemp = stringA + "&key=" + key;
		String sign = MD5Util.MD5Encode(stringSignTemp, "UTF-8");
		
		paySignRequestXml.append("<sign>"+sign+"</sign>");//签名  必填
		paySignRequestXml.append("</xml>");
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(weixin_pay_unifiedorder_url);
			httpPost.addHeader("Content-Type","text/html;charset=UTF-8");
			//解决中文乱码问题  
			StringEntity stringEntity = new StringEntity(paySignRequestXml.toString(),"UTF-8");
			stringEntity.setContentEncoding("UTF-8");  
			httpPost.setEntity(stringEntity);
			//CloseableHttpResponse response = httpclient.execute(httpPost);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			    @Override
			    public String handleResponse(final HttpResponse response)throws ClientProtocolException, IOException {//                 
			        int status = response.getStatusLine().getStatusCode();
			        if (status >= 200 && status < 300) {

			            HttpEntity entity = response.getEntity();
			            return entity != null ? EntityUtils.toString(entity) : null;
			        } else {
			            throw new ClientProtocolException("Unexpected response status: " + status);
			        }
			    }
			};          
			String responseBody = client.execute(httpPost, responseHandler);
			
			/**
			 * 正确结果
			 */
			/*<xml><return_code><![CDATA[SUCCESS]]></return_code>
			<return_msg><![CDATA[OK]]></return_msg>
			<appid><![CDATA[wx11062cf326bb09bb]]></appid>
			<mch_id><![CDATA[1315715001]]></mch_id>
			<nonce_str><![CDATA[bIaV939eQSIeaAE1]]></nonce_str>
			<sign><![CDATA[04D186DF39ACD9B245F191E7FFDC5DE1]]></sign>
			<result_code><![CDATA[SUCCESS]]></result_code>
			<prepay_id><![CDATA[wx101455263058677f20ec29282872414310]]></prepay_id>
			<trade_type><![CDATA[APP]]></trade_type>
			</xml>*/
			
			log.info("统一下单响应结果" + responseBody);
			
			try {
				response = xmlParseMap(responseBody);
			} catch (DocumentException e) {
				log.info("统一下单获取响应结果出错");
				e.printStackTrace();
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return response;
	}
	
	public static String create32bitRandomString(){
		String randomSource = "ABCDEFGHIJKLMNOPQISTUVWXYZabcdefghijklmnopqistuvwxyz0123456789";
		String random = "";
		
		while(random.length() < 16){
			random += randomSource.charAt((int)(Math.random() * randomSource.length()));
		}
		return random;
	}
	
	
	public static Map<String,String> xmlParseMap(String xmlStr) throws DocumentException{
		Document document = DocumentHelper.parseText(xmlStr);;
		// 解析结果存储在HashMap
	    Map<String, String> map = new HashMap<String, String>();
	    // 读取输入流
	    SAXReader reader = new SAXReader();
	    // 得到xml根元素
	    Element root = document.getRootElement();
	    // 得到根元素的所有子节点
	    List<Element> elementList = root.elements();
	    // 遍历所有子节点
	    for (Element e : elementList){
	        map.put(e.getName(), e.getText());
	    }
	    return map;
	}
	
	
	public static String getKeyValueString(Map<String,String> param){
		
		if(param == null || param.size() == 0){
			throw new IllegalArgumentException("参数错误");
		}
		
		// 先将参数以其参数名的字典序升序进行排序
	    Map<String, String> sortedParams = new TreeMap<String, String>(param);
	    Set<Entry<String, String>> entrys = sortedParams.entrySet();
	 
	    // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
	    StringBuilder basestring = new StringBuilder();
	    int i = 0;
	    for (Entry<String, String> param_ : entrys) {
	    	if(i != 0){
	    		 basestring.append("&");
	    	}
	    	basestring.append(param_.getKey()).append("=").append(param_.getValue());
	    	i++;
	    }
	    
	    return basestring.toString();
		
	}
	
	public static void main(String[] args) {
		
		/*String randomSource = "ABCDEFGHIJKLMNOPQISTUVWXYZabcdefghijklmnopqistuvwxyz0123456789";
		String random = "";
		
		while(random.length() < 16){
			random += randomSource.charAt((int)(Math.random() * randomSource.length()));
		}
		System.out.println(random);*/
		
		try {
			createWeixinPaySign(1000, "12345678911", "", "测试", "https://newtest.mouchina.com", true);
		} catch (IllegalAccessException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}


	public static String getAppid() {
		return appid;
	}


	public static void setAppid(String appid) {
		WeixinPayUtils.appid = appid;
	}


	public static String getMch_id() {
		return mch_id;
	}


	public static void setMch_id(String mch_id) {
		WeixinPayUtils.mch_id = mch_id;
	}


	public static String getTrade_type() {
		return trade_type;
	}


	public static void setTrade_type(String trade_type) {
		WeixinPayUtils.trade_type = trade_type;
	}


	public static String getKey() {
		return key;
	}


	public static void setKey(String key) {
		WeixinPayUtils.key = key;
	}


	public static String getSpbill_create_ip() {
		return spbill_create_ip;
	}


	public static void setSpbill_create_ip(String spbill_create_ip) {
		WeixinPayUtils.spbill_create_ip = spbill_create_ip;
	}


	public static String getWeixin_pay_unifiedorder_url() {
		return weixin_pay_unifiedorder_url;
	}


	public static void setWeixin_pay_unifiedorder_url(String weixin_pay_unifiedorder_url) {
		WeixinPayUtils.weixin_pay_unifiedorder_url = weixin_pay_unifiedorder_url;
	}
	
	
}
