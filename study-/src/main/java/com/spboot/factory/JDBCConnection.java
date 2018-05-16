package com.spboot.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class JDBCConnection implements InitializingBean,DisposableBean{

	private String url;
	
	private String username;
	
	private String password;
	
	public void destroy() throws Exception {
		System.out.println("destroy propertity");
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("set propertity");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "JDBCConnection [url=" + url + ", username=" + username + ", password=" + password + "]";
	}
	
	

}
