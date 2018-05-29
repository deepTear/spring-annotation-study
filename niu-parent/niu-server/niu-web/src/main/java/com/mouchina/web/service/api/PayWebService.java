package com.mouchina.web.service.api;

import org.springframework.ui.ModelMap;

import com.mouchina.entity.pojo.order.OrderRecord;

/**
* @ClassName: OrderService
* @Description: TODO
* @author yaochenglong
* @date 2017年3月16日 上午11:11:08
*/  
public interface PayWebService {
	
	
	/**
	 * 创建系统订单，并生成支付签名
	 * @param payChannel   支付渠道  0：微信 1：支付宝
	 * @param detail  订单详情
	 * @param loginKey 订单用户token
	 * @param sourceId 订单源Id
	 * @param type   订单类别   0：余额充值   1：购买广告币
	 * @param originMoney  订单原始总额
	 * @param payType   支付类型   0：全额   1：折扣
	 * @param discount  折扣百分比
	 * @param count   商品数量
	 * @return
	 */
	public void createSysOrderRecordAndGetPaySign(byte payChannel,String title,String detail,String loginKey,Long sourceId,byte type,
			Integer originMoney,byte payType,Integer discount,Integer count,ModelMap modelMap);
	
	
	/**
	 * 创建订单
	 * @param title  订单标题
	 * @param userId 下单用户ID
	 * @param sourceId 下单资源id
	 * @param orderType 订单类型
	 * @param dealType  支付类型   0：全额  1：折扣  2：代金券
	 * @param originalMoney 订单原始金额
	 * @param actualMoney  实际支付金额
	 * @param goodsCount   商品数量
	 * @param discount   订单折扣百分比
	 * @param couponId   代金券ID
	 * @return
	 */
	public OrderRecord createOrderRecord(String title,Long userId,Long sourceId,byte orderType,byte dealType,int originalMoney,int actualMoney,int goodsCount,int discount,Long couponId);
	
	
	/**
	 * 修改系统订单支付状态，以及保存支付订单信息
	 * @param payChannel  支付渠道    0：微信  1：支付宝
	 * @param thirdPartNo  第三方支付单号
	 * @param orderNo   系统订单号
	 * @param actualPayMoney  实际支付金额
	 * @return
	 */
	public boolean createPayOrder(byte payChannel,String thirdPartNo,String orderNo,int actualPayMoney);
	
	
	
	
	
	
	
	/**
	* @Title: createOrder
	* @Description: 用户支付之前需要创建订单：创建订单
	* @param @param modelMap
	* @param @return    参数
	* @return PayResponse    返回类型
	* @throws
	*//*
	    
	public Order createOrder(Order order); 
	
	*//**
	* @Title: updateAlipay
	* @Description: 支付宝回调函数
	* @param @return    参数
	* @return int    返回类型
	* @throws
	*//*
	public int updateAlipay(Map<String,String> map);
	
	*//**
	 * 微信回调函数
	 * @param map
	 * @return
	 *//*
	public int updateWechatpay(WxPayResult wxPayResult);
	
	*//**
	 * 如果支付失败，此方法修添加订单支付失败信息
	 * @param orderNo 订单流水号
	 * @param msg 交易失败信息
	 * @return 
	 *//*
	int updatePayErrorMsg(String orderNo, String msg);
	
	*//**
	 * 支付宝回调函数
	 * 修改订单状态,
	 * @param map
	 * @param orderState
	 *//*
	public void updateOrderStateByOrderNo(String orderNo,int orderState);*/
	
	
}
