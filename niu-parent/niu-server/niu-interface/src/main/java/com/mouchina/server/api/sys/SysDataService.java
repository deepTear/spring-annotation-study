package com.mouchina.server.api.sys;

import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.server.api.base.BaseService;

public interface SysDataService extends BaseService<SysData,Long>{

	/**
	 * 获取由key 和 group 能唯一确定的单值配置
	 * @param key
	 * @param group
	 * @return
	 */
	public SysData findUniqueSysData(String key,SysData.Group group);
	
	/**
	 *通过key 获取相同key值得配置数量
	 * @param key
	 * @return
	 */
	public int countSysData(String key);
}
