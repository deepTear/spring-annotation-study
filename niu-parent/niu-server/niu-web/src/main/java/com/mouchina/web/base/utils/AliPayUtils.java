package com.mouchina.web.base.utils;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;

public class AliPayUtils {
	
	private static Logger log = Logger.getLogger(AliPayUtils.class);

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String PARTNER = "2088121765316246";
	
	private static String APPID = "2016020301137510";
	
	private static String APP_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMMdabHWokta+GXxlrBCZ4EaNufENiE3AhXynHrcYjXyMXDipcwNeTQYNoS0qquX18a20pYcRyj/QO1IRfWgrwAtQMerJvlZFfx4ayYAVKjGIId6nF4ulr2MoLiaepfBUJeOeVJGnq9FEPR3odChK1SH3MHHMXqlcIwhU80k1+ddAgMBAAECgYEAtEtmkpWh7H4IULNYKlGCMVMW7z1pQ3IANGW2YPVqryy4sPZOfGrq18+T+Ta1CtDJ6+6eU2WX//M2fMyvENVFPwYUsjV6y3xKMmSJLG+qo47oArWix0SC5/4IHHLal18ikXoY7DXRb11P7onlU6SqWO+ATz2whSwKc/etS5n3G8UCQQD5L0vO1USkq8ke89BLkEgcpkp7UdFFFDonVteIrIjidZA03pQoQB3Zyz+LLeHwLk54G6X4ai8T4xO3ZpAmO4BzAkEAyHOJ5PsY8z33t2GcZCTghbds8uP50G25wBTsBt1SZOG/XF4PB6hgKNobKllruu0/yBAUXzegmipJHTnNNzYU7wJARCGxaZEYXqn1qLfjSPXfMa4qCUPaozefmuf8sIr0Nm8MidgdzE+TmdwfuK9H6oLOw7pTi/OEgbyBxJCPc1SrjwJALJzaYeYoxWpTSbMl1XwHpHsQenaW0Pa60q9VYKm9fspnhq1AdxnYXP898Iz6Vx4ahfJeNlrxGZ6zqxeEPveAbQJBAPW/i8quXBibYAubC+sIpkvxrQ1GKEOnoCwZ9Z/RJvpf/89uh3O/GkwfupFXEFpLlDzNMdz9Kob874Db0OkcsE0=";
	
	private static String ALIPAY_GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
	
	private static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";;
	
	private static String DATA_FORMAT = "json";
	
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String CHARSET = "utf-8";
	
	// 签名方式 不需修改
	public static String SIGN_TYPE = "RSA";
	
	
	public static String createAliPaySign(String orderNo,String actualMoney,String title,String aliPayNotifyUrl) throws AlipayApiException, IllegalAccessException{
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY_URL, APPID, APP_PRIVATE_KEY, DATA_FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
		AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();//下单接口
		
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setSubject(title);
		model.setOutTradeNo(orderNo);
		model.setTotalAmount(actualMoney);
		
		String bizContent = "{" +
		"\"out_trade_no\":\""+orderNo+"\"," + //订单号
		"\"total_amount\":\""+actualMoney+"\"," +   //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
		"\"subject\":\""+title+"\"" + //订单标题
		"}";
		
		request.setBizModel(model);
		request.setBizContent(bizContent);
		request.setNotifyUrl(aliPayNotifyUrl);
		
		try {
	        AlipayTradeCreateResponse response = alipayClient.sdkExecute(request);
	        if(response.isSuccess()){
	        	System.out.println("调用成功");
	        	return response.getBody();
        	} else {
        		System.out.println("调用失败");
        	}
	    } catch (AlipayApiException e) {
		    e.printStackTrace();
		    log.info("支付宝创建订单失败");
		}
		return null;
	}
	
	/**
	 * 验证签名
	 * @param params
	 * @return
	 * @throws AlipayApiException
	 */
	public static boolean checkSign(Map<String,String> params) throws AlipayApiException{
		if(params == null || params.size() == 0){
			throw new IllegalArgumentException("参数错误");
		}
		return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET,SIGN_TYPE);
	}
	
	
	public static void main(String[] args) {
		try {
			createAliPaySign("2018051100003", "0.01", "ceshi", "https://newtest.mouchina.com");
		} catch (IllegalAccessException | AlipayApiException e) {
			e.printStackTrace();
		}
	}
}
