package com.mouchina.server.provider.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.dao.sys.SysRechargeConfigMapper;
import com.mouchina.entity.pojo.sys.SysRechargeConfig;
import com.mouchina.entity.pojo.sys.SysRechargeConfigExample;
import com.mouchina.entity.pojo.sys.SysRechargeConfigExample.Criteria;
import com.mouchina.server.api.sys.SysRechargeConfigService;

@Service("sysRechargeConfigService")
public class SysRechargeConfigServiceImpl implements SysRechargeConfigService{

	@Autowired
	private SysRechargeConfigMapper sysRechargeConfigMapper;
	
	@Override
	public SysRechargeConfig findByPrimaryKey(Long pk) {
		return sysRechargeConfigMapper.selectByPrimaryKey(pk);
	}

	@Override
	public boolean save(SysRechargeConfig t) {
		if(t != null){
			return sysRechargeConfigMapper.insert(t) == 1;
		}
		return false;
	}

	@Override
	public boolean saveBySelective(SysRechargeConfig t) {
		if(t != null){
			return sysRechargeConfigMapper.insertSelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		return sysRechargeConfigMapper.deleteByPrimaryKey(pk) == 1;
	}

	@Override
	public boolean updateByPrimaryKeySelective(SysRechargeConfig t) {
		if(t != null){
			return sysRechargeConfigMapper.updateByPrimaryKeySelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(SysRechargeConfig t) {
		if(t != null){
			return sysRechargeConfigMapper.updateByPrimaryKey(t) == 1;
		}
		return false;
	}

	@Override
	public List<SysRechargeConfig> findList(String order, SysRechargeConfig t) {
		SysRechargeConfigExample example = new SysRechargeConfigExample();
		Criteria c = example.createCriteria();
		if(StringUtils.isNotEmpty(order)){
			example.setOrderByClause(order);
		}
		if(t != null){
			c.andTypeEqualTo(t.getType());
		}
		return sysRechargeConfigMapper.selectByExample(example);
	}

	@Override
	public List<SysRechargeConfig> findPageList(int currentPage, int pageSize, String order, SysRechargeConfig t) {
		RowBounds row = new RowBounds((currentPage - 1) * pageSize, pageSize);
		SysRechargeConfigExample example = new SysRechargeConfigExample();
		example.setOrderByClause(order);
		Criteria c = example.createCriteria();
		if(t != null){
			//TODO
			c.andTypeEqualTo(t.getType());
		}
		return sysRechargeConfigMapper.selectByExampleWithRowbounds(example, row);
	}

}
