package com.spboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spboot.controller.UserController;
import com.spboot.factory.JDBCConnection;
import com.spboot.factory.UserAccountServiceFactoryBean;
import com.spboot.service.UserService;
import com.spboot.service.UserServiceImpl;

@Configuration
public class BeanConfig {

	@Bean
	public UserService createUserService(){
		return new UserServiceImpl();
	}
	
	@Bean
	public UserAccountServiceFactoryBean createUserAccountServiceFactoryBean(){
		return new UserAccountServiceFactoryBean();
	}
	
	@Bean
	public UserController createUserController(UserService userService,UserAccountServiceFactoryBean userAccountServiceFactoryBean) throws Exception{
		return new UserController(userService,userAccountServiceFactoryBean.getObject());
	}
	
	@Bean
	public JDBCConnection createJDBCConnection(){
		JDBCConnection conn = new JDBCConnection();
		System.out.println("---------------------");
		conn.setPassword("123");
		System.out.println("++++++++++++++++++++++");
		return conn;
	}
}
