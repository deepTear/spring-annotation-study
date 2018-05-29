package com.mouchina.web.pay.tenpay;

import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

/**
 * 微信支付接口配置
 * @author ZDG
 *
 */
public class WXPayConfigImpl implements WXPayConfig{

	private static WXPayConfigImpl INSTANCE;
	public static String AGENT_BODY = "微信公众号支付-代理商";
	
	public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }
	
	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getCertStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMchID() {
		// TODO Auto-generated method stub
		return null;
	}

}
