package com.mouchina.server.provider.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.dao.user.UserTopicMapper;
import com.mouchina.entity.pojo.user.UserTopic;
import com.mouchina.entity.pojo.user.UserTopicExample;
import com.mouchina.entity.pojo.user.UserTopicExample.Criteria;
import com.mouchina.server.api.user.UserTopicService;

@Service("userTopicService")
public class UserTopicServiceImpl implements UserTopicService {

	@Autowired
	private UserTopicMapper userTopicMapper;
	
	@Override
	public UserTopic findByPrimaryKey(Long pk) {
		return userTopicMapper.selectByPrimaryKey(pk);
	}

	@Override
	public boolean save(UserTopic t) {
		if(t != null){
			return userTopicMapper.insert(t) == 1;
		}
		return false;
	}

	@Override
	public boolean saveBySelective(UserTopic t) {
		if(t != null){
			return userTopicMapper.insertSelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		return userTopicMapper.deleteByPrimaryKey(pk) == 1;
	}

	@Override
	public boolean updateByPrimaryKeySelective(UserTopic t) {
		if(t != null){
			return userTopicMapper.updateByPrimaryKey(t) == 1;
		}
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(UserTopic t) {
		if(t != null){
			return userTopicMapper.updateByPrimaryKeySelective(t) == 1;
		}
		return false;
	}

	@Override
	public List<UserTopic> findList(String order, UserTopic t) {
		return null;
	}

	@Override
	public List<UserTopic> findPageList(int currentPage, int pageSize, String order, UserTopic t) {
		return null;
	}

	@Override
	public int countPublishTopic(Date date, Long userId, Boolean isFree) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		UserTopicExample example = new UserTopicExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andCreateTimeLike(format.format(date) + "%");
		if(isFree != null && isFree){
			List<Byte> list = new ArrayList<>();
			list.add((byte)1);
			list.add((byte)3);
			criteria.andTypeIn(list);
		}
		return userTopicMapper.countByExample(example);
	}

}
