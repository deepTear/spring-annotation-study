package com.mouchina.web.controller.payNotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mouchina.web.service.api.PayWebService;

/**
 * Created by douzy on 16/2/15.
 */
public class NotifyController{
	private static final Logger logger = LoggerFactory
			.getLogger(NotifyController.class);

	private PayWebService orderService;
	
	

	/**
	 * SYSTEMERROR(0),
	 * SUCCESS(1),PAYORDERNOEXITS(2),ORDERNOEXITS(3),PAYNOLOCKED(
	 * 5),PAYNOFINISHED
	 * (6),ORDERNOFINISHED(7),CASHCOUPONINVALID(8),CASHCOUPONNOEXITS(9) 0系统错误
	 * 1成功 2支付订单不存在 3订单号不存在 5支付订单被锁 6支付已经完成 7订单已经完成 8优惠券无效 9优惠券不存在
	 *//*

	@RequestMapping(value = "/alipay_notify")
	public void aliPayNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> params = new HashMap<>();
		Map<String,Object> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
			logger.info("====> aliPayNotify  key="+name+",value="+valueStr);
		}
		logger.info("---------------------alipay_notify-------------------参数：out_trade_no(商户订单号) ："+request.getParameter("out_trade_no")+" --------------------");
		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status")
				.getBytes("ISO-8859-1"), "UTF-8");
		//订单金额
		String total_fee = new String(request.getParameter("total_amount")
				.getBytes("ISO-8859-1"), "UTF-8");

//		boolean verifyFlag =AlipayNotify.verify(params);  之前验签写法
		//TODO public_key 预留
		boolean verifyFlag = AlipaySignature.rsaCheckV1(params, "public_key", "utf-8", "RSA2"); //public_key 预留

		logger.info("====> aliPayNotify验证消息是否来自支付宝  out_trade_no : " + out_trade_no
				+ " ,trade_no:" + trade_no + ",total_fee=" + total_fee
				+ ",verifyFlag:" + verifyFlag + "<====");
		if (verifyFlag) {
			logger.info("==================================================");
			logger.info("====> aliPayNotify  trade_status : " + trade_status
					+ " <====");

			if (trade_status.equals("TRADE_FINISHED")
					|| trade_status.equals("TRADE_SUCCESS")) {
				// ------------------------------
				// 处理业务开始
				// ------------------------------
				PayOrderView payOrderView = new PayOrderView();
				OrderPay payOrder = new OrderPay();
				payOrder.setPayNo(out_trade_no);
				payOrder.setThirdPartyPayNo(trade_no);
				int payPrice = new BigDecimal(total_fee).multiply(
						new BigDecimal(100)).intValue();
				payOrder.setPayPrice(payPrice);
				payOrderView.setPayOrder(payOrder);

				try {
					
					PayResult<Integer> payResult = null;
					if(trade_no != null && !"".equals(trade_no)){
						payResult = payOrderService.updatePayOrderCheckAndUnlock(payOrderView);
					}
					if (payResult != null) {
						System.out.println("updatePayOrderCheckAndUnlock getPayOrderState ="
										+ payResult.getPayOrderState()
										+ ",getPayOrderSateContent="
										+ payResult.getPayOrderSateContent());
						if (payResult.getPayOrderState() == PayOrderState.SUCCESS) {
							System.out.println("==> aliPayNotify SUCCESS <==");
							response.getWriter().println("success");
							return;
						} else {
							if (payResult.getPayOrderState() == PayOrderState.PAYNOFINISHED
									|| payResult.getPayOrderState() == PayOrderState.ORDERNOFINISHED) {
								System.out.println("==> aliPayNotify FAIL PAYNOFINISHED or ORDERNOFINISHED  <==");
								response.getWriter().println("success");
								return;
							} else if (payResult.getPayOrderState() == PayOrderState.PAYPRICEERROR) {
								System.out.println("==> aliPayNotify FAIL payprice error  <==");
								response.getWriter().println("success");
								return;
							} else {
								response.getWriter().println("fail");
								return;
							}
						}
					} else {
						System.out.println("==> payResult null <==");
						response.getWriter().println("fail");
						return;
					}
				} catch (PayOrderException e) {
					System.out.println("----------------------------------updatePayOrderCheckAndUnlock process  payNo "
							+ payOrder.getPayNo()
							+ "   OrderException="
							+ e.toExecptionString());
					if (e.getPayOrderState() == PayOrderState.PAYNOFINISHED
							|| e.getPayOrderState() == PayOrderState.ORDERNOFINISHED) {
						System.out.println("==> aliPayNotify FAIL PAYNOFINISHED or ORDERNOFINISHED  <==");
						response.getWriter().println("success");
						return;
					} else if (e.getPayOrderState() == PayOrderState.PAYPRICEERROR) {
						logger.error("==> aliPayNotify FAIL payprice error  <==");
						response.getWriter().println("success");
						return;
					} else {
						response.getWriter().println("fail");
						return;
					}
				}
				// ------------------------------
				// 处理业务完毕
				// ------------------------------
			} else {
				response.getWriter().println("success");
				return;
			}
		} else {
			System.out.println("=================================================");
			System.out.println("====> aliPayNotify error : verify params <====");
			System.out.println("=================================================");
			response.getWriter().println("fail");
			return;
		}
	}

	// V3
	@RequestMapping(value = "/weixinpay_notify")
	public void weixinPayNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> resultMap = parseXml(request);
		WxPayResult wxPayResult = new WxPayResult();
		wxPayResult.setAppid(resultMap.get("appid"));
		wxPayResult.setAttach(resultMap.get("attach"));
		wxPayResult.setBank_type(resultMap.get("bank_type"));
		wxPayResult.setCash_fee(resultMap.get("cash_fee"));
		wxPayResult.setCash_fee_type(resultMap.get("cash_fee_type"));
		wxPayResult.setCoupon_count(resultMap.get("coupon_count"));
		wxPayResult.setCoupon_fee(resultMap.get("coupon_fee"));
		wxPayResult.setDevice_info(resultMap.get("device_info"));
		wxPayResult.setErr_code(resultMap.get("err_code"));
		wxPayResult.setErr_code_des(resultMap.get("err_code_des"));
		wxPayResult.setFee_type(resultMap.get("fee_type"));
		wxPayResult.setIs_subscribe(resultMap.get("is_subscribe"));
		wxPayResult.setMch_id(resultMap.get("mch_id"));
		wxPayResult.setNonce_str(resultMap.get("nonce_str"));
		wxPayResult.setOpenid(resultMap.get("openid"));
		wxPayResult.setOut_trade_no(resultMap.get("out_trade_no"));
		wxPayResult.setResult_code(resultMap.get("result_code"));
		wxPayResult.setSign(resultMap.get("sign"));
		wxPayResult.setTime_end(resultMap.get("time_end"));
		wxPayResult.setTotal_fee(resultMap.get("total_fee"));
		wxPayResult.setTrade_type(resultMap.get("trade_type"));
		wxPayResult.setTransaction_id(resultMap.get("transaction_id"));

		if (logger.isInfoEnabled()) {
			logger.info("==================================================");
			logger.info("====> weixinPayNotify wxPayResult="
					+ wxPayResult.toString() + "<====");
			logger.info("==================================================");
		}

		String successXml = "<xml>"
				+ "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		String failXml = "<xml>"
				+ "<return_code><![CDATA[FAIL]]></return_code>"
				+ "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";

		ResponseHandler resHandler = new ResponseHandler(request, response);
		if ("SUCCESS".equals(wxPayResult.getResult_code())) {
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			PayOrderView payOrderView = new PayOrderView();
			OrderPay payOrder = new OrderPay();
			payOrder.setPayNo(wxPayResult.getOut_trade_no());
			payOrder.setPayPrice(Integer.valueOf(wxPayResult.getTotal_fee()));
			payOrder.setThirdPartyPayNo(wxPayResult.getTransaction_id());
			payOrderView.setPayOrder(payOrder);
			try {
				PayResult<Integer> payResult = null;
				
				if(wxPayResult.getTransaction_id() != null && !wxPayResult.getTransaction_id().equals("")){
					payResult = payOrderService
					.updatePayOrderCheckAndUnlock(payOrderView);
				}
				
				

				if (payResult != null) {
					logger.info("updatePayOrderCheckAndUnlock getPayOrderState ="
							+ payResult.getPayOrderState()
							+ ",getPayOrderSateContent="
							+ payResult.getPayOrderSateContent());
					if (payResult.getPayOrderState() == PayOrderState.SUCCESS) {
						logger.info("==> weixinPay SUCCESS <==");
						resHandler.sendToCFT(successXml);
						return;
					} else {
						if (payResult.getPayOrderState() == PayOrderState.PAYNOFINISHED
								|| payResult.getPayOrderState() == PayOrderState.ORDERNOFINISHED) {
							logger.error("==> weixinPay FAIL PAYNOFINISHED or ORDERNOFINISHED  <==");
							resHandler.sendToCFT(successXml);
							return;
						} else if (payResult.getPayOrderState() == PayOrderState.PAYPRICEERROR) {
							logger.error("==> weixinPay FAIL payprice error  <==");
							response.getWriter().println("success");
							return;
						} else {
							resHandler.sendToCFT(failXml);
							return;
						}
					}
				} else {
					logger.error("==> payResult null <==");
					response.getWriter().println(successXml);
					return;
				}

			} catch (PayOrderException e) {
				logger.error("----------------------------------updatePayOrderCheckAndUnlock process  payNo "
						+ payOrder.getPayNo()
						+ "   OrderException="
						+ e.toExecptionString());
				if (e.getPayOrderState() == PayOrderState.PAYNOFINISHED
						|| e.getPayOrderState() == PayOrderState.ORDERNOFINISHED) {
					logger.error("==> weixinPay FAIL PAYNOFINISHED or ORDERNOFINISHED  <==");
					resHandler.sendToCFT(successXml);
					return;
				} else if (e.getPayOrderState() == PayOrderState.PAYPRICEERROR) {
					logger.error("==> weixinPay FAIL payprice error  <==");
					response.getWriter().println("success");
					return;
				} else {
					response.getWriter().println(failXml);
					return;
				}
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
		} else {
			response.getWriter().println(failXml);
			return;
		}
	}

	
	*//**
	 * 支付宝支付异步通知
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/alipayNotify",method=RequestMethod.POST)
	public void alipayNodify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("支付宝回调开始..");
		Map<String, String> map = new HashMap<>();
		Map<String, Object> params = request.getParameterMap();
		
		for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			String[] values = (String[]) params.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			map.put(name, valueStr);
			logger.info("====> aliPayNotify  key=" + name + ",value=" + valueStr);
		}
		
		// 验证消息是否是支付宝发出的合法消息
//		boolean verifyFlag =AlipayNotify.verify(map);  // 之前验签写法
		//TODO public_key 预留
		boolean verifyFlag = AlipaySignature.rsaCheckV1(map, "public_key", "utf-8", "RSA2");  //public_key 预留
		
		//boolean verifyFlag =true;
		logger.info("验证支付宝信息通过!");
		
		
		if (verifyFlag) {
			// 如果返回状态成功，则执行修改订单业务
			logger.info("alipay获取参数..:"+request);
			String trade_status = request.getParameter("trade_status");
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				String out_trade_no = request.getParameter("out_trade_no");
				String trade_no = request.getParameter("trade_no");
				String total_amount = request.getParameter("total_amount");
				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("trade_no", trade_no);
				paramMap.put("total_amount", total_amount);
				paramMap.put("out_trade_no", out_trade_no);
				
				paramMap.put("trade_status", trade_status);//add by zhangkun 20170720加入具体支付状态，决定在回调中如何处理(到底是交易成功还是结束)
				
				int callback = orderService.updateAlipay(paramMap);
				if (callback > 0) {
					logger.info("alipay回调成功!");
					response.getWriter().println("success");
				} else {
					logger.info("alipay回调失败1!");
					orderService.updateOrderStateByOrderNo(out_trade_no, Order.ORDER_FAILD);
					response.getWriter().println("fail");
				}
			} else {
				logger.info("alipay回调失败2!");
				orderService.updateOrderStateByOrderNo(request.getParameter("out_trade_no"), Order.ORDER_FAILD);
				response.getWriter().println("fail");
			}
		} else {
//			tempOrderService.updatePayErrorMsg(params.get("out_trade_no"), params.get(""));
			response.getWriter().println("fail");
		}

	}

	*//**
	 * 微信支付异步通知
	 *//*
	@RequestMapping("/wechatpayNotify")
	public void wechatpayNodify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义返回值
		String successXml = "<xml>"
				+ "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		String failXml = "<xml>"
				+ "<return_code><![CDATA[FAIL]]></return_code>"
				+ "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";
		
		Map<String, String> resultMap = parseXml(request);
		logger.info("weixin call back begin....");
		WxPayResult wxPayResult = new WxPayResult();
		wxPayResult.setAppid(resultMap.get("appid"));
		wxPayResult.setAttach(resultMap.get("attach"));
		wxPayResult.setBank_type(resultMap.get("bank_type"));
		wxPayResult.setCash_fee(resultMap.get("cash_fee"));
		wxPayResult.setCash_fee_type(resultMap.get("cash_fee_type"));
		wxPayResult.setCoupon_count(resultMap.get("coupon_count"));
		wxPayResult.setCoupon_fee(resultMap.get("coupon_fee"));
		wxPayResult.setDevice_info(resultMap.get("device_info"));
		wxPayResult.setErr_code(resultMap.get("err_code"));
		wxPayResult.setErr_code_des(resultMap.get("err_code_des"));
		wxPayResult.setFee_type(resultMap.get("fee_type"));
		wxPayResult.setIs_subscribe(resultMap.get("is_subscribe"));
		wxPayResult.setMch_id(resultMap.get("mch_id"));
		wxPayResult.setNonce_str(resultMap.get("nonce_str"));
		wxPayResult.setOpenid(resultMap.get("openid"));
		wxPayResult.setOut_trade_no(resultMap.get("out_trade_no"));
		wxPayResult.setResult_code(resultMap.get("result_code"));
		wxPayResult.setSign(resultMap.get("sign"));
		wxPayResult.setTime_end(resultMap.get("time_end"));
		wxPayResult.setTotal_fee(resultMap.get("total_fee"));
		wxPayResult.setTrade_type(resultMap.get("trade_type"));
		wxPayResult.setTransaction_id(resultMap.get("transaction_id"));

		if (logger.isInfoEnabled()) {
			logger.info("==================================================");
			logger.info("====> weixinPayNotify wxPayResult=" + wxPayResult.toString() + "<====");
			logger.info("==================================================");
		}
		if ("SUCCESS".equals(wxPayResult.getResult_code())) {
			logger.info("weixin call back execute business logic");
			String thirdPartPayNo = resultMap.get("transaction_id");
			if(thirdPartPayNo != null && !"".equals(thirdPartPayNo.trim())){
				orderService.updateWechatpay(wxPayResult);
				logger.info("weixin call back execute business logic end");
			}
			response.getWriter().println(successXml);
		} else {
			orderService.updatePayErrorMsg(wxPayResult.getOut_trade_no(), wxPayResult.getErr_code_des());
			response.getWriter().println(failXml);
		}
	}
	
	private static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		return map;
	}*/
}