package com.spboot.factory;

import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import com.spboot.entity.Advert;

@Component
public class DynamicInjectBean implements BeanDefinitionRegistryPostProcessor{

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(Advert.class);
		bdb.addPropertyValue("name", "zhangsan");
		bdb.addPropertyValue("createTime", new Date());
		registry.registerBeanDefinition("advert", bdb.getBeanDefinition());
	}

}
