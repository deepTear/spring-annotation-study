package com.spboot.entity;

import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserRelation implements InitializingBean,DisposableBean{

	private Integer a;
	
	private Integer b;
	
	private Date createDate;

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public void destroy() throws Exception {
		System.out.println("--------------userRelation bean destroy-----------------");
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("--------------userRelation bean init-----------------");
	}

	@Override
	public String toString() {
		return "UserRelation [a=" + a + ", b=" + b + ", createDate=" + createDate + "]";
	}
	
	
}
