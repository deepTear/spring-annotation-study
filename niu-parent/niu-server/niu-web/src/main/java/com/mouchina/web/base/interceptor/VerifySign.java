package com.mouchina.web.base.interceptor;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class VerifySign implements HandlerInterceptor {

	private final static Logger LOGGER = LogManager.getLogger(VerifySign.class);

	private String Key;

	//@Value("${H5_BASE_URL_DOMAIN}")
	private String H5_BASE_URL_DOMAIN;
	
	//@Value("${WEB_BASE_URL}")
	private String WEB_BASE_URL;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String referer = request.getHeader("referer");
		if (StringUtils.isNotEmpty(referer)) {
			referer = referer.substring(referer.indexOf("//") + 2, referer.indexOf('/', referer.indexOf("//") + 2));
			if (H5_BASE_URL_DOMAIN.equals(referer)) {
				return true;
			}
		}
		
		String signMsg = getSignMsg(request);
		String sign = signature(signMsg);
		if (!sign.equals(request.getParameter("sign"))) {
			LOGGER.info(request.getRequestURI() + " ==> sign faild!");
			response.sendRedirect(WEB_BASE_URL + "/error/sign?errorMsg=sign is Invalid");
			return false;
		}
		
		if ("/common/resource/uploadVideos".equals(request.getRequestURI())) {
			return true;
		}

		long timestamp = Long.valueOf(request.getParameter("timestamp"));
		if (new Date().getTime() - timestamp > 30000) {
			LOGGER.info(request.getRequestURI() + " ==> sign timeout!");
			response.sendRedirect(WEB_BASE_URL + "/error/sign?errorMsg=sign is timeout");
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 参数签名
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private String getSignMsg(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException {
		StringBuffer signMsg = new StringBuffer();

		Map<?, ?> requestParams = request.getParameterMap();
		Map<String, String> params = new HashMap<>();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		params.remove("sign");

		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			signMsg.append((i == 0 ? "" : "&") + key + "=" + value);
		}

		return signMsg + "&key=" + Key;
	}

	/**
	 * MD5签名
	 * 
	 * @param orgin
	 * @return String
	 */
	public static String signature(String orgin) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = bytes2HexString(md.digest(orgin.toString().getBytes("utf-8")));
		} catch (Exception e) {
			throw new java.lang.RuntimeException("sign error !");
		}
		return result;
	}

	/**
	 * Byte数组转十六进制字符串，字节间不用空格分隔
	 * 
	 * @param b
	 * @return String
	 */
	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public void setKey(String key) {
		Key = key;
	}

}
