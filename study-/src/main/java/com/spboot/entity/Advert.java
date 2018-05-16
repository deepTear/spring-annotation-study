package com.spboot.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

public class Advert {

	private String name;
	
	private Date createTime;
	
	private String imgPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "Advert [name=" + name + ", createTime=" + createTime + ", imgPath=" + imgPath + "]";
	}
	
	
}
