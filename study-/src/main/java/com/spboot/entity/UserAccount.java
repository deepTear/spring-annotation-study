package com.spboot.entity;

public class UserAccount {

	private Integer balance;
	
	private Integer score;

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "UserAccount [balance=" + balance + ", score=" + score + "]";
	}
	
	
}
