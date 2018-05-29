package com.mouchina.server.api.order;

import com.mouchina.entity.pojo.order.OrderPay;
import com.mouchina.server.api.base.BaseService;

/**
* @ClassName: OrderPayService
* @Description: 支付订单
* @author yaochenglong
* @date 2017年3月16日 下午2:16:56
*/  
public interface OrderPayService extends BaseService<OrderPay,Long>{
	
	/**
	 * 
	 * 通过支付订单号查询支付订单
	 * @param payNo
	 * @return
	 */
	public OrderPay findOrderPayByPayNo(String payNo);
	
	
	//public OrderPay createOrderPay(String payNo);
}
