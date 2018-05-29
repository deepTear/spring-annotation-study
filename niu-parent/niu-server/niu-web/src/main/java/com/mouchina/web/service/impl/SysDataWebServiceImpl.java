package com.mouchina.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.server.api.sys.SysDataService;
import com.mouchina.web.base.page.Page;
import com.mouchina.web.service.api.SysDataWebService;

@Service
public class SysDataWebServiceImpl implements SysDataWebService{

	@Autowired
	private SysDataService sysDataService;
	
	@Override
	public SysData findSysDataByKeyAndGroup(String key,SysData.Group group) {
		return sysDataService.findUniqueSysData(key, group);
	}

	@Override
	public Page<Map<String,Object>> findItems(int currentPage, int pageSize,String order, String key) {
		SysData sd = new SysData();
		sd.setConfigKey(key);
		List<SysData> list = sysDataService.findPageList(currentPage, pageSize, order, sd);
		int count = sysDataService.countSysData(key);
		
		List<Map<String,Object>> data = new ArrayList<>();
		if(list != null && list.size() > 0){
			Map<String,Object> map = null;
			for(SysData ss : list){
				map = new HashMap<>();
				map.put("value", ss.getConfigData());
				map.put("name", ss.getConfigName());
				data.add(map);
			}
		}
		
		Page<Map<String,Object>> page = new Page<>(currentPage, pageSize, count, data);
		
		return page;
	}

}
