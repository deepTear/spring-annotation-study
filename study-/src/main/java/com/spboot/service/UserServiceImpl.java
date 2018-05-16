package com.spboot.service;

import org.springframework.stereotype.Service;

import com.spboot.entity.UserInfo;

@Service
public class UserServiceImpl implements UserService{

	public UserInfo findUserInfo() {
		UserInfo u = new UserInfo();
		u.setAddress("shanxi xian");
		u.setName("xijinping");
		return u;
	}

}
