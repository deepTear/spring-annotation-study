package com.mouchina.web.service.api;

import java.util.Map;

import org.springframework.ui.ModelMap;

import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.web.base.page.Page;

public interface UserWebService {

	/**
	 * 通过用户请求令牌查询出唯一的用户信息
	 * @param token
	 * @return
	 */
	public UserInfo findUserByToken(String token,boolean valid);
	
	/**
	 * 通过主键查找用户信息
	 * @param pk
	 * @return
	 */
	public UserInfo findUserByPrimaryKey(Long pk);
	
	/**
	 * 通过手机号，查询出用户
	 * @param phone
	 * @param valid
	 * @return
	 */
	public UserInfo findUserByPhone(String phone,boolean valid);
	
	
	/**
	 * 登陆验证
	 * @param flag  验证码标识
	 * @param phone 手机号
	 * @param identifyCode 验证码
	 * @param version app版本号
	 * @param modelMap
	 */
	public void login(String flag,String phone,String identifyCode,Integer version,ModelMap modelMap);
	
	/**
	 * 获取验证码
	 * @param phone 手机号
	 * @param flag  验证码标识
	 * @param modelMap 
	 */
	public void getIdenfityCode(String phone,String flag,ModelMap modelMap);
	
	
	/**
	 * 验证码验证
	 * @param flag 验证码标识
	 * @param phone 手机号
	 * @param identifyCode 客户端发送的验证码
	 * @param modelMap
	 * @return
	 */
	public boolean checkIdentifyCode(String flag,String phone, String identifyCode,ModelMap modelMap);
	
	
	/**
	 * 修改用户信息
	 * @param loginKey
	 * @param userInfo
	 * @param modelMap
	 */
	public void updateUserInfo(String loginKey,UserInfo userInfo,ModelMap modelMap);
	
	
	/**
	 * 获取用户兴趣选项
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String,Object>> getUserInterestItems(int currentPage,int pageSize);

	
	/**
	 * 下单充值(添加order payOrder)
	 * 
	 * @param loginKey
	 * @param title
	 * @param paySource
	 * @param payPrice 实际支付金额(分)
	 * @param payChannel
	 * @param rechargeConfigId
	 * @param checkState 是否勾选(0:未勾选,1:勾选)
	 * @param consumerMoneyStr 抵扣的消费金
	 * @param modelMap
	 * @return
	 *//*
	ModelMap getOrderRecharge(String loginKey, String title, String paySource, String payPrice, String payChannel,
			String rechargeConfigId,String type,String checkState,String consumerMoneyStr,String bid, ModelMap modelMap);


	*//**
	 * 余额兑换广告币
	 * 
	 * @param loginKey
	 * @param title
	 * @param paySource
	 * @param payPrice
	 * @param payChannel
	 * @param rechargeConfigId
	 * @param modelMap
	 * @return
	 *//*
	ModelMap updateUseBalanceExchangeCoins(String loginKey, String title, String paySource, String payPrice,
			String payChannel, String rechargeConfigId, ModelMap modelMap);
	
	*//**
	 * 广告币和充值配置查询
	 * 
	 * @param loginKey
	 * @param modelMap
	 * @return
	 *//*
	ModelMap getCoinsSelect(String loginKey, ModelMap modelMap);
	
	*//**
	 * 兑换广告币
	 * @param loginKey
	 * @param count
	 * @param flag
	 * @return
	 *//*
	public ModelMap changeCoin(String loginKey,String title,String paySource,String configId,String flag,ModelMap modelMap);
*/
}
