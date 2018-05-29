package com.mouchina.entity.pojo.order;

import java.io.Serializable;
import java.util.Date;

public class OrderRecord implements Serializable {
	
	public final static byte ORDER_RECORD_STATE_0 = 0;
	public final static byte ORDER_RECORD_STATE_1 = 1;
	public final static byte ORDER_RECORD_STATE_2 = 2;
	public final static byte ORDER_RECORD_CLIENT_STATE_0 = 0;
	public final static byte ORDER_RECORD_CLIENT_STATE_1 = 1;
	public final static byte ORDER_RECORD_DEAL_TYPE_0 = 0;
	public final static byte ORDER_RECORD_DEAL_TYPE_1 = 1;
	public final static byte ORDER_RECORD_DEAL_TYPE_2 = 2;
	
    /** */
    private Long id;

    /** 订单类型 0:广告币充值*/
    private Byte orderType;

    /** 订单流水号*/
    private String orderNo;

    /** 支付订单号*/
    private String payOrderNo;

    /** 标题*/
    private String title;

    /** 状态（0：新建未支付  1：支付成功 2：支付失败）*/
    private Byte orderState;

    /** 创建时间*/
    private Date createTime;

    /** 修改时间*/
    private Date modifyTime;

    /** 完成时间*/
    private Date finishTime;

    /** 订单来源*/
    private Byte orderSource;

    /** 用户ID*/
    private Long userId;

    /** 商品源ID*/
    private Long sourceId;

    /** 商品数量*/
    private Integer goodsCount;

    /** 内容*/
    private String content;

    /** 原始总金额*/
    private Integer originalPrice;

    /** 成交总金额*/
    private Integer actualPrice;

    /** 折扣百分比*/
    private Integer discount;

    /** 客户端状态（0：正常  1：客户端删除）*/
    private Byte clientState;

    /** 数据值1*/
    private String data1;

    /** 数据值2*/
    private String data2;

    /** 优惠券ID*/
    private Long couponId;

    /** 成交金额类型（0：全额  1：折扣  2：代金券）*/
    private Byte dealType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_order_record
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.id
     *
     * @return the value of tb_order_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.id
     *
     * @param id the value for tb_order_record.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.order_type
     *
     * @return the value of tb_order_record.order_type
     *
     * @mbggenerated
     */
    public Byte getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.order_type
     *
     * @param orderType the value for tb_order_record.order_type
     *
     * @mbggenerated
     */
    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.order_no
     *
     * @return the value of tb_order_record.order_no
     *
     * @mbggenerated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.order_no
     *
     * @param orderNo the value for tb_order_record.order_no
     *
     * @mbggenerated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.pay_order_no
     *
     * @return the value of tb_order_record.pay_order_no
     *
     * @mbggenerated
     */
    public String getPayOrderNo() {
        return payOrderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.pay_order_no
     *
     * @param payOrderNo the value for tb_order_record.pay_order_no
     *
     * @mbggenerated
     */
    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.title
     *
     * @return the value of tb_order_record.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.title
     *
     * @param title the value for tb_order_record.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.order_state
     *
     * @return the value of tb_order_record.order_state
     *
     * @mbggenerated
     */
    public Byte getOrderState() {
        return orderState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.order_state
     *
     * @param orderState the value for tb_order_record.order_state
     *
     * @mbggenerated
     */
    public void setOrderState(Byte orderState) {
        this.orderState = orderState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.create_time
     *
     * @return the value of tb_order_record.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.create_time
     *
     * @param createTime the value for tb_order_record.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.modify_time
     *
     * @return the value of tb_order_record.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.modify_time
     *
     * @param modifyTime the value for tb_order_record.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.finish_time
     *
     * @return the value of tb_order_record.finish_time
     *
     * @mbggenerated
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.finish_time
     *
     * @param finishTime the value for tb_order_record.finish_time
     *
     * @mbggenerated
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.order_source
     *
     * @return the value of tb_order_record.order_source
     *
     * @mbggenerated
     */
    public Byte getOrderSource() {
        return orderSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.order_source
     *
     * @param orderSource the value for tb_order_record.order_source
     *
     * @mbggenerated
     */
    public void setOrderSource(Byte orderSource) {
        this.orderSource = orderSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.user_id
     *
     * @return the value of tb_order_record.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.user_id
     *
     * @param userId the value for tb_order_record.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.source_id
     *
     * @return the value of tb_order_record.source_id
     *
     * @mbggenerated
     */
    public Long getSourceId() {
        return sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.source_id
     *
     * @param sourceId the value for tb_order_record.source_id
     *
     * @mbggenerated
     */
    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.goods_count
     *
     * @return the value of tb_order_record.goods_count
     *
     * @mbggenerated
     */
    public Integer getGoodsCount() {
        return goodsCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.goods_count
     *
     * @param goodsCount the value for tb_order_record.goods_count
     *
     * @mbggenerated
     */
    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.content
     *
     * @return the value of tb_order_record.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.content
     *
     * @param content the value for tb_order_record.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.original_price
     *
     * @return the value of tb_order_record.original_price
     *
     * @mbggenerated
     */
    public Integer getOriginalPrice() {
        return originalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.original_price
     *
     * @param originalPrice the value for tb_order_record.original_price
     *
     * @mbggenerated
     */
    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.actual_price
     *
     * @return the value of tb_order_record.actual_price
     *
     * @mbggenerated
     */
    public Integer getActualPrice() {
        return actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.actual_price
     *
     * @param actualPrice the value for tb_order_record.actual_price
     *
     * @mbggenerated
     */
    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.discount
     *
     * @return the value of tb_order_record.discount
     *
     * @mbggenerated
     */
    public Integer getDiscount() {
        return discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.discount
     *
     * @param discount the value for tb_order_record.discount
     *
     * @mbggenerated
     */
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.client_state
     *
     * @return the value of tb_order_record.client_state
     *
     * @mbggenerated
     */
    public Byte getClientState() {
        return clientState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.client_state
     *
     * @param clientState the value for tb_order_record.client_state
     *
     * @mbggenerated
     */
    public void setClientState(Byte clientState) {
        this.clientState = clientState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.data1
     *
     * @return the value of tb_order_record.data1
     *
     * @mbggenerated
     */
    public String getData1() {
        return data1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.data1
     *
     * @param data1 the value for tb_order_record.data1
     *
     * @mbggenerated
     */
    public void setData1(String data1) {
        this.data1 = data1 == null ? null : data1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.data2
     *
     * @return the value of tb_order_record.data2
     *
     * @mbggenerated
     */
    public String getData2() {
        return data2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.data2
     *
     * @param data2 the value for tb_order_record.data2
     *
     * @mbggenerated
     */
    public void setData2(String data2) {
        this.data2 = data2 == null ? null : data2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.coupon_id
     *
     * @return the value of tb_order_record.coupon_id
     *
     * @mbggenerated
     */
    public Long getCouponId() {
        return couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.coupon_id
     *
     * @param couponId the value for tb_order_record.coupon_id
     *
     * @mbggenerated
     */
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_record.deal_type
     *
     * @return the value of tb_order_record.deal_type
     *
     * @mbggenerated
     */
    public Byte getDealType() {
        return dealType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_record.deal_type
     *
     * @param dealType the value for tb_order_record.deal_type
     *
     * @mbggenerated
     */
    public void setDealType(Byte dealType) {
        this.dealType = dealType;
    }
}