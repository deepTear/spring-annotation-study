/**
 * 
 */
package com.mouchina.web.controller.H5;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mouchina.base.utils.StringUtil;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.UserWebService;

/**
 * ClassName: Html5Controller
 *
 * @Description TODO
 *
 * @author Ronaldo
 *
 * @date 2017年3月29日 下午6:01:47
 *
 */
public class Html5Controller {
	
	private Logger logger = Logger.getLogger(getClass());
	UserWebService userWebService;
	
	/**
	 * 兑换广告币
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/change/coin", method = { RequestMethod.GET, RequestMethod.POST })
	public String changeAdvertCoin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		//response.addHeader("Access-Control-Allow-Origin", "*");
		String loginKey = request.getParameter("loginKey");
		String type = request.getParameter("type");//type 0：广告币   1：消费金       默认为  type = 0;
		if(StringUtil.isEmpty(type)){
			type = "0";
		}else{
			if(!type.equals("0") && !type.equals("1")){
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode", Constants.ERROR_CODE_100001);
				modelMap.put("errorMsg", "请求类型错误");
				return "";
			}
		}
		
		if(StringUtil.isNotEmpty(loginKey)){
			String configId = request.getParameter("configId");
			String flag = request.getParameter("flag");
			String title = request.getParameter("title");
			String paySource = request.getParameter("paySource");
			
			return "";
		}
		modelMap.put("result", Constants.ERROR_ERROR);
		modelMap.put("errorCode", Constants.ERROR_CODE_100004);
		modelMap.put("errorMsg", "认证错误");
		return "";
	}
}
