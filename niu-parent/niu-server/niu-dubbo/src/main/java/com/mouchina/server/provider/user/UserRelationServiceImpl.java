package com.mouchina.server.provider.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.dao.user.UserRelationMapper;
import com.mouchina.entity.pojo.user.UserRelation;
import com.mouchina.entity.pojo.user.UserRelationExample;
import com.mouchina.entity.pojo.user.UserRelationExample.Criteria;
import com.mouchina.server.api.user.UserRelationService;

@Service("userRelationService")
public class UserRelationServiceImpl implements UserRelationService{

	@Autowired
	private UserRelationMapper userRelationMapper;
	
	@Override
	public UserRelation findByPrimaryKey(Long pk) {
		return userRelationMapper.selectByPrimaryKey(pk);
	}

	@Override
	public boolean save(UserRelation t) {
		if(t != null){
			return userRelationMapper.insert(t) == 1;
		}
		return false;
	}

	@Override
	public boolean saveBySelective(UserRelation t) {
		if(t != null){
			return userRelationMapper.insertSelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		return userRelationMapper.deleteByPrimaryKey(pk) == 1;
	}

	@Override
	public boolean updateByPrimaryKeySelective(UserRelation t) {
		if(t != null){
			return userRelationMapper.updateByPrimaryKeySelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(UserRelation t) {
		if(t != null){
			return userRelationMapper.updateByPrimaryKey(t) == 1;
		}
		return false;
	}

	@Override
	public List<UserRelation> findList(String order,UserRelation t) {
		UserRelationExample example = new UserRelationExample();
		Criteria c = example.createCriteria();
		if(StringUtils.isNotEmpty(order)){
			example.setOrderByClause(order);
		}
		if(t != null){
			
		}
		return userRelationMapper.selectByExample(example);
	}

	@Override
	public List<UserRelation> findPageList(int currentPage, int pageSize, String order,UserRelation t) {
		RowBounds row = new RowBounds((currentPage - 1) * pageSize, pageSize);
		UserRelationExample example = new UserRelationExample();
		Criteria c = example.createCriteria();
		if(StringUtils.isNotEmpty(order)){
			example.setOrderByClause(order);
		}
		if(t != null){
			
		}
		
		return userRelationMapper.selectByExampleWithRowbounds(example, row);
	}

	@Override
	public List<Map<String, Object>> findMineFanOrInterestUsers(String order, String loginKey, Integer type,
			int currentPage, int pageSize) {
		RowBounds row = new RowBounds((currentPage - 1) * pageSize, pageSize);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("order", order);
		map.put("loginKey", loginKey);
		map.put("type", type);
		
		return userRelationMapper.findMineFanOrInterestUsers(map, row);
	}

	@Override
	public int findMineFanOrInterestUsersCount(String loginKey, Integer type) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loginKey", loginKey);
		map.put("type", type);
		return userRelationMapper.findMineFanOrInterestUsersCount(map);
	}

	@Override
	public boolean saveFanRelation(Long dataId, Long userAid, Long userBid, byte type, boolean twoWay) {
		
		UserRelation relationA = new UserRelation();
		relationA.setDataId(dataId);
		relationA.setRelationA(userAid);
		relationA.setRelationB(userBid);
		relationA.setCreateTime(new Date());
		relationA.setRelationType(UserRelation.RELATION_TYPE_0);
		relationA.setType(type);
		boolean result = false;
		if(result = (userRelationMapper.insert(relationA) == 1) && twoWay){
			UserRelation relationB = new UserRelation();
			relationB.setDataId(dataId);
			relationB.setRelationA(userBid);
			relationB.setRelationB(userAid);
			relationB.setCreateTime(new Date());
			relationB.setRelationType(UserRelation.RELATION_TYPE_1);
			relationB.setType(type);
			return userRelationMapper.insert(relationB) == 1;
		}
		
		return result;
	}

	@Override
	public boolean isFan(Long userAid, Long userBid, byte type) {
		UserRelationExample example = new UserRelationExample();
		Criteria c = example.createCriteria();
		c.andRelationAEqualTo(userAid);
		c.andRelationBEqualTo(userBid);
		c.andTypeEqualTo(type);
		List<UserRelation> list = userRelationMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

}
