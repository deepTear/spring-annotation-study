package com.mouchina.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.web.base.annotation.RepeatSubmitFilterAnnotation;
import com.mouchina.web.base.page.Page;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.UserRelationWebService;
import com.mouchina.web.service.api.UserWebService;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	public UserWebService userWebService;
	
	@Autowired
	public UserRelationWebService userRelationWebService;
	
	
	/**
	 * 
	 * 获取短信验证码
	 * @param phone
	 * @param flag
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/identify/code/get")
	public String getSmsCode(String phone,@RequestParam(defaultValue = "")String flag,ModelMap modelMap){
		userWebService.getIdenfityCode(phone,flag, modelMap);
		return "";
	}
	
	/**
	 * 用户登录
	 * @param flag
	 * @param phone
	 * @param smsCode
	 * @param version
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/login/check")
	public String login(@RequestParam(defaultValue = "")String flag,String phone,String smsCode,Integer version,ModelMap modelMap){
		userWebService.login(flag, phone, smsCode, version, modelMap);
		return "";
	}
	
	/**
	 * 更新或新增用户信息
	 * @param type   0:进入首页保存   1:领取红包保存  2：编辑信息保存
	 * @param loginKey
	 * @param userInfo
	 * @param modelMap
	 * @return
	 */
	@RepeatSubmitFilterAnnotation
	@RequestMapping("/info/update")
	public String updateUserInfo(@RequestParam(defaultValue = "0")Integer type,@RequestParam(defaultValue = "")String loginKey,UserInfo userInfo,ModelMap modelMap){
		userWebService.updateUserInfo(loginKey, userInfo,modelMap);
		return "";
	}
	
	
	/**
	 * 获取用户兴趣选项
	 * @param currentPage
	 * @param pageSize
	 * @param loginKey
	 * @param modelMap
	 * @return
	 */
	@RepeatSubmitFilterAnnotation
	@RequestMapping("/interest/items")
	public String getUserInterestItems(@RequestParam(defaultValue = "1")Integer currentPage,
			@RequestParam(defaultValue = "10")Integer pageSize,String loginKey,ModelMap modelMap){
		Page<Map<String,Object>> list = userWebService.getUserInterestItems(currentPage,pageSize);
		modelMap.put("page", list);
		modelMap.put("result", Constants.RESPONSE_SUCCESS);
		return "";
	}
	
	/**
	 * 添加关注
	 * @return
	 */
	@RequestMapping("/add/fans")
	public String saveFanOrInterest(Long dataId,String loginKey,Long userBid,byte type,ModelMap modelMap){
		userRelationWebService.saveFanOrInterest(dataId, loginKey, userBid, type, true, modelMap);
		return "";
	}
	
	
	/**
	 * 用户的关注或粉丝列表
	 * @param type 0:关注  1：粉丝
	 * @param currentPage
	 * @param pageSize
	 * @param loginKey
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/fans")
	public String mineFanOrAttention(@RequestParam(defaultValue = "0")Integer type,@RequestParam(defaultValue = "1")Integer currentPage,
			@RequestParam(defaultValue = "10")Integer pageSize,String loginKey,ModelMap modelMap){
		String order = "a.create_time desc";
		Page<Map<String,Object>> page = userRelationWebService.findMineFanOrInterestUsers(order, loginKey, type, currentPage, pageSize);
		modelMap.put("result", Constants.RESPONSE_SUCCESS);
		modelMap.put("page", page);
		return "";
	}
	
	
	
	
	
}
