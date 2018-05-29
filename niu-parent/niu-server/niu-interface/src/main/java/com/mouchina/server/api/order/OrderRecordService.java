package com.mouchina.server.api.order;

import com.mouchina.entity.pojo.order.OrderRecord;
import com.mouchina.server.api.base.BaseService;

/**
 * 订单记录
 * 
 * @author larry 2015年6月19日下午3:38:15
 */

public interface OrderRecordService extends BaseService<OrderRecord,Long>{

	/**
	 * 通过订单号查询系统订单
	 * @param orderNo
	 * @return
	 */
	public OrderRecord findOrderRecordByUniqueOrderNo(String orderNo);
}
