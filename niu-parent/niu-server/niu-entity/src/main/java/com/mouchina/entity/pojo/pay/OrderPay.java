package com.mouchina.entity.pojo.pay;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单支付信息
 * @author Cris
 *
 */
public class OrderPay implements Serializable {
	
	/**1未支付  */
	public static final byte ORDER_PAY_UNPAY=1;
	
	/**2支付成功 */
	public static final byte ORDER_PAY_SUCCESS=2;
	
	/** 3支付失败   */
	public static final byte ORDER_PAY_FAILD=3;
	
	/** 4用户取消  '*/
	public static final byte ORDER_PAY_CANCEL=4;
	
	/**5支付完成 通知失败   */
	public static final byte ORDER_PAY_FINISHED=5;
	
	/** 6退款申请*/
	public static final byte ORDER_PAY_RETURN_APPLY=6;
	
	/**7退款完成 */
	public static final byte ORDER_PAY_APPLY_SUCCESS=7;
	
	
	/** ORDER_PAY_TYPE_ALIPAY  支付宝支付*/
	public static final byte ORDER_PAY_TYPE_ALIPAY=1;
	
	/** ORDER_PAY_TYPE_WEIXIN 微信支付*/
	public static final byte ORDER_PAY_TYPE_WEIXIN=2;
	
	/** ORDER_PAY_TYPE_零钱支付 */
	public static final byte ORDER_PAY_TYPE_CHANGE=3;
	
	/** ORDER_PAY_TYPE_ADMIN 管理员支付*/
	public static final byte ORDER_PAY_TYPE_ADMIN=4;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3534878222643462064L;
	
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 支付金额
     */
    private Integer payPrice;
    /**
     * 支付总额
     */
    private Integer paySumPrice;
    /**
     * 抵现金额
     */
    private Integer exchangePrice;
    /**
     * 使用余额
     */
    private Integer useBalancePrice;
    /**
     * 支付流水号
     */
    private String payNo;
    /**
     * 订单流水号
     */
    private String orderNo;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 提供者id
     */
    private Integer provideId;
    /**
     * 支付渠道  1android 2ios
     */
    private Byte payChannel;
    /**
     * 支付方式  1 支付宝 2微信 3 余额 4管理员
     */
    private Byte payWay;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;
    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date finshiTime;
    /**
     * 状态 1未支付  2支付成功 3支付失败  4用户取消 5支付完成 通知失败  6退款申请 7退款完成
     */
    private Byte payState;
    /**
     * 第三方支付流水号
     */
    private String thirdPartyPayNo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Integer payPrice) {
        this.payPrice = payPrice;
    }

    public Integer getPaySumPrice() {
        return paySumPrice;
    }

    public void setPaySumPrice(Integer paySumPrice) {
        this.paySumPrice = paySumPrice;
    }

    public Integer getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(Integer exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public Integer getUseBalancePrice() {
        return useBalancePrice;
    }

    public void setUseBalancePrice(Integer useBalancePrice) {
        this.useBalancePrice = useBalancePrice;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProvideId() {
        return provideId;
    }

    public void setProvideId(Integer provideId) {
        this.provideId = provideId;
    }

    public Byte getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Byte payChannel) {
        this.payChannel = payChannel;
    }

    public Byte getPayWay() {
        return payWay;
    }

    public void setPayWay(Byte payWay) {
        this.payWay = payWay;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getFinshiTime() {
        return finshiTime;
    }

    public void setFinshiTime(Date finshiTime) {
        this.finshiTime = finshiTime;
    }

    public Byte getPayState() {
        return payState;
    }

    public void setPayState(Byte payState) {
        this.payState = payState;
    }

    public String getThirdPartyPayNo() {
        return thirdPartyPayNo;
    }

    public void setThirdPartyPayNo(String thirdPartyPayNo) {
        this.thirdPartyPayNo = thirdPartyPayNo == null ? null : thirdPartyPayNo.trim();
    }
    
}