package com.mouchina.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.entity.pojo.sys.SysRechargeConfig;
import com.mouchina.server.api.sys.SysRechargeConfigService;
import com.mouchina.web.service.api.SysRechargeConfigWebService;

@Service
public class SysRechargeConfigWebServiceImpl implements SysRechargeConfigWebService {

	@Autowired
	private SysRechargeConfigService sysRechargeConfigService;

	@Override
	public SysRechargeConfig findById(Long id) {
		return sysRechargeConfigService.findByPrimaryKey(id);
	}

	@Override
	public List<SysRechargeConfig> findAllByType(String order, byte type) {
		SysRechargeConfig config = new SysRechargeConfig();
		config.setType(type);
		return sysRechargeConfigService.findList(order, config);
	}
	
	
}
