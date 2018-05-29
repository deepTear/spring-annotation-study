package com.mouchina.web.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mouchina.web.base.utils.Constants;


/**
 * 测试
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/auth")
public class AuthTokenController {

	
	
	@RequestMapping("/get/token")
	public String token(HttpServletRequest request,ModelMap modelMap){
		
		try {
			HashMap<String,String> param = new HashMap<>();
			Map<String, String[]> params_ = request.getParameterMap();
			Iterator<Map.Entry<String, String[]>> entries = params_.entrySet().iterator();
			while (entries.hasNext()) {
				StringBuffer strArray = new StringBuffer();
				Map.Entry<String, String[]> entry = entries.next();
				for (String str : entry.getValue()) {
					strArray.append(str);
					//strArray.append(",");
				}
				param.put(entry.getKey(), strArray.toString());
			}
			
			// 先将参数以其参数名的字典序升序进行排序
		    Map<String, String> sortedParams = new TreeMap<String, String>(param);
		    Set<Entry<String, String>> entrys = sortedParams.entrySet();
		 
		    // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		    StringBuilder basestring = new StringBuilder();
		    for (Entry<String, String> param_ : entrys) {
		        basestring.append(param_.getKey()).append("=").append(param_.getValue());
		    }
		    basestring.append("111111");
		 
		    // 使用MD5对待签名串求签
		    byte[] bytes = null;
		    try {
		        MessageDigest md5 = MessageDigest.getInstance("MD5");
		        bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		    } catch (GeneralSecurityException ex) {
		        throw new IOException(ex);
		    }
		 
		    // 将MD5输出的二进制结果转换为小写的十六进制
		    StringBuilder sign = new StringBuilder();
		    for (int i = 0; i < bytes.length; i++) {
		        String hex = Integer.toHexString(bytes[i] & 0xFF);
		        if (hex.length() == 1) {
		            sign.append("0");
		        }
		        sign.append(hex);
		    }
			
			String access_token = sign.toString();
			modelMap.put("result", Constants.RESPONSE_SUCCESS);
			modelMap.put("access_token", access_token);
		} catch (Exception e) {
			//e.printStackTrace();
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorMsg", "签名获取失败");
		}
		
		return "";
	}
	
	
}
