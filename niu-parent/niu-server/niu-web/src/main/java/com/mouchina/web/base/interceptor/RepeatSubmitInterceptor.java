package com.mouchina.web.base.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.mouchina.base.redis.RedisServer;
import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.web.base.annotation.RepeatSubmitFilterAnnotation;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.UserWebService;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/**
 * 重复提交请求处理
 * @author Administrator
 *
 */
public class RepeatSubmitInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(RepeatSubmitInterceptor.class);
	
	private final static String KEY_PREFIX = "REPEAT_SUBMIT_REQUEST";//redis key 前缀
	
	@Resource
	private UserWebService userWebService;
	
	@Resource
	private RedisServer redisServer;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		String loginKey = request.getParameter("loginKey");
		String uri = "";
		String key = "";
		
		HandlerMethod handlerMethod = null;  
		handlerMethod = (HandlerMethod)handler;
		//System.out.println(handlerMethod.getMethod());
	    if(handlerMethod != null){
	    	Method method = handlerMethod.getMethod();
	    	RepeatSubmitFilterAnnotation anno = method.getAnnotation(RepeatSubmitFilterAnnotation.class);
	    	if(anno != null){
	    		if(StringUtils.isNotEmpty(loginKey)){
	    			UserInfo u = userWebService.findUserByToken(loginKey,true);
	    			if(u != null){
	    				uri = request.getRequestURI();
	    				
	    				key = KEY_PREFIX + "_" + u.getId() + "_" + uri;
    					if(!this.setex(key, 30, u.getId() + "")){
    						logger.info("------------重复提交-------------->" + getUrl(request));
    						Map<String,Object> map = new HashMap<>();
    						map.put("result", Constants.RESPONSE_FAIL);
    						//map.put("errorCode", Constants.ERROR_CODE_100010);
    						map.put("errorMsg", "重复提交!");
    						String json = JSONUtils.toJSONString(map);
    						response.setContentType("application/json;charset=utf-8");
    						response.getWriter().println(json);
    						return false;
    					}
	    				
	    			}
	    		}
	    	}
	    }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		String loginKey = request.getParameter("loginKey");
		String uri = "";
		String key = "";
		
		HandlerMethod handlerMethod = null;  
		handlerMethod = (HandlerMethod)handler;
		//System.out.println(handlerMethod.getMethod());
	    if(handlerMethod != null){
	    	Method method = handlerMethod.getMethod();
	    	RepeatSubmitFilterAnnotation anno = method.getAnnotation(RepeatSubmitFilterAnnotation.class);
	    	
	    	if(anno != null){
	    		if(StringUtils.isNotEmpty(loginKey)){
	    			UserInfo u = userWebService.findUserByToken(loginKey,true);
	    			if(u != null){
	    				uri = request.getRequestURI();
	    				key = KEY_PREFIX + "_" + u.getId() + "_" + uri;
	    				this.remove(key);
	    			}
	    		}
	    	}
	    }
	}
	
	private long remove(String key) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			return jedis.del(key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
		return 0;
	}
	
	private boolean setex(String key, Integer seconds,String value) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			logger.debug("lock key: " + key);
			long i = jedis.setnx(key,value);
			if (i == 1) {
				jedis.expire(key, seconds);
				logger.debug("get lock, key: " + key + " , expire in "+ seconds + " seconds.");
				return true;
			}
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
		return false;
	}
	
	private String getUrl(HttpServletRequest request){
		String url = "http://" + request.getServerName() //服务器地址    
        + ":"     
        + request.getServerPort()           //端口号    
        + request.getRequestURI();
		
		String queryurl = request.getQueryString();  
	    if(null != queryurl){  
	        url += "?" + queryurl;  
	    }
		return url;
	}

}
