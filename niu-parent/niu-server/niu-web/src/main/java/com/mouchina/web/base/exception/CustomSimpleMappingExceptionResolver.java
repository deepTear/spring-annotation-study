package com.mouchina.web.base.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.mouchina.web.base.utils.Constants;

/**
 * 异常的统一处理
 * @author Cris
 *
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
    private final static Logger logger = LogManager.getLogger(CustomSimpleMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,Object handler, Exception ex ){
    	ex.printStackTrace();
    	logger.error(ex);
    	
    	ModelAndView modelAndView=new ModelAndView();
    	modelAndView.addObject("result", Constants.RESPONSE_FAIL);
    	modelAndView.addObject("errorCode", Constants.ERROR_CODE_100002);
    	modelAndView.addObject("errorMsg", "请求出错");

		return modelAndView;
	}

}
