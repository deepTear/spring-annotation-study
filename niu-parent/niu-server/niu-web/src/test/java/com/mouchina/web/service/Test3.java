package com.mouchina.web.service;

public class Test3 {

	public static void main(String[] args) {
		
		int baseLength = 8;
		
		int baseKey = 1;

		Long sequence = 0L;

		Long invoteCode = 0L;
		
		String inviteCodeStr = "";
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String chars2 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for(int i = 0 ; i < 100000 ; i++,sequence++){
			invoteCode = baseKey + sequence;
			inviteCodeStr = invoteCode + "";
			inviteCodeStr += chars.charAt((int)(Math.random() * 52));
			while(inviteCodeStr.length() < baseLength){
				inviteCodeStr += chars2.charAt((int)(Math.random() * 62));
			}
			
			System.out.println(inviteCodeStr);
		}
		

	}
}
