package com.spboot.service;

import org.springframework.stereotype.Service;

import com.spboot.entity.UserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService{

	public UserAccount findUserAccount() {
		System.out.println(this.getClass().getName());
		UserAccount u = new UserAccount();
		u.setBalance(1000);
		u.setScore(500);
		System.out.println(u.getClass() + "---" + u);
		return u;
	}
	
	public void otherMethod() {
		System.out.println("----------------------otherMethod--------------------");
	}

}
