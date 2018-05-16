package com.spboot.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.spboot.entity.UserRelation;
import com.spboot.proxy.UserAccountServiceProxy;
import com.spboot.service.UserAccountService;

@Component
public class BeanPost implements BeanPostProcessor{

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanClass:" + bean.getClass() + "---------beanName:" + beanName);
		if(bean instanceof UserRelation){
			System.out.println("--------------userRelation bean init before-----------------");
		}
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof UserRelation){
			System.out.println("--------------userRelation bean init after-----------------");
		}
		if(bean instanceof UserAccountService){
			System.out.println("----------------create UserAccountService proxy-------------------");
			UserAccountService service = UserAccountServiceProxy.getUserServiceProxy();
			System.out.println(service.getClass().getName());
			return service;
		}
		return bean;
	}
}
