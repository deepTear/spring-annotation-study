package com.mouchina.server.provider.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.dao.user.UserAccountMapper;
import com.mouchina.entity.pojo.user.UserAccount;
import com.mouchina.server.api.user.UserAccountService;

@Service("userAccountService")
public class UserAccountServiceImpl  implements UserAccountService{

	@Autowired
	private UserAccountMapper userAccountMapper;
	
	@Override
	public UserAccount findByPrimaryKey(Long pk) {
		// TODO Auto-generated method stub
		return userAccountMapper.selectByPrimaryKey(pk);
	}

	@Override
	public boolean save(UserAccount t) {
		if(t != null){
			return userAccountMapper.insert(t) == 1;
		}
		return false;
	}

	@Override
	public boolean saveBySelective(UserAccount t) {
		// TODO Auto-generated method stub
		return userAccountMapper.insertSelective(t) == 1;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		// TODO Auto-generated method stub
		return userAccountMapper.deleteByPrimaryKey(pk) == 1;
	}

	@Override
	public boolean updateByPrimaryKeySelective(UserAccount t) {
		// TODO Auto-generated method stub
		return userAccountMapper.updateByPrimaryKey(t) == 1;
	}

	@Override
	public boolean updateByPrimaryKey(UserAccount t) {
		// TODO Auto-generated method stub
		return userAccountMapper.updateByPrimaryKeySelective(t) == 1;
	}

	//TODO
	@Override
	public List<UserAccount> findList(String order,UserAccount t) {
		// TODO Auto-generated method stub
		return null;
	}

	//TODO
	@Override
	public List<UserAccount> findPageList(int currentPage, int pageSize, String order,UserAccount t) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
