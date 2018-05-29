package com.mouchina.server.provider.sys;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.dao.sys.SysDataMapper;
import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.entity.pojo.sys.SysDataExample;
import com.mouchina.entity.pojo.sys.SysDataExample.Criteria;
import com.mouchina.server.api.sys.SysDataService;

@Service("sysDataService")
public class SysDataServiceImpl implements SysDataService{

	@Autowired
	private SysDataMapper sysDataMapper;
	
	@Override
	public SysData findByPrimaryKey(Long pk) {
		return sysDataMapper.selectByPrimaryKey(pk);
	}

	@Override
	public boolean save(SysData t) {
		if(t != null){
			return sysDataMapper.insert(t) == 1;
		}
		return false;
	}

	@Override
	public boolean saveBySelective(SysData t) {
		if(t != null){
			return sysDataMapper.insertSelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		return sysDataMapper.deleteByPrimaryKey(pk) == 1;
	}

	@Override
	public boolean updateByPrimaryKeySelective(SysData t) {
		if(t != null){
			return sysDataMapper.updateByPrimaryKeySelective(t) == 1;
		}
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(SysData t) {
		if(t != null){
			return sysDataMapper.updateByPrimaryKey(t) == 1;
		}
		return false;
	}

	@Override
	public List<SysData> findPageList(int currentPage, int pageSize, String order,SysData t) {
		
		RowBounds row = new RowBounds((currentPage - 1) * pageSize, pageSize);
		SysDataExample example = new SysDataExample();
		example.setOrderByClause(order);
		Criteria criteria = example.createCriteria();
		if(t != null){
			//TODO
			String key = t.getConfigKey();
			criteria.andConfigKeyEqualTo(key);
		}
		return sysDataMapper.selectByExampleWithRowbounds(example, row);
	}

	@Override
	public List<SysData> findList(String order, SysData t) {
		
		SysDataExample example = new SysDataExample();
		Criteria criteria = example.createCriteria();
		if(t != null){
			//TODO
		}
		return sysDataMapper.selectByExample(example);
	}
	
	@Override
	public SysData findUniqueSysData(String key, SysData.Group group) {
		SysDataExample example = new SysDataExample();
		Criteria criteria = example.createCriteria();
		criteria.andConfigKeyEqualTo(key);
		criteria.andConfigGroupEqualTo(group.name);
		criteria.andStateEqualTo((byte)0);
		List<SysData> list = sysDataMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int countSysData(String key) {
		SysDataExample example = new SysDataExample();
		Criteria criteria = example.createCriteria();
		criteria.andConfigKeyEqualTo(key);
		return sysDataMapper.countByExample(example);
	}

}
