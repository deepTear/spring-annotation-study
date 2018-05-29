package com.mouchina.server.api.user;

import java.util.Map;

import com.mouchina.entity.pojo.user.UserInfo;
import com.mouchina.server.api.base.BaseService;

public interface UserInfoService extends BaseService<UserInfo,Long>{
	

	public Long createUserUniqueId();
	
	public UserInfo findUserByPhone(String phone,boolean valid);
	
	public UserInfo findUserByToken(String token,boolean valid);
	

	/**
     * 根据特定属性（保证唯一）获取UserInfo对象
     *
     * @param attr
     * @param val
     * @return
     */
	UserInfo getUserInfoByAttr(String attr, Object val);
    
    /**
     * 获取用户信息
     *
     * @param map
     * @return
     */
	UserInfo getUserInfo(Map<String, Object> map);

	/**
     * 更新用户基本信息
     *
     * @param map
     * @return
     */
    Boolean updateUserInfo(UserInfo userInfo);
    

}
