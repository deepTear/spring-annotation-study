package com.mouchina.server.provider.order;


/**
 * 
 * ClassName: PayOrderServiceSupport 
 *
 * @Description 订单
 * @author Cris
 * @date 2017年3月3日 下午3:59:40
 *
 */
public class PayOrderServiceSupport {
	
	/*private final static Logger logger = LogManager.getLogger();
	
	private static final String PAY_ORDER_LOCK_PRFIX = "pay_order_lock_prefix_";

	@Resource
	private OrderService orderService;
	@Resource
	private OrderPayMapper orderPayMapper;
	@Resource
	private RedisHelper redisHelper;
	@Resource
	private RedisLockHandler redisLockHandler;

	@Override
	public PayResult<OrderPay> addPayOrder(OrderPay payOrder) {
		PayResult<OrderPay> payResult = new PayResult<OrderPay>();

		String payNo = UUIDGenerator.payOrderNoGenerator(redisHelper);
		payOrder.setPayNo(payNo);
		payResult.setState(orderPayMapper.insertSelective(payOrder));
		payResult.setContent(payOrder);
		return payResult;

	}


	@Override
	public PayResult<Integer> updatePayOrderCheckAndUnlock(PayOrderView payOrderView) {
		// TODO Auto-generated method stub
				logger.info("开始调用业务");
				PayResult<Integer> payResult = new PayResult<Integer>();
				OrderPay payOrder = payOrderView.getPayOrder();
				PayResult<OrderPay> payOrderResult = selectPayOrder(payOrderView.getPayOrder().getPayNo());
				if (payOrderResult.getState() != 1) {
					throw new PayOrderException(PayOrderState.PAYORDERNOEXITS,
							payOrder.getPayNo() + " " + payOrder.getOrderNo());
				}else{
					OrderPay or = payOrderResult.getContent();
					int orderPrice = or.getPayPrice();
					int realPrice = payOrder.getPayPrice();
					if(realPrice < orderPrice){
						logger.info("----------------------------------订单金额:" + orderPrice + ",实际支付金额：" + realPrice);
						return null;
					}
				}

				Date startTime = new Date();
				logger.info("----------------------------------updatePayOrderCheckAndUnlock start  payNo= " + payOrder.getPayNo() + ",ThirdPartyPayId=" + payOrder.getThirdPartyPayNo());
				List<String> payOrderBuinessLocks = new ArrayList<String>();

				try {
					String payOrderLock = PAY_ORDER_LOCK_PRFIX + payOrder.getPayNo();
					payOrderBuinessLocks.add(payOrderLock);
					String orderNoLock = OrderServiceSupport.ORDER_NO_LOCK_PREFIX + payOrder.getOrderNo();
					payOrderBuinessLocks.add(orderNoLock);
					if (redisLockHandler.tryLock(payOrderLock) && redisLockHandler.tryLock(orderNoLock)) {
						logger.info("----------------------------------updatePayOrderCheckAndUnlock lock suceess  payNo= "+ payOrder.getPayNo());
						if (updateCheckPayOrderViewBefore(payOrderView) == 0) {

							//更新订单和修改余额
							orderService.updateOrderAndUnlockOrder(payOrder.getOrderNo(), payOrder.getPayNo());

							logger.info("----------------------------------updatePayOrderCheckAndUnlock  updateCheckPayOrderView start process   payNo= " + payOrder.getPayNo());

							PayResult<OrderPay> paySelectResult = selectPayOrder(payOrder.getPayNo());
//							OrderPay updatePayOrder = new OrderPay();
							OrderPay dbOrderPay = paySelectResult.getContent();
							dbOrderPay.setId(paySelectResult.getContent().getId());
							dbOrderPay.setPayNo(payOrder.getPayNo());
							dbOrderPay.setFinshiTime(new Date());
							dbOrderPay.setPayState(OrderPay.ORDER_PAY_SUCCESS);
							dbOrderPay.setThirdPartyPayNo(payOrder.getThirdPartyPayNo());

							logger.info(
									"----------------------------------updatePayOrderCheckAndUnlock  upateCheckPayOrder start process   payNo= "
											+ payOrder.getPayNo());
							updatePayOrder(dbOrderPay);

							logger.info("----------------------------------updatePayOrderCheckAndUnlock sms send start orderNo"
									+ payOrder.getPayNo());
							try {
							} catch (Exception e) {
								logger.error("----------------------------------updatePayOrderCheckAndUnlock sms send error");
							}

							payResult.setPayOrderState(PayOrderState.SUCCESS);
							payResult.setState(1);
						} else {
							logger.info("----------------------------------updatePayOrderRepetProcesFinished payNo="
									+ payOrder.getPayNo());
						}
					} else {
						logger.info("----------------------------------updatePayOrderCheckAndUnlock lock fail  payNo= "
								+ payOrder.getPayNo());
						throw new PayOrderException(PayOrderState.PAYNOLOCKED, "");
					}

				} catch (PayOrderException e) {
					logger.error("----------------------------------updatePayOrderCheckAndUnlock process  payNo"
							+ payOrder.getPayNo() + "   PayOrderException=" + e.toExecptionString());
					payResult.setPayOrderState(e.getPayOrderState());
					payResult.setPayOrderSateContent(e.getPayOrderSateContent());
					throw new PayOrderException(e.getPayOrderState(), e.getPayOrderSateContent());
				} finally {
					for (String lock : payOrderBuinessLocks) {
						redisLockHandler.unLock(lock);
					}
				}

				Date endTime = new Date();
				logger.info("----------------------------------updatePayOrderCheckAndUnlock  end  payNo= " + payOrder.getPayNo()
						+ ",ThirdPartyPayId=" + payOrder.getThirdPartyPayNo() + ", count time="
						+ (endTime.getTime() - startTime.getTime()) + " millisecond");

				return payResult;
	}


	@Override
	public PayResult<OrderPay> selectPayOrder(String payNo) {
		Map<String, Object> map = new HashMap<>();
//		String tableNum = getPayOrderNoTableNum(payNo);
		map.put("payNo", payNo);
//		map.put("tableNum", tableNum);
		PayResult<OrderPay> payResult = new PayResult<OrderPay>();
		OrderPay payOrder = orderPayMapper.selectModel(map);
		if (payOrder != null) {
			payResult.setContent(payOrder);
			payResult.setState(1);
		}
		return payResult;
	}
	
	*//**
	 * 
	 * @param payOrderView
	 * @return result 1终止处理 0继续执行
	 * @throws PayOrderException
	 *//*
	private int updateCheckPayOrderViewBefore(PayOrderView payOrderView) throws PayOrderException {

		int result = 0;
		OrderPay payOrder = payOrderView.getPayOrder();
		PayResult<OrderPay> payResult = selectPayOrder(payOrder.getPayNo());
		payOrder.setOrderNo(payResult.getContent().getOrderNo());
		payOrderView.setPayOrder(payOrder);
		logger.info("----------------------------------updateCheckPayOrder  start  payNo= " + payOrder.getPayNo());
//		result = upateCheckPayOrder(payOrder);此处无用,注释by zhangkun 20171105
		return result;
	}


	@Override
	public PayResult<Integer> updatePayOrder(OrderPay payOrder) {
		PayResult<Integer> payResult = new PayResult<Integer>();

		payOrder.setModifyTime(new Date());
		payResult.setState(orderPayMapper.updateByPrimaryKeySelective(payOrder));

		return payResult;
	}*/
}
