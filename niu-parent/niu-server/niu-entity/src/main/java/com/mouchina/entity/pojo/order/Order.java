package com.mouchina.entity.pojo.order;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Order implements Serializable {
	
	/** 未支付 */
	public static final int ORDER_UNPAY = 100;
	/** 支付成功 */
	public static final int ORDER_SUCCESS = 200;
	/** 支付失败 */
	public static final int ORDER_FAILD = 300;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Long id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.order_no
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private String orderNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.title
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private String title;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.pay_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private String payNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.order_desc
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private String orderDesc;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.order_bak
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private String orderBak;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.order_state
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer orderState;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.goods_count
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer goodsCount;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.goods_total_count
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer goodsTotalCount;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.create_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.modify_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.finshi_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finshiTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.pay_source
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Byte paySource;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.user_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Long userId;

	private Long orderAddressId;

	public Long getOrderAddressId() {
		return orderAddressId;
	}

	public void setOrderAddressId(Long orderAddressId) {
		this.orderAddressId = orderAddressId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.provider_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer providerId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.original_pirce
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer originalPirce;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.deal_price
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer dealPrice;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.discount_price
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer discountPrice;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.order_type
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer orderType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.coupon_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer cashCouponId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column orders_2015.integral_value
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private Integer integralValue;

	private String tableNum;

	private Integer cashCouponDeductPrice;
	private Integer integrationDeductPrice;

	private String coursePublicSnapshotId;

	private String userName;

	private Integer  reimbursePrice;
	
	public Integer getReimbursePrice() {
		return reimbursePrice;
	}

	public void setReimbursePrice(Integer reimbursePrice) {
		this.reimbursePrice = reimbursePrice;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIntegrationDeductPrice() {
		return integrationDeductPrice;
	}

	public void setIntegrationDeductPrice(Integer integrationDeductPrice) {
		this.integrationDeductPrice = integrationDeductPrice;
	}

	public String getCoursePublicSnapshotId() {
		return coursePublicSnapshotId;
	}

	public void setCoursePublicSnapshotId(String coursePublicSnapshotId) {
		this.coursePublicSnapshotId = coursePublicSnapshotId;
	}

	public Integer getCashCouponDeductPrice() {
		return cashCouponDeductPrice;
	}

	public void setCashCouponDeductPrice(Integer cashCouponDeductPrice) {
		this.cashCouponDeductPrice = cashCouponDeductPrice;
	}

	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database table orders_2015
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	public Integer getCashCouponId() {
		return cashCouponId;
	}

	public void setCashCouponId(Integer cashCouponId) {
		this.cashCouponId = cashCouponId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.id
	 * 
	 * @return the value of orders_2015.id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.id
	 * 
	 * @param id
	 *            the value for orders_2015.id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.order_no
	 * 
	 * @return the value of orders_2015.order_no
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.order_no
	 * 
	 * @param orderNo
	 *            the value for orders_2015.order_no
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.title
	 * 
	 * @return the value of orders_2015.title
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.title
	 * 
	 * @param title
	 *            the value for orders_2015.title
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.pay_id
	 * 
	 * @return the value of orders_2015.pay_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public String getPayNo() {
		return payNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.pay_id
	 * 
	 * @param PayNo
	 *            the value for orders_2015.pay_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setPayNo(String payNo) {
		this.payNo = payNo == null ? null : payNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.order_desc
	 * 
	 * @return the value of orders_2015.order_desc
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public String getOrderDesc() {
		return orderDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.order_desc
	 * 
	 * @param orderDesc
	 *            the value for orders_2015.order_desc
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc == null ? null : orderDesc.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.order_bak
	 * 
	 * @return the value of orders_2015.order_bak
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public String getOrderBak() {
		return orderBak;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.order_bak
	 * 
	 * @param orderBak
	 *            the value for orders_2015.order_bak
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setOrderBak(String orderBak) {
		this.orderBak = orderBak == null ? null : orderBak.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.goods_count
	 * 
	 * @return the value of orders_2015.goods_count
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getGoodsCount() {
		return goodsCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.goods_count
	 * 
	 * @param goodsCount
	 *            the value for orders_2015.goods_count
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.goods_total_count
	 * 
	 * @return the value of orders_2015.goods_total_count
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getGoodsTotalCount() {
		return goodsTotalCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.goods_total_count
	 * 
	 * @param goodsTotalCount
	 *            the value for orders_2015.goods_total_count
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setGoodsTotalCount(Integer goodsTotalCount) {
		this.goodsTotalCount = goodsTotalCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.create_time
	 * 
	 * @return the value of orders_2015.create_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.create_time
	 * 
	 * @param createTime
	 *            the value for orders_2015.create_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.modify_time
	 * 
	 * @return the value of orders_2015.modify_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.modify_time
	 * 
	 * @param modifyTime
	 *            the value for orders_2015.modify_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.finshi_time
	 * 
	 * @return the value of orders_2015.finshi_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Date getFinshiTime() {
		return finshiTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.finshi_time
	 * 
	 * @param finshiTime
	 *            the value for orders_2015.finshi_time
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setFinshiTime(Date finshiTime) {
		this.finshiTime = finshiTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.pay_source
	 * 
	 * @return the value of orders_2015.pay_source
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Byte getPaySource() {
		return paySource;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.pay_source
	 * 
	 * @param paySource
	 *            the value for orders_2015.pay_source
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setPaySource(Byte paySource) {
		this.paySource = paySource;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.provider_id
	 * 
	 * @return the value of orders_2015.provider_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getProviderId() {
		return providerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.provider_id
	 * 
	 * @param providerId
	 *            the value for orders_2015.provider_id
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.original_pirce
	 * 
	 * @return the value of orders_2015.original_pirce
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getOriginalPirce() {
		return originalPirce;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.original_pirce
	 * 
	 * @param originalPirce
	 *            the value for orders_2015.original_pirce
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setOriginalPirce(Integer originalPirce) {
		this.originalPirce = originalPirce;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.deal_price
	 * 
	 * @return the value of orders_2015.deal_price
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getDealPrice() {
		return dealPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.deal_price
	 * 
	 * @param dealPrice
	 *            the value for orders_2015.deal_price
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setDealPrice(Integer dealPrice) {
		this.dealPrice = dealPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.discount_price
	 * 
	 * @return the value of orders_2015.discount_price
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.discount_price
	 * 
	 * @param discountPrice
	 *            the value for orders_2015.discount_price
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setDiscountPrice(Integer discountPrice) {
		this.discountPrice = discountPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.order_type
	 * 
	 * @return the value of orders_2015.order_type
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.order_type
	 * 
	 * @param orderType
	 *            the value for orders_2015.order_type
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column orders_2015.integral_value
	 * 
	 * @return the value of orders_2015.integral_value
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public Integer getIntegralValue() {
		return integralValue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column orders_2015.integral_value
	 * 
	 * @param integralValue
	 *            the value for orders_2015.integral_value
	 * 
	 * @mbggenerated Mon Jun 15 13:12:26 CST 2015
	 */
	public void setIntegralValue(Integer integralValue) {
		this.integralValue = integralValue;
	}
}