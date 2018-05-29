package com.mouchina.web.service.api;

import java.util.List;

import com.mouchina.entity.pojo.sys.SysRechargeConfig;

public interface SysRechargeConfigWebService {

	public SysRechargeConfig findById(Long id);
	
	public List<SysRechargeConfig> findAllByType(String order,byte type);
}
