package com.mouchina.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mouchina.web.base.utils.Constants;

@Controller
@RequestMapping("/error")
public class ErrorController {
	
	@RequestMapping("/nouser")
	public String errorIndex(HttpServletRequest request, HttpServletResponse response,String msg, ModelMap modelMap){
		response.addHeader("Access-Control-Allow-Origin", "*");
		modelMap.put("result", Constants.RESPONSE_FAIL);
		//modelMap.put("errorCode", Constants.ERROR_CODE_100101);
		modelMap.put("errorMsg", msg);
		return "";
	}
	
	/*@RequestMapping("/sign")
	@ResponseBody
	public WebResult signError(String errorMsg) {
		return WebResult.build(errorMsg);
	}*/

}
