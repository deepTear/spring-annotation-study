package com.spboot.factory;

import org.springframework.beans.factory.FactoryBean;

import com.spboot.service.UserAccountService;
import com.spboot.service.UserAccountServiceImpl;

public class UserAccountServiceFactoryBean implements FactoryBean<UserAccountService>{

	public UserAccountService getObject() throws Exception {
		return new UserAccountServiceImpl();
	}

	public Class<?> getObjectType() {
		return UserAccountService.class;
	}

	public boolean isSingleton() {
		return false;
	}



}
