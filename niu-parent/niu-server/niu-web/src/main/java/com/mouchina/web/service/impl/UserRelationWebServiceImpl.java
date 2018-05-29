package com.mouchina.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.server.api.user.UserInfoService;
import com.mouchina.server.api.user.UserRelationService;
import com.mouchina.web.base.page.Page;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.UserRelationWebService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service
public class UserRelationWebServiceImpl implements UserRelationWebService{

	@Autowired
	private UserRelationService userRelationService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public Page<Map<String, Object>> findMineFanOrInterestUsers(String order, String loginKey, Integer type,
			int currentPage, int pageSize) {
		
		int total = userRelationService.findMineFanOrInterestUsersCount(loginKey, type);
		List<Map<String,Object>> data = userRelationService.findMineFanOrInterestUsers(order, loginKey, type, currentPage, pageSize);
		Page<Map<String,Object>> page = new Page<>(currentPage,pageSize, total, data);
		return page;
	}

	@Override
	public void saveFanOrInterest(Long dataId, String userAtoken, Long userBid, byte type,boolean twoWay, ModelMap modelMap) {
		UserInfo userA = userInfoService.findUserByToken(userAtoken, true);
		Long userAid = userA.getId();
		
		UserInfo userB = userInfoService.findByPrimaryKey(userBid);
		if(userB == null){
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorCode", Constants.ERROR_CODE_100000);
			modelMap.put("errorMsg", "您将要关注的用户不存在");
			return;
		}
		
		boolean isFan = userRelationService.isFan(userAid, userBid, type);
		if(isFan){
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorCode", Constants.ERROR_CODE_100000);
			modelMap.put("errorMsg", "您已关注了该用户");
			return;
		}
		
		boolean result = userRelationService.saveFanRelation(dataId, userAid, userBid, type, twoWay);
		if(result){
			modelMap.put("result", Constants.RESPONSE_SUCCESS);
		}else{
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorCode", Constants.ERROR_CODE_100000);
			modelMap.put("errorMsg", "关注失败");
		}
	}

}
