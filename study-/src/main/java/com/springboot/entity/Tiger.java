package com.springboot.entity;

import org.springframework.stereotype.Component;

@Component
public class Tiger {

	private String name;
	
	private Integer legs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLegs() {
		return legs;
	}

	public void setLegs(Integer legs) {
		this.legs = legs;
	}

	@Override
	public String toString() {
		return "Tiger [name=" + name + ", legs=" + legs + "]";
	}
	
	
}
