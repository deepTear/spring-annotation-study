package com.mouchina.web.pay.alipay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.json.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.mouchina.web.pay.alipay.config.AlipayConfig;
import com.mouchina.web.pay.alipay.entity.AliPayRequestContent;

public class AlipayCore {
	
	static AlipayClient alipayClient = null;
	
	static {
		 alipayClient = new DefaultAlipayClient(AlipayConfig.gateway, AlipayConfig.APP_ID, AlipayConfig.ali_rsa_private_key, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.ali_public_key, AlipayConfig.sign_type);
	}
	
	public AlipayTradeCreateResponse aliPayTradeCreate(AliPayRequestContent content){
		AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
		
		request.setNotifyUrl(AlipayConfig.notify_url);//将来支付后支付宝会通知此地址
		try {
			request.setBizContent(JSON.json(content));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		AlipayTradeCreateResponse aliTradeCreateresponse = null;
		try {
			aliTradeCreateresponse = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return aliTradeCreateresponse;
    }
	
	
	/** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
	
	
	public static void main(String[] args) {
		Map<String,Object> paraMap = new HashMap<>();
		paraMap.put("out_trade_no", "20150320010101001");
		paraMap.put("total_amount", 123);
		paraMap.put("subject", "预留");   //预留 主题 参数
		paraMap.put("buyer_id", 12333);
		
		try {
			System.out.println(JSON.json(paraMap));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
