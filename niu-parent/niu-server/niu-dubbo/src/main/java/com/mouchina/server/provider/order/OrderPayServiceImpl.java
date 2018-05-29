package com.mouchina.server.provider.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.dao.order.OrderPayMapper;
import com.mouchina.entity.pojo.order.OrderPay;
import com.mouchina.entity.pojo.order.OrderPayExample;
import com.mouchina.entity.pojo.order.OrderPayExample.Criteria;
import com.mouchina.server.api.order.OrderPayService;

/**
 * 支付订单的CURD <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2017年3月16日 下午2:24:29
 * <p>
 * Company: 西安哞哞信息科技有限公司
 * <p>
 * @author yaochenglong@mouchina.com
 * @version 4.0.0
 */

@Service("orderPayService")
public class OrderPayServiceImpl implements OrderPayService{

	@Autowired
	private OrderPayMapper orderPayMapper;
	
	@Override
	public OrderPay findByPrimaryKey(Long pk) {
		return orderPayMapper.selectByPrimaryKey(pk);
	}

	@Override
	public boolean save(OrderPay t) {
		if(t != null){
			return orderPayMapper.insert(t) == 1;
		}
		return false;
	}

	@Override
	public boolean saveBySelective(OrderPay t) {
		if(t != null){
			return orderPayMapper.insertSelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		return orderPayMapper.deleteByPrimaryKey(pk) == 1;
	}

	@Override
	public boolean updateByPrimaryKeySelective(OrderPay t) {
		if(t != null){
			return orderPayMapper.updateByPrimaryKeySelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(OrderPay t) {
		if(t != null){
			return orderPayMapper.updateByPrimaryKey(t) == 1;
		}
		return false;
	}

	@Override
	public List<OrderPay> findList(String order, OrderPay t) {
		return null;
	}

	@Override
	public List<OrderPay> findPageList(int currentPage, int pageSize, String order, OrderPay t) {
		RowBounds row = new RowBounds((currentPage - 1) * pageSize, pageSize);
		OrderPayExample example = new OrderPayExample();
		Criteria c = example.createCriteria();
		if(StringUtils.isNotEmpty(order)){
			example.setOrderByClause(order);
		}
		
		if(t != null){
			String payNo = t.getPayNo();
			if(StringUtils.isNotEmpty(payNo)){
				c.andPayNoLike("%" + payNo + "%");
			}
		}
		
		return orderPayMapper.selectByExampleWithRowbounds(example, row);
	}

	@Override
	public OrderPay findOrderPayByPayNo(String payNo) {
		
		if(StringUtils.isEmpty(payNo)){
			throw new IllegalArgumentException("支付订单号错误");
		}
		
		OrderPayExample example = new OrderPayExample();
		Criteria c = example.createCriteria();
		c.andPayNoEqualTo(payNo);
		List<OrderPay> list = orderPayMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}

}
