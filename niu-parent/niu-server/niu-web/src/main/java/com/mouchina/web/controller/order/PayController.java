package com.mouchina.web.controller.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alipay.api.AlipayApiException;
import com.mouchina.web.base.utils.AliPayUtils;
import com.mouchina.web.service.api.PayWebService;

@Controller
@RequestMapping("/pay")
public class PayController {

	private Logger logger = Logger.getLogger(PayController.class);
	
	@Autowired
	private PayWebService payWebService;
	
	public String payValid(String loginKey,Integer type,Integer originalMoney,Integer actualMoney,ModelMap modelMap){
		
		return "";
	}
	
	
	/**
	 * 创建系统订单，并生成支付签名
	 * @param payChannel 应用类别  0：微信 1:支付宝
	 * @param title   订单标题
	 * @param detail  订单详情
	 * @param loginKey 订单用户token
	 * @param sourceId 订单源Id
	 * @param type   订单类别   0：广告币充值  
	 * @param originMoney  订单原始总额
	 * @param payType   支付类型   0：全额   1：折扣
	 * @param discount  折扣百分比
	 * @param count   商品数量
	 * @return
	 */
	@RequestMapping(value="/create/order",method={RequestMethod.POST,RequestMethod.GET})
	public String createSysOrderRecordAndGetPaySign(byte payChannel,String title,String detail,String loginKey,Long sourceId,@RequestParam(value="type",defaultValue="0") byte type,
			@RequestParam(value="originMoney",defaultValue="0")Integer originMoney,@RequestParam(value="payType",defaultValue="0")byte payType,
			@RequestParam(value="discount",defaultValue="100")Integer discount,@RequestParam(value="count",defaultValue="0")Integer count,ModelMap modelMap){
		
		payWebService.createSysOrderRecordAndGetPaySign(payChannel,title, detail, loginKey, sourceId, type, originMoney, payType, discount, count, modelMap);
		
		return "";
	}
	
	/**
	 * 支付宝完成支付后回调地址
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws AlipayApiException 
	 */
	public void alipayNotifyCallback(HttpServletRequest request,HttpServletResponse response) throws IOException, AlipayApiException{
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
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		//订单金额
		String total_fee = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

		boolean flag = AliPayUtils.checkSign(params);

		logger.info("====> aliPayNotify验证消息是否来自支付宝  out_trade_no : " + out_trade_no+ " ,trade_no:" + trade_no + ",total_fee=" + total_fee+ ",verifyFlag:" + flag + "<====");
		if (flag) {
			logger.info("==================================================");
			logger.info("====> aliPayNotify  trade_status : " + trade_status+ " <====");

			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				/*// ------------------------------
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
*/			} else {
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
	
	public String weixinpayNotifyCallback(HttpServletRequest request){
		
		return "";
	}
}
