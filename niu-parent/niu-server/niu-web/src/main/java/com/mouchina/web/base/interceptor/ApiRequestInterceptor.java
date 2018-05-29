package com.mouchina.web.base.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.druid.support.json.JSONUtils;
import com.mouchina.base.redis.RedisHelper;
import com.mouchina.base.utils.DateUtils;
import com.mouchina.base.utils.MD5Util;
import com.mouchina.base.utils.StringUtil;
import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.UserWebService;

/**
 * Description: ApiRequestInterceptor Author: ourufeng Update:
 * zhangkun(2016-10-25 16:22)
 */
@Component
public class ApiRequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ApiRequestInterceptor.class);

	private String appIoskey;

	private String appAndroidkey;
	
	@Resource
	private UserWebService userWebService;
	
	@Resource
	private RedisHelper redisHelper;
	
	@Value("${test.model}")
	private boolean test_model;
	
	
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String headValue = request.getHeader("User-Agent");
		String host = request.getHeader("Host");
		String appversion = request.getParameter("version");
		String ua = request.getParameter("ua");
		String uadetail = request.getParameter("uadetail");
		String udid = request.getParameter("udid");
		String s = request.getParameter("s");
		String t = request.getParameter("t");
		String loginKey = request.getParameter("loginKey");
		
		if(StringUtil.isEmpty(loginKey)){
			
			boolean result = filterParameterStr(request);
			if(result){
				return true;
			}else{
				Map<String,Object> map = new HashMap<>();
				map.put("result", Constants.RESPONSE_FAIL);
				map.put("errorCode", Constants.ERROR_CODE_100003);
				map.put("errorMsg", "非法的验证请求!");
				String json = JSONUtils.toJSONString(map);
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().println(json);
				return false;
			}
		}else{
			//如果请求传入了loginKey则进行过滤
			UserInfo uInfo = userWebService.findUserByToken(loginKey,true);
			
			if(uInfo != null){
				return true;
			}else{
				logger.info("拦截器过滤loginKey,没有获取到用户,直接返回响应，不传递给后续controller");
				Map<String,Object> map = new HashMap<>();
				map.put("result", Constants.RESPONSE_FAIL);
				map.put("errorCode", Constants.ERROR_CODE_100001);
				map.put("errorMsg", "User Not Exists!");
				String json = JSONUtils.toJSONString(map);
				//说明当前用户不存在，直接给前台响应用户不存在的json串
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().println(json);
				return false;
			}
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

	/**
	 * 根据请求User-Agent中的信息决定是否拒绝此请求
	 * 
	 * @param request
	 * @return
	 */
	boolean filterParameterStr(HttpServletRequest request) {
		boolean flag = false;
		String userAgent = request.getHeader("User-Agent");
		String uri = request.getRequestURI();//
		String timeStamp = request.getParameter("timestamp");// 调用接口的时间戳
		String token = request.getParameter("token");// 调用接口的token
		Map<String, String[]> params = request.getParameterMap();
		
		
		if ("/user/identify/code/get".equals(uri)) {//用户获取验证码
			logger.info("获取验证码接口");
			if (userAgent != null) {
				if (userAgent.contains("Mozilla") && userAgent.contains("Windows") && !userAgent.contains("Android")) {
					flag = test_model;
				}else{
					flag = true;
				}
			}
		}

		if ("/user/login/check".equals(uri)) {//用户登录
			logger.info("用户登录验证接口");
			if (userAgent != null) {
				if (userAgent.contains("Mozilla") && userAgent.contains("Windows") && !userAgent.contains("Android")) {
					flag = test_model;
				}else{
					flag = true;
				}
			}
		}
		
		if ("/user/info/update".equals(uri)) {//用户注册
			logger.info("用户注册接口");
			if (userAgent != null) {
				if (userAgent.contains("Mozilla") && userAgent.contains("Windows") && !userAgent.contains("Android")) {
					flag = test_model;
				}else{
					flag = true;
				}
			}
		}
		
//		 flag = isValidKey(timeStamp, token);
		return flag;
	}
	
	/**
	 * 前端调用接口校验(签名校验)
	 * 
	 * @param timeStamp
	 *            调用发起的时间戳
	 * @param token
	 *            加密token
	 * @return
	 */
	boolean isValidKey(String timeStamp, String token) {
		boolean flag = false;

		long currentTimeMillion = System.currentTimeMillis();

		String serverIosToken = MD5Util.md5Hex("timestamp=" + timeStamp + "&key=bbb981f42b0d4f629dc5d3612b86acde");// Ios传入的对应的token
		String serverAndroidToken = MD5Util.md5Hex("timestamp=" + timeStamp + "&key=f3e9aaa6672d41e38036b9cd8492a1eb");// Android&H5传入的token
		if (timeStamp != null || !"".equals(timeStamp) || token != null || !"".equals(token)) {
			try {
				long invokeTime = Long.parseLong(timeStamp);
				if (currentTimeMillion > invokeTime) {
					long twoMinutesAgo = DateUtils
							.getSeveralMinutesAgoDate(DateUtils.timeStampToDate(currentTimeMillion), 2).getTime();// 当前时间推前2分钟对应的时间戳
					if (twoMinutesAgo <= invokeTime) {
						// 说明调用时间未超过2分钟
						if (token.equals(serverAndroidToken) || token.equals(serverIosToken)) {
							flag = true;
						}
					} else {
						// 调用时间超过2分钟摒弃
						flag = false;
					}
				} else {
					// 调用时间非法
					flag = false;
				}
			} catch (Exception e) {
				logger.error("解析签名参数异常!");
				flag = false;
			}
		} else {
			flag = false;
		}

		return flag;
	}

	public String getAppIoskey() {
		return appIoskey;
	}

	public void setAppIoskey(String appIoskey) {
		this.appIoskey = appIoskey;
	}

	public String getAppAndroidkey() {
		return appAndroidkey;
	}

	public void setAppAndroidkey(String appAndroidkey) {
		this.appAndroidkey = appAndroidkey;
	}

	public boolean isTest_model() {
		return test_model;
	}

	public void setTest_model(boolean test_model) {
		this.test_model = test_model;
	}
	
}
