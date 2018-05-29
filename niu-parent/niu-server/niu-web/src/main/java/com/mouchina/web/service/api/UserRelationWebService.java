package com.mouchina.web.service.api;

import java.util.Map;

import org.springframework.ui.ModelMap;

import com.mouchina.web.base.page.Page;

public interface UserRelationWebService {

	/**
	 * 分页查询我的粉丝或关注
	 * @param order
	 * @param loginKey
	 * @param type  0：粉丝  1：关注
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String,Object>> findMineFanOrInterestUsers(String order,String loginKey,Integer type,int currentPage,int pageSize);
	
	
	/**
	 * 添加关注关系
	 * @param dataId  数据源id
	 * @param userAtoken  关注用户的loginKey
	 * @param userBid     被关注用户的ID
	 * @param type        关系类型  0：关注
	 * @param twoWay      是否双向关注
	 * @param modelMap
	 */
	public void saveFanOrInterest(Long dataId,String userAtoken,Long userBid,byte type,boolean twoWay,ModelMap modelMap);
	
}
