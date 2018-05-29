package com.mouchina.server.provider.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.base.redis.RedisHelper;
import com.mouchina.base.utils.UUIDGenerator;
import com.mouchina.dao.user.UserAccountMapper;
import com.mouchina.dao.user.UserInfoMapper;
import com.mouchina.entity.pojo.user.UserAccount;
import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.entity.pojo.user.UserInfoExample;
import com.mouchina.entity.pojo.user.UserInfoExample.Criteria;
import com.mouchina.server.api.user.UserInfoService;


@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserAccountMapper userAccountMapper;
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public UserInfo findByPrimaryKey(Long pk) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectByPrimaryKey(pk);
	}

	@Override
	public boolean save(UserInfo t) {
		if(t != null){
			//String loginKey = SHA1.hex_sha1(UUIDGenerator.getUUID());
			//t.setToken(loginKey);
			t.setCreateTime(new Date());
			t.setModifyTime(new Date());
			t.setLastLoginTime(new Date());
			t.setState(UserInfo.USER_STATE_0);
			
			String inviteCode = UUIDGenerator.getGlobalUniqueInviteCode(redisHelper);
			t.setInviteCode(inviteCode);
			Long id = this.createUserUniqueId();
			t.setId(id);
			if(userInfoMapper.insert(t) == 1){
				UserAccount account = new UserAccount();
				account.setAdcoinBalance(0L);
				account.setBalance(0L);
				account.setCreateTime(new Date());
				account.setModifyTime(new Date());
				account.setUserId(id);
				account.setScore(0);
				account.setVersion(0);
				return userAccountMapper.insert(account) == 1;
			}
		}
		
		return false;
	}

	@Override
	public boolean saveBySelective(UserInfo t) {
		// TODO Auto-generated method stub
		return userInfoMapper.insertSelective(t) == 1;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		// TODO Auto-generated method stub
		return userInfoMapper.deleteByPrimaryKey(pk) == 1;
	}


	@Override
	public boolean updateByPrimaryKeySelective(UserInfo t) {
		// TODO Auto-generated method stub
		return userInfoMapper.updateByPrimaryKeySelective(t) == 1;
	}

	@Override
	public boolean updateByPrimaryKey(UserInfo t) {
		// TODO Auto-generated method stub
		return userInfoMapper.updateByPrimaryKey(t) == 1;
	}

	@Override
	public List<UserInfo> findList(String order,UserInfo t) {
		UserInfoExample example = new UserInfoExample();
		Criteria criteria = example.createCriteria();
		if(t != null){
			String phone = t.getPhone();
			criteria.andPhoneEqualTo(phone);
		}
		return userInfoMapper.selectByExample(example);
	}

	@Override
	public List<UserInfo> findPageList(int currentPage, int pageSize,String order, UserInfo t) {
		RowBounds row = new RowBounds((currentPage - 1) * pageSize, pageSize);
		UserInfoExample example = new UserInfoExample();
		Criteria criteria = example.createCriteria();
		if(t != null){
			String loginKey = t.getToken();
			
		}
		return userInfoMapper.selectByExampleWithRowbounds(example, row);
	}

	@Override
	public Long createUserUniqueId() {
		Long id = 0L;
		while (true) {
            Long userId = UUIDGenerator.userIdGenerator(redisHelper);
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
            if (userInfo == null) {
                id = userId;
                break;
            }
        }
		return id;
	}

	@Override
	public UserInfo findUserByPhone(String phone, boolean valid) {
		UserInfo userInfo = new UserInfo();
		userInfo.setPhone(phone);
		userInfo.setState(valid ? UserInfo.USER_STATE_0 : UserInfo.USER_STATE_1);
		
		RowBounds row = new RowBounds(0, 1);
		UserInfoExample example = new UserInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<UserInfo> list= userInfoMapper.selectByExampleWithRowbounds(example, row);
		
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public UserInfo findUserByToken(String token, boolean valid) {
		UserInfo userInfo = new UserInfo();
		userInfo.setToken(token);
		userInfo.setState(valid ? UserInfo.USER_STATE_0 : UserInfo.USER_STATE_1);
		
		RowBounds row = new RowBounds(0, 1);
		UserInfoExample example = new UserInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andTokenEqualTo(token);
		List<UserInfo> list= userInfoMapper.selectByExampleWithRowbounds(example, row);
		
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public UserInfo getUserInfoByAttr(String attr, Object val) {
		// TODO Auto-generated method stub
        Map<String, Object> mapSql = new HashMap<>();
        mapSql.put(attr, val);
        return userInfoMapper.selectModel(mapSql);
	}

	@Override
	public UserInfo getUserInfo(Map<String, Object> map) {
		 return userInfoMapper.selectModel(map);
	}

	@Override
	public Boolean updateUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
        return userInfoMapper.updateByPrimaryKeySelective(userInfo) > 0 ? true : false;
	}

	
}
