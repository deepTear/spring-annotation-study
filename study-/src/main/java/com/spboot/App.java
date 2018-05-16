package com.spboot;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import com.spboot.config.BeanConfig;
import com.spboot.controller.UserController;
import com.spboot.entity.Advert;

@ComponentScan(basePackages="com.spboot",excludeFilters={@Filter(type=FilterType.ASSIGNABLE_TYPE,classes=BeanConfig.class)})
public class App {

	public static void main(String[] args) {
		
		//AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserController.class,UserAccountServiceImpl.class);
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
		
		//context.register(BeanConfig.class);
		
		//context.getBean(UserController.class).getUserInfo();
		
		context.getBean(UserController.class).getUserAccount();
		
		context.getBean(UserController.class).otherMethod();
		
		System.out.println(context.getBean(Advert.class));
		
		//System.out.println(context.getBean(JDBCConnection.class));
		
		context.close();
	}
}
