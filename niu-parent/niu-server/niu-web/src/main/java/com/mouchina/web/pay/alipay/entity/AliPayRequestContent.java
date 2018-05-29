package com.mouchina.web.pay.alipay.entity;

import java.io.Serializable;

public class AliPayRequestContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String out_trade_no;
	
	private double total_amount;
	
	private String subject;
	
	private String buyer_id;

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	
	
}
