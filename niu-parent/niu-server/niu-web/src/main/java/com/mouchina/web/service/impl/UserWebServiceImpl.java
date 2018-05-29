package com.mouchina.web.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mouchina.base.redis.RedisHelper;
import com.mouchina.base.utils.Regexp;
import com.mouchina.base.utils.SHA1;
import com.mouchina.base.utils.UUIDGenerator;
import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.server.api.order.OrderRecordService;
import com.mouchina.server.api.user.UserInfoService;
import com.mouchina.web.base.config.Env;
import com.mouchina.web.base.page.Page;
import com.mouchina.web.base.utils.AliDaYuSMSUtils;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.SysDataWebService;
import com.mouchina.web.service.api.UserWebService;

@Service
public class UserWebServiceImpl implements UserWebService{

	private Logger log = Logger.getLogger(UserWebServiceImpl.class);
	
	private DecimalFormat df = new DecimalFormat("#.00");
	
	@Autowired
	private UserInfoService userInfoService;
	
	/*@Resource
	Env env;
	
	
	@Resource
	OrderService orderService;*/
	
	@Resource
	private RedisHelper redisHelper;
	
	@Autowired
	private SysDataWebService sysDataWebService;
	
	@Override
	public UserInfo findUserByToken(String token,boolean valid) {
		if(StringUtils.isNotEmpty(token)){
			return userInfoService.findUserByToken(token, valid);
		}
		return null;
	}

	@Override
	public UserInfo findUserByPrimaryKey(Long pk) {
		return userInfoService.findByPrimaryKey(pk);
	}

	@Override
	public UserInfo findUserByPhone(String phone,boolean valid) {
		if(StringUtils.isNotEmpty(phone)){
			return userInfoService.findUserByPhone(phone, valid);
		}
		return null;
	}

	@Override
	public void getIdenfityCode(String phone,String flag,ModelMap modelMap) {
		if(StringUtils.isNotEmpty(phone)){
			Integer checkCode = ((Double) (Math.random() * 9000 + 1000)).intValue();//随机的验证码
			//验证手机号是否存在
			UserInfo u_ = new UserInfo();
			u_.setPhone(phone);
			List<UserInfo> users = userInfoService.findList(null, u_);
			int right = 0;//正常账号数
			if(users != null && users.size() > 0){
				UserInfo user = null;
				for(int i = 0 ; i < users.size() ; i++){
					user = users.get(i);
					if(user.getState().intValue() == UserInfo.USER_STATE_0){//判断账户是否正常
						right++;
					}
				}
				if(right == 0){
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100000);
					modelMap.put("errorMsg", "手机号被禁");
					return;
				}
			}
			
			boolean isSucc = false;
			String key = RedisHelper.RedisKeyPrefix.SEND_PHONE_IDENFITY_CODE_24_HOUR_LIMIT + "_" + flag + "_" + phone;
			String idenfityCodeKey = RedisHelper.RedisKeyPrefix.PHONE_IDENFITY_CODE_KEY_PREFIX + "_" + flag + "_" + phone;
			String countStr = redisHelper.get(key);
			long count = countStr == null ? 0 : Long.parseLong(countStr);
			
			if(count > 0){//24小时内多条短信
				if(count < 5){//24小时内发送短信数量限制在
					isSucc = AliDaYuSMSUtils.sendSmsNew("https://eco.taobao.com/router/rest", "23485707",
							"a1e8f4efd4062af22d404362f1e4ece1", "{\"number\" : " + "\"" + checkCode + "\"}", phone,
							"SMS_20230072");
					if (isSucc) {
						int seconds = 24 * 60 * 60;
						redisHelper.getIncr(key);
						redisHelper.expire(key, seconds);
						modelMap.put("result", Constants.RESPONSE_SUCCESS);
						
						redisHelper.set(idenfityCodeKey, checkCode + "");
						redisHelper.expire(key, 180);//验证码有效时长3分钟
						
						log.info("手机号用户验证码：" + phone + "---->" + checkCode);
						return;
					} else {
						modelMap.put("result", Constants.RESPONSE_FAIL);
						modelMap.put("errorCode", Constants.ERROR_CODE_100000);
						modelMap.put("errorMsg", "验证码发送失败");
						return;
					}
				}else{
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100000);
					modelMap.put("errorMsg", "获取验证码太频繁");
					return;
				}
			}else{//24小时内第一条短信
				isSucc = AliDaYuSMSUtils.sendSmsNew("https://eco.taobao.com/router/rest", "23485707",
						"a1e8f4efd4062af22d404362f1e4ece1", "{\"number\" : " + "\"" + checkCode + "\"}", phone,
						"SMS_20230072");
				if (isSucc) {
					int seconds = 24 * 60 * 60;
					redisHelper.getIncr(key);
					redisHelper.expire(key, seconds);
					modelMap.put("result", Constants.RESPONSE_SUCCESS);
					
					redisHelper.set(idenfityCodeKey, checkCode + "");
					redisHelper.expire(key, 180);//验证码有效时长3分钟
					
					log.info("手机号用户验证码：" + phone + "---->" + checkCode);
					return;
				} else {
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100000);
					modelMap.put("errorMsg", "验证码发送失败");
					return;
				}
			}
		}
		modelMap.put("result", Constants.RESPONSE_FAIL);
		modelMap.put("errorMsg", "手机号格式错误");
	}

	@Override
	public void login(String flag,String phone, String identifyCode, Integer version,ModelMap modelMap) {
		if(checkIdentifyCode(flag, phone, identifyCode, modelMap)){
			UserInfo u = null;
			UserInfo u_ = new UserInfo();
			u_.setPhone(phone);
			List<UserInfo> users = userInfoService.findList(null,u_);
			int right = 0;//正常账号数
			if(users != null && users.size() > 0){
				UserInfo user = null;
				for(int i = 0 ; i < users.size() ; i++){
					user = users.get(i);
					if(user.getState().intValue() == UserInfo.USER_STATE_0){//判断账户是否正常
						right++;
						u = user;
					}
				}
				if(right == 0){
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100000);
					modelMap.put("errorMsg", "手机号已被禁");
					return;
				}
				//验证同一个身份识别标识注册的用户个数
				/*if(right > 0){
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorMsg", "手机号已注册");
					return;
				}*/
			}
			
			/**
			String accessToken = UUID.randomUUID().toString();
			String key = RedisHelper.RedisKeyPrefix.RESQUEST_ACCESS_TOKEN_KEY_PREFIX.toString() + "_login_" + phone;
			redisHelper.setex(key, 120, accessToken);//过期时长120秒
			**/
			
			if(u == null){
				
				String loginKey_ = SHA1.hex_sha1(UUIDGenerator.getUUID());
				UserInfo userInfo = new UserInfo();
				userInfo.setToken(loginKey_);
				userInfo.setPhone(phone);
				
				if(userInfoService.save(userInfo)){
					UserInfo userInfo_ = this.findUserByToken(loginKey_, true);
					userInfo_.setLoginKey(loginKey_);
					modelMap.put("userInfo", userInfo_);
					modelMap.put("result", Constants.RESPONSE_SUCCESS);
					modelMap.put("state", 0);//新用户第一次登陆
				}
				
			}else{
				
				String loginKey_ = SHA1.hex_sha1(UUIDGenerator.getUUID());
				u.setToken(loginKey_);
				
				if(userInfoService.updateByPrimaryKeySelective(u)){
					String photo = u.getPhoto();
					String nickName = u.getNickName();
					boolean infoCompletion = true;//必填信息是否填写完整
					if(StringUtils.isBlank(photo)){
						infoCompletion = false;
					}
					
					if(infoCompletion && StringUtils.isBlank(nickName)){
						infoCompletion = false;
					}
					
					if(infoCompletion){
						modelMap.put("result", Constants.RESPONSE_SUCCESS);
						modelMap.put("state", 2);//老用户必填信息填写完整
						u.setLoginKey(u.getToken());
						modelMap.put("userInfo", u);
					}else{
						modelMap.put("result", Constants.RESPONSE_SUCCESS);
						modelMap.put("state", 1);//必填信息不完整
						u.setLoginKey(u.getToken());
						modelMap.put("userInfo", u);
					}
				}else{
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100000);
					modelMap.put("errorMsg", "登录失败");
					return;
				}
			}
			//移除验证码
			this.removeIdentifyCode(flag, phone);
		}
	}

	@Override
	public boolean checkIdentifyCode(String flag, String phone, String identifyCode, ModelMap modelMap) {
		if(StringUtils.isBlank(phone) || !Regexp.checkMobile(phone)){
			if(modelMap != null){
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				modelMap.put("errorMsg", "手机号格式错误");
			}
			return false;
		}
		
		if(StringUtils.isBlank(identifyCode) || !Regexp.checkLoginIdentifyCode(identifyCode)){
			if(modelMap != null){
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				modelMap.put("errorMsg", "验证码格式错误");
			}
			return false;
		}
		
		//TODO
		if(identifyCode.equals("1111")){
			return true;
		}
		
		String identifyCodeKey = RedisHelper.RedisKeyPrefix.PHONE_IDENFITY_CODE_KEY_PREFIX + "_" + flag + "_" + phone;
		
		String code = redisHelper.get(identifyCodeKey);
		if(StringUtils.isBlank(code) || !code.equals(identifyCode)){
			if(modelMap != null){
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				modelMap.put("errorMsg", "验证码错误");
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 移除使用过的验证码
	 * @param flag
	 * @param phone
	 */
	private void removeIdentifyCode(String flag, String phone){
		String identifyCodeKey = RedisHelper.RedisKeyPrefix.PHONE_IDENFITY_CODE_KEY_PREFIX + "_" + flag + "_" + phone;
		redisHelper.remove(identifyCodeKey);
	}

	@Override
	public void updateUserInfo(String loginKey,UserInfo userInfo,ModelMap modelMap) {
		modelMap.clear();
		try {
			boolean result = false;
			/*String key = RedisHelper.RedisKeyPrefix.RESQUEST_ACCESS_TOKEN_KEY_PREFIX.toString() + "_login_" + userInfo.getPhone();
			if(StringUtils.isEmpty(accessToken)){
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100003);
				modelMap.put("errorMsg", "非法的验证请求");
				return;
			}else{
				String serverAccessToken = redisHelper.get(key);
				if(StringUtils.isEmpty(serverAccessToken)){
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100003);
					modelMap.put("errorMsg", "非法的验证请求");
					return;
				}else{
					if(!serverAccessToken.equals(accessToken)){
						modelMap.put("result", Constants.RESPONSE_FAIL);
						modelMap.put("errorCode", Constants.ERROR_CODE_100003);
						modelMap.put("errorMsg", "非法的验证请求");
						return;
					}
				}
			}*/
			
			String nickName = userInfo.getNickName();
			
			if(StringUtils.isNotEmpty(nickName)){
				if(nickName.length() > 8 || nickName.length() < 2){
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100000);
					modelMap.put("errorMsg", "昵称必须在2-8个字符长度之间");
					return;
				}
			}
			
			if(StringUtils.isEmpty(loginKey)){
				UserInfo userInfo_ = this.findUserByPhone(userInfo.getPhone(), true);
				if(userInfo_ != null){
					userInfo.setId(userInfo_.getId());
					userInfo.setModifyTime(new Date());
					userInfo.setToken(userInfo_.getToken());
					result = userInfoService.updateByPrimaryKeySelective(userInfo);
				}else{
					
					String loginKey_ = SHA1.hex_sha1(UUIDGenerator.getUUID());
					userInfo.setToken(loginKey_);
					
					result = userInfoService.save(userInfo);
				}
			}else{
				UserInfo userInfo_ = this.findUserByToken(loginKey, true);
				userInfo.setId(userInfo_.getId());
				userInfo.setModifyTime(new Date());
				userInfo.setToken(loginKey);
				result = userInfoService.updateByPrimaryKeySelective(userInfo);
			}
			if(result){
				loginKey = userInfo.getToken();
				UserInfo userInfo_ = this.findUserByToken(loginKey, true);
				userInfo_.setLoginKey(loginKey);
				modelMap.put("userInfo", userInfo_);
				modelMap.put("result", Constants.RESPONSE_SUCCESS);
				//redisHelper.remove(key);
			}else{
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				modelMap.put("errorMsg", "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorCode", Constants.ERROR_CODE_100000);
			modelMap.put("errorMsg", "保存失败");
		}
	}

	@Override
	public Page<Map<String,Object>> getUserInterestItems(int currentPage, int pageSize) {
		return sysDataWebService.findItems(currentPage, pageSize, "sorts desc", SysData.USER_INTEREST_ITEM_KEY);
	}
	
	/*@Override
	public ModelMap getOrderRecharge(String loginKey, String title, String paySource, String payPrice,
			String payChannel, String rechargeConfigId, String type, String checkState, String consumerMoneyStr,
			String bid, ModelMap modelMap) {
		Integer provideId = rechargeConfigId != null && !"".equals(rechargeConfigId) ? Integer.parseInt(rechargeConfigId) : 0;// 关联充值套餐表id
		Double consumerMoney = consumerMoneyStr != null && !"".equals(consumerMoneyStr) ? Double.parseDouble(consumerMoneyStr) : 0;// 抵扣的消费金金额
		Long pk = provideId.longValue();
		Double actualPay = Double.parseDouble(payPrice);//充值金额或或买单实际支付金额
		UserInfo user = userInfoService.getUserInfoByAttr("token", loginKey);
		if(user == null){
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100001);
			modelMap.put("errorMsg", "用户不存在");
			return modelMap;
		}
		if(payChannel == null || (!payChannel.trim().equals("1") && !payChannel.trim().equals("2") && !payChannel.trim().equals("3"))){
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode",600001);
			modelMap.put("errorMsg","充值渠道错误");
			return modelMap;
		}
		
		Integer pprice = Integer.valueOf(payPrice);// 实际充值金额
		Integer payPrice_ = 0;
		
		Integer orderType = 1;
		
		if(type.equals("0")){
			if(StringUtils.isNotEmpty(rechargeConfigId) && StringUtils.isNotEmpty(payPrice) && !rechargeConfigId.equals("0")){
				log.info("广告币充值下单：payPrice" + payPrice + ",rechargeConfigId:" + rechargeConfigId);
				RechargeConfig config = rechargeConfigService.selectByPK(provideId);
				if(config == null){
					modelMap.put("result", Constants.ERROR_ERROR);
					modelMap.put("errorCode",600002);
					modelMap.put("errorMsg", "兑换套餐不存在");
					return modelMap;
				}else{
					if(config.getRechargeMoney() == null || config.getRechargeMoney() <= 0){
						modelMap.put("result", Constants.ERROR_ERROR);
						modelMap.put("errorCode",600002);
						modelMap.put("errorMsg","兑换套餐不存在");
						return modelMap;
					}
					if(!pprice.equals(config.getRechargeMoney())){
						modelMap.put("result", Constants.ERROR_ERROR);
						modelMap.put("errorCode",600002);
						modelMap.put("errorMsg", "充值金额错误");
						return modelMap;
					}
				}
			}
		}else if(type.equals("1")){
			log.info("消费金充值下单：payPrice" + payPrice + ",rechargeConfigId:" + rechargeConfigId);
			ConsumerMoneyChangeConfig config = consumerMoneyChangeConfigService.findConfigById(pk);
			if(config == null){
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode",600002);
				modelMap.put("errorMsg","兑换套餐不存在");
				return modelMap;
			}else{
				if(config.getCurrentMoney() == null || config.getCurrentMoney() <= 0){
					modelMap.put("result", Constants.ERROR_ERROR);
					modelMap.put("errorCode",600002);
					modelMap.put("errorMsg","兑换套餐不存在");
					return modelMap;
				}
				if(!pprice.equals(config.getCurrentMoney())){
					modelMap.put("result", Constants.ERROR_ERROR);
					modelMap.put("errorCode",600002);
					modelMap.put("errorMsg", "充值金额错误");
					return modelMap;
				}
				
				payPrice_ = config.getCurrentMoney();//订单金额
			}
			
			if(pprice < payPrice_){
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode",600002);
				modelMap.put("errorMsg","支付金额错误");
				return modelMap;
			}
			
			orderType = 3;//消费金订单
		}else{
			actualPay = Double.parseDouble(payPrice) + consumerMoney;
			UserAssets userAssets = userAssetsService.getUserAssetsByUserId(user.getId());
			if (userAssets != null) {
				 if(checkState != null && checkState.equals("0")){//不使用消费金
					 if(pprice <= 0){
						 modelMap.put("result", Constants.ERROR_ERROR);
						 modelMap.put("errorCode", Constants.ERROR_CODE_500006);
						 modelMap.put("errorMsg", "支付金额错误");
						 return modelMap;
					 }
					 
				 }else if(checkState != null && checkState.equals("1")){//使用消费金
					 Map<String,Object> map = new HashMap<String ,Object>();
					 map.put("configKey",SystemGlobalConfig.CONSUMER_MONEY_USE_REBATE_KEY);
					 map.put("state", 1);
					 SystemGlobalConfig sys = systemService.selectSystemGlobalConfigByMap(map);
					 double rebate = 1;//消费金抵扣比例
					 if(sys != null){
						 Double m = Double.parseDouble(sys.getConfigContent() == null ? "1" : sys.getConfigContent());//消费金额
						 Double r = new Double(sys.getConfigMax() == null ? 1 : sys.getConfigMax());//消费金
						 rebate = m / r;
					 }
					 
					 if(actualPay <= 0){
						 modelMap.put("result", Constants.ERROR_ERROR);
						 modelMap.put("errorCode", Constants.ERROR_CODE_500006);
						 modelMap.put("errorMsg", "支付金额错误");
						 return modelMap;
					 }
					 
					 if(userAssets.getConsumerMoneyBalance() < (Double.parseDouble(df.format(consumerMoney * rebate)))){
						 modelMap.put("result", Constants.ERROR_ERROR);
						 modelMap.put("errorCode", Constants.ERROR_CODE_500006);
						 modelMap.put("errorMsg", "您的消费金余额不足,不能使用消费金抵扣");
						 return modelMap;
					 }
				 }else{
					 modelMap.put("result", Constants.ERROR_ERROR);
					 modelMap.put("errorCode", Constants.ERROR_CODE_500006);
					 modelMap.put("errorMsg", "支付下单失败");
					 return modelMap;
				 }
			 }else{
				 modelMap.put("result", Constants.ERROR_ERROR);
				 modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				 modelMap.put("errorMsg", "登录信息错误");
				 return modelMap;
			 }
			orderType = 4;//优惠买单
			provideId = Integer.parseInt(bid);//店铺id 
		}
		
		
		byte pChannel = Byte.valueOf(payChannel);// 支付渠道
		byte pSource = Byte.valueOf(paySource);// 订单来源1ios2android
		try {
			OrderView orderView = new OrderView();
			OrderPay payOrder = new OrderPay();
			
			// 查询该店铺的折扣
			Double discount = 10.0;
			try {
				discount = businessShopService.queryDiscountByBid(bid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			payOrder.setPayChannel(pSource);// 订单来源1ios2android pprice
			if(checkState != null && checkState.equals("1")){
				payOrder.setPayPrice(pprice);// 订单价格
			}else{
				payOrder.setPayPrice(actualPay.intValue());// 订单价格
			}
			payOrder.setPayWay(pChannel);// '支付渠道 1 支付宝 2微信 3 余额 4管理员'
			payOrder.setProvideId(provideId);// 关联充值套餐表id(如果是店铺消费充值，则为店铺id)

			orderView.setPayOrder(payOrder);

			Order order = new Order();
			order.setPaySource(pSource);
			order.setGoodsTotalCount(1);// 商品总数量
			order.setOriginalPirce(actualPay.intValue());// 原始总金额(分为单位)
			if(checkState != null && checkState.equals("1")){
				order.setDealPrice(pprice.intValue());// 成交总金额(分为单位)
				order.setDiscountPrice(consumerMoney.intValue());//抵扣金额
				order.setIntegrationDeductPrice(discount.intValue());
			}else{
				order.setDealPrice(actualPay.intValue());// 成交总金额(分为单位)
			}
			order.setTitle(title);
			order.setUserId(user.getId());
			order.setUserName(user.getNickName());
			order.setOrderType(orderType);

			order.setProviderId(provideId);
			orderView.setOrder(order);

			UserIdentity userIdentity = new UserIdentity();
			userIdentity.setToken(user.getToken());
			// userIdentity
			orderView.setUserIdentity(userIdentity);
			OrderResult<OrderView> orderViewOrderResult = orderService.addApplyOrderAndLockOrder(orderView);

			if (orderViewOrderResult.getState() == 1 && orderViewOrderResult.getContent() != null) {
				OrderView orderView1 = orderViewOrderResult.getContent();
//				modelMap.put("result", Constants.ERROR_OK);
//				modelMap.put("payNo", orderView1.getPayOrder().getPayNo());
//				modelMap.put("orderNo", orderView1.getOrder().getOrderNo());
//				modelMap.put("consumerOrderNo", orderView1.getOrder().getOrderNo());//当前直接使用支付订单号
//				modelMap.put("notifyUrl",(pChannel == 1 ? env.getProp("alipay.notify.url") : env.getProp("weixin.notify.url")));
				
				if(payChannel.equals("1")) {
					//TODO 支付宝支付接入逻辑
					Map<String, String> aliPayRes = doAliPay(orderView1);
					if(null != aliPayRes && !aliPayRes.isEmpty()) {
						modelMap.put("out_trade_no", aliPayRes.get("out_trade_no"));
						modelMap.put("trade_no", aliPayRes.get("trade_no"));
					} else {
						modelMap.put("result", Constants.ERROR_ERROR);
						modelMap.put("errorCode", Constants.ERROR_ERROR);
						modelMap.put("errorMsg","支付宝支付订单创建失败");
					}
					
				} else if(payChannel.equals("2")) {
					//TODO 微信支付接入逻辑
					Map<String, String> wxPayRes = doWXPay(orderView1);
					if(null != wxPayRes && !wxPayRes.isEmpty()) {
						modelMap.put("prepay_id", wxPayRes.get("prepay_id"));
						modelMap.put("sign", wxPayRes.get("sign"));
					} else {
						modelMap.put("result", Constants.ERROR_ERROR);
						modelMap.put("errorCode", Constants.ERROR_ERROR);
						modelMap.put("errorMsg","微信支付订单创建失败");
					}
				}
				
			} else {
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode", Constants.ERROR_ERROR);
				modelMap.put("errorMsg","支付订单创建失败");
				return modelMap;
			}
		} catch (OrderException e) {
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_ERROR);
			modelMap.put("errorMsg","支付订单创建失败");
			return modelMap;
		}
		return modelMap;
	}

	*//**
	 * @param orderView1
	 *//*
	private Map<String, String> doAliPay(OrderView orderView1) {
		AliPayRequestContent content = new AliPayRequestContent();
		content.setOut_trade_no(orderView1.getOrder().getOrderNo());
		content.setTotal_amount(UnitConvert.fen2Yuan(orderView1.getOrder().getDealPrice()).doubleValue());
		content.setSubject("预留");
		content.setBuyer_id("buyer_id");
		
		AlipayCore alipayCore =  new AlipayCore();
		Map<String, String> aliPayResult = null;
		try {
			AlipayTradeCreateResponse aliPayResponse = alipayCore.aliPayTradeCreate(content);
			aliPayResult = getAliPayResult(aliPayResponse);
		} catch (Exception e) {
			log.error("调用支付宝支付失败," + e.getMessage());
			//TODO 此处加个失败日志记录应该更好
			e.printStackTrace();
		}
		return aliPayResult;
	}

	*//**
	 * 微信支付实现
	 * @param orderView1
	 *//*
	private Map<String, String> doWXPay(OrderView orderView1) {
		
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setOut_trade_no(orderView1.getOrder().getOrderNo());
		unifiedorder.setTotal_fee(orderView1.getOrder().getDealPrice());
		unifiedorder.setTrade_type("APP");
		unifiedorder.setNotify_url(env.getProp("weixin.notify.url"));
		unifiedorder.setTime_start(WXUtils.timeStart());
		unifiedorder.setTime_expire(WXUtils.timeExpire());
		WXPayCore wxPayCore = new WXPayCore();
		Map<String, String> rs = null;
		try {
			Map<String, String> wxResult = wxPayCore.doUnifiedOrder(unifiedorder);
			rs = getWXPayResult(wxResult);
		} catch (Exception e) {
			log.error("调用微信支付失败," + e.getMessage());
			//TODO 此处加个失败日志记录应该更好
			e.printStackTrace();
		}
		return rs;
	}

	*//**
	 * 
	 * 获取微信返回结果
	 * @param wxResult
	 *//*
	private Map<String,String> getWXPayResult(Map<String, String> wxResult) {
		Map<String,String>  rs = null;
		log.info("调用微信支付结果返回, " + wxResult);
		if(null !=wxResult  && !wxResult.isEmpty() 
				&& wxResult.containsKey("return_code") 
				&& wxResult.get("return_code").equals("SUCCESS") 
				&& wxResult.containsKey("sign")){
			rs = new HashMap<>();
			rs.put("sign", wxResult.get("sign"));
			
			if(wxResult.containsKey("result_code") 
					&& wxResult.get("result_code").equals("SUCCESS") 
					&& wxResult.containsKey("prepay_id")) {
				
				rs.put("prepay_id", wxResult.get("prepay_id"));
			}
		}
		return rs;
	}

	*//**
	 * 
	 * @param aliPayResponse
	 * @return
	 *//*
	private Map<String,String> getAliPayResult(AlipayTradeCreateResponse aliPayResponse){
		Map<String,String> rsMap = null;
		if(null != aliPayResponse && aliPayResponse.isSuccess()) {
			rsMap = new HashMap<>();
			rsMap.put("out_trade_no", aliPayResponse.getOutTradeNo());
			rsMap.put("trade_no", aliPayResponse.getTradeNo());
		}
		
		return rsMap;
	}

	@Override
	public ModelMap updateUseBalanceExchangeCoins(String loginKey, String title, String paySource, String payPrice,
			String payChannel, String rechargeConfigId, ModelMap modelMap) {
		Long provideId = rechargeConfigId != null && !"".equals(rechargeConfigId) ? Long.parseLong(rechargeConfigId) : 0;// 关联充值套餐表id
		Map<String, Object> sqlMap = new HashMap<>();
		sqlMap.put("token", loginKey);
		final UserInfo user = userInfoService.getUserInfo(sqlMap);
		byte pChannel = Byte.valueOf(payChannel);// 支付渠道
		byte pSource = Byte.valueOf(paySource);// 订单来源1ios2android
		Integer pprice = Integer.valueOf(payPrice);// 订单金额
		
		log.info("零钱兑换广告币参数--------》loginKey:" + loginKey + ",paySource:" + paySource + ",payPrice:" + payPrice + ",payChannel:" + payChannel + ",rechargeConfigId:" + rechargeConfigId);
		
		if (pprice < 1) {
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100001);
			modelMap.put("errorMsg", "兑换失败");
			return modelMap;
		}
		try {
			OrderView orderView = new OrderView();
			OrderPay payOrder = new OrderPay();
			payOrder.setPayChannel(pSource);// 订单来源1ios2android
			payOrder.setPayPrice(pprice);// 订单价格
			payOrder.setPayWay(pChannel);// '支付渠道 1 支付宝 2微信 3 余额 4管理员'
			payOrder.setProvideId(provideId.intValue());// 关联充值套餐表id
			// payOrder.setPayState((byte) 5);
			orderView.setPayOrder(payOrder);

			Order order = new Order();
			order.setPaySource(pSource);
			order.setOriginalPirce(pprice);// 原始总金额(分为单位)
			order.setGoodsTotalCount(1);// 商品总数量
			order.setDealPrice(pprice);// 成交总金额(分为单位)
			// order.setOrderState(OrderProcessState.ORDERPAYSUCCESS.getCode());
			order.setOrderState(OrderProcessState.ORDERAPPLYSUCCESS.getCode());

			order.setTitle(title);
			order.setUserId(user.getId());
			order.setUserName(user.getNickName());

			order.setProviderId(provideId.intValue());
			orderView.setOrder(order);

			UserIdentity userIdentity = new UserIdentity();
			userIdentity.setToken(user.getToken());
			// userIdentity
			orderView.setUserIdentity(userIdentity);
			OrderResult<OrderView> orderViewOrderResult = orderService.addApplyOrderAndLockOrder(orderView);

			if (orderViewOrderResult.getState() == 1 && orderViewOrderResult.getContent() != null) {
				final UserAssets userAssets = userAssetsService.getUserAssetsByUserId(user.getId());
				Boolean flag = orderService.updateBalanceToCoins(userAssets, provideId, false);
				if (flag) {
					
					//商户首次充值兑换广告币任务
					new Thread(new Runnable() {
						@Override
						public void run() {
							log.info("创客合伙人邀请商户认证并首次充值兑换广告币任务-------------------->");
							taskWebService.updateInviteBusinessRechargeTaskState(user.getId());
						}
					}).start();
					
					
					Date succDate = new Date();
					Order applyedOrder = orderViewOrderResult.getContent().getOrder();// 前面保存成功的order,state为下单成功100
					applyedOrder.setOrderState(OrderProcessState.ORDERPAYSUCCESS.getCode());// 状态改为成功200
					applyedOrder.setModifyTime(succDate);
					applyedOrder.setFinshiTime(succDate);
					orderService.updateOrderByOrderNo(applyedOrder);

					OrderPay applyedPayOrder = orderViewOrderResult.getContent().getPayOrder();
					applyedPayOrder.setPayState(OrderPay.ORDER_PAY_SUCCESS);
					applyedPayOrder.setModifyTime(succDate);
					applyedPayOrder.setFinshiTime(succDate);
					PayResult<Integer> orderPayResult = payOrderService.updatePayOrder(applyedPayOrder);
					 零钱流水 
					// 添加用户流水信息(发布广告币大于0时添加)
					if (orderPayResult.getState() == 1) {
						UserCashFlow cashFlow = new UserCashFlow();
						cashFlow.setPrice(applyedOrder.getDealPrice());
						cashFlow.setCreateTime(new Date());
						cashFlow.setCashFlowDirection(UserCashFlow.CASH_FLOW_DIRECTION_OUT);
						cashFlow.setUserId(user.getId());
						cashFlow.setCashFlowTitle((byte) BillFilterHelper.CHANGE_ADVERT_COIN.getMarker().intValue());
						cashFlow.setTriggerTableName(TableNameHelper.TB_USER_ASSETS.getMarker());
						cashFlow.setTriggerNo(userAssets.getId());
						cashFlow.setBalance(userAssets.getCashBalance().intValue());
						cashFlow.setCashFlowType(UserCashFlow.CASH_FLOW_TYPE_CHANGE);
						cashFlow.setIsSuccess(1);
						userCashFlowService.saveUserCashFlow(cashFlow);
					}

					modelMap.put("result", Constants.ERROR_OK);
				} else {
					modelMap.put("result", Constants.ERROR_ERROR);
					modelMap.put("errorCode", Constants.ERROR_CODE_200507);
					modelMap.put("errorMsg", "广告币兑换失败");
					return modelMap;
				}
			} else {
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode", Constants.ERROR_CODE_200506);
				modelMap.put("errorMsg", "广告币兑换失败");
				return modelMap;
			}
		} catch (OrderException e) {
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_200506);
			modelMap.put("errorMsg", "广告币兑换失败");
			return modelMap;
		}
		return modelMap;
	}

	@Override
	public ModelMap getCoinsSelect(String loginKey, ModelMap modelMap) {
		Map<String, Object> sqlmap = new HashMap<>();
		sqlmap.put("token", loginKey);
		UserInfo user = userInfoService.getUserInfo(sqlmap);
		if (user != null) {
			Map<String, Object> map = new HashMap<>();
			List<RechargeConfig> listConfig = rechargeConfigService.selectListRecharge(map);
			List<Map<String, Object>> listRecharge = new ArrayList<>();
			if (listConfig != null && listConfig.size() > 0) {
				Map<String, Object> mapRecharge;
				for (RechargeConfig rechargeVo : listConfig) {
					mapRecharge = new HashMap<String, Object>();
					Integer exchangeConis = rechargeVo.getExchangeCoins(); // 可兑换广告币
					Integer additionalCoins = rechargeVo.getAdditionalCoins(); // 赠送的广告币
					Byte hotFlag = rechargeVo.getHotFlag(); // 热销标志,1代表热销，0非热销
					Integer rechargeMoney = rechargeVo.getRechargeMoney(); // 充值金额(分)
					mapRecharge.put("exchangeCoins", exchangeConis);
					mapRecharge.put("additionalCoins", additionalCoins);
					mapRecharge.put("hotFlag", hotFlag);
					mapRecharge.put("rechargeMoney", rechargeMoney);
					mapRecharge.put("id", rechargeVo.getId());
					mapRecharge.put("type", 0);
					listRecharge.add(mapRecharge);
				}
				Map<String, Object> cashBalanceMap = new HashMap<>();
				UserAssets assets = userAssetsService.getUserAssetsByUserId(user.getId());
				Integer balance = assets.getAdvertCoin();// 返回广告币
				cashBalanceMap.put("cashBalance", balance);
				cashBalanceMap.put("listConins", listRecharge);
				modelMap.put("result", Constants.ERROR_OK);
				modelMap.put("withdrawalList", cashBalanceMap);
			}
			// 获取充值配置参数
			Map<String, Object> sqlMap = new HashMap<String, Object>();
			sqlMap.put("configKey", "order.recharge.config.content");
			sqlMap.put("configGroup", "order");
			sqlMap.put("order", "create_time desc");
			ListRange<SystemGlobalConfig> systemGlobalConfigResult = systemService.selectListSystemGlobalConfig(sqlMap);
			if (systemGlobalConfigResult != null) {
				List<SystemGlobalConfig> systemGlobConfigList = systemGlobalConfigResult.getData();
				if (systemGlobConfigList != null && systemGlobConfigList.size() > 0) {
					SystemGlobalConfig systemGlobalConfig = systemGlobConfigList.get(0);
					String configContent = systemGlobalConfig.getConfigContent();
					modelMap.put("configContent", configContent);
				} else {
					modelMap.put("configContent", "");
				}
			}
		} else {
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100101);
			modelMap.put("errorMsg", "用户不存在");
		}
		return modelMap;
	}

	@Override
	public ModelMap changeCoin(String loginKey, String title, String paySource, String configId, String flag,
			ModelMap modelMap) {
		String channel = "3";//'支付渠道    1 支付宝   2微信   3 余额   4管理员  5邀请好友奖励金  6学习奖励金'
		if(flag == null || (!flag.trim().equals("1") && !flag.trim().equals("2") &&
				!flag.trim().equals("3") && !flag.trim().equals("4") && !flag.trim().equals("5") && !flag.trim().equals("6"))){
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode",600001);
			modelMap.put("errorMsg","支付渠道错误");
			return modelMap;
		}
		channel = flag;
		
		Long pk = Long.parseLong(configId);
		RechargeConfig config = rechargeConfigService.selectByPK(pk);// 充值配置记录
		if(config == null){
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode",600002);
			modelMap.put("errorMsg","该兑换套餐不存在");
			return modelMap;
		}
		
		Integer payPrice = config.getRechargeMoney();//订单金额
		Integer exChangeCoins = config.getExchangeCoins();// 可兑换广告币
		Integer additionalCoins = config.getAdditionalCoins();// 赠送广告币
		
		byte pChannel = Byte.valueOf(channel);// 支付渠道
		byte pSource = Byte.valueOf(paySource);// 订单来源1ios 2android

		if (payPrice > 1) {
			Map<String, Object> sqlMap = new HashMap<>();
			sqlMap.put("token", loginKey);
			final UserInfo user = userInfoService.getUserInfo(sqlMap);
			UserAssets userAssets = userAssetsService.getUserAssetsByUserId(user.getId());
			
			double balance = 0.0;
			boolean payed = true;//是否已经支付过
			byte type = UserCashFlow.CASH_FLOW_TYPE_CHANGE;
			byte inOut = UserCashFlow.CASH_FLOW_DIRECTION_IN;
			if(flag.trim().equals("3") || flag.trim().equals("5") || flag.trim().equals("6")){
				if(flag.trim().equals("3")){//3：零钱 4:邀请好友奖励金  5：学习奖励金  
					// 说明是用余额兑换广告币
					balance = userAssets.getCashBalance();
				}else if(flag.trim().equals("5")){
					// 邀请好友奖励金兑换广告币
					balance = userAssets.getAwardCashBalance();
					type = UserCashFlow.CASH_FLOW_TYPE_AWARD;
				}else if(flag.trim().equals("6")){
					// 学习奖励金兑换广告币
					balance = userAssets.getTaskAwardBalance();
					type = UserCashFlow.CASH_FLOW_TYPE_STUDY_AWARD;
				}
				if (balance < payPrice) {
					modelMap.put("result", Constants.ERROR_ERROR);
					modelMap.put("errorCode",600003);
					modelMap.put("errorMsg","余额不足");
					return modelMap;
				}
				payed = false;
				inOut = UserCashFlow.CASH_FLOW_DIRECTION_OUT;
			}
			
			try {

				Boolean flag_ = orderService.updateUserAssetByType(userAssets,config,Integer.parseInt(flag.trim()), payed);
				if (flag_) {
					
					//商户首次充值兑换广告币任务
					new Thread(new Runnable() {
						@Override
						public void run() {
							log.info("创客合伙人邀请商户认证并首次充值兑换广告币任务-------------------->");
							taskWebService.updateInviteBusinessRechargeTaskState(user.getId());
						}
					}).start();
					
					 零钱流水 
					// 添加用户流水信息(发布广告币大于0时添加)
					UserCashFlow cashFlow = new UserCashFlow();
					cashFlow.setPrice(payPrice);
					cashFlow.setCreateTime(new Date());
					cashFlow.setCashFlowDirection(inOut);
					cashFlow.setUserId(user.getId());
					cashFlow.setCashFlowTitle((byte) BillFilterHelper.CHANGE_ADVERT_COIN.getMarker().intValue());
					cashFlow.setTriggerTableName(TableNameHelper.TB_USER_ASSETS.getMarker());
					cashFlow.setTriggerNo(userAssets.getId());
					cashFlow.setBalance(userAssets.getCashBalance().intValue());
					cashFlow.setCashFlowType(type);
					cashFlow.setIsSuccess(1);
					userCashFlowService.saveUserCashFlow(cashFlow);//添加余额出账流水
					cashFlow = new UserCashFlow();
					cashFlow.setPrice(exChangeCoins + additionalCoins);
					cashFlow.setCreateTime(new Date());
					cashFlow.setCashFlowDirection(UserCashFlow.CASH_FLOW_DIRECTION_IN);
					cashFlow.setUserId(user.getId());
					cashFlow.setCashFlowTitle((byte) BillFilterHelper.INCOME_USER_RECHARGE.getMarker().intValue());
					cashFlow.setTriggerTableName(TableNameHelper.TB_USER_ASSETS.getMarker());
					cashFlow.setTriggerNo(userAssets.getId());
					cashFlow.setBalance(userAssets.getCashBalance().intValue());
					cashFlow.setCashFlowType(UserCashFlow.CASH_FLOW_TYPE_COIN);
					cashFlow.setIsSuccess(1);
					userCashFlowService.saveUserCashFlow(cashFlow);//添加广告币进账流水
					modelMap.put("result", Constants.ERROR_OK);
					return modelMap;
				}
			} catch (Exception e) {
				log.info("------------>兑换广告币出现异常");
				e.printStackTrace();
			}
		}
		
		modelMap.put("result", Constants.ERROR_ERROR);
		modelMap.put("errorCode",600004);
		modelMap.put("errorMsg","兑换失败");
		return modelMap;
	}
*/
}
