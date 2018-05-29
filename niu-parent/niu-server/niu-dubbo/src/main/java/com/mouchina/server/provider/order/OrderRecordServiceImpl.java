package com.mouchina.server.provider.order;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.base.redis.RedisHelper;
import com.mouchina.base.redis.RedisLockHandler;
import com.mouchina.base.utils.DateUtils;
import com.mouchina.dao.order.OrderRecordMapper;
import com.mouchina.entity.pojo.order.OrderPay;
import com.mouchina.entity.pojo.order.OrderPayExample;
import com.mouchina.entity.pojo.order.OrderRecord;
import com.mouchina.entity.pojo.order.OrderRecordExample;
import com.mouchina.entity.pojo.order.OrderRecordExample.Criteria;
import com.mouchina.server.api.order.OrderRecordService;
import com.mouchina.server.api.user.UserInfoService;

/**
 * 类说明
 * 
 * @author larry 2015年6月19日下午9:11:00
 */
@Service("orderRecordService")
public class OrderRecordServiceImpl implements OrderRecordService {

	static Logger logger = LogManager.getLogger();
	
	@Resource
	private RedisHelper redisHelper;
	
	@Resource
	private RedisLockHandler redisLockHandler;
	
	@Resource
	UserInfoService userInfoService;
	
	@Autowired
	private OrderRecordMapper orderRecordMapper;
	
	
	public static final String USER_RESOURCE_COURSE_PUBLIC_LOCK_PREFIX = "user_resource_cousre_public_lock_prefix_";
	public static final String ORDER_NO_LOCK_PREFIX = "order_no_lock_prefix_";
	
	
	@Override
	public OrderRecord findByPrimaryKey(Long pk) {
		return orderRecordMapper.selectByPrimaryKey(pk);
	}
	@Override
	public boolean save(OrderRecord t) {
		if(t != null){
			return orderRecordMapper.insert(t) == 1;
		}
		return false;
	}
	@Override
	public boolean saveBySelective(OrderRecord t) {
		if(t != null){
			return orderRecordMapper.insertSelective(t) == 1;
		}
		return false;
	}
	@Override
	public boolean deletePrimaryKey(Long pk) {
		return orderRecordMapper.deleteByPrimaryKey(pk) == 1;
	}
	@Override
	public boolean updateByPrimaryKeySelective(OrderRecord t) {
		if(t != null){
			return orderRecordMapper.updateByPrimaryKeySelective(t) == 1;
		}
		return false;
	}
	@Override
	public boolean updateByPrimaryKey(OrderRecord t) {
		if(t != null){
			return orderRecordMapper.updateByPrimaryKey(t) == 1;
		}
		return false;
	}
	@Override
	public List<OrderRecord> findList(String order, OrderRecord t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<OrderRecord> findPageList(int currentPage, int pageSize, String order, OrderRecord t) {
		RowBounds row = new RowBounds((currentPage - 1) * pageSize, pageSize);
		OrderRecordExample example = new OrderRecordExample();
		Criteria c = example.createCriteria();
		if(StringUtils.isNotEmpty(order)){
			example.setOrderByClause(order);
		}
		
		if(t != null){
			String orderNo = t.getOrderNo();
			if(StringUtils.isNotEmpty(orderNo)){
				c.andOrderNoLike("%" + orderNo + "%");
			}
		}
		
		return orderRecordMapper.selectByExampleWithRowbounds(example, row);
	}
	
	@Override
	public OrderRecord findOrderRecordByUniqueOrderNo(String orderNo) {
		if(StringUtils.isEmpty(orderNo)){
			throw new IllegalArgumentException("订单号 参数错误");
		}
		
		OrderRecordExample example = new OrderRecordExample();
		Criteria c = example.createCriteria();
		c.andOrderNoEqualTo(orderNo);
		List<OrderRecord> list = orderRecordMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}


	/*@Override
	*//**
	 * 根据传入的orderView对象，添加封装好的order和payorder
	 * @param orderView
	 * @return
	 * @throws OrderException
	 *//*
	private OrderResult<OrderView> addOrderView(OrderView orderView) throws OrderException {
		OrderResult<OrderView> orderViewResult = new OrderResult<OrderView>();
		Order order = orderView.getOrder();
		ConsumerMoneyOrder  consumerMoneyOrder = orderView.getConsumerMoneyOrder();
		UserIdentity userIdentity = orderView.getUserIdentity();
		String orderNo = UUIDGenerator.orderNoGenerator(redisHelper);// 生成订单流水号

		List<String> orderBuinessLocks = new ArrayList<String>();
		Date startTime = new Date();
		logger.info("----------------------------------addOrderView start  orderNo= " + orderNo + ",startTime="
				+ startTime);
		String tableNum = getOrderNoTableNum(orderNo);
		logger.info("----------------------------------addOrderView lock  resources start " + orderNo);

		UserInfo user = userInfoService.getUserInfoByAttr("token", userIdentity.getToken());
		order.setOrderNo(orderNo);
		order.setTableNum(tableNum);
		order.setUserName(user.getNickName().equals("") ? user.getPhone() : user.getNickName());
		orderView.setOrder(order);
		
		if(consumerMoneyOrder != null){
			consumerMoneyOrder.setOrderNo(orderNo);
			orderView.setConsumerMoneyOrder(consumerMoneyOrder);
		}

		String orderLock = USER_RESOURCE_COURSE_PUBLIC_LOCK_PREFIX + order.getUserId();
		orderBuinessLocks.add(orderLock);
		try {
			if (redisLockHandler.tryLock(orderLock)) {
				logger.info("----------------------------------addOrderView addOrder  start " + orderNo);
				OrderResult<Order> addOrderResult = addOrderWithReturn(order);// 添加订单对象order
				logger.info("----------------------------------addOrderView addOrder  end " + orderNo);
				if(consumerMoneyOrder != null){
					// 添加订单对象consumerMoneyOrder
					OrderResult<ConsumerMoneyOrder> addConsumerOrderResult = consumerMoneyOrderService.addOrderWithReturn(consumerMoneyOrder);
					if(addConsumerOrderResult.getState() <= 0){
						return null;
					}
				}
				if(addOrderResult.getState() > 0){
					logger.info("----------------------------------addOrderView addPayOrder  start " + orderNo);
					PayResult<OrderPay> payResult = addPayOrder(order, orderNo, orderView);// 添加payOrder
					logger.info("----------------------------------addOrderView addPayOrder  end " + orderNo);

					if(payResult.getState() > 0){
						order.setPayNo(payResult.getContent().getPayNo());
						orderView.setOrder(order);
						orderView.setPayOrder(payResult.getContent());
						orderView.setUserIdentity(orderView.getUserIdentity());
						orderViewResult.setContent(orderView);
						orderViewResult.setState(1);

						orderViewResult.setOrderState(OrderState.SUCCESS);
					}else{
						throw new OrderException(OrderState.USERRESOURCECOURSEPUBLICLOCKED, "order添加成功后添加payOrder出错！");
					}
				}else{
					return null;
				}
			} else {
				throw new OrderException(OrderState.USERRESOURCECOURSEPUBLICLOCKED, "");
			}
		} catch (OrderException e) {
			logger.error("----------------------------------addOrderView process  orderNo" + orderNo
					+ "   OrderException=" + e.toExecptionString());
			orderViewResult.setOrderState(e.getOrderState());
			orderViewResult.setOrderSateContent(e.getOrderSateContent());
			throw new OrderException(e.getOrderState(), e.getOrderSateContent());
		} finally {
			for (String lock : orderBuinessLocks) {
				redisLockHandler.unLock(lock);
			}
		}

		logger.info("----------------------------------addOrderView lock  resources   end " + orderNo);
		Date endTime = new Date();
		logger.info("----------------------------------addOrderView(添加订单order和支付订单orderPay成功) end  orderNo=" + orderNo + ",endtime=" + endTime
				+ ",总共花费时间 count time=" + (endTime.getTime() - startTime.getTime()) + " millisecond");
		return orderViewResult;

	}
	
	
	private OrderResult<OrderView> addOrderViewBdby(OrderView orderView) throws OrderException {
		OrderResult<OrderView> orderViewResult = new OrderResult<OrderView>();
		Order order = orderView.getOrder();
		String orderNo = UUIDGenerator.orderNoGenerator(redisHelper);// 生成订单流水号

		List<String> orderBuinessLocks = new ArrayList<String>();
		Date startTime = new Date();
		logger.info("----------------------------------addOrderView start  orderNo= " + orderNo + ",startTime="
				+ startTime);
		String tableNum = getOrderNoTableNum(orderNo);

		logger.info("----------------------------------addOrderView lock  resources start " + orderNo);

		order.setOrderNo(orderNo);
		order.setTableNum(tableNum);
		order.setUserName(order.getUserName());

		orderView.setOrder(order);

		String orderLock = USER_RESOURCE_COURSE_PUBLIC_LOCK_PREFIX + order.getUserId();
		orderBuinessLocks.add(orderLock);
		try {
			order.setDealPrice(orderView.getOrder().getDealPrice());
			if (redisLockHandler.tryLock(orderLock)) {

				logger.info("----------------------------------addOrderView addOrderAddress  start " + orderNo);
				logger.info("----------------------------------addOrderView addOrderAddress  end " + orderNo);
				logger.info("----------------------------------addOrderView addOrder  start " + orderNo);

				OrderResult<Order> addOrderResult = addOrderWithReturn(order);// 添加订单对象order
				logger.info("----------------------------------addOrderView addOrder  end " + orderNo);
				logger.info("----------------------------------addOrderView addOrderDetails  start " + orderNo);

				logger.info("----------------------------------addOrderView addOrderDetails  end " + orderNo);
				logger.info("----------------------------------addOrderView addPayOrder  start " + orderNo);
				PayResult<OrderPay> payResult = addPayOrder(order, orderNo, orderView);// 添加payOrder

				logger.info("----------------------------------addOrderView addPayOrder  end " + orderNo);
				order.setPayNo(payResult.getContent().getPayNo());
				orderView.setOrder(order);
				orderView.setPayOrder(payResult.getContent());
				orderViewResult.setContent(orderView);
				orderViewResult.setState(1);

				orderViewResult.setOrderState(OrderState.SUCCESS);

			} else {
				throw new OrderException(OrderState.USERRESOURCECOURSEPUBLICLOCKED, "");
			}
		} catch (OrderException e) {
			logger.error("----------------------------------addOrderView process  orderNo" + orderNo
					+ "   OrderException=" + e.toExecptionString());
			orderViewResult.setOrderState(e.getOrderState());
			orderViewResult.setOrderSateContent(e.getOrderSateContent());
			throw new OrderException(e.getOrderState(), e.getOrderSateContent());
		} finally {
			for (String lock : orderBuinessLocks) {
				redisLockHandler.unLock(lock);
			}
		}

		logger.info("----------------------------------addOrderView lock  resources   end " + orderNo);
		Date endTime = new Date();
		logger.info("----------------------------------addOrderView end  orderNo=" + orderNo + ",endtime=" + endTime
				+ ", count time=" + (endTime.getTime() - startTime.getTime()) + " millisecond");
		return orderViewResult;

	}
	
	private PayResult<OrderPay> addPayOrder(Order order, String orderNo, OrderView orderView) {
		OrderPay payOrder = orderView.getPayOrder();
		payOrder.setPayPrice(order.getDealPrice());
		payOrder.setOrderNo(orderNo);
		payOrder.setPaySumPrice(order.getOriginalPirce());
		payOrder.setExchangePrice(0);
		payOrder.setUserId(order.getUserId());
		payOrder.setPayChannel(orderView.getPayOrder().getPayChannel());
		payOrder.setPayWay(orderView.getPayOrder().getPayWay());
		return payOrderService.addPayOrder(payOrder);
	}
	
	*//**
	 * 根据传入的订单对象添加order到数据库
	 * 
	 * @param order
	 * @return OrderResult<Order>
	 *//*
	@Override
	public OrderResult<Order> addOrderWithReturn(Order order) {
		// TODO Auto-generated method stub
		OrderResult<Order> payResult = new OrderResult<Order>();

		String orderNo = order.getOrderNo();
		String tableNum = getOrderNoTableNum(orderNo);
		order.setOrderNo(orderNo);
		// order.setTableNum(tableNum);
		payResult.setState(orderMapper.insertSelective(order));
		payResult.setContent(order);
		return payResult;

	}
	
	public static String getOrderNoTableNum(String orderNo) {
		String tableNum = orderNo.substring(0, 4);
		return tableNum;
	}

	@Override
	public OrderResult<OrderView> updateOrderAndUnlockOrder(String orderNo, String payNo) throws OrderException {
		OrderResult<OrderView> orderResult = selectOrderView(orderNo);
		Order order = orderResult.getContent().getOrder();
		final UserInfo userInfo = userInfoService.getUserInfoByAttr("id", order.getUserId().toString());
		PayResult<OrderPay> payResult = payOrderService.selectPayOrder(payNo);
		order.setOrderState(OrderProcessState.ORDERPAYSUCCESS.getCode());
		order.setFinshiTime(new Date());
		if (payResult.getContent().getPayState().intValue() != 1) {
			order.setOrderState(OrderProcessState.ORDERPAYSUCCESS.getCode());
		} else {
			order.setPayNo(payNo);
		}

		Integer provideId = order.getProviderId();// 关联充值配置表id //-1代表代理商充值
		// ，0代表一般充值 ， 其他值代表广告币兑换充值
		
		代理商维护，有可能不是注册用户，所以这里防止空指针异常
		UserAssets userAssets = null;
		if(provideId != -1){
			Map<String, Object> userAssetsMap = new HashMap<String, Object>();
			userAssetsMap.put("userId", userInfo.getId());
			userAssets = userAssetsMapper.selectModel(userAssetsMap);
		}

		if (provideId > 0) {
			Integer orderType = order.getOrderType();
			if(orderType.equals(3)){//消费金充值兑换
				logger.info("消费金充值---------------》userId:" + order.getUserId() + ",payNo:" + order.getPayNo() + ",configId:" + order.getProviderId());
				consumerMoneyChangeConfigService.changeConsumerMoney(order);
			}else if(orderType.equals(4)){
				logger.info("优惠买单---------------》userId:" + order.getUserId() + ",payNo:" + order.getPayNo() + ",configId:" + order.getProviderId());
				consumerMoneyOrderService.addConsumerOrder(order);
			}else{// 广告币充值兑换
				logger.info("广告币充值---------------》userId:" + order.getUserId() + ",payNo:" + order.getPayNo() + ",configId:" + order.getProviderId());
				updateBalanceToCoins(userAssets, provideId.longValue(), true);
			}
			// 如果provideId大于0就表示充值成功【只针对充值广告币】
			try {
				taskHistoryService.insertTask(order.getUserId(), (long) 8, "1");
				
				
				//商户首次充值兑换广告币任务
				new Thread(new Runnable() {
					@Override
					public void run() {
						logger.info("创客合伙人邀请商户认证并首次充值兑换广告币任务-------------------->");
						taskService.updateInviteBusinessRechargeTaskState(userInfo.getId());
					}
				}).start();
				
				
			} catch (Exception e) {
				e.getStackTrace();
				logger.error("微信or支付宝充值之后完成任务出现异常 :[{}]", e.getMessage());
			}
		} else if (provideId == 0) {

			Integer orderType = order.getOrderType();
			//根据orderType为2决定变更创客身份   TODO
			if(orderType == 2){
				try {
					modifyUserRoleByOrder(order);
				} catch (Exception e) {
					e.getStackTrace();
					logger.error("订单结算后修改创客、合伙人身份时出错 :[{}]", e.getMessage());
				}
			}else{
				// 正常充值
				userAssets.setCashBalance(userAssets.getCashBalance() + order.getOriginalPirce());				
				userAssetsMapper.updateByPrimaryKeySelective(userAssets);
				添加充值流水
				logger.info("order:"+order);
				logger.info("userAssets:"+userAssets);
				cashFlowService.addUserCashFlowAsyn((double)order.getOriginalPirce(), 
						                                UserCashFlow.CASH_FLOW_DIRECTION_IN, 
						                                order.getUserId(), 
						                                BillFilterHelper.INCOME_USER_RECHARGE.getMarker(), 
						                                TableNameHelper.TB_USER_ASSETS.getMarker(),
						                                userAssets.getId(), 
						                                (userAssets.getAwardCashBalance() + order.getOriginalPirce()),
						                                UserCashFlow.CASH_FLOW_TYPE_CHANGE);
				

			}
			
			
//			if(orderType == 2){
//				try {
//					modifyUserRoleByOrder(order);
//				} catch (Exception e) {
//					e.getStackTrace();
//					logger.error("订单结算后修改创客、合伙人身份时出错 :[{}]", e.getMessage());
//				}
//			}
			
			 正常充值流水(目前只能领钱兑换成广告币) 
		} else {
			// 代理商后续操作
			this.Maintainagent(order);
		}

		updateOrderByOrderNo(order);

		return orderResult;
	}

	@Override
	public OrderResult<OrderView> selectOrderView(String orderNo) {
		OrderResult<OrderView> orderResult = new OrderResult<OrderView>();

		OrderResult<Order> orderModelResult = selectOrder(orderNo);
		if (orderModelResult.getState() == 1) {
			OrderView orderView = new OrderView();
			orderView.setOrder(orderModelResult.getContent());
			orderResult.setState(1);

			orderResult.setContent(orderView);
		}
		return orderResult;
	}

	@Override
	public OrderResult<Integer> updateOrderByOrderNo(Order order) {
		OrderResult<Integer> orderResult = new OrderResult<Integer>();
		order.setModifyTime(new Date());
		orderResult.setState(1);
		orderResult.setContent(orderMapper.updateByPrimaryKeySelective(order));
		return orderResult;
	}

	
	*//**
	 * 代理商维护相关操作
	 * 
	 * @param order
	 *//*
	private void Maintainagent(Order order) {
		Map<String, Object> map = new HashMap<>();
		 查询代理商支付系统配置参数 
		map.put("state", AgentIncomeHelper.STATE_1.getMarker());
		map.put("configKey", "client.twitter.pay.charge");
		String twitterMoney = userInfoService.getUserJoinCount(map).getConfigContent();// 推客支付金额
		map.put("configKey", "client.stars.twitter.pay.charge");
		String starLevelTwitter = userInfoService.getUserJoinCount(map).getConfigContent();// 星级推客支付金额
		map.put("configKey", "client.down.payment.agent.pay.charge");
		String money = userInfoService.getUserJoinCount(map).getConfigContent();// 预支付金额
		*//**
		 * 支付金额等于推客、星级推客逻辑
		 *//*
		String payAmount = String.valueOf(order.getOriginalPirce());
		if (payAmount.equals(twitterMoney) || payAmount.equals(starLevelTwitter)) {
			Agent agent = new Agent();
			agent.setUserId(order.getUserId());
			agent.setCutMark(AgentIncomeHelper.CUT_MARK_1.getMarker());
			agent.setAgentAmount(Integer.valueOf(payAmount));
			维护代理商信息 
			logger.info("用户维护代理商身份，用户ID:" + order.getUserId() + ";支付金额：" + payAmount);
			agentIncomeService.processAgentInfo(agent);
		} else if (payAmount.equals(money)) {
			*//**
			 * 支付金额等于代理商订金逻辑
			 *//*
			 app支付逻辑 
			if (order.getPaySource() != 3) {
				EnrollInfo enrollInfo = new EnrollInfo();
				enrollInfo.setUserId(order.getUserId());
				map.clear();
				map.put("id", order.getUserId());
				UserInfo userInfo = userInfoService.getUserInfo(map);
				enrollInfo.setName(userInfo.getNickName());
				enrollInfo.setCreateTime(new Date());
				enrollInfo.setPayType(Integer.valueOf(order.getOrderDesc()));
				enrollInfo.setDownPayment(Integer.valueOf(payAmount));
				enrollInfo.setPhone(userInfo.getPhone());
				enrollInfo.setProvince(userInfo.getProvince());
				enrollInfo.setCity(userInfo.getCity());
				enrollInfo.setArea(userInfo.getArea());
				enrollInfo.setAddress(userInfo.getAddress());
				agentIncomeService.saveEnrollInfo(enrollInfo);
			} else {
				 微信公众号支付逻辑 
				map.put("id", order.getUserId());
				EnrollInfo enrollInfo = agentIncomeService.selectEnrollInfo(map);
				enrollInfo.setDownPayment(Integer.valueOf(payAmount));// 订金
				enrollInfo.setModifyTime(new Date());
				enrollInfo.setPayType(Integer.valueOf(order.getOrderDesc()));// 支付方式
				agentIncomeService.updateEnrollInfo(enrollInfo);
			}
		}
	}

	@Override
	public Boolean updateUserAssetByType(UserAssets userAssets, ConsumerMoneyChangeConfig config, int type,
			boolean isPayed) {
		boolean isSucc = false;
		boolean assetsResult = false;

		Integer reChargeMoney = config.getCurrentMoney();//订单金额
		Integer exChangeCoins = config.getConsumerMoney();// 可兑换消费金
		Integer additionalCoins = config.getBundled();// 赠送消费金
		
		reChargeMoney = reChargeMoney != null ? reChargeMoney : 0;
		exChangeCoins = exChangeCoins != null ? exChangeCoins : 0;
		additionalCoins = additionalCoins != null ? additionalCoins : 0;
		
		logger.error("兑换所需金额：" + reChargeMoney + ",可对换消费金数量：" + exChangeCoins + ",附赠消费金数量：" + additionalCoins);
		if (isPayed) {
			// 说明已经付通过微信，支付宝付款完成
			userAssets.setConsumerMoneyBalance(userAssets.getConsumerMoneyBalance() + exChangeCoins + additionalCoins);
			assetsResult = userAssetsMapper.updateByPrimaryKeySelective(userAssets) > 0 ? true : false;
		} else {
			// 说明是用余额兑换消费金
			if (userAssets.getCashBalance() >= reChargeMoney) {
				// 说明余额足够
				userAssets.setCashBalance(userAssets.getCashBalance() - reChargeMoney);
				userAssets.setConsumerMoneyBalance(userAssets.getConsumerMoneyBalance() + exChangeCoins + additionalCoins);
				assetsResult = userAssetsMapper.updateByPrimaryKeySelective(userAssets) > 0 ? true : false;
				try {
					taskHistoryService.insertTask(userAssets.getUserId(), (long) 8, "1");
				} catch (Exception e) {
					logger.error("零钱兑换消费金出现异常 :[{}]", e.getMessage());
					throw new BusinessException("兑换消费金完成任务抛出异常");
				}
			}else{
				logger.error("用户["+userAssets.getUserId()+"],零钱余额["+userAssets.getCashBalance()+"],兑换消费金零钱余额不足");
			}
		}
		isSucc = assetsResult;
		return isSucc;
	}

	@Override
	public Boolean updateBalanceToCoins(UserAssets userAssets, Long configId, boolean isPayed) {
		boolean isSucc = false;
		RechargeConfig config = rechargeConfigService.selectByPK(configId);// 充值配置记录
		if (config != null) {
			logger.info("查出来的充值配置 : " + config.getId() + ", " + config.getExchangeCoins());
			boolean assetsResult = false;

			Integer reChargeMoney = config.getRechargeMoney();// 充值金额(分)
			Integer exChangeCoins = config.getExchangeCoins();// 可兑换广告币
			Integer additionalCoins = config.getAdditionalCoins();// 赠送广告币
			if (isPayed) {
				// 说明已经付过款
				userAssets.setAdvertCoin(userAssets.getAdvertCoin() + exChangeCoins + additionalCoins);
				assetsResult = userAssetsMapper.updateByPrimaryKeySelective(userAssets) > 0 ? true : false;
			} else {
				// 说明是用余额兑换广告币
				if (userAssets.getCashBalance() >= reChargeMoney) {
					// 说明余额足够
					userAssets.setCashBalance(userAssets.getCashBalance() - reChargeMoney);
					userAssets.setAdvertCoin(userAssets.getAdvertCoin() + exChangeCoins + additionalCoins);
					assetsResult = userAssetsMapper.updateByPrimaryKeySelective(userAssets) > 0 ? true : false;

					// 用户用零钱充值成功【只针对充值广告币】
					try {
						taskHistoryService.insertTask(userAssets.getUserId(), (long) 8, "1");
					} catch (Exception e) {
						logger.error("零钱充值之后完成任务出现异常 :[{}]", e.getMessage());
						throw new BusinessException("兑换广告币完成任务抛出异常");
					}
				}
			}
			// 用户购买广告币，代理商获取推广收益start 20170103
			Map<String, Object> userInfoMap = new HashMap<String, Object>();
			userInfoMap.put("id", userAssets.getUserId());
			UserInfo user = userInfoService.getUserInfo(userInfoMap);
			if (user != null) {
				if (reChargeMoney >= 12800) {
					// int agentFlag =
					// userAgentApplicationRecordService.saveAgentPromotionAward(user,
					// reChargeMoney);
					// logger.info("成为星级代理 状态标识 : " + agentFlag);
				}

				 广告币流水 

				UserCashFlow cashFlow = new UserCashFlow();
				cashFlow.setPrice(exChangeCoins + additionalCoins);
				cashFlow.setCreateTime(new Date());
				cashFlow.setCashFlowDirection(UserCashFlow.CASH_FLOW_DIRECTION_IN);
				cashFlow.setUserId(user.getId());
				cashFlow.setCashFlowTitle((byte) BillFilterHelper.INCOME_USER_RECHARGE.getMarker().intValue());
				cashFlow.setTriggerTableName(TableNameHelper.TB_USER_ASSETS.getMarker());
				cashFlow.setTriggerNo(userAssets.getId());
				cashFlow.setBalance(userAssets.getCashBalance().intValue());
				cashFlow.setCashFlowType(UserCashFlow.CASH_FLOW_TYPE_COIN);
				cashFlowService.saveUserCashFlow(cashFlow);

			} else {
				logger.error("用户购买广告币，代理商获取推广收益出错!");
			}

			// 用户购买广告币，代理商获取推广收益end
			isSucc = assetsResult;
		} else {
			logger.info("充值配置未查询到！配置id ： " + configId);
		}

		return isSucc;
	}

	
	*//**
	 * 根据订单金额修改用户角色身份（创客系列专用、计算创客上线的收益）
	 * @param order
	 *//*
	public void modifyUserRoleByOrder(Order order){
		Integer dealPrice = order.getDealPrice();//创客3300、合伙人9900
		Long payUserId = order.getUserId();//付款人用户id
		//付款人信息
		UserInfo payUserInfo = userInfoService.getUserInfoByAttr("id", payUserId.toString());
		if(dealPrice == 330000){
			//创客
			payUserInfo.setRole(Byte.valueOf("CHUANG"));  //TODO 角色类型需要调整
		}else if(dealPrice == 990000){
			//合伙人
			payUserInfo.setRole(Byte.valueOf("HEHUOREN")); //TODO 角色类型需要调整
		}
		
		boolean updateFlag = userInfoService.updateUserInfo(payUserInfo);
		邀请收益暂时取消
		if(updateFlag){
			//计算邀请当前人成为创客、合伙人的那个人的收益(如果满足条件的话)
			//创客邀请前5个创客获得订单金额的15%收益,超过5个后每个20%收益,合伙人邀请前5个创客获得订单金额的20%收益,超过5个后每个25%收益
			UserInfo parentUserInfo = userInviteService.selectParentUserInfo(payUserId);//邀请人信息
			
			if(parentUserInfo != null){//邀请人存在
				UserAssets parentAsset = userAssetsService.getUserAssetsByUserId(parentUserInfo.getId());//邀请人账户
				//查询邀请人的下线个数
				List<UserInvite> childrenList = userInviteService.selectChildrenUserInfo(parentUserInfo.getId());
				if(childrenList != null && childrenList.size() > 0){
					String parentRole = String.valueOf(parentUserInfo.getRole());  //TODO 这个地方需要调整,暂时占位
					if(childrenList.size() <= 10){
						if(parentRole.equals("CHUANG")){
							parentAsset.setAwardCashBalance(parentAsset.getAwardCashBalance() + dealPrice * 0.15);
							
							添加奖励金流水
							cashFlowService.addUserCashFlowAsyn((dealPrice * 0.15), UserCashFlow.CASH_FLOW_DIRECTION_IN, 
									parentUserInfo.getId(), BillFilterHelper.INCOME_CHUANG_AVTIVE.getMarker(), 
									TableNameHelper.TB_USER_ASSETS.getMarker(), parentAsset.getId(), 
									(parentAsset.getAwardCashBalance() + dealPrice * 0.15), UserCashFlow.CASH_FLOW_TYPE_AWARD);
						}else if(parentRole.equals("HEHUOREN")){
							parentAsset.setAwardCashBalance(parentAsset.getAwardCashBalance() + dealPrice * 0.2);
							添加奖励金流水
							cashFlowService.addUserCashFlowAsyn((dealPrice * 0.2), UserCashFlow.CASH_FLOW_DIRECTION_IN, 
									parentUserInfo.getId(), BillFilterHelper.INCOME_CHUANG_AVTIVE.getMarker(), 
									TableNameHelper.TB_USER_ASSETS.getMarker(), parentAsset.getId(), 
									(parentAsset.getAwardCashBalance() + dealPrice * 0.2), UserCashFlow.CASH_FLOW_TYPE_AWARD);
						}
					}else{
						int childChuangNum = 0;//邀请的创客或者合伙人数量(因为下线大于5个不一定下线都是创客)
						for(UserInvite invite : childrenList){
							Long childUserId = invite.getNewUserId();
							UserInfo childUserInfo = userInfoService.getUserInfoByAttr("id", childUserId.toString());
							if(childUserInfo !=null && !childUserInfo.equals("")){
								if(childUserInfo.getRole().equals("CHUANG") || childUserInfo.getRole().equals("HEHUOREN")){
									childChuangNum++;
								}
							}
						}
						if(parentRole.equals("CHUANG")){
							if(childChuangNum > 10){
								parentAsset.setAwardCashBalance(parentAsset.getAwardCashBalance() + dealPrice * 0.2);
								添加奖励金流水
								cashFlowService.addUserCashFlowAsyn((dealPrice * 0.2), UserCashFlow.CASH_FLOW_DIRECTION_IN, 
										parentUserInfo.getId(), BillFilterHelper.INCOME_CHUANG_AVTIVE.getMarker(), 
										TableNameHelper.TB_USER_ASSETS.getMarker(), parentAsset.getId(), 
										(parentAsset.getAwardCashBalance() + dealPrice * 0.2), UserCashFlow.CASH_FLOW_TYPE_AWARD);
							}else{
								parentAsset.setAwardCashBalance(parentAsset.getAwardCashBalance() + dealPrice * 0.15);
								添加奖励金流水
								cashFlowService.addUserCashFlowAsyn((dealPrice * 0.15), UserCashFlow.CASH_FLOW_DIRECTION_IN, 
										parentUserInfo.getId(), BillFilterHelper.INCOME_CHUANG_AVTIVE.getMarker(), 
										TableNameHelper.TB_USER_ASSETS.getMarker(), parentAsset.getId(), 
										(parentAsset.getAwardCashBalance() + dealPrice * 0.15), UserCashFlow.CASH_FLOW_TYPE_AWARD);
							}
						}else if(parentRole.equals("HEHUOREN")){
							if(childChuangNum > 10){
								parentAsset.setAwardCashBalance(parentAsset.getAwardCashBalance() + dealPrice * 0.25);
								添加奖励金流水
								cashFlowService.addUserCashFlowAsyn((dealPrice * 0.25), UserCashFlow.CASH_FLOW_DIRECTION_IN, 
										parentUserInfo.getId(), BillFilterHelper.INCOME_CHUANG_AVTIVE.getMarker(), 
										TableNameHelper.TB_USER_ASSETS.getMarker(), parentAsset.getId(), 
										(parentAsset.getAwardCashBalance() + dealPrice * 0.25), UserCashFlow.CASH_FLOW_TYPE_AWARD);
							}else{
								parentAsset.setAwardCashBalance(parentAsset.getAwardCashBalance() + dealPrice * 0.2);
								添加奖励金流水
								cashFlowService.addUserCashFlowAsyn((dealPrice * 0.2), UserCashFlow.CASH_FLOW_DIRECTION_IN, 
										parentUserInfo.getId(), BillFilterHelper.INCOME_CHUANG_AVTIVE.getMarker(), 
										TableNameHelper.TB_USER_ASSETS.getMarker(), parentAsset.getId(), 
										(parentAsset.getAwardCashBalance() + dealPrice * 0.2), UserCashFlow.CASH_FLOW_TYPE_AWARD);
							}
						}
					}
				}
				userAssetsService.updateUserAssets(parentAsset);
			}
			
		}
		
		
	}

	@Override
	public OrderResult<Order> selectOrder(String orderNo) {
		OrderResult<Order> orderResult = new OrderResult<Order>();
		Map<String, Object> map = new HashMap<String, Object>();
		String tableNum = getOrderNoTableNum(orderNo);
		map.put("tableNum", tableNum);
		map.put("orderNo", orderNo);
		Order order = orderMapper.selectModel(map);
		if (order != null) {
			orderResult.setState(1);
			orderResult.setContent(order);

		}
		return orderResult;
	}

	@Override
	public Boolean updateUserAssetByProvideId(Long bid, Long userId, Double pprice, Double consumerMoney,
			boolean isPayed) {
		boolean isSucc = false;
		boolean assetsResult = false;
		
		try {
			Map<String,Object> map2 = new HashMap<>();
			map2.put("userId",userId);
			UserAssets userAssets = userAssetsService.getUserAssets(map2);//卖方账户
			if (isPayed) {
				// 说明已经付通过微信，支付宝付款完成
				userAssets.setConsumerMoneyBalance(userAssets.getConsumerMoneyBalance() - consumerMoney);
				userAssets.setModifyTime(new Date());
				userAssetsMapper.updateByPrimaryKeySelective(userAssets);
			} else {
				// 说明是用余额兑换消费金
				if (userAssets.getCashBalance() >= pprice) {
					// 说明余额足够
					userAssets.setCashBalance(userAssets.getCashBalance() - pprice);
					userAssets.setConsumerMoneyBalance(userAssets.getConsumerMoneyBalance() - consumerMoney);
					userAssets.setModifyTime(new Date());
					userAssetsMapper.updateByPrimaryKeySelective(userAssets);
					
					BusinessShop shop = businessShopService.selectBusinessShopById(bid);
					map2.clear();
					map2.put("userId",shop.getUserId());
					UserAssets busAssets = userAssetsService.getUserAssets(map2);//卖方账户
					busAssets.setModifyTime(new Date());
					busAssets.setCashBalance(busAssets.getCashBalance() + pprice);
					userAssetsMapper.updateByPrimaryKeySelective(busAssets);
				}else{
					logger.error("用户["+userAssets.getUserId()+"],零钱余额["+userAssets.getCashBalance()+"],兑换消费金零钱余额不足");
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		isSucc = assetsResult;
		return isSucc;
	}

	@Override
	public Boolean updateUserAssetByType(UserAssets userAssets, RechargeConfig config, int type, boolean isPayed) {
		boolean isSucc = false;
		logger.info("查出来的充值配置 : " + config.getId() + ", " + config.getExchangeCoins());
		boolean assetsResult = false;

		Integer reChargeMoney = config.getRechargeMoney();// 充值金额(分)
		Integer exChangeCoins = config.getExchangeCoins();// 可兑换广告币
		Integer additionalCoins = config.getAdditionalCoins();// 赠送广告币
		//byte b = BillFilterHelper.INCOME_USER_RECHARGE.getMarker().byteValue();
		if (isPayed) {
			// 说明已经付过款
			userAssets.setAdvertCoin(userAssets.getAdvertCoin() + exChangeCoins + additionalCoins);
			assetsResult = userAssetsMapper.updateByPrimaryKeySelective(userAssets) > 0 ? true : false;
		} else {
			
			if(type == 5){
				// 邀请好友奖励金兑换广告币
				if (userAssets.getAwardCashBalance() >= reChargeMoney) {
					// 说明余额足够
					userAssets.setAwardCashBalance(userAssets.getAwardCashBalance() - reChargeMoney);
					userAssets.setAdvertCoin(userAssets.getAdvertCoin() + exChangeCoins + additionalCoins);
					//b = BillFilterHelper.INCOME_CHUANG_AVTIVE.getMarker().byteValue();
				}
			}else if(type == 6){
				// 学习奖励金兑换广告币
				if (userAssets.getTaskAwardBalance() >= reChargeMoney) {
					// 说明余额足够
					userAssets.setTaskAwardBalance(new Double(userAssets.getTaskAwardBalance() - reChargeMoney).intValue());
					userAssets.setAdvertCoin(userAssets.getAdvertCoin() + exChangeCoins + additionalCoins);
					//b = BillFilterHelper.INCOME_CHUANG_TASK.getMarker().byteValue();
				}
			}else{//3
				// 说明是用余额兑换广告币
				if (userAssets.getCashBalance() >= reChargeMoney) {
					// 说明余额足够
					userAssets.setCashBalance(userAssets.getCashBalance() - reChargeMoney);
					userAssets.setAdvertCoin(userAssets.getAdvertCoin() + exChangeCoins + additionalCoins);
					//b = BillFilterHelper.INCOME_USER_RECHARGE.getMarker().byteValue();
				}
			}
			assetsResult = userAssetsMapper.updateByPrimaryKeySelective(userAssets) > 0 ? true : false;
			try {
				taskHistoryService.insertTask(userAssets.getUserId(), (long) 8, "1");
			} catch (Exception e) {
				logger.error("零钱充值之后完成任务出现异常 :[{}]", e.getMessage());
				throw new BusinessException("兑换广告币完成任务抛出异常");
			}
		}
		// 用户购买广告币，代理商获取推广收益start 20170103
		Map<String, Object> userInfoMap = new HashMap<String, Object>();
		userInfoMap.put("id", userAssets.getUserId());
		UserInfo user = userInfoService.getUserInfo(userInfoMap);
		if (user != null) {
			if (reChargeMoney >= 12800) {
				// int agentFlag =
				// userAgentApplicationRecordService.saveAgentPromotionAward(user,
				// reChargeMoney);
				// logger.info("成为星级代理 状态标识 : " + agentFlag);
			}
			
			 广告币流水 
			UserCashFlow cashFlow = new UserCashFlow();
			cashFlow.setPrice(exChangeCoins + additionalCoins);
			cashFlow.setCreateTime(new Date());
			cashFlow.setCashFlowDirection(UserCashFlow.CASH_FLOW_DIRECTION_OUT);
			cashFlow.setUserId(user.getId());
			cashFlow.setCashFlowTitle(b);
			cashFlow.setTriggerTableName(TableNameHelper.TB_USER_ASSETS.getMarker());
			cashFlow.setTriggerNo(userAssets.getId());
			cashFlow.setBalance(userAssets.getCashBalance().intValue());
			cashFlow.setCashFlowType(UserCashFlow.CASH_FLOW_TYPE_COIN);
			userCashFlowService.saveUserCashFlow(cashFlow);
		} else {
			logger.error("用户购买广告币，代理商获取推广收益出错!");
		}
		// 用户购买广告币，代理商获取推广收益end
		isSucc = assetsResult;
		return isSucc;
	}*/
	
}
