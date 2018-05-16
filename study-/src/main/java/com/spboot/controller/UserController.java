package com.spboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.spboot.entity.UserAccount;
import com.spboot.entity.UserInfo;
import com.spboot.service.UserAccountService;
import com.spboot.service.UserService;

@Controller
public class UserController {

	private UserService userService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	public UserController(){}
	
	public UserController(UserService userService,UserAccountService userAccountService){
		this.userService = userService;
		this.userAccountService = userAccountService;
	}
	
	public String getUserInfo(){
		UserInfo u = userService.findUserInfo();
		System.out.println(u.getClass() + "---" + u);
		return "";
	}
	
	public String getUserAccount(){
		userAccountService.findUserAccount();
		return "";
	}
	
	public String otherMethod(){
		userAccountService.otherMethod();
		return "";
	}
}
