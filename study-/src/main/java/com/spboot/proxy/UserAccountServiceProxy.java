package com.spboot.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.spboot.service.UserAccountService;
import com.spboot.service.UserAccountServiceImpl;

public class UserAccountServiceProxy{

	private static UserAccountService userAccountService;
	
	public static UserAccountService getUserServiceProxy(){
		if(userAccountService == null){
			userAccountService = (UserAccountService)Proxy.newProxyInstance(UserAccountServiceProxy.class.getClassLoader(), new Class[]{UserAccountService.class}, new UserAccountServiceInvocationHandler());
		}
		return userAccountService;
	}
	
	
	static class UserAccountServiceInvocationHandler implements InvocationHandler{

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("------------------in proxy-------------------");
			UserAccountServiceImpl source = new UserAccountServiceImpl();
			if(method.getName().equals("findUserAccount")){
				System.out.println("----------------qian----------------");
			}
			
			Object p = method.invoke(source, args);
			
			if(method.getName().equals("findUserAccount")){
				System.out.println("----------------hou----------------");
			}
			return p;
		}
		
	}

}
