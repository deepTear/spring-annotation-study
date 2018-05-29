package com.mouchina.server.provider.sys;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mouchina.entity.pojo.sys.SysBanner;
import com.mouchina.server.api.sys.SysBannerService;

@Service("sysBannerService")
public class SysBannerServiceImpl implements SysBannerService{

	@Override
	public SysBanner findByPrimaryKey(Long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(SysBanner t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveBySelective(SysBanner t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePrimaryKey(Long pk) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateByPrimaryKeySelective(SysBanner t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(SysBanner t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SysBanner> findList(String order,SysBanner t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysBanner> findPageList(int currentPage, int pageSize,String order, SysBanner t) {
		// TODO Auto-generated method stub
		return null;
	}

}
