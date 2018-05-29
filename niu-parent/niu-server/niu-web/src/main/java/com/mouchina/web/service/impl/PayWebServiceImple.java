package com.mouchina.web.service.impl;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alipay.api.AlipayApiException;
import com.mouchina.base.redis.RedisHelper;
import com.mouchina.base.utils.UUIDGenerator;
import com.mouchina.entity.pojo.order.OrderRecord;
import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.entity.pojo.sys.SysRechargeConfig;
import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.server.api.order.OrderPayService;
import com.mouchina.server.api.order.OrderRecordService;
import com.mouchina.web.base.dto.WeixinPrepayDTO;
import com.mouchina.web.base.utils.AliPayUtils;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.base.utils.WeixinPayUtils;
import com.mouchina.web.service.api.PayWebService;
import com.mouchina.web.service.api.SysDataWebService;
import com.mouchina.web.service.api.SysRechargeConfigWebService;
import com.mouchina.web.service.api.UserWebService;

/**
 * @ClassName: OrderServiceSupport
 * @Description: 订单相关操作
 * @author yaochenglong
 * @date 2017年3月16日 上午11:12:47
 */
@Service
public class PayWebServiceImple implements PayWebService {

	private Logger log = Logger.getLogger(PayWebServiceImple.class);
	
	private DecimalFormat de = new DecimalFormat("0.00");
	
	@Autowired
	private OrderRecordService orderRecordService;
	
	@Autowired
	private OrderPayService orderPayService;
	
	@Autowired
	private SysDataWebService sysDataWebService;
	
	@Autowired
	private SysRechargeConfigWebService sysRechargeConfigWebService;
	
	@Autowired
	private UserWebService userWebService;
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Value("${weixinpay.notify.url}")
	private String weixinNotifyUrl;
	
	@Value("${alipay.notify.url}")
	private String alipayNotifyUrl;
	
	
	public OrderRecord createOrderRecord(String title,Long userId,Long sourceId,byte orderType,byte dealType,int originalMoney,int actualMoney,int goodsCount,int discount,Long couponId){
		
		OrderRecord record = new OrderRecord();
		String orderNo = UUIDGenerator.createGlobalUniqueOrderNo(redisHelper);
		record.setOrderNo(orderNo);
		record.setCreateTime(new Date());
		record.setClientState(OrderRecord.ORDER_RECORD_CLIENT_STATE_0);
		record.setOrderState(OrderRecord.ORDER_RECORD_STATE_0);
		record.setModifyTime(new Date());
		record.setDealType(dealType);
		record.setUserId(userId);
		record.setGoodsCount(goodsCount);
		record.setDiscount(discount);
		record.setOrderType(orderType);
		record.setSourceId(sourceId);
		record.setTitle(title);
		record.setCouponId(couponId);
		
		if(orderRecordService.save(record)){
			return record;
		}
		
		return null;
	}


	@Override
	public void createSysOrderRecordAndGetPaySign(byte payChannel,String title, String detail, String loginKey, Long sourceId,
			byte type, Integer originalMoney, byte payType, Integer discount, Integer count, ModelMap modelMap) {
		
		boolean valid = createOrderValidate(payChannel,type, sourceId, originalMoney, payType, discount, count, modelMap);
		if(valid){
			
			OrderRecord record = null;
			UserInfo u = userWebService.findUserByToken(loginKey, true);
			Long userId = u.getId();
			
			if(type == 0){//余额充值
				record = createOrderRecord(title, userId, sourceId, type, payType, originalMoney, originalMoney, count, discount, null);
			}else if(type == 1){//广告币购买
				record = createOrderRecord(title, userId, sourceId, type, payType, originalMoney, originalMoney, count, discount, null);
			}
			
			if(record != null){//申请支付签名
				
				boolean limitCredit = true;//限制使用信用卡
				SysData sd = sysDataWebService.findSysDataByKeyAndGroup(SysData.RECHARGE_LIMIT_CREDIT_KEY,SysData.Group.ALL);
		        if(sd != null){
		        	String data = sd.getConfigData();
		        	if(StringUtils.isNotEmpty(data)){
		        		if(data.equals("0")){//false
		        			limitCredit = false;
		        		}
		        	}
		        }
				
		        String orderNo = record.getOrderNo();//系统订单号
		        
				Map<String,String> response = null;
				if(payChannel == 0){//微信
					try {
						response = WeixinPayUtils.createWeixinPaySign(record.getActualPrice(), orderNo, "", title, weixinNotifyUrl, limitCredit);
					} catch (IllegalAccessException | NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
					
					if(response != null && response.size() > 0){
						WeixinPrepayDTO dto = new WeixinPrepayDTO();
						dto.setAppid(response.get("appid"));
						dto.setMch_id(response.get("mch_id"));
						dto.setPrepay_id(response.get("prepay_id"));
						dto.setOrderNo(orderNo);
						modelMap.put("result", Constants.RESPONSE_SUCCESS);
						modelMap.put("weixinPay", dto);
						return;
					}else{
						modelMap.put("result", Constants.RESPONSE_FAIL);
						modelMap.put("errorCode", Constants.ERROR_CODE_100000);
						modelMap.put("errorMsg", "支付请求失败");
						return;
					}
					
				}else{//支付宝
					String actualMoney = de.format(originalMoney / 10);//换成角
					try {
						String orderStr = AliPayUtils.createAliPaySign(orderNo, actualMoney, title, alipayNotifyUrl);
						if(StringUtils.isNotEmpty(orderStr)){
							modelMap.put("result", Constants.RESPONSE_SUCCESS);
							modelMap.put("aliPay", orderStr);
							return;
						}else{
							modelMap.put("result", Constants.RESPONSE_FAIL);
							modelMap.put("errorCode", Constants.ERROR_CODE_100000);
							modelMap.put("errorMsg", "支付请求失败");
							return;
						}
					} catch (IllegalAccessException | AlipayApiException e) {
						e.printStackTrace();
						modelMap.put("result", Constants.RESPONSE_FAIL);
						modelMap.put("errorCode", Constants.ERROR_CODE_100000);
						modelMap.put("errorMsg", "支付请求失败");
						return;
					}
				}
			}
			
		}
		
		modelMap.put("result", Constants.RESPONSE_FAIL);
		modelMap.put("errorCode", Constants.ERROR_CODE_100000);
		modelMap.put("errorMsg", "支付请求失败");
	}
	
	
	/**
	 * 创建订单验证
	 * @param payChannel
	 * @param type
	 * @param sourceId
	 * @param originalMoney
	 * @param payType
	 * @param discount
	 * @param count
	 * @param modelMap
	 * @return
	 */
	private boolean createOrderValidate(byte payChannel,byte type,Long sourceId,Integer originalMoney, byte payType, Integer discount, Integer count, ModelMap modelMap){
		
		if(payChannel != 0 && payChannel != 1){
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorCode", Constants.ERROR_CODE_100000);
			modelMap.put("errorMsg", "应用类型错误");
			return false;
		}
		
		if(originalMoney <= 10){
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorCode", Constants.ERROR_CODE_100000);
			modelMap.put("errorMsg", "金额错误");
			return false;
		}
		
		if(type == 0){//余额充值
			
		}else if(type == 1){//广告币购买
			SysRechargeConfig sysRechargeConfig = sysRechargeConfigWebService.findById(sourceId);
			if(sysRechargeConfig == null){
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				modelMap.put("errorMsg", "兑换套餐不存在");
				return false;
			}
			
			int actualMoney = sysRechargeConfig.getRechargeMoney();//该套餐所需金额
			if(originalMoney < actualMoney){
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				modelMap.put("errorMsg", "金额错误");
				return false;
			}
		}
		
		return true;
	}


	@Override
	public boolean createPayOrder(byte payChannel, String thirdPartNo, String orderNo, int actualPayMoney) {
		
		OrderRecord record = orderRecordService.findOrderRecordByUniqueOrderNo(orderNo);
		if(record != null){
			if(record.getOrderState() == OrderRecord.ORDER_RECORD_STATE_0){
				
			}
		}
		
		return false;
	}


	public String getWeixinNotifyUrl() {
		return weixinNotifyUrl;
	}


	public void setWeixinNotifyUrl(String weixinNotifyUrl) {
		this.weixinNotifyUrl = weixinNotifyUrl;
	}


	public String getAlipayNotifyUrl() {
		return alipayNotifyUrl;
	}


	public void setAlipayNotifyUrl(String alipayNotifyUrl) {
		this.alipayNotifyUrl = alipayNotifyUrl;
	}
	
	
	
	
	/*private final static Logger logger = LogManager.getLogger();

	@Resource
	private OrderService orderService;
	@Resource
	private OrderPayService orderPayService;

	
	@Resource
	private UserInfoService userInfoService;


	@Autowired
	private RedisHelper helper;

	*//**
	 * @Title: createOrder @Description: 创建订单 @param @param
	 * request @param @param user @param @param payMap @param @param receMap
	 * 参数 @return void 返回类型 @throws
	 *//*
	@Override
	public Order createOrder(Order o) {
//		o.setOrderNo(UUIDGenerator.orderNoGenerator(helper));
//		o.setTitle(BillFilterHelper.PAY_FACE_TOO.getDisplay());
//		o.setOrderDesc("第三方面对面付款");
//		o.setOrderState(OrderVO.ORDER_UNPAY);
//		o.setCreateTime(new Date());
//		Map<String,Object> map =new HashMap<String, Object>();
//		map.put("id", o.getUserId());
//		UserInfo uInfo = userInfoService.getUserInfo(map);
//		o.setUserName(StringUtils.isNotEmpty(uInfo.getNickName())?uInfo.getNickName():"");
//		OrderResult<Order> result = orderService.addOrderWithReturn(o);
//		o = result.getContent();
		return null;
	}

	*//**
	 * @Title: addOrderPay @Description:添加订单支付信息 @param @return 参数 @return int
	 * 返回类型 @throws
	 *//*

	private int addOrderPay(OrderPay pay) {
		int result = orderPayService.addPayOrder(pay);
		return result;
	}

	*//**
	 * @param map
	 *            map参数来自于第三方回调回来的参数，详情参考支付宝开发文档
	 * @param userId
	 * @return
	 *//*
	@Override
	public int updateAlipay(Map<String, String> map) {
		// 原支付请求的商户订单号
		String orderNo = map.get("out_trade_no");
		//-----------------add by zhangkun20170720  start
		String trade_status = map.get("trade_status");
		if("TRADE_FINISHED".equals(trade_status)){//如果支付宝给的是交易结束标识不作任何处理
			return 1;
		}
		//-----------------add by zhangkun20170720   end
		// 交易金额 将元转换成分
		Double money = Double.valueOf(map.get("total_amount")) * 100;

		OrderResult<Order> resultOrder = orderService.selectOrder(orderNo);
		Order orderValue = resultOrder.getContent();
		// 支付方用户id
		Long userId = orderValue.getUserId();
		//收款方用户id
		Long providerId = orderValue.getProviderId().longValue();
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("id", providerId);
		UserInfo uInfo = userInfoService.getUserInfo(mapParam);

		// 收款方资产信息
		UserAssets assets = userAssetsService.getUserAssetsByUserId(providerId);
		double afterBalance = assets.getCashBalance() + money;
		assets.setCashBalance(afterBalance);

		// 更新收款方资产信息
		
		userAssetsService.updateUserAssets(assets);  

		//////////////////////////// 更新相关订单信息////////////////////////////////

		// 更新订单信息
		orderValue.setOrderState(Order.ORDER_SUCCESS);
		orderValue.setPayNo(map.get("trade_no"));
		orderService.updateOrderByOrderNo(orderValue);

		if (result.getContent() > 0) {
			logger.info("更新订单信息 成功" + (result));
		}
		
		// 添加支付订单
		int count = saveOrderPayForAlipay(map,orderNo,userId,orderValue.getPaySource());
		if (count > 0) {
			logger.info("添加支付订单成功");
		}
		// 添加收款方流水
		addReceiptsLog(map, orderValue.getId(), providerId, afterBalance, BillFilterHelper.INCOME_FACING_EACH.getMarker().byteValue());
		// 添加通知
		String content="收到"+uInfo!=null?uInfo.getNickName():providerId+"的付款";
		saveNotify(providerId,content);
		logger.info("添加通知成功");
		return 1;
	}
	
	*//**
	 * 支付宝回调函数
	 * 修改订单状态,
	 * @param map
	 * @param orderState
	 *//*
	@Override
	public void updateOrderStateByOrderNo(String orderNo,int orderState){
		OrderResult<Order> resultOrder = orderService.selectOrder(orderNo);
		Order orderValue = resultOrder.getContent();
		// 更新订单信息
		orderValue.setOrderState(orderState);
		//OrderResult<Integer> result = orderService.updateOrderByOrderNo(orderValue);
		orderService.updateOrderByOrderNo(orderValue);
	}

	*//**
	 * 微信回调函数
	 *//*
	@Override
	public int updateWechatpay(WxPayResult payResult) {
		String orderNo = payResult.getOut_trade_no();
		//OrderResult<Order> order = orderService.selectOrder(orderNo);

		// 交易金额
		Double money = Double.valueOf(payResult.getTotal_fee());

		OrderResult<Order> resultOrder = orderService.selectOrder(orderNo);
		Order orderValue = resultOrder.getContent();
		// 支付方用户id
		
		Long userId = orderValue.getUserId();
		Long providerId = orderValue.getProviderId().longValue();
		Map<String,Object> mapParam = new HashMap<String,Object>();
		
		mapParam.put("id", providerId);
		UserInfo uInfo = userInfoService.getUserInfo(mapParam);

		// 收款方资产信息
		UserAssets assets = userAssetsService.getUserAssetsByUserId(providerId);
		double afterBalance = assets.getCashBalance() + money;
		assets.setCashBalance(afterBalance);

		// 更新收款方资产信息
		userAssetsService.updateUserAssets(assets);
		
		/////////////////////////////

		
		////////////////////////////更新相关订单信息////////////////////////////////
		orderValue.setOrderState(Order.ORDER_SUCCESS);
		OrderResult<Integer> result = orderService.updateOrderByOrderNo(orderValue);
		
		

		if (result.getContent() > 0) {
			logger.info("更新订单信息 成功" + (result));
		}
		
		// 添加支付订单
		int count = saveOrderForWx(payResult,orderValue.getOrderNo(),userId,orderValue.getPaySource());
		
		if (count > 0) {
			logger.info("添加支付订单成功");
		}
		// 添加收款方日志
		addReceiptsLogForWx(payResult, orderValue.getId(), providerId, afterBalance, BillFilterHelper.INCOME_FACING_EACH.getMarker().byteValue());
		
		// 添加通知
		String content="收到"+uInfo!=null?uInfo.getNickName():providerId+"的付款";
		saveNotify(providerId,content);
		logger.info("添加通知成功");
		return 1;
	}

	*//**
	 * @param payResult
	 * @return
	 * @throws NumberFormatException
	 *//*
	private int saveOrderForWx(WxPayResult payResult,String orderNo,Long userId,byte paySource) throws NumberFormatException {
		// 添加支付订单
		OrderPay op = new OrderPay();
		op.setPayWay(OrderPay.ORDER_PAY_TYPE_WEIXIN);
		// 2.更新订单支付表
		// 设置第三方支付流水号
		op.setThirdPartyPayNo(payResult.getOut_trade_no());
		// 设置支付金额
		double total_amount = Double.valueOf(payResult.getTotal_fee());
		op.setPayPrice((int) total_amount);
		op.setPaySumPrice((int) total_amount);
		// 支付状态 1未支付 2支付成功 3支付失败 4用户取消 5支付完成 通知失败 6退款申请 7退款完成
		op.setPayState(OrderPay.ORDER_PAY_SUCCESS);
		Date date = new Date();
		op.setModifyTime(date);
		op.setFinshiTime(date);
		op.setPayNo(payResult.getTransaction_id());//支付流水号
		op.setOrderNo(orderNo);//订单流水号
		op.setUserId(userId);
		op.setPayChannel(paySource);
		int count = addOrderPay(op);
		return count;
	}
	
	

	*//**
	 *微信支付失败更新订单状态
	 *//*
	@Override
	public int updatePayErrorMsg(String orderNo, String msg) {
		OrderResult<Order> result = orderService.selectOrder(orderNo);
		Order order = result.getContent();
		order.setOrderState(Order.ORDER_FAILD);
		OrderResult<Integer> resultInteger = orderService.updateOrderByOrderNo(order);
		return resultInteger.getContent();
	}

	*//**
	 * @param providerId
	 *//*
	private int saveNotify(Long providerId,String content) {
//		// 添加通知，通知收款方以收到付款
//		SystemMessage sm = new SystemMessage();
//		sm.setContent(content);
//		sm.setUserId(providerId);
//		sm.setCreateTime(new Date());
//		int flag = systemMessageService.insertSystemMessage(sm);
//		return flag;
		return 0;
	}

	*//**
	 * @Desc 添加资金变动流水
	 * @param map
	 * @param orderNo
	 * @param providerId
	 * @param afterBalance
	 *//*
	private void addReceiptsLog(Map<String, String> map, Long orderId, Long providerId, double afterBalance, byte titleType) {
//		// 3.添加日志
//		UserCashFlow flow = new UserCashFlow();
//		Double total_amount = Double.valueOf(map.get("total_amount").toString()) * 100;
//		flow.setPrice(total_amount.intValue());
//		flow.setCreateTime(new Date());
//		flow.setCashFlowDirection(UserCashFlow.CASH_FLOW_DIRECTION_IN);
//		flow.setUserId(providerId);
//		flow.setCashFlowTitle(titleType);
//		flow.setTriggerTableName("tb_order");
//		flow.setTriggerNo(orderId);
//		flow.setBalance((new Double(afterBalance)).intValue());
//		flow.setCashFlowType(UserCashFlow.CASH_FLOW_TYPE_CHANGE);
//		userCashFlowService.saveUserCashFlow(flow);
	}
	
	*//**
	 * @Desc 添加资金变动流水
	 * @param map
	 * @param orderNo
	 * @param providerId
	 * @param afterBalance
	 *//*
	private void addReceiptsLogForWx(WxPayResult payResult, Long orderId, Long providerId, double afterBalance, byte titleType) {
//		// 3.添加日志
//		UserCashFlow flow = new UserCashFlow();
//		flow.setPrice(Integer.parseInt(payResult.getTotal_fee()));
//		flow.setCreateTime(new Date());
//		flow.setCashFlowDirection(UserCashFlow.CASH_FLOW_DIRECTION_IN);
//		flow.setUserId(providerId);
//		flow.setCashFlowTitle(titleType);
//		flow.setTriggerTableName("tb_order");
//		flow.setTriggerNo(orderId);
//		flow.setBalance((new Double(afterBalance)).intValue());
//		flow.setCashFlowType(UserCashFlow.CASH_FLOW_TYPE_CHANGE);
//		userCashFlowService.saveUserCashFlow(flow);
	}

	*//**
	 * 保存支付订单信息
	 * 
	 * @param map
	 *//*
	private int saveOrderPayForAlipay(Map<String, String> map,String orderNo,Long userId,byte paySource) {
		// 添加支付订单
		OrderPay op = new OrderPay();
		
		op.setPayWay(OrderPay.ORDER_PAY_TYPE_ALIPAY); //支付方式
		// 2.更新订单支付表
		// 设置第三方支付流水号
		op.setThirdPartyPayNo(map.get("trade_no"));
		// 设置支付金额
		double total_amount = Double.valueOf(map.get("total_amount")) * 100;
		op.setPayPrice((int) total_amount);
		op.setPaySumPrice((int) total_amount);
		// 支付状态 1未支付 2支付成功 3支付失败 4用户取消 5支付完成 通知失败 6退款申请 7退款完成
		op.setPayState(OrderPay.ORDER_PAY_SUCCESS);
		Date date = new Date();
		op.setModifyTime(date);
		op.setFinshiTime(date);
		op.setPayNo(map.get("trade_no"));//支付流水号
		op.setOrderNo(orderNo);//订单流水号
		op.setUserId(userId);
		op.setPayChannel(paySource);
		int count = addOrderPay(op);
		return count;
	}*/
	
	
	
}
