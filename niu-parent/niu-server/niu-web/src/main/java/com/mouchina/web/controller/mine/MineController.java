package com.mouchina.web.controller.mine;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mouchina.web.service.api.UserWebService;

public class MineController {

	private UserWebService userWebService;
	
	/**
	 * 下单充值(添加order payOrder)app用
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 *//*
	@RequestMapping(value = "/reCharge", method = { RequestMethod.POST, RequestMethod.GET })
	public String orderRecharge(HttpServletRequest request, ModelMap modelMap) {
		String loginKey = request.getParameter("loginKey");
		String title = request.getParameter("title");// 支付标题
		String paySource = request.getParameter("paySource");// 订单来源 (1 ios,2 android)
		String payPrice = request.getParameter("payPrice");// 实际支付金额(分)
		String payChannel = request.getParameter("payChannel");// 支付渠道 (1支付宝  2微信  3零钱下单（开启福袋情况下用到）)
		String rechargeConfigId = request.getParameter("rechargeConfigId");// 充值套餐id(关联providerId)
		String type = request.getParameter("type");//type 0：广告币   1：消费金   2：优惠买单    默认为  type = 0;
		String checkState = request.getParameter("checkState");//是否勾选(0:未勾选,1:勾选)
		String consumerMoneyStr = request.getParameter("consumerMoneyStr");// 抵扣的消费金
		String bid = request.getParameter("businessShopId");// 店铺id
		if(StringUtils.isEmpty(type)){
			type = "0";
		}else{
			if(!type.equals("0") && !type.equals("1") && !type.equals("2")){
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode", Constants.ERROR_CODE_100001);
				modelMap.put("errorMsg", "充值类型错误");
				return "";
			}
		}
		
		if(StringUtils.isEmpty(paySource)){
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100001);
			modelMap.put("errorMsg", "订单来源错误");
			return "";
		}
		
		if(StringUtils.isEmpty(loginKey)){
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100001);
			modelMap.put("errorMsg", "用户认证错误");
			return "";
		}
		
		modelMap = userWebService.getOrderRecharge(loginKey, title, paySource, payPrice, payChannel, rechargeConfigId,type,checkState,consumerMoneyStr,bid,modelMap);
		return "";
	}
	
	*//**
	 * 余额兑换广告币
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 *//*
	@RequestMapping(value = "/useBalanceExchangeCoins", method = { RequestMethod.POST, RequestMethod.GET })
	public String useBalanceExchangeCoins(HttpServletRequest request, ModelMap modelMap) {
		String loginKey = request.getParameter("loginKey");
		String type = request.getParameter("type");//type 0：广告币   1：消费金  2：优惠买单     默认为  type = 0;
		if(StringUtil.isEmpty(type)){
			type = "0";
		}else{
			if(!type.equals("0") && !type.equals("1") && !type.equals("2")){
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode", Constants.ERROR_CODE_100001);
				modelMap.put("errorMsg", "兑换类型错误");
				return "";
			}
		}
		
		if(StringUtil.isNotEmpty(loginKey)){
			String title = request.getParameter("title");// 支付标题
			String paySource = request.getParameter("paySource");// 订单来源 (1 ios,2 android)
			String payPrice = request.getParameter("payPrice");// 实际支付金额(分)
			String payChannel = request.getParameter("payChannel");// 支付渠道 (3零钱)
			String rechargeConfigId = request.getParameter("rechargeConfigId");// 充值套餐id(关联providerId)
			String bid = request.getParameter("businessShopId");// 店铺id
			String checkState = request.getParameter("checkState");//是否勾选(0:未勾选,1:勾选)
			String consumerMoneyStr = request.getParameter("consumerMoneyStr");// 抵扣的消费金
			
			if(type.equals("0")){
				modelMap = userWebService.updateUseBalanceExchangeCoins(loginKey, title, paySource, payPrice, payChannel,rechargeConfigId, modelMap);
			}else if(type.equals("1")){
				modelMap = consumerMoneyChangeConfigWebService.changeConsumerMoney(loginKey, title, paySource, rechargeConfigId, payChannel, modelMap);
			}else{
				modelMap = consumerOrderAppraiseWebService.userPaymentInfo(loginKey, title, paySource, payPrice, payChannel,bid,checkState,consumerMoneyStr,rechargeConfigId, modelMap);
			}
			return "";
		}
		modelMap.put("result", Constants.ERROR_ERROR);
		modelMap.put("errorCode", Constants.ERROR_CODE_100004);
		modelMap.put("errorMsg", "认证错误");
		
		return "";
	}
	
	*//**
	 * 广告币或消费金和充值配置查询
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 *//*
	@RequestMapping(value = "/coinsSelect", method = { RequestMethod.GET, RequestMethod.POST })
	public String CoinsSelect(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		String loginKey = request.getParameter("loginKey");
		String type = request.getParameter("type");//type 0：广告币   1：消费金       默认为  type = 0;
		
		if(StringUtil.isEmpty(type)){
			type = "0";
		}else{
			if(!type.equals("0") && !type.equals("1")){
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode", Constants.ERROR_CODE_100001);
				modelMap.put("errorMsg", "兑换类型错误");
			}
		}
		
		if (StringUtil.isEmpty(loginKey)) {
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100001);
			modelMap.put("errorMsg", "信息不完整");
			return "";
		}
		
		if(type.equals("0")){
			modelMap = userWebService.getCoinsSelect(loginKey, modelMap);
		}else{
			modelMap = consumerMoneyChangeConfigWebService.getConsumerMoneyBalanceAndChangeConfig(loginKey, modelMap);
		}
		return "";
	}*/
}
