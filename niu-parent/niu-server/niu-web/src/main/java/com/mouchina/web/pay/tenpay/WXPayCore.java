package com.mouchina.web.pay.tenpay;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.wxpay.sdk.WXPay;
import com.mouchina.web.pay.tenpay.entity.Unifiedorder;

public class WXPayCore {
	
	private WXPay wxpay;
    private WXPayConfigImpl config;
	
	public WXPayCore(){
		try {
			config = WXPayConfigImpl.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
        wxpay = new WXPay(config);
	}


	public  Map<String, String> doUnifiedOrder(Unifiedorder unifiedorder){
		HashMap<String, String> data = new HashMap<String, String>();
		
		String tradeType = unifiedorder.getTrade_type();
		
		data.put("body", config.AGENT_BODY);
        data.put("out_trade_no", unifiedorder.getOut_trade_no());
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", String.valueOf(unifiedorder.getTotal_fee()));
        data.put("spbill_create_ip", unifiedorder.getSpbill_create_ip());
        data.put("notify_url", unifiedorder.getNotify_url());
        data.put("trade_type", tradeType);
        
        if(!StringUtils.isEmpty(tradeType) && "NATIVE".equals(tradeType)){
        	data.put("product_id", unifiedorder.getProduct_id());
        }
		
		Map<String, String> r = null;
		try {
			r = wxpay.unifiedOrder(data);
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}
}
