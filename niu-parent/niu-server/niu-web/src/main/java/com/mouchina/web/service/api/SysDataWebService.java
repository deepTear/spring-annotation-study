package com.mouchina.web.service.api;

import java.util.Map;

import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.web.base.page.Page;

public interface SysDataWebService {

	public SysData findSysDataByKeyAndGroup(String key,SysData.Group group);
	
	public Page<Map<String,Object>> findItems(int currentPage,int pageSize,String order,String key);
}
