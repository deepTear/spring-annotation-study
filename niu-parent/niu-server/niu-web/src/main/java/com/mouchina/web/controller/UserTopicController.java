package com.mouchina.web.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.SysDataWebService;
import com.mouchina.web.service.api.UserTopicWebService;
import com.mouchina.web.service.api.UserWebService;

@Controller
@RequestMapping("/topic")
public class UserTopicController {
	
	Logger log = Logger.getLogger(UserTopicController.class);

	@Autowired
	public UserTopicWebService userTopicWebService;
	
	@Autowired
	public SysDataWebService sysDataWebService;
	
	@Autowired
	public UserWebService userWebService;
	
	/**
	 * 
	 * @param loginKey  当前发布动人用户 token
	 * @param couponId  附加优惠券id
	 * @param redPacketMoney  附加红包金额
	 * @param limitType  动态可见类型 0：距离  1：粉丝
	 * @param type    动态类型  0：普通动态 1：红包动态 2：优惠券动态 3：红包+优惠卷
	 * @param content 动态内容
	 * @param longitude  经度
	 * @param latitude   纬度
	 * @param photos   附加图片
	 * @param videos   附加视频
	 * @param radius   可见范围半径
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/add")
	public String addTopic(String loginKey,Long couponId,int redPacketMoney,byte limitType,byte type,String content,String longitude,String latitude,
			String photos,String videos,int radius,ModelMap modelMap){
		
		if(StringUtils.isEmpty(content)){
			modelMap.put("result", Constants.RESPONSE_FAIL);
			modelMap.put("errorCode", Constants.ERROR_CODE_100000);
			modelMap.put("errorMsg", "发布内容不能为空");
			return "";
		}
		
		if(type == 1 || type == 3){
			//发布动态最低金额配置
			SysData sd = sysDataWebService.findSysDataByKeyAndGroup(SysData.PUBLISH_TOPIC_MIN_MONEY_KEY, SysData.Group.ALL);
			
			int minMoney = 100;
			if(sd != null){
				String data = sd.getConfigData();
				if(StringUtils.isNotEmpty(data)){
					try {
						minMoney = Integer.parseInt(data);
					} catch (NumberFormatException e) {
						log.warn("发布动态时，最低金额配置错误");
					}
				}
			}
			if(redPacketMoney < minMoney){
				modelMap.put("result", Constants.RESPONSE_FAIL);
				modelMap.put("errorCode", Constants.ERROR_CODE_100000);
				modelMap.put("errorMsg", "发布福袋动态金额不能低于" + (minMoney / 10) + "元");
				return "";
			}
		}else if(type == 0){//发布普通红包
			UserInfo u = userWebService.findUserByToken(loginKey, true);
			Date date = new Date();
			int count = userTopicWebService.countPublishTopic(date, u.getId(), true);//用户当天发布的免费动态
			int role = u.getRole();//角色
			if(role == 0){//普通用户
				int maxCount = 1;
				SysData sd = sysDataWebService.findSysDataByKeyAndGroup(SysData.GENERAL_USER_PUBLISH_FREE_TOPIC_MAX_COUNT_KEY, SysData.Group.USER);
				if(sd != null){
					try {
						maxCount = Integer.parseInt(sd.getConfigData());
					} catch (NumberFormatException e) {
						log.warn("发布动态时，普通用户每天免费发布动态最大数量配置错误");
					}
				}
				if(count > maxCount){
					modelMap.put("result", Constants.RESPONSE_FAIL);
					modelMap.put("errorCode", Constants.ERROR_CODE_100000);
					modelMap.put("errorMsg", "每天只能发布一条");
					return "";
				}
			}else if(role == 1){//系统内部用户
				
			}else if(role == 2){//商户
				int days = 30;
				int maxCount = 1;
				SysData sd = sysDataWebService.findSysDataByKeyAndGroup(SysData.SHOP_USER_REGISTER_PUBLISH_FREE_TOPIC_MAX_DAYS_KEY, SysData.Group.SHOP);
				SysData sd2 = sysDataWebService.findSysDataByKeyAndGroup(SysData.SHOP_USER_PUBLISH_FREE_TOPIC_MAX_COUNT_KEY, SysData.Group.SHOP);
				if(sd != null){
					try {
						maxCount = Integer.parseInt(sd.getConfigData());
					} catch (NumberFormatException e) {
						log.warn("商户注册后每天可免费发布动态最大数量配置错误");
					}
				}
				if(sd2 != null){
					try {
						days = Integer.parseInt(sd2.getConfigData());
					} catch (NumberFormatException e) {
						log.warn("商户注册后限制发布免费动态天数配置错误");
					}
				}
				
				//TODO
				Date registerDate = new Date();//认证商户后的
				/*if(){
					
				}*/
				
			}
			
		}
		
		return "";
	}
	
}
