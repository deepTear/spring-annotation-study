package com.mouchina.server.api.user;

import java.util.List;
import java.util.Map;

import com.mouchina.entity.pojo.user.UserRelation;
import com.mouchina.server.api.base.BaseService;

public interface UserRelationService extends BaseService<UserRelation,Long>{

	public List<Map<String,Object>> findMineFanOrInterestUsers(String order,String loginKey,Integer type,int currentPage,int pageSize);
	
	public int findMineFanOrInterestUsersCount(String loginKey,Integer type);
	
	public boolean saveFanRelation(Long dataId,Long userAid,Long userBid,byte type,boolean twoWay);
	
	public boolean isFan(Long userAid,Long userBid,byte type);
}
